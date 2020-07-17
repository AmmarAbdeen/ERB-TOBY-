package com.toby.businessservice;

import com.toby.bussinessservice.global.InvPurchaseReturnSave;
import com.toby.core.GenericDAO;
import com.toby.dto.InvReservationDTO;
import com.toby.dto.InvReservationDetailDTO;
import com.toby.entity.InvInventory;
import com.toby.entity.InvItem;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.InvReservation;
import com.toby.entity.InvReservationDetail;
import com.toby.entity.InventoryDelegator;
import com.toby.entity.InventorySetup;
import com.toby.entity.TobyUser;
import com.toby.views.ItemsBarcodesDetailsView;
import com.toby.views.QuantityItemsStore;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author khaled
 */
@Stateless
public class InvReservationServiceImp implements InvReservationService {

    @EJB
    private GenericDAO dao;
    @EJB
    private GenericService genericService;
    @EJB
    private InvReservationDetailService invReservationDetailService;
    @EJB
    private InventorySetupService inventorySetupService;
    @EJB
    private QuantityItemsStoreService itemsStoreService;
    @EJB
    private InvItemService invItemService;
    @EJB
    private ItemsBarcodesDetailsViewService itemsBarcodesDetailsViewService;

    InvPurchaseReturnSave invPurchaseReturnSave;

    @Override
    public InvPurchaseReturnSave addInvReservation(InvReservation invReservation, List<InvReservationDetail> invReservationDetailDetailList,
            List<InvReservationDetail> ReservationDetailDeleted) {
        Integer serial;
        invPurchaseReturnSave = new InvPurchaseReturnSave();
        if (invReservation.getId() != null) {
            dao.updateEntity(invReservation);
        } else {
            serial = genericService.getMaxGenericSerialByType(InvReservation.class, invReservation.getBranchId().getId(), null);
            invReservation.setSerial(serial);
            dao.saveEntity(invReservation);
        }

        String msg = updateFinalQuantityAndStatusDetailAndSaveDetail(invReservationDetailDetailList, invReservation);

        if (msg == null) {
            // لو الدنيا اللى فاتت تمام نروح نمسح من قاعدة البيانات اللى اتمسح من الفاتورة
            deleteDetails(ReservationDetailDeleted);

        } else {
            dao.rollbackQuery();
            invReservation.setMsg(msg);
            invPurchaseReturnSave.setMsg(msg);
        }
        invPurchaseReturnSave.setInvReservation(invReservation);
        invPurchaseReturnSave.setInvReservationDetailList(invReservationDetailService.getAllInvReservationDetailsByInvReservationIdWithStatus(invReservation.getId()));

        return invPurchaseReturnSave;
    }

    public void deleteDetails(List<InvReservationDetail> ReservationDetailListDeleted) {
        if (ReservationDetailListDeleted != null && !ReservationDetailListDeleted.isEmpty()) {
            for (InvReservationDetail deleted : ReservationDetailListDeleted) {
                if (deleted.getId() != null) {
                    restorQuantity(deleted);
                    invReservationDetailService.deleteInvReservationDetails(deleted);
                }
            }
        }
    }

    public synchronized void restorQuantity(InvReservationDetail invReservationDetail) {
        InvReservationDetail invReservationDetail1 = invReservationDetailService.findInvReservationDetailById(invReservationDetail.getId());
        invReservationDetail.setFinalQuantity(invReservationDetail.getFinalQuantity().add(invReservationDetail1.getAmount()));
        invReservationDetail.setStatus(restorDetailStatus(invReservationDetail1.getAmount(), invReservationDetail1.getFinalQuantity()));
        invReservationDetailService.updateInvReservationDetails(invReservationDetail1);
        List<Integer> updatedPurchaseOrAddingOrderList = new ArrayList<>();
        updatedPurchaseOrAddingOrderList.add(invReservationDetail1.getReservationId().getId());

    }

    public Integer restorDetailStatus(BigDecimal quantity, BigDecimal finalQuantity) {
        if (quantity.compareTo(finalQuantity) == 0) {
            return 0;
        } else if (finalQuantity.compareTo(BigDecimal.ZERO) == 1 && quantity.compareTo(finalQuantity) == 1) {
            return 1;
        } else {
            return 2;
        }

    }

    public synchronized String updateFinalQuantityAndStatusDetailAndSaveDetail(List<InvReservationDetail> invReservationDetailUpdatedList,
            InvReservation invReservation) {
        InventorySetup invSetup = inventorySetupService.getInventoryByBranchId(invReservation.getBranchId().getId());

        Integer selectedId;
        QuantityItemsStore quantityItemsStore;

        // اذا كنت فى فاتورة مبيعات فالامر بيزيد تعقيد
        // لانه بيشوف هل مسموحله يبيع بكميات سالبة ولا لاء
        for (InvReservationDetail invReservationDetail : invReservationDetailUpdatedList) {
            // لو مسموحلة يبيع بكميات سالبة بيخش يشوف شغلة وينفض للباقى
            if (invSetup.getNegativeSell()) {
                mergeDetailQuantities(invReservationDetail, invReservation);
            } else {
                quantityItemsStore = itemsStoreService.findInventoryItem(invReservation.getInvId().getId(),
                        invReservation.getBranchId().getId(), invReservationDetail.getItemId().getId(), invSetup.getSellBuy());

                if (quantityItemsStore != null && quantityItemsStore.getQty() != null
                        && quantityItemsStore.getQty().compareTo(invReservationDetail.getAmount()) != -1 && !invSetup.getNegativeSell()) {
                    // بيدخل هنا فى حالة لو مش مسموحلة يبيع بكميات سالبة بس بشرط يكون الكمية اكبر من الصفر 
                    if (invReservationDetail.getId() != null) {
                        InvReservationDetail oldvalue = invReservationDetailService.findInvReservationDetailById(invReservationDetail.getId());
                        quantityItemsStore.setQty(quantityItemsStore.getQty().add(oldvalue.getAmount()));
                    }
                    mergeDetailQuantities(invReservationDetail, invReservation);
                } else {
//                    dao.rollbackQuery();
                    return "Quantity Not Avialable";
                }

            }

        }

        return null;
    }

    public void mergeDetailQuantities(InvReservationDetail invReservationDetail, InvReservation invReservation) {

        // فى الاخر بحفظ التفصيلى
        saveDetail(invReservationDetail, invReservation);
    }

    public void saveDetail(InvReservationDetail invReservationDetail, InvReservation invReservation) {
        invReservationDetail.setReservationId(invReservation);
        if (invReservationDetail.getId() != null) {
            invReservationDetailService.updateInvReservationDetails(invReservationDetail);
        } else {
            invReservationDetail.setSerial(getMaxSerialForInvoiceDetail(invReservationDetail.getReservationId()));
            invReservationDetailService.updateInvReservationDetails(invReservationDetail);
        }

//        invPurchaseReturnSave.getInvPurchaseInvoiceDetailList().add(invPurchaseInvoiceDetail);
        updateCostAvarage(invReservationDetail);

    }

    public void updateCostAvarage(InvReservationDetail invReservationDetail) {
        InvItem invItem = invItemService.findInvItem(invReservationDetail.getItemId().getId());
        BigDecimal costAvarage = invReservationDetail.getNet().divide(((invReservationDetail.getAmount()).multiply(invReservationDetail.getScrewing() == null ? BigDecimal.ONE : invReservationDetail.getScrewing())), 4, 4).setScale(2, BigDecimal.ROUND_UP);
        invItem.setCostAverage((costAvarage.add(invItem.getCostAverage() == null ? BigDecimal.ZERO : invItem.getCostAverage())).divide(BigDecimal.valueOf(2)));
        invItemService.updateInvItem(invItem);
    }

    @Override
    public void deleteInvReservation(InvReservation invReservation, Integer invReservationIdToPass) {
        if (invReservationIdToPass != null && invReservationIdToPass > 0) {
            dao.executeDeleteQuery("delete from InvReservationDetail e WHERE e.id > 0 AND e.reservationId.id = " + invReservationIdToPass);
        }
        dao.deleteEntity(invReservation);
    }

    @Override
    public List<InvReservation> getALLInvReservation() {
        return dao.findAll(InvReservation.class);
    }

    @Override
    public InvReservation updateInvReservation(InvReservation invReservation) {
        return dao.updateEntity(invReservation);
    }

    @Override
    public List<InvReservation> getALLInvReservationByCompanyId(Integer Id) {
        return dao.findEntityListByCompanyId(InvReservation.class, Id);
    }

    @Override
    public List<InvReservation> getALLInvReservationByBranchId(Integer Id) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", Id);
        List<InvReservation> invReservationList = dao.findListByQuery("SELECT e FROM InvReservation e WHERE e.branchId.id = :branchId", params);
        return invReservationList;
    }

    @Override
    public List<InvReservation> getALLInvReservationsByBranchIdByStatus(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        List<InvReservation> InvReservations = dao.findListByQuery("SELECT e FROM InvReservation e WHERE e.branchId.id = :branchId and (e.status != 2  OR e.status is null) ", params);
        return InvReservations;
    }

    @Override
    public List<InvReservationDetail> getALLInvReservationDetailsByInvReservation(Integer invReservationId) {
        List<InvReservationDetail> details = invReservationDetailService.getAllInvReservationDetails(invReservationId);
        return details;
    }

    @Override
    public InvReservation findInvReservationByInvReservationId(Integer invReservationId) {
        return dao.findEntityById(InvReservation.class, invReservationId);
    }

    @Override
    public synchronized Integer findMaxSerialNoByBranch(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        Integer serialNo = dao.findEntityByQuery(
                "SELECT MAX(e.serialNo) FROM InvReservation e WHERE e.branchId.id = :branchId", params);
        if (serialNo != null && serialNo != 0) {
            serialNo += 1;
        } else {
            serialNo = 1;
        }
        return serialNo;
    }

    @Override
    public synchronized Integer getMaxSerialForInvoiceDetail(InvReservation invReservation) {
        Map<String, Object> params = new HashMap<>();
        params.put("invReservationId", invReservation.getId());

        Integer serial = dao.findEntityByQuery("SELECT max(e.serial) FROM InvReservationDetail e WHERE e.reservationId.id =:invReservationId ", params);

        if (serial == null) {
            return 1;
        } else {
            serial = serial + 1;
            return serial;
        }
    }

    public InvReservationDTO findInvReservationByInvReservation(Integer invReservationId) {
        InvReservationDTO reservationDTO = new InvReservationDTO();
        InvReservation invReservation = dao.findEntityById(InvReservation.class, invReservationId);
        reservationDTO = mapTODTO(invReservation, true);
        return reservationDTO;
    }

    public InvReservationDTO mapTODTO(InvReservation invReservation, Boolean check) {
        InvReservationDTO dTO = new InvReservationDTO();
        initMapToDTO(dTO, invReservation);
        if (check) {
            dTO.setInvSetup(inventorySetupService.getInventoryByBranchId(invReservation.getBranchId().getId()));
            dTO.setItemsBarcodesDetailsViewList(itemsBarcodesDetailsViewService.findItemsBarcodesDetailsViewBranchId(invReservation.getBranchId().getId()));

            for (ItemsBarcodesDetailsView itemsBarcodesDetailsView : dTO.getItemsBarcodesDetailsViewList()) {
                dTO.getItemsBarcodeMap().put(itemsBarcodesDetailsView.getBarcode(), itemsBarcodesDetailsView);
            }

            List<InvReservationDetailDTO> invReservationDetailDTOList = invReservationDetailService.getAllInvReservationDetailsDTO(dTO);
            dTO.setInvReservationDetailsEntityList(invReservationDetailDTOList);
            updateSummary(dTO);
        }
        return dTO;
    }

    private void initMapToDTO(InvReservationDTO dTO, InvReservation invReservation) {

        dTO.setAddress(invReservation.getAddress());
        dTO.setCreatedBy(invReservation.getCreatedBy() == null ? null : invReservation.getCreatedBy().getId());
        dTO.setCreatedDate(invReservation.getCreationDate());
        dTO.setDelegator(invReservation.getDelegatorId() == null ? null : invReservation.getDelegatorId().getId());
        dTO.setEndDate(invReservation.getEndDate());
        dTO.setId(invReservation.getId());
        dTO.setInventory(invReservation.getInvId() != null ? invReservation.getInvId().getId() : null);
        dTO.setMainSite(invReservation.getSiteIdMain() != null ? invReservation.getSiteIdMain().getId() : null);
        dTO.setRemark(invReservation.getRemarks());
        dTO.setReservationDate(invReservation.getReservationDate());
        dTO.setSite(invReservation.getSiteId() != null ? invReservation.getSiteId().getId() : null);
        dTO.setSerial(invReservation.getSerial());
    }

    public InvReservation mapFromDTO(InvReservationDTO dTO, TobyUser isagUser) {
        InvReservation invReservation = new InvReservation();

        invReservation.setAddress(dTO.getAddress());
        invReservation.setEndDate(dTO.getEndDate());
        invReservation.setId(dTO.getId());
        invReservation.setRemarks(dTO.getRemark());
        invReservation.setReservationDate(dTO.getReservationDate());
        invReservation.setSerial(dTO.getSerial());

        if (dTO.getDelegator() != null) {
            InventoryDelegator delegator = new InventoryDelegator();
            delegator.setId(dTO.getDelegator());
            invReservation.setDelegatorId(delegator);
        }

        if (dTO.getInventory() != null) {
            InvInventory invInventory = new InvInventory();
            invInventory.setId(dTO.getInventory());
            invReservation.setInvId(invInventory);
        }

        if (dTO.getMainSite() != null) {
            InvOrganizationSite invOrganizationSite = new InvOrganizationSite();
            invOrganizationSite.setId(dTO.getMainSite());
            invReservation.setSiteIdMain(invOrganizationSite);
        }

        if (dTO.getSite() != null) {
            InvOrganizationSite invOrganizationSite = new InvOrganizationSite();
            invOrganizationSite.setId(dTO.getSite());
            invReservation.setSiteId(invOrganizationSite);
        }

        invReservation.setBranchId(isagUser.getBranchId());
        invReservation.setCompanyId(isagUser.getCompanyId());
        if (dTO.getId() == null) {
            invReservation.setCreatedBy(isagUser);
            invReservation.setCreationDate(new Date());
        } else {
            invReservation.setId(dTO.getId());
            invReservation.setModificationDate(new Date());
            invReservation.setModifiedBy(isagUser);
            TobyUser user = new TobyUser();
            user.setId(dTO.getCreatedBy());
            invReservation.setCreatedBy(user);
            invReservation.setCreationDate(dTO.getCreatedDate());
        }
        return invReservation;
    }

    private void returnListDTO(List<InvReservation> list, List<InvReservationDTO> dTOList) {
        for (InvReservation invReservation : list) {
            dTOList.add(mapTODTO(invReservation, false));
        }
    }

    public void updateSummary(InvReservationDTO reservationDTO) {

        for (InvReservationDetailDTO reservationDetailDTO : reservationDTO.getInvReservationDetailsEntityList()) {

            reservationDTO.setFinalTotal(BigDecimal.ZERO);

            reservationDTO.getInvReservationDetailMap().put(reservationDetailDTO.getId(), reservationDetailDTO);
            reservationDTO.setTotalQuatity(reservationDTO.getTotalQuatity().add(reservationDetailDTO.getAmount() != null ? reservationDetailDTO.getAmount() : BigDecimal.ZERO));
            reservationDTO.setTotalNet(reservationDTO.getTotalNet().add(reservationDetailDTO.getNet() != null ? reservationDetailDTO.getNet() : BigDecimal.ZERO));

            BigDecimal dbQut = reservationDTO.getItemsMap().get(reservationDetailDTO.getItemsBarcodesDetail().getItemId());
            if (dbQut != null) {
                reservationDTO.getItemsMap().put(reservationDetailDTO.getItemsBarcodesDetail().getItemId(), dbQut.add(reservationDetailDTO.getAmount()));
            } else {
                reservationDTO.getItemsMap().put(reservationDetailDTO.getItemsBarcodesDetail().getItemId(), reservationDetailDTO.getAmount());
            }
        }

    }

}

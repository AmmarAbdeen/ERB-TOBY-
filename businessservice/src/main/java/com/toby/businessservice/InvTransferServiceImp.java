package com.toby.businessservice;

import com.toby.bussinessservice.global.InvPurchaseReturnSave;
import com.toby.core.GenericDAO;
import com.toby.dto.InvInventoryDTO;
import com.toby.dto.InvPurchaseInvoiceDetailDTO;
import com.toby.dto.InvTransferDTO;
import com.toby.dto.InvTransferDetailDTO;
import com.toby.entity.Branch;
import com.toby.entity.InvInventory;
import com.toby.entity.InvTransfer;
import com.toby.entity.InvTransferDetail;
import com.toby.entity.InventorySetup;
import com.toby.entity.TobyCompany;
import com.toby.entity.TobyUser;
import com.toby.views.QuantityItemsStore;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Basic;

/**
 * @author khaled
 */
@Stateless
public class InvTransferServiceImp implements InvTransferService {

    @EJB
    private GenericDAO dao;

    @EJB
    private InvTransferDetailService invTransferDetailService;
    @EJB
    private InventorySetupService inventorySetupService;
    @EJB
    private QuantityItemsStoreService itemsStoreService;
    @EJB
    private InvPurchaseInvoiceDetailsService invPurchaseInvoiceDetailsService;
    @EJB
    QuantityItemsStoreAddExitService quantityItemsStoreAddExitService;

    InvPurchaseReturnSave invPurchaseReturnSave;

    @Override
    public InvPurchaseReturnSave addInvTransfer(InvTransfer invTransfer, List<InvTransferDetail> invTransferDetailDetailList, List<InvTransferDetail> TransferDetailDeleted,
            List<Integer> updatedInvTransferList,
            StringBuilder headIdList, Boolean isTransfer) {
        invPurchaseReturnSave = new InvPurchaseReturnSave();
        if (invTransfer.getId() != null) {
            dao.updateEntity(invTransfer);
        } else {
            invTransfer.setSerialNo(findMaxSerialNoByBranch(invTransfer.getBranchId().getId()));
            dao.saveEntity(invTransfer);
        }

        for (InvTransferDetail deleted : TransferDetailDeleted) {
            invTransferDetailService.deleteInvTransferDetails(deleted);
        }
//        if (!updateFinalQuantityAndStatusDetailAndSaveDetail(invTransferDetailDetailList, isTransfer, invTransfer).isEmpty()) {
//            dao.rollbackQuery();
//        }

        //--------------------
        // لو انا محمل الفاتورة من مرحلة سابقة بعمل هنا تعديل للكميات وحالة كل فاتورة من المحمل منها
        String msg = updateFinalQuantityAndStatusDetailAndSaveDetail(invTransferDetailDetailList, isTransfer, invTransfer);

        if (msg == null || msg.isEmpty()) {
            // لو الدنيا اللى فاتت تمام نروح نمسح من قاعدة البيانات اللى اتمسح من الفاتورة
            deleteDetails(TransferDetailDeleted, isTransfer);
            // هنا بروح اعدل حالة الفاتورة علشان وانا جاي استدعي الفواتير اكون عارف حالتها

        } else {
            invTransfer.setMsg(msg);
            invPurchaseReturnSave.setMsgTransfer(msg);
            dao.rollbackQuery();
        }
        invPurchaseReturnSave.setInvTransfer(invTransfer);
        invPurchaseReturnSave.setInvTransferDetailList(invTransferDetailService.getAllInvTransferDetailsByInvTransferIdWithStatus(invTransfer.getId()));
        return invPurchaseReturnSave;
    }

    //-----------------------
    public List<Integer> findHeadSelectedId(StringBuilder headIdList) {
        String query;

        query = "select DISTINCT h.id FROM InvTransfer h Left join InvTransferDetail d WHERE h.id = d.invTransferId.id and d.id in (" + headIdList.toString() + ") ";
        return dao.findListByQuery(query);

    }
    //------------------------

    public synchronized String updateFinalQuantityAndStatusDetailAndSaveDetail(List<InvTransferDetail> invTransferDetailUpdatedDetailList, Boolean isTransfer, InvTransfer invTransfer) {
        InventorySetup invSetup = inventorySetupService.getInventoryByBranchId(invTransfer.getBranchId().getId());
        QuantityItemsStore quantityItemsStore;

        if (!isTransfer) {
            // بعمل لوب على تفاصيل الفاتورة
            for (InvTransferDetail invTransferDetail : invTransferDetailUpdatedDetailList) {
                // هنا بقي بعدل الكميات وحالة الفواتير من الشاشات المحمل منها الفاتورةر و بحفظ التفاصيل
                mergeDetailQuantities(invTransferDetail, isTransfer, invTransfer);
            }
        } else {
            for (InvTransferDetail invTransferDetail : invTransferDetailUpdatedDetailList) {
                quantityItemsStore = itemsStoreService.findInventoryItem(invTransfer.getInvFrom().getId(),
                        invTransfer.getBranchId().getId(), invTransferDetail.getInvItemId().getId(), invSetup.getSellBuy());
                // لو مسموحلة يبيع بكميات سالبة بيخش يشوف شغلة وينفض للباقى
                if (invSetup.getNegativeSell()) {
                    mergeDetailQuantities(invTransferDetail, isTransfer, invTransfer);
                } else {
                    BigDecimal DBamount = BigDecimal.ZERO;
                    if (invTransferDetail.getId() != null) {
                        InvTransferDetail DBdetail = invTransferDetailService.findInvTransferDetailsById(invTransferDetail.getId());
                        DBamount = DBdetail.getAmount() == null ? BigDecimal.ZERO : DBdetail.getAmount();
                    }
                    quantityItemsStore.setQty(DBamount.add(quantityItemsStore.getQty() == null ? BigDecimal.ZERO : quantityItemsStore.getQty()));

                    if (quantityItemsStore != null && quantityItemsStore.getQty() != null
                            && quantityItemsStore.getQty().compareTo(invTransferDetail.getAmount()) != -1) {
                        // بيدخل هنا فى حالة لو مش مسموحلة يبيع بكميات سالبة بس بشرط يكون الكمية اكبر من الصفر 
                        mergeDetailQuantities(invTransferDetail, isTransfer, invTransfer);
                    } else {
//                    dao.rollbackQuery();
                        return "Quantity Not Avialable";
                    }

                }

            }

        }
        return "";
    }

    public void mergeDetailQuantities(InvTransferDetail invTransferDetail, Boolean isTransfer, InvTransfer invTransfer) {

        if (!isTransfer) {
            InvTransferDetail selectedId = invTransferDetail.getSelectedId();
            if (selectedId != null) {
                Integer transferFrom = invTransferDetail.getTransferFrom();
                // 0 -> محمل من فاتورة امر شراء
                if (0 == transferFrom) {
                    updateSalesInvoiceStatusAndQuantity(invTransferDetail);
                }
            }
            updateFinalAmount(invTransferDetail);
            // فى الاخر بحفظ التفصيلى
            saveDetail(invTransferDetail, isTransfer, invTransfer);
        } else {
            updateFinalAmount(invTransferDetail);
            saveDetail(invTransferDetail, isTransfer, invTransfer);
        }

    }
    //----------------------

    private void updateFinalAmount(InvTransferDetail invTransferDetail) {
        invTransferDetail.setFinalQuantity(invTransferDetail.getAmount());
    }

    public void saveDetail(InvTransferDetail invTransferDetail, Boolean isTransfer, InvTransfer invTransfer) {

        invTransferDetail.setInvTransferId(invTransfer);
        if (invTransferDetail.getId() != null) {
            invTransferDetailService.updateInvTransferDetails(invTransferDetail);
        } else {
            invTransferDetailService.addInvTransferDetails(invTransferDetail);
        }
    }

    //----------------------      
    public void updateSalesInvoiceStatusAndQuantity(InvTransferDetail invTransferDetail) {
        InvTransferDetail detail;

        BigDecimal purchaseInvoiceFinalQuantity;
        BigDecimal finalQuantity;
        Integer res;
        BigDecimal quantityInvoiceOld = null;
        // بجيب السطر اللى هشتغل عليه من جدول امر الشراء
        detail = invTransferDetailService.findInvTransferDetailsById(invTransferDetail.getSelectedId().getId());
//        detail = invTransferDetail;
        // بجيب الكمية المتبقية من الصنف دا علشان اشتغل عليها
        purchaseInvoiceFinalQuantity = invTransferDetail.getFinalQuantity() != null
                ? invTransferDetail.getFinalQuantity() : BigDecimal.ZERO;
        // هنا بقى بجيب الكمية القديمة اللى متسجله فى قاعدة البايات يعنى قبل اما اعدل فيها 
        if (invTransferDetail.getId() != null) {
            InvTransferDetail invAddingorderDetailOld = invTransferDetailService.findInvTransferDetailsById(invTransferDetail.getId());
            quantityInvoiceOld = invAddingorderDetailOld.getAmount();
        }
        finalQuantity = calculateFinalQuantity(invTransferDetail, purchaseInvoiceFinalQuantity, quantityInvoiceOld);

        res = finalQuantity.compareTo(BigDecimal.ZERO);
        if (res == 1) {
            detail.setFinalQuantity(finalQuantity);
            detail.setStatus(1);
        } else {
            detail.setFinalQuantity(BigDecimal.ZERO);
            detail.setStatus(2);
        }
        // هنحفظ التفصيلى
        invTransferDetailService.updateInvTransferDetails(detail);
    }

    public BigDecimal calculateFinalQuantity(InvTransferDetail invTransferDetail,
            BigDecimal existQuantitiyFromDataBaseSource, BigDecimal quantityInvoiceOld) {
        BigDecimal finalQuantity;
        // بتأكد الاول هل السطر دا متعدل ولا لسه جديد
        if (invTransferDetail.getId() == null) {
            // لو كان لسه جديد فكل اللى هعمله انى هطرح الكمية اللى داخلة فى التفصيلى من اللى فى قاعدة البيانات اللى فى جدول امر الشرا
            finalQuantity = existQuantitiyFromDataBaseSource.subtract(invTransferDetail.getAmount());
        } else {
            // اما لو كان تعديل فهطرح الكمية اللى مكتوبه فى التفصيلى من الكمية القديمة اللى فى جدول المشتريات 
            // وبعدين الناتج هطرحه من الكمية اللى موجدة فى جدول امر الشرا
            finalQuantity = existQuantitiyFromDataBaseSource.subtract(invTransferDetail.getAmount().subtract(quantityInvoiceOld));
        }
        return finalQuantity;
    }
//---------------------

    public void deleteDetails(List<InvTransferDetail> invTransferDetailDeleted, Boolean isTransfer) {
        if (invTransferDetailDeleted != null && !invTransferDetailDeleted.isEmpty()) {
            for (InvTransferDetail deleted : invTransferDetailDeleted) {
                if (deleted.getId() != null) {
                    restorQuantity(deleted, isTransfer);
                    invTransferDetailService.deleteInvTransferDetails(deleted);
                }
            }
        }
    }
    //----------------------

    public synchronized void restorQuantity(InvTransferDetail invTransferDetail, Boolean isTransfer) {
        if (!isTransfer) {
            if (invTransferDetail.getSelectedId() != null) {
                if (invTransferDetail.getTransferFrom() == 0) {
//                    InvPurchaseInvoiceDetail dbPurchaseInvoice = invPurchaseInvoiceDetailsService.findInvPurchaseInvoiceDetailsById(invAddingorderDetail.getSelectedId());
                    InvTransferDetail detail = invTransferDetail.getSelectedId();
                    InvTransferDetail detailOld = invTransferDetailService.findInvTransferDetailsById(invTransferDetail.getId());
                    //  dbPurchaseInvoice.setFinalQuantity(dbPurchaseInvoice.getFinalQuantity().add(invTransferDetail.getQuantity()));
                    //    dbPurchaseInvoice.setStatus(restorDetailStatus(dbPurchaseInvoice.getQuantity(), dbPurchaseInvoice.getFinalQuantity()));
                    invTransferDetailService.updateInvTransferDetails(detail);
                    List<Integer> updatedPurchaseOrAddingOrderList = new ArrayList<>();
                    updatedPurchaseOrAddingOrderList.add(detail.getInvTransferId().getId());
                    //   updateHeadStatus(updatedPurchaseOrAddingOrderList);
                }
            }
        }
    }
//----------------------

    @Override
    public void deleteInvTransfer(InvTransfer invTransfer, Integer invTransferIdToPass) {
        if (invTransferIdToPass != null && invTransferIdToPass > 0) {
            dao.executeDeleteQuery("delete from InvTransferDetail e WHERE e.id > 0 AND e.invTransferId.id = " + invTransferIdToPass);
        }
        dao.deleteEntity(invTransfer);
    }

    @Override
    public List<InvTransfer> getALLInvTransfer() {
        return dao.findAll(InvTransfer.class);
    }

    @Override
    public List<InvTransfer> getALLInvTransferByCompanyId(Integer Id) {
        return dao.findEntityListByCompanyId(InvTransfer.class, Id);
    }

    @Override
    public List<InvTransfer> getALLInvTransferByBranchId(Integer Id) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", Id);
        List<InvTransfer> invTransferList = dao.findListByQuery("SELECT e FROM InvTransfer e WHERE e.branchId.id = :branchId", params);
        return invTransferList;
    }

    @Override
    public List<InvTransferDetail> getALLInvTransferDetailsByInvTransfer(Integer invTransferId) {
        List<InvTransferDetail> details = invTransferDetailService.getAllInvTransferDetails(invTransferId);
        return details;
    }

    @Override
    public InvTransferDTO findInvTransferByInvTransferId(Integer invTransferId) {

        return mapToDTO(dao.findEntityById(InvTransfer.class, invTransferId), null);
    }
    @Override
    public InvTransferDTO findInvTransferBySerialNoAndBranch(Integer serial, TobyUser tobyUser){
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", tobyUser.getBranchId().getId());
        params.put("serialNo", serial);
        InvTransfer invTransfer = dao.findEntityByQuery("SELECT e FROM InvTransfer e WHERE e.branchId.id = :branchId AND e.serialNo = :serialNo", params);
        return mapToDTO(invTransfer, tobyUser);
    }

    @Override
    public InvTransfer updateInvTransfer(InvTransfer invTransfer) {
        return dao.updateEntity(invTransfer);
    }

    @Override
    public Integer findMaxSerialNoByBranch(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        Integer serialNo = dao.findEntityByQuery(
                "SELECT MAX(e.serialNo) FROM InvTransfer e WHERE e.branchId.id = :branchId", params);
        if (serialNo != null && serialNo != 0) {
            serialNo += 1;
        } else {
            serialNo = 1;
        }
        return serialNo;
    }

    @Override
    public List<InvTransferDTO> getALLInvTransferByBranchIdAndTransferType(TobyUser tobyUser, Integer transferType) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", tobyUser.getBranchId().getId());
        params.put("transferType", transferType);
        List<InvTransfer> invTransferList = dao.findListByQuery("SELECT e FROM InvTransfer e WHERE e.branchId.id = :branchId AND e.transferType = :transferType", params);
        return mapToDTOList(invTransferList, tobyUser);
    }
    @Override
    public List<Integer> getSerialNoInvTransferDTO(TobyUser tobyUser, Integer transferType) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", tobyUser.getBranchId().getId());
        params.put("transferType", transferType);
        List<Integer> invTransferList = dao.findListByQuery("SELECT e.serialNo FROM InvTransfer e WHERE e.branchId.id = :branchId AND e.transferType = :transferType", params);
        return invTransferList;
    }

    @Override
    public List<InvTransfer> getALLInvTransferByBranchIdAndStatus(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        List<InvTransfer> invTransferList = dao.findListByQuery("SELECT e FROM InvTransfer e WHERE e.branchId.id = :branchId and (e.status != 2  OR e.status is null) and e.transferType = 1 ", params);
        return invTransferList;
    }

    @Override
    public List<InvTransfer> getALLInvTransferByBranchIdAndStatusandType(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        List<InvTransfer> invTransferList = dao.findListByQuery("SELECT e FROM InvTransfer e WHERE e.branchId.id = :branchId and (e.status != 2  OR e.status is null) and e.transferType = 0 ", params);
        return invTransferList;
    }

    @Override
    public InvTransfer addInvTransferMofakroun(InvTransfer invTransfer) {
        return dao.updateEntity(invTransfer);
    }

    public InvTransfer mapToEntity(InvTransferDTO invTransferDTO, TobyUser tobyUser) {
        InvTransfer invTransfer = new InvTransfer();
        invTransfer.setId(invTransferDTO.getId());
        invTransfer.setRemarks(invTransferDTO.getRemarks());
        invTransfer.setTransferDate(invTransferDTO.getTransferDate());
        /// Get Serial ///
        if (invTransferDTO.getId() == null) {
            Map<String, Object> params = new HashMap<>();
            params.put("branchId", tobyUser.getBranchId().getId());
            Integer serialmax = 0;
            synchronized (serialmax) {
                try {
                    serialmax = dao.findEntityByQuery("SELECT MAX(g.serialNo) FROM InvTransfer g  WHERE g.branchId.id =:branchId ", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (serialmax == null) {
                serialmax = 0;
            }
            invTransfer.setSerialNo(serialmax + 1);
        } else {
            invTransfer.setSerialNo(invTransferDTO.getSerialNo());
        }
        if (invTransferDTO.getInvFrom() != null) {
            InvInventory invInventory = new InvInventory();
            invInventory.setId(invTransferDTO.getInvFrom().getId());
            invInventory.setName(invTransferDTO.getInvFrom().getName());
            invInventory.setCode(invTransferDTO.getInvFrom().getCode());
            invTransfer.setInvFrom(invInventory);
        }
        if (invTransferDTO.getInvTo() != null) {
            InvInventory invInventory = new InvInventory();
            invInventory.setId(invTransferDTO.getInvTo().getId());
            invInventory.setName(invTransferDTO.getInvTo().getName());
            invInventory.setCode(invTransferDTO.getInvTo().getCode());
            invTransfer.setInvTo(invInventory);
        }
        if (invTransferDTO.getParent() != null) {
            InvTransfer transfer = new InvTransfer();
            transfer.setId(invTransferDTO.getParent().getId());
            invTransfer.setParent(transfer);
        }
        if (invTransferDTO.getCreatedBy() != null) {
            TobyUser user = new TobyUser();
            user.setId(invTransferDTO.getCreatedBy());
            invTransfer.setCreatedBy(user);
            invTransfer.setCreationDate(invTransferDTO.getCreatedDate());
            invTransfer.setModifiedBy(tobyUser);
            invTransfer.setModificationDate(new Date());
            if (invTransferDTO.getCompanyId() != null) {
                TobyCompany company = new TobyCompany();
                company.setId(invTransferDTO.getCompanyId());
                invTransfer.setCompanyId(company);
            }
            if (invTransferDTO.getBranchId() != null) {
                Branch branch = new Branch();
                branch.setId(invTransferDTO.getBranchId());
                invTransfer.setBranchId(branch);
            }
        } else {
            invTransfer.setCreatedBy(tobyUser);
            invTransfer.setCreationDate(new Date());
            invTransfer.setCompanyId(tobyUser.getCompanyId());
            invTransfer.setBranchId(tobyUser.getBranchId());

        }
        return invTransfer;

    }

    public InvTransferDTO mapToDTO(InvTransfer invTransfer, TobyUser tobyUser) {

        InvTransferDTO invTransferDTO = new InvTransferDTO();

        invTransferDTO.setId(invTransfer.getId());
        invTransferDTO.setRemarks(invTransfer.getRemarks());
        invTransferDTO.setCreatedDate(invTransfer.getCreationDate());
        invTransferDTO.setSerialNo(invTransfer.getSerialNo());

        if (invTransfer.getInvFrom() != null) {
            InvInventoryDTO invInventoryDTO = new InvInventoryDTO();
            invInventoryDTO.setId(invTransfer.getInvFrom().getId());
            invInventoryDTO.setName(invTransfer.getInvFrom().getName());
            invInventoryDTO.setCode(invTransfer.getInvFrom().getCode());
            invTransferDTO.setInvFrom(invInventoryDTO);
        }
        if (invTransfer.getInvTo() != null) {
            InvInventoryDTO invInventoryDTO = new InvInventoryDTO();
            invInventoryDTO.setId(invTransfer.getInvTo().getId());
            invInventoryDTO.setName(invTransfer.getInvTo().getName());
            invInventoryDTO.setCode(invTransfer.getInvTo().getCode());
            invTransferDTO.setInvTo(invInventoryDTO);
        }
        if (invTransfer.getParent() != null) {
            InvTransferDTO transferDTO = new InvTransferDTO();
            transferDTO.setId(invTransfer.getParent().getId());
            invTransferDTO.setParent(transferDTO);
        }

        invTransferDTO.setCreatedBy(invTransfer.getCreatedBy() != null ? invTransfer.getCreatedBy().getId() : null);
        invTransferDTO.setCreatedDate(invTransfer.getCreationDate());
        invTransferDTO.setBranchId(invTransfer.getBranchId().getId() != null ? invTransfer.getBranchId().getId() : null);
        invTransferDTO.setCompanyId(invTransfer.getCompanyId() != null ? invTransfer.getCompanyId().getId() : null);

        return invTransferDTO;
    }

    public List<InvTransferDTO> mapToDTOList(List<InvTransfer> invTransferList, TobyUser tobyUser) {
        List<InvTransferDTO> invTransferDTOList = new ArrayList<>();
        for (InvTransfer invTransfer : invTransferList) {
            invTransferDTOList.add(mapToDTO(invTransfer, tobyUser));
        }
        return invTransferDTOList;
    }

    public InvTransferDTO addInvTransferDTO(InvTransferDTO invTransferDTO, TobyUser tobyUser) {
        BigDecimal total;
        for (InvTransferDetailDTO detailDTO : invTransferDTO.getInvTransferDetailDTOList()) {
            Map<Integer, BigDecimal> avialableQuantityMap = quantityItemsStoreAddExitService.findInventoryDTOList(tobyUser, invTransferDTO.getInvFrom().getId());
            total = detailDTO.getAmount().multiply(detailDTO.getUnitsItem().getScrewing() != null ? detailDTO.getUnitsItem().getScrewing() : BigDecimal.ONE);
            if (total.compareTo(avialableQuantityMap.get(detailDTO.getInvItemId().getId())) > 0) {
                invTransferDTO.setMsg("كمية الصنف اكبر  من الكمية الموجودة فى المخزن");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, invTransferDTO.getMsg(), detailDTO.getInvItemId().toString()));
                return invTransferDTO;
            }
        }
        InvTransfer invTransfer = mapToEntity(invTransferDTO, tobyUser);
        invTransfer = dao.updateEntity(invTransfer);
        InvTransferDTO invTransferDTO1 = new InvTransferDTO();
        invTransferDTO1.setInvTransferDetailDTOList(invTransferDetailService.addInvTransferDetailsDTO(invTransferDTO.getInvTransferDetailDTOList(), invTransfer.getId(), tobyUser));
        invTransferDTO1 = mapToDTO(invTransfer, tobyUser);
        invTransferDTO1.setInvTransferDetailDTOList(invTransferDTO.getInvTransferDetailDTOList());
        return invTransferDTO1;
    }

    @Override
    public InvTransferDetailDTO addRow(InvTransferDTO invTransferDTO) {
        InvTransferDetailDTO invTransferDetailDTO = new InvTransferDetailDTO();

        if (invTransferDTO != null && invTransferDTO.getInvFrom() != null && invTransferDTO.getInvFrom().getId() != null
                && invTransferDTO.getInvTo() != null && invTransferDTO.getInvTo().getId() != null) {
            if (validateChangeInventory(invTransferDTO) && validateDetailsWhenAddRow(invTransferDTO)) {

                if (invTransferDTO.getInvTransferDetailDTOList() != null) {
                    invTransferDTO.getInvTransferDetailDTOList().add(invTransferDetailDTO);
                }
                return invTransferDetailDTO;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "يجب اختيار من مستودع و إلى مستودع", null));
        }
        return invTransferDetailDTO;
    }

    public boolean validateDetailsWhenAddRow(InvTransferDTO invTransferDTO) {
        boolean bool = true;
        for (InvTransferDetailDTO invTransferDetailDTO : invTransferDTO.getInvTransferDetailDTOList()) {
            if (invTransferDetailDTO.getInvItemId() == null || invTransferDetailDTO.getUnitsItemsSelected() == null || invTransferDetailDTO.getAmount() == null) {
                bool = false;
                invTransferDetailDTO.setMsg(" الصنف - الكمية - الوحدة -  يجب وضع قيمة ل");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invTransferDetailDTO.getMsg()));
            }
        }

        return bool;
    }

    public boolean validateChangeInventory(InvTransferDTO invTransferDTO) {
        if (invTransferDTO.getInvFrom() != null && invTransferDTO.getInvFrom().getId() != null
                && invTransferDTO.getInvTo() != null && invTransferDTO.getInvTo().getId() != null) {

            if (invTransferDTO.getInvFrom().getId().compareTo(invTransferDTO.getInvTo().getId()) == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "يجب عدم اختيار نفس المخزن   اختر مخزن اخر", null));
                invTransferDTO.setInvTo(null);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean validateDetailsWhenSave(InvTransferDTO invTransferDTO) {
        boolean bool = true;
        if (invTransferDTO.getInvTransferDetailDTOList() != null && !invTransferDTO.getInvTransferDetailDTOList().isEmpty()) {
            for (InvTransferDetailDTO invTransferDetailDTO : invTransferDTO.getInvTransferDetailDTOList()) {
                if (invTransferDetailDTO.getInvItemId() == null || invTransferDetailDTO.getUnitsItemsSelected() == null || invTransferDetailDTO.getAmount() == null) {
                    bool = false;
                    invTransferDetailDTO.setMsg(" الصنف - الكمية - الوحدة -  يجب وضع قيمة ل");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invTransferDetailDTO.getMsg()));
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "يجب اضافة تفاصيل", null));
        }

        return bool;
    }

    @Override
    public boolean ValidateQuantity(InvTransferDTO invTransferDTO, InvTransferDetailDTO invTransferDetailDTOSelected, Map<Integer, BigDecimal> avialableQuantityMap) {
        boolean b = false;
        if (invTransferDTO != null && invTransferDTO.getInvFrom() != null && invTransferDTO.getInvFrom().getId() != null && invTransferDetailDTOSelected != null && invTransferDetailDTOSelected.getInvItemId() != null
                && invTransferDetailDTOSelected.getInvItemId().getId() != null && invTransferDetailDTOSelected.getAmount() != null
                && invTransferDetailDTOSelected.getUnitsItem() != null) {

            BigDecimal totalQuantiy = invTransferDetailDTOSelected.getAmount().multiply(invTransferDetailDTOSelected.getUnitsItem().getScrewing() != null ? invTransferDetailDTOSelected.getUnitsItem().getScrewing() : BigDecimal.ONE);
            if (totalQuantiy.compareTo(avialableQuantityMap.get(invTransferDetailDTOSelected.getInvItemId().getId())) > 0) {
                b = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "الكمية اكبر من الكمية الموجودة فى المخزن", null));

            } else {
                b = true;
            }

        }
        return b;
    }

    @Override
    public InvTransferDTO save(InvTransferDTO invTransferDTO, TobyUser tobyUser) {
        try {

            if (validateTransfer(invTransferDTO) && validateDetailsWhenSave(invTransferDTO)) {
                invTransferDTO = addInvTransferDTO(invTransferDTO, tobyUser);
                if (invTransferDTO.getId() != null) {
                    invTransferDTO.setMsg(" تم الحفظ بنجاح");
                }
                if (invTransferDTO.getMsg() != null && !invTransferDTO.getMsg().isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error!", invTransferDTO.getMsg()));

                }
            }

        } catch (Exception e) {
            return null;
        }
        return invTransferDTO;
    }

    public boolean validateTransfer(InvTransferDTO invTransferDTO) {
        //save تبع
        try {

            if (invTransferDTO.getTransferDate() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "يجب اختيار تاريخ"));
                return false;
            }

            if (invTransferDTO.getInvFrom() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "يجب اختيار من مستودع"));
                return false;
            }

            if (invTransferDTO.getInvTo() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "يجب اختيار الى مستودع"));
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return validateInvFrom(invTransferDTO);
    }

    private boolean validateInvFrom(InvTransferDTO invTransferDTO) {
        try {

            if (invTransferDTO.getInvFrom() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "يجب اختيار من مستودع"));
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}

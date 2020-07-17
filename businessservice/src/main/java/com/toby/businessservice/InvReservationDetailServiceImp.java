package com.toby.businessservice;

import com.toby.core.GenericDAO;
import com.toby.dto.InvReservationDTO;
import com.toby.dto.InvReservationDetailDTO;
import com.toby.entity.InvItem;
import com.toby.entity.InvReservationDetail;
import com.toby.entity.InventorySetup;
import com.toby.entity.TobyUser;
import com.toby.views.ItemsBarcodesDetailsView;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author khaled
 */
@Stateless
public class InvReservationDetailServiceImp implements InvReservationDetailService {

    @EJB
    private GenericDAO dao;

    @Override
    public void addInvReservationDetails(InvReservationDetail details) {
        dao.saveEntity(details);
    }

    @Override
    public void deleteInvReservationDetails(InvReservationDetail details) {
        dao.deleteEntity(details);
    }

    @Override
    public InvReservationDetail updateInvReservationDetails(InvReservationDetail details) {
        return dao.updateEntity(details);
    }

    @Override
    public List<InvReservationDetail> getAllInvReservationDetails() {
        return dao.findAll(InvReservationDetail.class);
    }

    @Override
    public List<InvReservationDetail> getAllInvReservationDetails(Integer ReservationId) {
        Map<String, Object> params = new HashMap();
        params.put("ReservationId", ReservationId);
        return dao.findListByQuery("SELECT d FROM InvReservationDetail d WHERE d.reservationId.id =:ReservationId", params);
    }

    @Override
    public InvReservationDetail findInvReservationDetailById(Integer invReservationDetailId) {
        return dao.findEntityById(InvReservationDetail.class, invReservationDetailId);
    }

    @Override
    public List<InvReservationDetail> getAllInvReservationDetailsByInvReservationIdWithStatus(Integer invReservationId) {
        Map<String, Object> params = new HashMap<>();
        params.put("invReservationId", invReservationId);
        List<InvReservationDetail> details = dao.findListByQuery("SELECT e FROM InvReservationDetail e WHERE e.reservationId.id = :invReservationId and (e.status != 2  OR e.status is null) ", params);
        return details;
    }

    @Override
    public List<InvReservationDetailDTO> getAllInvReservationDetailsDTO(InvReservationDTO invReservationDTO) {
        Map<String, Object> params = new HashMap();
        List<InvReservationDetailDTO> reservationDetailDTOList = new ArrayList<>();
        params.put("ReservationId", invReservationDTO.getId());
        List<InvReservationDetail> invReservationDetailList = dao.findListByQuery("SELECT d FROM InvReservationDetail d WHERE d.reservationId.id =:ReservationId", params);
        returnListDTO(invReservationDetailList,reservationDetailDTOList,invReservationDTO.getItemsBarcodeMap());
        return reservationDetailDTOList;
    }

    public InvReservationDetailDTO mapTODTO(InvReservationDetail invReservationDetail, Map<String, ItemsBarcodesDetailsView> itemsBarcodeMap) {
        InvReservationDetailDTO dTO = new InvReservationDetailDTO();
        initMapToDTO(dTO, invReservationDetail, itemsBarcodeMap);
        return dTO;
    }

    private void initMapToDTO(InvReservationDetailDTO dTO, InvReservationDetail invReservationDetail, Map<String, ItemsBarcodesDetailsView> itemsBarcodeMap) {

        dTO.setCreatedBy(invReservationDetail.getCreatedBy() == null ? null : invReservationDetail.getCreatedBy().getId());
        dTO.setCreatedDate(invReservationDetail.getCreationDate());
        dTO.setId(invReservationDetail.getId());
        dTO.setSerial(invReservationDetail.getSerial());

        if (invReservationDetail.getItemBarcode() != null && !"".equals(invReservationDetail.getItemBarcode()) && itemsBarcodeMap != null && !itemsBarcodeMap.isEmpty()) {
            dTO.setItemsBarcodesDetail(itemsBarcodeMap.get(invReservationDetail.getItemBarcode()));
            dTO.setItemsBarcodesDetailTrans(dTO.getItemsBarcodesDetail());
            dTO.setScrewing(dTO.getItemsBarcodesDetail().getScrewing());
        }

        if (invReservationDetail.getItemId() != null) {
            dTO.setItem(invReservationDetail.getItemId().getId());
            dTO.setItemCode(invReservationDetail.getItemId().getCode());
            dTO.setItemName(invReservationDetail.getItemId().getName());
            dTO.setUnit(invReservationDetail.getItemId().getUnitId() != null ? invReservationDetail.getItemId().getUnitId().getName() : null);
        }

        dTO.setAmount(invReservationDetail.getAmount() != null ? invReservationDetail.getAmount() : BigDecimal.ZERO);
        dTO.setPrice(invReservationDetail.getPrice() != null ? invReservationDetail.getPrice() : BigDecimal.ZERO);
        dTO.setDiscount(invReservationDetail.getDiscount() != null ? invReservationDetail.getDiscount() : BigDecimal.ZERO);
        dTO.setTotal(invReservationDetail.getTotal() != null ? invReservationDetail.getTotal() : BigDecimal.ZERO);
        dTO.setAdding(invReservationDetail.getAdding() != null ? invReservationDetail.getAdding() : BigDecimal.ZERO);
        dTO.setNet(invReservationDetail.getNet() != null ? invReservationDetail.getNet() : BigDecimal.ZERO);

    }

    public InvReservationDetail mapFromDTO(InvReservationDTO invReservationDTO,InvReservationDetailDTO dTO, TobyUser isagUser, InventorySetup invSetup) {
        InvReservationDetail invReservationDetail = new InvReservationDetail();

        invReservationDetail.setId(dTO.getId());
        invReservationDetail.setSerial(dTO.getSerial());

        if (dTO.getDiscount() != null) {
            if (isDiscountValid(dTO)) {
                invReservationDetail.setDiscount(dTO.getDiscount());
            } else {
                return invReservationDetail;
            }
        }

        if (dTO.getItemsBarcodesDetail() == null
                && dTO.getItemsBarcodesDetailTrans() == null) {
            dTO.setMsg("يجب ادخال الصنف");
            return invReservationDetail;
        } else {
            InvItem item = new InvItem();
            item.setId(dTO.getItemsBarcodesDetailTrans().getItemId());
            invReservationDetail.setItemId(item);
            invReservationDetail.setItemBarcode(dTO.getItemsBarcodesDetailTrans().getBarcode());
            invReservationDetail.setScrewing(dTO.getItemsBarcodesDetailTrans().getScrewing());
            dTO.setItemsBarcodesDetail(dTO.getItemsBarcodesDetailTrans());
        }

        if (validatePrice(dTO)) {
            invReservationDetail.setPrice(dTO.getPrice().setScale(2, BigDecimal.ROUND_UP));
        } else {
            dTO.setMsg("يجب ادخال تفاصيل الفاتورة");
            return invReservationDetail;
        }

        if (validateQuantity(dTO,invReservationDTO)) {
            invReservationDetail.setAmount(dTO.getAmount());
        } else {
            dTO.setMsg("يجب ادخال تفاصيل الفاتورة");
            return invReservationDetail;
        }

        if (dTO.getAdding() != null && dTO.getAdding().compareTo(BigDecimal.ZERO) == 1) {
            if (validateAdding(dTO)) {
                invReservationDetail.setAdding(dTO.getAdding());
            } else {
                dTO.setMsg("يجب ادخال تفاصيل الفاتورة");
                return invReservationDetail;
            }
        }

        invReservationDetail.setNet(dTO.getNet());

        invReservationDetail.setBranchId(isagUser.getBranchId());
        invReservationDetail.setCompanyId(isagUser.getCompanyId());
        if (dTO.getId() == null) {
            invReservationDetail.setCreatedBy(isagUser);
            invReservationDetail.setCreationDate(new Date());
            invReservationDetail.setStatus(0);
        } else {
            invReservationDetail.setId(dTO.getId());
            invReservationDetail.setModificationDate(new Date());
            invReservationDetail.setModifiedBy(isagUser);
            TobyUser user = new TobyUser();
            user.setId(dTO.getCreatedBy());
            invReservationDetail.setCreatedBy(user);
            invReservationDetail.setCreationDate(dTO.getCreatedDate());
        }
        return invReservationDetail;
    }

    private Boolean isDiscountValid(InvReservationDetailDTO discValue) {
        BigDecimal hundred = new BigDecimal(100);
        if (discValue != null) {
            if ((discValue.getDiscount().compareTo(BigDecimal.ZERO) == 0 || discValue.getDiscount().compareTo(BigDecimal.ZERO) == 1)
                    && (discValue.getDiscount().compareTo(hundred) == 0 || hundred.compareTo(discValue.getDiscount()) == 1)) {
                return true;
            } else {
                discValue.setMsg("لا يجب ادخال خصم اعلى من 100% في حالة النسبة");
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean validateAdding(InvReservationDetailDTO invReservationTable) {
        return invReservationTable != null && invReservationTable.getItemsBarcodesDetail() != null
                && invReservationTable.getAdding() != null
                && invReservationTable.getAdding().compareTo(BigDecimal.ZERO) == 1;
    }

    public Boolean validateUnit(InvReservationDetailDTO invReservationTable) {
        return invReservationTable != null && invReservationTable.getItemsBarcodesDetail() != null
                && invReservationTable.getItemsBarcodesDetail().getUnitId() != null
                && invReservationTable.getUnit() != null;
    }

    public Boolean validatePrice(InvReservationDetailDTO invReservationTable) {
        return invReservationTable != null
                && invReservationTable.getItemsBarcodesDetailTrans() != null
                && invReservationTable.getPrice() != null
                && invReservationTable.getPrice().compareTo(BigDecimal.ZERO) == 1;
    }

    public Boolean validateDiscount(InvReservationDetailDTO invReservationTable) {
        return isDiscountValid(invReservationTable);
    }

    public Boolean validateQuantity(InvReservationDetailDTO invReservationTable, InvReservationDTO invReservationDTO) {
        if (invReservationTable != null && invReservationTable.getItemsBarcodesDetailTrans() != null
                && invReservationTable.getItemsBarcodesDetailTrans().getItemId() != null) {

            if (invReservationTable.getAmount() != null
                    && invReservationTable.getAmount().compareTo(BigDecimal.ZERO) == 1) {
                if (invReservationDTO.getInvSetup().getNegativeSell() == false) {
                    if (!invReservationDTO.getItemsMap().isEmpty()) {
                        BigDecimal dbQut = invReservationDTO.getItemsMap().get(invReservationTable.getItemsBarcodesDetail().getItemId());
                        if (dbQut != null) {
                            BigDecimal usrQut = sumItemQuantity(invReservationTable.getItemsBarcodesDetail().getItemId(),invReservationDTO.getInvReservationDetailsEntityList());
                            return dbQut.compareTo(BigDecimal.ZERO) == 1 && dbQut.compareTo(usrQut) != -1;
                        } else {
                            return false;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                invReservationTable.setMsg("يجب ادخال كمية");
                return false;
            }
        } else {
            invReservationTable.setMsg("يجب اختيار صنف");
            return false;
        }
        return false;
    }

    BigDecimal sumItemQuantity(Integer itemId,List<InvReservationDetailDTO> invReservationTable) {

        BigDecimal detailQuant, detailScrewing, quant = BigDecimal.ZERO;
        for ( InvReservationDetailDTO detailsEntity : invReservationTable) {
            if (detailsEntity.getItemsBarcodesDetail() != null && detailsEntity.getItemsBarcodesDetail().getItemId() != null
                    && Objects.equals(itemId, detailsEntity.getItemsBarcodesDetail().getItemId())
                    && detailsEntity.getAmount() != null && detailsEntity.getAmount().compareTo(BigDecimal.ZERO) == 1) {

                detailQuant = detailsEntity.getAmount();
                detailScrewing = detailsEntity.getScrewing() != null ? detailsEntity.getScrewing() : BigDecimal.ONE;

                quant = quant.add(detailQuant.multiply(detailScrewing));
            }
        }
        return quant;
    }

    private void returnListDTO(List<InvReservationDetail> list, List<InvReservationDetailDTO> dTOList, Map<String, ItemsBarcodesDetailsView> itemsBarcodeMap) {
        for (InvReservationDetail invReservationDetail : list) {
            dTOList.add(mapTODTO(invReservationDetail, itemsBarcodeMap));
        }
    }
}

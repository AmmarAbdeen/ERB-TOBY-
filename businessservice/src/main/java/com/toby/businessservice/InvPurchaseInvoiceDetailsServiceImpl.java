/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.common.Validation;
import com.toby.core.GenericDAO;
import com.toby.define.CompanyActivityClassEnum;
import com.toby.dto.InvDetailDTO;
import com.toby.dto.InvInventoryDTO;
import com.toby.dto.InvItemDTO;
import com.toby.dto.InvPurchaseInvoiceDTO;
import com.toby.dto.SymbolDTO;
import com.toby.entity.GlYear;
import com.toby.entity.InvInventory;
import com.toby.entity.InvItem;
import com.toby.entity.InvPurchaseInvoice;
import com.toby.entity.InvPurchaseInvoiceDetail;
import com.toby.entity.InventorySetup;
import com.toby.entity.Symbol;
import com.toby.entity.TobyUser;
import com.toby.entity.UnitsItems;
import com.toby.views.ItemsBarcodesDetailsView;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author WIN7
 */
@Stateless
public class InvPurchaseInvoiceDetailsServiceImpl implements InvPurchaseInvoiceDetailsService {

    @EJB
    private GenericDAO dao;
    @EJB
    private UnitsItemsService unitsItemsService;
    @EJB
    private InventorySetupService inventorySetupService;
    @EJB
    InvPurchaseInvoiceService invPurchaseInvoiceService;
    @EJB
    private QuantityItemsStoreAddExitService quantityItemsStoreAddExitService;
    @EJB
    ItemsBarcodesDetailsViewService itemsBarcodesDetailsViewService;

    @Override
    public List<InvPurchaseInvoiceDetail> getAllInvPurchaseInvoiceDetailByBranchId(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        List<InvPurchaseInvoiceDetail> detailses = dao.findListByQuery("SELECT e FROM InvPurchaseInvoiceDetail e WHERE e.branchId.id = :branchId", params);
        return detailses;
    }

    @Override
    public void reCalcCostAvarage(Integer branchId) {
        InventorySetup invSetup = inventorySetupService.getInventoryByBranchId(branchId);
        List<InvPurchaseInvoiceDetail> invPurchaseInvoiceDetailList = getAllInvPurchaseInvoiceDetailByBranchId(branchId);
        for (InvPurchaseInvoiceDetail detail : invPurchaseInvoiceDetailList) {
            BigDecimal quantityItemsStoreByDate = invPurchaseInvoiceService.findQuantityItemByDate(detail.getInvPurchaseInvoiceId(), detail);
            invPurchaseInvoiceService.saveDetail(detail, detail.getInvPurchaseInvoiceId(), !detail.getInvPurchaseInvoiceId().getType(), quantityItemsStoreByDate, invSetup);
        }

    }

    @Override
    public List<InvPurchaseInvoiceDetail> getAllInvPurchaseInvoiceDetailByBranchIdByDate(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        List<InvPurchaseInvoiceDetail> detailses = dao.findListByQuery("SELECT e FROM InvPurchaseInvoiceDetail e WHERE e.branchId.id = :branchId order e.invPurchaseInvoiceId.date ", params);
        return detailses;
    }

    @Override
    public void addInvPurchaseInvoiceDetails(InvPurchaseInvoiceDetail invPurchaseInvoiceDetail) {
        dao.saveEntity(invPurchaseInvoiceDetail);
    }

    @Override
    public void deleteInvPurchaseInvoiceDetails(InvPurchaseInvoiceDetail invPurchaseInvoiceDetail) {
        dao.deleteEntity(invPurchaseInvoiceDetail);
    }

    @Override
    public InvPurchaseInvoiceDetail updateInvPurchaseInvoiceDetails(InvPurchaseInvoiceDetail invPurchaseInvoiceDetail) {
        return dao.updateEntity(invPurchaseInvoiceDetail);
    }

    @Override
    public List<InvPurchaseInvoiceDetail> getAllInvPurchaseInvoiceDetailsByInvPurchaseInvoiceId(Integer invPurchaseInvoiceId) {
        Map<String, Object> params = new HashMap<>();
        params.put("invPurchaseInvoiceId", invPurchaseInvoiceId);
        List<InvPurchaseInvoiceDetail> details = dao.findListByQuery("SELECT e FROM InvPurchaseInvoiceDetail e WHERE e.invPurchaseInvoiceId.id = :invPurchaseInvoiceId", params);
        return details;
    }

    @Override
    public List<InvPurchaseInvoiceDetail> getAllInvPurchaseInvoiceDetailsByInvPurchaseInvoiceIdWithStatus(Integer invPurchaseInvoiceId) {
        Map<String, Object> params = new HashMap<>();
        params.put("invPurchaseInvoiceId", invPurchaseInvoiceId);
        List<InvPurchaseInvoiceDetail> details = dao.findListByQuery("SELECT e FROM InvPurchaseInvoiceDetail e WHERE e.invPurchaseInvoiceId.id = :invPurchaseInvoiceId AND (e.status != 2 OR e.status is null)", params);
        return details;
    }

    @Override
    public InvPurchaseInvoiceDetail findInvPurchaseInvoiceDetailsById(Integer invPurchaseInvoiceDetailsId) {
        return dao.findEntityById(InvPurchaseInvoiceDetail.class, invPurchaseInvoiceDetailsId);
    }

    @Override
    public BigDecimal getLastCostItem(Integer itemId, GlYear glYear, Integer branchId) {
        List<BigDecimal> purchaseInvoiceDetails = new ArrayList<>();
        Map<String, Object> params = new HashMap();
        params.put("itemId", itemId);
        params.put("branchId", branchId);
        params.put("dateFrom", glYear.getDateFrom());
        params.put("dateTo", glYear.getDateTo());
        purchaseInvoiceDetails = dao.findListByQuery("SELECT d.price FROM InvPurchaseOrder ipo Left join InvPurchaseOrderDetail d  WHERE ipo.branchId.id = :branchId AND d.itemId.id = :itemId AND ipo.date >= :dateFrom AND ipo.date <= :dateTo order by ipo.date desc", params);
        if (purchaseInvoiceDetails != null && !purchaseInvoiceDetails.isEmpty()) {
            return purchaseInvoiceDetails.get(0);
        }

        return BigDecimal.ZERO;
    }

    public void updateCostAvarage(Integer branchId) {
        List<InvPurchaseInvoiceDetail> purchaseInvoiceDetails = new ArrayList<>();
        Map<String, Object> params = new HashMap();
        params.put("branchId", branchId);
        params.put("type", true);

        purchaseInvoiceDetails = dao.findListByQuery("SELECT ipid FROM InvPurchaseInvoiceDetail ipid Left join InvPurchaseInvoice ipi  WHERE ipi.branchId.id = :branchId AND ipi.id = ipid.invPurchaseInvoiceId.id AND ipi.type = :type", params);

        for (InvPurchaseInvoiceDetail detail : purchaseInvoiceDetails) {
            if (detail.getItemId() != null) {
                List<BigDecimal> costAvarage = dao.executeNativeQuery("select avarage_cost from update_cost_avarage  where item_id = " + detail.getItemId().getId());
                if (costAvarage != null && !costAvarage.isEmpty()) {
                    detail.setCostAvarage(costAvarage.get(0));
                    dao.updateEntity(detail);
                }

            }
        }

    }

    @Override
    public List<InvDetailDTO> getAllInvPurchaseInvoiceDetailsByInvPurchaseInvoiceIdDTO(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO, Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("invPurchaseInvoiceId", invPurchaseInvoiceDTO.getId());
        List<InvPurchaseInvoiceDetail> details = dao.findListByQuery("SELECT e FROM InvPurchaseInvoiceDetail e WHERE e.invPurchaseInvoiceId.id = :invPurchaseInvoiceId", params);
        return returnListDTO(details, branchId, invPurchaseInvoiceDTO);
    }

    private List<InvDetailDTO> returnListDTO(List<InvPurchaseInvoiceDetail> list, Integer branchId, InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        List<InvDetailDTO> dTOList = new ArrayList<>();
        List<ItemsBarcodesDetailsView> itemsBarcodesDetailsViewList = itemsBarcodesDetailsViewService.findItemsBarcodesDetailsViewBranchId(branchId);
        Map<String, ItemsBarcodesDetailsView> itemsBarcodeMap = new HashMap<>();
        for (ItemsBarcodesDetailsView itemsBarcodesDetailsView : itemsBarcodesDetailsViewList) {
            itemsBarcodeMap.put(itemsBarcodesDetailsView.getBarcode(), itemsBarcodesDetailsView);
        }
        for (InvPurchaseInvoiceDetail entity : list) {
            dTOList.add(mapTODTO(entity, itemsBarcodeMap, invPurchaseInvoiceDTO));
        }
        return dTOList;
    }

    private InvDetailDTO mapTODTO(InvPurchaseInvoiceDetail entity, Map<String, ItemsBarcodesDetailsView> itemsBarcodeMap, InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        InvDetailDTO dTO = initMapToDTO(entity, itemsBarcodeMap, invPurchaseInvoiceDTO);
        return dTO;
    }

    private InvDetailDTO initMapToDTO(InvPurchaseInvoiceDetail entity, Map<String, ItemsBarcodesDetailsView> itemsBarcodeMap, InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {

        InvDetailDTO dTO = new InvDetailDTO();
        dTO.setId(entity.getId());
        dTO.setQuantity(entity.getQuantity());
        dTO.setDiscount(entity.getDiscount());
        dTO.setBounse(entity.getBounse());
        dTO.setNet(entity.getNet());

        if (entity.getInvInventoryId() != null) {
            InvInventoryDTO invInventoryDTO = new InvInventoryDTO();
            invInventoryDTO.setId(entity.getInvInventoryId().getId());
            invInventoryDTO.setName(entity.getInvInventoryId().getName());
            invInventoryDTO.setCode(entity.getInvInventoryId().getCode());
            dTO.setInventoryDTO(invInventoryDTO);
        }

        dTO.setNumber(entity.getNumber());
        dTO.setClothNumber(entity.getClothNumber());
        dTO.setSerial(entity.getSerial());
        if (entity.getInvItemParentId() != null) {
            InvItemDTO invItemDTO = new InvItemDTO();
            invItemDTO.setId(entity.getInvItemParentId().getId());
            invItemDTO.setName(entity.getInvItemParentId().getName());
            invItemDTO.setCode(entity.getInvItemParentId().getCode());
            dTO.setInvItemParentId(invItemDTO);
        }

        dTO.setBranchId(entity.getBranchId() != null ? entity.getBranchId().getId() : null);
        dTO.setCost(entity.getCost() != null ? entity.getCost().setScale(3, BigDecimal.ROUND_UP) : BigDecimal.ZERO);
        dTO.setDiscount(entity.getDiscount() != null ? entity.getDiscount().setScale(3, BigDecimal.ROUND_UP) : BigDecimal.ZERO);
        dTO.setQuantity(entity.getQuantity() != null ? entity.getQuantity().setScale(3, BigDecimal.ROUND_UP) : BigDecimal.ZERO);

        dTO.setDiscountType(entity.getDiscountType());
        dTO.setInvPurchaseInvoiceId(entity.getInvPurchaseInvoiceId() != null ? entity.getInvPurchaseInvoiceId().getId() : null);

//        if (entity.getItemBarcode() != null && !"".equals(entity.getItemBarcode())) {
//            ItemsBarcodesDetailsView barcodesDetailsView = itemsBarcodeMap.get(entity.getItemBarcode());
//            dTO.setItemsBarcodesDetail(itemsBarcodeMap.get(entity.getItemBarcode()));
//            dTO.setItemsBarcodesDetailTrans(dTO.getItemsBarcodesDetail());
//            dTO.setScrewing(entity.getScrewing());
//            if (entity.getItemId() != null && barcodesDetailsView != null && barcodesDetailsView.getPaintColor() != null) {
//                SymbolDTO paintColor = new SymbolDTO();
//                paintColor.setId(barcodesDetailsView.getPaintColor());
//                paintColor.setName(barcodesDetailsView.getPaintColorName());
//                dTO.setPaintColor(paintColor);
//            }
//            dTO.setLength(entity.getItemId().getLength());
//        }
        dTO.setWeight(entity.getWeight() != null ? entity.getWeight() : BigDecimal.ZERO);
        if (entity.getUnitId() != null) {
            SymbolDTO unitDTO = new SymbolDTO();
            unitDTO.setId(entity.getUnitId().getId());
            unitDTO.setName(entity.getUnitId().getName());
            unitDTO.setSerial(entity.getUnitId().getSerial());
            dTO.setUnitId(unitDTO);
        }
        if (entity.getItemId() != null) {
            InvItemDTO invItemDTO = new InvItemDTO();
            invItemDTO.setId(entity.getItemId().getId());
            invItemDTO.setName(entity.getItemId().getName());
            invItemDTO.setCode(entity.getItemId().getCode());
            dTO.setItemId(invItemDTO);
        }

        dTO.setExtraCost(entity.getExtraCost());

        dTO.setNet(entity.getNet());

        dTO.setIndex(entity.getId());
        dTO.setScrewing(entity.getScrewing());
        dTO.setCreatedDate(entity.getCreationDate());
        dTO.setCreatedBy(entity.getCreatedBy().getId());
        dTO = calculateDetailTotalsRow(dTO, invPurchaseInvoiceDTO);

        return dTO;
    }

    @Override
    public InvDetailDTO calculateDetailTotalsRow(InvDetailDTO detailEntity, InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        BigDecimal quantity;
        BigDecimal weight;
        BigDecimal cost;
        BigDecimal discount;
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal valueAfterDiscount;
//        if (detailEntity.getItemsBarcodesDetail() == null && detailEntity.getItemsBarcodesDetailTrans() != null) {
//            detailEntity.setItemsBarcodesDetail(detailEntity.getItemsBarcodesDetailTrans());
//        }

        if (detailEntity.getItemId() == null && detailEntity.getItemIdBak() != null) {
            detailEntity.setItemId(detailEntity.getItemIdBak());
        }

        quantity = detailEntity.getQuantity();
        weight = detailEntity.getWeight();
        cost = detailEntity.getCost() != null ? detailEntity.getCost().multiply(invPurchaseInvoiceDTO.getRate()) : BigDecimal.ZERO;

        detailEntity.setTotalQuantity(quantity.multiply(detailEntity.getNumber()));

        if (invPurchaseInvoiceDTO.getCompanyActivity().equals(CompanyActivityClassEnum.Alumetal)) {
            total = AlumetalActivity(weight, total, detailEntity.getTotalQuantity(), cost);
        } else if (invPurchaseInvoiceDTO.getCompanyActivity().equals(CompanyActivityClassEnum.RetailSale)) {
            if (detailEntity.getUnitsItemSelected() != null) {
                UnitsItems unitsItems = getScrewingAndPrice(detailEntity.getItemId().getId(), detailEntity.getUnitsItemSelected());
                total = RetailActivity(total, detailEntity.getQuantity(), cost, detailEntity.getNumber(), unitsItems.getScrewing());
                if (unitsItems.getDiscountrate() != null) {
                    discount = unitsItems.getDiscountrate();
                } else {
                    discount = detailEntity.getDiscount() != null ? detailEntity.getDiscount() : BigDecimal.ZERO;
                }
                detailEntity.setTotal(total.subtract((total.multiply(discount).divide(new BigDecimal(100)))));
            }
        }
        discount = detailEntity.getDiscount() != null ? detailEntity.getDiscount() : BigDecimal.ZERO;
        valueAfterDiscount = total.add(detailEntity.getExtraCost()).subtract((total.multiply(discount).divide(new BigDecimal(100))));
        detailEntity.setValueAfterDiscount(valueAfterDiscount);
        detailEntity.setNet(valueAfterDiscount.setScale(3, BigDecimal.ROUND_UP));
        detailEntity.setTotalQuantityWithScrewing(detailEntity.getQuantity().multiply(detailEntity.getNumber()).multiply(detailEntity.getScrewing()));

        return detailEntity;
    }

    private BigDecimal AlumetalActivity(BigDecimal weight, BigDecimal total, BigDecimal totalQuanyity, BigDecimal cost) {
        if (weight == null || weight.compareTo(BigDecimal.ZERO) == 0) {
            total = totalQuanyity.multiply(cost);
        } else {
            total = weight.multiply(cost);
        }
        return total;
    }

    private BigDecimal RetailActivity(BigDecimal total, BigDecimal quantity, BigDecimal cost, BigDecimal number, BigDecimal screwing) {
        total = quantity.multiply(cost).multiply(number).multiply(screwing).setScale(2, BigDecimal.ROUND_UP);
        return total;
    }

    @Override
    public InvPurchaseInvoiceDTO addInvPurchaseInvoiceDetail(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO, Integer index) {
        try {
            if (invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList() != null && !invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList().isEmpty()) {
                String error = validateSave(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList());
                if (error != null && !error.isEmpty()) {
                    invPurchaseInvoiceDTO.setMsg(error);
                    return invPurchaseInvoiceDTO;
                }
            }

            InvDetailDTO invPurchaseInvoiceDetailsEntityNew = new InvDetailDTO();
            invPurchaseInvoiceDetailsEntityNew.setIndex(index);
            if (invPurchaseInvoiceDTO.getInvInventoryId() != null) {
                invPurchaseInvoiceDetailsEntityNew.setInventoryDTO(new InvInventoryDTO());
                invPurchaseInvoiceDetailsEntityNew.getInventoryDTO().setName(invPurchaseInvoiceDTO.getInvInventoryId().getName());
                invPurchaseInvoiceDetailsEntityNew.getInventoryDTO().setId(invPurchaseInvoiceDTO.getInvInventoryId().getId());
            }
            invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList().add(invPurchaseInvoiceDetailsEntityNew);
            invPurchaseInvoiceDTO.setInvPurchaseInvoiceDetailsSelected(invPurchaseInvoiceDetailsEntityNew);

        } catch (Exception e) {
            invPurchaseInvoiceDTO.setMsg(e.toString());
        }
        return invPurchaseInvoiceDTO;
    }

    @Override
    public InvDetailDTO validateDiscountColumn(InvDetailDTO detailDTO, Integer priceType) {
        try {
            if (!validateDiscount(detailDTO)) {
                detailDTO.setDiscount(BigDecimal.ZERO);
            } else {

                if (priceType == 1) {
                    checkDiscountForMinimunCost(detailDTO, detailDTO.getUnitsItem().getMinPriceMen());
                } else {
                    checkDiscountForMinimunCost(detailDTO, detailDTO.getUnitsItem().getMinPriceYoung());

                }
            }
        } catch (Exception e) {
//            saveError(e, "InvPurchaseInvoiceFormMB", "validateDiscountColumn");
        }
        return detailDTO;
    }

    private void checkDiscountForMinimunCost(InvDetailDTO detailDTO, BigDecimal x) {
        BigDecimal costAfterDiscount;
        costAfterDiscount = detailDTO.getCost().subtract(detailDTO.getCost().divide(new BigDecimal(100)).multiply(detailDTO.getDiscount()));
        if (costAfterDiscount.compareTo(x) < 0) {
            detailDTO.setMsg("الخصم غير مسموح بيه ");
            detailDTO.setDiscount(BigDecimal.ZERO);
        }
    }

    @Override
    public InvDetailDTO validatePriceColumn(InvDetailDTO detailDTO, Integer priceType) {
        try {
            if (!validatePrice(detailDTO)) {
                detailDTO.setCost(BigDecimal.ZERO);
            } else {
                if (priceType == 1) {
                    if (detailDTO.getCost().compareTo(detailDTO.getUnitsItem().getMinPriceMen()) < 0) {
                        detailDTO.setCost(detailDTO.getUnitsItem().getMinPriceMen());
                        detailDTO.setMsg("اقل سعر ممكن " + "  = " + detailDTO.getUnitsItem().getMinPriceMen());
                        detailDTO.setDiscount(BigDecimal.ZERO);
                        detailDTO.setShowDiscount(false);
                    } else {
                        detailDTO.setShowDiscount(true);
                    }
                } else {
                    if (detailDTO.getCost().compareTo(detailDTO.getUnitsItem().getMinPriceYoung()) < 0) {
                        detailDTO.setCost(detailDTO.getUnitsItem().getMinPriceYoung());
                        detailDTO.setMsg("اقل سعر ممكن " + "  = " + detailDTO.getUnitsItem().getMinPriceYoung());
                        detailDTO.setDiscount(BigDecimal.ZERO);
                        detailDTO.setShowDiscount(false);
                    } else {
                        detailDTO.setShowDiscount(true);
                    }
                }
            }
        } catch (Exception e) {
//            saveError(e, "InvPurchaseInvoiceFormMB", "validatePriceColumn");
        }
        return detailDTO;
    }

    @Override
    public InvDetailDTO validateQuantityColum(InvDetailDTO detailDTO) {
        try {
            if (validateQuantity(detailDTO)) {
                if (detailDTO.getQuantity().compareTo(BigDecimal.ZERO) < 0) {
                    detailDTO.setMsg("يجب ان تكون الكمية أكبر من الصفر");
                    detailDTO.setQuantity(BigDecimal.ZERO);
                } else {
                    detailDTO = validateTotalQuantity(detailDTO);
                    if (detailDTO.getMsg() != null && !detailDTO.getMsg().isEmpty()) {
                        detailDTO.setQuantity(BigDecimal.ZERO);
                    }
                }
            } else {
                detailDTO.setMsg("يجب اختيار صنف  وكميه");
                detailDTO.setQuantity(BigDecimal.ZERO);
            }

        } catch (Exception e) {
//            saveError(e, "InvPurchaseInvoiceFormMB", "validateQuantityColum");
        }
        return detailDTO;
    }

    public Boolean validateQuantityWhenSave(InvDetailDTO detailDTO) {
        try {

            BigDecimal avilableQuantity = quantityItemsStoreAddExitService.getQuantatyItemByinventory(detailDTO.getInventoryDTO().getId(), detailDTO.getItemId().getId());
            if (avilableQuantity.compareTo(detailDTO.getTotalQuantity()) >= 0) {
                return true;
            }
        } catch (Exception e) {
//            saveError(e, "InvPurchaseInvoiceFormMB", "validateQuantityColum");
        }
        return false;
    }

    @Override
    public InvDetailDTO validateNumberColum(InvDetailDTO detailDTO) {
        try {
            if (!validateNumber(detailDTO)) {
                detailDTO.setNumber(BigDecimal.ZERO);
            } else {
                detailDTO = validateTotalQuantity(detailDTO);
                if (detailDTO.getMsg() != null && !detailDTO.getMsg().isEmpty()) {
                    detailDTO.setQuantity(BigDecimal.ZERO);
                    detailDTO.setNumber(BigDecimal.ZERO);
                }
            }
        } catch (Exception e) {
//            saveError(e, "InvPurchaseInvoiceFormMB", "validateQuantityColum");
        }
        return detailDTO;
    }

    @Override
    public InvDetailDTO validateBounceColum(InvDetailDTO detailDTO) {
        try {
            if (!validateBounse(detailDTO)) {
                detailDTO.setBounse(BigDecimal.ZERO);
            } else {
                detailDTO.setNet(detailDTO.getTotal().add(detailDTO.getBounse()));
            }
        } catch (Exception e) {
//            saveError(e, "InvPurchaseInvoiceFormMB", "validateQuantityColum");
        }
        return detailDTO;
    }

    private InvDetailDTO validateTotalQuantity(InvDetailDTO detailDTO) {

        if (((detailDTO.getQuantity().multiply(detailDTO.getNumber())).multiply(detailDTO.getScrewing())).compareTo(detailDTO.getAvailableQuantityRow()) > 0) {
            detailDTO.setMsg("الكميه اكبر من الكميه الموجوده في المخزن ");
        }
        return detailDTO;
    }

    public Boolean validateQuantity(InvDetailDTO detailDTO) {

        return detailDTO != null && detailDTO.getItemId() != null
                && detailDTO.getQuantity() != null
                && detailDTO.getQuantity().compareTo(BigDecimal.ZERO) == 1;

    }

    public Boolean validateUnit(InvDetailDTO detailDTO) {

        return detailDTO != null && detailDTO.getItemId() != null
                && detailDTO.getUnitsItemSelected() != null;

    }

    @Override
    public InvDetailDTO validateUnitColum(InvDetailDTO detailDTO, Integer priceType) {
        try {
            if (validateUnit(detailDTO)) {
                findDataForItem(priceType, detailDTO);
                validateTotalQuantity(detailDTO);
            }
        } catch (Exception e) {
//            saveError(e, "InvPurchaseInvoiceFormMB", "validateQuantityColum");
        }
        return detailDTO;
    }

    public Boolean validatePrice(InvDetailDTO detailDTO) {
        return detailDTO != null
                && detailDTO.getItemId() != null
                && detailDTO.getCost() != null
                && detailDTO.getCost().compareTo(BigDecimal.ZERO) >= 0;
    }

    public Boolean validateInvParentId(InvDetailDTO detailDTO) {
        return detailDTO != null
                && detailDTO.getItemId() != null
                && detailDTO.getInvItemParentId() != null;

    }

    public Boolean validateNumber(InvDetailDTO detailDTO) {
        return detailDTO != null
                && detailDTO.getItemId() != null
                && detailDTO.getNumber() != null;
    }

    public Boolean validateBounse(InvDetailDTO detailDTO) {
        return detailDTO != null
                && detailDTO.getItemId() != null
                && detailDTO.getBounse() != null;
    }

    public Boolean validateCost(InvDetailDTO detailDTO) {
        return detailDTO != null
                && detailDTO.getItemId() != null
                && detailDTO.getCost() != null;
    }

    public Boolean validateInentory(InvDetailDTO detailDTO) {
        return detailDTO != null
                && detailDTO.getItemId() != null
                && detailDTO.getInventoryDTO() != null;
    }

    public Boolean validateClothNumber(InvDetailDTO detailDTO) {
        return detailDTO != null
                && detailDTO.getItemId() != null
                && detailDTO.getClothNumber() != null;
    }

    public Boolean validateDiscount(InvDetailDTO detailDTO) {
        return Validation.isDiscountValid(detailDTO.getDiscount());
    }

    @Override
    public InvDetailDTO validateItems(InvDetailDTO detailDTO, Integer priceType) {
        try {
            if (detailDTO.getItemId() == null) {
                detailDTO = resetInvItem(detailDTO);
            } else {
                detailDTO.setItemIdBak(detailDTO.getItemId());

                if (detailDTO.getItemId().getUnitId() != null) {
                    SymbolDTO unit = new SymbolDTO();
                    unit.setId(detailDTO.getItemId().getUnitId().getId());
                    unit.setName(detailDTO.getItemId().getUnitId().getName());
                    detailDTO.setUnitId(unit);
                    detailDTO = validateUnitColum(detailDTO, priceType);
                } else {
                    detailDTO = resetInvItem(detailDTO);
                    detailDTO.setMsg("هذا الصنف ليس لديه وحدات.");
                    return detailDTO;
                }
                if (detailDTO.getItemId().getPaintColor() != null) {
                    SymbolDTO paintColor = new SymbolDTO();
                    paintColor.setId(detailDTO.getItemId().getPaintColor().getId());
                    paintColor.setName(detailDTO.getItemId().getPaintColor().getName());
                    detailDTO.setPaintColor(paintColor);
                }
                detailDTO.setAvailableQuantityRow(quantityItemsStoreAddExitService.getQuantatyItemByinventory(detailDTO.getInventoryDTO().getId(), detailDTO.getItemId().getId()));

                detailDTO.setLength(detailDTO.getItemId().getLength());
                detailDTO.setWeight(detailDTO.getItemId().getWeight() != null ? detailDTO.getItemId().getWeight() : BigDecimal.ZERO);
                if (detailDTO.getMsg() != null && !detailDTO.getMsg().isEmpty()) {
                    detailDTO.setMsg(detailDTO.getMsg());
                    return detailDTO;

                }
                findDataForItem(priceType, detailDTO);
            }

        } catch (Exception e) {
            detailDTO.setMsg(e.toString());
            return detailDTO;
        }
        return detailDTO;
    }

    private InvDetailDTO resetInvItem(InvDetailDTO detailDTO) {
        try {
            detailDTO.setQuantity(null);
            detailDTO.setWeight(null);
            detailDTO.setCost(null);
            detailDTO.setDiscount(null);
            detailDTO.setUnitId(null);
        } catch (Exception e) {
            detailDTO.setMsg(e.toString());
        }
        return detailDTO;
    }

    public InvDetailDTO validateRow(InvDetailDTO invPurchaseInvoiceDetailDTO) {
        StringBuilder error = new StringBuilder();
//
//        if (invPurchaseInvoiceDetailDTO.getItemsBarcodesDetail() == null && invPurchaseInvoiceDetailDTO.getItemsBarcodesDetailTrans() != null) {
//            invPurchaseInvoiceDetailDTO.setItemsBarcodesDetail(invPurchaseInvoiceDetailDTO.getItemsBarcodesDetailTrans());
//        } else if (invPurchaseInvoiceDetailDTO.getItemsBarcodesDetail() != null && invPurchaseInvoiceDetailDTO.getItemsBarcodesDetailTrans() == null) {
//            invPurchaseInvoiceDetailDTO.setItemsBarcodesDetailTrans(invPurchaseInvoiceDetailDTO.getItemsBarcodesDetail());
//        } else if (invPurchaseInvoiceDetailDTO.getItemsBarcodesDetail() == null && invPurchaseInvoiceDetailDTO.getItemsBarcodesDetailTrans() == null) {
//            error.append("\n يجب ادخال الصنف");
//        }

        if (invPurchaseInvoiceDetailDTO.getItemId() == null && invPurchaseInvoiceDetailDTO.getItemIdBak() != null) {
            invPurchaseInvoiceDetailDTO.setItemId(invPurchaseInvoiceDetailDTO.getItemIdBak());
        } else if (invPurchaseInvoiceDetailDTO.getItemId() != null && invPurchaseInvoiceDetailDTO.getItemIdBak() == null) {
            invPurchaseInvoiceDetailDTO.setItemIdBak(invPurchaseInvoiceDetailDTO.getItemId());
        } else if (invPurchaseInvoiceDetailDTO.getItemId() == null && invPurchaseInvoiceDetailDTO.getItemIdBak() == null) {
            error.append("\n يجب ادخال الصنف");
        }

        if (invPurchaseInvoiceDetailDTO.getInvItemParentId() == null) {
            error.append("\n يجب اختيار منتج تام ");

        }
        if (!validateNumber(invPurchaseInvoiceDetailDTO)) {
            error.append("\n يجب اختيار العدد ");
        }

        if (!validateUnit(invPurchaseInvoiceDetailDTO)) {
            error.append("\n يجب اختيار الوحدة ");
        }

        if (!validateCost(invPurchaseInvoiceDetailDTO)) {
            error.append("\n يجب اختيار التكلفه ");
        }

        if (!validateQuantity(invPurchaseInvoiceDetailDTO)) {
            error.append("\n يجب اختيار الكميه ");
        } else {
            if (!validateQuantityWhenSave(invPurchaseInvoiceDetailDTO)) {
                error.append("\n الكمية غير موجودة بالمخزن ");
            }
        }
        if (!validateInentory(invPurchaseInvoiceDetailDTO)) {
            error.append("\n يجب اختيار المخزن ");
        }

        if (!validateClothNumber(invPurchaseInvoiceDetailDTO)) {
            error.append("\n يجب اختيار رقم التوب ");
        }
        invPurchaseInvoiceDetailDTO.setMsg(error.toString());
        return invPurchaseInvoiceDetailDTO;
    }

    public String validateSave(List<InvDetailDTO> invPurchaseInvoiceDetailDTOList) {
        StringBuilder error = new StringBuilder();
        for (InvDetailDTO detailDTO : invPurchaseInvoiceDetailDTOList) {
            detailDTO = validateRow(detailDTO);
            if (detailDTO.getMsg() != null && !detailDTO.getMsg().isEmpty()) {
                error.append("\n ").append(detailDTO.getMsg());
            }
        }
        return error.toString();
    }

    private InvPurchaseInvoiceDetail mapToEntity(InvDetailDTO dTO, Integer invPurchaseInvoiceId, TobyUser tobyUser) {
        InvPurchaseInvoiceDetail entity = new InvPurchaseInvoiceDetail();

        entity.setSerial(dTO.getSerial());
        entity.setNumber(dTO.getNumber());
        entity.setClothNumber(dTO.getClothNumber());
        if (dTO.getInvItemParentId() != null) {
            InvItem invItem = new InvItem();
            invItem.setId(dTO.getInvItemParentId().getId());
            invItem.setName(dTO.getInvItemParentId().getName());
            invItem.setCode(dTO.getInvItemParentId().getCode());
            entity.setInvItemParentId(invItem);
        }
        entity.setCost(dTO.getCost().setScale(3, BigDecimal.ROUND_UP));
        entity.setCostAvarage(dTO.getCostAvarage());
        entity.setDiscount(dTO.getDiscount());
        entity.setBounse(dTO.getBounse());
        entity.setNumber(dTO.getNumber());
        entity.setClothNumber(dTO.getClothNumber());

        if (dTO.getInventoryDTO() != null) {
            InvInventory invInventory = new InvInventory();
            invInventory.setId(dTO.getInventoryDTO().getId());
            entity.setInvInventoryId(invInventory);
        }

        entity.setDiscountType(dTO.getDiscountType());
        entity.setExtraCost(dTO.getExtraCost());
        entity.setItemBarcode(dTO.getItemBarcode());
        entity.setNet(dTO.getNet());
        entity.setQuantity(dTO.getQuantity());
        entity.setScrewing(dTO.getScrewing());
        entity.setStatus(dTO.getStatus());
        entity.setTaxValue(dTO.getTaxValue());
        entity.setTransferFrom(dTO.getTransferFrom());
        entity.setWeight(dTO.getWeight());

        if (invPurchaseInvoiceId != null) {
            InvPurchaseInvoice invPurchaseInvoice = new InvPurchaseInvoice();
            invPurchaseInvoice.setId(invPurchaseInvoiceId);
            entity.setInvPurchaseInvoiceId(invPurchaseInvoice);
        }

        if (dTO.getItemId() != null && dTO.getItemId().getId() != null) {
            InvItem item = new InvItem();
            item.setId(dTO.getItemId().getId());
            entity.setItemId(item);
        }
//        if (dTO.getItemsBarcodesDetail() != null || dTO.getItemsBarcodesDetailTrans() != null) {
//            InvItem invItem = new InvItem();
//            invItem.setId(dTO.getItemsBarcodesDetail() != null ? dTO.getItemsBarcodesDetail().getItemId() : dTO.getItemsBarcodesDetailTrans().getItemId());
//            entity.setItemId(invItem);
//            entity.setItemBarcode(dTO.getItemsBarcodesDetail() != null ? dTO.getItemsBarcodesDetail().getBarcode() : dTO.getItemsBarcodesDetailTrans().getBarcode());
//        }

//        if (dTO.getItemsBarcodesDetail().getUnitId() != null) {
//            Symbol symbol = new Symbol();
//            symbol.setId(dTO.getItemsBarcodesDetail().getUnitId());
//            entity.setUnitId(symbol);
//        }
        if (dTO.getUnitId() != null) {
            Symbol symbol = new Symbol();
            symbol.setId(dTO.getUnitId().getId());
            entity.setUnitId(symbol);
        }
        if (dTO.getId() == null) {
            entity.setCreatedBy(tobyUser);
            entity.setCreationDate(new Date());
        } else {
            TobyUser user = new TobyUser();
            user.setId(dTO.getCreatedBy());
            entity.setCreatedBy(user);
            entity.setCreationDate(dTO.getCreatedDate());
            entity.setId(dTO.getId());
            entity.setModificationDate(new Date());
            entity.setModifiedBy(tobyUser);
        }

        entity.setCompanyId(tobyUser.getCompanyId());
        entity.setBranchId(tobyUser.getBranchId());
        return entity;
    }

    @Override
    public void deleteInvPurchaseInvoiceDetailListDTO(List<InvDetailDTO> invPurchaseInvoiceDetailList) {
        if (invPurchaseInvoiceDetailList != null && !invPurchaseInvoiceDetailList.isEmpty()) {
            for (InvDetailDTO detailDTO : invPurchaseInvoiceDetailList) {
                if (detailDTO.getId() != null) {
                    InvPurchaseInvoiceDetail invPurchaseInvoiceDetail = new InvPurchaseInvoiceDetail();
                    invPurchaseInvoiceDetail.setId(detailDTO.getId());
                    dao.deleteEntity(invPurchaseInvoiceDetail);
                }
            }
        }

    }

    @Override
    public String addInvPurchaseInvoiceDetailsDTO(List<InvDetailDTO> invPurchaseInvoiceDetailList, Integer invPurchaseInvoiceId, TobyUser tobyUser) {
        String error = null;
        if (invPurchaseInvoiceDetailList != null && !invPurchaseInvoiceDetailList.isEmpty()) {
            error = validateSave(invPurchaseInvoiceDetailList);
            if (error != null && !error.isEmpty()) {
                return error;
            }
            InvPurchaseInvoiceDetail detail;
            for (InvDetailDTO detailDTO : invPurchaseInvoiceDetailList) {

                detail = mapToEntity(detailDTO, invPurchaseInvoiceId, tobyUser);
                dao.saveEntity(detail);
            }
        }

        return error;
    }

    private UnitsItems getScrewingAndPrice(Integer itemId, Integer unitId) {
        UnitsItems unitsItems = new UnitsItems();
        if (itemId != null && unitId != null) {
            unitsItems = unitsItemsService.getScrewingAndPrice(itemId, unitId);
            if (unitsItems == null) {
                unitsItems = new UnitsItems();
                unitsItems.setMsg("الصنف غير موجود");

            }
        } else {
            unitsItems.setMsg("يجب إدخل صنف و وحدة");
        }
        return unitsItems;
    }

    private InvDetailDTO findDataForItem(Integer priceType, InvDetailDTO invDetailDTO) {
        UnitsItems unitsItems = getScrewingAndPrice(invDetailDTO.getItemId().getId(), invDetailDTO.getUnitId().getId());
        if (unitsItems != null && unitsItems.getMsg() == null || unitsItems.getMsg().isEmpty()) {
            if (unitsItems.getDiscountrate() != null && unitsItems.getDiscountrate().compareTo(BigDecimal.ZERO) == 1) {
                invDetailDTO.setShowDiscount(true);
                invDetailDTO.setShowPrice(true);

            }
            if (priceType == 1) {
                invDetailDTO.setCost(unitsItems.getMaxPriceMen());
                invDetailDTO.setQuantity(new BigDecimal(4));

            } else {
                invDetailDTO.setCost(unitsItems.getMaxPriceYoung());
                invDetailDTO.setQuantity(new BigDecimal(3.5));
            }
            invDetailDTO.setDiscount(unitsItems.getDiscountrate());
            invDetailDTO.setScrewing(unitsItems.getScrewing());
        } else {
            invDetailDTO.setMsg(unitsItems.getMsg());
        }
        return invDetailDTO;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.bussinessservice.global.InvPurchaseReturnSave;
import com.toby.core.GenericDAO;
import com.toby.entity.InvAddingorder;
import com.toby.entity.InvAddingorderDetail;
import com.toby.entity.InvPurchaseInvoice;
import com.toby.entity.InvPurchaseInvoiceDetail;
import com.toby.entity.InventorySetup;
import com.toby.views.QuantityItemsStore;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class InvAddingOrderServiceImpl implements InvAddingOrderService {

    @EJB
    private GenericDAO dao;
    @EJB
    private GenericService genericService;
    @EJB
    private InvPurchaseInvoiceService invPurchaseInvoiceService;
    @EJB
    private InvPurchaseInvoiceDetailsService invPurchaseInvoiceDetailsService;
    @EJB
    private InvPurchaseOrderDetailsService invPurchaseOrderDetailsService;
    @EJB
    private InvQuotationDetailsService invQuotationDetailsService;
    @EJB
    private InvReservationDetailService invReservationDetailService;
    @EJB
    private InvPurchaseOrderService invPurchaseOrderService;
    @EJB
    private InvAddingOrderService invAddingOrderService;
    @EJB
    private InvItemService invItemService;
    @EJB
    private QuantityItemsStoreService itemsStoreService;
    @EJB
    private InventorySetupService inventorySetupService;

    @EJB
    InvAddingOrderDetailsService invAddingOrderDetailsService;

    InvPurchaseReturnSave invPurchaseReturnSave;

    @Override
    public InvPurchaseReturnSave addInvAddingOrder(InvAddingorder invAddingOrder,
            List<InvAddingorderDetail> invAddingOrderDetailList,
            List<InvAddingorderDetail> invAddingOrderDetailListDeleted,
            List<Integer> updatedPurchaseOrAddingOrderList,
            StringBuilder headIdList, Boolean isPurchaseOrSales) {
        invPurchaseReturnSave = new InvPurchaseReturnSave();
        Integer serial;
        if (invAddingOrder.getId() != null) {
            dao.updateEntity(invAddingOrder);
        } else {

            serial = genericService.getMaxSerialForInvoice(InvAddingorder.class, invAddingOrder.getBranchId().getId(), invAddingOrder.getInvInventoryId().getId(), invAddingOrder.getType());
            invAddingOrder.setSerial(serial);

            dao.saveEntity(invAddingOrder);
        }

        //--------------------
        // لو انا محمل الفاتورة من مرحلة سابقة بعمل هنا تعديل للكميات وحالة كل فاتورة من المحمل منها
        String msg = updateFinalQuantityAndStatusDetailAndSaveDetail(invAddingOrderDetailList, isPurchaseOrSales, invAddingOrder);

        if (msg == null) {
            // لو الدنيا اللى فاتت تمام نروح نمسح من قاعدة البيانات اللى اتمسح من الفاتورة
            deleteDetails(invAddingOrderDetailListDeleted, isPurchaseOrSales);
            // هنا بروح اعدل حالة الفاتورة علشان وانا جاي استدعي الفواتير اكون عارف حالتها
            if (updatedPurchaseOrAddingOrderList != null && !updatedPurchaseOrAddingOrderList.isEmpty()) {
                updateHeadStatus(updatedPurchaseOrAddingOrderList);
            } else {
                if (headIdList != null && !"".equals(headIdList.toString())) {
                    updateHeadStatus(findHeadSelectedId(headIdList));
                }
            }
            invPurchaseReturnSave.setInvAddingorder(invAddingOrder);
            invPurchaseReturnSave.setInvAddingorderDetailList(invAddingOrderDetailsService.getAllInvAddingOrderDetailsByInvAddingOrderId(invAddingOrder.getId()));
        } else {
            invAddingOrder.setMsg(msg);
            invPurchaseReturnSave.setMsgOrder(msg);
            dao.rollbackQuery();
        }

        return invPurchaseReturnSave;

    }

    //-----------------------
    public List<Integer> findHeadSelectedId(StringBuilder headIdList) {
        String query;

        query = "select DISTINCT h.id FROM InvPurchaseInvoice h Left join InvPurchaseInvoiceDetail d WHERE h.id = d.invPurchaseInvoiceId.id and d.id in (" + headIdList.toString() + ") ";
        return dao.findListByQuery(query);

    }

//---------------------------
    public synchronized String updateFinalQuantityAndStatusDetailAndSaveDetail(List<InvAddingorderDetail> invAddingorderDetailsUpdatedList,
            Boolean isPurchaseOrSales, InvAddingorder invAddingorder) {
        InventorySetup invSetup = inventorySetupService.getInventoryByBranchId(invAddingorder.getBranchId().getId());

        Integer selectedId;
        QuantityItemsStore quantityItemsStore;
        // بحدد اذا انا كنت فى فاتورة المشتريات
        if (!isPurchaseOrSales) {
            // بعمل لوب على تفاصيل الفاتورة
            for (InvAddingorderDetail invAddingorderDetail : invAddingorderDetailsUpdatedList) {
                // هنا بقي بعدل الكميات وحالة الفواتير من الشاشات المحمل منها الفاتورةر و بحفظ التفاصيل
                mergeDetailQuantities(invAddingorderDetail, isPurchaseOrSales, invAddingorder);
            }
        } else {
            // اذا كنت فى فاتورة مبيعات فالامر بيزيد تعقيد
            // لانه بيشوف هل مسموحله يبيع بكميات سالبة ولا لاء
            for (InvAddingorderDetail invAddingorderDetail : invAddingorderDetailsUpdatedList) {
                quantityItemsStore = itemsStoreService.findInventoryItem(invAddingorder.getInvInventoryId().getId(),
                        invAddingorder.getBranchId().getId(), invAddingorderDetail.getItemId().getId(), invSetup.getSellBuy());
                // لو مسموحلة يبيع بكميات سالبة بيخش يشوف شغلة وينفض للباقى
                if (invSetup.getNegativeSell()) {
                    mergeDetailQuantities(invAddingorderDetail, isPurchaseOrSales, invAddingorder);
                } else {
                    quantityItemsStore = findQuantityItemNow(invAddingorder, invAddingorderDetail, invSetup);

                    if (quantityItemsStore != null && quantityItemsStore.getQty() != null
                            && quantityItemsStore.getQty().compareTo(invAddingorderDetail.getQuantity()) != -1 && !invSetup.getNegativeSell()) {
                        // بيدخل هنا فى حالة لو مش مسموحلة يبيع بكميات سالبة بس بشرط يكون الكمية اكبر من الصفر 
                        if (invAddingorderDetail.getId() != null) {
                            InvAddingorderDetail oldvalue = invAddingOrderDetailsService.findInvAddingOrderDetailsById(invAddingorderDetail.getId());
                            quantityItemsStore.setQty(quantityItemsStore.getQty().add(oldvalue.getQuantity()));
                        }
                        mergeDetailQuantities(invAddingorderDetail, isPurchaseOrSales, invAddingorder);
                    } else {
//                    dao.rollbackQuery();
                        return "Quantity Not Avialable";
                    }
                }

            }
        }

        return null;
    }
    
    private QuantityItemsStore findQuantityItemNow(InvAddingorder invAddingorder, InvAddingorderDetail addingorderDetail, InventorySetup invSetup) {
        QuantityItemsStore quantityItemsStore;
        quantityItemsStore = itemsStoreService.findInventoryItem(invAddingorder.getInvInventoryId().getId(),
                invAddingorder.getBranchId().getId(), addingorderDetail.getItemId().getId(), invSetup.getSellBuy());
        return quantityItemsStore;
    }

    //---------------------
    public void mergeDetailQuantities(InvAddingorderDetail invAddingorderDetail, Boolean isPurchaseOrSales, InvAddingorder invAddingorder) {
        // انا بجيب هنا رقم التفصيلى اللى فى الفاتورة اللى محمل منها وبردوا بحدد منه اذا كان التفصيلى دا متحمل من فاتورة ولا لاء   

        if (isPurchaseOrSales) {
            InvPurchaseInvoiceDetail selectedId = invAddingorderDetail.getSelectedId();
            if (selectedId != null) {
                Integer transferFrom = invAddingorderDetail.getTransferFrom();
                // 0 -> محمل من فاتورة امر شراء
                if (0 == transferFrom) {
                    updateSalesInvoiceStatusAndQuantity(invAddingorderDetail);
                }
            }
            // فى الاخر بحفظ التفصيلى
            saveDetail(invAddingorderDetail, invAddingorder, isPurchaseOrSales);
        } else {
            InvPurchaseInvoiceDetail selectedPurchaseId = invAddingorderDetail.getSelectedPurchaseId();
            if (selectedPurchaseId != null) {
                Integer transferFrom = invAddingorderDetail.getTransferFrom();
                // 0 -> محمل من فاتورة امر شراء
                if (0 == transferFrom) {
                    updateSalesInvoiceStatusAndQuantity(invAddingorderDetail);
                }
            }
            // فى الاخر بحفظ التفصيلى
            saveDetail(invAddingorderDetail, invAddingorder, isPurchaseOrSales);
        }

    }
    // هنا بقى لو كان التفصيلى دا متحمل من فاتورة تانية بخش اعمل تعديل فى الكميات والحالة بتاعته فى فاتورتة الاساسية

    //----------------------
    public void saveDetail(InvAddingorderDetail invAddingorderDetail, InvAddingorder invAddingorder, Boolean isPurchaseOrSales) {
        invAddingorderDetail.setAddingorderId(invAddingorder);
        if (invAddingorderDetail.getId() != null) {
            invAddingOrderDetailsService.updateInvAddingOrderDetails(invAddingorderDetail);
        } else {
            invAddingorderDetail.setSerial(invAddingOrderDetailsService.getMaxSerialForInvoiceDetail(invAddingorderDetail.getAddingorderId()));
            invAddingOrderDetailsService.addInvAddingOrderDetails(invAddingorderDetail);
        }
    }

    //----------------------      
    public void updateSalesInvoiceStatusAndQuantity(InvAddingorderDetail invAddingorderDetail) {
        InvPurchaseInvoiceDetail purchaseInvoiceDetail;

        BigDecimal purchaseInvoiceFinalQuantity;
        BigDecimal finalQuantity;
        Integer res;
        BigDecimal quantityInvoiceOld = null;
        // بجيب السطر اللى هشتغل عليه من جدول امر الشراء
//        purchaseInvoiceDetail = invPurchaseInvoiceDetailsService.findInvPurchaseInvoiceDetailsById(invAddingorderDetail.getSelectedId().getId());
        purchaseInvoiceDetail = invAddingorderDetail.getSelectedId();
        // بجيب الكمية المتبقية من الصنف دا علشان اشتغل عليها
        purchaseInvoiceFinalQuantity = purchaseInvoiceDetail.getFinalQuantity() != null
                ? purchaseInvoiceDetail.getFinalQuantity() : purchaseInvoiceDetail.getQuantity();
        // هنا بقى بجيب الكمية القديمة اللى متسجله فى قاعدة البايات يعنى قبل اما اعدل فيها 
        if (invAddingorderDetail.getId() != null) {
            InvAddingorderDetail invAddingorderDetailOld = invAddingOrderDetailsService.findInvAddingOrderDetailsById(invAddingorderDetail.getId());
            quantityInvoiceOld = invAddingorderDetailOld.getQuantity();
        }
        // المعادلة دي بتجيب الكمية النهائية المتبقية علشان احطها فى الجدول الاساسي
        finalQuantity = calculateFinalQuantity(invAddingorderDetail, purchaseInvoiceFinalQuantity, quantityInvoiceOld);
        // هندخل على مرحلة تعديل حالة التفصيلى
        // هنشوف هل لسه فيه كمية متبقية فى التفصيلى ولا لسه 
        res = finalQuantity.compareTo(BigDecimal.ZERO);
        //لو لسه فى كمية متبقية فى التفصيلى هنحطها وهنخلى الحالة بواحد
        if (res == 1) {
            purchaseInvoiceDetail.setFinalQuantity(finalQuantity);
            purchaseInvoiceDetail.setStatus(1);
        } else {
            // لو الكمية صفر او اقل هنخلى الحالة باثنين
            purchaseInvoiceDetail.setFinalQuantity(BigDecimal.ZERO);
            purchaseInvoiceDetail.setStatus(2);
        }
        // هنحفظ التفصيلى
        invPurchaseInvoiceDetailsService.updateInvPurchaseInvoiceDetails(purchaseInvoiceDetail);
    }
//---------------------

    public BigDecimal calculateFinalQuantity(InvAddingorderDetail invAddingorderDetail,
            BigDecimal existQuantitiyFromDataBaseSource, BigDecimal quantityInvoiceOld) {
        BigDecimal finalQuantity;
        // بتأكد الاول هل السطر دا متعدل ولا لسه جديد
        if (invAddingorderDetail.getId() == null) {
            // لو كان لسه جديد فكل اللى هعمله انى هطرح الكمية اللى داخلة فى التفصيلى من اللى فى قاعدة البيانات اللى فى جدول امر الشرا
            finalQuantity = existQuantitiyFromDataBaseSource.subtract(invAddingorderDetail.getQuantity());
        } else {
            // اما لو كان تعديل فهطرح الكمية اللى مكتوبه فى التفصيلى من الكمية القديمة اللى فى جدول المشتريات 
            // وبعدين الناتج هطرحه من الكمية اللى موجدة فى جدول امر الشرا
            finalQuantity = existQuantitiyFromDataBaseSource.subtract(invAddingorderDetail.getQuantity().subtract(quantityInvoiceOld));
        }
        return finalQuantity;
    }

    //--------------------------
    public void deleteDetails(List<InvAddingorderDetail> invAddingorderDetailListDeleted, Boolean isPurchaseOrSales) {
        if (invAddingorderDetailListDeleted != null && !invAddingorderDetailListDeleted.isEmpty()) {
            for (InvAddingorderDetail deleted : invAddingorderDetailListDeleted) {
                if (deleted.getId() != null) {
                    restorQuantity(deleted, isPurchaseOrSales);
                    invAddingOrderDetailsService.deleteInvAddingOrderDetails(deleted);
                }
            }
        }
    }
//----------------------

    public synchronized void restorQuantity(InvAddingorderDetail invAddingorderDetail, Boolean isPurchaseOrSales) {
        if (isPurchaseOrSales) {
            if (invAddingorderDetail.getSelectedId() != null) {
                if (invAddingorderDetail.getTransferFrom() == 0) {
//                    InvPurchaseInvoiceDetail dbPurchaseInvoice = invPurchaseInvoiceDetailsService.findInvPurchaseInvoiceDetailsById(invAddingorderDetail.getSelectedId());
                    InvPurchaseInvoiceDetail dbPurchaseInvoice = invAddingorderDetail.getSelectedId();
                    InvAddingorderDetail invAddingorderDetailOld = invAddingOrderDetailsService.findInvAddingOrderDetailsById(invAddingorderDetail.getId());
                    dbPurchaseInvoice.setFinalQuantity(dbPurchaseInvoice.getFinalQuantity().add(invAddingorderDetailOld.getQuantity()));
                    dbPurchaseInvoice.setStatus(restorDetailStatus(dbPurchaseInvoice.getQuantity(), dbPurchaseInvoice.getFinalQuantity()));
                    invPurchaseInvoiceDetailsService.updateInvPurchaseInvoiceDetails(dbPurchaseInvoice);
                    List<Integer> updatedPurchaseOrAddingOrderList = new ArrayList<>();
                    updatedPurchaseOrAddingOrderList.add(dbPurchaseInvoice.getInvPurchaseInvoiceId().getId());
                    updateHeadStatus(updatedPurchaseOrAddingOrderList);
                }
            }
        }
        /* else{
            if (invAddingorderDetail.getSelectedPurchaseId() != null) {
                if (invAddingorderDetail.getTransferFrom() == 0) {
//                    InvPurchaseInvoiceDetail dbPurchaseInvoice = invPurchaseInvoiceDetailsService.findInvPurchaseInvoiceDetailsById(invAddingorderDetail.getSelectedId());
                    InvPurchaseInvoiceDetail dbPurchaseInvoice = invAddingorderDetail.getSelectedId();
                        InvAddingorderDetail invAddingorderDetailOld = invAddingOrderDetailsService.findInvAddingOrderDetailsById(invAddingorderDetail.getId());
                    dbPurchaseInvoice.setFinalQuantity(dbPurchaseInvoice.getFinalQuantity().add(invAddingorderDetailOld.getQuantity()));
                    dbPurchaseInvoice.setStatus(restorDetailStatus(dbPurchaseInvoice.getQuantity(), dbPurchaseInvoice.getFinalQuantity()));
                    invPurchaseInvoiceDetailsService.updateInvPurchaseInvoiceDetails(dbPurchaseInvoice);
                    List<Integer> updatedPurchaseOrAddingOrderList = new ArrayList<>();
                    updatedPurchaseOrAddingOrderList.add(dbPurchaseInvoice.getInvPurchaseInvoiceId().getId());
                    updateHeadStatus(updatedPurchaseOrAddingOrderList);
                }
            } 
        }*/
    }
//----------------

    public Integer restorDetailStatus(BigDecimal quantity, BigDecimal finalQuantity) {
        if (quantity.compareTo(finalQuantity) == 0) {
            return 0;
        } else if (finalQuantity.compareTo(BigDecimal.ZERO) == 1 && quantity.compareTo(finalQuantity) == 1) {
            return 1;
        } else {
            return 2;
        }

    }

    //----------------------
    public void updateHeadStatus(List<Integer> updatedPurchaseOrAddingOrderList) {
        InvPurchaseInvoice purchaseInvoice;

        Map<String, Object> params = new HashMap<>();
        List<Integer> purchaseInvoiceDetailList;

        if (updatedPurchaseOrAddingOrderList != null) {
            for (Integer headId : updatedPurchaseOrAddingOrderList) {
                String query;

                params.put("headId", headId);
                query = "select p.status FROM InvPurchaseInvoiceDetail p WHERE p.invPurchaseInvoiceId.id = :headId ";
                purchaseInvoiceDetailList = dao.findListByQuery(query, params);
                purchaseInvoice = invPurchaseInvoiceService.findInvPurchaseInvoiceById(headId);
                if (purchaseInvoiceDetailList != null && purchaseInvoiceDetailList.size() > 0) {
                    for (int i = 0; i < purchaseInvoiceDetailList.size(); i++) {
                        if (i == 0) {
                            purchaseInvoice.setStatus(purchaseInvoiceDetailList.get(0));
                        } else {
                            Integer x = purchaseInvoiceDetailList.get(0) == null ? 0 : purchaseInvoiceDetailList.get(0);
                            Integer y = purchaseInvoiceDetailList.get(i) == null ? 0 : purchaseInvoiceDetailList.get(i);
                            if (x.compareTo(y) != 0) {

                                purchaseInvoice.setStatus(1);
                                break;
                            }
                        }
                    }
                    invPurchaseInvoiceService.updateInvPurchaseInvoice(purchaseInvoice);
                }

            }
        }
    }
    //----------------------

    @Override
    public void deleteInvAddingOrder(InvAddingorder invAddingOrder) {
        if (invAddingOrder.getId() != null && invAddingOrder.getId() > 0) {
            dao.executeDeleteQuery("delete from InvAddingorderDetail e WHERE e.id > 0 AND e.addingorderId.id = " + invAddingOrder.getId());
        }
        dao.deleteEntity(invAddingOrder);
    }

    @Override
    public List<InvAddingorder> getALLInvAddingOrderByCompanyId(Integer companyId) {
        return dao.findEntityListByCompanyId(InvAddingorder.class, companyId);
    }

    @Override
    public List<InvAddingorder> getALLInvAddingOrderByBranchId(Integer branchId, Boolean type) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        params.put("type", type);
        List<InvAddingorder> invAddingOrdersList = dao.findListByQuery("SELECT e FROM InvAddingorder e WHERE e.branchId.id = :branchId and e.type = :type", params);
        return invAddingOrdersList;
    }

    @Override
    public InvAddingorder findInvAddingOrderByInvAddingOrderId(Integer invAddingOrderId) {
        return dao.findEntityById(InvAddingorder.class, invAddingOrderId);
    }

    @Override
    public InvAddingorder updateInvAddingOrder(InvAddingorder invAddingOrder) {
        return dao.updateEntity(invAddingOrder);
    }

    @Override
    public List<InvAddingorder> getALLInvSalesOrderByBranchIdByStatus(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        List<InvAddingorder> invAddingOrdersList = dao.findListByQuery("SELECT e FROM InvAddingorder e WHERE e.branchId.id = :branchId and e.status != 2 and e.type = 1 ", params);
        return invAddingOrdersList;
    }

    @Override
    public List<InvAddingorder> getALLInvAddingOrderByBranchIdByStatus(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        List<InvAddingorder> invAddingOrdersList = dao.findListByQuery("SELECT e FROM InvAddingorder e WHERE e.branchId.id = :branchId and e.status != 2 and e.type = 0 ", params);
        return invAddingOrdersList;
    }

    /**
     * @return the invPurchaseInvoiceDetailsService
     */
    public InvPurchaseInvoiceDetailsService getInvPurchaseInvoiceDetailsService() {
        return invPurchaseInvoiceDetailsService;
    }

    /**
     * @param invPurchaseInvoiceDetailsService the
     * invPurchaseInvoiceDetailsService to set
     */
    public void setInvPurchaseInvoiceDetailsService(InvPurchaseInvoiceDetailsService invPurchaseInvoiceDetailsService) {
        this.invPurchaseInvoiceDetailsService = invPurchaseInvoiceDetailsService;
    }

    /**
     * @return the invPurchaseOrderDetailsService
     */
    public InvPurchaseOrderDetailsService getInvPurchaseOrderDetailsService() {
        return invPurchaseOrderDetailsService;
    }

    /**
     * @param invPurchaseOrderDetailsService the invPurchaseOrderDetailsService
     * to set
     */
    public void setInvPurchaseOrderDetailsService(InvPurchaseOrderDetailsService invPurchaseOrderDetailsService) {
        this.invPurchaseOrderDetailsService = invPurchaseOrderDetailsService;
    }

    /**
     * @return the invQuotationDetailsService
     */
    public InvQuotationDetailsService getInvQuotationDetailsService() {
        return invQuotationDetailsService;
    }

    /**
     * @param invQuotationDetailsService the invQuotationDetailsService to set
     */
    public void setInvQuotationDetailsService(InvQuotationDetailsService invQuotationDetailsService) {
        this.invQuotationDetailsService = invQuotationDetailsService;
    }

    /**
     * @return the invReservationDetailService
     */
    public InvReservationDetailService getInvReservationDetailService() {
        return invReservationDetailService;
    }

    /**
     * @param invReservationDetailService the invReservationDetailService to set
     */
    public void setInvReservationDetailService(InvReservationDetailService invReservationDetailService) {
        this.invReservationDetailService = invReservationDetailService;
    }

    /**
     * @return the invPurchaseOrderService
     */
    public InvPurchaseOrderService getInvPurchaseOrderService() {
        return invPurchaseOrderService;
    }

    /**
     * @param invPurchaseOrderService the invPurchaseOrderService to set
     */
    public void setInvPurchaseOrderService(InvPurchaseOrderService invPurchaseOrderService) {
        this.invPurchaseOrderService = invPurchaseOrderService;
    }

    /**
     * @return the invAddingOrderService
     */
    public InvAddingOrderService getInvAddingOrderService() {
        return invAddingOrderService;
    }

    /**
     * @param invAddingOrderService the invAddingOrderService to set
     */
    public void setInvAddingOrderService(InvAddingOrderService invAddingOrderService) {
        this.invAddingOrderService = invAddingOrderService;
    }

    /**
     * @return the invItemService
     */
    public InvItemService getInvItemService() {
        return invItemService;
    }

    /**
     * @param invItemService the invItemService to set
     */
    public void setInvItemService(InvItemService invItemService) {
        this.invItemService = invItemService;
    }

    /**
     * @return the itemsStoreService
     */
    public QuantityItemsStoreService getItemsStoreService() {
        return itemsStoreService;
    }

    /**
     * @param itemsStoreService the itemsStoreService to set
     */
    public void setItemsStoreService(QuantityItemsStoreService itemsStoreService) {
        this.itemsStoreService = itemsStoreService;
    }

    /**
     * @return the inventorySetupService
     */
    public InventorySetupService getInventorySetupService() {
        return inventorySetupService;
    }

    /**
     * @param inventorySetupService the inventorySetupService to set
     */
    public void setInventorySetupService(InventorySetupService inventorySetupService) {
        this.inventorySetupService = inventorySetupService;
    }
}

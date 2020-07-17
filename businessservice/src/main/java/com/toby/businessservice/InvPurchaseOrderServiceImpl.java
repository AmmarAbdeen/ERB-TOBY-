/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.bussinessservice.global.InvPurchaseReturnSave;
import com.toby.core.GenericDAO;
import com.toby.dto.InvInventoryDTO;
import com.toby.dto.InvPurchaseOrderDTO;
import com.toby.dto.InvPurchaseOrderDetailDTO;
import com.toby.entity.Branch;
import com.toby.entity.InvInventory;
import com.toby.entity.InvPurchaseOrder;
import com.toby.entity.InvPurchaseOrderDetail;
import com.toby.entity.TobyCompany;
import com.toby.entity.TobyUser;
import com.toby.views.PurchaseOrderNotLoadedFromInvAddingOrder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author H
 */
@Stateless
public class InvPurchaseOrderServiceImpl implements InvPurchaseOrderService {

    @EJB
    GenericDAO dao;
    @EJB
    private GenericService genericService;
    @EJB
    private InvPurchaseOrderDetailsService invPurchaseOrderDetailsService;
    InvPurchaseReturnSave invPurchaseReturnSave;
    
    

    @Override
    public List<InvPurchaseOrderDTO> findInvPurchaseOrederrDTO(TobyUser tobyUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", tobyUser.getBranchId().getId());         //////  AND ((e.type = 0 OR e.type = 2) AND e.active = 1) ORDER BY e.code
        List<InvPurchaseOrder> detailses = dao.findListByQuery("SELECT e FROM InvPurchaseOrder e WHERE e.branchId.id = :branchId ", params);
        return mapToDTOList(detailses, tobyUser);
    }

    //mapToEntity
    public InvPurchaseOrder mapToEntity(InvPurchaseOrderDTO invPurchaseOrderDTO, TobyUser tobyUser) {

        InvPurchaseOrder invPurchaseOrder = new InvPurchaseOrder();
        invPurchaseOrder.setDate(invPurchaseOrderDTO.getDate());
        invPurchaseOrder.setDiscount(invPurchaseOrderDTO.getDiscount());
        invPurchaseOrder.setDiscountType(invPurchaseOrderDTO.getDiscountType());
        invPurchaseOrder.setCurrencyId(invPurchaseOrderDTO.getCurrencyId());
        invPurchaseOrder.setId(invPurchaseOrderDTO.getId());
        invPurchaseOrder.setRemarks(invPurchaseOrderDTO.getRemarks());
        invPurchaseOrder.setSerial(invPurchaseOrderDTO.getSerial());
        invPurchaseOrder.setCurrencyId(invPurchaseOrderDTO.getCurrencyId());

        invPurchaseOrder.setMarkDisable(invPurchaseOrderDTO.getMarkDisable());
        invPurchaseOrder.setMarkEdit(invPurchaseOrderDTO.getMarkEdit());
        invPurchaseOrder.setMsg(invPurchaseOrderDTO.getMsg());
        invPurchaseOrder.setRate(invPurchaseOrderDTO.getRate());
        invPurchaseOrder.setRemarks(invPurchaseOrderDTO.getRemarks());
        invPurchaseOrder.setStatus(invPurchaseOrderDTO.getStatus());
        invPurchaseOrder.setCurrencyId(invPurchaseOrderDTO.getCurrencyId());
        invPurchaseOrder.setSupplierReference(invPurchaseOrderDTO.getSupplierReference());

        // invPurchaseOrder.setSupplierId(invPurchaseOrderDTO.getSupplierId());
//      if(invPurchaseOrderDTO.getSupplierId() !=null){
//            InvOrganizationSite invOrganizationSite =new InvOrganizationSite();
//            invOrganizationSite.set
//      
        if (invPurchaseOrderDTO.getInvInventoryId() != null) {
            InvInventory invInventory = new InvInventory();
            invInventory.setId(invPurchaseOrderDTO.getInvInventoryId().getId());
            invPurchaseOrder.setInvInventoryId(invInventory);
        }
        if (invPurchaseOrderDTO.getInvInventoryId() != null) {
            InvInventory invInventory = new InvInventory();
            invInventory.setId(invPurchaseOrderDTO.getInvInventoryId().getId());
            invPurchaseOrder.setInvInventoryId(invInventory);
        }

        if (invPurchaseOrderDTO.getCreatedBy() != null) {
            TobyUser user = new TobyUser();
            user.setId(invPurchaseOrderDTO.getCreatedBy());
            invPurchaseOrder.setCreatedBy(user);
            invPurchaseOrder.setCreationDate(invPurchaseOrderDTO.getCreatedDate());
            invPurchaseOrder.setModifiedBy(tobyUser);
            invPurchaseOrder.setModificationDate(new Date());
            if (invPurchaseOrderDTO.getCompanyId() != null) {
                TobyCompany company = new TobyCompany();
                company.setId(invPurchaseOrderDTO.getCompanyId());
                invPurchaseOrder.setCompanyId(company);
            }

            if (invPurchaseOrderDTO.getBranchId() != null) {
                Branch branch = new Branch();
                branch.setId(invPurchaseOrderDTO.getBranchId());
                invPurchaseOrder.setBranchId(branch);
            }
        } else {
            invPurchaseOrder.setCreatedBy(tobyUser);
            invPurchaseOrder.setCreationDate(new Date());
            invPurchaseOrder.setCompanyId(tobyUser.getCompanyId());
            invPurchaseOrder.setBranchId(tobyUser.getBranchId());
        }

        return invPurchaseOrder;
    }

    public InvPurchaseOrderDTO mapToDTO(InvPurchaseOrder invPurchaseOrder, TobyUser tobyUser) {

        InvPurchaseOrderDTO invPurchaseOrderDTO = new InvPurchaseOrderDTO();
        invPurchaseOrderDTO.setCreatedBy(invPurchaseOrder.getCreatedBy() != null ? invPurchaseOrder.getCreatedBy().getId() : null);
        invPurchaseOrderDTO.setCreatedDate(invPurchaseOrder.getCreationDate());
        invPurchaseOrderDTO.setBranchId(invPurchaseOrder.getBranchId() != null ? invPurchaseOrder.getBranchId().getId() : null);
        invPurchaseOrderDTO.setDate(invPurchaseOrder.getDate());
        invPurchaseOrderDTO.setDiscount(invPurchaseOrder.getDiscount());
        invPurchaseOrderDTO.setDiscountType(invPurchaseOrder.getDiscountType());
        invPurchaseOrderDTO.setId(invPurchaseOrder.getId());
        invPurchaseOrderDTO.setMarkDisable(invPurchaseOrder.getMarkDisable());
        invPurchaseOrderDTO.setMarkEdit(invPurchaseOrder.getMarkEdit());
        invPurchaseOrderDTO.setMsg(invPurchaseOrder.getMsg());
        invPurchaseOrderDTO.setRate(invPurchaseOrder.getRate());
        invPurchaseOrderDTO.setRemarks(invPurchaseOrder.getRemarks());
        invPurchaseOrderDTO.setSerial(invPurchaseOrder.getSerial());
        invPurchaseOrderDTO.setStatus(invPurchaseOrder.getStatus());
        invPurchaseOrderDTO.setCurrencyId(invPurchaseOrder.getCurrencyId());
//        invPurchaseOrderDTO.setSupplierId(invPurchaseOrder.getDiscountType());

        invPurchaseOrderDTO.setId(invPurchaseOrder.getId());

        invPurchaseOrderDTO.setCompanyId(invPurchaseOrder.getCompanyId().getId() != null ? invPurchaseOrder.getCompanyId().getId() : null);
        if (invPurchaseOrder.getInvInventoryId() != null) {
            InvInventoryDTO invInventoryDTO = new InvInventoryDTO();

        }
        return invPurchaseOrderDTO;

    }

    public List<InvPurchaseOrderDTO> mapToDTOList(List<InvPurchaseOrder> invPurchaseOrderList, TobyUser tobyUser) {
        List<InvPurchaseOrderDTO> invPurchaseOrderDTOList = new ArrayList<>();
        for (InvPurchaseOrder invPurchaseOrder : invPurchaseOrderList) {
            invPurchaseOrderDTOList.add(mapToDTO(invPurchaseOrder, tobyUser));
        }
        return invPurchaseOrderDTOList;
    }

    @Override
    public InvPurchaseReturnSave addPurchaseOrder(InvPurchaseOrder invPurchaseOrder, List<InvPurchaseOrderDetail> invPurchaseOrderDetailList, List<InvPurchaseOrderDetail> invPurchaseOrderDeleted) {
        Integer serial;
        invPurchaseReturnSave = new InvPurchaseReturnSave();
        if (invPurchaseOrder.getId() != null) {
            dao.updateEntity(invPurchaseOrder);
        } else {
            serial = genericService.getMaxGenericSerialByType(InvPurchaseOrder.class, invPurchaseOrder.getBranchId().getId(), null);
            invPurchaseOrder.setSerial(serial);
            dao.saveEntity(invPurchaseOrder);
        }

        for (InvPurchaseOrderDetail deleted : invPurchaseOrderDeleted) {
            invPurchaseOrderDetailsService.deletePurchaseOrderDetails(deleted);
        }

        for (InvPurchaseOrderDetail detail : invPurchaseOrderDetailList) {
            detail.setInvPurchaseOrderId(invPurchaseOrder);
            if (detail.getId() != null) {
                invPurchaseOrderDetailsService.updatePurchaseOrderDetails(detail);
            } else {
                serial = getMaxSerialForInvoiceDetail(invPurchaseOrder);
                detail.setSerial(serial);
                invPurchaseOrderDetailsService.addPurchaseOrderDetails(detail);
            }
        }
        invPurchaseReturnSave.setInvPurchaseOrder(invPurchaseOrder);
        invPurchaseReturnSave.setInvPurchaseOrderDetailList(invPurchaseOrderDetailsService.getAllPurchaseOrderDetailsByInvPurchaseOrderId(invPurchaseOrder.getId()));

        return invPurchaseReturnSave;
    }

    @Override
    public void deletePurchaseOrder(InvPurchaseOrder invPurchaseOrder, Integer invPurchaseOrderIdToPass) {
        if (invPurchaseOrderIdToPass != null && invPurchaseOrderIdToPass > 0) {
            dao.executeDeleteQuery("delete from InvPurchaseOrderDetail e WHERE e.id > 0 AND e.invPurchaseOrderId.id = " + invPurchaseOrderIdToPass);
        }
        dao.deleteEntity(invPurchaseOrder);
    }

    @Override
    public void deletePurchaseOrder(InvPurchaseOrderDTO invPurchaseOrderDTO, TobyUser tobyUser) {
        dao.executeDeleteQuery("delete from InvPurchaseOrderDetail e WHERE e.id > 0 AND e.invPurchaseOrderId.id = " + invPurchaseOrderDTO.getId());
        InvPurchaseOrder invPurchaseOrder = mapToEntity(invPurchaseOrderDTO, tobyUser);
        dao.deleteEntity(invPurchaseOrder);
    }

    @Override
    public List<InvPurchaseOrder> getALLPurchaseOrder() {
        return dao.findAll(InvPurchaseOrder.class
        );
    }

    @Override
    public List<InvPurchaseOrder> getALLPurchaseOrderByCompanyId(Integer Id) {
        return dao.findEntityListByCompanyId(InvPurchaseOrder.class, Id);
    }

    @Override
    public List<InvPurchaseOrder> getALLPurchaseOrderByBranchId(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        List<InvPurchaseOrder> invPurchaseOrders = dao.findListByQuery("SELECT e FROM InvPurchaseOrder e WHERE e.branchId.id = :branchId order by e.serial desc", params);
        return invPurchaseOrders;
    }

    @Override
    public List<InvPurchaseOrderDTO> getALLPurchaseOrderByBranchId(Integer branchId, TobyUser tobyUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        List<InvPurchaseOrder> invPurchaseOrders = dao.findListByQuery("SELECT e FROM InvPurchaseOrder e WHERE e.branchId.id = :branchId order by e.serial desc", params);
        return mapToDTOList(invPurchaseOrders, tobyUser);
    }

    @Override
    public List<InvPurchaseOrderDetail> getALLPurchaseOrderDetailsByPurchaseOrder(Integer purchaseOrderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("purchaseOrderId", purchaseOrderId);
        List<InvPurchaseOrderDetail> details = dao.findListByQuery("SELECT e FROM InvPurchaseOrderDetail e WHERE e.invPurchaseOrderId.id = :purchaseOrderId", params);
        return details;
    }


    @Override
    public InvPurchaseOrder findPurchaseOrderByPurchaseOrderId(Integer purchaseOrderId) {
        return dao.findEntityById(InvPurchaseOrder.class, purchaseOrderId);
    }

    @Override
    public InvPurchaseOrderDTO findPurchaseOrderByPurchaseOrderId(Integer purchaseOrderId,TobyUser tobyUser) {
        InvPurchaseOrder invPurchaseOrder=dao.findEntityById(InvPurchaseOrder.class, purchaseOrderId);
        return mapToDTO(invPurchaseOrder, tobyUser);
    }

    @Override
    public List<PurchaseOrderNotLoadedFromInvAddingOrder> getAllPurchaseOrderNotLoaded(Integer branchId) {
        Map<String, Object> params = new HashMap<>();

        params.put("branchId", branchId);
        List<PurchaseOrderNotLoadedFromInvAddingOrder> details = dao.findListByQuery("SELECT e FROM PurchaseOrderNotLoadedFromInvAddingOrder e WHERE e.branchId= :branchId", params);
        return details;
    }

    @Override
    public InvPurchaseOrder updateInvPurchaseOrder(InvPurchaseOrder invPurchaseOrder) {
        return dao.updateEntity(invPurchaseOrder);
    }

    @Override
    public List<InvPurchaseOrder> getALLPurchaseOrderByBranchIdByStatus(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        List<InvPurchaseOrder> invPurchaseOrders = dao.findListByQuery("SELECT e FROM InvPurchaseOrder e WHERE e.branchId.id = :branchId and e.status != 2 ", params);
        return invPurchaseOrders;
    }

    public synchronized Integer getMaxSerialForInvoiceDetail(InvPurchaseOrder invPurchaseOrder) {
        Map<String, Object> params = new HashMap<>();
        params.put("invPurchaseOrderId", invPurchaseOrder.getId());

        Integer serial = dao.findEntityByQuery("SELECT max(e.serial) FROM InvPurchaseOrderDetail e WHERE e.invPurchaseOrderId.id =:invPurchaseOrderId ", params);

        if (serial == null) {
            return 1;
        } else {
            serial = serial + 1;
            return serial;
        }
    }

    
}

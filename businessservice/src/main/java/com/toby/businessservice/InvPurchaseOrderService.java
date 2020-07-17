/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.bussinessservice.global.InvPurchaseReturnSave;
import com.toby.dto.InvPurchaseOrderDTO;
import com.toby.dto.InvPurchaseOrderDetailDTO;
import com.toby.entity.InvPurchaseOrder;
import com.toby.entity.InvPurchaseOrderDetail;
import com.toby.entity.TobyUser;
import com.toby.views.PurchaseOrderNotLoadedFromInvAddingOrder;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author H
 */
@Remote
public interface InvPurchaseOrderService {
   public List<InvPurchaseOrderDTO> findInvPurchaseOrederrDTO( TobyUser tobyUser) ;
   
   public InvPurchaseReturnSave addPurchaseOrder(InvPurchaseOrder invPurchaseOrder, List<InvPurchaseOrderDetail> invPurchaseOrderDetailList, List<InvPurchaseOrderDetail> invPurchaseOrderDeleted);

    public void deletePurchaseOrder(InvPurchaseOrder invPurchaseOrder, Integer invPurchaseOrderIdToPass);
    
    public void deletePurchaseOrder(InvPurchaseOrderDTO invPurchaseOrderDTO,TobyUser tobyUser);

    public List<InvPurchaseOrder> getALLPurchaseOrder();

    public List<InvPurchaseOrder> getALLPurchaseOrderByCompanyId(Integer Id);

    public List<InvPurchaseOrder> getALLPurchaseOrderByBranchId(Integer Id);
    
    public List<InvPurchaseOrderDTO> getALLPurchaseOrderByBranchId(Integer Id ,TobyUser tobyUser);

    public List<InvPurchaseOrderDetail> getALLPurchaseOrderDetailsByPurchaseOrder(Integer purchaseOrderId);
    
    
    public InvPurchaseOrder findPurchaseOrderByPurchaseOrderId(Integer purchaseOrderId);
    
    public InvPurchaseOrderDTO findPurchaseOrderByPurchaseOrderId(Integer purchaseOrderId,TobyUser tobyUser);
    
     public List<PurchaseOrderNotLoadedFromInvAddingOrder> getAllPurchaseOrderNotLoaded(Integer branchId);

    public InvPurchaseOrder updateInvPurchaseOrder(InvPurchaseOrder invPurchaseOrder);

    public List<InvPurchaseOrder> getALLPurchaseOrderByBranchIdByStatus(Integer branchId);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.dto.InvPurchaseOrderDetailDTO;
import com.toby.entity.InvPurchaseOrderDetail;
import com.toby.entity.TobyUser;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author hq002
 */
@Remote
public interface InvPurchaseOrderDetailsService {

    public void addPurchaseOrderDetails(InvPurchaseOrderDetail details);

    public void deletePurchaseOrderDetails(InvPurchaseOrderDetail details);

    public InvPurchaseOrderDetail updatePurchaseOrderDetails(InvPurchaseOrderDetail details);

    public List<InvPurchaseOrderDetail> getAllPurchaseOrderDetails();
    
    public List<InvPurchaseOrderDetail> getAllPurchaseOrderDetailsByInvPurchaseOrderId(Integer invPurchaseOrderId);
    
   public List<InvPurchaseOrderDetailDTO> getALLPurchaseOrderDetailsByPurchaseOrder(Integer purchaseOrderId,TobyUser tobyUser);

      

    public List<InvPurchaseOrderDetail> getAllPurchaseOrderDetails(Integer id);

    public InvPurchaseOrderDetail findInvPurchaseOrderDetailById(Integer invPurchaseOrderDetailId);

    public List<InvPurchaseOrderDetail> getAllPurchaseOrderDetailsWithStatus(Integer id);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.entity.InvAddingorder;
import com.toby.entity.InvAddingorderDetail;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author WIN7
 */
@Remote
public interface InvAddingOrderDetailsService {

    public void addInvAddingOrderDetails(InvAddingorderDetail invAddingOrderDetail);

    public void deleteInvAddingOrderDetails(InvAddingorderDetail invAddingOrderDetail);

    public InvAddingorderDetail updateInvAddingOrderDetails(InvAddingorderDetail invAddingOrderDetail);

    public List<InvAddingorderDetail> getAllInvAddingOrderDetailsByInvAddingOrderId(Integer invAddingOrderId);

    public InvAddingorderDetail findInvAddingOrderDetailsById(Integer invAddingOrderDetailsId);

    public List<InvAddingorderDetail> getAllInvAddingOrderDetailsByInvAddingOrderIdWithStatus(Integer invAddingOrderId);
    
    public BigDecimal findQuantityUsageForItem(Integer invPurchaseOrderDetailId);
    
    public Integer getMaxSerialForInvoiceDetail(InvAddingorder addingOrderId) ;
}

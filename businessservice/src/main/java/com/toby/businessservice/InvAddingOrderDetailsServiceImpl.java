/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.core.GenericDAO;
import com.toby.entity.InvAddingorder;
import com.toby.entity.InvAddingorderDetail;
import java.math.BigDecimal;
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
public class InvAddingOrderDetailsServiceImpl implements InvAddingOrderDetailsService {

    @EJB
    private GenericDAO dao;

    @Override
    public void addInvAddingOrderDetails(InvAddingorderDetail invAddingOrderDetail) {
        dao.saveEntity(invAddingOrderDetail);
    }

    @Override
    public void deleteInvAddingOrderDetails(InvAddingorderDetail invAddingOrderDetail) {
        dao.deleteEntity(invAddingOrderDetail);
    }

    @Override
    public InvAddingorderDetail updateInvAddingOrderDetails(InvAddingorderDetail invAddingOrderDetail) {
        return dao.updateEntity(invAddingOrderDetail);
    }

    @Override
    public List<InvAddingorderDetail> getAllInvAddingOrderDetailsByInvAddingOrderId(Integer invAddingOrderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("invAddingOrderId", invAddingOrderId);
        List<InvAddingorderDetail> details = dao.findListByQuery("SELECT e FROM InvAddingorderDetail e WHERE e.addingorderId.id = :invAddingOrderId", params);
        return details;
    }

    @Override
    public List<InvAddingorderDetail> getAllInvAddingOrderDetailsByInvAddingOrderIdWithStatus(Integer invAddingOrderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("invAddingOrderId", invAddingOrderId);
        List<InvAddingorderDetail> details = dao.findListByQuery("SELECT e FROM InvAddingorderDetail e WHERE e.addingorderId.id = :invAddingOrderId and e.status not in (2) or e.status is null ", params);
        return details;
    }

    @Override
    public InvAddingorderDetail findInvAddingOrderDetailsById(Integer invAddingOrderDetailsId) {
        return dao.findEntityById(InvAddingorderDetail.class, invAddingOrderDetailsId);
    }
    
    
    @Override
    public BigDecimal findQuantityUsageForItem(Integer invPurchaseOrderDetailId) {
        Map<String, Object> params = new HashMap<>();
        params.put("selectedInvPurchaseOrderDetailId", invPurchaseOrderDetailId);
        BigDecimal invAddingOrdersList = dao.findEntityByQuery("SELECT sum(e.quantity) FROM InvAddingorderDetail e WHERE e.selectedPurchaseOrderId.id = :selectedInvPurchaseOrderDetailId  ", params);
        return invAddingOrdersList;
    }
    
    @Override
    public synchronized Integer getMaxSerialForInvoiceDetail(InvAddingorder addingOrderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("addingorderId", addingOrderId.getId());

        Integer serial = dao.findEntityByQuery("SELECT max(e.serial) FROM InvAddingorderDetail e WHERE e.addingorderId.id =:addingorderId ", params);

        if (serial == null) {
            return 1;
        } else {
            serial = serial + 1;
            return serial;
        }
    }

}

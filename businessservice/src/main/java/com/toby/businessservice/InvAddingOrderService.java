/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.bussinessservice.global.InvPurchaseReturnSave;
import com.toby.entity.InvAddingorder;
import com.toby.entity.InvAddingorderDetail;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author WIN7
 */
@Remote
public interface InvAddingOrderService {

    public InvPurchaseReturnSave addInvAddingOrder(InvAddingorder invAddingOrder,
            List<InvAddingorderDetail> invAddingOrderDetailList,
            List<InvAddingorderDetail> invAddingOrderDetailListDeleted,
            List<Integer> updatedPurchaseOrAddingOrderList,
            StringBuilder headIdList, Boolean isPurchaseOrSales);
    public void deleteInvAddingOrder(InvAddingorder invAddingOrder);

    public List<InvAddingorder> getALLInvAddingOrderByCompanyId(Integer companyId);

    public List<InvAddingorder> getALLInvAddingOrderByBranchId(Integer branchId, Boolean type);

    public InvAddingorder findInvAddingOrderByInvAddingOrderId(Integer invAddingOrderId);

    public InvAddingorder updateInvAddingOrder(InvAddingorder invAddingOrder);

    public List<InvAddingorder> getALLInvSalesOrderByBranchIdByStatus(Integer branchId);

    public List<InvAddingorder> getALLInvAddingOrderByBranchIdByStatus(Integer branchId);
}

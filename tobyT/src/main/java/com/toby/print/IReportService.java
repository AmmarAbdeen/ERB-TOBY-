/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.print;

import com.toby.entiy.InvAddingOrderDetailsEntity;
import com.toby.entiy.InvAddingOrderEntity;
import com.toby.entiy.InvPurchaseOrderDetailEntity;
import com.toby.entiy.InvPurchaseOrderEntity;
import com.toby.toby.UserData;
import java.util.List;

/**
 *
 * @author hq004
 */
public interface IReportService {

    public void printReportInvPurchaseOrder(InvPurchaseOrderEntity invPurchaseOrderEntity, List<InvPurchaseOrderDetailEntity> invPurchaseOrderDetailEntityList, UserData userData);

    public void printReportInvAddingOrder(InvAddingOrderEntity invAddingOrderEntity,
            List<InvAddingOrderDetailsEntity> invAddingOrderDetailsEntityList, UserData userData);

    public void printReportInvPermissionOrder(InvAddingOrderEntity invAddingOrderEntity,
            List<InvAddingOrderDetailsEntity> invAddingOrderDetailsEntityList, UserData userData);

    }

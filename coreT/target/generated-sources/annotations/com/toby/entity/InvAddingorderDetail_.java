package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvAddingorder;
import com.toby.entity.InvItem;
import com.toby.entity.InvPurchaseInvoiceDetail;
import com.toby.entity.InvPurchaseOrderDetail;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvAddingorderDetail.class)
public class InvAddingorderDetail_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvAddingorderDetail, Branch> branchId;
    public static volatile SingularAttribute<InvAddingorderDetail, BigDecimal> quantity;
    public static volatile SingularAttribute<InvAddingorderDetail, Integer> transferFrom;
    public static volatile SingularAttribute<InvAddingorderDetail, InvAddingorder> addingorderId;
    public static volatile SingularAttribute<InvAddingorderDetail, InvPurchaseInvoiceDetail> selectedId;
    public static volatile SingularAttribute<InvAddingorderDetail, String> itemBarcode;
    public static volatile SingularAttribute<InvAddingorderDetail, BigDecimal> finalQuantity;
    public static volatile SingularAttribute<InvAddingorderDetail, InvItem> itemId;
    public static volatile SingularAttribute<InvAddingorderDetail, BigDecimal> screwing;
    public static volatile SingularAttribute<InvAddingorderDetail, InvPurchaseInvoiceDetail> selectedPurchaseId;
    public static volatile SingularAttribute<InvAddingorderDetail, InvPurchaseOrderDetail> selectedPurchaseOrderId;
    public static volatile SingularAttribute<InvAddingorderDetail, Integer> serial;
    public static volatile SingularAttribute<InvAddingorderDetail, Integer> status;

}
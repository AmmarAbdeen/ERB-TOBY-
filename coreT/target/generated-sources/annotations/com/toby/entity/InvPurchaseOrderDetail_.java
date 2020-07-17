package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvItem;
import com.toby.entity.InvPurchaseOrder;
import com.toby.entity.Symbol;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(InvPurchaseOrderDetail.class)
public class InvPurchaseOrderDetail_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvPurchaseOrderDetail, BigDecimal> discountRate;
    public static volatile SingularAttribute<InvPurchaseOrderDetail, Branch> branchId;
    public static volatile SingularAttribute<InvPurchaseOrderDetail, BigDecimal> quantity;
    public static volatile SingularAttribute<InvPurchaseOrderDetail, String> itemBarcode;
    public static volatile SingularAttribute<InvPurchaseOrderDetail, BigDecimal> finalQuantity;
    public static volatile SingularAttribute<InvPurchaseOrderDetail, InvItem> itemId;
    public static volatile SingularAttribute<InvPurchaseOrderDetail, BigDecimal> screwing;
    public static volatile SingularAttribute<InvPurchaseOrderDetail, BigDecimal> total;
    public static volatile SingularAttribute<InvPurchaseOrderDetail, Integer> serial;
    public static volatile SingularAttribute<InvPurchaseOrderDetail, BigDecimal> price;
    public static volatile SingularAttribute<InvPurchaseOrderDetail, InvPurchaseOrder> invPurchaseOrderId;
    public static volatile SingularAttribute<InvPurchaseOrderDetail, Symbol> unitId;
    public static volatile SingularAttribute<InvPurchaseOrderDetail, BigDecimal> net;
    public static volatile SingularAttribute<InvPurchaseOrderDetail, Integer> status;

}
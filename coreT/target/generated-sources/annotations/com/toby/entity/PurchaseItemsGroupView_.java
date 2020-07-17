package com.toby.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(PurchaseItemsGroupView.class)
public class PurchaseItemsGroupView_ { 

    public static volatile SingularAttribute<PurchaseItemsGroupView, Date> date;
    public static volatile SingularAttribute<PurchaseItemsGroupView, Integer> branchId;
    public static volatile SingularAttribute<PurchaseItemsGroupView, String> itemNumber;
    public static volatile SingularAttribute<PurchaseItemsGroupView, String> groupName;
    public static volatile SingularAttribute<PurchaseItemsGroupView, Integer> serial;
    public static volatile SingularAttribute<PurchaseItemsGroupView, Integer> rowNum;
    public static volatile SingularAttribute<PurchaseItemsGroupView, Integer> groupId;
    public static volatile SingularAttribute<PurchaseItemsGroupView, Integer> invoiceNumber;
    public static volatile SingularAttribute<PurchaseItemsGroupView, Integer> invInventoryId;
    public static volatile SingularAttribute<PurchaseItemsGroupView, BigDecimal> net;
    public static volatile SingularAttribute<PurchaseItemsGroupView, Boolean> type;

}
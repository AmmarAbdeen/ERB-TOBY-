package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.Currency;
import com.toby.entity.InvAddingorder;
import com.toby.entity.InvInventory;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.InvPurchaseOrderDetail;
import com.toby.entity.InventoryDelegator;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:57")
@StaticMetamodel(InvPurchaseOrder.class)
public class InvPurchaseOrder_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvPurchaseOrder, String> supplierReference;
    public static volatile SingularAttribute<InvPurchaseOrder, Date> date;
    public static volatile SingularAttribute<InvPurchaseOrder, Branch> branchId;
    public static volatile SingularAttribute<InvPurchaseOrder, InvOrganizationSite> supplierId;
    public static volatile SingularAttribute<InvPurchaseOrder, BigDecimal> discount;
    public static volatile CollectionAttribute<InvPurchaseOrder, InvPurchaseOrderDetail> invPurchaseOrderDetailCollection;
    public static volatile SingularAttribute<InvPurchaseOrder, BigDecimal> rate;
    public static volatile SingularAttribute<InvPurchaseOrder, Integer> serial;
    public static volatile SingularAttribute<InvPurchaseOrder, InvInventory> invInventoryId;
    public static volatile SingularAttribute<InvPurchaseOrder, Integer> discountType;
    public static volatile CollectionAttribute<InvPurchaseOrder, InvAddingorder> invAddingorderCollection;
    public static volatile SingularAttribute<InvPurchaseOrder, Currency> currencyId;
    public static volatile SingularAttribute<InvPurchaseOrder, InventoryDelegator> delegatorId;
    public static volatile SingularAttribute<InvPurchaseOrder, String> remarks;
    public static volatile SingularAttribute<InvPurchaseOrder, Integer> status;

}
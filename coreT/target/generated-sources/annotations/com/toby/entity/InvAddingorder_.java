package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvAddingorderDetail;
import com.toby.entity.InvInventory;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.InvPurchaseOrder;
import com.toby.entity.InventoryDelegator;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvAddingorder.class)
public class InvAddingorder_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvAddingorder, Date> date;
    public static volatile SingularAttribute<InvAddingorder, Branch> branchId;
    public static volatile SingularAttribute<InvAddingorder, Date> supplierDate;
    public static volatile SingularAttribute<InvAddingorder, InvOrganizationSite> organizationSiteId;
    public static volatile SingularAttribute<InvAddingorder, String> supplierInvoice;
    public static volatile SingularAttribute<InvAddingorder, String> remark;
    public static volatile SingularAttribute<InvAddingorder, Boolean> type;
    public static volatile SingularAttribute<InvAddingorder, Integer> postFlag;
    public static volatile SingularAttribute<InvAddingorder, Integer> serial;
    public static volatile SingularAttribute<InvAddingorder, InvPurchaseOrder> purchaseOrderId;
    public static volatile CollectionAttribute<InvAddingorder, InvAddingorderDetail> invAddingorderDetailCollection;
    public static volatile SingularAttribute<InvAddingorder, InvInventory> invInventoryId;
    public static volatile SingularAttribute<InvAddingorder, InventoryDelegator> invDelegatorId;
    public static volatile SingularAttribute<InvAddingorder, Integer> status;

}
package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvInventory;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.InvPurchaseInvoice;
import com.toby.entity.InvPurchaseOrder;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvInventoryTransaction.class)
public class InvInventoryTransaction_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvInventoryTransaction, Date> date;
    public static volatile SingularAttribute<InvInventoryTransaction, Branch> branchId;
    public static volatile SingularAttribute<InvInventoryTransaction, Date> supplierDate;
    public static volatile SingularAttribute<InvInventoryTransaction, InvOrganizationSite> organizationSiteId;
    public static volatile SingularAttribute<InvInventoryTransaction, String> supplierInvoice;
    public static volatile SingularAttribute<InvInventoryTransaction, String> remark;
    public static volatile SingularAttribute<InvInventoryTransaction, InvPurchaseInvoice> invpurchaseinvoiceId;
    public static volatile SingularAttribute<InvInventoryTransaction, Integer> type;
    public static volatile SingularAttribute<InvInventoryTransaction, Integer> isdeleted;
    public static volatile SingularAttribute<InvInventoryTransaction, Integer> postFlag;
    public static volatile SingularAttribute<InvInventoryTransaction, Integer> serial;
    public static volatile SingularAttribute<InvInventoryTransaction, InvPurchaseOrder> purchaseOrderId;
    public static volatile SingularAttribute<InvInventoryTransaction, Integer> invDelegatorId;
    public static volatile SingularAttribute<InvInventoryTransaction, InvInventory> invInventoryId;
    public static volatile SingularAttribute<InvInventoryTransaction, Integer> status;

}
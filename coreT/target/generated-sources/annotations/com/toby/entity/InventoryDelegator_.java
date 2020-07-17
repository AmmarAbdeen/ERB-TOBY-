package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.InvPurchaseOrder;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:57")
@StaticMetamodel(InventoryDelegator.class)
public class InventoryDelegator_ extends BaseEntity_ {

    public static volatile CollectionAttribute<InventoryDelegator, InvOrganizationSite> invOrganizationSiteCollection;
    public static volatile SingularAttribute<InventoryDelegator, Branch> branchId;
    public static volatile SingularAttribute<InventoryDelegator, String> img;
    public static volatile SingularAttribute<InventoryDelegator, String> code;
    public static volatile SingularAttribute<InventoryDelegator, BigDecimal> targetSales;
    public static volatile SingularAttribute<InventoryDelegator, String> name;
    public static volatile SingularAttribute<InventoryDelegator, String> mobile;
    public static volatile SingularAttribute<InventoryDelegator, BigDecimal> allowDiscountLimit;
    public static volatile SingularAttribute<InventoryDelegator, BigDecimal> commission;
    public static volatile SingularAttribute<InventoryDelegator, Integer> type;
    public static volatile CollectionAttribute<InventoryDelegator, InvPurchaseOrder> invPurchaseOrderCollection;

}
package com.toby.entity;

import com.toby.define.InventoryPricesGroupEnum;
import com.toby.entity.Branch;
import com.toby.entity.CostCenter;
import com.toby.entity.GlAccount;
import com.toby.entity.GlAdminUnit;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvInventory.class)
public class InvInventory_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvInventory, GlAccount> accountId;
    public static volatile SingularAttribute<InvInventory, Branch> branchId;
    public static volatile SingularAttribute<InvInventory, GlAdminUnit> adminUnitId;
    public static volatile SingularAttribute<InvInventory, Integer> invType;
    public static volatile SingularAttribute<InvInventory, String> code;
    public static volatile SingularAttribute<InvInventory, CostCenter> costCenterId;
    public static volatile SingularAttribute<InvInventory, String> typeItem;
    public static volatile SingularAttribute<InvInventory, InventoryPricesGroupEnum> sellPriceType;
    public static volatile SingularAttribute<InvInventory, String> name;

}
package com.toby.entity;

import com.toby.entity.Branch;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InventorySetup.class)
public class InventorySetup_ extends BaseEntity_ {

    public static volatile SingularAttribute<InventorySetup, Boolean> sellAllow;
    public static volatile SingularAttribute<InventorySetup, Boolean> dateSystem;
    public static volatile SingularAttribute<InventorySetup, Branch> branchId;
    public static volatile SingularAttribute<InventorySetup, Integer> itemCoding;
    public static volatile SingularAttribute<InventorySetup, Boolean> invSystem;
    public static volatile SingularAttribute<InventorySetup, Boolean> transfer;
    public static volatile SingularAttribute<InventorySetup, Boolean> negativeSell;
    public static volatile SingularAttribute<InventorySetup, Integer> reservationPeriod;
    public static volatile SingularAttribute<InventorySetup, Boolean> defaultSalesTax;
    public static volatile SingularAttribute<InventorySetup, Boolean> sellBuy;

}
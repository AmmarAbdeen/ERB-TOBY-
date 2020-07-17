package com.toby.views;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(QuantityItemsStore.class)
public class QuantityItemsStore_ { 

    public static volatile SingularAttribute<QuantityItemsStore, Integer> itemId;
    public static volatile SingularAttribute<QuantityItemsStore, Integer> branchId;
    public static volatile SingularAttribute<QuantityItemsStore, String> itemName;
    public static volatile SingularAttribute<QuantityItemsStore, BigDecimal> costAverage;
    public static volatile SingularAttribute<QuantityItemsStore, String> inventoryCode;
    public static volatile SingularAttribute<QuantityItemsStore, Integer> rowNum;
    public static volatile SingularAttribute<QuantityItemsStore, String> inventoryName;
    public static volatile SingularAttribute<QuantityItemsStore, String> itemCode;
    public static volatile SingularAttribute<QuantityItemsStore, BigDecimal> qty;
    public static volatile SingularAttribute<QuantityItemsStore, Integer> inventoryId;

}
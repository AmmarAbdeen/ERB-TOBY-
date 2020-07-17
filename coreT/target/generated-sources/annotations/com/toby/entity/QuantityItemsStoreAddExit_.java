package com.toby.entity;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(QuantityItemsStoreAddExit.class)
public class QuantityItemsStoreAddExit_ extends BaseEntity_ {

    public static volatile SingularAttribute<QuantityItemsStoreAddExit, Integer> itemId;
    public static volatile SingularAttribute<QuantityItemsStoreAddExit, Integer> branchId;
    public static volatile SingularAttribute<QuantityItemsStoreAddExit, String> itemName;
    public static volatile SingularAttribute<QuantityItemsStoreAddExit, BigDecimal> costAverage;
    public static volatile SingularAttribute<QuantityItemsStoreAddExit, String> inventoryCode;
    public static volatile SingularAttribute<QuantityItemsStoreAddExit, Integer> rowNum;
    public static volatile SingularAttribute<QuantityItemsStoreAddExit, String> itemCode;
    public static volatile SingularAttribute<QuantityItemsStoreAddExit, String> inventoryName;
    public static volatile SingularAttribute<QuantityItemsStoreAddExit, BigDecimal> qty;
    public static volatile SingularAttribute<QuantityItemsStoreAddExit, Integer> inventoryId;

}
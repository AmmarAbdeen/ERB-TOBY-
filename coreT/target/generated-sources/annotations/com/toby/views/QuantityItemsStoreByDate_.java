package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:57")
@StaticMetamodel(QuantityItemsStoreByDate.class)
public class QuantityItemsStoreByDate_ { 

    public static volatile SingularAttribute<QuantityItemsStoreByDate, Date> date;
    public static volatile SingularAttribute<QuantityItemsStoreByDate, Integer> itemId;
    public static volatile SingularAttribute<QuantityItemsStoreByDate, Integer> branchId;
    public static volatile SingularAttribute<QuantityItemsStoreByDate, String> itemName;
    public static volatile SingularAttribute<QuantityItemsStoreByDate, String> inventoryCode;
    public static volatile SingularAttribute<QuantityItemsStoreByDate, Integer> rowNum;
    public static volatile SingularAttribute<QuantityItemsStoreByDate, String> itemCode;
    public static volatile SingularAttribute<QuantityItemsStoreByDate, String> inventoryName;
    public static volatile SingularAttribute<QuantityItemsStoreByDate, BigDecimal> qty;
    public static volatile SingularAttribute<QuantityItemsStoreByDate, Integer> inventoryId;
    public static volatile SingularAttribute<QuantityItemsStoreByDate, Long> screenCode;

}
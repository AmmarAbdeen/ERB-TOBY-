package com.toby.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(HistoryChangeItemPrice.class)
public class HistoryChangeItemPrice_ extends BaseEntity_ {

    public static volatile SingularAttribute<HistoryChangeItemPrice, Integer> itemId;
    public static volatile SingularAttribute<HistoryChangeItemPrice, Integer> branchId;
    public static volatile SingularAttribute<HistoryChangeItemPrice, BigDecimal> price;
    public static volatile SingularAttribute<HistoryChangeItemPrice, Date> chagePriceDate;

}
package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(ItemsBalanceView.class)
public class ItemsBalanceView_ { 

    public static volatile SingularAttribute<ItemsBalanceView, Date> date;
    public static volatile SingularAttribute<ItemsBalanceView, Integer> branchId;
    public static volatile SingularAttribute<ItemsBalanceView, Integer> itemSerial;
    public static volatile SingularAttribute<ItemsBalanceView, Integer> accountIdFrom;
    public static volatile SingularAttribute<ItemsBalanceView, BigDecimal> valueLocal;
    public static volatile SingularAttribute<ItemsBalanceView, String> bankNameTo;
    public static volatile SingularAttribute<ItemsBalanceView, String> remark;
    public static volatile SingularAttribute<ItemsBalanceView, String> bankNameFrom;
    public static volatile SingularAttribute<ItemsBalanceView, Integer> bankIdfrom;
    public static volatile SingularAttribute<ItemsBalanceView, Integer> transactionType;
    public static volatile SingularAttribute<ItemsBalanceView, Integer> itemId;
    public static volatile SingularAttribute<ItemsBalanceView, String> itemName;
    public static volatile SingularAttribute<ItemsBalanceView, Integer> serial;
    public static volatile SingularAttribute<ItemsBalanceView, Integer> rowNum;
    public static volatile SingularAttribute<ItemsBalanceView, Integer> accountIdTo;
    public static volatile SingularAttribute<ItemsBalanceView, Integer> bankIdTo;
    public static volatile SingularAttribute<ItemsBalanceView, BigDecimal> glValue;
    public static volatile SingularAttribute<ItemsBalanceView, Integer> glYear;

}
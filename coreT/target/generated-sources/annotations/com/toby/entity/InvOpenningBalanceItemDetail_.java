package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvItem;
import com.toby.entity.InvOpenningBalanceItem;
import com.toby.entity.Symbol;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvOpenningBalanceItemDetail.class)
public class InvOpenningBalanceItemDetail_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvOpenningBalanceItemDetail, BigDecimal> screwing;
    public static volatile SingularAttribute<InvOpenningBalanceItemDetail, Branch> branchId;
    public static volatile SingularAttribute<InvOpenningBalanceItemDetail, InvItem> itemId;
    public static volatile SingularAttribute<InvOpenningBalanceItemDetail, BigDecimal> quantity;
    public static volatile SingularAttribute<InvOpenningBalanceItemDetail, BigDecimal> cost;
    public static volatile SingularAttribute<InvOpenningBalanceItemDetail, Integer> serial;
    public static volatile SingularAttribute<InvOpenningBalanceItemDetail, InvOpenningBalanceItem> balanceItemId;
    public static volatile SingularAttribute<InvOpenningBalanceItemDetail, Symbol> unitId;
    public static volatile SingularAttribute<InvOpenningBalanceItemDetail, BigDecimal> net;

}
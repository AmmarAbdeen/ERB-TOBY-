package com.toby.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(SalesDelegateView.class)
public class SalesDelegateView_ extends BaseEntity_ {

    public static volatile SingularAttribute<SalesDelegateView, Date> date;
    public static volatile SingularAttribute<SalesDelegateView, Integer> branchId;
    public static volatile SingularAttribute<SalesDelegateView, Integer> serail;
    public static volatile SingularAttribute<SalesDelegateView, String> delegatorCode;
    public static volatile SingularAttribute<SalesDelegateView, Integer> organizationSiteId;
    public static volatile SingularAttribute<SalesDelegateView, Boolean> typeView;
    public static volatile SingularAttribute<SalesDelegateView, BigDecimal> documentarycredit;
    public static volatile SingularAttribute<SalesDelegateView, Integer> paymentType;
    public static volatile SingularAttribute<SalesDelegateView, String> delegatorName;
    public static volatile SingularAttribute<SalesDelegateView, BigDecimal> total;
    public static volatile SingularAttribute<SalesDelegateView, Integer> invDelegatorId;
    public static volatile SingularAttribute<SalesDelegateView, Integer> invInventoryId;
    public static volatile SingularAttribute<SalesDelegateView, BigDecimal> cash;
    public static volatile SingularAttribute<SalesDelegateView, BigDecimal> postpone;

}
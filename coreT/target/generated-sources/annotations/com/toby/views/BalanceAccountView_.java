package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(BalanceAccountView.class)
public class BalanceAccountView_ { 

    public static volatile SingularAttribute<BalanceAccountView, Integer> accountId;
    public static volatile SingularAttribute<BalanceAccountView, Integer> accNumber;
    public static volatile SingularAttribute<BalanceAccountView, BigDecimal> balance;
    public static volatile SingularAttribute<BalanceAccountView, Integer> costCenter;
    public static volatile SingularAttribute<BalanceAccountView, Integer> rowNum;
    public static volatile SingularAttribute<BalanceAccountView, Date> generalDate;
    public static volatile SingularAttribute<BalanceAccountView, Integer> adminUnit;

}
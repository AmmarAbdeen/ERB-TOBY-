package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:57")
@StaticMetamodel(BankBalanceView.class)
public class BankBalanceView_ { 

    public static volatile SingularAttribute<BankBalanceView, Date> date;
    public static volatile SingularAttribute<BankBalanceView, Integer> branchId;
    public static volatile SingularAttribute<BankBalanceView, Integer> accountIdFrom;
    public static volatile SingularAttribute<BankBalanceView, BigDecimal> valueLocal;
    public static volatile SingularAttribute<BankBalanceView, String> bankNameTo;
    public static volatile SingularAttribute<BankBalanceView, String> remark;
    public static volatile SingularAttribute<BankBalanceView, String> bankNameFrom;
    public static volatile SingularAttribute<BankBalanceView, Integer> transactionType;
    public static volatile SingularAttribute<BankBalanceView, String> serial;
    public static volatile SingularAttribute<BankBalanceView, Integer> rowNum;
    public static volatile SingularAttribute<BankBalanceView, Integer> accountIdTo;
    public static volatile SingularAttribute<BankBalanceView, Integer> bankIdTo;
    public static volatile SingularAttribute<BankBalanceView, BigDecimal> glValue;
    public static volatile SingularAttribute<BankBalanceView, Integer> bankIdFrom;
    public static volatile SingularAttribute<BankBalanceView, Integer> glYear;

}
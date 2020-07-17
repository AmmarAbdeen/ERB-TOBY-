package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(BankTransactionView.class)
public class BankTransactionView_ { 

    public static volatile SingularAttribute<BankTransactionView, Date> date;
    public static volatile SingularAttribute<BankTransactionView, Integer> branchId;
    public static volatile SingularAttribute<BankTransactionView, BigDecimal> valueLocal;
    public static volatile SingularAttribute<BankTransactionView, Integer> rowNum;
    public static volatile SingularAttribute<BankTransactionView, String> nameBankFrom;
    public static volatile SingularAttribute<BankTransactionView, Integer> bankIdTo;
    public static volatile SingularAttribute<BankTransactionView, BigDecimal> glValue;
    public static volatile SingularAttribute<BankTransactionView, Integer> bankIdFrom;
    public static volatile SingularAttribute<BankTransactionView, String> remark;
    public static volatile SingularAttribute<BankTransactionView, String> nameBankTo;

}
package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InstTransactionView.class)
public class InstTransactionView_ { 

    public static volatile SingularAttribute<InstTransactionView, Integer> instDetailId;
    public static volatile SingularAttribute<InstTransactionView, Integer> serial;
    public static volatile SingularAttribute<InstTransactionView, Integer> rowNum;
    public static volatile SingularAttribute<InstTransactionView, Date> dueDate;
    public static volatile SingularAttribute<InstTransactionView, Integer> instContractId;
    public static volatile SingularAttribute<InstTransactionView, BigDecimal> paidValue;
    public static volatile SingularAttribute<InstTransactionView, BigDecimal> installmentValue;
    public static volatile SingularAttribute<InstTransactionView, BigDecimal> remaining;

}
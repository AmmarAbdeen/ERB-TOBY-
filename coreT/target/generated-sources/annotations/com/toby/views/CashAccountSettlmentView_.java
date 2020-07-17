package com.toby.views;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(CashAccountSettlmentView.class)
public class CashAccountSettlmentView_ { 

    public static volatile SingularAttribute<CashAccountSettlmentView, Date> date;
    public static volatile SingularAttribute<CashAccountSettlmentView, Integer> branchId;
    public static volatile SingularAttribute<CashAccountSettlmentView, Integer> ordered;
    public static volatile SingularAttribute<CashAccountSettlmentView, Integer> banktransactionId;
    public static volatile SingularAttribute<CashAccountSettlmentView, String> code;
    public static volatile SingularAttribute<CashAccountSettlmentView, BigDecimal> valueLocal;
    public static volatile SingularAttribute<CashAccountSettlmentView, String> typeName;
    public static volatile SingularAttribute<CashAccountSettlmentView, String> remark;
    public static volatile SingularAttribute<CashAccountSettlmentView, Integer> type;
    public static volatile SingularAttribute<CashAccountSettlmentView, Integer> paymentType;
    public static volatile SingularAttribute<CashAccountSettlmentView, Integer> companyId;
    public static volatile SingularAttribute<CashAccountSettlmentView, Integer> bankId;
    public static volatile SingularAttribute<CashAccountSettlmentView, String> currencyName;
    public static volatile SingularAttribute<CashAccountSettlmentView, BigInteger> serial;
    public static volatile SingularAttribute<CashAccountSettlmentView, String> createdBy;
    public static volatile SingularAttribute<CashAccountSettlmentView, Integer> rowNum;
    public static volatile SingularAttribute<CashAccountSettlmentView, String> name;
    public static volatile SingularAttribute<CashAccountSettlmentView, Integer> typeId;
    public static volatile SingularAttribute<CashAccountSettlmentView, BigDecimal> value;
    public static volatile SingularAttribute<CashAccountSettlmentView, String> currencyCode;
    public static volatile SingularAttribute<CashAccountSettlmentView, Integer> status;
    public static volatile SingularAttribute<CashAccountSettlmentView, String> remark2;

}
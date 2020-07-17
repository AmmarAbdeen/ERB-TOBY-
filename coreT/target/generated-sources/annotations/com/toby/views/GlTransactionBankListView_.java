package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(GlTransactionBankListView.class)
public class GlTransactionBankListView_ { 

    public static volatile SingularAttribute<GlTransactionBankListView, Date> date;
    public static volatile SingularAttribute<GlTransactionBankListView, Integer> branchId;
    public static volatile SingularAttribute<GlTransactionBankListView, Integer> bankToId;
    public static volatile SingularAttribute<GlTransactionBankListView, BigDecimal> valueLocal;
    public static volatile SingularAttribute<GlTransactionBankListView, String> bankFromCode;
    public static volatile SingularAttribute<GlTransactionBankListView, String> bankFromName;
    public static volatile SingularAttribute<GlTransactionBankListView, String> remark;
    public static volatile SingularAttribute<GlTransactionBankListView, Integer> paymentType;
    public static volatile SingularAttribute<GlTransactionBankListView, Integer> transactionType;
    public static volatile SingularAttribute<GlTransactionBankListView, Integer> bankFromId;
    public static volatile SingularAttribute<GlTransactionBankListView, Integer> postFlag;
    public static volatile SingularAttribute<GlTransactionBankListView, Integer> serial;
    public static volatile SingularAttribute<GlTransactionBankListView, BigDecimal> rate;
    public static volatile SingularAttribute<GlTransactionBankListView, Integer> rowNum;
    public static volatile SingularAttribute<GlTransactionBankListView, Integer> id;
    public static volatile SingularAttribute<GlTransactionBankListView, String> bankToName;
    public static volatile SingularAttribute<GlTransactionBankListView, String> bankToCode;

}
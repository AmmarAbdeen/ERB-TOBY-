package com.toby.views;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(MonthlyAccountTransactionView.class)
public class MonthlyAccountTransactionView_ { 

    public static volatile SingularAttribute<MonthlyAccountTransactionView, Integer> glBranchId;
    public static volatile SingularAttribute<MonthlyAccountTransactionView, Integer> adminUnitId;
    public static volatile SingularAttribute<MonthlyAccountTransactionView, String> monthDate;
    public static volatile SingularAttribute<MonthlyAccountTransactionView, String> glAccountName;
    public static volatile SingularAttribute<MonthlyAccountTransactionView, Integer> costCenterId;
    public static volatile SingularAttribute<MonthlyAccountTransactionView, Integer> rowNum;
    public static volatile SingularAttribute<MonthlyAccountTransactionView, Integer> glaccountId;
    public static volatile SingularAttribute<MonthlyAccountTransactionView, String> accClass;
    public static volatile SingularAttribute<MonthlyAccountTransactionView, BigDecimal> debitAmount;
    public static volatile SingularAttribute<MonthlyAccountTransactionView, Integer> generalJournalId;
    public static volatile SingularAttribute<MonthlyAccountTransactionView, BigDecimal> creditAmount;
    public static volatile SingularAttribute<MonthlyAccountTransactionView, Integer> glAccountCode;

}
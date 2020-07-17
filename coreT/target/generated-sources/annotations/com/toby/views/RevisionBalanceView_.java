package com.toby.views;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(RevisionBalanceView.class)
public class RevisionBalanceView_ { 

    public static volatile SingularAttribute<RevisionBalanceView, String> date;
    public static volatile SingularAttribute<RevisionBalanceView, Integer> adminUnitId;
    public static volatile SingularAttribute<RevisionBalanceView, Integer> glAdminUnitCode;
    public static volatile SingularAttribute<RevisionBalanceView, Integer> glAccountNumber;
    public static volatile SingularAttribute<RevisionBalanceView, String> glAccountName;
    public static volatile SingularAttribute<RevisionBalanceView, String> glAdminUnitName;
    public static volatile SingularAttribute<RevisionBalanceView, Integer> rowNum;
    public static volatile SingularAttribute<RevisionBalanceView, Integer> glaccountId;
    public static volatile SingularAttribute<RevisionBalanceView, BigDecimal> debitAmount;
    public static volatile SingularAttribute<RevisionBalanceView, Integer> levelAcc;
    public static volatile SingularAttribute<RevisionBalanceView, BigDecimal> creditAmount;

}
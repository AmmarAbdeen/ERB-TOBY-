package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(ComplexRevisionBalance.class)
public class ComplexRevisionBalance_ { 

    public static volatile SingularAttribute<ComplexRevisionBalance, Date> date;
    public static volatile SingularAttribute<ComplexRevisionBalance, Integer> glBranchId;
    public static volatile SingularAttribute<ComplexRevisionBalance, String> glAccountName;
    public static volatile SingularAttribute<ComplexRevisionBalance, Integer> glAccountNumber;
    public static volatile SingularAttribute<ComplexRevisionBalance, Integer> glCostCenterCode;
    public static volatile SingularAttribute<ComplexRevisionBalance, Integer> level;
    public static volatile SingularAttribute<ComplexRevisionBalance, Integer> glAccountId;
    public static volatile SingularAttribute<ComplexRevisionBalance, Integer> glAdminUnitId;
    public static volatile SingularAttribute<ComplexRevisionBalance, String> glCostCenterName;
    public static volatile SingularAttribute<ComplexRevisionBalance, Integer> glAdminUnitCode;
    public static volatile SingularAttribute<ComplexRevisionBalance, String> accountClass;
    public static volatile SingularAttribute<ComplexRevisionBalance, Integer> glCostCenterId;
    public static volatile SingularAttribute<ComplexRevisionBalance, String> glAdminUnitName;
    public static volatile SingularAttribute<ComplexRevisionBalance, Integer> rowNum;
    public static volatile SingularAttribute<ComplexRevisionBalance, BigDecimal> debit;
    public static volatile SingularAttribute<ComplexRevisionBalance, BigDecimal> credit;

}
package com.toby.entity;

import com.toby.define.AccountClassEnum;
import com.toby.define.AccountGroupEnum;
import com.toby.define.AdminUnitDependantEnum;
import com.toby.define.AssisstantAccountDependantEnum;
import com.toby.define.CostCenterDependantEnum;
import com.toby.entity.Branch;
import com.toby.entity.Currency;
import com.toby.entity.GeneralBudgetGuide;
import com.toby.entity.GlAccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(GlAccount.class)
public class GlAccount_ extends BaseEntity_ {

    public static volatile SingularAttribute<GlAccount, Branch> branchId;
    public static volatile SingularAttribute<GlAccount, Integer> accNumber;
    public static volatile SingularAttribute<GlAccount, CostCenterDependantEnum> costCenter;
    public static volatile SingularAttribute<GlAccount, GeneralBudgetGuide> generalBudgetIdCredit;
    public static volatile SingularAttribute<GlAccount, GeneralBudgetGuide> generalBudgetIdDebit;
    public static volatile SingularAttribute<GlAccount, Integer> levelAcc;
    public static volatile SingularAttribute<GlAccount, Integer> type;
    public static volatile ListAttribute<GlAccount, GlAccount> childAccounts;
    public static volatile SingularAttribute<GlAccount, GlAccount> parentAcc;
    public static volatile SingularAttribute<GlAccount, AdminUnitDependantEnum> administrativeUnit;
    public static volatile SingularAttribute<GlAccount, GeneralBudgetGuide> generalBudgetId;
    public static volatile SingularAttribute<GlAccount, AccountGroupEnum> accGroup;
    public static volatile SingularAttribute<GlAccount, String> name;
    public static volatile SingularAttribute<GlAccount, AccountClassEnum> accClass;
    public static volatile SingularAttribute<GlAccount, Integer> shotCode;
    public static volatile SingularAttribute<GlAccount, AssisstantAccountDependantEnum> assistantAcc;
    public static volatile SingularAttribute<GlAccount, Currency> currencyId;
    public static volatile SingularAttribute<GlAccount, Boolean> status;

}
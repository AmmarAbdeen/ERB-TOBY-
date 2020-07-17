package com.toby.entity;

import com.toby.define.GroupItemEnum;
import com.toby.entity.Branch;
import com.toby.entity.GlAccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(GeneralBudgetGuide.class)
public class GeneralBudgetGuide_ extends BaseEntity_ {

    public static volatile CollectionAttribute<GeneralBudgetGuide, GlAccount> glAccountCollection;
    public static volatile SingularAttribute<GeneralBudgetGuide, String> number;
    public static volatile SingularAttribute<GeneralBudgetGuide, Branch> branchId;
    public static volatile SingularAttribute<GeneralBudgetGuide, String> nameAr;
    public static volatile CollectionAttribute<GeneralBudgetGuide, GlAccount> glAccountCollection1;
    public static volatile SingularAttribute<GeneralBudgetGuide, GroupItemEnum> accGroup;
    public static volatile SingularAttribute<GeneralBudgetGuide, GlAccount> accountGroup;
    public static volatile SingularAttribute<GeneralBudgetGuide, String> nameEn;

}
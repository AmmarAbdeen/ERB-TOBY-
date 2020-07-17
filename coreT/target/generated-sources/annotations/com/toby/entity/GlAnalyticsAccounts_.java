package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.GlAccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(GlAnalyticsAccounts.class)
public class GlAnalyticsAccounts_ extends BaseEntity_ {

    public static volatile SingularAttribute<GlAnalyticsAccounts, GlAccount> accountId;
    public static volatile SingularAttribute<GlAnalyticsAccounts, Branch> branchId;
    public static volatile SingularAttribute<GlAnalyticsAccounts, String> code;
    public static volatile SingularAttribute<GlAnalyticsAccounts, Integer> type;

}
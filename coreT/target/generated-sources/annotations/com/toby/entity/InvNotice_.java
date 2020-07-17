package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.GlAccount;
import com.toby.entity.InvOrganizationSite;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvNotice.class)
public class InvNotice_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvNotice, Date> date;
    public static volatile SingularAttribute<InvNotice, Branch> branchId;
    public static volatile SingularAttribute<InvNotice, Integer> serial;
    public static volatile SingularAttribute<InvNotice, InvOrganizationSite> organizationSiteId;
    public static volatile SingularAttribute<InvNotice, GlAccount> contrastAccountId;
    public static volatile SingularAttribute<InvNotice, String> remark;
    public static volatile SingularAttribute<InvNotice, Integer> type;
    public static volatile SingularAttribute<InvNotice, BigDecimal> value;

}
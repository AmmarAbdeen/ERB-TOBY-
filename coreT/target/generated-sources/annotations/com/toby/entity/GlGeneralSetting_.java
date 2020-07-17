package com.toby.entity;

import com.toby.entity.Branch;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(GlGeneralSetting.class)
public class GlGeneralSetting_ extends BaseEntity_ {

    public static volatile SingularAttribute<GlGeneralSetting, Boolean> voucherUpdate;
    public static volatile SingularAttribute<GlGeneralSetting, Branch> branchId;
    public static volatile SingularAttribute<GlGeneralSetting, Boolean> voucherBalance;
    public static volatile SingularAttribute<GlGeneralSetting, Boolean> summaryAppear;

}
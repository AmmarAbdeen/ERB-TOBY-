package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.GlAccount;
import com.toby.entity.GlYear;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:57")
@StaticMetamodel(GlAnnualClosing.class)
public class GlAnnualClosing_ extends BaseEntity_ {

    public static volatile SingularAttribute<GlAnnualClosing, Branch> branchId;
    public static volatile SingularAttribute<GlAnnualClosing, GlAccount> glAccountId;
    public static volatile SingularAttribute<GlAnnualClosing, GlYear> glYearId;
    public static volatile SingularAttribute<GlAnnualClosing, BigDecimal> ratio;

}
package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.CloseAndSaveMonth;
import com.toby.entity.TobyUserYear;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(GlYear.class)
public class GlYear_ extends BaseEntity_ {

    public static volatile SingularAttribute<GlYear, Branch> branchId;
    public static volatile SingularAttribute<GlYear, Boolean> isDefault;
    public static volatile CollectionAttribute<GlYear, CloseAndSaveMonth> closeAndSaveMonthCollection;
    public static volatile SingularAttribute<GlYear, Integer> year;
    public static volatile SingularAttribute<GlYear, Date> dateTo;
    public static volatile SingularAttribute<GlYear, String> name;
    public static volatile SingularAttribute<GlYear, Integer> openning;
    public static volatile CollectionAttribute<GlYear, TobyUserYear> tobyUserYearCollection;
    public static volatile SingularAttribute<GlYear, Date> dateFrom;

}
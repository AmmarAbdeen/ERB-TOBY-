package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.GlYear;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(CloseAndSaveMonth.class)
public class CloseAndSaveMonth_ extends BaseEntity_ {

    public static volatile SingularAttribute<CloseAndSaveMonth, Branch> branchId;
    public static volatile SingularAttribute<CloseAndSaveMonth, GlYear> year;
    public static volatile SingularAttribute<CloseAndSaveMonth, Integer> monthNumber;
    public static volatile SingularAttribute<CloseAndSaveMonth, String> monthName;
    public static volatile SingularAttribute<CloseAndSaveMonth, Boolean> status;

}
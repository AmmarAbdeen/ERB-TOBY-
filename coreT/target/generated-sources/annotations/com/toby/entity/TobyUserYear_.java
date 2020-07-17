package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.GlYear;
import com.toby.entity.TobyUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(TobyUserYear.class)
public class TobyUserYear_ extends BaseEntity_ {

    public static volatile SingularAttribute<TobyUserYear, Branch> branchId;
    public static volatile SingularAttribute<TobyUserYear, TobyUser> userId;
    public static volatile SingularAttribute<TobyUserYear, GlYear> yearId;

}
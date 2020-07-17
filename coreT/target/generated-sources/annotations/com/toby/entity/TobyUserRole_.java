package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.TobyRole;
import com.toby.entity.TobyUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(TobyUserRole.class)
public class TobyUserRole_ extends BaseEntity_ {

    public static volatile SingularAttribute<TobyUserRole, Branch> branchId;
    public static volatile SingularAttribute<TobyUserRole, TobyRole> roleId;
    public static volatile SingularAttribute<TobyUserRole, TobyUser> userId;

}
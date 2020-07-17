package com.toby.entity;

import com.toby.entity.Branch;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(TobyError.class)
public class TobyError_ extends BaseEntity_ {

    public static volatile SingularAttribute<TobyError, Branch> branchId;
    public static volatile SingularAttribute<TobyError, String> method;
    public static volatile SingularAttribute<TobyError, String> className;
    public static volatile SingularAttribute<TobyError, String> error;

}
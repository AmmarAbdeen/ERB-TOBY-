package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.Symbol;
import com.toby.entity.TobyUserYear;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(TobyUser.class)
public class TobyUser_ extends BaseEntity_ {

    public static volatile CollectionAttribute<TobyUser, TobyUserYear> tobyUserYearCollection1;
    public static volatile SingularAttribute<TobyUser, Branch> branchId;
    public static volatile SingularAttribute<TobyUser, String> password;
    public static volatile CollectionAttribute<TobyUser, TobyUserYear> tobyUserYearCollection2;
    public static volatile SingularAttribute<TobyUser, String> name;
    public static volatile CollectionAttribute<TobyUser, TobyUserYear> tobyUserYearCollection;
    public static volatile SingularAttribute<TobyUser, Symbol> lang;
    public static volatile SingularAttribute<TobyUser, String> userCode;
    public static volatile SingularAttribute<TobyUser, Integer> master;

}
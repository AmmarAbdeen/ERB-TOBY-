package com.toby.entity;

import com.toby.entity.TobyRole;
import com.toby.entity.TobyScreen;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(TobyPrivilege.class)
public class TobyPrivilege_ extends BaseEntity_ {

    public static volatile SingularAttribute<TobyPrivilege, TobyScreen> screenId;
    public static volatile SingularAttribute<TobyPrivilege, Boolean> add;
    public static volatile SingularAttribute<TobyPrivilege, Boolean> view;
    public static volatile SingularAttribute<TobyPrivilege, Boolean> edit;
    public static volatile SingularAttribute<TobyPrivilege, TobyRole> roleId;
    public static volatile SingularAttribute<TobyPrivilege, Boolean> delete;

}
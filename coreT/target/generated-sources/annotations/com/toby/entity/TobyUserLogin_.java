package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.TobyUser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(TobyUserLogin.class)
public class TobyUserLogin_ extends BaseEntity_ {

    public static volatile SingularAttribute<TobyUserLogin, String> hostName;
    public static volatile SingularAttribute<TobyUserLogin, Branch> branchId;
    public static volatile SingularAttribute<TobyUserLogin, String> macId;
    public static volatile SingularAttribute<TobyUserLogin, Date> dateLogin;
    public static volatile SingularAttribute<TobyUserLogin, String> ipAddress;
    public static volatile SingularAttribute<TobyUserLogin, String> browserName;
    public static volatile SingularAttribute<TobyUserLogin, Integer> state;
    public static volatile SingularAttribute<TobyUserLogin, String> operatingSystem;
    public static volatile SingularAttribute<TobyUserLogin, TobyUser> userId;

}
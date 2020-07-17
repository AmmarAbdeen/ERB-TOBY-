package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.GlBank;
import com.toby.entity.TobyUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(TobyUserBank.class)
public class TobyUserBank_ extends BaseEntity_ {

    public static volatile SingularAttribute<TobyUserBank, Branch> branchId;
    public static volatile SingularAttribute<TobyUserBank, GlBank> bankId;
    public static volatile SingularAttribute<TobyUserBank, TobyUser> userId;

}
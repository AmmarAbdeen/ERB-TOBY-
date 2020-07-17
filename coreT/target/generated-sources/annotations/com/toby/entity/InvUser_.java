package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvInventory;
import com.toby.entity.TobyUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvUser.class)
public class InvUser_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvUser, Branch> branchId;
    public static volatile SingularAttribute<InvUser, InvInventory> invId;
    public static volatile SingularAttribute<InvUser, TobyUser> userId;

}
package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvGroup;
import com.toby.entity.InvItem;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvGroup.class)
public class InvGroup_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvGroup, Branch> branchId;
    public static volatile SingularAttribute<InvGroup, InvGroup> parent;
    public static volatile SingularAttribute<InvGroup, Integer> code;
    public static volatile SingularAttribute<InvGroup, Integer> isDeleted;
    public static volatile SingularAttribute<InvGroup, Integer> level;
    public static volatile CollectionAttribute<InvGroup, InvItem> invItemCollection;
    public static volatile SingularAttribute<InvGroup, String> name;
    public static volatile CollectionAttribute<InvGroup, InvGroup> invGroupCollection;

}
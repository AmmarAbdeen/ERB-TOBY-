package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.GlAdminUnit;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(GlAdminUnit.class)
public class GlAdminUnit_ extends BaseEntity_ {

    public static volatile SingularAttribute<GlAdminUnit, Branch> branchId;
    public static volatile SingularAttribute<GlAdminUnit, GlAdminUnit> parent;
    public static volatile SingularAttribute<GlAdminUnit, Integer> code;
    public static volatile SingularAttribute<GlAdminUnit, Integer> level;
    public static volatile ListAttribute<GlAdminUnit, GlAdminUnit> AdminUnitChilds;
    public static volatile SingularAttribute<GlAdminUnit, String> name;
    public static volatile SingularAttribute<GlAdminUnit, Boolean> active;
    public static volatile SingularAttribute<GlAdminUnit, Integer> shortCode;

}
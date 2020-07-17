package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.CostCenter;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(CostCenter.class)
public class CostCenter_ extends BaseEntity_ {

    public static volatile SingularAttribute<CostCenter, CostCenter> parent;
    public static volatile SingularAttribute<CostCenter, Branch> branchId;
    public static volatile SingularAttribute<CostCenter, Integer> code;
    public static volatile SingularAttribute<CostCenter, Integer> level;
    public static volatile ListAttribute<CostCenter, CostCenter> childNodes;
    public static volatile SingularAttribute<CostCenter, String> name;
    public static volatile SingularAttribute<CostCenter, Boolean> active;
    public static volatile SingularAttribute<CostCenter, Integer> shortCode;

}
package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvItem;
import com.toby.entity.ProProductionStages;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(ProItemProductionStages.class)
public class ProItemProductionStages_ extends BaseEntity_ {

    public static volatile SingularAttribute<ProItemProductionStages, Branch> branchId;
    public static volatile SingularAttribute<ProItemProductionStages, ProProductionStages> proProductionStagesId;
    public static volatile SingularAttribute<ProItemProductionStages, Integer> serial;
    public static volatile SingularAttribute<ProItemProductionStages, InvItem> invItemId;

}
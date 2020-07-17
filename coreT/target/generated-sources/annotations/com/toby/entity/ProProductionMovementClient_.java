package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvOrganizationSite;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(ProProductionMovementClient.class)
public class ProProductionMovementClient_ extends BaseEntity_ {

    public static volatile SingularAttribute<ProProductionMovementClient, Date> date;
    public static volatile SingularAttribute<ProProductionMovementClient, Branch> branchId;
    public static volatile SingularAttribute<ProProductionMovementClient, InvOrganizationSite> invOrganizationSiteId;
    public static volatile SingularAttribute<ProProductionMovementClient, String> time;

}
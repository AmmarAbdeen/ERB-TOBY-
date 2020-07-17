package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvInventory;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.ProProductMovement;
import com.toby.entity.TobyCompany;
import com.toby.entity.TobyUser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(ProProductMovement.class)
public class ProProductMovement_ { 

    public static volatile SingularAttribute<ProProductMovement, Date> date;
    public static volatile SingularAttribute<ProProductMovement, Branch> branchId;
    public static volatile SingularAttribute<ProProductMovement, ProProductMovement> parent;
    public static volatile SingularAttribute<ProProductMovement, String> remark;
    public static volatile SingularAttribute<ProProductMovement, Integer> type;
    public static volatile SingularAttribute<ProProductMovement, Date> creationDate;
    public static volatile SingularAttribute<ProProductMovement, InvInventory> invGalaryId;
    public static volatile SingularAttribute<ProProductMovement, Date> modificationDate;
    public static volatile SingularAttribute<ProProductMovement, TobyCompany> companyId;
    public static volatile SingularAttribute<ProProductMovement, Integer> serial;
    public static volatile SingularAttribute<ProProductMovement, TobyUser> createdBy;
    public static volatile SingularAttribute<ProProductMovement, InvOrganizationSite> invOrganizationSiteId;
    public static volatile SingularAttribute<ProProductMovement, InvInventory> invInventoryId;
    public static volatile SingularAttribute<ProProductMovement, InvOrganizationSite> client;
    public static volatile SingularAttribute<ProProductMovement, TobyUser> modifiedBy;
    public static volatile CollectionAttribute<ProProductMovement, ProProductMovement> proProductMovementCollection;
    public static volatile SingularAttribute<ProProductMovement, Integer> id;
    public static volatile SingularAttribute<ProProductMovement, Date> time;

}
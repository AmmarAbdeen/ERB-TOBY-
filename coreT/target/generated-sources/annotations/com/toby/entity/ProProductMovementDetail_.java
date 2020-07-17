package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvPurchaseInvoice;
import com.toby.entity.ProProductMovement;
import com.toby.entity.TobyCompany;
import com.toby.entity.TobyUser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(ProProductMovementDetail.class)
public class ProProductMovementDetail_ { 

    public static volatile SingularAttribute<ProProductMovementDetail, Branch> branchId;
    public static volatile SingularAttribute<ProProductMovementDetail, Date> modificationDate;
    public static volatile SingularAttribute<ProProductMovementDetail, TobyCompany> companyId;
    public static volatile SingularAttribute<ProProductMovementDetail, Integer> serial;
    public static volatile SingularAttribute<ProProductMovementDetail, TobyUser> createdBy;
    public static volatile SingularAttribute<ProProductMovementDetail, InvPurchaseInvoice> invPurchesInvoiceId;
    public static volatile SingularAttribute<ProProductMovementDetail, ProProductMovement> proProductionDeliveryId;
    public static volatile SingularAttribute<ProProductMovementDetail, TobyUser> modifiedBy;
    public static volatile SingularAttribute<ProProductMovementDetail, Integer> id;
    public static volatile SingularAttribute<ProProductMovementDetail, Date> creationDate;

}
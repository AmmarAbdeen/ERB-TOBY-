package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvInventory;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.InvReservationDetail;
import com.toby.entity.InventoryDelegator;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvReservation.class)
public class InvReservation_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvReservation, Branch> branchId;
    public static volatile SingularAttribute<InvReservation, String> address;
    public static volatile CollectionAttribute<InvReservation, InvReservationDetail> invReservationDetailCollection;
    public static volatile SingularAttribute<InvReservation, Integer> serial;
    public static volatile SingularAttribute<InvReservation, Date> endDate;
    public static volatile SingularAttribute<InvReservation, InvInventory> invId;
    public static volatile SingularAttribute<InvReservation, InvOrganizationSite> siteId;
    public static volatile SingularAttribute<InvReservation, Date> reservationDate;
    public static volatile SingularAttribute<InvReservation, InventoryDelegator> delegatorId;
    public static volatile SingularAttribute<InvReservation, String> remarks;
    public static volatile SingularAttribute<InvReservation, Integer> status;
    public static volatile SingularAttribute<InvReservation, InvOrganizationSite> siteIdMain;

}
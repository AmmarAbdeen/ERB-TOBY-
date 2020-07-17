package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvItem;
import com.toby.entity.InvReservation;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvReservationDetail.class)
public class InvReservationDetail_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvReservationDetail, Branch> branchId;
    public static volatile SingularAttribute<InvReservationDetail, BigDecimal> amount;
    public static volatile SingularAttribute<InvReservationDetail, String> itemBarcode;
    public static volatile SingularAttribute<InvReservationDetail, BigDecimal> discount;
    public static volatile SingularAttribute<InvReservationDetail, BigDecimal> finalQuantity;
    public static volatile SingularAttribute<InvReservationDetail, InvItem> itemId;
    public static volatile SingularAttribute<InvReservationDetail, BigDecimal> screwing;
    public static volatile SingularAttribute<InvReservationDetail, BigDecimal> total;
    public static volatile SingularAttribute<InvReservationDetail, InvReservation> reservationId;
    public static volatile SingularAttribute<InvReservationDetail, Integer> serial;
    public static volatile SingularAttribute<InvReservationDetail, BigDecimal> price;
    public static volatile SingularAttribute<InvReservationDetail, BigDecimal> adding;
    public static volatile SingularAttribute<InvReservationDetail, BigDecimal> net;
    public static volatile SingularAttribute<InvReservationDetail, Integer> status;

}
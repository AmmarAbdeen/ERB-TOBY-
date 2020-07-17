package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(HosClinicView.class)
public class HosClinicView_ { 

    public static volatile SingularAttribute<HosClinicView, String> clinicName;
    public static volatile SingularAttribute<HosClinicView, Date> date;
    public static volatile SingularAttribute<HosClinicView, Integer> branchId;
    public static volatile SingularAttribute<HosClinicView, Integer> hosInsuranceId;
    public static volatile SingularAttribute<HosClinicView, String> serviceCode;
    public static volatile SingularAttribute<HosClinicView, BigDecimal> bearRatio;
    public static volatile SingularAttribute<HosClinicView, String> serviceName;
    public static volatile SingularAttribute<HosClinicView, Integer> hosDoctorId;
    public static volatile SingularAttribute<HosClinicView, Integer> paymentType;
    public static volatile SingularAttribute<HosClinicView, BigDecimal> total;
    public static volatile SingularAttribute<HosClinicView, String> doctorName;
    public static volatile SingularAttribute<HosClinicView, Integer> hosClinicId;
    public static volatile SingularAttribute<HosClinicView, Integer> hosCheckUpId;
    public static volatile SingularAttribute<HosClinicView, String> hosPatientName;
    public static volatile SingularAttribute<HosClinicView, Integer> rowNum;
    public static volatile SingularAttribute<HosClinicView, String> doctorCode;
    public static volatile SingularAttribute<HosClinicView, String> hosInsuranceName;
    public static volatile SingularAttribute<HosClinicView, String> clinicCode;
    public static volatile SingularAttribute<HosClinicView, Integer> hosPatientId;
    public static volatile SingularAttribute<HosClinicView, BigDecimal> value;
    public static volatile SingularAttribute<HosClinicView, BigDecimal> discountRatio;

}
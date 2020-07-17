package com.toby.views;

import com.toby.entity.BaseEntity_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(RscTenderContractView.class)
public class RscTenderContractView_ extends BaseEntity_ {

    public static volatile SingularAttribute<RscTenderContractView, Date> date;
    public static volatile SingularAttribute<RscTenderContractView, Integer> projectManager;
    public static volatile SingularAttribute<RscTenderContractView, Integer> branchId;
    public static volatile SingularAttribute<RscTenderContractView, String> docNumber;
    public static volatile SingularAttribute<RscTenderContractView, String> projectManagerName;
    public static volatile SingularAttribute<RscTenderContractView, String> customerCode;
    public static volatile SingularAttribute<RscTenderContractView, String> remark;
    public static volatile SingularAttribute<RscTenderContractView, Integer> profissionalBussinessConsultant;
    public static volatile SingularAttribute<RscTenderContractView, String> customerName;
    public static volatile SingularAttribute<RscTenderContractView, String> generalConsultantName;
    public static volatile SingularAttribute<RscTenderContractView, Integer> generalConsultant;
    public static volatile SingularAttribute<RscTenderContractView, String> profissionalBussinessConsultantName;
    public static volatile SingularAttribute<RscTenderContractView, Integer> serial;
    public static volatile SingularAttribute<RscTenderContractView, Integer> customerId;
    public static volatile SingularAttribute<RscTenderContractView, Integer> status;

}
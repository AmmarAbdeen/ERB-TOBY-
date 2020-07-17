package com.toby.entity;

import com.toby.entity.Branch;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(ProProductionStages.class)
public class ProProductionStages_ extends BaseEntity_ {

    public static volatile SingularAttribute<ProProductionStages, String> hostName;
    public static volatile SingularAttribute<ProProductionStages, Branch> branchId;
    public static volatile SingularAttribute<ProProductionStages, Integer> serial;
    public static volatile SingularAttribute<ProProductionStages, BigDecimal> price;
    public static volatile SingularAttribute<ProProductionStages, String> name;
    public static volatile SingularAttribute<ProProductionStages, String> nameEn;
    public static volatile SingularAttribute<ProProductionStages, String> nameIn;
    public static volatile SingularAttribute<ProProductionStages, Integer> typeStage;

}
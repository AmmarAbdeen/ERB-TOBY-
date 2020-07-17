package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(ProductionReport.class)
public class ProductionReport_ { 

    public static volatile SingularAttribute<ProductionReport, Date> date;
    public static volatile SingularAttribute<ProductionReport, BigDecimal> total;
    public static volatile SingularAttribute<ProductionReport, BigDecimal> quantity;
    public static volatile SingularAttribute<ProductionReport, String> stageName;
    public static volatile SingularAttribute<ProductionReport, Integer> productionStage;
    public static volatile SingularAttribute<ProductionReport, Integer> invoiceSerial;
    public static volatile SingularAttribute<ProductionReport, BigDecimal> price;
    public static volatile SingularAttribute<ProductionReport, Integer> rowNum;
    public static volatile SingularAttribute<ProductionReport, String> empName;
    public static volatile SingularAttribute<ProductionReport, Integer> invoiceId;

}
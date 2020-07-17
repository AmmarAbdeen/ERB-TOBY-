package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:57")
@StaticMetamodel(InvoiceProductionStagesReport.class)
public class InvoiceProductionStagesReport_ { 

    public static volatile SingularAttribute<InvoiceProductionStagesReport, Date> date;
    public static volatile SingularAttribute<InvoiceProductionStagesReport, BigDecimal> quantity;
    public static volatile SingularAttribute<InvoiceProductionStagesReport, String> stage;
    public static volatile SingularAttribute<InvoiceProductionStagesReport, String> clientName;
    public static volatile SingularAttribute<InvoiceProductionStagesReport, Integer> rowNum;
    public static volatile SingularAttribute<InvoiceProductionStagesReport, String> empName;
    public static volatile SingularAttribute<InvoiceProductionStagesReport, String> client;
    public static volatile SingularAttribute<InvoiceProductionStagesReport, Integer> invoice;
    public static volatile SingularAttribute<InvoiceProductionStagesReport, Integer> invInvoice;

}
package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvoiceDetailsReport.class)
public class InvoiceDetailsReport_ { 

    public static volatile SingularAttribute<InvoiceDetailsReport, Date> date;
    public static volatile SingularAttribute<InvoiceDetailsReport, Integer> branchId;
    public static volatile SingularAttribute<InvoiceDetailsReport, BigDecimal> quantity;
    public static volatile SingularAttribute<InvoiceDetailsReport, String> clientName;
    public static volatile SingularAttribute<InvoiceDetailsReport, Integer> rowNum;
    public static volatile SingularAttribute<InvoiceDetailsReport, String> client;
    public static volatile SingularAttribute<InvoiceDetailsReport, Integer> invoice;
    public static volatile SingularAttribute<InvoiceDetailsReport, String> branch;

}
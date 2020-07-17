package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(ReturnInvoiceView.class)
public class ReturnInvoiceView_ { 

    public static volatile SingularAttribute<ReturnInvoiceView, Date> date;
    public static volatile SingularAttribute<ReturnInvoiceView, String> supplierName;
    public static volatile SingularAttribute<ReturnInvoiceView, Integer> branchId;
    public static volatile SingularAttribute<ReturnInvoiceView, String> inventoryCode;
    public static volatile SingularAttribute<ReturnInvoiceView, String> remark;
    public static volatile SingularAttribute<ReturnInvoiceView, String> supplierCode;
    public static volatile SingularAttribute<ReturnInvoiceView, Boolean> type;
    public static volatile SingularAttribute<ReturnInvoiceView, Integer> generalJournalId;
    public static volatile SingularAttribute<ReturnInvoiceView, Integer> paymentType;
    public static volatile SingularAttribute<ReturnInvoiceView, Integer> companyId;
    public static volatile SingularAttribute<ReturnInvoiceView, Integer> postFlag;
    public static volatile SingularAttribute<ReturnInvoiceView, Integer> serial;
    public static volatile SingularAttribute<ReturnInvoiceView, Integer> invoiceSerial;
    public static volatile SingularAttribute<ReturnInvoiceView, String> inventoryName;
    public static volatile SingularAttribute<ReturnInvoiceView, Integer> id;
    public static volatile SingularAttribute<ReturnInvoiceView, BigDecimal> net;

}
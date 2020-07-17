package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(ProProductionTransactionOfInvoice.class)
public class ProProductionTransactionOfInvoice_ { 

    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, Date> date;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, Integer> invPurchaseInvoiceserial;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, BigDecimal> totalNumber;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, Integer> branchId;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, Integer> clothNumber;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, Integer> invPurchaseInvoiceId;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, String> itemCode;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, BigDecimal> numberExcute;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, BigDecimal> numberRemain;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, String> userName;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, Integer> userId;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, Integer> itemId;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, String> itemName;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, Integer> invoiceDetailId;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, String> productionLine;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, Integer> rowNum;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, String> productionStagesName;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, BigDecimal> productionStageCost;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, Integer> productionLineId;
    public static volatile SingularAttribute<ProProductionTransactionOfInvoice, Integer> productionStagesId;

}
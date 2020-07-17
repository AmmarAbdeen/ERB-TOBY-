package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(ProductionTransactionOfUser.class)
public class ProductionTransactionOfUser_ { 

    public static volatile SingularAttribute<ProductionTransactionOfUser, Date> date;
    public static volatile SingularAttribute<ProductionTransactionOfUser, Integer> invPurchaseInvoiceserial;
    public static volatile SingularAttribute<ProductionTransactionOfUser, BigDecimal> totalNumber;
    public static volatile SingularAttribute<ProductionTransactionOfUser, Integer> branchId;
    public static volatile SingularAttribute<ProductionTransactionOfUser, Integer> invPurchaseInvoiceId;
    public static volatile SingularAttribute<ProductionTransactionOfUser, String> itemCode;
    public static volatile SingularAttribute<ProductionTransactionOfUser, BigDecimal> numberExcute;
    public static volatile SingularAttribute<ProductionTransactionOfUser, BigDecimal> numberRemain;
    public static volatile SingularAttribute<ProductionTransactionOfUser, String> userName;
    public static volatile SingularAttribute<ProductionTransactionOfUser, Integer> userId;
    public static volatile SingularAttribute<ProductionTransactionOfUser, String> userCode;
    public static volatile SingularAttribute<ProductionTransactionOfUser, Integer> itemId;
    public static volatile SingularAttribute<ProductionTransactionOfUser, String> itemName;
    public static volatile SingularAttribute<ProductionTransactionOfUser, String> stageName;
    public static volatile SingularAttribute<ProductionTransactionOfUser, String> productionLine;
    public static volatile SingularAttribute<ProductionTransactionOfUser, BigDecimal> price;
    public static volatile SingularAttribute<ProductionTransactionOfUser, Integer> rowNum;
    public static volatile SingularAttribute<ProductionTransactionOfUser, BigDecimal> productionStageCost;
    public static volatile SingularAttribute<ProductionTransactionOfUser, Integer> productionLineId;
    public static volatile SingularAttribute<ProductionTransactionOfUser, Integer> productionStagesId;

}
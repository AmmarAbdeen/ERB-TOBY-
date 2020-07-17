package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvPurchaseInvoiceDetail;
import com.toby.entity.ProProductionStages;
import com.toby.entity.ProProductionTransaction;
import com.toby.entity.TobyCompany;
import com.toby.entity.TobyUser;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:57")
@StaticMetamodel(ProProductionItemsTransaction.class)
public class ProProductionItemsTransaction_ extends BaseEntity_ {

    public static volatile SingularAttribute<ProProductionItemsTransaction, Date> date;
    public static volatile SingularAttribute<ProProductionItemsTransaction, Branch> branchId;
    public static volatile SingularAttribute<ProProductionItemsTransaction, ProProductionStages> proProductionStagesId;
    public static volatile SingularAttribute<ProProductionItemsTransaction, InvPurchaseInvoiceDetail> invPurchaseInvoiceDetailId;
    public static volatile SingularAttribute<ProProductionItemsTransaction, ProProductionTransaction> proProductionTransactionId;
    public static volatile SingularAttribute<ProProductionItemsTransaction, Date> creationDate;
    public static volatile SingularAttribute<ProProductionItemsTransaction, BigDecimal> number;
    public static volatile SingularAttribute<ProProductionItemsTransaction, Date> modificationDate;
    public static volatile SingularAttribute<ProProductionItemsTransaction, TobyCompany> companyId;
    public static volatile SingularAttribute<ProProductionItemsTransaction, Integer> isDeleted;
    public static volatile SingularAttribute<ProProductionItemsTransaction, Integer> serial;
    public static volatile SingularAttribute<ProProductionItemsTransaction, TobyUser> createdBy;
    public static volatile SingularAttribute<ProProductionItemsTransaction, BigDecimal> productionStageCost;
    public static volatile SingularAttribute<ProProductionItemsTransaction, TobyUser> modifiedBy;
    public static volatile SingularAttribute<ProProductionItemsTransaction, Integer> id;
    public static volatile SingularAttribute<ProProductionItemsTransaction, Integer> status;

}
package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvPurchaseInvoice;
import com.toby.entity.ProProductionStages;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(ProProductionTransaction.class)
public class ProProductionTransaction_ extends BaseEntity_ {

    public static volatile SingularAttribute<ProProductionTransaction, ProProductionStages> proProductionId;
    public static volatile SingularAttribute<ProProductionTransaction, Branch> branchId;
    public static volatile SingularAttribute<ProProductionTransaction, String> inOrganizationSiteName;
    public static volatile SingularAttribute<ProProductionTransaction, InvPurchaseInvoice> invPurchaseInvoiceId;
    public static volatile SingularAttribute<ProProductionTransaction, Integer> status;

}
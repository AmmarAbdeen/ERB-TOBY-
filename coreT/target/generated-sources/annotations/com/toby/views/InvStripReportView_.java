package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(InvStripReportView.class)
public class InvStripReportView_ { 

    public static volatile SingularAttribute<InvStripReportView, Date> date;
    public static volatile SingularAttribute<InvStripReportView, Integer> branchId;
    public static volatile SingularAttribute<InvStripReportView, String> itemUnitName;
    public static volatile SingularAttribute<InvStripReportView, String> inventoryCode;
    public static volatile SingularAttribute<InvStripReportView, Integer> groupId;
    public static volatile SingularAttribute<InvStripReportView, String> itemCode;
    public static volatile SingularAttribute<InvStripReportView, String> itemBarcode;
    public static volatile SingularAttribute<InvStripReportView, Integer> itemId;
    public static volatile SingularAttribute<InvStripReportView, String> groupName;
    public static volatile SingularAttribute<InvStripReportView, String> itemName;
    public static volatile SingularAttribute<InvStripReportView, BigDecimal> balance;
    public static volatile SingularAttribute<InvStripReportView, BigDecimal> costAverage;
    public static volatile SingularAttribute<InvStripReportView, BigDecimal> qtyin;
    public static volatile SingularAttribute<InvStripReportView, BigDecimal> qtyout;
    public static volatile SingularAttribute<InvStripReportView, BigDecimal> balancevalue;
    public static volatile SingularAttribute<InvStripReportView, Integer> inventoryId;
    public static volatile SingularAttribute<InvStripReportView, Integer> id;
    public static volatile SingularAttribute<InvStripReportView, String> detailUnitName;

}
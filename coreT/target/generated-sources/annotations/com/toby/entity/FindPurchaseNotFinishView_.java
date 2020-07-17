package com.toby.entity;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(FindPurchaseNotFinishView.class)
public class FindPurchaseNotFinishView_ { 

    public static volatile SingularAttribute<FindPurchaseNotFinishView, Integer> itemId;
    public static volatile SingularAttribute<FindPurchaseNotFinishView, Integer> branchId;
    public static volatile SingularAttribute<FindPurchaseNotFinishView, String> itemName;
    public static volatile SingularAttribute<FindPurchaseNotFinishView, BigDecimal> quantity;
    public static volatile SingularAttribute<FindPurchaseNotFinishView, Integer> invinventoryId;
    public static volatile SingularAttribute<FindPurchaseNotFinishView, String> unitName;
    public static volatile SingularAttribute<FindPurchaseNotFinishView, String> itemCode;
    public static volatile SingularAttribute<FindPurchaseNotFinishView, Integer> itemUnit;
    public static volatile SingularAttribute<FindPurchaseNotFinishView, Integer> unitCode;
    public static volatile SingularAttribute<FindPurchaseNotFinishView, Integer> invoiceId;

}
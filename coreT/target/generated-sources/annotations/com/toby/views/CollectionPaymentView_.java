package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(CollectionPaymentView.class)
public class CollectionPaymentView_ { 

    public static volatile SingularAttribute<CollectionPaymentView, Integer> branchId;
    public static volatile SingularAttribute<CollectionPaymentView, String> codeCustomer;
    public static volatile SingularAttribute<CollectionPaymentView, Date> dueDate;
    public static volatile SingularAttribute<CollectionPaymentView, BigDecimal> dueValue;
    public static volatile SingularAttribute<CollectionPaymentView, Integer> dueDateId;
    public static volatile SingularAttribute<CollectionPaymentView, String> customerName;
    public static volatile SingularAttribute<CollectionPaymentView, Integer> instSerial;
    public static volatile SingularAttribute<CollectionPaymentView, String> customerPhone;
    public static volatile SingularAttribute<CollectionPaymentView, Integer> serial;
    public static volatile SingularAttribute<CollectionPaymentView, Integer> rowNum;
    public static volatile SingularAttribute<CollectionPaymentView, Integer> customerId;
    public static volatile SingularAttribute<CollectionPaymentView, String> projectName;
    public static volatile SingularAttribute<CollectionPaymentView, BigDecimal> value;
    public static volatile SingularAttribute<CollectionPaymentView, Integer> projectId;
    public static volatile SingularAttribute<CollectionPaymentView, Integer> status;

}
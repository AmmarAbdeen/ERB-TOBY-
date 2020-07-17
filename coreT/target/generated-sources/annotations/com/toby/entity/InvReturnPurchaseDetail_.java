package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvItem;
import com.toby.entity.InvPurchaseInvoiceDetail;
import com.toby.entity.InvReturnPurchase;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvReturnPurchaseDetail.class)
public class InvReturnPurchaseDetail_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvReturnPurchaseDetail, BigDecimal> unitPrice;
    public static volatile SingularAttribute<InvReturnPurchaseDetail, Branch> branchId;
    public static volatile SingularAttribute<InvReturnPurchaseDetail, InvItem> itemId;
    public static volatile SingularAttribute<InvReturnPurchaseDetail, BigDecimal> screwing;
    public static volatile SingularAttribute<InvReturnPurchaseDetail, BigDecimal> quantity;
    public static volatile SingularAttribute<InvReturnPurchaseDetail, Integer> dicountType;
    public static volatile SingularAttribute<InvReturnPurchaseDetail, Integer> serial;
    public static volatile SingularAttribute<InvReturnPurchaseDetail, String> itemBarcode;
    public static volatile SingularAttribute<InvReturnPurchaseDetail, InvPurchaseInvoiceDetail> invInvoicePurchaseDetailId;
    public static volatile SingularAttribute<InvReturnPurchaseDetail, BigDecimal> discount;
    public static volatile SingularAttribute<InvReturnPurchaseDetail, InvReturnPurchase> invReturnPurchaseId;
    public static volatile SingularAttribute<InvReturnPurchaseDetail, BigDecimal> net;

}
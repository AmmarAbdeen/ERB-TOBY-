package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvInventory;
import com.toby.entity.InvItem;
import com.toby.entity.InvPurchaseInvoice;
import com.toby.entity.InvReturnPurchaseDetail;
import com.toby.entity.Symbol;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvPurchaseInvoiceDetail.class)
public class InvPurchaseInvoiceDetail_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, Integer> transferFrom;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, Integer> selectedId;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, BigDecimal> discount;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, Integer> isdeleted;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, BigDecimal> number;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, InvItem> invItemParentId;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, Symbol> unitId;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, InvInventory> invInventoryId;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, Integer> discountType;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, BigDecimal> net;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, BigDecimal> costAvarage;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, Branch> branchId;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, BigDecimal> extraCost;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, BigDecimal> quantity;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, BigDecimal> cost;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, Integer> clothNumber;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, InvPurchaseInvoice> invPurchaseInvoiceId;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, String> itemBarcode;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, BigDecimal> weight;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, BigDecimal> finalQuantity;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, InvItem> itemId;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, BigDecimal> screwing;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, Integer> serial;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, BigDecimal> taxValue;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, BigDecimal> bounse;
    public static volatile CollectionAttribute<InvPurchaseInvoiceDetail, InvReturnPurchaseDetail> invReturnPurchaseDetailCollection;
    public static volatile SingularAttribute<InvPurchaseInvoiceDetail, Integer> status;

}
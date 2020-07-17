package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvInventoryTransaction;
import com.toby.entity.InvItem;
import com.toby.entity.InvPurchaseInvoiceDetail;
import com.toby.entity.InvPurchaseOrderDetail;
import com.toby.entity.Symbol;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvInventoryTransactionDetail.class)
public class InvInventoryTransactionDetail_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvInventoryTransactionDetail, Branch> branchId;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, BigDecimal> quantity;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, Integer> transferFrom;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, InvPurchaseInvoiceDetail> selectedId;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, String> itemBarcode;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, BigDecimal> finalQuantity;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, Date> creationDate;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, Integer> isdeleted;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, BigDecimal> screwing;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, InvItem> itemId;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, Date> modificationDate;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, InvPurchaseInvoiceDetail> selectedPurchaseId;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, InvPurchaseOrderDetail> selectedPurchaseOrderId;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, Integer> serial;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, InvInventoryTransaction> inventoryTransactionId;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, Symbol> unitId;
    public static volatile SingularAttribute<InvInventoryTransactionDetail, Integer> status;

}
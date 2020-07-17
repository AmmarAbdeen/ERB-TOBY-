package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.CostCenter;
import com.toby.entity.Currency;
import com.toby.entity.GeneralJournal;
import com.toby.entity.GlBank;
import com.toby.entity.InvInventory;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.InvPurchaseInvoice;
import com.toby.entity.InvReturnPurchaseDetail;
import com.toby.entity.InventoryDelegator;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(InvReturnPurchase.class)
public class InvReturnPurchase_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvReturnPurchase, Date> date;
    public static volatile SingularAttribute<InvReturnPurchase, Branch> branchId;
    public static volatile SingularAttribute<InvReturnPurchase, InvOrganizationSite> supplierId;
    public static volatile SingularAttribute<InvReturnPurchase, CostCenter> costCenter;
    public static volatile SingularAttribute<InvReturnPurchase, String> remark;
    public static volatile SingularAttribute<InvReturnPurchase, Date> invoiceDate;
    public static volatile SingularAttribute<InvReturnPurchase, Boolean> type;
    public static volatile SingularAttribute<InvReturnPurchase, GeneralJournal> generalJournalId;
    public static volatile SingularAttribute<InvReturnPurchase, Integer> paymentType;
    public static volatile SingularAttribute<InvReturnPurchase, Integer> postFlag;
    public static volatile SingularAttribute<InvReturnPurchase, BigDecimal> rate;
    public static volatile SingularAttribute<InvReturnPurchase, Integer> serial;
    public static volatile SingularAttribute<InvReturnPurchase, BigDecimal> taxValue;
    public static volatile SingularAttribute<InvReturnPurchase, GlBank> glBankId;
    public static volatile SingularAttribute<InvReturnPurchase, InvInventory> invInventoryId;
    public static volatile SingularAttribute<InvReturnPurchase, InventoryDelegator> salesPerson;
    public static volatile SingularAttribute<InvReturnPurchase, Currency> currencyId;
    public static volatile SingularAttribute<InvReturnPurchase, BigDecimal> net;
    public static volatile SingularAttribute<InvReturnPurchase, InvPurchaseInvoice> purchaseInvoiceId;
    public static volatile SingularAttribute<InvReturnPurchase, BigDecimal> discountValue;
    public static volatile CollectionAttribute<InvReturnPurchase, InvReturnPurchaseDetail> invReturnPurchaseDetailCollection;

}
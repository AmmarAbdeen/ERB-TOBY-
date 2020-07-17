package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.CostCenter;
import com.toby.entity.Currency;
import com.toby.entity.GeneralJournal;
import com.toby.entity.GlAccount;
import com.toby.entity.GlAdminUnit;
import com.toby.entity.GlBank;
import com.toby.entity.GlBankTransaction;
import com.toby.entity.InvInventory;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.InvPurchaseInvoiceDetail;
import com.toby.entity.InvReturnPurchase;
import com.toby.entity.InventoryDelegator;
import com.toby.entity.Symbol;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvPurchaseInvoice.class)
public class InvPurchaseInvoice_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvPurchaseInvoice, Date> date;
    public static volatile SingularAttribute<InvPurchaseInvoice, BigDecimal> taxdiscvalue;
    public static volatile SingularAttribute<InvPurchaseInvoice, Integer> serialTax;
    public static volatile SingularAttribute<InvPurchaseInvoice, Date> dueDate;
    public static volatile SingularAttribute<InvPurchaseInvoice, InvOrganizationSite> organizationSiteId;
    public static volatile SingularAttribute<InvPurchaseInvoice, Symbol> documentTypeId;
    public static volatile SingularAttribute<InvPurchaseInvoice, BigDecimal> discount;
    public static volatile SingularAttribute<InvPurchaseInvoice, Boolean> type;
    public static volatile SingularAttribute<InvPurchaseInvoice, GeneralJournal> generalJournalId;
    public static volatile SingularAttribute<InvPurchaseInvoice, BigDecimal> priceKilo;
    public static volatile SingularAttribute<InvPurchaseInvoice, Integer> isdeleted;
    public static volatile SingularAttribute<InvPurchaseInvoice, Integer> paymentType;
    public static volatile SingularAttribute<InvPurchaseInvoice, BigDecimal> actualWeight;
    public static volatile CollectionAttribute<InvPurchaseInvoice, GlBankTransaction> glBankTransactionCollection;
    public static volatile SingularAttribute<InvPurchaseInvoice, BigDecimal> rate;
    public static volatile SingularAttribute<InvPurchaseInvoice, CostCenter> costCenterId;
    public static volatile SingularAttribute<InvPurchaseInvoice, GlBank> glBankId;
    public static volatile SingularAttribute<InvPurchaseInvoice, InventoryDelegator> invDelegatorId;
    public static volatile SingularAttribute<InvPurchaseInvoice, InvInventory> invInventoryId;
    public static volatile SingularAttribute<InvPurchaseInvoice, Integer> pricetype;
    public static volatile SingularAttribute<InvPurchaseInvoice, Integer> discountType;
    public static volatile SingularAttribute<InvPurchaseInvoice, Date> recievedate;
    public static volatile SingularAttribute<InvPurchaseInvoice, Integer> proof;
    public static volatile SingularAttribute<InvPurchaseInvoice, Currency> currencyId;
    public static volatile SingularAttribute<InvPurchaseInvoice, String> supplierInvoiceNumber;
    public static volatile SingularAttribute<InvPurchaseInvoice, Date> supplierInvoiceDate;
    public static volatile SingularAttribute<InvPurchaseInvoice, Boolean> taxdiscflag;
    public static volatile SingularAttribute<InvPurchaseInvoice, GlAdminUnit> adminUnitId;
    public static volatile SingularAttribute<InvPurchaseInvoice, Branch> branchId;
    public static volatile SingularAttribute<InvPurchaseInvoice, BigDecimal> extraCost;
    public static volatile CollectionAttribute<InvPurchaseInvoice, InvPurchaseInvoiceDetail> invPurchaseInvoiceDetailCollection;
    public static volatile SingularAttribute<InvPurchaseInvoice, Integer> dueperiod;
    public static volatile SingularAttribute<InvPurchaseInvoice, Boolean> taxFlagFinal;
    public static volatile SingularAttribute<InvPurchaseInvoice, Integer> recieved;
    public static volatile SingularAttribute<InvPurchaseInvoice, BigDecimal> amountInvSupplier;
    public static volatile SingularAttribute<InvPurchaseInvoice, GlAccount> accountId;
    public static volatile SingularAttribute<InvPurchaseInvoice, Integer> customeraccept;
    public static volatile SingularAttribute<InvPurchaseInvoice, Integer> postFlag;
    public static volatile SingularAttribute<InvPurchaseInvoice, Boolean> taxflag;
    public static volatile CollectionAttribute<InvPurchaseInvoice, InvReturnPurchase> invReturnPurchaseCollection;
    public static volatile SingularAttribute<InvPurchaseInvoice, Integer> serial;
    public static volatile SingularAttribute<InvPurchaseInvoice, InvInventory> galaryId;
    public static volatile SingularAttribute<InvPurchaseInvoice, BigDecimal> totalActualWeight;
    public static volatile SingularAttribute<InvPurchaseInvoice, String> remarks;
    public static volatile SingularAttribute<InvPurchaseInvoice, Integer> status;

}
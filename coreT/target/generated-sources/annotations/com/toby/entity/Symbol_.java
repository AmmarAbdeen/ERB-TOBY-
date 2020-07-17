package com.toby.entity;

import com.toby.entity.CompanyLanguage;
import com.toby.entity.DataDictionary;
import com.toby.entity.GeneralSymbol;
import com.toby.entity.GlAccount;
import com.toby.entity.InvBarcode;
import com.toby.entity.InvItem;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.InvPurchaseInvoiceDetail;
import com.toby.entity.InvPurchaseOrderDetail;
import com.toby.entity.TobyUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(Symbol.class)
public class Symbol_ extends BaseEntity_ {

    public static volatile CollectionAttribute<Symbol, InvOrganizationSite> invOrganizationSiteCollection;
    public static volatile CollectionAttribute<Symbol, InvOrganizationSite> invOrganizationSiteCollection1;
    public static volatile CollectionAttribute<Symbol, InvOrganizationSite> invOrganizationSiteCollection2;
    public static volatile CollectionAttribute<Symbol, InvPurchaseInvoiceDetail> invPurchaseInvoiceDetailCollection;
    public static volatile CollectionAttribute<Symbol, DataDictionary> dataDictionaryCollection;
    public static volatile CollectionAttribute<Symbol, InvItem> invItemCollection4;
    public static volatile CollectionAttribute<Symbol, InvPurchaseOrderDetail> invPurchaseOrderDetailCollection;
    public static volatile CollectionAttribute<Symbol, InvItem> invItemCollection5;
    public static volatile CollectionAttribute<Symbol, InvItem> invItemCollection2;
    public static volatile CollectionAttribute<Symbol, InvItem> invItemCollection3;
    public static volatile SingularAttribute<Symbol, GlAccount> accountId;
    public static volatile CollectionAttribute<Symbol, InvItem> invItemCollection1;
    public static volatile CollectionAttribute<Symbol, CompanyLanguage> companyLanguageCollection;
    public static volatile SingularAttribute<Symbol, Integer> serial;
    public static volatile SingularAttribute<Symbol, Integer> arrange;
    public static volatile CollectionAttribute<Symbol, InvBarcode> invBarcodeCollection1;
    public static volatile CollectionAttribute<Symbol, InvItem> invItemCollection;
    public static volatile SingularAttribute<Symbol, String> name;
    public static volatile CollectionAttribute<Symbol, TobyUser> tobyUserCollection;
    public static volatile SingularAttribute<Symbol, GeneralSymbol> generalSymbolId;
    public static volatile CollectionAttribute<Symbol, InvBarcode> invBarcodeCollection;

}
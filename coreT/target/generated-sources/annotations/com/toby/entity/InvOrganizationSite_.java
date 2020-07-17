package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.Currency;
import com.toby.entity.GlAccount;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.InventoryDelegator;
import com.toby.entity.Symbol;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvOrganizationSite.class)
public class InvOrganizationSite_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvOrganizationSite, InvOrganizationSite> parent;
    public static volatile SingularAttribute<InvOrganizationSite, String> code;
    public static volatile SingularAttribute<InvOrganizationSite, String> phone2;
    public static volatile SingularAttribute<InvOrganizationSite, BigDecimal> discount;
    public static volatile SingularAttribute<InvOrganizationSite, String> contactPerson;
    public static volatile SingularAttribute<InvOrganizationSite, String> remark;
    public static volatile SingularAttribute<InvOrganizationSite, Integer> type;
    public static volatile SingularAttribute<InvOrganizationSite, Symbol> countryId;
    public static volatile SingularAttribute<InvOrganizationSite, BigDecimal> openBalanceCredit;
    public static volatile SingularAttribute<InvOrganizationSite, String> fax;
    public static volatile SingularAttribute<InvOrganizationSite, Currency> currencyId;
    public static volatile SingularAttribute<InvOrganizationSite, String> email;
    public static volatile SingularAttribute<InvOrganizationSite, BigDecimal> openBalanceDebit;
    public static volatile SingularAttribute<InvOrganizationSite, String> zip;
    public static volatile SingularAttribute<InvOrganizationSite, Branch> branchId;
    public static volatile CollectionAttribute<InvOrganizationSite, InvOrganizationSite> invOrganizationSiteCollection;
    public static volatile SingularAttribute<InvOrganizationSite, String> address;
    public static volatile SingularAttribute<InvOrganizationSite, Symbol> contractorType;
    public static volatile SingularAttribute<InvOrganizationSite, String> postBox;
    public static volatile SingularAttribute<InvOrganizationSite, String> mobile;
    public static volatile SingularAttribute<InvOrganizationSite, BigDecimal> balanceLimit;
    public static volatile SingularAttribute<InvOrganizationSite, Integer> active;
    public static volatile SingularAttribute<InvOrganizationSite, GlAccount> accountId;
    public static volatile SingularAttribute<InvOrganizationSite, String> phone;
    public static volatile SingularAttribute<InvOrganizationSite, Symbol> regionId;
    public static volatile SingularAttribute<InvOrganizationSite, String> name;
    public static volatile SingularAttribute<InvOrganizationSite, Date> openBalanceDate;
    public static volatile SingularAttribute<InvOrganizationSite, Symbol> supplierType;
    public static volatile SingularAttribute<InvOrganizationSite, InventoryDelegator> delegatorId;

}
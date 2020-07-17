package com.toby.entity;

import com.toby.entity.CostCenter;
import com.toby.entity.GlAccount;
import com.toby.entity.GlAdminUnit;
import com.toby.entity.GlBank;
import com.toby.entity.GlBankTransaction;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.Symbol;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(GlBankTransactionDetail.class)
public class GlBankTransactionDetail_ extends BaseEntity_ {

    public static volatile SingularAttribute<GlBankTransactionDetail, GlAdminUnit> adminUnitId;
    public static volatile SingularAttribute<GlBankTransactionDetail, Symbol> symbolId;
    public static volatile SingularAttribute<GlBankTransactionDetail, GlBankTransaction> glBankTransactionId;
    public static volatile SingularAttribute<GlBankTransactionDetail, BigDecimal> valueLocal;
    public static volatile SingularAttribute<GlBankTransactionDetail, InvOrganizationSite> organizationSiteId;
    public static volatile SingularAttribute<GlBankTransactionDetail, BigDecimal> rateBankFrom;
    public static volatile SingularAttribute<GlBankTransactionDetail, GlAccount> accountCreditId;
    public static volatile SingularAttribute<GlBankTransactionDetail, GlAccount> accountDebitId;
    public static volatile SingularAttribute<GlBankTransactionDetail, CostCenter> costCenterId;
    public static volatile SingularAttribute<GlBankTransactionDetail, Integer> serial;
    public static volatile SingularAttribute<GlBankTransactionDetail, GlBank> bankIdTo;
    public static volatile SingularAttribute<GlBankTransactionDetail, GlBank> bankIdFrom;
    public static volatile SingularAttribute<GlBankTransactionDetail, BigDecimal> value;
    public static volatile SingularAttribute<GlBankTransactionDetail, BigDecimal> rateBankTo;
    public static volatile SingularAttribute<GlBankTransactionDetail, String> remarks;

}
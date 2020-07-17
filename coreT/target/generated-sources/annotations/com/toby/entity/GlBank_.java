package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.Currency;
import com.toby.entity.GlAccount;
import com.toby.entity.GlBankTransaction;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(GlBank.class)
public class GlBank_ extends BaseEntity_ {

    public static volatile SingularAttribute<GlBank, GlAccount> accountId;
    public static volatile SingularAttribute<GlBank, Branch> branchId;
    public static volatile CollectionAttribute<GlBank, GlBankTransaction> glBankTransactionCollection;
    public static volatile SingularAttribute<GlBank, String> code;
    public static volatile SingularAttribute<GlBank, String> name;
    public static volatile SingularAttribute<GlBank, Date> dateOpeninngBalance;
    public static volatile SingularAttribute<GlBank, BigDecimal> openinngBalance;
    public static volatile SingularAttribute<GlBank, Integer> type;
    public static volatile SingularAttribute<GlBank, Currency> currencyId;

}
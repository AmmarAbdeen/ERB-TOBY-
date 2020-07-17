package com.toby.entity;

import com.toby.entity.CurrencyOperation;
import com.toby.entity.GlAccount;
import com.toby.entity.InvPurchaseOrder;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(Currency.class)
public class Currency_ extends BaseEntity_ {

    public static volatile SingularAttribute<Currency, String> code;
    public static volatile SingularAttribute<Currency, Integer> serial;
    public static volatile SingularAttribute<Currency, String> name;
    public static volatile ListAttribute<Currency, GlAccount> glAccountList;
    public static volatile CollectionAttribute<Currency, InvPurchaseOrder> invPurchaseOrderCollection;
    public static volatile ListAttribute<Currency, CurrencyOperation> currencyOperationList;

}
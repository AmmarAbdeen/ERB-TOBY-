package com.toby.entity;

import com.toby.entity.Currency;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(CurrencyOperation.class)
public class CurrencyOperation_ extends BaseEntity_ {

    public static volatile SingularAttribute<CurrencyOperation, Date> operationDate;
    public static volatile SingularAttribute<CurrencyOperation, String> notes;
    public static volatile SingularAttribute<CurrencyOperation, BigDecimal> rate;
    public static volatile SingularAttribute<CurrencyOperation, Currency> currencyId;

}
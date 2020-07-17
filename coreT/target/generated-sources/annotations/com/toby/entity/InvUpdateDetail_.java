package com.toby.entity;

import com.toby.entity.InvItem;
import com.toby.entity.InvUpdate;
import com.toby.entity.Symbol;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvUpdateDetail.class)
public class InvUpdateDetail_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvUpdateDetail, BigDecimal> screwing;
    public static volatile SingularAttribute<InvUpdateDetail, InvUpdate> invUpdateId;
    public static volatile SingularAttribute<InvUpdateDetail, Integer> serial;
    public static volatile SingularAttribute<InvUpdateDetail, BigDecimal> oldAmount;
    public static volatile SingularAttribute<InvUpdateDetail, String> itemBarcode;
    public static volatile SingularAttribute<InvUpdateDetail, BigDecimal> difference;
    public static volatile SingularAttribute<InvUpdateDetail, InvItem> invItemId;
    public static volatile SingularAttribute<InvUpdateDetail, Symbol> unitId;
    public static volatile SingularAttribute<InvUpdateDetail, BigDecimal> newAmount;
    public static volatile SingularAttribute<InvUpdateDetail, BigDecimal> value;

}
package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvItem;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:57")
@StaticMetamodel(InvPriceSellItem.class)
public class InvPriceSellItem_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvPriceSellItem, Date> date;
    public static volatile SingularAttribute<InvPriceSellItem, Branch> branchId;
    public static volatile SingularAttribute<InvPriceSellItem, InvItem> itemId;
    public static volatile SingularAttribute<InvPriceSellItem, BigDecimal> value;

}
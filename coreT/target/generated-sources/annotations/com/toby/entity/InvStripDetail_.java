package com.toby.entity;

import com.toby.entity.InvItem;
import com.toby.entity.InvStrip;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvStripDetail.class)
public class InvStripDetail_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvStripDetail, InvStrip> invStripId;
    public static volatile SingularAttribute<InvStripDetail, Integer> serial;
    public static volatile SingularAttribute<InvStripDetail, BigDecimal> actualAmount;
    public static volatile SingularAttribute<InvStripDetail, BigDecimal> difference;
    public static volatile SingularAttribute<InvStripDetail, InvItem> invItemId;
    public static volatile SingularAttribute<InvStripDetail, BigDecimal> bookBalance;

}
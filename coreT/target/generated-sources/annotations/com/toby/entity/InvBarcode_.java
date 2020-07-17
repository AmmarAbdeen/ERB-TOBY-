package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvItem;
import com.toby.entity.Symbol;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvBarcode.class)
public class InvBarcode_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvBarcode, BigDecimal> discountvalue;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> price_edit;
    public static volatile SingularAttribute<InvBarcode, String> code;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> bounsepriceyoung;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> numbermetersfreemen;
    public static volatile SingularAttribute<InvBarcode, Symbol> typeshow;
    public static volatile SingularAttribute<InvBarcode, Symbol> item_natural;
    public static volatile SingularAttribute<InvBarcode, Symbol> paintColor;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> maxpriceyoung;
    public static volatile SingularAttribute<InvBarcode, Symbol> typeBarcode;
    public static volatile SingularAttribute<InvBarcode, Symbol> unitId;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> price1;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> height;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> minpriceyoung;
    public static volatile SingularAttribute<InvBarcode, Branch> branchId;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> numbermetersmen;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> numbermetersfreeyoung;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> bounsepricemen;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> length;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> weight;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> commissionvalue;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> discountrate;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> maxpricemen;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> screwing;
    public static volatile SingularAttribute<InvBarcode, InvItem> itemId;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> commissionrate;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> width;
    public static volatile SingularAttribute<InvBarcode, Integer> isinventoryitem;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> numbermetersyoung;
    public static volatile SingularAttribute<InvBarcode, BigDecimal> minpricemen;

}
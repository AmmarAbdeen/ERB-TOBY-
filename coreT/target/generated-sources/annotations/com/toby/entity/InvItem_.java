package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvGroup;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.Symbol;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(InvItem.class)
public class InvItem_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvItem, BigDecimal> discountvalue;
    public static volatile SingularAttribute<InvItem, BigDecimal> price_edit;
    public static volatile SingularAttribute<InvItem, BigDecimal> storesQuality;
    public static volatile SingularAttribute<InvItem, BigDecimal> bounsepriceyoung;
    public static volatile SingularAttribute<InvItem, InvGroup> groupId;
    public static volatile SingularAttribute<InvItem, BigDecimal> numbermetersfreemen;
    public static volatile SingularAttribute<InvItem, String> storageLocation;
    public static volatile SingularAttribute<InvItem, Symbol> typeshow;
    public static volatile SingularAttribute<InvItem, BigDecimal> sellPrice;
    public static volatile SingularAttribute<InvItem, Symbol> paintColor;
    public static volatile SingularAttribute<InvItem, Date> dateCreateCat;
    public static volatile SingularAttribute<InvItem, Integer> typeCat;
    public static volatile SingularAttribute<InvItem, Integer> statusCat;
    public static volatile SingularAttribute<InvItem, BigDecimal> height;
    public static volatile SingularAttribute<InvItem, String> image;
    public static volatile SingularAttribute<InvItem, Branch> branchId;
    public static volatile SingularAttribute<InvItem, BigDecimal> numbermetersfreeyoung;
    public static volatile SingularAttribute<InvItem, Symbol> author;
    public static volatile SingularAttribute<InvItem, BigDecimal> weight;
    public static volatile SingularAttribute<InvItem, BigDecimal> costAverage;
    public static volatile SingularAttribute<InvItem, Symbol> brandId;
    public static volatile SingularAttribute<InvItem, String> name;
    public static volatile SingularAttribute<InvItem, BigDecimal> minimumAmount;
    public static volatile SingularAttribute<InvItem, Integer> isinventoryitem;
    public static volatile SingularAttribute<InvItem, BigDecimal> openingCost;
    public static volatile SingularAttribute<InvItem, String> code;
    public static volatile SingularAttribute<InvItem, Symbol> addon2;
    public static volatile SingularAttribute<InvItem, Symbol> addon1;
    public static volatile SingularAttribute<InvItem, Symbol> item_natural;
    public static volatile SingularAttribute<InvItem, String> nameen;
    public static volatile SingularAttribute<InvItem, Boolean> issell;
    public static volatile SingularAttribute<InvItem, BigDecimal> maxpriceyoung;
    public static volatile SingularAttribute<InvItem, String> nickname;
    public static volatile SingularAttribute<InvItem, Symbol> unitId;
    public static volatile SingularAttribute<InvItem, Symbol> enamelColor;
    public static volatile SingularAttribute<InvItem, BigDecimal> minpriceyoung;
    public static volatile SingularAttribute<InvItem, BigDecimal> numbermetersmen;
    public static volatile SingularAttribute<InvItem, BigDecimal> weightPackage;
    public static volatile SingularAttribute<InvItem, BigDecimal> bounsepricemen;
    public static volatile SingularAttribute<InvItem, BigDecimal> length;
    public static volatile SingularAttribute<InvItem, BigDecimal> undirectCost;
    public static volatile SingularAttribute<InvItem, Boolean> ispurchase;
    public static volatile SingularAttribute<InvItem, BigDecimal> commissionvalue;
    public static volatile SingularAttribute<InvItem, BigDecimal> discountrate;
    public static volatile SingularAttribute<InvItem, Symbol> stone;
    public static volatile SingularAttribute<InvItem, BigDecimal> maxpricemen;
    public static volatile SingularAttribute<InvItem, BigDecimal> maxmumAmount;
    public static volatile SingularAttribute<InvItem, BigDecimal> commissionrate;
    public static volatile SingularAttribute<InvItem, BigDecimal> contractPrice;
    public static volatile SingularAttribute<InvItem, BigDecimal> lastCost;
    public static volatile SingularAttribute<InvItem, Symbol> originCountry;
    public static volatile SingularAttribute<InvItem, BigDecimal> width;
    public static volatile SingularAttribute<InvItem, InvOrganizationSite> siteId;
    public static volatile SingularAttribute<InvItem, BigDecimal> numbermetersyoung;
    public static volatile SingularAttribute<InvItem, String> remarks;
    public static volatile SingularAttribute<InvItem, BigDecimal> minpricemen;

}
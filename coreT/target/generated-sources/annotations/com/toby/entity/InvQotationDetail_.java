package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvItem;
import com.toby.entity.InvQotation;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvQotationDetail.class)
public class InvQotationDetail_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvQotationDetail, Branch> branchId;
    public static volatile SingularAttribute<InvQotationDetail, InvItem> itemId;
    public static volatile SingularAttribute<InvQotationDetail, BigDecimal> screwing;
    public static volatile SingularAttribute<InvQotationDetail, BigDecimal> quantity;
    public static volatile SingularAttribute<InvQotationDetail, InvQotation> qotationId;
    public static volatile SingularAttribute<InvQotationDetail, Integer> serial;
    public static volatile SingularAttribute<InvQotationDetail, BigDecimal> price;
    public static volatile SingularAttribute<InvQotationDetail, String> itemBarcode;
    public static volatile SingularAttribute<InvQotationDetail, BigDecimal> discount;
    public static volatile SingularAttribute<InvQotationDetail, BigDecimal> finalQuantity;
    public static volatile SingularAttribute<InvQotationDetail, Integer> discountId;
    public static volatile SingularAttribute<InvQotationDetail, Integer> status;

}
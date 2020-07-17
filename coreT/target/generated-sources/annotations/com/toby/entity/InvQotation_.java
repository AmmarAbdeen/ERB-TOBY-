package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.InvQotationDetail;
import com.toby.entity.InventoryDelegator;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvQotation.class)
public class InvQotation_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvQotation, Date> date;
    public static volatile SingularAttribute<InvQotation, Branch> branchId;
    public static volatile CollectionAttribute<InvQotation, InvQotationDetail> invQotationDetailCollection;
    public static volatile SingularAttribute<InvQotation, InvOrganizationSite> supplierId;
    public static volatile SingularAttribute<InvQotation, Date> endDate;
    public static volatile SingularAttribute<InvQotation, String> remark;
    public static volatile SingularAttribute<InvQotation, BigDecimal> discountPercentage;
    public static volatile SingularAttribute<InvQotation, Integer> postFlag;
    public static volatile SingularAttribute<InvQotation, Integer> serial;
    public static volatile SingularAttribute<InvQotation, InvOrganizationSite> customerId;
    public static volatile SingularAttribute<InvQotation, BigDecimal> totalDiscount;
    public static volatile SingularAttribute<InvQotation, BigDecimal> discountValue;
    public static volatile SingularAttribute<InvQotation, InventoryDelegator> delegatorId;
    public static volatile SingularAttribute<InvQotation, Integer> status;

}
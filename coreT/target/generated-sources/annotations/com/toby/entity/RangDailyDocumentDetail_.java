package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.RangDailyDocument;
import com.toby.entity.TobyUser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(RangDailyDocumentDetail.class)
public class RangDailyDocumentDetail_ extends BaseEntity_ {

    public static volatile SingularAttribute<RangDailyDocumentDetail, Integer> rangTo;
    public static volatile SingularAttribute<RangDailyDocumentDetail, Branch> branchId;
    public static volatile SingularAttribute<RangDailyDocumentDetail, Date> deletedDate;
    public static volatile SingularAttribute<RangDailyDocumentDetail, Integer> rangFrom;
    public static volatile SingularAttribute<RangDailyDocumentDetail, Integer> rangYear;
    public static volatile SingularAttribute<RangDailyDocumentDetail, RangDailyDocument> rangDailyDocumentId;
    public static volatile SingularAttribute<RangDailyDocumentDetail, TobyUser> deletedBy;

}
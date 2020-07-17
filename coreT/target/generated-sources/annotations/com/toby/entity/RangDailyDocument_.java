package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.RangDailyDocumentDetail;
import com.toby.entity.TobyUser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(RangDailyDocument.class)
public class RangDailyDocument_ extends BaseEntity_ {

    public static volatile SingularAttribute<RangDailyDocument, Branch> branchId;
    public static volatile SingularAttribute<RangDailyDocument, Date> deletedDate;
    public static volatile SingularAttribute<RangDailyDocument, String> name;
    public static volatile SetAttribute<RangDailyDocument, RangDailyDocumentDetail> rangDailyDocumentDetailSet;
    public static volatile SingularAttribute<RangDailyDocument, TobyUser> deletedBy;

}
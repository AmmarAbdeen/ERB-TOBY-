package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.GlYear;
import com.toby.entity.Symbol;
import com.toby.entity.TobyUser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(GeneralJournal.class)
public class GeneralJournal_ extends BaseEntity_ {

    public static volatile SingularAttribute<GeneralJournal, Symbol> generalType;
    public static volatile SingularAttribute<GeneralJournal, String> generalStatement;
    public static volatile SingularAttribute<GeneralJournal, Branch> branchId;
    public static volatile SingularAttribute<GeneralJournal, String> postFlagReview;
    public static volatile SingularAttribute<GeneralJournal, Integer> generalNumber;
    public static volatile SingularAttribute<GeneralJournal, String> macId;
    public static volatile SingularAttribute<GeneralJournal, Date> generalData;
    public static volatile SingularAttribute<GeneralJournal, TobyUser> deletedBy;
    public static volatile SingularAttribute<GeneralJournal, Integer> serial;
    public static volatile SingularAttribute<GeneralJournal, Integer> generalDecument;
    public static volatile SingularAttribute<GeneralJournal, Boolean> post_flag;
    public static volatile SingularAttribute<GeneralJournal, GlYear> glYear;
    public static volatile SingularAttribute<GeneralJournal, Date> deleteDate;

}
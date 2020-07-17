package com.toby.entity;

import com.toby.entity.TobyCompany;
import com.toby.entity.TobyUser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(HistoryGeneralJournal.class)
public class HistoryGeneralJournal_ { 

    public static volatile SingularAttribute<HistoryGeneralJournal, Integer> generalType;
    public static volatile SingularAttribute<HistoryGeneralJournal, String> generalStatement;
    public static volatile SingularAttribute<HistoryGeneralJournal, Integer> branchId;
    public static volatile SingularAttribute<HistoryGeneralJournal, Integer> generalNumber;
    public static volatile SingularAttribute<HistoryGeneralJournal, Date> generalData;
    public static volatile SingularAttribute<HistoryGeneralJournal, Date> creationDate;
    public static volatile SingularAttribute<HistoryGeneralJournal, String> generalStatementNew;
    public static volatile SingularAttribute<HistoryGeneralJournal, Date> modificationDateNew;
    public static volatile SingularAttribute<HistoryGeneralJournal, TobyCompany> companyId;
    public static volatile SingularAttribute<HistoryGeneralJournal, Date> modificationDate;
    public static volatile SingularAttribute<HistoryGeneralJournal, Date> generalDataNew;
    public static volatile SingularAttribute<HistoryGeneralJournal, TobyUser> createdBy;
    public static volatile SingularAttribute<HistoryGeneralJournal, Integer> symbolIdNew;
    public static volatile SingularAttribute<HistoryGeneralJournal, Integer> generalDecument;
    public static volatile SingularAttribute<HistoryGeneralJournal, Boolean> post_flag;
    public static volatile SingularAttribute<HistoryGeneralJournal, Integer> rowStatus;
    public static volatile SingularAttribute<HistoryGeneralJournal, Integer> modifiedByNew;
    public static volatile SingularAttribute<HistoryGeneralJournal, TobyUser> modifiedBy;
    public static volatile SingularAttribute<HistoryGeneralJournal, Integer> id;

}
package com.toby.entity;

import com.toby.entity.TobyCompany;
import com.toby.entity.TobyUser;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:57")
@StaticMetamodel(HistoryGeneralJournalDetails.class)
public class HistoryGeneralJournalDetails_ { 

    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> generalJournalId;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Date> modificationDateNew;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> costCenterId;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, BigDecimal> rate;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> costCenterIdNew;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> adminUnitIdNew;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, TobyUser> modifiedBy;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> id;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> serialNew;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> currencyId;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> adminUnitId;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> branchId;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> glAssistantAccount;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, BigDecimal> creditAmountNew;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> glAssistantAccountIdNew;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, BigDecimal> debitAmount;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> currencyIdNew;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Date> creationDate;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> glaccountIdNew;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, String> discribtionNew;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, TobyCompany> companyId;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Date> modificationDate;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> glACCOUNTId;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, TobyUser> createdBy;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> serial;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, BigDecimal> debitAmountNew;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> rowStatus;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, BigDecimal> creditamount;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, Integer> modifiedByNew;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, BigDecimal> rateNew;
    public static volatile SingularAttribute<HistoryGeneralJournalDetails, String> discribtion;

}
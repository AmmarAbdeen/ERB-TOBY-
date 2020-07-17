package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.CostCenter;
import com.toby.entity.Currency;
import com.toby.entity.GeneralJournal;
import com.toby.entity.GlAccount;
import com.toby.entity.GlAdminUnit;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(GeneralJournalDetails.class)
public class GeneralJournalDetails_ extends BaseEntity_ {

    public static volatile SingularAttribute<GeneralJournalDetails, GlAdminUnit> adminUnitId;
    public static volatile SingularAttribute<GeneralJournalDetails, BigDecimal> creditamountLocal;
    public static volatile SingularAttribute<GeneralJournalDetails, Branch> branchId;
    public static volatile SingularAttribute<GeneralJournalDetails, GlAccount> glAssistantAccount;
    public static volatile SingularAttribute<GeneralJournalDetails, BigDecimal> debitAmount;
    public static volatile SingularAttribute<GeneralJournalDetails, GeneralJournal> generalJournalId;
    public static volatile SingularAttribute<GeneralJournalDetails, GlAccount> glACCOUNTId;
    public static volatile SingularAttribute<GeneralJournalDetails, Integer> serial;
    public static volatile SingularAttribute<GeneralJournalDetails, CostCenter> costCenterId;
    public static volatile SingularAttribute<GeneralJournalDetails, BigDecimal> rate;
    public static volatile SingularAttribute<GeneralJournalDetails, BigDecimal> creditamount;
    public static volatile SingularAttribute<GeneralJournalDetails, BigDecimal> debitAmountLocal;
    public static volatile SingularAttribute<GeneralJournalDetails, Currency> currencyId;
    public static volatile SingularAttribute<GeneralJournalDetails, String> discribtion;

}
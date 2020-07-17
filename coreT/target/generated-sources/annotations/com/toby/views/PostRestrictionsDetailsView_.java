package com.toby.views;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(PostRestrictionsDetailsView.class)
public class PostRestrictionsDetailsView_ { 

    public static volatile SingularAttribute<PostRestrictionsDetailsView, Date> date;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, Integer> adminUnitId;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, String> accNameDebit;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, Integer> branchId;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, Integer> glBankTransactionId;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, Integer> moduleCode;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, BigDecimal> valueLocal;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, Integer> accNumberDebit;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, Integer> transactionType;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, Integer> accountDebitId;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, Integer> accountCreditId;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, Integer> costCenterId;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, BigDecimal> taxValue;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, BigDecimal> taxValueLocal;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, BigDecimal> secondValue;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, Integer> rowNum;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, Integer> secondAccNumberCredit;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, String> secondAccNameCredit;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, Integer> accNumberCredit;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, Integer> secondAccountCreditId;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, String> accNameCredit;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, BigDecimal> value;
    public static volatile SingularAttribute<PostRestrictionsDetailsView, String> remarks;

}
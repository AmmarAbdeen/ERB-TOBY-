package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.GeneralJournal;
import com.toby.entity.GlBank;
import com.toby.entity.GlBankTransaction;
import com.toby.entity.GlBankTransactionDetail;
import com.toby.entity.InvPurchaseInvoice;
import com.toby.entity.Symbol;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(GlBankTransaction.class)
public class GlBankTransaction_ extends BaseEntity_ {

    public static volatile SingularAttribute<GlBankTransaction, Date> date;
    public static volatile SingularAttribute<GlBankTransaction, Branch> branchId;
    public static volatile SingularAttribute<GlBankTransaction, GlBankTransaction> parent;
    public static volatile CollectionAttribute<GlBankTransaction, GlBankTransactionDetail> glBankTransactionDetailCollection;
    public static volatile SingularAttribute<GlBankTransaction, BigDecimal> valueLocal;
    public static volatile SingularAttribute<GlBankTransaction, Symbol> documentTypeId;
    public static volatile SingularAttribute<GlBankTransaction, Integer> chequeStatus;
    public static volatile SingularAttribute<GlBankTransaction, String> remark;
    public static volatile SingularAttribute<GlBankTransaction, GeneralJournal> generalJournalId;
    public static volatile SingularAttribute<GlBankTransaction, Integer> chequeNumber;
    public static volatile SingularAttribute<GlBankTransaction, Date> chequeDate;
    public static volatile SingularAttribute<GlBankTransaction, Integer> paymentType;
    public static volatile SingularAttribute<GlBankTransaction, Integer> organizationType;
    public static volatile SingularAttribute<GlBankTransaction, Integer> transactionType;
    public static volatile SingularAttribute<GlBankTransaction, Integer> postFlag;
    public static volatile SingularAttribute<GlBankTransaction, Integer> serial;
    public static volatile SingularAttribute<GlBankTransaction, BigDecimal> rate;
    public static volatile SingularAttribute<GlBankTransaction, Date> chequeDueDate;
    public static volatile SingularAttribute<GlBankTransaction, GlBank> glBankId;
    public static volatile SingularAttribute<GlBankTransaction, InvPurchaseInvoice> invoiceId;
    public static volatile SingularAttribute<GlBankTransaction, BigDecimal> value;
    public static volatile SingularAttribute<GlBankTransaction, Integer> glYear;
    public static volatile SingularAttribute<GlBankTransaction, String> remark2;

}
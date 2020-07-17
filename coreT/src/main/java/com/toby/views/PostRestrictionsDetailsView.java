/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.views;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ahmed
 */
@Entity
@Table(name = "post_restrictions_details_view")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PostRestrictionsDetailsView.findAll", query = "SELECT p FROM PostRestrictionsDetailsView p"),
    @NamedQuery(name = "PostRestrictionsDetailsView.findByRowNum", query = "SELECT p FROM PostRestrictionsDetailsView p WHERE p.rowNum = :rowNum"),
    @NamedQuery(name = "PostRestrictionsDetailsView.findByGlBankTransactionId", query = "SELECT p FROM PostRestrictionsDetailsView p WHERE p.glBankTransactionId = :glBankTransactionId"),
    @NamedQuery(name = "PostRestrictionsDetailsView.findByAccountDebitId", query = "SELECT p FROM PostRestrictionsDetailsView p WHERE p.accountDebitId = :accountDebitId"),
    @NamedQuery(name = "PostRestrictionsDetailsView.findByAccountCreditId", query = "SELECT p FROM PostRestrictionsDetailsView p WHERE p.accountCreditId = :accountCreditId"),
    @NamedQuery(name = "PostRestrictionsDetailsView.findByCostCenterId", query = "SELECT p FROM PostRestrictionsDetailsView p WHERE p.costCenterId = :costCenterId"),
    @NamedQuery(name = "PostRestrictionsDetailsView.findByAdminUnitId", query = "SELECT p FROM PostRestrictionsDetailsView p WHERE p.adminUnitId = :adminUnitId"),
    @NamedQuery(name = "PostRestrictionsDetailsView.findByAccNumberDebit", query = "SELECT p FROM PostRestrictionsDetailsView p WHERE p.accNumberDebit = :accNumberDebit"),
    @NamedQuery(name = "PostRestrictionsDetailsView.findByAccNumberCredit", query = "SELECT p FROM PostRestrictionsDetailsView p WHERE p.accNumberCredit = :accNumberCredit"),
    @NamedQuery(name = "PostRestrictionsDetailsView.findByAccNameDebit", query = "SELECT p FROM PostRestrictionsDetailsView p WHERE p.accNameDebit = :accNameDebit"),
    @NamedQuery(name = "PostRestrictionsDetailsView.findByAccNameCredit", query = "SELECT p FROM PostRestrictionsDetailsView p WHERE p.accNameCredit = :accNameCredit"),
    @NamedQuery(name = "PostRestrictionsDetailsView.findByValue", query = "SELECT p FROM PostRestrictionsDetailsView p WHERE p.value = :value"),
    @NamedQuery(name = "PostRestrictionsDetailsView.findByValueLocal", query = "SELECT p FROM PostRestrictionsDetailsView p WHERE p.valueLocal = :valueLocal"),
    @NamedQuery(name = "PostRestrictionsDetailsView.findByRemarks", query = "SELECT p FROM PostRestrictionsDetailsView p WHERE p.remarks = :remarks")})
public class PostRestrictionsDetailsView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "rowNum")
    private Integer rowNum;
    @Column(name = "gl_bank_transaction_id")
    private Integer glBankTransactionId;
    @Column(name = "account_debit_id")
    private Integer accountDebitId;
    @Column(name = "account_credit_id")
    private Integer accountCreditId;
    @Column(name = "second_account_credit_id")
    private Integer secondAccountCreditId;
    @Column(name = "cost_center_id")
    private Integer costCenterId;
    @Column(name = "admin_unit_id")
    private Integer adminUnitId;
    @Column(name = "acc_number_debit")
    private Integer accNumberDebit;
    @Column(name = "acc_number_credit")
    private Integer accNumberCredit;
    @Column(name = "second_acc_number_credit")
    private Integer secondAccNumberCredit;
    @Size(max = 45)
    @Column(name = "acc_name_debit")
    private String accNameDebit;
    @Size(max = 45)
    @Column(name = "acc_name_credit")
    private String accNameCredit;
    @Size(max = 45)
    @Column(name = "second_acc_name_credit")
    private String secondAccNameCredit;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private BigDecimal value;
    @Column(name = "value_local")
    private BigDecimal valueLocal;
    @Column(name = "second_value")
    private BigDecimal secondValue;
    @Column(name = "tax_value")
    private BigDecimal taxValue;
    @Column(name = "tax_value_local")
    private BigDecimal taxValueLocal;
    @Size(max = 450)
    @Column(name = "remarks")
    private String remarks;
    @Basic(optional = false)
    @NotNull
    @Column(name = "module_code")
    private Integer moduleCode;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "branch_id")
    private Integer branchId;
 @Column(name = "transaction_type")
    private Integer transactionType;
 
    public PostRestrictionsDetailsView() {
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getGlBankTransactionId() {
        return glBankTransactionId;
    }

    public void setGlBankTransactionId(Integer glBankTransactionId) {
        this.glBankTransactionId = glBankTransactionId;
    }

    public Integer getAccountDebitId() {
        return accountDebitId;
    }

    public void setAccountDebitId(Integer accountDebitId) {
        this.accountDebitId = accountDebitId;
    }

    public Integer getAccountCreditId() {
        return accountCreditId;
    }

    public void setAccountCreditId(Integer accountCreditId) {
        this.accountCreditId = accountCreditId;
    }

    public Integer getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(Integer costCenterId) {
        this.costCenterId = costCenterId;
    }

    public Integer getAdminUnitId() {
        return adminUnitId;
    }

    public void setAdminUnitId(Integer adminUnitId) {
        this.adminUnitId = adminUnitId;
    }

    public Integer getAccNumberDebit() {
        return accNumberDebit;
    }

    public void setAccNumberDebit(Integer accNumberDebit) {
        this.accNumberDebit = accNumberDebit;
    }

    public Integer getAccNumberCredit() {
        return accNumberCredit;
    }

    public void setAccNumberCredit(Integer accNumberCredit) {
        this.accNumberCredit = accNumberCredit;
    }

    public String getAccNameDebit() {
        return accNameDebit;
    }

    public void setAccNameDebit(String accNameDebit) {
        this.accNameDebit = accNameDebit;
    }

    public String getAccNameCredit() {
        return accNameCredit;
    }

    public void setAccNameCredit(String accNameCredit) {
        this.accNameCredit = accNameCredit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValueLocal() {
        return valueLocal;
    }

    public void setValueLocal(BigDecimal valueLocal) {
        this.valueLocal = valueLocal;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the moduleCode
     */
    public Integer getModuleCode() {
        return moduleCode;
    }

    /**
     * @param moduleCode the moduleCode to set
     */
    public void setModuleCode(Integer moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * @return the taxValue
     */
    public BigDecimal getTaxValue() {
        return taxValue;
    }

    /**
     * @param taxValue the taxValue to set
     */
    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }

    /**
     * @return the taxValueLocal
     */
    public BigDecimal getTaxValueLocal() {
        return taxValueLocal;
    }

    /**
     * @param taxValueLocal the taxValueLocal to set
     */
    public void setTaxValueLocal(BigDecimal taxValueLocal) {
        this.taxValueLocal = taxValueLocal;
    }

    /**
     * @return the secondValue
     */
    public BigDecimal getSecondValue() {
        return secondValue;
    }

    /**
     * @param secondValue the secondValue to set
     */
    public void setSecondValue(BigDecimal secondValue) {
        this.secondValue = secondValue;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the branchId
     */
    public Integer getBranchId() {
        return branchId;
    }

    /**
     * @param branchId the branchId to set
     */
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    /**
     * @return the transactionType
     */
    public Integer getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * @return the secondAccountCreditId
     */
    public Integer getSecondAccountCreditId() {
        return secondAccountCreditId;
    }

    /**
     * @param secondAccountCreditId the secondAccountCreditId to set
     */
    public void setSecondAccountCreditId(Integer secondAccountCreditId) {
        this.secondAccountCreditId = secondAccountCreditId;
    }

    /**
     * @return the secondAccNumberCredit
     */
    public Integer getSecondAccNumberCredit() {
        return secondAccNumberCredit;
    }

    /**
     * @param secondAccNumberCredit the secondAccNumberCredit to set
     */
    public void setSecondAccNumberCredit(Integer secondAccNumberCredit) {
        this.secondAccNumberCredit = secondAccNumberCredit;
    }

    /**
     * @return the secondAccNameCredit
     */
    public String getSecondAccNameCredit() {
        return secondAccNameCredit;
    }

    /**
     * @param secondAccNameCredit the secondAccNameCredit to set
     */
    public void setSecondAccNameCredit(String secondAccNameCredit) {
        this.secondAccNameCredit = secondAccNameCredit;
    }

}

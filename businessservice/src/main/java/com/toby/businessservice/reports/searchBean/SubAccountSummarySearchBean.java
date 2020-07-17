package com.toby.businessservice.reports.searchBean;

import com.toby.entity.GlBank;
import com.toby.entity.Symbol;
import java.io.Serializable;
import java.util.Date;

public class SubAccountSummarySearchBean implements Serializable {

    private Date dateFrom;
    private Date dateTo;
    private Integer costCenterFrom;
    private Integer costCenterTo;
    private Integer adminUnitFrom;
    private Integer adminUnitTo;
    private Integer accountId;
    private Integer financailYear;
    private Integer branchId;
    private Integer currencyType;
    private String accClass;
    private GlBank glBankFrom;
    private GlBank glBankTo;
    private Integer type;

    private Integer orderBy;
    private Integer transactionType; 
    private Integer serialFrom;
    private Integer serialTo;
    private Symbol itemSerialFrom;
    private Symbol itemSerialTo;
    private String IsagUserFrom;
    private String IsagUserTo;
    
    public GlBank getGlBankFrom() {
        return glBankFrom;
    }

    public void setGlBankFrom(GlBank glBankFrom) {
        this.glBankFrom = glBankFrom;
    }

    public GlBank getGlBankTo() {
        return glBankTo;
    }

    public void setGlBankTo(GlBank glBankTo) {
        this.glBankTo = glBankTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getCostCenterFrom() {
        return costCenterFrom;
    }

    public void setCostCenterFrom(Integer costCenterFrom) {
        this.costCenterFrom = costCenterFrom;
    }

    public Integer getCostCenterTo() {
        return costCenterTo;
    }

    public void setCostCenterTo(Integer costCenterTo) {
        this.costCenterTo = costCenterTo;
    }

    public Integer getAdminUnitFrom() {
        return adminUnitFrom;
    }

    public void setAdminUnitFrom(Integer adminUnitFrom) {
        this.adminUnitFrom = adminUnitFrom;
    }

    public Integer getAdminUnitTo() {
        return adminUnitTo;
    }

    public void setAdminUnitTo(Integer adminUnitTo) {
        this.adminUnitTo = adminUnitTo;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * @return the financailYear
     */
    public Integer getFinancailYear() {
        return financailYear;
    }

    /**
     * @param financailYear the financailYear to set
     */
    public void setFinancailYear(Integer financailYear) {
        this.financailYear = financailYear;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(Integer currencyType) {
        this.currencyType = currencyType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * @return the accClass
     */
    public String getAccClass() {
        return accClass;
    }

    /**
     * @param accClass the accClass to set
     */
    public void setAccClass(String accClass) {
        this.accClass = accClass;
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
     * @return the serialFrom
     */
    public Integer getSerialFrom() {
        return serialFrom;
    }

    /**
     * @param serialFrom the serialFrom to set
     */
    public void setSerialFrom(Integer serialFrom) {
        this.serialFrom = serialFrom;
    }

    /**
     * @return the serialTo
     */
    public Integer getSerialTo() {
        return serialTo;
    }

    /**
     * @param serialTo the serialTo to set
     */
    public void setSerialTo(Integer serialTo) {
        this.serialTo = serialTo;
    }

    /**
     * @return the itemSerialFrom
     */
    public Symbol getItemSerialFrom() {
        return itemSerialFrom;
    }

    /**
     * @param itemSerialFrom the itemSerialFrom to set
     */
    public void setItemSerialFrom(Symbol itemSerialFrom) {
        this.itemSerialFrom = itemSerialFrom;
    }

    /**
     * @return the itemSerialTo
     */
    public Symbol getItemSerialTo() {
        return itemSerialTo;
    }

    /**
     * @param itemSerialTo the itemSerialTo to set
     */
    public void setItemSerialTo(Symbol itemSerialTo) {
        this.itemSerialTo = itemSerialTo;
    }

    /**
     * @return the IsagUserFrom
     */
    public String getIsagUserFrom() {
        return IsagUserFrom;
    }

    /**
     * @param IsagUserFrom the IsagUserFrom to set
     */
    public void setIsagUserFrom(String IsagUserFrom) {
        this.IsagUserFrom = IsagUserFrom;
    }

    /**
     * @return the IsagUserTo
     */
    public String getIsagUserTo() {
        return IsagUserTo;
    }

    /**
     * @param IsagUserTo the IsagUserTo to set
     */
    public void setIsagUserTo(String IsagUserTo) {
        this.IsagUserTo = IsagUserTo;
    }

}

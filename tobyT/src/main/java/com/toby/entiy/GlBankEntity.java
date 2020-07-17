/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.entiy;

import com.toby.entity.Branch;
import com.toby.entity.Currency;
import com.toby.entity.GlAccount;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author WIN7
 */
public class GlBankEntity extends BaseEntity {

    private String name;
    private String code;
    private BigDecimal openingBalance;
    private Date openingBalanceDate;
    private GlAccount accountId;
    private Branch branchId;
    private Currency currencyId;
    private List<GlBankTransactionEntity> glBankTransactionCollection;
    
    private Integer type;
    private String typeName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GlAccount getAccountId() {
        return accountId;
    }

    public void setAccountId(GlAccount accountId) {
        this.accountId = accountId;
    }

    public Branch getBranchId() {
        return branchId;
    }

    public void setBranchId(Branch branchId) {
        this.branchId = branchId;
    }

    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
    }

    public List<GlBankTransactionEntity> getGlBankTransactionCollection() {
        return glBankTransactionCollection;
    }

    public void setGlBankTransactionCollection(List<GlBankTransactionEntity> glBankTransactionCollection) {
        this.glBankTransactionCollection = glBankTransactionCollection;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * @return the openingBalance
     */
    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    /**
     * @param openingBalance the openingBalance to set
     */
    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    /**
     * @return the openingBalanceDate
     */
    public Date getOpeningBalanceDate() {
        return openingBalanceDate;
    }

    /**
     * @param openingBalanceDate the openingBalanceDate to set
     */
    public void setOpeningBalanceDate(Date openingBalanceDate) {
        this.openingBalanceDate = openingBalanceDate;
    }
}

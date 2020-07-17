/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hq002
 */
@Entity
@Table(name = "accounts_system_settings")
@XmlRootElement
public class AccountsSystemSettings extends BaseEntity{
  
    @Column(name = "journals_serial")
    private String journalsSerial;
    
    @Column(name = "journals_daily_balanced")
    private String journalsDailyBalanced;
    
    @Column(name = "journals_edit")
    private String journalsEdit;
    
    @Column(name = "account_statement_appearance")
    private String accountStatementAppearance;
    
    @Column(name = "Allow_subsequent_cache_administration")
    private String AllowSubsequentCacheAdministration;
    
    @Column(name = "work_with_accounts")
    private String workWithAccounts;
    
     @Column(name = "note_sreceivables_type")
    private String noteSreceivablesType;

    public String getJournalsSerial() {
        return journalsSerial;
    }

    public void setJournalsSerial(String journalsSerial) {
        this.journalsSerial = journalsSerial;
    }
    
    public String getJournalsEdit() {
        return journalsEdit;
    }

    public void setJournalsEdit(String journalsEdit) {
        this.journalsEdit = journalsEdit;
    }

    public String getAccountStatementAppearance() {
        return accountStatementAppearance;
    }

    public void setAccountStatementAppearance(String accountStatementAppearance) {
        this.accountStatementAppearance = accountStatementAppearance;
    }

    public String getJournalsDailyBalanced() {
        return journalsDailyBalanced;
    }

    public void setJournalsDailyBalanced(String journalsDailyBalanced) {
        this.journalsDailyBalanced = journalsDailyBalanced;
    }    
    
    /**
     * @return the AllowSubsequentCacheAdministration
     */
    public String getAllowSubsequentCacheAdministration() {
        return AllowSubsequentCacheAdministration;
    }

    /**
     * @param AllowSubsequentCacheAdministration the AllowSubsequentCacheAdministration to set
     */
    public void setAllowSubsequentCacheAdministration(String AllowSubsequentCacheAdministration) {
        this.AllowSubsequentCacheAdministration = AllowSubsequentCacheAdministration;
    }

    /**
     * @return the workWithAccounts
     */
    public String getWorkWithAccounts() {
        return workWithAccounts;
    }

    /**
     * @param workWithAccounts the workWithAccounts to set
     */
    public void setWorkWithAccounts(String workWithAccounts) {
        this.workWithAccounts = workWithAccounts;
    }

    /**
     * @return the noteSreceivablesType
     */
    public String getNoteSreceivablesType() {
        return noteSreceivablesType;
    }

    /**
     * @param noteSreceivablesType the noteSreceivablesType to set
     */
    public void setNoteSreceivablesType(String noteSreceivablesType) {
        this.noteSreceivablesType = noteSreceivablesType;
    }
}

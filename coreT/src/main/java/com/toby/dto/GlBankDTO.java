/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.dto;

import java.math.BigDecimal;
import java.util.Date;

public class GlBankDTO extends BaseEntityDTO {

    private String name;
    private String code;
    private BigDecimal openinngBalance;
    private Date dateOpeninngBalance;
    private Integer accountId;
    private Integer type;
    private Integer currencyId;
    

    public GlBankDTO() {
    }

    public GlBankDTO(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }


    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GlBankDTO)) {
            return false;
        }
        GlBankDTO other = (GlBankDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + " " + code;
    }

    public BigDecimal getOpeninngBalance() {
        return openinngBalance;
    }

    public void setOpeninngBalance(BigDecimal openinngBalance) {
        this.openinngBalance = openinngBalance;
    }

    public Date getDateOpeninngBalance() {
        return dateOpeninngBalance;
    }

    public void setDateOpeninngBalance(Date dateOpeninngBalance) {
        this.dateOpeninngBalance = dateOpeninngBalance;
    }

}

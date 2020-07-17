/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.views;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Toby
 */
@Entity
@Table(name = " production_transaction_of_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductionTransactionOfUser.findAll", query = "SELECT p FROM ProductionTransactionOfUser p"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByRowNum", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.rowNum = :rowNum"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByTotalNumber", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.totalNumber = :totalNumber"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByNumberExcute", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.numberExcute = :numberExcute"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByNumberRemain", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.numberRemain = :numberRemain"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByProductionStagesId", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.productionStagesId = :productionStagesId"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByProductionStageCost", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.productionStageCost = :productionStageCost"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByDate", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.date = :date"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByItemId", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.itemId = :itemId"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByItemName", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.itemName = :itemName"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByItemCode", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.itemCode = :itemCode"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByInvPurchaseInvoiceId", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.invPurchaseInvoiceId = :invPurchaseInvoiceId"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByInvPurchaseInvoiceserial", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.invPurchaseInvoiceserial = :invPurchaseInvoiceserial"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByProductionLine", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.productionLine = :productionLine"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByProductionLineId", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.productionLineId = :productionLineId"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByBranchId", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.branchId = :branchId"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByUserId", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.userId = :userId"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByUserName", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.userName = :userName"),
    @NamedQuery(name = "ProductionTransactionOfUser.findByPrice", query = "SELECT p FROM ProductionTransactionOfUser p WHERE p.price = :price")})
public class ProductionTransactionOfUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "rowNum")
    private Integer rowNum;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "totalNumber")
    private BigDecimal totalNumber;
    @Column(name = "numberExcute")
    private BigDecimal numberExcute;
    @Column(name = "numberRemain")
    private BigDecimal numberRemain;
    @Column(name = "productionStagesId")
    private Integer productionStagesId;
    @Column(name = "productionStageCost")
    private BigDecimal productionStageCost;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "itemId")
    private Integer itemId;
    @Size(max = 450)
    @Column(name = "itemName")
    private String itemName;
    @Column(name = "stage_name")
    private String stageName;
    @Size(max = 45)
    @Column(name = "itemCode")
    private String itemCode;
    @Column(name = "invPurchaseInvoiceId")
    private Integer invPurchaseInvoiceId;
    @Column(name = "invPurchaseInvoiceserial")
    private Integer invPurchaseInvoiceserial;
    @Size(max = 450)
    @Column(name = "ProductionLine")
    private String productionLine;
    @Column(name = "ProductionLineId")
    private Integer productionLineId;
    @Column(name = "branchId")
    private Integer branchId;
    @Column(name = "userId")
    private Integer userId;
    @Size(max = 45)
    @Column(name = "userName")
    private String userName;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "userCode")
    private String  userCode;

    public ProductionTransactionOfUser() {
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public BigDecimal getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(BigDecimal totalNumber) {
        this.totalNumber = totalNumber;
    }

    public BigDecimal getNumberExcute() {
        return numberExcute;
    }

    public void setNumberExcute(BigDecimal numberExcute) {
        this.numberExcute = numberExcute;
    }

    public BigDecimal getNumberRemain() {
        return numberRemain;
    }

    public void setNumberRemain(BigDecimal numberRemain) {
        this.numberRemain = numberRemain;
    }

    public Integer getProductionStagesId() {
        return productionStagesId;
    }

    public void setProductionStagesId(Integer productionStagesId) {
        this.productionStagesId = productionStagesId;
    }

    public BigDecimal getProductionStageCost() {
        return productionStageCost;
    }

    public void setProductionStageCost(BigDecimal productionStageCost) {
        this.productionStageCost = productionStageCost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getInvPurchaseInvoiceId() {
        return invPurchaseInvoiceId;
    }

    public void setInvPurchaseInvoiceId(Integer invPurchaseInvoiceId) {
        this.invPurchaseInvoiceId = invPurchaseInvoiceId;
    }

    public Integer getInvPurchaseInvoiceserial() {
        return invPurchaseInvoiceserial;
    }

    public void setInvPurchaseInvoiceserial(Integer invPurchaseInvoiceserial) {
        this.invPurchaseInvoiceserial = invPurchaseInvoiceserial;
    }

    public String getProductionLine() {
        return productionLine;
    }

    public void setProductionLine(String productionLine) {
        this.productionLine = productionLine;
    }

    public Integer getProductionLineId() {
        return productionLineId;
    }

    public void setProductionLineId(Integer productionLineId) {
        this.productionLineId = productionLineId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the userCode
     */
    public String  getUserCode() {
        return userCode;
    }

    /**
     * @param userCode the userCode to set
     */
    public void setUserCode(String  userCode) {
        this.userCode = userCode;
    }

    /**
     * @return the stageName
     */
    public String getStageName() {
        return stageName;
    }

    /**
     * @param stageName the stageName to set
     */
    public void setStageName(String stageName) {
        this.stageName = stageName;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.entity;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amr
 */
@Entity
@Table(name = "inv_purchase_invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvPurchaseInvoice.findAll", query = "SELECT i FROM InvPurchaseInvoice i")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findById", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.id = :id")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByDate", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.date = :date")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByRate", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.rate = :rate")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByPaymentType", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.paymentType = :paymentType")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByAmountInvSupplier", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.amountInvSupplier = :amountInvSupplier")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByDueDate", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.dueDate = :dueDate")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findBySupplierInvoiceNumber", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.supplierInvoiceNumber = :supplierInvoiceNumber")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByDiscount", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.discount = :discount")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByDiscountType", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.discountType = :discountType")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByPostFlag", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.postFlag = :postFlag")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByCreationDate", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.creationDate = :creationDate")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByModificationDate", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.modificationDate = :modificationDate")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByType", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.type = :type")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByRemarks", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.remarks = :remarks")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByDuePeriod", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.dueperiod = :dueperiod")
    ,
    @NamedQuery(name = "InvPurchaseInvoice.findByRecieveDate", query = "SELECT i FROM InvPurchaseInvoice i WHERE i.recievedate = :recievedate")})
public class InvPurchaseInvoice extends BaseEntity {

    /**
     * @return the paymentType
     */
    public Integer getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rate")
    private BigDecimal rate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "payment_type")
    private Integer paymentType;
    @Column(name = "status")
    private Integer status;
    @Column(name = "actual_weight")
    private BigDecimal actualWeight;
    @Column(name = "price_kilo")
    private BigDecimal priceKilo;
    @Column(name = "total_actual_weight")
    private BigDecimal totalActualWeight;
    @Column(name = "amount_inv_supplier")
    private BigDecimal amountInvSupplier;
    @Column(name = "due_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @Size(max = 45)
    @Column(name = "supplier_invoice_number")
    private String supplierInvoiceNumber;
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "discount_type")
    private Integer discountType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "post_flag")
    private Integer postFlag;
    @Column(name = "tax_flag")
    private Boolean taxflag;
    @Column(name = "tax_disc_flag")
    private Boolean taxdiscflag;
    @Column(name = "tax_disc_value")
    private BigDecimal taxdiscvalue;
    @Column(name = "tax_flag_final")
    private Boolean taxFlagFinal;
    @Column(name = "type")
    private Boolean type;
    @Size(max = 450)
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "extra_cost")
    private BigDecimal extraCost;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invPurchaseInvoiceId")
    private Collection<InvPurchaseInvoiceDetail> invPurchaseInvoiceDetailCollection;
    @OneToMany(mappedBy = "purchaseInvoiceId")
    private Collection<InvReturnPurchase> invReturnPurchaseCollection;
    @OneToMany(mappedBy = "invoiceId")
    private Collection<GlBankTransaction> glBankTransactionCollection;
    @JoinColumn(name = "admin_unit_id", referencedColumnName = "id")
    @ManyToOne
    private GlAdminUnit adminUnitId;
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ManyToOne
    private GlAccount accountId;
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @ManyToOne
    private Branch branchId;
    @JoinColumn(name = "cost_center_id", referencedColumnName = "id")
    @ManyToOne
    private CostCenter costCenterId;
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    @ManyToOne
    private Currency currencyId;
    @JoinColumn(name = "inv_delegator_id", referencedColumnName = "id")
    @ManyToOne
    private InventoryDelegator invDelegatorId;
    @JoinColumn(name = "inv_inventory_id", referencedColumnName = "id")
    @ManyToOne
    private InvInventory invInventoryId;
    
    @JoinColumn(name = "galary_Id", referencedColumnName = "id")
    @ManyToOne
    private InvInventory galaryId;
    
    @JoinColumn(name = "organization_site_id", referencedColumnName = "id")
    @ManyToOne
    private InvOrganizationSite organizationSiteId;
    @JoinColumn(name = "gl_bank_id", referencedColumnName = "id")
    @ManyToOne
    private GlBank glBankId;
    @JoinColumn(name = "document_type_id", referencedColumnName = "id")
    @ManyToOne
    private Symbol documentTypeId;
    @JoinColumn(name = "general_journal_id", referencedColumnName = "id")
    @ManyToOne
    private GeneralJournal generalJournalId;
    @Column(name = "supplier_invoice_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date supplierInvoiceDate;

    @Column(name = "serial")
    private Integer serial;

    @Column(name = "serial_tax")
    private Integer serialTax;
    
    @Column(name = "due_period")
    private Integer dueperiod;
    
    @Column(name = "recieve_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recievedate;
    @Column(name = "recieved")
    private Integer recieved;
    
    @Column(name = "proof")
    private Integer proof;
    @Column(name = "customer_accept")
    private Integer customeraccept;
    @Column(name = "is_deleted")
    private Integer isdeleted;
    @Column(name = "price_type")
    private Integer pricetype;
            
    public InvPurchaseInvoice() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmountInvSupplier() {
        return amountInvSupplier;
    }

    public void setAmountInvSupplier(BigDecimal amountInvSupplier) {
        this.amountInvSupplier = amountInvSupplier;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getSupplierInvoiceNumber() {
        return supplierInvoiceNumber;
    }

    public void setSupplierInvoiceNumber(String supplierInvoiceNumber) {
        this.supplierInvoiceNumber = supplierInvoiceNumber;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public Integer getPostFlag() {
        return postFlag;
    }

    public void setPostFlag(Integer postFlag) {
        this.postFlag = postFlag;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @XmlTransient
    public Collection<InvPurchaseInvoiceDetail> getInvPurchaseInvoiceDetailCollection() {
        return invPurchaseInvoiceDetailCollection;
    }

    public void setInvPurchaseInvoiceDetailCollection(Collection<InvPurchaseInvoiceDetail> invPurchaseInvoiceDetailCollection) {
        this.invPurchaseInvoiceDetailCollection = invPurchaseInvoiceDetailCollection;
    }

    @XmlTransient
    public Collection<InvReturnPurchase> getInvReturnPurchaseCollection() {
        return invReturnPurchaseCollection;
    }

    public void setInvReturnPurchaseCollection(Collection<InvReturnPurchase> invReturnPurchaseCollection) {
        this.invReturnPurchaseCollection = invReturnPurchaseCollection;
    }

    @XmlTransient
    public Collection<GlBankTransaction> getGlBankTransactionCollection() {
        return glBankTransactionCollection;
    }

    public void setGlBankTransactionCollection(Collection<GlBankTransaction> glBankTransactionCollection) {
        this.glBankTransactionCollection = glBankTransactionCollection;
    }

    public GlAdminUnit getAdminUnitId() {
        return adminUnitId;
    }

    public void setAdminUnitId(GlAdminUnit adminUnitId) {
        this.adminUnitId = adminUnitId;
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

    public CostCenter getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(CostCenter costCenterId) {
        this.costCenterId = costCenterId;
    }

    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
    }

    public InventoryDelegator getInvDelegatorId() {
        return invDelegatorId;
    }

    public void setInvDelegatorId(InventoryDelegator invDelegatorId) {
        this.invDelegatorId = invDelegatorId;
    }

    public InvInventory getInvInventoryId() {
        return invInventoryId;
    }

    public void setInvInventoryId(InvInventory invInventoryId) {
        this.invInventoryId = invInventoryId;
    }

    public InvOrganizationSite getOrganizationSiteId() {
        return organizationSiteId;
    }

    public void setOrganizationSiteId(InvOrganizationSite organizationSiteId) {
        this.organizationSiteId = organizationSiteId;
    }

    public GlBank getGlBankId() {
        return glBankId;
    }

    public void setGlBankId(GlBank glBankId) {
        this.glBankId = glBankId;
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
        if (!(object instanceof InvPurchaseInvoice)) {
            return false;
        }
        InvPurchaseInvoice other = (InvPurchaseInvoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return serial != null ? serial.toString() : "";
    }

    /**
     * @return the extraCost
     */
    public BigDecimal getExtraCost() {
        return extraCost;
    }

    /**
     * @param extraCost the extraCost to set
     */
    public void setExtraCost(BigDecimal extraCost) {
        this.extraCost = extraCost;
    }

    public Symbol getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Symbol documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Boolean getTaxflag() {
        return taxflag;
    }

    public void setTaxflag(Boolean taxflag) {
        this.taxflag = taxflag;
    }

    public Date getSupplierInvoiceDate() {
        return supplierInvoiceDate;
    }

    public void setSupplierInvoiceDate(Date supplierInvoiceDate) {
        this.supplierInvoiceDate = supplierInvoiceDate;
    }

    /**
     * @return the serialTax
     */
    public Integer getSerialTax() {
        return serialTax;
    }

    /**
     * @param serialTax the serialTax to set
     */
    public void setSerialTax(Integer serialTax) {
        this.serialTax = serialTax;
    }

    /**
     * @return the taxFlagFinal
     */
    public Boolean getTaxFlagFinal() {
        return taxFlagFinal;
    }

    /**
     * @param taxFlagFinal the taxFlagFinal to set
     */
    public void setTaxFlagFinal(Boolean taxFlagFinal) {
        this.taxFlagFinal = taxFlagFinal;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the actualWeight
     */
    public BigDecimal getActualWeight() {
        return actualWeight;
    }

    /**
     * @param actualWeight the actualWeight to set
     */
    public void setActualWeight(BigDecimal actualWeight) {
        this.actualWeight = actualWeight;
    }

    /**
     * @return the priceKilo
     */
    public BigDecimal getPriceKilo() {
        return priceKilo;
    }

    /**
     * @param priceKilo the priceKilo to set
     */
    public void setPriceKilo(BigDecimal priceKilo) {
        this.priceKilo = priceKilo;
    }

    /**
     * @return the totalActualWeight
     */
    public BigDecimal getTotalActualWeight() {
        return totalActualWeight;
    }

    /**
     * @param totalActualWeight the totalActualWeight to set
     */
    public void setTotalActualWeight(BigDecimal totalActualWeight) {
        this.totalActualWeight = totalActualWeight;
    }

    /**
     * @return the generalJournalId
     */
    public GeneralJournal getGeneralJournalId() {
        return generalJournalId;
    }

    /**
     * @param generalJournalId the generalJournalId to set
     */
    public void setGeneralJournalId(GeneralJournal generalJournalId) {
        this.generalJournalId = generalJournalId;
    }

    /**
     * @return the taxdiscflag
     */
    public Boolean getTaxdiscflag() {
        return taxdiscflag;
    }

    /**
     * @param taxdiscflag the taxdiscflag to set
     */
    public void setTaxdiscflag(Boolean taxdiscflag) {
        this.taxdiscflag = taxdiscflag;
    }

    /**
     * @return the taxdiscvalue
     */
    public BigDecimal getTaxdiscvalue() {
        return taxdiscvalue;
    }

    /**
     * @param taxdiscvalue the taxdiscvalue to set
     */
    public void setTaxdiscvalue(BigDecimal taxdiscvalue) {
        this.taxdiscvalue = taxdiscvalue;
    }

    /**
     * @return the dueperiod
     */
    public Integer getDueperiod() {
        return dueperiod;
    }

    /**
     * @param dueperiod the dueperiod to set
     */
    public void setDueperiod(Integer dueperiod) {
        this.dueperiod = dueperiod;
    }

    /**
     * @return the recievedate
     */
    public Date getRecievedate() {
        return recievedate;
    }

    /**
     * @param recievedate the recievedate to set
     */
    public void setRecievedate(Date recievedate) {
        this.recievedate = recievedate;
    }

    /**
     * @return the recieved
     */
    public Integer getRecieved() {
        return recieved;
    }

    /**
     * @param recieved the recieved to set
     */
    public void setRecieved(Integer recieved) {
        this.recieved = recieved;
    }

    /**
     * @return the proof
     */
    public Integer getProof() {
        return proof;
    }

    /**
     * @param proof the proof to set
     */
    public void setProof(Integer proof) {
        this.proof = proof;
    }

    /**
     * @return the customeraccept
     */
    public Integer getCustomeraccept() {
        return customeraccept;
    }

    /**
     * @param customeraccept the customeraccept to set
     */
    public void setCustomeraccept(Integer customeraccept) {
        this.customeraccept = customeraccept;
    }

    /**
     * @return the isdeleted
     */
    public Integer getIsdeleted() {
        return isdeleted;
    }

    /**
     * @param isdeleted the isdeleted to set
     */
    public void setIsdeleted(Integer isdeleted) {
        this.isdeleted = isdeleted;
    }


    public Integer getPricetype() {
        return pricetype;
    }

    /**
     * @param pricetype the pricetype to set
     */
    public void setPricetype(Integer pricetype) {
        this.pricetype = pricetype;
    }

    /**
     * @return the gallaryId
     */
    public InvInventory getGalaryId() {
        return galaryId;
    }

    /**
     * @param gallaryId the gallaryId to set
     */
    public void setGalaryId(InvInventory gallaryId) {
        this.galaryId = gallaryId;
    }

}

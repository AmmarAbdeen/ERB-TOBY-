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
 * @author hq003
 */
@Entity
@Table(name = "inv_qotation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvQotation.findAll", query = "SELECT i FROM InvQotation i"),
    @NamedQuery(name = "InvQotation.findById", query = "SELECT i FROM InvQotation i WHERE i.id = :id"),
    @NamedQuery(name = "InvQotation.findByDate", query = "SELECT i FROM InvQotation i WHERE i.date = :date"),
    @NamedQuery(name = "InvQotation.findByEndDate", query = "SELECT i FROM InvQotation i WHERE i.endDate = :endDate"),
    @NamedQuery(name = "InvQotation.findByRemark", query = "SELECT i FROM InvQotation i WHERE i.remark = :remark"),
    
    @NamedQuery(name = "InvQotation.findByCreationDate", query = "SELECT i FROM InvQotation i WHERE i.creationDate = :creationDate"),
    @NamedQuery(name = "InvQotation.findByModificationDate", query = "SELECT i FROM InvQotation i WHERE i.modificationDate = :modificationDate"),
    @NamedQuery(name = "InvQotation.findByPostFlag", query = "SELECT i FROM InvQotation i WHERE i.postFlag = :postFlag")})
public class InvQotation extends BaseEntity {

    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Size(max = 450)
    @Column(name = "remark")
    private String remark;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
     @Column(name = "discount_value")
    private BigDecimal discountValue;
    @Column(name = "discount_percentage")
    private BigDecimal discountPercentage;
     @Column(name = "total_discount")
    private BigDecimal totalDiscount;
    @Column(name = "post_flag")
    private Integer postFlag;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "qotationId")
    private Collection<InvQotationDetail> invQotationDetailCollection;
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @ManyToOne
    private Branch branchId;
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne
    private InvOrganizationSite customerId;
    @JoinColumn(name = "delegator_id", referencedColumnName = "id")
    @ManyToOne
    private InventoryDelegator delegatorId;
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    @ManyToOne
    private InvOrganizationSite supplierId;
    @Column(name = "serial")
    private Integer serial;
    @Column(name = "status")
    private Integer status;

    public InvQotation() {
    }

    public InvQotation(Integer id) {
        this.id = id;
    }

    public InvQotation(Integer id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

   
    public Integer getPostFlag() {
        return postFlag;
    }

    public void setPostFlag(Integer postFlag) {
        this.postFlag = postFlag;
    }

    @XmlTransient
    public Collection<InvQotationDetail> getInvQotationDetailCollection() {
        return invQotationDetailCollection;
    }

    public void setInvQotationDetailCollection(Collection<InvQotationDetail> invQotationDetailCollection) {
        this.invQotationDetailCollection = invQotationDetailCollection;
    }

    public Branch getBranchId() {
        return branchId;
    }

    public void setBranchId(Branch branchId) {
        this.branchId = branchId;
    }

    public InvOrganizationSite getCustomerId() {
        return customerId;
    }

    public void setCustomerId(InvOrganizationSite customerId) {
        this.customerId = customerId;
    }

    public InvOrganizationSite getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(InvOrganizationSite supplierId) {
        this.supplierId = supplierId;
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
        if (!(object instanceof InvQotation)) {
            return false;
        }
        InvQotation other = (InvQotation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.toby.entity.InvQotation[ id=" + id + " ]";
    }

    public InventoryDelegator getDelegatorId() {
        return delegatorId;
    }

    public void setDelegatorId(InventoryDelegator delegatorId) {
        this.delegatorId = delegatorId;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
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
     * @return the discountValue
     */
    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    /**
     * @param discountValue the discountValue to set
     */
    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    /**
     * @return the discountPercentage
     */
    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    /**
     * @param discountPercentage the discountPercentage to set
     */
    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    /**
     * @return the totalDiscount
     */
    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    /**
     * @param totalDiscount the totalDiscount to set
     */
    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

}

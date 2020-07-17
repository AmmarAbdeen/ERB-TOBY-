/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.views;

import java.io.Serializable;
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
@Table(name = "invoice_movement_report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvoiceMovementReport.findAll", query = "SELECT i FROM InvoiceMovementReport i"),
    @NamedQuery(name = "InvoiceMovementReport.findByRowNum", query = "SELECT i FROM InvoiceMovementReport i WHERE i.rowNum = :rowNum"),
    @NamedQuery(name = "InvoiceMovementReport.findByInvoice", query = "SELECT i FROM InvoiceMovementReport i WHERE i.invoice = :invoice"),
    @NamedQuery(name = "InvoiceMovementReport.findByInventoryName", query = "SELECT i FROM InvoiceMovementReport i WHERE i.inventoryName = :inventoryName"),
    @NamedQuery(name = "InvoiceMovementReport.findByClientName", query = "SELECT i FROM InvoiceMovementReport i WHERE i.clientName = :clientName"),
    @NamedQuery(name = "InvoiceMovementReport.findByDeliveryDate", query = "SELECT i FROM InvoiceMovementReport i WHERE i.deliveryDate = :deliveryDate"),
    @NamedQuery(name = "InvoiceMovementReport.findByClientDate", query = "SELECT i FROM InvoiceMovementReport i WHERE i.clientDate = :clientDate"),
    @NamedQuery(name = "InvoiceMovementReport.findByGalaryDate", query = "SELECT i FROM InvoiceMovementReport i WHERE i.galaryDate = :galaryDate"),
    @NamedQuery(name = "InvoiceMovementReport.findByFactoryDate", query = "SELECT i FROM InvoiceMovementReport i WHERE i.factoryDate = :factoryDate")})
public class InvoiceMovementReport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "rowNum")
    private Integer rowNum;
    @Column(name = "invoice")
    private Integer invoice;
    @Size(max = 45)
    @Column(name = "inventory_name")
    private String inventoryName;
    @Size(max = 45)
    @Column(name = "client_name")
    private String clientName;
    @Column(name = "delivery_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;
    @Column(name = "client_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date clientDate;
    @Column(name = "galary_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date galaryDate;
    @Column(name = "factory_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date factoryDate;

    public InvoiceMovementReport() {
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getClientDate() {
        return clientDate;
    }

    public void setClientDate(Date clientDate) {
        this.clientDate = clientDate;
    }

    public Date getGalaryDate() {
        return galaryDate;
    }

    public void setGalaryDate(Date galaryDate) {
        this.galaryDate = galaryDate;
    }

    public Date getFactoryDate() {
        return factoryDate;
    }

    public void setFactoryDate(Date factoryDate) {
        this.factoryDate = factoryDate;
    }
    
}

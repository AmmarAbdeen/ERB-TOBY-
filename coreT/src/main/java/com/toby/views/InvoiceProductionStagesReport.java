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


@Entity
@Table(name = "invoice_production_stages_report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvoiceProductionStagesReport.findAll", query = "SELECT i FROM InvoiceProductionStagesReport i"),
    @NamedQuery(name = "InvoiceProductionStagesReport.findByRowNum", query = "SELECT i FROM InvoiceProductionStagesReport i WHERE i.rowNum = :rowNum"),
    @NamedQuery(name = "InvoiceProductionStagesReport.findByInvoice", query = "SELECT i FROM InvoiceProductionStagesReport i WHERE i.invoice = :invoice"),
    @NamedQuery(name = "InvoiceProductionStagesReport.findByInvInvoice", query = "SELECT i FROM InvoiceProductionStagesReport i WHERE i.invInvoice = :invInvoice"),
    @NamedQuery(name = "InvoiceProductionStagesReport.findByDate", query = "SELECT i FROM InvoiceProductionStagesReport i WHERE i.date = :date"),
    @NamedQuery(name = "InvoiceProductionStagesReport.findByClient", query = "SELECT i FROM InvoiceProductionStagesReport i WHERE i.client = :client"),
    @NamedQuery(name = "InvoiceProductionStagesReport.findByClientName", query = "SELECT i FROM InvoiceProductionStagesReport i WHERE i.clientName = :clientName"),
    @NamedQuery(name = "InvoiceProductionStagesReport.findByQuantity", query = "SELECT i FROM InvoiceProductionStagesReport i WHERE i.quantity = :quantity"),
    @NamedQuery(name = "InvoiceProductionStagesReport.findByStage", query = "SELECT i FROM InvoiceProductionStagesReport i WHERE i.stage = :stage"),
    @NamedQuery(name = "InvoiceProductionStagesReport.findByEmpName", query = "SELECT i FROM InvoiceProductionStagesReport i WHERE i.empName = :empName")})
public class InvoiceProductionStagesReport implements Serializable {
    @Id
    @Column(name = "rowNum")
    private Integer rowNum;
    @Column(name = "invoice")
    private Integer invoice;
    @Column(name = "inv_invoice")
    private Integer invInvoice;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Size(max = 45)
    @Column(name = "client")
    private String client;
    @Size(max = 45)
    @Column(name = "client_name")
    private String clientName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "quantity")
    private BigDecimal quantity;
    @Size(max = 45)
    @Column(name = "stage")
    private String stage;
    @Size(max = 45)
    @Column(name = "emp_name")
    private String empName;

    public InvoiceProductionStagesReport() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    /**
     * @return the invInvoice
     */
    public Integer getInvInvoice() {
        return invInvoice;
    }

    /**
     * @param invInvoice the invInvoice to set
     */
    public void setInvInvoice(Integer invInvoice) {
        this.invInvoice = invInvoice;
    }
    
}

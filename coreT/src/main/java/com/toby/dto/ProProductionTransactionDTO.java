package com.toby.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProProductionTransactionDTO extends BaseEntityDTO {
     
    private Integer invPurchaseInvoiceId;
    private Integer invPurchaseInvoiceSerial;
    private String inOrganizationSiteName;
    private Boolean hiddenFinished;
    private Integer status;
    private String statusName;
    private Integer proProductionId;
    private String stageName;
    private String employeename;
    private BigDecimal quantity;
    private BigDecimal stagePrice;
    private BigDecimal total;
    private List<ProProductionProductNumberDTO> productNumberDTOList;

    
    

    public String getInOrganizationSiteName() {
        return inOrganizationSiteName;
    }

    public void setInOrganizationSiteName(String inOrganizationSiteName) {
        this.inOrganizationSiteName = inOrganizationSiteName;
    }

    
    public Boolean getHiddenFinished() {
        return hiddenFinished;
    }

    
    public void setHiddenFinished(Boolean hiddenFinished) {
        this.hiddenFinished = hiddenFinished;
    }

    
    public Integer getInvPurchaseInvoiceId() {
        return invPurchaseInvoiceId;
    }

    
    public void setInvPurchaseInvoiceId(Integer invPurchaseInvoiceId) {
        this.invPurchaseInvoiceId = invPurchaseInvoiceId;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }

    
    public Integer getProProductionId() {
        return proProductionId;
    }

    
    public void setProProductionId(Integer proProductionId) {
        this.proProductionId = proProductionId;
    } 

    
    public String getEmployeename() {
        return employeename;
    }

    
    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    
    public BigDecimal getQuantity() {
        return quantity;
    }

    
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    
    public BigDecimal getStagePrice() {
        return stagePrice;
    }

    
    public void setStagePrice(BigDecimal stagePrice) {
        this.stagePrice = stagePrice;
    }

    
    public BigDecimal getTotal() {
        return total;
    }

    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the productNumberDTOList
     */
    public List<ProProductionProductNumberDTO> getProductNumberDTOList() {
       
        return productNumberDTOList;
    }

    /**
     * @param productNumberDTOList the productNumberDTOList to set
     */
    public void setProductNumberDTOList(List<ProProductionProductNumberDTO> productNumberDTOList) {
        this.productNumberDTOList = productNumberDTOList;
    }

    /**
     * @return the invPurchaseInvoiceSerial
     */
    public Integer getInvPurchaseInvoiceSerial() {
        return invPurchaseInvoiceSerial;
    }

    /**
     * @param invPurchaseInvoiceSerial the invPurchaseInvoiceSerial to set
     */
    public void setInvPurchaseInvoiceSerial(Integer invPurchaseInvoiceSerial) {
        this.invPurchaseInvoiceSerial = invPurchaseInvoiceSerial;
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

    /**
     * @return the statusName
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName the statusName to set
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
  
}

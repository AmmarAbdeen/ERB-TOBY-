/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.invsalesinvoice;

import com.toby.businessservice.InvPurchaseInvoiceService;
import com.toby.dto.*;
import com.toby.toby.BaseFormBean;
import com.toby.toby.UserData;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author WIN7
 */
@Named(value = "invsalesinvoicelistMB")
@ViewScoped

public class InvSalesInvoiceListMB extends BaseFormBean {

    private InvPurchaseInvoiceDTO1 invPurchaseInvoiceDTO;
    private List<InvPurchaseInvoiceDTO1> invPurchaseInvoiceDTOList;
    private Integer invPurchaseInvoiceId;
    
    @EJB
    private InvPurchaseInvoiceService invPurchaseInvoiceService; 
   
    @PostConstruct
    public void init() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        setUserData((UserData) context.getSessionMap().get("userLogInData"));
        
    }
    public String goToAdd(){
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.getSessionMap().put("ScreenMode", "addInvoice");
//            exit("../invsalesinvoice/invsalesinvoiceform.xhtml");
            exit("../invsalesinvoice/invsalesinvoicedtoform.xhtml");
            return "";

        } catch (Exception e) {
            saveError(e, "invsalesinvoicelistMB", "goToAdd");
            return null;
        }
    }
    public String goToEdit() {
        System.out.println("");
        try {
            if (invPurchaseInvoiceId != null && invPurchaseInvoiceId > 0) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.getSessionMap().put("invPurchaseInvoiceId", invPurchaseInvoiceId);
                context.getSessionMap().put("ScreenMode", "Edit");
                exit("../invsalesinvoice/invsalesinvoiceform.xhtml");
                return "";
            } else {
                return "";
            }
        } catch (Exception e) {
            saveError(e, "invsalesinvoicelistMB", "goToEdit");
            return null;
        }
    }
    public void removeRow() {
        invPurchaseInvoiceDTOList.remove(invPurchaseInvoiceDTO);
        invPurchaseInvoiceService.deleteInvPurchaseInvoiceDTO(invPurchaseInvoiceDTO, getUserData().getUser());
//        invPurchaseInvoiceService.deleteInvPurchaseInvoiceDetailDTO(invPurchaseInvoiceDTO,  getUserData().getUser());
    }

    @Override
    public String save() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getScreenName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the invPurchaseInvoiceDTO
     */
    public InvPurchaseInvoiceDTO1 getInvPurchaseInvoiceDTO() {
        return invPurchaseInvoiceDTO;
    }

    /**
     * @param invPurchaseInvoiceDTO the invPurchaseInvoiceDTO to set
     */
    public void setInvPurchaseInvoiceDTO(InvPurchaseInvoiceDTO1 invPurchaseInvoiceDTO) {
        this.invPurchaseInvoiceDTO = invPurchaseInvoiceDTO;
    }

    /**
     * @return the invPurchaseInvoiceDTOList
     */
    public List<InvPurchaseInvoiceDTO1> getInvPurchaseInvoiceDTOList() {
        if(invPurchaseInvoiceDTOList == null || invPurchaseInvoiceDTOList.isEmpty()){
            invPurchaseInvoiceDTOList = invPurchaseInvoiceService.findInvPurchaseInvoiceDTOList(getUserData().getUser());
        }
        return invPurchaseInvoiceDTOList;
    }

    /**
     * @param invPurchaseInvoiceDTOList the invPurchaseInvoiceDTOList to set
     */
    public void setInvPurchaseInvoiceDTOList(List<InvPurchaseInvoiceDTO1> invPurchaseInvoiceDTOList) {
        this.invPurchaseInvoiceDTOList = invPurchaseInvoiceDTOList;
    }

    

    /**
     * @return the invPurchaseInvoiceId
     */
    public Integer getInvPurchaseInvoiceId() {
        return invPurchaseInvoiceId;
    }

    /**
     * @param invPurchaseInvoiceId the invPurchaseInvoiceId to set
     */
    public void setInvPurchaseInvoiceId(Integer invPurchaseInvoiceId) {
        this.invPurchaseInvoiceId = invPurchaseInvoiceId;
    }

   

}

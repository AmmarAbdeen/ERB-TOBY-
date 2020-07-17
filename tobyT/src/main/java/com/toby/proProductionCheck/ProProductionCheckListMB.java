
package com.toby.proProductionCheck;

import com.toby.businessservice.ProProductionTransactionService;
import com.toby.dto.ProProductionTransactionDTO;
import com.toby.toby.BaseFormBean;
import com.toby.toby.UserData;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;


@Named(value = "proProductionCheckListMB")
@ViewScoped
public class ProProductionCheckListMB extends BaseFormBean{
    
    private ProProductionTransactionDTO proProductionTransactionDTOSelected;
    private List<ProProductionTransactionDTO> proProductionTransactionDTOs;
    
    @EJB
    private ProProductionTransactionService proProductionTransactionService;

    
    public ProProductionCheckListMB() {
    }

    @Override
    public String save() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    @PostConstruct
    public void init() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        setUserData((UserData) context.getSessionMap().get("userLogInData"));
        setScreenMode((String) context.getSessionMap().get("ScreenMode"));
        if (getScreenMode() != null && getScreenMode().equalsIgnoreCase("save")) {
            try {
                proProductionTransactionDTOs=proProductionTransactionService.getAllCheckedInvoicePurchase(getUserData().getUser());
               
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void delete() {
        proProductionTransactionService.deleteProProductionTransaction(proProductionTransactionDTOSelected, getUserData().getUser());
        proProductionTransactionDTOs = proProductionTransactionService.getAllCheckedInvoicePurchase(getUserData().getUser());
    }

    public String goToForm() {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.getSessionMap().put("ScreenMode", "Add");
            exit("../proProductionCheck/proProductionCheck.xhtml");
            return "";
        } catch (Exception e) {
            saveError(e, "proProductionCheckListMB", "goToAdd");
            return null;
        }
    }

    public String goToEdit() {
        try {
            if (proProductionTransactionDTOSelected != null && proProductionTransactionDTOSelected.getId() > 0) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.getSessionMap().put("proProductionTransactionDTOSelected", proProductionTransactionDTOSelected.getId());
                context.getSessionMap().put("ScreenMode", "Edit");
                exit("../proProductionCheck/proProductionCheck.xhtml");
                return "";
            } else {
                return "";
            }
        } catch (Exception e) {
            saveError(e, "proProductionCheckListMB", "goToEdit");
            return null;
        }
    }

    
    @Override
    public void load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getScreenName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public ProProductionTransactionDTO getProProductionTransactionDTOSelected() {
        return proProductionTransactionDTOSelected;
    }

    
    public void setProProductionTransactionDTOSelected(ProProductionTransactionDTO proProductionTransactionDTOSelected) {
        this.proProductionTransactionDTOSelected = proProductionTransactionDTOSelected;
    }

   
    public List<ProProductionTransactionDTO> getProProductionTransactionDTOs() {
        if (proProductionTransactionDTOs == null || proProductionTransactionDTOs.isEmpty()) {
            proProductionTransactionDTOs = proProductionTransactionService.getAllCheckedInvoicePurchase(getUserData().getUser());
        }
        return proProductionTransactionDTOs;
    }

    
    public void setProProductionTransactionDTOs(List<ProProductionTransactionDTO> proProductionTransactionDTOs) {
        this.proProductionTransactionDTOs = proProductionTransactionDTOs;
    }
    
}

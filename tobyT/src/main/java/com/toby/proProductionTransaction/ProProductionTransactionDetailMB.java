package com.toby.proProductionTransaction;

import com.toby.businessservice.InvPurchaseInvoiceService;
import com.toby.businessservice.ProProductionTransactionService;
import com.toby.dto.InvInventoryTransactionDetailDTO;
import com.toby.dto.InvPurchaseInvoiceDTO1;
import com.toby.dto.InvPurchaseInvoiceDetailDTO;
import com.toby.dto.ProProductionTransactionDTO;
import com.toby.toby.BaseListBean;
import com.toby.toby.UserData;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "proProductionTransactionDetailMB")
@ViewScoped
public class ProProductionTransactionDetailMB extends BaseListBean {

    private List<InvPurchaseInvoiceDetailDTO> invPurchaseInvoiceDetailDTOs;
    private InvPurchaseInvoiceDTO1 invPurchaseInvoiceDTO;

    @EJB
    private ProProductionTransactionService proProductionTransactionService;

    public ProProductionTransactionDetailMB() {
    }

    @Override
    @PostConstruct
    public void init() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        setUserData((UserData) context.getSessionMap().get("userLogInData"));
        try {
            setInvPurchaseInvoiceDTO((InvPurchaseInvoiceDTO1) context.getSessionMap().get("invPurchaseInvoiceDTOSelected"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void filter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String goToAdd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String goToEdit() {
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

    public InvPurchaseInvoiceDTO1 getInvPurchaseInvoiceDTO() {
        if (invPurchaseInvoiceDTO == null) {
            invPurchaseInvoiceDTO = new InvPurchaseInvoiceDTO1();

        }
        return invPurchaseInvoiceDTO;
    }

    public void setInvPurchaseInvoiceDTO(InvPurchaseInvoiceDTO1 invPurchaseInvoiceDTO) {
        this.invPurchaseInvoiceDTO = invPurchaseInvoiceDTO;
    }

    public List<InvPurchaseInvoiceDetailDTO> getInvPurchaseInvoiceDetailDTOs() {
        if (invPurchaseInvoiceDetailDTOs == null || invPurchaseInvoiceDetailDTOs.isEmpty()) {
            invPurchaseInvoiceDetailDTOs = proProductionTransactionService.getInvPurchaseInvoiceDetailsByPurchaseId(getInvPurchaseInvoiceDTO().getId(), getUserData().getUser());
        }
        return invPurchaseInvoiceDetailDTOs;
    }

    public void setInvPurchaseInvoiceDetailDTOs(List<InvPurchaseInvoiceDetailDTO> invPurchaseInvoiceDetailDTOs) {
        this.invPurchaseInvoiceDetailDTOs = invPurchaseInvoiceDetailDTOs;
    }

}

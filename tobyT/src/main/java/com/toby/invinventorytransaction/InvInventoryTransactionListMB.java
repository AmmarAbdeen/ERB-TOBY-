/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.invinventorytransaction;

import com.toby.businessservice.InvInventoryTransactionDetailService;
import com.toby.businessservice.InvInventoryTransactionService;
import com.toby.dto.InvInventoryTransactionDTO;
import com.toby.entity.InvInventoryTransaction;
import com.toby.toby.BaseFormBean;
import com.toby.toby.UserData;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author H
 */
@Named(value = "invInventoryTransactionListMB")
@ViewScoped
public class InvInventoryTransactionListMB extends BaseFormBean implements Serializable {
    private InvInventoryTransactionDTO invInventoryTransactionDTOSelected;
    private List<InvInventoryTransactionDTO> invInventoryTransactionDTOList;
    private InvInventoryTransaction invInventoryTransaction;
    
    private Integer invinventoryidselected;
   @EJB
   private InvInventoryTransactionDetailService invInventoryTransactionDetailService;
    @EJB
    private InvInventoryTransactionService invInventoryTransactionService;

    @Override
    @PostConstruct
    public void init() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        setUserData((UserData) context.getSessionMap().get("userLogInData"));
        getInvInventoryTransactionDTOList();

    }

   public void delete(){
     invInventoryTransactionDTOList.remove(invInventoryTransactionDTOSelected);
        invInventoryTransactionService.deleteInvInventoryTransaction(invInventoryTransactionDTOSelected, getUserData().getUser());
        invInventoryTransactionDetailService.deleteInvInventoryTransactionDetailList(invInventoryTransactionDTOSelected.getId(), getUserData().getUser());
   }
   public String goToAdd(){
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.getSessionMap().remove("ScreenMode");
            exit("../invinventorytransaction/invinventorytransactionForm.xhtml");
            return "";

        } catch (Exception e) {
            saveError(e, "invInventoryTransactionListMB", "goToAdd");
            return null;
        }
    }


 
    public String goToEdit() {
        try {
            if (invinventoryidselected != null && invinventoryidselected > 0) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.getSessionMap().put("invinventoryidselected", invinventoryidselected);
                context.getSessionMap().put("ScreenMode", "Edit");
                exit("../invinventorytransaction/invinventorytransactionForm.xhtml");
                return "";
            } else {
                return "";
            }
        } catch (Exception e) {
            saveError(e, "InvInventoryTransactionListMB", "goToAdd");
            return null;
        }
    }

    @Override
    public void load() {
        
    }

    @Override
    public String getScreenName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the invInventoryTransactionDTOList
     */
    public List<InvInventoryTransactionDTO> getInvInventoryTransactionDTOList() {
       
       
        if (invInventoryTransactionDTOList == null || invInventoryTransactionDTOList.isEmpty()) {
        //       Map<Integer,BigDecimal> map= new HashMap<>();
            invInventoryTransactionDTOList = invInventoryTransactionService.getInventoryTransactionList(getUserData().getUser());
//            List<InvInventoryTransactionDetailDTO> details = invInventoryTransactionDetailService.invInventoryTransactionDetails(getUserData().getUser());
//            for (InvInventoryTransactionDetailDTO invInventoryTransactionDetailDTO : details) {
//                if (map.get(invInventoryTransactionDetailDTO.getInventoryTransactionId().getId()) != null) {
//                    map.put(invInventoryTransactionDetailDTO.getInventoryTransactionId().getId(),
//                            map.get(invInventoryTransactionDetailDTO.getInventoryTransactionId().getId()).add(invInventoryTransactionDetailDTO.getQuantity()));
//                } else {
//                    map.put(invInventoryTransactionDetailDTO.getInventoryTransactionId().getId(), invInventoryTransactionDetailDTO.getQuantity());
//                }
//
//            }
//            for(InvInventoryTransactionDTO invInventoryTransactionDTO : invInventoryTransactionDTOList){
//              invInventoryTransactionDTO.setSumQuantity(map.get(invInventoryTransactionDTO.getId()));
//                }
//            
             
        
        }
        return invInventoryTransactionDTOList;
        
        }
    /**
     * @param invInventoryTransactionDTOList the invInventoryTransactionDTOList
     * to set
     */
    public void setInvInventoryTransactionDTOList(List<InvInventoryTransactionDTO> invInventoryTransactionDTOList) {
        this.invInventoryTransactionDTOList = invInventoryTransactionDTOList;
    }

    /**
     * @return the invinventoryidselected
     */
    public Integer getInvinventoryidselected() {
        return invinventoryidselected;
    }

    /**
     * @param invinventoryidselected the invinventoryidselected to set
     */
    public void setInvinventoryidselected(Integer invinventoryidselected) {
        this.invinventoryidselected = invinventoryidselected;
    }

    /**
     * @return the invInventoryTransactionDTOSelected
     */
    public InvInventoryTransactionDTO getInvInventoryTransactionDTOSelected() {
      
        return invInventoryTransactionDTOSelected;
    }

    /**
     * @param invInventoryTransactionDTOSelected the invInventoryTransactionDTOSelected to set
     */
    public void setInvInventoryTransactionDTOSelected(InvInventoryTransactionDTO invInventoryTransactionDTOSelected) {
        this.invInventoryTransactionDTOSelected = invInventoryTransactionDTOSelected;
    }

    @Override
    public String save() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the invInventoryTransactionDTOList
     */
}

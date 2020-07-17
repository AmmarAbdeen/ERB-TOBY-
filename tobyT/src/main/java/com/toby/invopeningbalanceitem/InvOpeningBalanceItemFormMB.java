/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.invopeningbalanceitem;

import com.toby.businessservice.InvItemService;
import com.toby.businessservice.InvOpeningBalanceItemService;
import com.toby.businessservice.TobyUserInventoryService;
import com.toby.converter.InvInventoryDTOConverter;
import com.toby.converter.ItemDTOConverter;
import com.toby.dto.InvInventoryDTO;
import com.toby.dto.InvItemDTO;
import com.toby.dto.InvOpenningBalanceItemDTO;
import com.toby.dto.InvOpenningBalanceItemDetailDTO;
import com.toby.toby.AutoComplete;
import com.toby.toby.BaseFormBean;
import com.toby.toby.UserData;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author hq002
 */
@Named(value = "InvOpeningBalanceItemFormMB")
@ViewScoped
public class InvOpeningBalanceItemFormMB extends BaseFormBean {

    private InvOpenningBalanceItemDTO invOpenningBalanceItemDTO;
    private InvOpenningBalanceItemDetailDTO invOpenningBalanceItemDetailDTOSelected;
    private List<InvOpenningBalanceItemDTO> invOpenningBalanceItemDTOList;
    private List<InvInventoryDTO> invInventoryDTOList;
    private List<InvItemDTO> invItemDTOList;
    private InvInventoryDTOConverter invInventoryDTOConverter;
    private ItemDTOConverter itemDTOConverter;

    @EJB
    private TobyUserInventoryService isagUserInventoryService;
    @EJB
    private InvItemService invItemService;
    @EJB
    private InvOpeningBalanceItemService invOpeningBalanceItemService;

    @Override
    @PostConstruct
    public void init() {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            setUserData((UserData) context.getSessionMap().get("userLogInData"));
            setScreenMode((String) context.getSessionMap().get("ScreenMode"));

        } catch (Exception e) {
            saveError(e, "InvOpeningBalanceItemFormMB", "init");
        }

    }

    public List<InvInventoryDTO> completeInventory(String query) {
        return AutoComplete.completeInventory(query, getInvInventoryDTOList(), invInventoryDTOConverter);

    }

    public List<InvItemDTO> completeInvItemData(String query) {
        return AutoComplete.completeInvItemData(query, getInvItemDTOList(), itemDTOConverter);

    }

    public void addRow() {

        if (validateDetails()) {
            InvOpenningBalanceItemDetailDTO invOpenningBalanceItemDetailDTO = new InvOpenningBalanceItemDetailDTO();
            invOpenningBalanceItemDetailDTO.setIndex(getIndex());
            invOpenningBalanceItemDTO.getInvOpenningBalanceItemDetailDTOList().add(invOpenningBalanceItemDetailDTO);
            invOpenningBalanceItemDetailDTOSelected = invOpenningBalanceItemDetailDTO;
            
        }

    }

    public boolean validateDetails() {
        return invOpeningBalanceItemService.validateDetails(invOpenningBalanceItemDTO);
    }

    public void validateQuantityColum() {
        invOpenningBalanceItemDTO = invOpeningBalanceItemService.validateQuantityColum(invOpenningBalanceItemDetailDTOSelected, invOpenningBalanceItemDTO);

    }

    public void updateSummition() {
        invOpenningBalanceItemDTO = invOpeningBalanceItemService.updateSummition(invOpenningBalanceItemDTO);
    }

    public void validatePriceColumn() {
        updateSummition();
        invOpenningBalanceItemDTO = invOpeningBalanceItemService.validatePriceColumn(invOpenningBalanceItemDetailDTOSelected, invOpenningBalanceItemDTO);

    }

    public void deleteInvQuotationDetail() {
        try {

            if (invOpenningBalanceItemDetailDTOSelected != null) {

                invOpenningBalanceItemDTO.getInvOpenningBalanceItemDetailDTOList().remove(invOpenningBalanceItemDetailDTOSelected);

                updateSummition();
                validatePriceColumn();
                validateQuantityColum();
            } else {
                errorMessage("يجب اختيار سطر للمسح");
            }

        } catch (Exception e) {
            saveError(e, "InvOpeningBalanceItemFormMB", "deleteInvQuotationDetail");
        }
    }

    public void reset() {
        invOpenningBalanceItemDTO = new InvOpenningBalanceItemDTO();
        invOpenningBalanceItemDetailDTOSelected = new InvOpenningBalanceItemDetailDTO();
    }

    public String exit() {
        try {
            exit("../invopeningbalanceitem/invopeningbalanceitemlist.xhtml");
            return "";
        } catch (Exception e) {
            saveError(e, "InvOpeningBalanceItemFormMB", "exit");
            return null;
        }
    }

    
    public String save() {
   invOpeningBalanceItemService.saveInvOpenningBalanceItemDTO(invOpenningBalanceItemDTO, getUserData().getUser());

        return "";
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
     * @return the invOpenningBalanceItemDTO
     */
    public InvOpenningBalanceItemDTO getInvOpenningBalanceItemDTO() {
        if (invOpenningBalanceItemDTO == null) {
            invOpenningBalanceItemDTO = new InvOpenningBalanceItemDTO();
            invOpenningBalanceItemDTO.setInvOpenningBalanceItemDetailDTOList(new ArrayList<>());
            invOpenningBalanceItemDTO.setDate(new Date());
        }
        return invOpenningBalanceItemDTO;
    }

    /**
     * @param invOpenningBalanceItemDTO the invOpenningBalanceItemDTO to set
     */
    public void setInvOpenningBalanceItemDTO(InvOpenningBalanceItemDTO invOpenningBalanceItemDTO) {
        this.invOpenningBalanceItemDTO = invOpenningBalanceItemDTO;
    }

    /**
     * @return the invOpenningBalanceItemDTOList
     */
    public List<InvOpenningBalanceItemDTO> getInvOpenningBalanceItemDTOList() {
        return invOpenningBalanceItemDTOList;
    }

    /**
     * @param invOpenningBalanceItemDTOList the invOpenningBalanceItemDTOList to
     * set
     */
    public void setInvOpenningBalanceItemDTOList(List<InvOpenningBalanceItemDTO> invOpenningBalanceItemDTOList) {
        this.invOpenningBalanceItemDTOList = invOpenningBalanceItemDTOList;
    }

    /**
     * @return the invInventoryDTOList
     */
    public List<InvInventoryDTO> getInvInventoryDTOList() {
        if (invInventoryDTOList == null || invInventoryDTOList.isEmpty()) {
            invInventoryDTOList = isagUserInventoryService.getAllInventroyDTOByUserAndBranch(getUserData().getUser());
            setInvInventoryDTOConverter(new InvInventoryDTOConverter(invInventoryDTOList));
        }
        return invInventoryDTOList;
    }

    /**
     * @param invInventoryDTOList the invInventoryDTOList to set
     */
    public void setInvInventoryDTOList(List<InvInventoryDTO> invInventoryDTOList) {
        this.invInventoryDTOList = invInventoryDTOList;
    }

    /**
     * @return the invInventoryDTOConverter
     */
    public InvInventoryDTOConverter getInvInventoryDTOConverter() {
        return invInventoryDTOConverter;
    }

    /**
     * @param invInventoryDTOConverter the invInventoryDTOConverter to set
     */
    public void setInvInventoryDTOConverter(InvInventoryDTOConverter invInventoryDTOConverter) {
        this.invInventoryDTOConverter = invInventoryDTOConverter;
    }

    public InvOpenningBalanceItemDetailDTO getInvOpenningBalanceItemDetailDTOSelected() {
        return invOpenningBalanceItemDetailDTOSelected;
    }

    /**
     * @param invOpenningBalanceItemDetailDTOSelected the
     * invOpenningBalanceItemDetailDTOSelected to set
     */
    public void setInvOpenningBalanceItemDetailDTOSelected(InvOpenningBalanceItemDetailDTO invOpenningBalanceItemDetailDTOSelected) {
        this.invOpenningBalanceItemDetailDTOSelected = invOpenningBalanceItemDetailDTOSelected;
    }

    /**
     * @return the invItemDTOList
     */
    public List<InvItemDTO> getInvItemDTOList() {
        if (invItemDTOList == null || invItemDTOList.isEmpty()) {
            invItemDTOList = invItemService.getInvItemListByBranchIdForOpeningBalanceItemFormDTO(getUserData().getUser());
            setItemDTOConverter(new ItemDTOConverter(invItemDTOList));
        }
        return invItemDTOList;
    }

    /**
     * @param invItemDTOList the invItemDTOList to set
     */
    public void setInvItemDTOList(List<InvItemDTO> invItemDTOList) {
        this.invItemDTOList = invItemDTOList;
    }

    /**
     * @return the itemDTOConverter
     */
    public ItemDTOConverter getItemDTOConverter() {
        return itemDTOConverter;
    }

    /**
     * @param itemDTOConverter the itemDTOConverter to set
     */
    public void setItemDTOConverter(ItemDTOConverter itemDTOConverter) {
        this.itemDTOConverter = itemDTOConverter;
    }

}

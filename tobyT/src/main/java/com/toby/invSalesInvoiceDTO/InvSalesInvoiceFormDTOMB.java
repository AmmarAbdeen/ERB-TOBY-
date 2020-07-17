/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.invSalesInvoiceDTO;

import com.toby.businessservice.InvPurchaseInvoiceDetailsService;
import com.toby.businessservice.InvPurchaseInvoiceService_1;
import com.toby.businessservice.InventorySetupService;
import com.toby.define.ScreenNameClassEnum;
import com.toby.dto.InvDetailDTO;
import com.toby.dto.InvPurchaseInvoiceDTO;
import com.toby.entity.UnitsItems;
import com.toby.toby.InventoryBasicDataForm;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author WIN7
 */
@Named(value = "invSalesInvoiceFormDTOMB")
@ViewScoped
public class InvSalesInvoiceFormDTOMB extends InventoryBasicDataForm {

    private InvPurchaseInvoiceDTO invPurchaseInvoiceDTO;

    // EJBs
    @EJB
    private InvPurchaseInvoiceService_1 invPurchaseInvoiceService;
    @EJB
    private InvPurchaseInvoiceDetailsService invPurchaseInvoiceDetailsService;
    @EJB
    private InventorySetupService inventorySetupService;

    @Override
    @PostConstruct
    public void init() {
        try {
            getInvPurchaseInvoiceDTO();
            if (validateloginDistributedScreens(invPurchaseInvoiceDTO.getScreenName()).isEmpty()) {
                load();
            } else {
                exit("../base/error.xhtml");
            }

        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "init");
        }
    }

    @Override
    public void load() {
        try {

            if (getScreenMode() != null && getScreenMode().equalsIgnoreCase("addInvoice")) {
                reset();
                fillDefaultList();
            } else if (getScreenMode() != null && getScreenMode().equalsIgnoreCase("editInvoice")) {
                Integer invPurchaseInvoiceId = null;
                if (getUri().contains("invpurchaseinvoiceform")) {
                    invPurchaseInvoiceId = (Integer) getContext().getSessionMap().get("invPurchaseInvoiceIdSeclected");
                } else if (getUri().contains("invsalesinvoicedtoform")) {
                    invPurchaseInvoiceId = (Integer) getContext().getSessionMap().get("invSalesInvoiceIdSeclected");
                }

                invPurchaseInvoiceDTO = invPurchaseInvoiceService.findInvPurchaseInvoiceById(invPurchaseInvoiceId, getUserData().getUser());
                fillLists();
            } else {
                exit("../invpurchaseinvoice/invpurchaseinvoicelist.xhtml");
            }
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "load");
        }
    }

    public void reset() {
        try {
            if (invPurchaseInvoiceDTO == null) {
                invPurchaseInvoiceDTO = new InvPurchaseInvoiceDTO();
            }
            invPurchaseInvoiceDTO.setInvInventoryId(getUserData().getInventoryDTODefault());
            invPurchaseInvoiceDTO.setGlBankId(getUserData().getGlBankDTODefault());
            if (getUri().contains("invpurchaseinvoiceform")) {
                invPurchaseInvoiceDTO.setOrganizationSiteId(getInvOrganizationSiteDTOList(true, 1).get(0));
            } else if (getUri().contains("invsalesinvoicedtoform")) {
                invPurchaseInvoiceDTO.setOrganizationSiteId(getInvOrganizationSiteDTOList(true, 0).get(0));
            }
            invPurchaseInvoiceDTO.setCurrencyId(getCurrencyDTOList().get(0));
            updateRate();
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "resetInvPurchaseInvoiceForm");
        }
    }

    public void subtractDatefromDueDate() {
        invPurchaseInvoiceDTO.setDueperiod(invPurchaseInvoiceService.subtractDatefromDueDate(invPurchaseInvoiceDTO.getDate(), invPurchaseInvoiceDTO.getDueDate(), invPurchaseInvoiceDTO.getDueperiod()));
    }

    public void sumDateToDueDate() {
        invPurchaseInvoiceDTO.setDueDate(invPurchaseInvoiceService.sumDateToDueDate(invPurchaseInvoiceDTO.getDate(), invPurchaseInvoiceDTO.getDueDate(), invPurchaseInvoiceDTO.getDueperiod()));
    }

    public void validateUnitColum() {
        invPurchaseInvoiceDTO.setInvPurchaseInvoiceDetailsSelected(invPurchaseInvoiceDetailsService.validateUnitColum(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected(), invPurchaseInvoiceDTO.getPricetype()));
    }

    public void validateBounceColum() {
        invPurchaseInvoiceDTO.setInvPurchaseInvoiceDetailsSelected(invPurchaseInvoiceDetailsService.validateBounceColum(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected()));
    }

    public void putCurrency() {
        try {
            invPurchaseInvoiceDTO.setMsg(null);
            invPurchaseInvoiceDTO = invPurchaseInvoiceService.putCurrency(invPurchaseInvoiceDTO);
            errorMsg(invPurchaseInvoiceDTO.getMsg());
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "putCurrency");
        }
    }

    public void addNewCustomer() {
        invPurchaseInvoiceDTO.setOrganizationSiteId(invPurchaseInvoiceService.addNewCustomer(getOrganizationSiteId(), getInvOrganizationSiteDTOList(true, 0), getUserData().getUser()));
    }

    public void addInvPurchaseInvoiceDetail() {
        try {
            getInvPurchaseInvoiceDTO().setMsg(null);
            setInvPurchaseInvoiceDTO(invPurchaseInvoiceDetailsService.addInvPurchaseInvoiceDetail(getInvPurchaseInvoiceDTO(), getIndex()));
            getInvPurchaseInvoiceDTO().getInvPurchaseInvoiceDetailsSelected().setInvItemParentId(getInvPurchaseInvoiceDTO().getInvPurchaseInvoiceDetails1().getInvItemParentId());
            errorMsg(getInvPurchaseInvoiceDTO().getMsg());
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "addInvPurchaseInvoiceDetail");
        }
    }

    public void deleteInvPurchaseInvoiceDetail() {
        try {
            if (invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected().getId() != null) {
                invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsDeletedList().add(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected());
            }
            for (InvDetailDTO detailEntity : invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList()) {
                if (detailEntity.getItemsBarcodesDetail() == null && detailEntity.getItemsBarcodesDetailTrans() != null) {
                    detailEntity.setItemsBarcodesDetail(detailEntity.getItemsBarcodesDetailTrans());
                }
            }
            invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList().remove(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected());
            updateSummition();
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "deleteInvPurchaseInvoiceDetail");
        }
    }

    public void recalcHeadValues() {
        try {
            invPurchaseInvoiceDTO = invPurchaseInvoiceService.recalcHeadValues(invPurchaseInvoiceDTO);
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "recalcHeadValues");
        }
    }

    private void errorMsg(String error) {
        if (error != null && !error.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), error));
        }
    }

    public void updateRate() {
        try {
            invPurchaseInvoiceDTO = invPurchaseInvoiceService.updateRate(invPurchaseInvoiceDTO);
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "updateRate");
        }
    }

    private void fillLists() {
        try {
            fillDefaultList();
            getCurrencyDTOList();
            getInvItemDTOList();
            if (getUri().contains("invpurchaseinvoiceform")) {
                getInvDelegatorList(0);
            } else if (getUri().contains("invsalesinvoiceform")) {
                getInvDelegatorList(1);
            }
            getCostCenterList();
            getAdminUnitList();
            getInvItemDTOList();
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "fillLists");
        }
    }

    private void fillDefaultList() {
        getInvInventoryDTOList();
        getInvOrganizationSiteDTOList(true, 1);
        getGlBankList();
    }

    private void afterValidation() {
        replacedetail();
        updateSummition();
        errorMsg(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected().getMsg());
    }

    private void replacedetail() {
        for (InvDetailDTO detailDTO : getInvPurchaseInvoiceDTO().getInvPurchaseInvoiceDetailList()) {
            if (detailDTO.getIndex().equals(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected().getIndex())) {
                int itemIndex = getInvPurchaseInvoiceDTO().getInvPurchaseInvoiceDetailList().indexOf(detailDTO);
                if (itemIndex != -1) {
                    getInvPurchaseInvoiceDTO().getInvPurchaseInvoiceDetailList().set(itemIndex, invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected());
                }
            }
        }
    }

    public void validateDiscountColumn() {
        try {
            invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected().setMsg(null);
            invPurchaseInvoiceDTO.setInvPurchaseInvoiceDetailsSelected(invPurchaseInvoiceDetailsService.validateDiscountColumn(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected(), invPurchaseInvoiceDTO.getPricetype()));
            afterValidation();
            errorMsg(getInvPurchaseInvoiceDTO().getInvPurchaseInvoiceDetailsSelected().getMsg());
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "validateDiscountColumn");
        }
    }

    public void validatePriceColumn() {
        try {
            invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected().setMsg(null);
            invPurchaseInvoiceDTO.setInvPurchaseInvoiceDetailsSelected(invPurchaseInvoiceDetailsService.validatePriceColumn(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected(), invPurchaseInvoiceDTO.getPricetype()));
            afterValidation();
            errorMsg(getInvPurchaseInvoiceDTO().getInvPurchaseInvoiceDetailsSelected().getMsg());
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "validatePriceColumn");
        }
    }

    public void validateQuantityColum() {
        try {
            invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected().setMsg(null);
            invPurchaseInvoiceDTO.setInvPurchaseInvoiceDetailsSelected(invPurchaseInvoiceDetailsService.validateQuantityColum(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected()));
            afterValidation();
            errorMsg(getInvPurchaseInvoiceDTO().getInvPurchaseInvoiceDetailsSelected().getMsg());
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "validateQuantityColum");
        }
    }

    public void validateNumberColum() {

        try {
            invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected().setMsg(null);
            invPurchaseInvoiceDetailsService.validateQuantityColum(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected());
            invPurchaseInvoiceDTO.setInvPurchaseInvoiceDetailsSelected(invPurchaseInvoiceDetailsService.validateNumberColum(invPurchaseInvoiceDetailsService.validateQuantityColum(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected())));
            afterValidation();
            errorMsg(getInvPurchaseInvoiceDTO().getInvPurchaseInvoiceDetailsSelected().getMsg());
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "validateQuantityColum");
        }
    }

    public Boolean validateItems() {
        try {
            invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected().setMsg(null);
            setUnitsItemseList(getUnitsItemsService().getUnitsByItemId(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected().getItemId().getId()));
            invPurchaseInvoiceDTO.setInvPurchaseInvoiceDetailsSelected(invPurchaseInvoiceDetailsService.validateItems(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected(), invPurchaseInvoiceDTO.getPricetype()));
            afterValidation();
            errorMsg(getInvPurchaseInvoiceDTO().getInvPurchaseInvoiceDetailsSelected().getMsg());

        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "validateItems");
            return null;
        }
        return true;
    }

    public void updateSummition() {
        setInvPurchaseInvoiceDTO(invPurchaseInvoiceService.updateSummition(invPurchaseInvoiceDTO));
    }

    public void updateDate(SelectEvent event) {
        try {
            updateRate();
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "updateDate");
        }
    }

    @Override
    public String save() {

        return "";
    }

    public String saveInv() {
        if (invPurchaseInvoiceDTO != null && invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList() != null && !invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList().isEmpty()) {
            invPurchaseInvoiceDTO.setMsg(null);
            invPurchaseInvoiceDTO = invPurchaseInvoiceService.save(invPurchaseInvoiceDTO, getUserData().getUser());
            errorMsg(invPurchaseInvoiceDTO.getMsg());
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "لا يوجد تفاصيل للفاتورة"));
        }
        return "";
    }

    @Override
    public String getScreenName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String exit() {
        exit("../invSalesInvoiceDTO/invsalesinvoiceDTOlist.xhtml");
        return "";
    }

    /**
     * @return the invPurchaseInvoiceDTO
     */
    public InvPurchaseInvoiceDTO getInvPurchaseInvoiceDTO() {
        if (invPurchaseInvoiceDTO == null) {
            invPurchaseInvoiceDTO = new InvPurchaseInvoiceDTO();

            settingInventory();
        }
        return invPurchaseInvoiceDTO;
    }

    private void settingInventory() {
        getUserData().getUserBranch().setCompanyActivity(getUserData().getUserBranch().getCompanyActivity().RetailSale);
        if (getUserData().getUserBranch().getCompanyActivity() != null) {
            invPurchaseInvoiceDTO.setCompanyActivity(getUserData().getUserBranch().getCompanyActivity());
            if (getUri().contains("invpurchaseinvoiceform")) {
                getInvPurchaseInvoiceDTO().setScreenName(ScreenNameClassEnum.invpurchaseinvoiceform);
            } else if (getUri().contains("invsalesinvoicedtoform")) {
                getInvPurchaseInvoiceDTO().setScreenName(ScreenNameClassEnum.invsalesinvoicedtoform);
            }
        }
        if (invPurchaseInvoiceDTO != null) {
            invPurchaseInvoiceDTO.setInventorySetupDTO(inventorySetupService.getInventoryByBranchIdDTO(getUserData().getUserBranch().getId()));
        }
    }

    /**
     * @param invPurchaseInvoiceDTO the invPurchaseInvoiceDTO to set
     */
    public void setInvPurchaseInvoiceDTO(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        this.invPurchaseInvoiceDTO = invPurchaseInvoiceDTO;
    }
}

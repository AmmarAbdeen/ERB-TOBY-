/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.invPurchaseInvoiceDTO;

import com.toby.businessservice.InvPurchaseInvoiceDetailsService;
import com.toby.businessservice.InvPurchaseInvoiceService_1;
import com.toby.define.CompanyActivityClassEnum;
import com.toby.define.ScreenNameClassEnum;
import com.toby.dto.InvDetailDTO;
import com.toby.dto.InvPurchaseInvoiceDTO;
import com.toby.toby.InventoryBasicDataForm;
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
@Named(value = "invPurchaseInvoiceFormDTOMB")
@ViewScoped
public class InvPurchaseInvoiceFormDTOMB extends InventoryBasicDataForm {

    private InvPurchaseInvoiceDTO invPurchaseInvoiceDTO;

    // EJBs
    @EJB
    private InvPurchaseInvoiceService_1 invPurchaseInvoiceService;
    @EJB
    private InvPurchaseInvoiceDetailsService invPurchaseInvoiceDetailsService;

    @Override
    @PostConstruct
    public void init() {
        try {
            settingInventory();
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
                } else if (getUri().contains("invsalesinvoiceform")) {
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
            invPurchaseInvoiceDTO = new InvPurchaseInvoiceDTO();
            invPurchaseInvoiceDTO.setInvInventoryId(getUserData().getInventoryDTODefault());
            invPurchaseInvoiceDTO.setGlBankId(getUserData().getGlBankDTODefault());
            if (getUri().contains("invpurchaseinvoiceform")) {
                invPurchaseInvoiceDTO.setOrganizationSiteId(getInvOrganizationSiteDTOList(true, 1).get(0));
            } else if (getUri().contains("invsalesinvoiceform")) {
                invPurchaseInvoiceDTO.setOrganizationSiteId(getInvOrganizationSiteDTOList(true, 0).get(0));
            }
            invPurchaseInvoiceDTO.setCurrencyId(getCurrencyDTOList().get(0));
            updateRate();
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "resetInvPurchaseInvoiceForm");
        }
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

    public void addInvPurchaseInvoiceDetail() {
        try {
            getInvPurchaseInvoiceDTO().setMsg(null);
            setInvPurchaseInvoiceDTO(invPurchaseInvoiceDetailsService.addInvPurchaseInvoiceDetail(getInvPurchaseInvoiceDTO(), getIndex()));
            errorMsg(getInvPurchaseInvoiceDTO().getMsg());
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "addInvPurchaseInvoiceDetail");
        }
    }

    public void deleteInvPurchaseInvoiceDetail(InvDetailDTO invPurchaseInvoiceDetailsValidate) {
        try {
            if (invPurchaseInvoiceDetailsValidate.getId() != null) {
                invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsDeletedList().add(invPurchaseInvoiceDetailsValidate);
            }
            for (InvDetailDTO detailEntity : invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList()) {
                if (detailEntity.getItemsBarcodesDetail() == null && detailEntity.getItemsBarcodesDetailTrans() != null) {
                    detailEntity.setItemsBarcodesDetail(detailEntity.getItemsBarcodesDetailTrans());
                }
            }
            invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList().remove(invPurchaseInvoiceDetailsValidate);
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
            getItemsBarcodesDetailsViewList();
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

    private void afterValidation(InvDetailDTO invPurchaseInvoiceDetailsValidate) {
        replacedetail(invPurchaseInvoiceDetailsValidate);
        updateSummition();
        errorMsg(invPurchaseInvoiceDetailsValidate.getMsg());
    }

    private void replacedetail(InvDetailDTO invPurchaseInvoiceDetailsValidate) {
        for (InvDetailDTO detailDTO : getInvPurchaseInvoiceDTO().getInvPurchaseInvoiceDetailList()) {
            if (detailDTO.getIndex().equals(invPurchaseInvoiceDetailsValidate.getIndex())) {
                int itemIndex = getInvPurchaseInvoiceDTO().getInvPurchaseInvoiceDetailList().indexOf(detailDTO);
                if (itemIndex != -1) {
                    getInvPurchaseInvoiceDTO().getInvPurchaseInvoiceDetailList().set(itemIndex, invPurchaseInvoiceDetailsValidate);
                }
            }
        }
    }

    public void validateDiscountColumn(InvDetailDTO invPurchaseInvoiceDetailsValidate) {
        try {
            invPurchaseInvoiceDetailsValidate.setMsg(null);
            invPurchaseInvoiceDetailsValidate = invPurchaseInvoiceDetailsService.validateDiscountColumn(invPurchaseInvoiceDetailsValidate,invPurchaseInvoiceDTO.getPricetype());
            afterValidation(invPurchaseInvoiceDetailsValidate);
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "validateDiscountColumn");
        }
    }

    public void validatePriceColumn(InvDetailDTO invPurchaseInvoiceDetailsValidate) {
        try {
            invPurchaseInvoiceDetailsValidate.setMsg(null);
            invPurchaseInvoiceDetailsValidate = invPurchaseInvoiceDetailsService.validatePriceColumn(invPurchaseInvoiceDetailsValidate,invPurchaseInvoiceDTO.getPricetype());
            afterValidation(invPurchaseInvoiceDetailsValidate);
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "validatePriceColumn");
        }
    }

    public void validateQuantityColum(InvDetailDTO invPurchaseInvoiceDetailsValidate) {
        try {
            invPurchaseInvoiceDetailsValidate.setMsg(null);
            invPurchaseInvoiceDetailsValidate = invPurchaseInvoiceDetailsService.validateQuantityColum(invPurchaseInvoiceDetailsValidate);
            afterValidation(invPurchaseInvoiceDetailsValidate);
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "validateQuantityColum");
        }
    }

    public Boolean validateItems(InvDetailDTO invPurchaseInvoiceDetailsValidate) {
        try {
            invPurchaseInvoiceDetailsValidate.setMsg(null);
            invPurchaseInvoiceDetailsValidate = invPurchaseInvoiceDetailsService.validateItems(invPurchaseInvoiceDetailsValidate,invPurchaseInvoiceDTO.getPricetype());
            afterValidation(invPurchaseInvoiceDetailsValidate);
        } catch (Exception e) {
            saveError(e, "InvPurchaseInvoiceFormMB", "validateItems");
            return null;
        }
        return true;
    }

    public void updateSummition() {
        invPurchaseInvoiceDTO = invPurchaseInvoiceService.updateSummition(invPurchaseInvoiceDTO);
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
        exit("../invpurchaseinvoice/invpurchaseinvoicelist.xhtml");
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
        invPurchaseInvoiceDTO.setCompanyActivity(getUserData().getUserBranch().getCompanyActivity());
        if (getUri().contains("invpurchaseinvoiceform")) {
            getInvPurchaseInvoiceDTO().setScreenName(ScreenNameClassEnum.invpurchaseinvoiceform);
        } else if (getUri().contains("invsalesinvoicedtoform")) {
            getInvPurchaseInvoiceDTO().setScreenName(ScreenNameClassEnum.invsalesinvoicedtoform);
        }
  //      invPurchaseInvoiceDTO.setInventorySetupDTO(inventorySetupService.getInventoryByBranchIdDTO(getUserData().getUserBranch().getId()));
    }

    /**
     * @param invPurchaseInvoiceDTO the invPurchaseInvoiceDTO to set
     */
    public void setInvPurchaseInvoiceDTO(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        this.invPurchaseInvoiceDTO = invPurchaseInvoiceDTO;
    }
}

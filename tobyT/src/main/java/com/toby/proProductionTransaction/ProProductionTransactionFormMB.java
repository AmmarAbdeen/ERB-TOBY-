package com.toby.proProductionTransaction;

import com.toby.businessservice.InvPurchaseInvoiceDetailService;
import com.toby.businessservice.ProProductionItemsTransactionService;
import com.toby.businessservice.ProProductionTransactionOfInvoiceService;
import com.toby.businessservice.ProProductionTransactionService;
import com.toby.converter.ProProductionStagesDTOConverter;
import com.toby.dto.ProProductionItemsTransactionDTO;
import com.toby.dto.ProProductionProductNumberDTO;
import com.toby.dto.ProProductionStagesDTO;
import com.toby.dto.ProProductionTransactionDTO;
import com.toby.dto.ProProductionTransactionDetailDetailViewDTO;
import com.toby.dto.ProProductionTransactionDetailViewDTO;
import com.toby.toby.BaseListBean;
import com.toby.toby.UserData;
import com.toby.views.ProProductionTransactionOfInvoice;
import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.SelectEvent;

@Named(value = "proProductionTransactionListMB")
@ViewScoped
public class ProProductionTransactionFormMB extends BaseListBean {

    private List<ProProductionTransactionDTO> proProductionTransactionDTOList;
    private ProProductionTransactionDTO proProductionTransactionDTO;
    private ProProductionItemsTransactionDTO proProductionItemsTransactionDTO;
    private ProProductionProductNumberDTO proProductionProductNumberDTO;
    private ProProductionStagesDTO proProductionStagesDTO;
    private ProProductionTransactionDetailViewDTO proProductionTransactionDetailViewDTO;
    private ProProductionTransactionDetailDetailViewDTO productionTransactionDetailDetailViewDTO;
    private List<ProProductionStagesDTO> proProductionStagesDTOs;
    private ProProductionTransactionDetailDetailViewDTO proProductionTransactionDetailDetailViewDTOSelected;
    private ProProductionStagesDTOConverter proProductionStagesDTOConverter;
    @EJB
    private InvPurchaseInvoiceDetailService invPurchaseInvoiceDetailService;
    @EJB
    private ProProductionTransactionService proProductionTransactionService;
    @EJB
    private ProProductionTransactionOfInvoiceService proProductionTransactionOfInvoiceService;
    @EJB
    private ProProductionItemsTransactionService proProductionItemsTransactionService;

    public ProProductionTransactionFormMB() {
    }

    @Override
    @PostConstruct
    public void init() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        setUserData((UserData) context.getSessionMap().get("userLogInData"));

        if (getUserData().getProProductionStagesDTOList() == null | getUserData().getProProductionStagesDTOList().isEmpty()) {
            HttpServletRequest origRequest = (HttpServletRequest) context.getRequest();
            String contextPath = origRequest.getContextPath();
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(contextPath + "/base/error.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String save() {
        
        
        if(proProductionTransactionDetailDetailViewDTOSelected.getNumberExcute().compareTo(proProductionTransactionDetailDetailViewDTOSelected.getNumberRemain())>0||proProductionTransactionDetailDetailViewDTOSelected.getNumberExcute().compareTo(BigDecimal.ZERO)<0){
             proProductionTransactionDetailDetailViewDTOSelected.setMsg("الكميه هذا الصنف غير موجوده" + proProductionTransactionDetailDetailViewDTOSelected.getInvItemName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "success!", proProductionTransactionDetailDetailViewDTOSelected.getMsg()));
        
         
        }
        else{
        proProductionItemsTransactionService.saveProProductionItemsTransaction(proProductionTransactionDetailDetailViewDTOSelected, proProductionTransactionDTO, getUserData().getUser());
       
        updateDetailItems();
        
         proProductionTransactionDetailDetailViewDTOSelected.setMsg("تم الحفظ بنجاح");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", proProductionTransactionDetailDetailViewDTOSelected.getMsg()));
        
        }
        return "";

    }

    public void selectUserRole1(SelectEvent event) {
        try {
            proProductionTransactionDTO = (ProProductionTransactionDTO) event.getObject();
//            System.out.println(proProductionTransactionDTOSelected.getId() + " " + proProductionTransactionDTOSelected.get() + " " + proProductionTransactionDTOSelected.getNameen());
            if (proProductionTransactionDTO != null) {
                proProductionTransactionDTO.setProductNumberDTOList(invPurchaseInvoiceDetailService.getInvPurchaseInvoiceDetailClothNumber(proProductionTransactionDTO.getInvPurchaseInvoiceId() ,proProductionTransactionDTO.getProProductionId()));
                   proProductionProductNumberDTO.setDetailDetailViewDTOList(new ArrayList<>());
          proProductionTransactionDetailViewDTO.setDetailDetailViewDTOs(new ArrayList<>());
            }
        } catch (Exception e) {
            saveError(e, "UserRoleFormBean", "selectUserRole");
        }
    }

    public void selectUserRole2(SelectEvent event) {
        try {
            proProductionProductNumberDTO = (ProProductionProductNumberDTO) event.getObject();
//            System.out.println(proProductionTransactionDTOSelected.getId() + " " + proProductionTransactionDTOSelected.get() + " " + proProductionTransactionDTOSelected.getNameen());
            if (proProductionProductNumberDTO != null && proProductionTransactionDTO != null) {
                proProductionProductNumberDTO.setDetailDetailViewDTOList(invPurchaseInvoiceDetailService.getInvPurchaseInvoiceDetailItemCompleted(proProductionTransactionDTO.getInvPurchaseInvoiceId(), proProductionProductNumberDTO.getProductNumber(),proProductionTransactionDTO.getProProductionId()));
                proProductionTransactionDetailViewDTO.setDetailDetailViewDTOs(new ArrayList<>());
           
            }
        } catch (Exception e) {
            saveError(e, "UserRoleFormBean", "selectUserRole");
        }
    }

    public void selectUserRole3(SelectEvent event) {
        try {
            proProductionTransactionDetailViewDTO = (ProProductionTransactionDetailViewDTO) event.getObject();
            updateDetailItems();
        } catch (Exception e) {
            saveError(e, "UserRoleFormBean", "selectUserRole");
        }
    }

    private void updateDetailItems() {
        //            System.out.println(proProductionTransactionDTOSelected.getId() + " " + proProductionTransactionDTOSelected.get() + " " + proProductionTransactionDTOSelected.getNameen());
        if (proProductionTransactionDetailViewDTO != null ) {
            proProductionTransactionDetailViewDTO.setDetailDetailViewDTOs(invPurchaseInvoiceDetailService.getInvPurchaseInvoiceDetailItems(proProductionTransactionDTO.getInvPurchaseInvoiceId(), proProductionProductNumberDTO.getProductNumber(), proProductionTransactionDetailViewDTO.getInvItemCode()));
            
            for (ProProductionTransactionDetailDetailViewDTO detailViewDTO : proProductionTransactionDetailViewDTO.getDetailDetailViewDTOs()) {
                ProProductionTransactionOfInvoice proProductionTransactionOfInvoice = proProductionTransactionOfInvoiceService.getRenaimNumberListOfproduction(proProductionTransactionDTO.getInvPurchaseInvoiceId(), proProductionProductNumberDTO.getProductNumber(), proProductionTransactionDetailViewDTO.getInvItemCode(), detailViewDTO.getInvItemCode(),proProductionTransactionDTO.getProProductionId(), getUserData().getUser());
                if (proProductionTransactionOfInvoice == null) {
                    detailViewDTO.setNumberRemain(detailViewDTO.getQuantity());
                } else {
                    detailViewDTO.setNumberRemain(proProductionTransactionOfInvoice.getNumberRemain());
                }
            }

        }
    }

    public void check() {
  
        proProductionTransactionDTOList = proProductionTransactionService.getProProductionTransactionDTOofViewList(getProProductionTransactionDTO().getProProductionId(), getUserData().getUser());
        
          
           proProductionTransactionDTO.setProductNumberDTOList(new ArrayList<>());
         proProductionProductNumberDTO.setDetailDetailViewDTOList(new ArrayList<>());
          proProductionTransactionDetailViewDTO.setDetailDetailViewDTOs(new ArrayList<>());
            
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

    /**
     * @return the proProductionTransactionDTOList
     */
    public List<ProProductionTransactionDTO> getProProductionTransactionDTOList() {
        return proProductionTransactionDTOList;
    }

    /**
     * @param proProductionTransactionDTOList the
     * proProductionTransactionDTOList to set
     */
    public void setProProductionTransactionDTOList(List<ProProductionTransactionDTO> proProductionTransactionDTOList) {
        this.proProductionTransactionDTOList = proProductionTransactionDTOList;
    }

    /**
     * @return the proProductionTransactionDTO
     */
    public ProProductionTransactionDTO getProProductionTransactionDTO() {
        if (proProductionTransactionDTO == null) {
            proProductionTransactionDTO = new ProProductionTransactionDTO();
        }
        return proProductionTransactionDTO;
    }

    /**
     * @param proProductionTransactionDTO the proProductionTransactionDTO to set
     */
    public void setProProductionTransactionDTO(ProProductionTransactionDTO proProductionTransactionDTO) {
        this.proProductionTransactionDTO = proProductionTransactionDTO;
    }

    /**
     * @return the proProductionStagesDTOConverter
     */
    public ProProductionStagesDTOConverter getProProductionStagesDTOConverter() {
        return proProductionStagesDTOConverter;
    }

    /**
     * @param proProductionStagesDTOConverter the
     * proProductionStagesDTOConverter to set
     */
    public void setProProductionStagesDTOConverter(ProProductionStagesDTOConverter proProductionStagesDTOConverter) {
        this.proProductionStagesDTOConverter = proProductionStagesDTOConverter;
    }

    /**
     * @return the proProductionStagesDTOs
     */
    public List<ProProductionStagesDTO> getProProductionStagesDTOs() {
        return getUserData().getProProductionStagesDTOList();
    }

    /**
     * @param proProductionStagesDTOs the proProductionStagesDTOs to set
     */
    public void setProProductionStagesDTOs(List<ProProductionStagesDTO> proProductionStagesDTOs) {
        this.proProductionStagesDTOs = proProductionStagesDTOs;
    }

    /**
     * @return the proProductionProductNumberDTO
     */
    public ProProductionProductNumberDTO getProProductionProductNumberDTO() {
        if (proProductionProductNumberDTO == null) {
            proProductionProductNumberDTO = new ProProductionProductNumberDTO();
        }
        return proProductionProductNumberDTO;
    }

    /**
     * @param proProductionProductNumberDTO the proProductionProductNumberDTO to
     * set
     */
    public void setProProductionProductNumberDTO(ProProductionProductNumberDTO proProductionProductNumberDTO) {
        this.proProductionProductNumberDTO = proProductionProductNumberDTO;
    }

    /**
     * @return the proProductionTransactionDetailViewDTO
     */
    public ProProductionTransactionDetailViewDTO getProProductionTransactionDetailViewDTO() {
        if (proProductionTransactionDetailViewDTO == null) {
            proProductionTransactionDetailViewDTO = new ProProductionTransactionDetailViewDTO();
        }
        return proProductionTransactionDetailViewDTO;
    }

    /**
     * @param proProductionTransactionDetailViewDTO the
     * proProductionTransactionDetailViewDTO to set
     */
    public void setProProductionTransactionDetailViewDTO(ProProductionTransactionDetailViewDTO proProductionTransactionDetailViewDTO) {
        this.proProductionTransactionDetailViewDTO = proProductionTransactionDetailViewDTO;
    }

    /**
     * @return the productionTransactionDetailDetailViewDTO
     */
    public ProProductionTransactionDetailDetailViewDTO getProductionTransactionDetailDetailViewDTO() {
        if (productionTransactionDetailDetailViewDTO == null) {
            productionTransactionDetailDetailViewDTO = new ProProductionTransactionDetailDetailViewDTO();
        }
        return productionTransactionDetailDetailViewDTO;
    }

    /**
     * @param productionTransactionDetailDetailViewDTO the
     * productionTransactionDetailDetailViewDTO to set
     */
    public void setProductionTransactionDetailDetailViewDTO(ProProductionTransactionDetailDetailViewDTO productionTransactionDetailDetailViewDTO) {
        this.productionTransactionDetailDetailViewDTO = productionTransactionDetailDetailViewDTO;
    }

    /**
     * @return the proProductionStagesDTO
     */
    public ProProductionStagesDTO getProProductionStagesDTO() {
        if (proProductionStagesDTO == null) {
            proProductionStagesDTO = new ProProductionStagesDTO();
        }
        return proProductionStagesDTO;
    }

    /**
     * @param proProductionStagesDTO the proProductionStagesDTO to set
     */
    public void setProProductionStagesDTO(ProProductionStagesDTO proProductionStagesDTO) {
        this.proProductionStagesDTO = proProductionStagesDTO;
    }

    /**
     * @return the proProductionTransactionDetailDetailViewDTOSelected
     */
    public ProProductionTransactionDetailDetailViewDTO getProProductionTransactionDetailDetailViewDTOSelected() {
        return proProductionTransactionDetailDetailViewDTOSelected;
    }

    /**
     * @param proProductionTransactionDetailDetailViewDTOSelected the
     * proProductionTransactionDetailDetailViewDTOSelected to set
     */
    public void setProProductionTransactionDetailDetailViewDTOSelected(ProProductionTransactionDetailDetailViewDTO proProductionTransactionDetailDetailViewDTOSelected) {
        this.proProductionTransactionDetailDetailViewDTOSelected = proProductionTransactionDetailDetailViewDTOSelected;
    }

    /**
     * @return the proProductionItemsTransactionDTO
     */
    public ProProductionItemsTransactionDTO getProProductionItemsTransactionDTO() {
        return proProductionItemsTransactionDTO;
    }

    /**
     * @param proProductionItemsTransactionDTO the
     * proProductionItemsTransactionDTO to set
     */
    public void setProProductionItemsTransactionDTO(ProProductionItemsTransactionDTO proProductionItemsTransactionDTO) {
        this.proProductionItemsTransactionDTO = proProductionItemsTransactionDTO;
    }

    /**
     * @return the proProductionStagesDTOList
     */
}

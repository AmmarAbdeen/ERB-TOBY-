package com.toby.invtransfer;

import com.toby.businessservice.InvTransferService;
import com.toby.dto.InvTransferDTO;
import com.toby.toby.BaseListBean;
import com.toby.toby.UserData;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author khaled
 */
@Named(value = "invTransferListMB")
@ViewScoped
public class InvTransferListMB extends BaseListBean {

    private InvTransferDTO invTransferDTO;
    private Integer invTransferSelected;
    private Boolean isPermission;
    private String uri;
    private List<InvTransferDTO> invTransferDTOList;

    @EJB
    private InvTransferService invTransferService;

    @Override
    @PostConstruct
    public void init() {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            setUserData((UserData) context.getSessionMap().get("userLogInData"));
            uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
            if (uri.contains("invtransferInventory")) {
                isPermission = false;
            } else {
                isPermission = true;
            }
            this.load();
        } catch (Exception e) {
            saveError(e, "InvTransferListMB", "init()");
        }
    }

    @Override
    public void delete() {
        try {

            Map<String, String> userDDs = getUserData().getUserDDs();
            if (invTransferDTO != null) {

                try {
//                    invTransferService.deleteInvTransfer(invTransferDTO);
                    invTransferDTOList.remove(invTransferDTO);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, userDDs.get("INFO"), userDDs.get("DELETED")));
                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, userDDs.get("ERROR"), userDDs.get("UNIQE_KEY_ERROR")));
                }
            }
        } catch (Exception e) {
            saveError(e, "InvTransferListMB", "delete()");
        }
    }

    @Override
    public void filter() {

    }

    @Override
    public String goToAdd() {
        try {

            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.getSessionMap().put("ScreenMode", "Add");
            if (!isPermission) {
                exit("../invtransferInventory/invTransferInventoryForm.xhtml");
            } else {
                exit("../invtransfer/invTransferForm.xhtml");
            }
            return "";
        } catch (Exception e) {
            saveError(e, "InvTransferListMB", "goToAdd()");
            return "";
        }
    }

    @Override
    public String goToEdit() {
        try {

            if (invTransferSelected != null && invTransferSelected > 0) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.getSessionMap().put("invTransferSelected", invTransferSelected);
                context.getSessionMap().put("ScreenMode", "Edit");
                if (!isPermission) {
                    exit("../invtransferInventory/invTransferInventoryForm.xhtml");
                } else {
                    exit("../invtransfer/invTransferForm.xhtml");
                }
                return "";
            } else {
                return "";
            }
        } catch (Exception e) {
            saveError(e, "InvTransferListMB", "goToEdit()");
            return "";
        }
    }

    @Override
    public void load() {
        try {

            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
//            userData = (UserData) context.getSessionMap().get("userLogInData");
//            invTransferList = invTransferService.getALLInvTransferByBranchIdAndTransferType(userData.getUserBranch().getId(), isPermission ? 1 : 0);

        } catch (Exception e) {
            saveError(e, "InvTransferListMB", "load()");
        }
    }

    @Override
    public String getScreenName() {
        return null;
    }

    public Integer getInvTransferSelected() {
        return invTransferSelected;
    }

    public void setInvTransferSelected(Integer invTransferSelected) {
        this.invTransferSelected = invTransferSelected;
    }

    public Boolean getIsPermission() {
        return isPermission;
    }

    /**
     * @param isPermission the isPermission to set
     */
    public void setIsPermission(Boolean isPermission) {
        this.isPermission = isPermission;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the invTransferDTO
     */
    public InvTransferDTO getInvTransferDTO() {
        if (invTransferDTO == null) {
            invTransferDTO = new InvTransferDTO();
        }
        return invTransferDTO;
    }

    /**
     * @param invTransferDTO the invTransferDTO to set
     */
    public void setInvTransferDTO(InvTransferDTO invTransferDTO) {
        this.invTransferDTO = invTransferDTO;
    }

    /**
     * @return the invTransferDTOList
     */
    public List<InvTransferDTO> getInvTransferDTOList() {
        if (invTransferDTOList == null || invTransferDTOList.isEmpty()) {
            invTransferDTOList = invTransferService.getALLInvTransferByBranchIdAndTransferType(getUserData().getUser(), isPermission ? 1 : 0);
        }
        return invTransferDTOList;
    }

    /**
     * @param invTransferDTOList the invTransferDTOList to set
     */
    public void setInvTransferDTOList(List<InvTransferDTO> invTransferDTOList) {
        this.invTransferDTOList = invTransferDTOList;
    }
}

package com.toby.organizationSideOpeningBalance;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.toby.businessservice.OrganizationSiteService;
import com.toby.entity.InvOrganizationSite;
import com.toby.entiy.OrganizationSiteOpeningBalanceEntity;
import com.toby.toby.BaseListBean;
import com.toby.toby.UserData;
import javax.servlet.http.HttpServletRequest;

@Named(value = "organizationSiteOpeningBalanceListMB")
@ViewScoped
public class OrganizationSiteOpeningBalanceListMB extends BaseListBean {

    private UserData userData;
    private Integer branchId;
    private List<OrganizationSiteOpeningBalanceEntity> organizationSideOpeningBalanceEntityList;

    private Integer organizationSiteOpeningBalanceId;

    private List<InvOrganizationSite> organizationSiteList;

    private HttpServletRequest request;
    private String url;

    @EJB
    private OrganizationSiteService organizationSiteService;

    @Override
    @PostConstruct
    public void init() {
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            url = request.getRequestURL().toString();
            load();
        } catch (Exception e) {
            saveError(e, "OrganizationSiteOpeningBalanceListMB", "init");
        }
    }

    @Override
    public void load() {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            userData = (UserData) context.getSessionMap().get("userLogInData");
            branchId = userData.getUserBranch().getId();
            if (userData != null) {
                organizationSideOpeningBalanceEntityList = new ArrayList<>();

                if (url.contains("supplierOpeningBalance")) {
                    organizationSiteList = organizationSiteService.getorganizationSiteByBranchIdForGlBankModule(branchId, true, 1);
                } else {
                    organizationSiteList = organizationSiteService.getorganizationSiteByBranchIdForGlBankModule(branchId, true, 0);
                }

                OrganizationSiteOpeningBalanceEntity siteOpeningBalanceEntity;
                for (InvOrganizationSite organizationSiteEntity : organizationSiteList) {

//                int type = organizationSiteEntity.getType() == null ? 2 : organizationSiteEntity.getType();
                    siteOpeningBalanceEntity = new OrganizationSiteOpeningBalanceEntity();
                    siteOpeningBalanceEntity.setCode(organizationSiteEntity.getCode());
                    siteOpeningBalanceEntity.setId(organizationSiteEntity.getId());
                    siteOpeningBalanceEntity.setOpenBalanceCredit(organizationSiteEntity.getOpenBalanceCredit());
                    siteOpeningBalanceEntity.setOpenBalanceDebit(organizationSiteEntity.getOpenBalanceDebit());
                    siteOpeningBalanceEntity.setOrganizationBalanceDate(organizationSiteEntity.getOpenBalanceDate());
                    siteOpeningBalanceEntity.setOrganizationSiteName(organizationSiteEntity.getName());

//                if (type == 0) {
//                    organizationSiteOpeningBalanceEntityLoad.setOrganizationSiteType("عميل");
//                } else if (type == 1) {
//                    organizationSiteOpeningBalanceEntityLoad.setOrganizationSiteType("مورد");
//
//                } else if (type == 2) {
//                    organizationSiteOpeningBalanceEntityLoad.setOrganizationSiteType("عميل ومورد");
//            }
                    organizationSideOpeningBalanceEntityList.add(siteOpeningBalanceEntity);
                }

            }
        } catch (Exception e) {
            saveError(e, "OrganizationSiteOpeningBalanceListMB", "load");
        }
    }

    @Override
    public String goToAdd() {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.getSessionMap().put("ScreenMode", "Add");
            exit();
            return "";
        } catch (Exception e) {
            saveError(e, "OrganizationSiteOpeningBalanceListMB", "goToAdd");
            return null;
        }
    }

    @Override
    public String goToEdit() {
        try {
            if (organizationSiteOpeningBalanceId != null && organizationSiteOpeningBalanceId > 0) {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.getSessionMap().put("organizationSiteSelected", organizationSiteOpeningBalanceId);
                context.getSessionMap().put("ScreenMode", "Edit");
                exit();
                return "";
            } else {
                return "";
            }
        } catch (Exception e) {
            saveError(e, "OrganizationSiteOpeningBalanceListMB", "goToEdit");
            return null;
        }

    }

    @Override
    public void delete() {
//        Map<String, String> userDDs = userData.getUserDDs();
//        if (organizationSiteOpeningBalanceEntity != null && organizationSiteOpeningBalanceEntity.getId() > 0) {
//            try {
//
//                InvOrganizationSite invOrganizationSite = new InvOrganizationSite();
//                invOrganizationSite.setId(organizationSiteOpeningBalanceEntity.getId());
//                organizationSiteService.deleteOrganizationSite(invOrganizationSite);
//
//                FacesContext.getCurrentInstance().addMessage(null,
//                        new FacesMessage(FacesMessage.SEVERITY_INFO, userDDs.get("INFO"), userDDs.get("DELETED")));
//                load();
//            } catch (Exception e) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                        userDDs.get("ERROR"), userDDs.get("UNIQE_KEY_ERROR")));
//            }
//        }
    }

    @Override
    public void filter() {
    }

    @Override
    public String getScreenName() {
        return null;
    }

    public void exit() {
        try {
            if (url.contains("supplierOpeningBalance")) {
                exit("../supplierOpeningBalance/supplierOpenningBalanceForm.xhtml");
            } else {
                exit("../customerOpeningBalance/customerOpenningBalanceForm.xhtml");
            }
        } catch (Exception e) {
            saveError(e, "OrganizationSiteOpeningBalanceListMB", "exit");
        }
    }

    public List<OrganizationSiteOpeningBalanceEntity> getOrganizationSideOpeningBalanceEntityList() {
        return organizationSideOpeningBalanceEntityList;
    }

    public void setOrganizationSideOpeningBalanceEntityList(List<OrganizationSiteOpeningBalanceEntity> organizationSideOpeningBalanceEntityList) {
        this.organizationSideOpeningBalanceEntityList = organizationSideOpeningBalanceEntityList;
    }

    public List<InvOrganizationSite> getOrganizationSiteList() {
        return organizationSiteList;
    }

    public void setOrganizationSiteList(List<InvOrganizationSite> organizationSiteList) {
        this.organizationSiteList = organizationSiteList;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Integer getOrganizationSiteOpeningBalanceId() {
        return organizationSiteOpeningBalanceId;
    }

    public void setOrganizationSiteOpeningBalanceId(Integer organizationSiteOpeningBalanceId) {
        this.organizationSiteOpeningBalanceId = organizationSiteOpeningBalanceId;
    }

}

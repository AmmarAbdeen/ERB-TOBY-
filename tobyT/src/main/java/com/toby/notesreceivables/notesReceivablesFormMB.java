/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.notesreceivables;

import com.toby.businessservice.AccountsSystemSettingsService;
import com.toby.businessservice.CostCenterService;
import com.toby.businessservice.GlAccountService;
import com.toby.businessservice.GlAdminUnitService;
import com.toby.businessservice.GlBankService;
import com.toby.businessservice.GlBankTransactionDetailsService;
import com.toby.businessservice.GlBankTransactionInvoiceViewService;
import com.toby.businessservice.GlBankTransactionService;
import com.toby.businessservice.TobyUserBankService;
import com.toby.businessservice.OrganizationSiteService;
import com.toby.businessservice.SymbolService;
import com.toby.businessservice.report.SupplierAccountService;
import com.toby.converter.GlBankConverter;
import com.toby.converter.GlaccountConverter;
import com.toby.converter.InstTransactionViewConverter;
import com.toby.converter.InvOrganizationSiteConverter;
import com.toby.converter.SymbolConverter;
import com.toby.entity.AccountsSystemSettings;
import com.toby.entity.CostCenter;
import com.toby.entity.GlAccount;
import com.toby.entity.GlAdminUnit;
import com.toby.entity.GlBank;
import com.toby.entity.GlBankTransaction;
import com.toby.entity.GlBankTransactionDetail;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.InvPurchaseInvoice;
import com.toby.entity.Symbol;
import com.toby.entiy.GlBankTransactionDetailEntity;
import com.toby.entiy.GlBankTransactionEntity;
import com.toby.generaljournal.GeneralJournalBean;
import com.toby.toby.GlBankBasicDataForm;
import com.toby.toby.UserData;
import com.toby.views.GlBankTransactionInvoiceView;
import com.toby.views.InstTransactionView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import org.olap4j.impl.ArrayNamedListImpl;
import org.primefaces.context.RequestContext;
import tafqeet.Tafqeet;

/**
 *
 * @author WIN7
 */
@Named(value = "notesReceivablesFormMB")
@ViewScoped
public class notesReceivablesFormMB extends GlBankBasicDataForm {

    private Integer index = 0;
    private String uri;

    private Integer branchId;
    private Integer companyId;
    private Integer glBankTransactionId;
    private Integer organizationTypeUpdated;

    private Boolean checkView = Boolean.FALSE;
    private Boolean lineCheckView = Boolean.FALSE;

    private Boolean paymentDropDownView = Boolean.FALSE;
    private String paymentTypeName;

    private Boolean showMessageDetails = Boolean.FALSE;
    private Boolean showMessageGeneral = Boolean.FALSE;

    private Boolean printButton = Boolean.FALSE;

    private Boolean payed = Boolean.FALSE;

    private Boolean saveValidation;

    private GlAccount account;
    private GlAccount accountGLItems;

    private Date dateFrom;
    private Date dateTo;

    // DB Entities
    private GlBankTransaction glBankTransaction;
    private GlBankTransaction glBankTransactionExist;

    private GlBankTransaction glBankTransactionSelectionPre;
    private List<GlBankTransaction> glBankTransactionListPre;

    private List<GlBankTransactionDetail> glBankTransactionDetailsList;
    private List<GlBankTransactionDetail> glBankTransactionDetailsUpdatedList;

    // Interface Entities
    private GlBankTransactionEntity glBankTransactionEntity;
    private GlBankTransactionEntity glBankTransactionEntityMapper;
    private GlBankTransactionDetailEntity glBankTransactionDetailEntity;
    private GlBankTransactionDetailEntity glBankTransactionDetailEntitySelected;

    private List<GlBankTransactionDetailEntity> glBankTransactionDetailEntityList;
    private List<GlBankTransactionDetail> glBankTransactionDetailsDeletedList;

    private boolean customerView = false;
    private boolean accountView = false;
    private boolean supplierView = false;
    private boolean printAndSave = false;

    // Lists
    private List<GlAccount> glAccountList;
    private List<CostCenter> costCenterList;
    private List<GlBank> glBankList;
    private List<GlAdminUnit> adminUnitList;
    private List<InvOrganizationSite> organizationList;
    private List<InvOrganizationSite> supplierList;
    private List<InvOrganizationSite> customerList;

    private Map<Integer, GlBankTransactionDetail> glBankTransactionDetailsMap = new HashMap();

    private GlBankConverter bankConverter;
    private InvOrganizationSiteConverter invOrganizationSiteConverter;

    private GlaccountConverter glAccountConverter;

    private Symbol symbol;
    private List<Symbol> symbolList;
    private List<Symbol> glItemsList;
    private SymbolConverter symbolConverter;
    private Integer OrganizationTypeAfterDialogConfirmation;
    private String remark1;
    private String remark2;
    private HttpSession session;
    private Boolean installment;
    private Boolean sendSms;
    private Boolean allowAccount;
    private InstTransactionViewConverter instTransactionViewConverter;
    private List<InstTransactionView> instTransactionViewList;
    private StringBuilder errorMessageBuilder;
    private StringBuilder errorNoNeedMessageBuilder;
    private SymbolConverter glItemConverter;

    private AccountsSystemSettings accountsSystemSettings;
    private List<GlBankTransactionInvoiceView> glBankTransactionInvoiceViewList;
    @EJB
    private GlBankTransactionInvoiceViewService bankTransactionInvoiceViewService;
    @EJB
    AccountsSystemSettingsService accountsSystemSettingsService;

    @EJB
    private GlBankTransactionService glBankTransactionService;

    @EJB
    private GlBankTransactionDetailsService glBankTransactionDetailsService;

    @EJB
    private GlAccountService glAccountService;

    @EJB
    private CostCenterService costCenterService;

    @EJB
    private GlAdminUnitService glAdminUnitService;

    @EJB
    private OrganizationSiteService organizationSiteService;

    @EJB
    private TobyUserBankService tobyUserBankService;

    @EJB
    SymbolService symbolService;
    
    @EJB
    SupplierAccountService supplierAccountService;

    @Override
    @PostConstruct
    public void init() {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            setUserData((UserData) context.getSessionMap().get("userLogInData"));
            setScreenMode((String) context.getSessionMap().get("ScreenMode"));
            setBranchId(getUserData().getUserBranch().getId());
            setCompanyId(getUserData().getCompany().getId());
            setUri(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI());

            // lists
            glAccountList = new ArrayList<>();
            costCenterList = new ArrayList<>();
            glBankList = new ArrayList<>();
            adminUnitList = new ArrayList<>();
            organizationList = new ArrayList<>();
            glItemsList = new ArrayList<>();
            getGlBankTransactionInvoiceViewList();

            load();

            paymentDropDownView = true;

            organizationTypeUpdated = 0; // set to 0 during ad
            setAllowAccount(Boolean.TRUE);

            glBankTransaction.setCompanyId(getUserData().getCompany());
            glBankTransaction.setBranchId(getUserData().getUserBranch());
            glBankTransaction.setTransactionType(0);

            setAccountsSystemSettings(accountsSystemSettingsService.getInventoryByCompanyId(getUserData().getCompany().getId()));
            if (getAccountsSystemSettings() != null && getAccountsSystemSettings().getWorkWithAccounts() != null && !getAccountsSystemSettings().getWorkWithAccounts().equals("ALLOW_ACCOUNT")) {
                setAllowAccount(Boolean.FALSE);
            }

            if (getScreenMode() != null && getScreenMode().equalsIgnoreCase("edit")) {
                setGlBankTransactionId((Integer) context.getSessionMap().get("glBankTransactionIdSeclected"));
                findnotesReceivablesObject(getGlBankTransactionId());
            }

            FacesContext fCtx = FacesContext.getCurrentInstance();
            session = (HttpSession) fCtx.getExternalContext().getSession(false);
            String sessionId = getSession().getId();
            getSession().setAttribute(sessionId, getGlBankTransactionId());

            fillLists();

            viewPaymentFields();
            updateTransactionRate();

            if (glBankList == null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                try {
                    fc.getExternalContext().redirect("../base/financailyear.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(GeneralJournalBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            checkVisability();
            if (glBankTransactionEntity.getId() == null) {
                printButton = Boolean.TRUE;
            } else {
                printButton = Boolean.FALSE;
            }

            /* symbolList = symbolService.getAllDocumentsByCompanyId(getUserData().getCompany().getId());
             if (symbolList != null && !symbolList.isEmpty()) {
             symbolConverter = new SymbolConverter(symbolList);
             }*/
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "init");
        }
    }
    
    
    public void check() {
        glBankTransactionInvoiceViewList =bankTransactionInvoiceViewService.getGlBankTransactionInvoiceViewList(glBankTransactionDetailEntity.getInvOrganizationSite().getId(), getUserData().getUser());
        glBankTransactionEntity.setAccountStatement(supplierAccountService.getAccountForOrganizationSite(glBankTransactionDetailEntity.getInvOrganizationSite().getId()));
    } 

    private void findnotesReceivablesObject(int glBankTransId) {
        try {
            glBankTransactionExist = glBankTransactionService.findGlBankTransactionById(glBankTransId);
            glBankTransactionDetailsList = glBankTransactionDetailsService.getAllGlBankTransactionDetailsByGlBankTransactionId(glBankTransId);
            glBankTransactionEntity = mapGlBankTransactionToGlBankTransactionEntity(glBankTransactionExist);
            organizationTypeUpdated = getGlBankTransactionEntity().getOrganizationType();
            if (glBankTransactionEntity != null) {
                remark1 = glBankTransactionEntity.getRemark() != null ? glBankTransactionEntity.getRemark() : null;
                remark2 = glBankTransactionEntity.getRemark2() != null ? glBankTransactionEntity.getRemark2() : null;
            }
            payed = true;
            paymentDropDownView = false;
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "findnotesReceivablesObject");
        }
    }

    @Override

    public void load() {
        try {
            glBankTransactionEntity = new GlBankTransactionEntity();
            glBankTransactionEntity.setOrganizationType(2);
            glBankTransactionEntity.setChequeStatus(0);
            glBankTransactionEntity.setChequeDate(getGlYearSelection() != null ? getGlYearSelection().getDateFrom() : null);
            glBankTransactionEntity.setChequeDueDate(getGlYearSelection() != null ? getGlYearSelection().getDateFrom() : null);
            glBankTransactionEntity.setPaymentType(0);
            glBankTransactionEntity.setSerial(null);
            glBankTransactionEntity.setValue(glBankTransactionEntity.getValue() != null ? glBankTransactionEntity.getValue().setScale(2, BigDecimal.ROUND_UP) : null);
            glBankTransactionEntity.setValueLocal(glBankTransactionEntity.getValueLocal() != null ? glBankTransactionEntity.getValueLocal().setScale(2, BigDecimal.ROUND_UP) : null);
            glBankTransactionEntity.setRate(glBankTransactionEntity.getRate() != null ? glBankTransactionEntity.getRate().setScale(2, BigDecimal.ROUND_UP) : null);

            glBankTransactionEntity.setDate(new Date());
            glBankTransaction = new GlBankTransaction();
            glBankTransactionDetailEntity = new GlBankTransactionDetailEntity();
            glBankTransactionDetailsList = new ArrayList<>();
            glBankTransactionDetailsUpdatedList = new ArrayList<>();
            glBankTransactionDetailEntityList = new ArrayList<>();
            glBankTransactionDetailEntitySelected = new GlBankTransactionDetailEntity();
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "load");
        }

    }

    public Boolean checkAllowDate() {
        try {
            if (glBankTransactionEntity.getDate().after(new Date())) {
                if (getAccountsSystemSettings().getAllowSubsequentCacheAdministration().equalsIgnoreCase("NOT_ALLOW_AFTER_DATE")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "غير مسموح بتاريخ اكبر من تاريخ اليوم"));
                    setShowMessageDetails(true);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "checkAllowDate");
            return false;
        }

    }

    private void fillLists() {
        try {
            costCenterList = costCenterService.getAllSubCostCenterByBranchIdActive(branchId);

//        glBankList = glBankService.getAllGlBankByBranchId(branchId);
            fillGlBanks();

            adminUnitList = glAdminUnitService.getAllSubAdminUnitByBranchIdActive(branchId);
            glItemsList = symbolService.getGLItemsByCompanyId(companyId);
            glItemConverter = new SymbolConverter(glItemsList);
            fillAccounts();

            if (glBankTransactionEntity.getGlBank() == null) {
                glBankTransactionEntity.setGlBank((glBankList != null && !glBankList.isEmpty()) ? glBankList.get(0) : null);
            }
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "fillLists");
        }

    }

    private void fillGlBanks() {
        try {
            if (glBankTransactionEntity.getPaymentType() != 1) {
                glBankList = tobyUserBankService.getAllGlBankForUserByTypeAndBranchId(getUserData().getUser().getId(), getBranchId(), 0);
            } else {
                glBankList = tobyUserBankService.getAllGlBankForUserByTypeAndBranchId(getUserData().getUser().getId(), getBranchId(), 1);
            }

            bankConverter = new GlBankConverter(glBankList);
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "fillGlBanks");
        }
    }

    public void loadDialog() {
        try {
            glBankTransactionListPre = glBankTransactionService.getALLGlBankTransactionRecievableNotloadByBranchId(getBranchId(), 3);
            OpenDlg("dlg2");
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "loadDialog");
        }

    }

    public void OpenDlg(String dlgvar) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('" + dlgvar + "').show();");
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "OpenDlg");
        }
    }

    public void CloseDlg(String dlgvar) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('" + dlgvar + "').hide();");
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "CloseDlg");
        }
    }

    public void loadnotes() {
        try {
            findnotesReceivablesObject(glBankTransactionSelectionPre.getId());
            glBankTransactionEntity.setSerial(null);
            glBankTransactionEntity.setId(null);
            glBankTransactionEntity.setSerailParent(glBankTransactionSelectionPre.getSerial());
            CloseDlg("dlg2");
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "loadnotes");
        }

    }

    public void fillAccounts() {
        try {
            Integer type = getGlBankTransactionEntity().getOrganizationType();
            if (type == 0) {
                if (customerList == null || customerList.size() == 0) {
                    customerList = organizationSiteService.getorganizationSiteByBranchIdForGlBankModule(branchId, true, type);
                }
                organizationList = customerList;
            } else if (type == 1) {
                if (supplierList == null || customerList.size() == 0) {
                    supplierList = organizationSiteService.getorganizationSiteByBranchIdForGlBankModule(branchId, true, type);
                }
                organizationList = supplierList;
            } else if (type == 2) {
//                if (glAccountList == null || glAccountList.size() == 0) {
                if (allowAccount) {
                    glAccountList = glAccountService.getBranchGLAccountsActiveWithoutGlBankAccounts(branchId);
                } else {
                    glAccountList = new ArrayList<>();
                    for (Symbol symbol : glItemsList) {
                        accountGLItems = new GlAccount();
                        accountGLItems = symbol.getAccountId();
                        glAccountList.add(accountGLItems);
                    }
                }
//                }
            }

            if ((type == 0 || type == 1) && organizationList != null && !organizationList.isEmpty()) {
                glAccountList = new ArrayList<>();
                for (InvOrganizationSite invOrganizationSite : organizationList) {
                    account = new GlAccount();
                    account = invOrganizationSite.getAccountId();
                    glAccountList.add(account);
                }

            }

            setInvOrganizationSiteConverter(new InvOrganizationSiteConverter(organizationList));
            setGlAccountConverter(new GlaccountConverter(glAccountList));
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "fillAccounts");
        }
    }

    public void checkVisability() {
        try {
            viewFooterColms();
            fillAccounts();
            // loadInstContract();
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "checkVisability");
        }
    }

    public void viewFooterColms() {
        try {
            if (glBankTransactionEntity.getOrganizationType() == 2) {
                customerView = false;
                accountView = true;
                supplierView = false;
            } else if (glBankTransactionEntity.getOrganizationType() == 0) {
                accountView = false;
                customerView = true;
                supplierView = false;
            } else {
                accountView = false;
                customerView = false;
                supplierView = true;
            }
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "viewFooterColms");
        }
    }

    public void changeOrganizationSiteCancel() {
        try {
            glBankTransactionEntity.setOrganizationType(organizationTypeUpdated);
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "changeOrganizationSiteCancel");
        }
    }

    public void changeOrganizationSiteOk() {
        try {
//            if (glBankTransactionDetailEntityList != null && !glBankTransactionDetailEntityList.isEmpty()) {
//                glBankTransactionEntity.setOrganizationType(OrganizationTypeAfterDialogConfirmation);
//            }
            if (!Objects.equals(organizationTypeUpdated, glBankTransactionEntity.getOrganizationType())) {
                organizationTypeUpdated = glBankTransactionEntity.getOrganizationType();
                glBankTransactionDetailEntityList = new ArrayList<>();

                glBankTransactionDetailsDeletedList = glBankTransactionDetailsList;
            }

            glBankTransactionDetailEntitySelected = new GlBankTransactionDetailEntity();
            glBankTransactionDetailEntityList = new ArrayList<>();
            fillAccounts();
            viewFooterColms();
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "changeOrganizationSiteOk");
        }
    }

    public void viewPaymentFields() {
        try {

            if (glBankTransactionEntity.getPaymentType() == 0) {
                checkView = false;
                lineCheckView = false;
            } else if (glBankTransactionEntity.getPaymentType() == 1) {
                checkView = true;
                lineCheckView = false;
            } else {
                checkView = false;
                lineCheckView = true;
            }

            fillGlBanks();
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "viewPaymentFields");
        }
    }

    /**
     * public void updateDebitAccount() { if
     * (glBankTransactionDetailEntitySelected != null) {
     *
     * if (glBankTransactionDetailEntitySelected.getInvOrganizationSite() ==
     * null ||
     * glBankTransactionDetailEntitySelected.getInvOrganizationSite().getAccountId()
     * == null) { FacesContext.getCurrentInstance().addMessage(null, new
     * FacesMessage(FacesMessage.SEVERITY_ERROR,
     * getUserData().getUserDDs().get("ERROR"), "يجب اختيار جهة التعامل"));
     * return; } else {
     * glBankTransactionDetailEntitySelected.setAccountCreditId(glBankTransactionDetailEntitySelected.getInvOrganizationSite().getAccountId().getId());
     * }
     *
     * if (glBankTransactionEntity.getGlBank() == null) {
     * FacesContext.getCurrentInstance().addMessage(null, new
     * FacesMessage(FacesMessage.SEVERITY_ERROR,
     * getUserData().getUserDDs().get("ERROR"), "يجب اختيار الخزنة")); } else {
     * glBankTransactionDetailEntitySelected.setAccountDebitId(glBankTransactionEntity.getGlBank().getAccountId().getId());
     * } } }
     */
    private GlBankTransactionEntity mapGlBankTransactionToGlBankTransactionEntity(GlBankTransaction bankTransaction) {
        try {

            glBankTransactionEntityMapper = new GlBankTransactionEntity();
            glBankTransactionEntityMapper.setId(bankTransaction.getId());
            glBankTransactionEntityMapper.setSerial(bankTransaction.getSerial());
            glBankTransactionEntityMapper.setDate(bankTransaction.getDate() != null ? bankTransaction.getDate() : new Date());
            glBankTransactionEntityMapper.setPaymentType(bankTransaction.getPaymentType() != null ? bankTransaction.getPaymentType() : 0);
            glBankTransactionEntityMapper.setPostFlag(bankTransaction.getPostFlag());
            if (bankTransaction.getPaymentType() == 1) {
                glBankTransactionEntityMapper.setChequeDate(bankTransaction.getChequeDate() != null ? bankTransaction.getChequeDate() : new Date());
                glBankTransactionEntityMapper.setChequeDueDate(bankTransaction.getChequeDueDate() != null ? bankTransaction.getChequeDueDate() : new Date());
                glBankTransactionEntityMapper.setChequeNumber(bankTransaction.getChequeNumber() != null ? bankTransaction.getChequeNumber() : null);
                glBankTransactionEntityMapper.setChequeStatus(bankTransaction.getChequeStatus() != null ? bankTransaction.getChequeStatus() : 0);
                paymentTypeName = "الشيك";
            } else if (bankTransaction.getPaymentType() == 2) {
                glBankTransactionEntityMapper.setChequeStatus(bankTransaction.getChequeStatus() != null ? bankTransaction.getChequeStatus() : 0);
                paymentTypeName = "الشيك الخطي";
            } else {
                paymentTypeName = "نقدي";
            }

            glBankTransactionEntityMapper.setGlBank(bankTransaction.getGlBankId() != null ? bankTransaction.getGlBankId() : null);
            glBankTransactionEntityMapper.setOrganizationType(bankTransaction.getOrganizationType() != null ? bankTransaction.getOrganizationType() : 0);
            glBankTransactionEntityMapper.setRemark(bankTransaction.getRemark() != null ? bankTransaction.getRemark() : null);
            glBankTransactionEntityMapper.setRemark2(bankTransaction.getRemark2());
            glBankTransactionEntityMapper.setTransactionType(bankTransaction.getTransactionType() != null ? bankTransaction.getTransactionType() : 0);
            glBankTransactionEntityMapper.setValue(bankTransaction.getValue() != null ? bankTransaction.getValue().setScale(2, BigDecimal.ROUND_UP) : BigDecimal.ZERO);
            glBankTransactionEntityMapper.setRate(bankTransaction.getRate() != null ? bankTransaction.getRate().setScale(2, BigDecimal.ROUND_UP) : BigDecimal.ONE);
            glBankTransactionEntityMapper.setValueLocal(bankTransaction.getValueLocal() == null ? BigDecimal.ZERO : bankTransaction.getValueLocal().setScale(2, BigDecimal.ROUND_UP));
            glBankTransactionEntityMapper.setGlYear(bankTransaction.getGlYear() != null ? bankTransaction.getGlYear() : null);

            for (GlBankTransactionDetail transactionDetail : glBankTransactionDetailsList) {
                glBankTransactionDetailEntity = new GlBankTransactionDetailEntity();
                ++index;
                glBankTransactionDetailEntity.setIndex(index);

                glBankTransactionDetailEntity.setId(transactionDetail.getId() != null ? transactionDetail.getId() : null);
                glBankTransactionDetailEntity.setCostCenterId(transactionDetail.getCostCenterId() != null ? transactionDetail.getCostCenterId().getId() : null);
                glBankTransactionDetailEntity.setAccountCreditId(transactionDetail.getAccountCreditId() != null ? transactionDetail.getAccountCreditId().getId() : null);
                glBankTransactionDetailEntity.setAccountDebitId(transactionDetail.getAccountDebitId() != null ? transactionDetail.getAccountDebitId().getId() : null);
                glBankTransactionDetailEntity.setAdminUnitId(transactionDetail.getAdminUnitId() != null ? transactionDetail.getAdminUnitId().getId() : null);
                glBankTransactionDetailEntity.setGlBankTransactionId(transactionDetail.getGlBankTransactionId() != null ? transactionDetail.getGlBankTransactionId().getId() : null);
                glBankTransactionDetailEntity.setRemarks(transactionDetail.getRemarks() != null ? transactionDetail.getRemarks() : null);
                glBankTransactionDetailEntity.setInvOrganizationSite(transactionDetail.getOrganizationSiteId() != null ? transactionDetail.getOrganizationSiteId() : null);
                glBankTransactionDetailEntity.setValue(transactionDetail.getValue() != null ? transactionDetail.getValue().setScale(2, BigDecimal.ROUND_UP) : null);
                glBankTransactionDetailEntity.setValueLocal(transactionDetail.getValueLocal() != null ? transactionDetail.getValueLocal().setScale(2, BigDecimal.ROUND_UP) : null);
                glBankTransactionDetailEntity.setGlItems(transactionDetail.getSymbolId());
                
//            valueSummary = valueSummary.add(glBankTransactionDetailEntity.getValue() != null ? glBankTransactionDetailEntity.getValue() : BigDecimal.ZERO);
                glBankTransactionDetailsMap.put(transactionDetail.getId(), transactionDetail);
                glBankTransactionDetailEntityList.add(glBankTransactionDetailEntity);
            }

//        localValue = valueSummary.multiply(glBankTransactionEntityMapper.getRate());
            return glBankTransactionEntityMapper;
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "mapGlBankTransactionToGlBankTransactionEntity");
            return null;
        }
    }

    public void updateTransactionRate() {
        try {
//            checkDate(true);
            if (glBankTransactionEntity.getGlBank() != null && glBankTransactionEntity.getGlBank().getCurrencyId() != null) {
                if (glBankTransactionEntity.getId() == null) {
                    glBankTransactionEntity.setRate(updateRate(glBankTransactionEntity.getGlBank().getCurrencyId(), glBankTransactionEntity.getDate()));
                }
                glBankTransactionEntity.setRate(glBankTransactionEntity.getRate() != null ? glBankTransactionEntity.getRate().setScale(2, BigDecimal.ROUND_UP) : null);
                changeRate();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "ظٹط¬ط¨ ط§ط®طھظٹط§ط± ط§ظ„ط®ط²ظٹظ†ط©"));
                glBankTransactionEntity.setValue(null);
                glBankTransactionEntity.setValueLocal(null);
            }

            checkAllowDate();
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "updateTransactionRate");
        }
    }

    public void changeRate() {
        try {
            calculateLocalValue();
//        updatetransactionDetailLocalValue();
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "changeRate");
        }
    }

    public void printAndSave() {
        try {
            printAndSave = true;
            save();
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "printAndSave");
        }
    }

    @Override
    public void calculateLocalValue() {
        try {
            glBankTransactionEntity.setValueLocal((glBankTransactionEntity.getValue() == null ? BigDecimal.ZERO : glBankTransactionEntity.getValue()).multiply(glBankTransactionEntity.getRate() == null ? BigDecimal.ONE : glBankTransactionEntity.getRate()));
            glBankTransactionEntity.setValueLocal(glBankTransactionEntity.getValueLocal() != null ? glBankTransactionEntity.getValueLocal().setScale(2, BigDecimal.ROUND_UP) : null);
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "calculateLocalValue");
        }
    }

    public void updatetransactionDetailLocalValue() {
        try {
            for (GlBankTransactionDetailEntity detailEntity : glBankTransactionDetailEntityList) {
                detailEntity.setValueLocal((detailEntity.getValue() == null ? BigDecimal.ZERO : detailEntity.getValue()).multiply(glBankTransactionEntity.getRate() == null ? BigDecimal.ONE : glBankTransactionEntity.getRate()));
            }
            updateLocalValueSummary();
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "updatetransactionDetailLocalValue");
        }
    }

    public void updateSummition(BigDecimal newValue, BigDecimal oldValue) {
        try {
            glBankTransactionEntity.getRate();

//        valueSummary = valueSummary.subtract(oldValue == null ? BigDecimal.ZERO : oldValue);
//        valueSummary = valueSummary.add(newValue == null ? BigDecimal.ZERO : newValue);
            updateLocalValueSummary();
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "updateSummition");
        }
    }

    public void updateLocalValueSummary() {

//        localValue = valueSummary.multiply(glBankTransactionEntity.getRate() != null ? glBankTransactionEntity.getRate() : BigDecimal.ZERO);
    }

    @Override
    public String save() {
        try {
            Map<String, String> userDDs = getUserData().getUserDDs();
            saveValidation = true;
            validateSave();
            if (saveValidation) {
                if (glBankTransactionEntity != null) {

                    if (!checkAllowDate()) {
                        return "";
                    }

                    glBankTransaction.setBranchId(getUserData().getUserBranch() != null ? getUserData().getUserBranch() : null);
                    glBankTransaction.setCompanyId(getUserData().getCompany() != null ? getUserData().getCompany() : null);

                    if (glBankTransactionEntity.getId() != null) {
                        glBankTransaction.setId(glBankTransactionEntity.getId());
                       
                        if(glBankTransactionEntity.getInvoiceId()!=null){
                        InvPurchaseInvoice invPurchaseInvoice =new InvPurchaseInvoice();
                        invPurchaseInvoice.setId(glBankTransactionEntity.getInvoiceId());
                          glBankTransaction.setInvoiceId(invPurchaseInvoice);
                        }
                        glBankTransaction.setCreatedBy(getGlBankTransactionExist().getCreatedBy() != null ? getGlBankTransactionExist().getCreatedBy() : null);
                        glBankTransaction.setCreationDate(getGlBankTransactionExist().getCreationDate() != null ? getGlBankTransactionExist().getCreationDate() : null);

                        glBankTransaction.setSerial(glBankTransactionEntity.getSerial());

                        glBankTransaction.setModifiedBy(getUserData().getUser());
                        glBankTransaction.setModificationDate(new Date());

                    } else {
                        glBankTransaction.setCreatedBy(getUserData().getUser());
                        glBankTransaction.setCreationDate(new Date());

                    }
                    if (glBankTransactionSelectionPre != null && glBankTransactionSelectionPre.getId() != null) {
                        glBankTransaction.setParent(glBankTransactionSelectionPre);
                    }
                    glBankTransaction.setDate(glBankTransactionEntity.getDate() != null ? glBankTransactionEntity.getDate() : null);
                    glBankTransaction.setPostFlag(0);
                    glBankTransaction.setPaymentType(glBankTransactionEntity.getPaymentType());
                    glBankTransaction.setRemark(glBankTransactionEntity.getRemark());
                    glBankTransaction.setRemark2(glBankTransactionEntity.getRemark2());
                    glBankTransaction.setOrganizationType(glBankTransactionEntity.getOrganizationType() != null ? glBankTransactionEntity.getOrganizationType() : null);
                    if (!getUri().contains("notesreceivablesFormpre")) {
                        glBankTransaction.setTransactionType(1);
                    } else {
                        glBankTransaction.setTransactionType(3);
                    }

                    glBankTransaction.setValue(glBankTransactionEntity.getValue() != null ? glBankTransactionEntity.getValue() : BigDecimal.ZERO);
                    glBankTransaction.setValueLocal(glBankTransactionEntity.getValueLocal() != null ? glBankTransactionEntity.getValueLocal() : BigDecimal.ZERO);
                    glBankTransaction.setRate(glBankTransactionEntity.getRate() != null ? glBankTransactionEntity.getRate() : BigDecimal.ONE);
                    glBankTransaction.setGlYear(getGlYearSelection().getYear() != null ? getGlYearSelection().getYear() : null);
                    if (glBankTransactionEntity.getPaymentType() == 1) {
                        glBankTransaction.setChequeDate(glBankTransactionEntity.getChequeDate() != null ? glBankTransactionEntity.getChequeDate() : new Date());
                        glBankTransaction.setChequeNumber(glBankTransactionEntity.getChequeNumber() != null ? glBankTransactionEntity.getChequeNumber() : null);
                    }

                    glBankTransaction.setChequeDueDate(glBankTransactionEntity.getChequeDueDate() != null ? glBankTransactionEntity.getChequeDueDate() : new Date());
                    glBankTransaction.setChequeStatus(glBankTransactionEntity.getChequeStatus() != null ? glBankTransactionEntity.getChequeStatus() : 0);

                    if (glBankTransactionEntity.getGlBank() != null) {
                        glBankTransaction.setGlBankId(glBankTransactionEntity.getGlBank());
                    }

                    if (glBankTransactionEntity.getInvoiceId() != null) {
                        InvPurchaseInvoice invoiceId = new InvPurchaseInvoice();
                        invoiceId.setId(glBankTransactionEntity.getInvoiceId());
                        glBankTransaction.setInvoiceId(invoiceId);
                    }

                    GlBankTransactionDetail bankTransactionDetail = new GlBankTransactionDetail();

                    if (glBankTransactionDetailEntity.getId() != null) {
                        bankTransactionDetail = glBankTransactionDetailsMap.get(glBankTransactionDetailEntity.getId());
                        bankTransactionDetail.setModificationDate(new Date());
                        bankTransactionDetail.setModifiedBy(getUserData().getUser() != null ? getUserData().getUser() : null);
                    } else {
                        bankTransactionDetail.setCreatedBy(getUserData().getUser() != null ? getUserData().getUser() : null);
                        bankTransactionDetail.setCreationDate(new Date());
                    }

                    bankTransactionDetail.setCompanyId(getUserData().getCompany() != null ? getUserData().getCompany() : null);

                    if (glBankTransactionDetailEntity.getAccountDebitId() != null) {
                        GlAccount accountDebitId = new GlAccount();
                        accountDebitId.setId(glBankTransactionDetailEntity.getAccountDebitId());
                        bankTransactionDetail.setAccountDebitId(accountDebitId);
                    }

                    if (glBankTransactionEntity.getOrganizationType() == 2) {
                        bankTransactionDetail.setOrganizationSiteId(null);
                        if (allowAccount) {
                            if (glBankTransactionDetailEntity.getAccountCreditId() != null) {
                                GlAccount accountCreditId = new GlAccount();
                                accountCreditId.setId(glBankTransactionDetailEntity.getAccountCreditId());
                                bankTransactionDetail.setAccountCreditId(accountCreditId);
                            }
                        } else {
                            if (glBankTransactionDetailEntity.getGlItems() != null) {
                                GlAccount accountCreditId = new GlAccount();
                                if (glBankTransactionDetailEntity.getGlItems().getAccountId() != null) {
                                    accountCreditId.setId(glBankTransactionDetailEntity.getGlItems().getAccountId().getId());
                                    bankTransactionDetail.setAccountCreditId(accountCreditId);
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, userDDs.get("ERROR"), "العميل غير مربوط على حساب"));
                                    return "";

                                }
                            }
                        }
                    } else {
                        if (glBankTransactionDetailEntity.getInvOrganizationSite() != null) {
                            bankTransactionDetail.setOrganizationSiteId(glBankTransactionDetailEntity.getInvOrganizationSite() != null ? glBankTransactionDetailEntity.getInvOrganizationSite() : null);
                            GlAccount accountCreditId = new GlAccount();
                            if (glBankTransactionDetailEntity.getInvOrganizationSite().getAccountId() != null) {
                                accountCreditId.setId(glBankTransactionDetailEntity.getInvOrganizationSite().getAccountId().getId());
                                bankTransactionDetail.setAccountCreditId(accountCreditId);
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, userDDs.get("ERROR"), "العميل غير مربوط على حساب"));
                                return "";

                            }
                        }
                    }

                    bankTransactionDetail.setAccountDebitId(glBankTransaction.getGlBankId().getAccountId());

                    if (glBankTransactionDetailEntity.getAdminUnitId() != null) {
                        GlAdminUnit glAdminUnit = new GlAdminUnit();
                        glAdminUnit.setId(glBankTransactionDetailEntity.getAdminUnitId());
                        bankTransactionDetail.setAdminUnitId(glAdminUnit);
                    } else {
                        bankTransactionDetail.setAdminUnitId(null);
                    }

                    if (glBankTransactionDetailEntity.getCostCenterId() != null) {
                        CostCenter costCenter = new CostCenter();
                        costCenter.setId(glBankTransactionDetailEntity.getCostCenterId());
                        bankTransactionDetail.setCostCenterId(costCenter);
                    } else {
                        bankTransactionDetail.setCostCenterId(null);
                    }

                    bankTransactionDetail.setBankIdFrom(glBankTransactionEntity.getGlBank());
//                if(glBankTransactionDetailEntity.getInvOrganizationSite() != null){}
                    bankTransactionDetail.setRemarks(glBankTransactionDetailEntity.getRemarks());
                    bankTransactionDetail.setValue(glBankTransactionEntity.getValue() != null ? glBankTransactionEntity.getValue() : BigDecimal.ZERO);
                    bankTransactionDetail.setValueLocal(glBankTransactionEntity.getValueLocal() != null ? glBankTransactionEntity.getValueLocal() : BigDecimal.ZERO);
                    bankTransactionDetail.setSymbolId(glBankTransactionDetailEntity.getGlItems());
//            bankTransactionDetail.set
                    try {
                        glBankTransactionDetailsUpdatedList.add(bankTransactionDetail);
                        GlBankTransaction bankTransaction;
                        if (installment != null && installment) {
                            bankTransaction = glBankTransactionService.addGlBankTransactionForInstallment(glBankTransaction, glBankTransactionDetailsUpdatedList, null);
                            if (sendSms) {
                                sendSms();
                            }
                        } else {
                            bankTransaction = glBankTransactionService.addGlBankTransaction(glBankTransaction, glBankTransactionDetailsUpdatedList, new ArrayList<>(), companyId);
                        }
                        if (bankTransaction != null && bankTransaction.getId() != null && printAndSave) {
                            exportPDF(null);
                            printAndSave = false;
                        }
                        if (bankTransaction != null && bankTransaction.getId() != null) {
                            findnotesReceivablesObject(bankTransaction.getId());
                        }
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, userDDs.get("INFO"), userDDs.get("SAVED")));
                        exit();
                    } catch (Exception e) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, userDDs.get("ERROR"), userDDs.get("SAVED_ERROR")));
                    }

                    //exit("../notesreceivables/notesreceivablesList.xhtml");
                    //return "";
                }
            }
            return "";
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "save");
            return null;
        }
    }

    public void validateSave() {
        try {

            errorMessageBuilder = new StringBuilder();
            errorNoNeedMessageBuilder = new StringBuilder();
            GlAccount account;
            Integer x = 0;
            Integer y = 0;
            errorMessageBuilder.append("يجب اختيار ");
            errorNoNeedMessageBuilder.append("لا يمكن اختيار ");

            if (glBankTransactionEntity != null && glBankTransactionEntity.getPostFlag() != null && glBankTransactionEntity.getPostFlag() == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "تم ترحيل السند"));
                setShowMessageDetails(true);
                saveValidation = false;
            }
            if (glBankTransactionEntity.getGlBank() == null && glBankTransactionEntity.getGlBank().getAccountId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "يجب اختيار الخزنة"));
                setShowMessageDetails(true);
                saveValidation = false;
            }
            if (glBankTransactionEntity.getDate() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "يجب اختيار التاريخ"));
                setShowMessageDetails(true);
                saveValidation = false;
            }
            if (glBankTransactionEntity.getValue() == null || glBankTransactionEntity.getValueLocal() == null || glBankTransactionEntity.getValue().compareTo(BigDecimal.ZERO) == -1 || glBankTransactionEntity.getValueLocal().compareTo(BigDecimal.ZERO) == -1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "قيمة المبلغ غير صحيحة"));
                setShowMessageDetails(true);
                saveValidation = false;
            }
            if (glBankTransactionEntity.getRate() == null || glBankTransactionEntity.getRate().compareTo(BigDecimal.ZERO) == -1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "معدل غير صحيح"));
                setShowMessageDetails(true);
                saveValidation = false;
            }

            if (glBankTransactionEntity.getOrganizationType() == 0) {
                if (glBankTransactionDetailEntity.getInvOrganizationSite() == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "يجب اختيار جهة التعامل"));
                    setShowMessageDetails(true);
                    saveValidation = false;
                } else if (glBankTransactionDetailEntity.getInvOrganizationSite() != null && glBankTransactionDetailEntity.getInvOrganizationSite().getAccountId() == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "لا يوجد حساب مخصص لهذا العميل"));
                    setShowMessageDetails(true);
                } else if (glBankTransactionDetailEntity.getGlItems() != null && glBankTransactionDetailEntity.getGlItems().getAccountId() == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "لا يوجد حساب مخصص لهذا البند"));
                    setShowMessageDetails(true);
                }
                
                /*  if (installment && glBankTransactionEntity.getValue() != null && glBankTransactionEntity.getInstTransactionView() != null && glBankTransactionEntity.getInstTransactionView().getRemaining().compareTo(glBankTransactionEntity.getValue()) == -1) {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "لا يمكن دفع قيمة اكبر من قيمة القسط"));
                 saveValidation = false;
                 } */
                if (glBankTransactionEntity.getValue() != null && glBankTransactionEntity.getValue().compareTo(BigDecimal.ZERO) <= 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "لا يمكن دفع  قيمه تساوي صفر"));
                    saveValidation = false;
                }

            } else if (glBankTransactionEntity.getOrganizationType() == 1) {
                if (glBankTransactionDetailEntity.getInvOrganizationSite() == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "يجب اختيار جهة التعامل"));
                    setShowMessageDetails(true);
                    saveValidation = false;
                }
            } else if (glBankTransactionEntity.getOrganizationType() == 2) {

                if (allowAccount) {
                    if (glBankTransactionDetailEntity.getAccountCreditId() == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "يجب اختيار جهة التعامل"));
                        setShowMessageDetails(true);
                        saveValidation = false;
                    }
                    account = glAccountService.findGlAccount(glBankTransactionDetailEntity.getAccountCreditId());
                } else {
                    if (glBankTransactionDetailEntity.getGlItems() == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "يجب اختياربند "));
                        setShowMessageDetails(true);
                        saveValidation = false;
                    }
                    account = glAccountService.findGlAccount(glBankTransactionDetailEntity.getGlItems().getAccountId().getId());
                }

                account = glAccountService.findGlAccount(glBankTransactionDetailEntity.getAccountCreditId());

                if (account != null) {
                    if (account.getCostCenter().toString().equalsIgnoreCase("CC_MANDATORY") && glBankTransactionDetailEntity.getCostCenterId() == null) {
                        if (x > 0) {
                            errorMessageBuilder.append("و ");
                        }
                        errorMessageBuilder.append("مركز تكلفة ");
                        saveValidation = false;
                        x++;
                    }

                    if (account.getAdministrativeUnit().toString().equalsIgnoreCase("ADMUNT_MANDATORY") && glBankTransactionDetailEntity.getAdminUnitId() == null) {
                        if (x > 0) {
                            errorMessageBuilder.append("و ");
                        }
                        errorMessageBuilder.append("وحدة ادارية ");
                        saveValidation = false;
                        x++;
                    }

                    if (account.getAdministrativeUnit().toString().equalsIgnoreCase("ADMUNT_NONEED") && glBankTransactionDetailEntity.getAdminUnitId() != null) {
                        if (y > 0) {
                            errorNoNeedMessageBuilder.append("و ");
                        }
                        errorNoNeedMessageBuilder.append("وحدة ادارية ");
                        saveValidation = false;
                        y++;
                    }
                    if (account.getCostCenter().toString().equalsIgnoreCase("CC_NONEED") && glBankTransactionDetailEntity.getCostCenterId() != null) {
                        if (y > 0) {
                            errorNoNeedMessageBuilder.append("و ");
                        }
                        errorNoNeedMessageBuilder.append("مركز تكلفة ");
                        saveValidation = false;
                        y++;
                    }

                    if (glBankTransactionDetailEntity.getAccountCreditId() != null) {
                        if (x > 0) {
                            errorMessageBuilder.append("لهذا الحساب");
                        }
                        if (y > 0) {
                            errorNoNeedMessageBuilder.append("لهذا الحساب");
                        }
                    }
                }
                if (errorMessageBuilder.toString().length() > 11) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), (errorMessageBuilder.toString())));
                }
                if (errorNoNeedMessageBuilder.toString().length() > 26) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), (errorNoNeedMessageBuilder.toString())));
                }
            }
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "validateSave");
        }
    }

    public List<Symbol> completeSymbol(String query) {
        try {
            List<Symbol> symbolsList = getSymbolList();
            if (query == null || query.trim().equals("")) {
                symbolConverter = new SymbolConverter(symbolsList);
                return symbolsList;
            }
            List<Symbol> filteredSymbols = new ArrayList<>();
            String nameAr;
            String code;
            Symbol symbolFilter;
            for (int i = 0; i < getSymbolList().size(); i++) {
                symbolFilter = symbolsList.get(i);
                nameAr = symbolFilter.getName();
                if (nameAr != null && nameAr.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSymbols.contains(symbolFilter)) {
                        filteredSymbols.add(symbolFilter);
                    }
                }
            }
            symbolConverter = new SymbolConverter(filteredSymbols);
            return filteredSymbols;
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "completeSymbol");
            return null;
        }
    }

    public List<InstTransactionView> completInstTransactionView(String query) {
        try {
            List<InstTransactionView> instTransactionViewList = this.instTransactionViewList;
            if (query == null || query.trim().equals("")) {

                instTransactionViewConverter = new InstTransactionViewConverter(instTransactionViewList);
                return instTransactionViewList;
            }
            List<InstTransactionView> filteredInst = new ArrayList<>();

            String nameAr;
            String code;
            InstTransactionView transactionViewFilter;

            for (int i = 0; i < this.instTransactionViewList.size(); i++) {
                transactionViewFilter = instTransactionViewList.get(i);
                nameAr = new SimpleDateFormat("yyyy-MM-dd").format(transactionViewFilter.getDueDate());
                //nameAr = instConstructFilter.getDueDate().toString();
                if (nameAr != null && nameAr.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredInst.contains(transactionViewFilter)) {
                        filteredInst.add(transactionViewFilter);
                    }
                }

                code = transactionViewFilter.getRemaining().toString();
                if (code != null && code.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredInst.contains(transactionViewFilter)) {
                        filteredInst.add(transactionViewFilter);
                    }
                }
            }

            instTransactionViewConverter = new InstTransactionViewConverter(filteredInst);
            return filteredInst;
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "completInstTransactionView");
            return null;
        }
    }

    public void exportPDF(ActionEvent actionEvent) throws JRException, IOException {
        try {
            Integer z = glBankTransactionEntity.getHiddenId();
            List<GlBankTransactionDetailEntity> gbtdes = new ArrayList<>();
            if (glBankTransactionEntity != null && glBankTransactionEntity.getId() != null) {
                findnotesReceivablesObject(z);
                if (glBankTransactionDetailEntityList != null && !glBankTransactionDetailEntityList.isEmpty()) {
                    gbtdes.add(glBankTransactionDetailEntityList.get(0));
                    fillReport(prepareReport(), getUserData().getReportPath() + "notesReceivables.jasper", gbtdes, "pdf");
                }
            } else {
                save();
                if (glBankTransactionDetailEntityList != null && !glBankTransactionDetailEntityList.isEmpty()) {
                    gbtdes.add(glBankTransactionDetailEntityList.get(0));
                    fillReport(prepareReport(), getUserData().getReportPath() + "notesReceivables.jasper", gbtdes, "pdf");
                }
            }
        } catch (Exception e) {
            saveError(e, "notesReceivablesFormMB", "exportPDF");
        }

    }

    @Override
    public String getScreenName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resetDateFrom() {
        setDateFrom(getGlYearSelection() != null ? getGlYearSelection().getDateFrom() : null);
    }

    @Override
    public void resetDateTo() {
        setDateTo(getGlYearSelection() != null ? getGlYearSelection().getDateTo() : null);
    }

    @Override
    public void checkDate(Boolean dateType) {
        if (dateType) {
            if (checkFinancailYear(getGlBankTransactionEntity().getDate())) {
                resetDateFrom();
            }
        }
    }

    public void loadThePageAgain() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.getSessionMap().put("ScreenMode", "Add");
        exit("../notesreceivables/notesreceivablesForm.xhtml");

    }

    public String sendSms() {
        try {

            String username = "l3TTd3Ur";
            String password = "lC2DacCyTH";
            String language = "2";
            String sender = "toby";
            String mobile = glBankTransactionDetailEntity.getInvOrganizationSite() != null ? glBankTransactionDetailEntity.getInvOrganizationSite().getMobile() : null;
            //String mobile = "201144038686";
            String message = "شكرا لحضراتكم تم دفع القسط عن شهر ";

            String instMonth = new SimpleDateFormat("MM").format(glBankTransactionEntity.getValue() != null ? glBankTransactionEntity.getDate() : null);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String paidValue = df.format(glBankTransactionEntity.getValue() != null ? glBankTransactionEntity.getValue() : null);
            message = message + instMonth + "بمبلغ " + paidValue;

            String requestUrl = "https://smsmisr.com/api/webapi/?"
                    + "username=" + URLEncoder.encode(username, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8")
                    + "&language=" + URLEncoder.encode(language, "UTF-8")
                    + "&sender=" + URLEncoder.encode(sender, "UTF-8")
                    + "&mobile=" + URLEncoder.encode(mobile, "UTF-8")
                    + "&message=" + URLEncoder.encode(message, "UTF-8");
            //  + "&message=thanks";

            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(requestUrl.length()));
            conn.getOutputStream().write(requestUrl.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
            System.out.println(conn.getResponseMessage());

            conn.disconnect();
            return conn.getResponseMessage();
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
    }

    public String exit() {
        if (!getUri().contains("notesreceivablesFormpre")) {
            exit("../notesreceivables/notesreceivablesList.xhtml");
        } else {
            exit("../notesreceivablespre/notesreceivablesListpre.xhtml");
        }

        return "";
    }

    public HashMap prepareReport() {
        HashMap hashMap = new HashMap();
        try {
            Map<String, String> userDDs = getUserData().getUserDDs();

            hashMap.put("PrintedBy", getUserData().getUser().getName());
            hashMap.put("branchName", getUserData().getUserBranch().getNameAr());
            hashMap.put("companyName", getUserData().getCompany().getName());
            hashMap.put("amountText", userDDs.get("AMOUNT"));
            hashMap.put("dateText", userDDs.get("DATE"));
            //  hashMap.put("companyImage", getUserData().getImageReportPath());

            // hashMap.put("reportNameText", "سند قبض ");
            hashMap.put("recipientText", "المستلم");
            hashMap.put("SecretaryOfTheTreasuryText", "امين الخزينة");
            hashMap.put("recievedFromText", "استلمنا من");
            hashMap.put("accountManager", "مدير الحسابات");
            hashMap.put("confirmationText", "إعتماد");
            hashMap.put("remarkText2", ("مناولة"));
            hashMap.put("valueFromText", "وذلك قيمة");
            hashMap.put("documentNumberText", "رقم السند");
            hashMap.put("chequeDateText", "تاريخ الشيك");
            hashMap.put("chequeDateValue", glBankTransactionEntityMapper.getChequeDate() != null ? glBankTransactionEntityMapper.getChequeDate() : null);
            hashMap.put("deservedDateText", "تاريخ الاستحقاق");
            hashMap.put("deservedDateValue", glBankTransactionEntityMapper.getChequeDueDate() != null ? glBankTransactionEntityMapper.getChequeDueDate() : null);

            hashMap.put("rateText", "المعدل");
            hashMap.put("rateValue", glBankTransactionEntityMapper.getRate());
            hashMap.put("amountInRateText", userDDs.get("TOTAL"));
            hashMap.put("amountInRateValue", glBankTransactionEntityMapper.getValueLocal());

            hashMap.put("dateValue", glBankTransactionEntityMapper.getDate());
            hashMap.put("documentNumberValue", glBankTransactionEntityMapper.getSerial());
            hashMap.put("recipientValue", glBankTransactionEntityMapper.getGlBank().getName());
            hashMap.put("amountValue", glBankTransactionEntityMapper.getValue());
            hashMap.put("amountValueText", Tafqeet.numberToText(Double.parseDouble(glBankTransactionEntityMapper.getValue().toString()), "جنيه", "قرش"));
            hashMap.put("valueFromValue", remark1);
            hashMap.put("explainationValue", remark2);

            if (glBankTransactionEntity.getOrganizationType() == 1 || glBankTransactionEntity.getOrganizationType() == 0) {
                hashMap.put("recievedFromValue", glBankTransactionDetailEntity.getInvOrganizationSite() != null ? glBankTransactionDetailEntity.getInvOrganizationSite().getName() : null);
            } else if (glBankTransactionEntity.getOrganizationType() == 2) {
                GlAccount recipientaccount = new GlAccount();
                recipientaccount = glAccountService.findGlAccount(glBankTransactionDetailEntity.getAccountCreditId());
                hashMap.put("recievedFromValue", recipientaccount != null ? recipientaccount.getName() : null);
            }
            if (glBankTransactionEntityMapper.getPaymentType() != null) {
                switch (glBankTransactionEntityMapper.getPaymentType()) {
                    case 0:
                        hashMap.put("reportNameText", "سند قبض نقدي ");
                        break;
                    case 1:
                        hashMap.put("reportNameText", "سند قبض شيك ");
                        break;
                    case 2:
                        hashMap.put("reportNameText", "سند قبض شيك خطي ");
                        break;
                }
            }
            hashMap.put("copyWordIcon", getUserData().getImagePath() + "copyWordIcon.png");
            System.out.println(getUserData().getImagePath() + "copyWordIcon.png  -> 1");
            if (isFileExist(getUserData().getImageReportPath())) {
                hashMap.put("companyImage", getUserData().getImageReportPath());
            } else {
                hashMap.put("companyImage", null);
            }

        } catch (Exception e) {
            System.out.println(getUserData().getImagePath() + "copyWordIcon.png   -> 2");
            saveError(e, "notesReceivablesFormMB", "prepareReport");
        }
        System.out.println(getUserData().getImagePath() + "copyWordIcon.png   -> 3");
        return hashMap;
    }

    public void catchValues() {
        int x = 0;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getGlBankTransactionId() {
        return glBankTransactionId;
    }

    public void setGlBankTransactionId(Integer glBankTransactionId) {
        this.glBankTransactionId = glBankTransactionId;
    }

    public GlBankTransaction getGlBankTransaction() {
        return glBankTransaction;
    }

    public void setGlBankTransaction(GlBankTransaction glBankTransaction) {
        this.glBankTransaction = glBankTransaction;
    }

    public List<GlBankTransactionDetail> getGlBankTransactionDetailsList() {
        return glBankTransactionDetailsList;
    }

    public void setGlBankTransactionDetailsList(List<GlBankTransactionDetail> glBankTransactionDetailsList) {
        this.glBankTransactionDetailsList = glBankTransactionDetailsList;
    }

    public List<GlBankTransactionDetail> getGlBankTransactionDetailsUpdatedList() {
        return glBankTransactionDetailsUpdatedList;
    }

    public void setGlBankTransactionDetailsUpdatedList(List<GlBankTransactionDetail> glBankTransactionDetailsUpdatedList) {
        this.glBankTransactionDetailsUpdatedList = glBankTransactionDetailsUpdatedList;
    }

    public GlBankTransactionEntity getGlBankTransactionEntity() {
        return glBankTransactionEntity;
    }

    public void setGlBankTransactionEntity(GlBankTransactionEntity glBankTransactionEntity) {
        this.glBankTransactionEntity = glBankTransactionEntity;
    }

    public GlBankTransactionDetailEntity getGlBankTransactionDetailEntity() {
        return glBankTransactionDetailEntity;
    }

    public void setGlBankTransactionDetailEntity(GlBankTransactionDetailEntity glBankTransactionDetailEntity) {
        this.glBankTransactionDetailEntity = glBankTransactionDetailEntity;
    }

    public GlBankTransactionDetailEntity getGlBankTransactionDetailEntitySelected() {
        return glBankTransactionDetailEntitySelected;
    }

    public void setGlBankTransactionDetailEntitySelected(GlBankTransactionDetailEntity glBankTransactionDetailEntitySelected) {
        this.glBankTransactionDetailEntitySelected = glBankTransactionDetailEntitySelected;
    }

    public List<GlBankTransactionDetailEntity> getGlBankTransactionDetailEntityList() {
        return glBankTransactionDetailEntityList;
    }

    public void setGlBankTransactionDetailEntityList(List<GlBankTransactionDetailEntity> glBankTransactionDetailEntityList) {
        this.glBankTransactionDetailEntityList = glBankTransactionDetailEntityList;
    }

    public Boolean getShowMessageDetails() {
        return showMessageDetails;
    }

    public void setShowMessageDetails(Boolean showMessageDetails) {
        this.showMessageDetails = showMessageDetails;
    }

    public Boolean getShowMessageGeneral() {
        return showMessageGeneral;
    }

    public void setShowMessageGeneral(Boolean showMessageGeneral) {
        this.showMessageGeneral = showMessageGeneral;
    }

    public List<GlAccount> getGlAccountList() {
        return glAccountList;
    }

    public void setGlAccountList(List<GlAccount> glAccountList) {
        this.glAccountList = glAccountList;
    }

    public List<CostCenter> getCostCenterList() {
        return costCenterList;
    }

    public void setCostCenterList(List<CostCenter> costCenterList) {
        this.costCenterList = costCenterList;
    }

    public List<GlBank> getGlBankList() {
        return glBankList;
    }

    public void setGlBankList(List<GlBank> glBankList) {
        this.glBankList = glBankList;
    }

    public List<GlAdminUnit> getAdminUnitList() {
        return adminUnitList;
    }

    public void setAdminUnitList(List<GlAdminUnit> adminUnitList) {
        this.adminUnitList = adminUnitList;
    }

    /**
     * @return the organizationList
     */
    public List<InvOrganizationSite> getOrganizationList() {
        return organizationList;
    }

    /**
     * @param organizationList the organizationList to set
     */
    public void setOrganizationList(List<InvOrganizationSite> organizationList) {
        this.organizationList = organizationList;
    }

    /**
     * @return the glBankTransactionExist
     */
    public GlBankTransaction getGlBankTransactionExist() {
        return glBankTransactionExist;
    }

    /**
     * @param glBankTransactionExist the glBankTransactionExist to set
     */
    public void setGlBankTransactionExist(GlBankTransaction glBankTransactionExist) {
        this.glBankTransactionExist = glBankTransactionExist;
    }

    /**
     * @return the bankConverter
     */
    public GlBankConverter getBankConverter() {
        return bankConverter;
    }

    /**
     * @param bankConverter the bankConverter to set
     */
    public void setBankConverter(GlBankConverter bankConverter) {
        this.bankConverter = bankConverter;
    }

    /**
     * @return the invOrganizationSiteConverter
     */
    public InvOrganizationSiteConverter getInvOrganizationSiteConverter() {
        return invOrganizationSiteConverter;
    }

    /**
     * @param invOrganizationSiteConverter the invOrganizationSiteConverter to
     * set
     */
    public void setInvOrganizationSiteConverter(InvOrganizationSiteConverter invOrganizationSiteConverter) {
        this.invOrganizationSiteConverter = invOrganizationSiteConverter;
    }

    public Map<Integer, GlBankTransactionDetail> getGlBankTransactionDetailsMap() {
        return glBankTransactionDetailsMap;
    }

    public void setGlBankTransactionDetailsMap(Map<Integer, GlBankTransactionDetail> glBankTransactionDetailsMap) {
        this.glBankTransactionDetailsMap = glBankTransactionDetailsMap;
    }

    public GlAccount getAccount() {
        return account;
    }

    public void setAccount(GlAccount account) {
        this.account = account;
    }

    public boolean isAccountView() {
        return accountView;
    }

    public void setAccountView(boolean accountView) {
        this.accountView = accountView;
    }

    public GlaccountConverter getGlAccountConverter() {
        return glAccountConverter;
    }

    public void setGlAccountConverter(GlaccountConverter glAccountConverter) {
        this.glAccountConverter = glAccountConverter;
    }

    /**
     * @return the customerView
     */
    public boolean isCustomerView() {
        return customerView;
    }

    /**
     * @param customerView the customerView to set
     */
    public void setCustomerView(boolean customerView) {
        this.customerView = customerView;
    }

    public GlBankTransactionEntity getGlBankTransactionEntityMapper() {
        return glBankTransactionEntityMapper;
    }

    public void setGlBankTransactionEntityMapper(GlBankTransactionEntity glBankTransactionEntityMapper) {
        this.glBankTransactionEntityMapper = glBankTransactionEntityMapper;
    }

    public Boolean getCheckView() {
        return checkView;
    }

    public void setCheckView(Boolean checkView) {
        this.checkView = checkView;
    }

    public Boolean getLineCheckView() {
        return lineCheckView;
    }

    public void setLineCheckView(Boolean lineCheckView) {
        this.lineCheckView = lineCheckView;
    }

    public Boolean getPaymentDropDownView() {
        return paymentDropDownView;
    }

    public void setPaymentDropDownView(Boolean paymentDropDownView) {
        this.paymentDropDownView = paymentDropDownView;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    public boolean isSupplierView() {
        return supplierView;
    }

    public void setSupplierView(boolean supplierView) {
        this.supplierView = supplierView;
    }

    /**
     * @return the printButton
     */
    public Boolean getPrintButton() {
        return printButton;
    }

    /**
     * @param printButton the printButton to set
     */
    public void setPrintButton(Boolean printButton) {
        this.printButton = printButton;
    }

    /**
     * @return the saveValidation
     */
    public Boolean getSaveValidation() {
        return saveValidation;
    }

    /**
     * @param saveValidation the saveValidation to set
     */
    public void setSaveValidation(Boolean saveValidation) {
        this.saveValidation = saveValidation;
    }

    /**
     * @return the symbol
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the symbolList
     */
    public List<Symbol> getSymbolList() {
        return symbolList;
    }

    /**
     * @param symbolList the symbolList to set
     */
    public void setSymbolList(List<Symbol> symbolList) {
        this.symbolList = symbolList;
    }

    /**
     * @return the symbolConverter
     */
    public SymbolConverter getSymbolConverter() {
        return symbolConverter;
    }

    /**
     * @param symbolConverter the symbolConverter to set
     */
    public void setSymbolConverter(SymbolConverter symbolConverter) {
        this.symbolConverter = symbolConverter;
    }

    /**
     * @return the dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom the dateFrom to set
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the dateTo
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * @param dateTo the dateTo to set
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * @return the OrganizationTypeAfterDialogConfirmation
     */
    public Integer getOrganizationTypeAfterDialogConfirmation() {
        return OrganizationTypeAfterDialogConfirmation;
    }

    /**
     * @param OrganizationTypeAfterDialogConfirmation the
     * OrganizationTypeAfterDialogConfirmation to set
     */
    public void setOrganizationTypeAfterDialogConfirmation(Integer OrganizationTypeAfterDialogConfirmation) {
        this.OrganizationTypeAfterDialogConfirmation = OrganizationTypeAfterDialogConfirmation;
    }

    /**
     * @return the glBankTransactionDetailsDeletedList
     */
    public List<GlBankTransactionDetail> getGlBankTransactionDetailsDeletedList() {
        return glBankTransactionDetailsDeletedList;
    }

    /**
     * @param glBankTransactionDetailsDeletedList the
     * glBankTransactionDetailsDeletedList to set
     */
    public void setGlBankTransactionDetailsDeletedList(List<GlBankTransactionDetail> glBankTransactionDetailsDeletedList) {
        this.glBankTransactionDetailsDeletedList = glBankTransactionDetailsDeletedList;
    }

    /**
     * @return the printAndSave
     */
    public boolean isPrintAndSave() {
        return printAndSave;
    }

    /**
     * @param printAndSave the printAndSave to set
     */
    public void setPrintAndSave(boolean printAndSave) {
        this.printAndSave = printAndSave;
    }

    /**
     * @return the remark1
     */
    public String getRemark1() {
        return remark1;
    }

    /**
     * @param remark1 the remark1 to set
     */
    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    /**
     * @return the remark2
     */
    public String getRemark2() {
        return remark2;
    }

    /**
     * @param remark2 the remark2 to set
     */
    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    /**
     * @return the session
     */
    public HttpSession getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(HttpSession session) {
        this.session = session;
    }

    /**
     * @return the installment
     */
    public Boolean getInstallment() {
        return installment;
    }

    /**
     * @param installment the installment to set
     */
    public void setInstallment(Boolean installment) {
        this.installment = installment;
    }

    /**
     * @return the errorMessageBuilder
     */
    public StringBuilder getErrorMessageBuilder() {
        return errorMessageBuilder;
    }

    /**
     * @param errorMessageBuilder the errorMessageBuilder to set
     */
    public void setErrorMessageBuilder(StringBuilder errorMessageBuilder) {
        this.errorMessageBuilder = errorMessageBuilder;
    }

    /**
     * @return the errorNoNeedMessageBuilder
     */
    public StringBuilder getErrorNoNeedMessageBuilder() {
        return errorNoNeedMessageBuilder;
    }

    /**
     * @param errorNoNeedMessageBuilder the errorNoNeedMessageBuilder to set
     */
    public void setErrorNoNeedMessageBuilder(StringBuilder errorNoNeedMessageBuilder) {
        this.errorNoNeedMessageBuilder = errorNoNeedMessageBuilder;
    }

    /**
     * @return the sendSms
     */
    public Boolean getSendSms() {
        return sendSms;
    }

    /**
     * @param sendSms the sendSms to set
     */
    public void setSendSms(Boolean sendSms) {
        this.sendSms = sendSms;
    }

    /**
     * @return the instTransactionViewConverter
     */
    public InstTransactionViewConverter getInstTransactionViewConverter() {
        return instTransactionViewConverter;
    }

    /**
     * @param instTransactionViewConverter the instTransactionViewConverter to
     * set
     */
    public void setInstTransactionViewConverter(InstTransactionViewConverter instTransactionViewConverter) {
        this.instTransactionViewConverter = instTransactionViewConverter;
    }

    /**
     * @return the instTransactionViewList
     */
    public List<InstTransactionView> getInstTransactionViewList() {
        return instTransactionViewList;
    }

    /**
     * @param instTransactionViewList the instTransactionViewList to set
     */
    public void setInstTransactionViewList(List<InstTransactionView> instTransactionViewList) {
        this.instTransactionViewList = instTransactionViewList;
    }

    /**
     * @return the accountsSystemSettings
     */
    public AccountsSystemSettings getAccountsSystemSettings() {
        return accountsSystemSettings;
    }

    /**
     * @param accountsSystemSettings the accountsSystemSettings to set
     */
    public void setAccountsSystemSettings(AccountsSystemSettings accountsSystemSettings) {
        this.accountsSystemSettings = accountsSystemSettings;
    }

    /**
     * @return the supplierList
     */
    public List<InvOrganizationSite> getSupplierList() {
        return supplierList;
    }

    /**
     * @param supplierList the supplierList to set
     */
    public void setSupplierList(List<InvOrganizationSite> supplierList) {
        this.supplierList = supplierList;
    }

    /**
     * @return the customerList
     */
    public List<InvOrganizationSite> getCustomerList() {
        return customerList;
    }

    /**
     * @param customerList the customerList to set
     */
    public void setCustomerList(List<InvOrganizationSite> customerList) {
        this.customerList = customerList;
    }

    /**
     * @return the allowAccount
     */
    public Boolean getAllowAccount() {
        return allowAccount;
    }

    /**
     * @param allowAccount the allowAccount to set
     */
    public void setAllowAccount(Boolean allowAccount) {
        this.allowAccount = allowAccount;
    }

    /**
     * @return the glItemConverter
     */
    public SymbolConverter getGlItemConverter() {
        return glItemConverter;
    }

    /**
     * @param glItemConverter the glItemConverter to set
     */
    public void setGlItemConverter(SymbolConverter glItemConverter) {
        this.glItemConverter = glItemConverter;
    }

    /**
     * @return the glItemsList
     */
    public List<Symbol> getGlItemsList() {
        return glItemsList;
    }

    /**
     * @param glItemsList the glItemsList to set
     */
    public void setGlItemsList(List<Symbol> glItemsList) {
        this.glItemsList = glItemsList;
    }

    /**
     * @return the accountGLItems
     */
    public GlAccount getAccountGLItems() {
        return accountGLItems;
    }

    /**
     * @param accountGLItems the accountGLItems to set
     */
    public void setAccountGLItems(GlAccount accountGLItems) {
        this.accountGLItems = accountGLItems;
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
     * @return the glBankTransactionListPre
     */
    public List<GlBankTransaction> getGlBankTransactionListPre() {
        return glBankTransactionListPre;
    }

    /**
     * @param glBankTransactionListPre the glBankTransactionListPre to set
     */
    public void setGlBankTransactionListPre(List<GlBankTransaction> glBankTransactionListPre) {
        this.glBankTransactionListPre = glBankTransactionListPre;
    }

    /**
     * @return the glBankTransactionSelectionPre
     */
    public GlBankTransaction getGlBankTransactionSelectionPre() {
        return glBankTransactionSelectionPre;
    }

    /**
     * @param glBankTransactionSelectionPre the glBankTransactionSelectionPre to
     * set
     */
    public void setGlBankTransactionSelectionPre(GlBankTransaction glBankTransactionSelectionPre) {
        this.glBankTransactionSelectionPre = glBankTransactionSelectionPre;
    }

    /**
     * @return the glBankTransactionInvoiceViewList
     */
    public List<GlBankTransactionInvoiceView> getGlBankTransactionInvoiceViewList() {
     if(glBankTransactionInvoiceViewList ==null || glBankTransactionInvoiceViewList.isEmpty()){
     glBankTransactionInvoiceViewList =new ArrayList<>();
     }  
        
        return glBankTransactionInvoiceViewList;
    }

    /**
     * @param glBankTransactionInvoiceViewList the glBankTransactionInvoiceViewList to set
     */
    public void setGlBankTransactionInvoiceViewList(List<GlBankTransactionInvoiceView> glBankTransactionInvoiceViewList) {
        this.glBankTransactionInvoiceViewList = glBankTransactionInvoiceViewList;
    }

}

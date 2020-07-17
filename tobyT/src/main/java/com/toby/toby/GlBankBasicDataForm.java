/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.toby;

import com.toby.businessservice.CurrencyOperationService;
import com.toby.businessservice.TobyUserYearService;
import com.toby.converter.GlYearConverter;
import com.toby.entity.Currency;
import com.toby.entity.CurrencyOperation;
import com.toby.entity.GlYear;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author m_els
 */
public abstract class GlBankBasicDataForm extends BaseFormBean {

    private List<GlYear> glYearList;

    private GlYear glYearSelection;

    private GlYearConverter glYearConverter;

    @EJB
    TobyUserYearService tobyUserYearService;

    @EJB
    CurrencyOperationService currencyOperationService;

    public BigDecimal updateRate(Currency currency, Date date) {

        if (currency != null) {
            if (date != null) {
                CurrencyOperation currencyOperation = currencyOperationService.getRatesByDates(currency.getId(), date, getUserData().getCompany().getId());

                return currencyOperation == null ? BigDecimal.ONE : currencyOperation.getRate();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, getUserData().getUserDDs().get("ERROR"), getUserData().getUserDDs().get("MUST_SELECT_DATE")));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, getUserData().getUserDDs().get("ERROR"), getUserData().getUserDDs().get("NO_CURRENCY_ERROR")));
        }
        return null;
    }

    public abstract void checkDate(Boolean dateType);

    public abstract void resetDateFrom();

    public abstract void resetDateTo();

    public void resetFinancailYearDate() {
        resetDateFrom();
        resetDateTo();
    }

    public void pushGlYearSelection() {
        resetFinancailYearDate();
        getUserData().setGlYear(glYearSelection);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.getSessionMap().put("userLogInData", getUserData());
    }

    public void redirectFinancailYearPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        try {
            fc.getExternalContext().redirect("../base/financailyear.xhtml");
        } catch (IOException ex) {
//            Logger.getLogger(DeletedAndUpdatedGeneralJournalMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean checkFinancailYear(Date date) {
        if ((date.before(getGlYearSelection().getDateFrom()) || date.after(getGlYearSelection().getDateTo()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "يجب اختيار تاريخ يقع بين الفترة الماليه"));
            return true;
        }
        return false;
    }

    public abstract void calculateLocalValue();

    public <T> void fillReport(HashMap hashMap, String reportPath, List<T> listBean, String exportType) throws JRException, IOException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listBean);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, hashMap, beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        if ("pdf".equals(exportType)) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        } else if ("excel".equals(exportType)) {

        } else if ("html".equals(exportType)) {

        }
        httpServletResponse.getOutputStream().flush();
        httpServletResponse.getOutputStream().close();
    }

    /**
     * @return the glYearList
     */
    public List<GlYear> getGlYearList() {
        if (glYearList == null || glYearList.isEmpty()) {
            glYearList = tobyUserYearService.findYearByUserId(getUserData().getUser().getId(), getUserData().getUserBranch());
            glYearConverter = new GlYearConverter(glYearList);
        }
        return glYearList;
    }

    /**
     * @param glYearList the glYearList to set
     */
    public void setGlYearList(List<GlYear> glYearList) {
        this.glYearList = glYearList;
    }

    /**
     * @return the glYearSelection
     */
    public GlYear getGlYearSelection() {
        if (glYearSelection == null) {
            if (getUserData().getGlYear() != null) {
                glYearSelection = getUserData().getGlYear();
            } else {
                if (getGlYearList() != null && !getGlYearList().isEmpty()) {
                    glYearSelection = getGlYearList().get(0);
                }
            }
        }
        return glYearSelection;
    }

    /**
     * @param glYearSelection the glYearSelection to set
     */
    public void setGlYearSelection(GlYear glYearSelection) {
        this.glYearSelection = glYearSelection;
    }

    /**
     * @return the glYearConverter
     */
    public GlYearConverter getGlYearConverter() {
        return glYearConverter;
    }

    /**
     * @param glYearConverter the glYearConverter to set
     */
    public void setGlYearConverter(GlYearConverter glYearConverter) {
        this.glYearConverter = glYearConverter;
    }
}

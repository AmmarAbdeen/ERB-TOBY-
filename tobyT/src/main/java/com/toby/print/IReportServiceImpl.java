/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.print;

import com.toby.businessservice.reports.entityBean.completionRateReportEntity;
import com.toby.entiy.InvAddingOrderDetailsEntity;
import com.toby.entiy.InvAddingOrderEntity;
import com.toby.entiy.InvPurchaseOrderDetailEntity;
import com.toby.entiy.InvPurchaseOrderEntity;
import com.toby.report.entity.RscExtractRegisteringBean;
import com.toby.report.entity.RscPriceTenderBean;
import com.toby.report.entity.RscQuotationPriceTenderBean;
import com.toby.report.entity.RscRestrictionQuantitiesBean;
import com.toby.report.entity.RscTenderBean;
import com.toby.toby.UserData;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author hq004
 */
public class IReportServiceImpl implements IReportService {

    public <T> void fillReport(HashMap hashMap, String reportPath, List<T> listBean, String exportType) throws JRException, IOException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listBean);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, hashMap, beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        if ("pdf".equals(exportType)) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            servletOutputStream.flush();
            servletOutputStream.close();
            httpServletResponse.getOutputStream().flush();
            httpServletResponse.getOutputStream().close();
        } else if ("excel".equals(exportType)) {

        } else if ("html".equals(exportType)) {

        }
    }

    public boolean isFileExist(String filePath, UserData userData) {
        File file = new File(userData.getImageReportPath());
        return file.exists();
    }

    public HashMap prepareReportGeneral(HashMap hashMap, UserData userData) {

        hashMap.put("PrintedBy", userData.getUser().getName());
        hashMap.put("Branch", userData.getUserBranch().getNameAr());
        hashMap.put("companyName", userData.getCompany().getName());
        if (isFileExist(userData.getImageReportPath(), userData)) {
            hashMap.put("companyImage", userData.getImageReportPath());
        } else {
            hashMap.put("companyImage", null);
        }

        return hashMap;

    }

    public HashMap prepareReportInvPurchaseOrder(InvPurchaseOrderEntity invPurchaseOrderEntity, UserData userData) {

        HashMap hashMap = new HashMap();

        prepareReportGeneral(hashMap, userData);

        Map<String, String> userDDs = userData.getUserDDs();

        hashMap.put("header2", " طلب شراء");
        hashMap.put("serialText", "رقم أمر الشراء");
        hashMap.put("serialvalue", invPurchaseOrderEntity.getSerial());
        hashMap.put("supplierText", "اسم المورد");
        hashMap.put("supplierValue", invPurchaseOrderEntity.getSupplierId() == null ? null : invPurchaseOrderEntity.getSupplierId().getName());
        hashMap.put("BillSDateText", "التاريخ");
        hashMap.put("BillSDatevalue", invPurchaseOrderEntity.getDate());
        hashMap.put("supplierbillText", "المعدل");
        hashMap.put("supplierBillValue", invPurchaseOrderEntity.getRate());
        hashMap.put("pOrderNumText", "العملة");
        hashMap.put("pOrderNumValue", invPurchaseOrderEntity.getCurrencyId() == null ? null : invPurchaseOrderEntity.getCurrencyId().getName());

        hashMap.put("delegatorText", "المندوب");
        hashMap.put("delegatorValue", invPurchaseOrderEntity.getDelegateId() == null ? null : invPurchaseOrderEntity.getDelegateId().getName());
        hashMap.put("remarkText", "الملاحظات");
        hashMap.put("remarkValue", invPurchaseOrderEntity.getRemarks());

        hashMap.put("itemText", "الصنف");
        hashMap.put("unitText", "رقم الوحدة");
        hashMap.put("quantityText", "الكمية");
        hashMap.put("priceText", "السعر");
        hashMap.put("discountText", "الخصم");
        hashMap.put("totalText", "الاجمالى");
        String x = invPurchaseOrderEntity.getDelegateId() == null ? null : invPurchaseOrderEntity.getDelegateId().getName();
        hashMap.put("test", x + " مندوب مشتريات الدقي tttttttest");

        hashMap.put("reportDate", new Date());

        return hashMap;
    }

    public HashMap prepareReportInvAddingOrder(InvAddingOrderEntity invAddingOrderEntity, UserData userData, Boolean isPermission) {

        HashMap hashMap = new HashMap();

        prepareReportGeneral(hashMap, userData);

        Map<String, String> userDDs = userData.getUserDDs();

        if (isPermission) {
            hashMap.put("header2", "اذن صرف بضاعة");
            hashMap.put("supplierText", "اسم العميل");
            hashMap.put("supplierValue", invAddingOrderEntity.getSupplierId().getName());
            hashMap.put("supplierbillText", "فاتورة العميل");
            hashMap.put("supplierBillValue", invAddingOrderEntity.getSupplierInvoice());
            hashMap.put("BillSDateText", "تاريخ فاتورة العميل");
            hashMap.put("BillSDatevalue", invAddingOrderEntity.getSupplierDate());

        } else {
            hashMap.put("header2", "اذن استلام بضاعة");
            hashMap.put("supplierText", "اسم المورد");
            hashMap.put("supplierValue", invAddingOrderEntity.getSupplierId().getName());
            hashMap.put("supplierbillText", "فاتورة المورد");
            hashMap.put("supplierBillValue", invAddingOrderEntity.getSupplierInvoice());
            hashMap.put("pOrderNumText", "رقم أمر شراء");
            hashMap.put("pOrderNumValue", invAddingOrderEntity.getPurchaseOrderNLoaded() == null ? null : invAddingOrderEntity.getPurchaseOrderNLoaded().getSerial());
            hashMap.put("BillSDateText", "تاريخ فاتورة المورد");
            hashMap.put("BillSDatevalue", invAddingOrderEntity.getPurchaseOrderNLoaded() == null ? null : invAddingOrderEntity.getPurchaseOrderNLoaded().getDate());
            hashMap.put("delegatorText", "المندوب");
            hashMap.put("delegatorValue", invAddingOrderEntity.getDelegatorId() == null ? null : invAddingOrderEntity.getDelegatorId().getName());
        }

        hashMap.put("serialText", "مسلسل");
        hashMap.put("serialvalue", invAddingOrderEntity.getSerial());

        hashMap.put("dateText", "تاريخ");
        hashMap.put("datevalue", invAddingOrderEntity.getDate());

        hashMap.put("depositoryText", "المخزن");
        hashMap.put("depositoryValue", invAddingOrderEntity.getInvInventory() == null ? null : invAddingOrderEntity.getInvInventory().getName());

        hashMap.put("remarkText", "الملاحظات");
        hashMap.put("remarkValue", invAddingOrderEntity.getRemark());

        hashMap.put("itemText", "الصنف");
        hashMap.put("unitText", "رقم الوحدة");
        hashMap.put("quantityText", "الكمية");

        hashMap.put("reportDate", new Date());

        return hashMap;
    }

    public void printReportInvPurchaseOrder(InvPurchaseOrderEntity invPurchaseOrderEntity, List<InvPurchaseOrderDetailEntity> invPurchaseOrderDetailEntityList, UserData userData) {

        try {
            fillReport(prepareReportInvPurchaseOrder(invPurchaseOrderEntity, userData), userData.getReportPath() + "PurchaseOrderReport.jasper", invPurchaseOrderDetailEntityList, "pdf");
        } catch (JRException ex) {
            Logger.getLogger(InvPurchaseOrderEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InvPurchaseOrderEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void printReportInvAddingOrder(InvAddingOrderEntity invAddingOrderEntity, List<InvAddingOrderDetailsEntity> invAddingOrderDetailsEntityList, UserData userData) {
        try {
            fillReport(prepareReportInvAddingOrder(invAddingOrderEntity, userData, false), userData.getReportPath() + "AddingReport.jasper", invAddingOrderDetailsEntityList, "pdf");
        } catch (JRException ex) {
            Logger.getLogger(InvAddingOrderEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InvAddingOrderEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void printReportInvPermissionOrder(InvAddingOrderEntity invAddingOrderEntity, List<InvAddingOrderDetailsEntity> invAddingOrderDetailsEntityList, UserData userData) {
        try {
            fillReport(prepareReportInvAddingOrder(invAddingOrderEntity, userData, true), userData.getReportPath() + "PermissionReport.jasper", invAddingOrderDetailsEntityList, "pdf");
        } catch (JRException ex) {
            Logger.getLogger(InvAddingOrderEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InvAddingOrderEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

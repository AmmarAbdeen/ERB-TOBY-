package com.toby.invoiceProductionReport;

import com.toby.businessservice.InvPurchaseInvoiceService;
import com.toby.businessservice.InvoiceProductionStagesReportService;
import com.toby.businessservice.ProProductionStagesService;
import com.toby.converter.InvPurchaseInvoiceDTOConverter;
import com.toby.converter.ProProductionStagesDTOConverter;
import com.toby.dto.InvPurchaseInvoiceDTO1;
import com.toby.dto.PrintProductionDTO;
import com.toby.dto.ProProductionStagesDTO;
import com.toby.toby.AutoComplete;
import com.toby.toby.BaseListBean;
import com.toby.toby.UserData;
import com.toby.views.InvoiceProductionStagesReport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import net.sf.jasperreports.engine.JRException;


@Named(value = "invoiceProductionStagesReportMB")
@ViewScoped
public class InvoiceProductionStagesReportMB extends BaseListBean{
    
    private List<ProProductionStagesDTO> proProductionStagesDTOList;
    private List<InvPurchaseInvoiceDTO1> invPurchaseInvoiceDTOList;
    private List<InvoiceProductionStagesReport> invoiceProductionStagesReports;
    private PrintProductionDTO printProductionDTO;
    
    
    private ProProductionStagesDTOConverter proProductionStagesDTOConverter;
    private InvPurchaseInvoiceDTOConverter invPurchaseInvoiceDTOConverter;
    
    
    @EJB
    private ProProductionStagesService proProductionStagesService;
    @EJB
    private InvPurchaseInvoiceService invPurchaseInvoiceService;
    @EJB
    private InvoiceProductionStagesReportService invoiceProductionStagesReportService;



    public InvoiceProductionStagesReportMB() {
    }

    @Override
    @PostConstruct
    public void init() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        setUserData((UserData) context.getSessionMap().get("userLogInData"));
        proProductionStagesDTOList = new ArrayList<>();
        getInvPurchaseInvoiceDTOList();
       

    }
     public  List<InvPurchaseInvoiceDTO1> completeInvpurchaseInvoice(String query){
     
     return AutoComplete.completeInvpurchaseInvoice(query, invPurchaseInvoiceDTOList, invPurchaseInvoiceDTOConverter);
     }
    public void search() {
          if((printProductionDTO.getFromInvoice()!=null&&printProductionDTO.getToInvoice()!=null)
                ||(printProductionDTO.getFromDate()!=null&&printProductionDTO.getToDate()!=null)){
        invoiceProductionStagesReports=invoiceProductionStagesReportService.searchReport(printProductionDTO, getUserData().getUser());}
          else{
             printProductionDTO.setMsg("ادخل من فاتوره الي فاتوره او من تاريخ الي تاريخ " );
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", printProductionDTO.getMsg()));
            }
    }
    
    public void exportPDF(ActionEvent actionEvent) throws JRException, IOException {
        if (invoiceProductionStagesReports != null && !invoiceProductionStagesReports.isEmpty()) {
            fillReport(prepareReport(), getUserData().getReportPath() + "invoiceProductionStageReport.jasper", invoiceProductionStagesReports, "pdf");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "لا توجد نتائج للطباعة"));
        }
    }
    
    public HashMap prepareReport() {
        Map<String, String> userDDs = getUserData().getUserDDs();
        HashMap hashMap = new HashMap();
        
        hashMap.put("fromInvoice", printProductionDTO.getFromInvoice().getId());
        hashMap.put("toInvoice", printProductionDTO.getToInvoice().getId());
        hashMap.put("toDate", printProductionDTO.getToDate());
        hashMap.put("fromDate", printProductionDTO.getFromDate());
        hashMap.put("fromInvoiceText", "من فاتورة");
        hashMap.put("toInvoiceText", "إلى فاتورة");
        hashMap.put("toDateText", "الي تاريخ");
        hashMap.put("fromDateText", "من تاريخ");
        
        hashMap.put("invoiceText", "الفاتورة");
        hashMap.put("dateText", "التاريخ");
        hashMap.put("clientText", "العميل");
        hashMap.put("clientNameText","اسم العميل");
        hashMap.put("quantityText", "الكمية");
        hashMap.put("stageText", "المرحلة");
        hashMap.put("empNameText", "الموظف");
        hashMap.put("branchName", getUserData().getUserBranch().getNameAr());
        hashMap.put("PrintedBy", getUserData().getUser().getName());
        if (isFileExist(getUserData().getImageReportPath())) {
            hashMap.put("companyImage", getUserData().getImageReportPath());
        } else {
            hashMap.put("companyImage", null);
        }
        return hashMap;
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

    
    public List<ProProductionStagesDTO> getProProductionStagesDTOList() {
        if (proProductionStagesDTOList == null | proProductionStagesDTOList.isEmpty()) {
            proProductionStagesDTOList = proProductionStagesService.getAllProproductionStages(getUserData().getUser());
        }
        return proProductionStagesDTOList;
    }

    
    public void setProProductionStagesDTOList(List<ProProductionStagesDTO> proProductionStagesDTOList) {
        this.proProductionStagesDTOList = proProductionStagesDTOList;
    }

    
    public List<InvPurchaseInvoiceDTO1> getInvPurchaseInvoiceDTOList() {
        if (invPurchaseInvoiceDTOList == null || invPurchaseInvoiceDTOList.isEmpty()) {
            invPurchaseInvoiceDTOList = invPurchaseInvoiceService.findInvPurchaseInvoiceDTOList(getUserData().getUser());
            setInvPurchaseInvoiceDTOConverter(new InvPurchaseInvoiceDTOConverter(invPurchaseInvoiceDTOList));
        }
        return invPurchaseInvoiceDTOList;
    }

    
    public void setInvPurchaseInvoiceDTOList(List<InvPurchaseInvoiceDTO1> invPurchaseInvoiceDTOList) {
        this.invPurchaseInvoiceDTOList = invPurchaseInvoiceDTOList;
    }

    
    public PrintProductionDTO getPrintProductionDTO() {
        if (printProductionDTO == null) {
            printProductionDTO = new PrintProductionDTO();
        }
        return printProductionDTO;

    }

    
    public void setPrintProductionDTO(PrintProductionDTO printProductionDTO) {
        this.printProductionDTO = printProductionDTO;
    }

    
    public ProProductionStagesDTOConverter getProProductionStagesDTOConverter() {
        return proProductionStagesDTOConverter;
    }

    
    public void setProProductionStagesDTOConverter(ProProductionStagesDTOConverter proProductionStagesDTOConverter) {
        this.proProductionStagesDTOConverter = proProductionStagesDTOConverter;
    }

    
    public InvPurchaseInvoiceDTOConverter getInvPurchaseInvoiceDTOConverter() {
        return invPurchaseInvoiceDTOConverter;
    }

    
    public void setInvPurchaseInvoiceDTOConverter(InvPurchaseInvoiceDTOConverter invPurchaseInvoiceDTOConverter) {
        this.invPurchaseInvoiceDTOConverter = invPurchaseInvoiceDTOConverter;
    }

    /**
     * @return the invoiceProductionStagesReports
     */
    public List<InvoiceProductionStagesReport> getInvoiceProductionStagesReports() {
        return invoiceProductionStagesReports;
    }

    /**
     * @param invoiceProductionStagesReports the invoiceProductionStagesReports to set
     */
    public void setInvoiceProductionStagesReports(List<InvoiceProductionStagesReport> invoiceProductionStagesReports) {
        this.invoiceProductionStagesReports = invoiceProductionStagesReports;
    }
    
}

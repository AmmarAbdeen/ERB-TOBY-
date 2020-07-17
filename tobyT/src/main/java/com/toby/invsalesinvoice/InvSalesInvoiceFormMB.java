/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.invsalesinvoice;

import com.toby.businessservice.*;
import com.toby.converter.*;
import com.toby.dto.*;
import com.toby.entity.TobyUser;
import com.toby.entity.UnitsItems;
import com.toby.toby.BaseFormBean;
import com.toby.toby.UserData;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author WIN7
 */
@Named(value = "invsalesinvoiceformMB")
@ViewScoped

public class InvSalesInvoiceFormMB extends BaseFormBean {

    private InvPurchaseInvoiceDTO1 invPurchaseInvoiceDTO;
    private InvPurchaseInvoiceDetailDTO invPurchaseInvoiceDetailDTOSelected;
    private InvPurchaseInvoiceDetailDTO invPurchaseInvoiceDetailDTO1;
    private InvOrganizationSiteDTO invOrganizationSiteDTO;
    private InvItemDTO invItemDTO;
    private InvInventoryTransactionDTO invInventoryTransactionDTO;

    private Map<Integer, BigDecimal> quantityMap;
    private Map<Integer, List<UnitsItems>> unitsItemsMap;
    private Boolean costRendered;

    private List<InvItemDTO> invItemDTOCompletedList;
    private List<InvInventoryDTO> inventoryDTOList ;
    private List<CostCenterDTO> costCenterDTOList;
    private List<InventoryDelegatorDTO> inventoryDelegatorList;
    private List<InvPurchaseInvoiceDTO1> invPurchaseInvoiceDTOList;
    private List<InvInventoryDTO> invInventoryList;
    private List<InvInventoryDTO> gallaryList;
    private List<GlAdminUnitDTO> glAdminUnitList;
    private List<GlBankDTO> glBankList;
    private List<InvOrganizationSiteDTO> customerList;
    private List<InvItemDTO> invItemList;
    private List<SymbolDTO> symbolDTOList;

    private InvOrganizationSiteDTOConverter invOrganizationSiteDTOConverter;
    private InvPurchaseInvoiceDTOConverter invPurchaseInvoiceDTOConverter;
    private InvDelegatorDTOConvertor invDelegatorDTOConvertor;
    private GlAdminUnitDTOConverter glAdminUnitDTOConverter;
    private CostCenterDTOConverter costCenterDTOConverter;
    private GlBankDTOConverter glBankDTOConverter;
    private InvInventoryDTOConverter invInventoryDTOConverter;
    private InvInventoryDTOConverter invInventoryDTOConverter1;
    private ItemDTOConverter itemDTOConverter;
    private ItemDTOConverter itemDTOConverter1;
    private InvPurchaseInvoiceDetailConverter invPurchaseInvoiceDetailConverter;
    private InvPurchaseInvoiceDetailDTOConverter invPurchaseInvoiceDetailDTOConverter;
    private UnitsItemsConverter unitsItemsConverter;

    @EJB
    private InvPurchaseInvoiceService invPurchaseInvoiceService;
    @EJB
    private OrganizationSiteService organizationSiteService;
    @EJB
    private InventoryDelegatorService inventoryDelegatorService;
    @EJB
    private GlAdminUnitServiceDTO glAdminUnitService;
    @EJB
    private CostCenterService costCenterService;
    @EJB
    private TobyUserInventoryService tobyUserInventoryService;
    @EJB
    private TobyUserBankService tobyUserBankService;
    @EJB
    private InvPurchaseInvoiceDetailService invPurchaseInvoiceDetailService;

    @EJB
    private InvItemService invItemService;
    @EJB
    private QuantityItemsStoreAddExitService quantityItemsStoreAddExitService;
    @EJB
    private UnitsItemsService unitsItemsService;

    @PostConstruct
    public void init() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        setScreenMode((String) context.getSessionMap().get("ScreenMode"));
        setUserData((UserData) context.getSessionMap().get("userLogInData"));
        getInvInventoryList();
        getCustomerList();
        getInventoryDelegatorList();
        getCostCenterDTOList();

        getGlBankList();
        getGallaryList();
//        getGlAdminUnitList();
        getInvItemList();
        if (getScreenMode() != null && getScreenMode().equalsIgnoreCase("Edit")) {
            try {
                Integer invPurchaseInvoiceId = (Integer) context.getSessionMap().get("invPurchaseInvoiceId");
                if (invPurchaseInvoiceDTO == null) {
                    invPurchaseInvoiceDetailDTO1 = new InvPurchaseInvoiceDetailDTO();
                    changeInventory();
                    for (InvPurchaseInvoiceDetailDTO invPurchaseInvoiceDetailDTO : invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailDTOList()) {

                        if (getInventoryDTOList().isEmpty() || !inventoryDTOList.contains(invPurchaseInvoiceDetailDTO.getInvInventoryDTO())) {
                            getInventoryDTOList().add(invPurchaseInvoiceDetailDTO.getInvInventoryDTO());
                        }
                        if (quantityMap != null) {
                            invPurchaseInvoiceDetailDTO.setAvailableQuantity(quantityMap.get(invPurchaseInvoiceDetailDTO.getItemId().getId()));
                            invPurchaseInvoiceDetailDTO.setInvInventoryDTO(invPurchaseInvoiceDetailDTO.getInvInventoryDTO());
                            invPurchaseInvoiceDetailDTO.setTotal(invPurchaseInvoiceDetailDTO.getQuantity().multiply(invPurchaseInvoiceDetailDTO.getCost()));
                            invPurchaseInvoiceDetailDTO.setTotalQuantityRow(invPurchaseInvoiceDetailDTO.getNumber().multiply(invPurchaseInvoiceDetailDTO.getQuantity()));
                            invPurchaseInvoiceDetailDTO.setIndex(getIndex());
                            // method to get unitItems object //
                            invPurchaseInvoiceDetailDTO.setUnitsItem(unitsItemsService.getScrewingAndPrice(invPurchaseInvoiceDetailDTO.getItemId().getId(), invPurchaseInvoiceDetailDTO.getUnitsItemsSelected()));
                            invPurchaseInvoiceDetailDTOSelected = invPurchaseInvoiceDetailDTO;
                        }
                    }
                    validateItem();
//                    System.out.println("");
//                    context.getSessionMap().remove("ScreenMode");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void exportPDF(ActionEvent actionEvent) throws JRException, IOException {
        if (invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailDTOList() != null && !invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailDTOList().isEmpty()) {
            for(InvPurchaseInvoiceDetailDTO detailDTO : invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailDTOList()){
                detailDTO.setItemName(detailDTO.getItemId().getName());
                detailDTO.setItem_id(detailDTO.getItemId().getId());
            }
            fillReport(prepareReport(), getUserData().getReportPath() + "printInvoice.jasper", invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailDTOList(), "pdf");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getUserData().getUserDDs().get("ERROR"), "لا توجد نتائج للطباعة"));
        }
    }

    public HashMap prepareReport() {
        Map<String, String> userDDs = getUserData().getUserDDs();
        HashMap hashMap = new HashMap();

        hashMap.put("invoiceId", invPurchaseInvoiceDTO.getId());
        hashMap.put("invoiceSerial", invPurchaseInvoiceDTO.getSerial());
        hashMap.put("date", invPurchaseInvoiceDTO.getDate());
        hashMap.put("custmerName", (invPurchaseInvoiceDTO.getOrganizationSiteIdDTO().getName() != null ? invPurchaseInvoiceDTO.getOrganizationSiteIdDTO().getName() : ""));
        hashMap.put("custmerCode", invPurchaseInvoiceDTO.getOrganizationSiteIdDTO().getCode());
        hashMap.put("custmerMobile", invPurchaseInvoiceDTO.getOrganizationSiteIdDTO().getMobile());
        hashMap.put("sumTotal", invPurchaseInvoiceDTO.getSumTotal());

        hashMap.put("invoiceIdText", "فاتورة");
        hashMap.put("dateText", "التاريخ");
        hashMap.put("custmerNameText", "العميل");
        hashMap.put("custmerMobileText", "رقم العميل");
        hashMap.put("sumTotalText", "الإجمالى");

        hashMap.put("itemIdText", "رقم الصنف");
        hashMap.put("itemNameText", "اسم الصنف");
        hashMap.put("numberText", "العدد");
        hashMap.put("quantityText", "الكمية");
        hashMap.put("costText", "السعر");
        hashMap.put("discountText", "الخصم");

        hashMap.put("branchName", getUserData().getUserBranch().getNameAr());
        hashMap.put("PrintedBy", getUserData().getUser().getName());
        if (isFileExist(getUserData().getImageReportPath())) {
            hashMap.put("companyImage", getUserData().getImageReportPath());
        } else {
            hashMap.put("companyImage", null);
        }
        return hashMap;
    }

    public void validatePrice() {

        BigDecimal discount = ((invPurchaseInvoiceDetailDTOSelected.getCost() != null ? invPurchaseInvoiceDetailDTOSelected.getCost() : BigDecimal.ZERO).divide(new BigDecimal(100))).multiply(invPurchaseInvoiceDetailDTOSelected.getDiscount() != null ? invPurchaseInvoiceDetailDTOSelected.getDiscount() : BigDecimal.ZERO);
        System.out.println("");
        if (invPurchaseInvoiceDetailDTOSelected.getDiscount() != null && invPurchaseInvoiceDetailDTOSelected.getCost() != null) {
            invPurchaseInvoiceDetailDTOSelected.setCost(discount);
            if (!(invPurchaseInvoiceDetailDTOSelected.getCost().compareTo(discount) == 0)) {

            }

        } else {
            if (invPurchaseInvoiceDTO.getDetectPrice() != null && invPurchaseInvoiceDTO.getDetectPrice() == 1) {
                if (!(invPurchaseInvoiceDetailDTOSelected != null && invPurchaseInvoiceDetailDTOSelected.getItemId() != null && invPurchaseInvoiceDetailDTOSelected.getCost() != null && invPurchaseInvoiceDetailDTOSelected.getCost().compareTo(invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpricemen()) >= 0)) {
                    invPurchaseInvoiceDTO.setMsg(" )أقل سعر ممكن لهذا الصنف   " + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )   = " + invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpricemen());
                    invPurchaseInvoiceDetailDTOSelected.setCost(invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpricemen());
                    discount();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));

                }
            } else {
                invPurchaseInvoiceDTO.setMsg(" )   أقل سعر ممكن لهذا الصنف" + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )  = " + invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpriceyoung());
                if (!invPurchaseInvoiceDTO.getMsg().isEmpty()) {
                    invPurchaseInvoiceDetailDTOSelected.setCost(invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpriceyoung());
                    discount();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
                }
            }
        }
    }

    public void changeInventory() {
        
        if (invPurchaseInvoiceDTO != null && invPurchaseInvoiceDTO.getGallary() != null && invPurchaseInvoiceDTO.getGallary().getId() != null) {
           
        }
//        if (invPurchaseInvoiceDTO.getInvInventoryDTO() != null ) {
//            invPurchaseInvoiceDetailDTOSelected.setInvInventoryDTO(invPurchaseInvoiceDTO.getInvInventoryDTO());
//        }
    }

    public void reset() {
        invPurchaseInvoiceDTO = new InvPurchaseInvoiceDTO1();
    }

    public void addNewCustomer() {

        if (invOrganizationSiteDTO != null && invOrganizationSiteDTO.getName() != null) {
            invOrganizationSiteDTO.setType(0);
            invOrganizationSiteDTO.setActive(1);
            organizationSiteService.addOrganizationSiteDTO(invOrganizationSiteDTO, getUserData().getUser());
            getCustomerList().add(0, invOrganizationSiteDTO);
            invPurchaseInvoiceDTO.setOrganizationSiteIdDTO(invOrganizationSiteDTO);
        }
        System.out.println("");
    }

    public void sumDateToDueDate() {
        
    }

    public void subtractDatefromDueDate() {
        LocalDate ld = invPurchaseInvoiceDTO.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ld1 = invPurchaseInvoiceDTO.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();     //2
        Duration duration = Duration.between(ld.atStartOfDay(), ld1.atStartOfDay());
        System.out.println("duration.toDays(); " + duration.toDays());
        if ((int) (long) duration.toDays() < 0) {
            invPurchaseInvoiceDTO.setDueperiod((Integer) 0);
            invPurchaseInvoiceDTO.setMsg(" تاريخ الاستحقاق لابد ان يكون أكبر من تاريخ الفاتورة");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
            sumDateToDueDate();
        } else {

            invPurchaseInvoiceDTO.setDueperiod((int) (long) duration.toDays());
        }
    }

    public void newInvoice() {
        invPurchaseInvoiceDTO = new InvPurchaseInvoiceDTO1();
        invPurchaseInvoiceDTO.setInvPurchaseInvoiceDetailDTOList(new ArrayList<>());
        invPurchaseInvoiceDetailDTOSelected = new InvPurchaseInvoiceDetailDTO();
    }

    public String backToList() {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            exit("../invsalesinvoice/invsalesinvoicelist.xhtml");
            return "";

        } catch (Exception e) {
            saveError(e, "invsalesinvoiceformMB", "backToList");
            return null;
        }

    }

    public void updateserial() {
        if (invPurchaseInvoiceDTO != null) {
            invPurchaseInvoiceDTO.setSerial(invPurchaseInvoiceDTO.getSerial());
        }
    }

    public List<InvOrganizationSiteDTO> completeCustomer(String query) {
        try {
            List<InvOrganizationSiteDTO> supplierList = customerList;
            if (query == null || query.trim().equals("")) {

                invOrganizationSiteDTOConverter = new InvOrganizationSiteDTOConverter(customerList);
                return supplierList;
            }
            List<InvOrganizationSiteDTO> filteredSuppliers = new ArrayList<>();

            String nameAr;
            String code;
            InvOrganizationSiteDTO supplierFilter;
            for (int i = 0; i < customerList.size(); i++) {
                supplierFilter = supplierList.get(i);
                nameAr = supplierFilter.getName();
                if (nameAr != null && nameAr.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }

                code = supplierFilter.getCode();
                if (code != null && code.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }
            }

            invOrganizationSiteDTOConverter = new InvOrganizationSiteDTOConverter(filteredSuppliers);
            return filteredSuppliers;
        } catch (Exception e) {
            saveError(e, "SettlementDeedFormMB", "completeSuppllier");
            return null;
        }
    }

    // Admin unit 
    public List<GlAdminUnitDTO> completeAdminUnit(String query) {
        try {
            List<GlAdminUnitDTO> supplierList = glAdminUnitList;
            if (query == null || query.trim().equals("")) {

                glAdminUnitDTOConverter = new GlAdminUnitDTOConverter(glAdminUnitList);
                return supplierList;
            }
            List<GlAdminUnitDTO> filteredSuppliers = new ArrayList<>();

            String nameAr;
            String code;
            GlAdminUnitDTO supplierFilter;
            for (int i = 0; i < glAdminUnitList.size(); i++) {
                supplierFilter = supplierList.get(i);
                nameAr = supplierFilter.getName();
                if (nameAr != null && nameAr.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }

                code = Integer.toString(supplierFilter.getCode());
                if (code != null && code.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }
            }

            glAdminUnitDTOConverter = new GlAdminUnitDTOConverter(filteredSuppliers);
            return filteredSuppliers;
        } catch (Exception e) {
            saveError(e, "SettlementDeedFormMB", "completeSuppllier");
            return null;
        }
    }

    public List<CostCenterDTO> completeCostCenter(String query) {
        try {
            List<CostCenterDTO> supplierList = costCenterDTOList;
            if (query == null || query.trim().equals("")) {

                costCenterDTOConverter = new CostCenterDTOConverter(costCenterDTOList);
                return supplierList;
            }
            List<CostCenterDTO> filteredSuppliers = new ArrayList<>();

            String nameAr;
            String code;
            CostCenterDTO supplierFilter;
            for (int i = 0; i < costCenterDTOList.size(); i++) {
                supplierFilter = supplierList.get(i);
                nameAr = supplierFilter.getName();
                if (nameAr != null && nameAr.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }

                code = Integer.toString(supplierFilter.getCode());
                if (code != null && code.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }
            }

            costCenterDTOConverter = new CostCenterDTOConverter(filteredSuppliers);
            return filteredSuppliers;
        } catch (Exception e) {
            saveError(e, "SettlementDeedFormMB", "completeSuppllier");
            return null;
        }
    }

    public List<GlBankDTO> completeGlBank(String query) {
        try {
            List<GlBankDTO> supplierList = glBankList;
            if (query == null || query.trim().equals("")) {

                glBankDTOConverter = new GlBankDTOConverter(glBankList);
                return supplierList;
            }
            List<GlBankDTO> filteredSuppliers = new ArrayList<>();

            String nameAr;
            String code;
            GlBankDTO supplierFilter;
            for (int i = 0; i < glBankList.size(); i++) {
                supplierFilter = supplierList.get(i);
                nameAr = supplierFilter.getName();
                if (nameAr != null && nameAr.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }

                code = supplierFilter.getCode();
                if (code != null && code.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }
            }

            glBankDTOConverter = new GlBankDTOConverter(filteredSuppliers);
            return filteredSuppliers;
        } catch (Exception e) {
            saveError(e, "SettlementDeedFormMB", "completeSuppllier");
            return null;
        }
    }

    public List<InvInventoryDTO> completeInvInventoty(String query) {
        try {
            List<InvInventoryDTO> supplierList = invInventoryList;
            if (query == null || query.trim().equals("")) {

                invInventoryDTOConverter = new InvInventoryDTOConverter(invInventoryList);
                return supplierList;
            }
            List<InvInventoryDTO> filteredSuppliers = new ArrayList<>();

            String nameAr;
            String code;
            InvInventoryDTO supplierFilter;
            for (int i = 0; i < invInventoryList.size(); i++) {
                supplierFilter = supplierList.get(i);
                nameAr = supplierFilter.getName();
                if (nameAr != null && nameAr.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }

                code = supplierFilter.getCode();
                if (code != null && code.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }
            }

            invInventoryDTOConverter = new InvInventoryDTOConverter(filteredSuppliers);
            return filteredSuppliers;
        } catch (Exception e) {
            saveError(e, "SettlementDeedFormMB", "completeSuppllier");
            return null;
        }
    }

    public List<InvInventoryDTO> completeGallary(String query) {
        try {
            List<InvInventoryDTO> supplierList = gallaryList;
            if (query == null || query.trim().equals("")) {

                invInventoryDTOConverter = new InvInventoryDTOConverter(gallaryList);
                return supplierList;
            }
            List<InvInventoryDTO> filteredSuppliers = new ArrayList<>();

            String nameAr;
            String code;
            InvInventoryDTO supplierFilter;
            for (int i = 0; i < gallaryList.size(); i++) {
                supplierFilter = supplierList.get(i);
                nameAr = supplierFilter.getName();
                if (nameAr != null && nameAr.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }

                code = supplierFilter.getCode();
                if (code != null && code.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }
            }

            invInventoryDTOConverter = new InvInventoryDTOConverter(filteredSuppliers);
            return filteredSuppliers;
        } catch (Exception e) {
            saveError(e, "SettlementDeedFormMB", "completeSuppllier");
            return null;
        }
    }

    public List<InventoryDelegatorDTO> completeInventotyDelegator(String query) {
        try {
            List<InventoryDelegatorDTO> supplierList = inventoryDelegatorList;
            if (query == null || query.trim().equals("")) {

                invDelegatorDTOConvertor = new InvDelegatorDTOConvertor(inventoryDelegatorList);
                return supplierList;
            }
            List<InventoryDelegatorDTO> filteredSuppliers = new ArrayList<>();

            String nameAr;
            String code;
            InventoryDelegatorDTO supplierFilter;
            for (int i = 0; i < inventoryDelegatorList.size(); i++) {
                supplierFilter = supplierList.get(i);
                nameAr = supplierFilter.getName();
                if (nameAr != null && nameAr.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }

                code = supplierFilter.getCode();
                if (code != null && code.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }
            }

            invDelegatorDTOConvertor = new InvDelegatorDTOConvertor(filteredSuppliers);
            return filteredSuppliers;
        } catch (Exception e) {
            saveError(e, "SettlementDeedFormMB", "completeSuppllier");
            return null;
        }
    }

    public List<InvItemDTO> completeInvItem(String query) {
        try {
            List<InvItemDTO> supplierList = invItemList;
            if (query == null || query.trim().equals("")) {

                itemDTOConverter = new ItemDTOConverter(invItemList);
                return supplierList;
            }
            List<InvItemDTO> filteredSuppliers = new ArrayList<>();

            String nameAr;
            String code;
            InvItemDTO supplierFilter;
            for (int i = 0; i < invItemList.size(); i++) {
                supplierFilter = supplierList.get(i);
                nameAr = supplierFilter.getName();
                if (nameAr != null && nameAr.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }

                code = supplierFilter.getCode();
                if (code != null && code.toLowerCase().contains(query.toLowerCase())) {
                    if (!filteredSuppliers.contains(supplierFilter)) {
                        filteredSuppliers.add(supplierFilter);
                    }
                }
            }

            itemDTOConverter = new ItemDTOConverter(filteredSuppliers);
            return filteredSuppliers;
        } catch (Exception e) {
            saveError(e, "SettlementDeedFormMB", "completeInvItem");
            return null;
        }
    }

    public void sumQuantity() {
        invPurchaseInvoiceDTO.setSumQuantity(BigDecimal.ZERO);
        for (InvPurchaseInvoiceDetailDTO detailDTO : invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailDTOList()) {
            if (detailDTO.getQuantity() != null && detailDTO.getNumber() != null) {
                invPurchaseInvoiceDTO.setSumQuantity((detailDTO.getQuantity().multiply(detailDTO.getNumber())).add(invPurchaseInvoiceDTO.getSumQuantity()));
                System.out.println("");
            }
        }

    }

    public void sumTotal() {
        invPurchaseInvoiceDTO.setSumTotal(BigDecimal.ZERO);
        for (InvPurchaseInvoiceDetailDTO detailDTO : invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailDTOList()) {
            if (detailDTO.getTotal() != null) {
                invPurchaseInvoiceDTO.setSumTotal(detailDTO.getTotal().add(invPurchaseInvoiceDTO.getSumTotal()));
            }
        }
    }

    public void removeRow() {
        invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailDTOList().remove(invPurchaseInvoiceDetailDTOSelected);
        finalQuantityAndTotal();
    }

    public void addrow() {
        if (invPurchaseInvoiceDetailDTO1.getInvItemParentId() != null && invPurchaseInvoiceDetailDTO1.getInvItemParentId().getId() != null) {
            if (validateDetailsWhenAddRow()) {
                InvPurchaseInvoiceDetailDTO invPurchaseInvoiceDetailDTO = new InvPurchaseInvoiceDetailDTO();
                invPurchaseInvoiceDetailDTO.setIndex(getIndex());
                if (invPurchaseInvoiceDTO.getInvInventoryDTO() != null) {
                    invPurchaseInvoiceDetailDTO.setInvInventoryDTO(new InvInventoryDTO());
                    invPurchaseInvoiceDetailDTO.getInvInventoryDTO().setName(invPurchaseInvoiceDTO.getInvInventoryDTO().getName());
                    invPurchaseInvoiceDetailDTO.getInvInventoryDTO().setId(invPurchaseInvoiceDTO.getInvInventoryDTO().getId());
                    if (getInventoryDTOList().isEmpty() || !inventoryDTOList.contains(invPurchaseInvoiceDTO.getInvInventoryDTO())) {
                        getInventoryDTOList().add(invPurchaseInvoiceDTO.getInvInventoryDTO());
                    }
                }
                invPurchaseInvoiceDetailDTO.setInvItemParentId(invPurchaseInvoiceDetailDTO1.getInvItemParentId());
//            invPurchaseInvoiceDetailDTO1.setInvItemParentId(new InvItemDTO());
                invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailDTOList().add(invPurchaseInvoiceDetailDTO);
                invPurchaseInvoiceDetailDTOSelected = invPurchaseInvoiceDetailDTO;

//            if (invPurchaseInvoiceDTO.getInvInventoryDTO() != null ) {
//                invPurchaseInvoiceDetailDTOSelected.setInvInventoryDTO(invPurchaseInvoiceDTO.getInvInventoryDTO());
//            }
            }
        }
    }

    public boolean validateDetailsWhenAddRow() {
        boolean bool = true;
        for (InvPurchaseInvoiceDetailDTO invPurchaseInvoiceDetailDTO : invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailDTOList()) {
            if (invPurchaseInvoiceDetailDTO.getItemId() == null || invPurchaseInvoiceDetailDTO.getCost() == null || invPurchaseInvoiceDetailDTO.getQuantity() == null) {
                bool = false;
                invPurchaseInvoiceDTO.setMsg(" الصنف - العدد - التكلفة -  يجب وضع قيمة ل");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
            }
        }

        return bool;
    }

    public void discount() {
//     if( invPurchaseInvoiceDetailDTOSelected.getQuantity() != null && invPurchaseInvoiceDetailDTOSelected.getAvailableQuantity() != null &&(invPurchaseInvoiceDetailDTOSelected.getQuantity()).compareTo(invPurchaseInvoiceDetailDTOSelected.getAvailableQuantity()) <=0  && invPurchaseInvoiceDetailDTOSelected.getQuantity().compareTo(BigDecimal.ZERO) >0 ){
//        BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
//        if (invPurchaseInvoiceDetailDTOSelected.getDiscount() != null && invPurchaseInvoiceDetailDTOSelected.getDiscount().compareTo(BigDecimal.ZERO) > 0) {
//            if (invPurchaseInvoiceDTO.getDetectPrice() != null) {
//                if (invPurchaseInvoiceDTO.getDetectPrice() == 1 && invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpricemen() != null && total.compareTo(invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpricemen()) >= 0) {
//                    invPurchaseInvoiceDetailDTOSelected.setTotal(total);
//                } else {
//                    // طلع رسالة خطا
//                    invPurchaseInvoiceDTO.setMsg(" )أقل سعر ممكن بعد الخصم   " + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )   = " + invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpricemen());
//                    invPurchaseInvoiceDetailDTOSelected.setCost(invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpricemen());
////                    discount();
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
//                }
//                if (invPurchaseInvoiceDTO.getDetectPrice() == 0 && invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpriceyoung() != null && total.compareTo(invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpriceyoung()) >= 0) {
//                    invPurchaseInvoiceDetailDTOSelected.setTotal(total);
//                } else {
//                    // طلع رسالة خطا
//                    invPurchaseInvoiceDTO.setMsg(" )أقل سعر ممكن بعد الخصم   " + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )   = " + invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpriceyoung());
//                    invPurchaseInvoiceDetailDTOSelected.setCost(invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpricemen());
////                    discount();
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
//                
//                }
//
//            }
//        }
//        total = total.add((invPurchaseInvoiceDetailDTOSelected.getBounse()== null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getBounse()));
//        invPurchaseInvoiceDetailDTOSelected.setTotal(total);
//        sumQuantity();
//        sumTotal();
//        }
//        else{
//            invPurchaseInvoiceDetailDTOSelected.setQuantity(BigDecimal.ZERO);
//        }
    }

    public List<InvOrganizationSiteDTO> getCustomerList() {

        if (customerList == null || customerList.isEmpty()) {
            customerList = organizationSiteService.getCustomerByBranchIdDTO(getUserData().getUser());
            setInvOrganizationSiteDTOConverter(new InvOrganizationSiteDTOConverter(customerList));
        }
        return customerList;
    }

    public boolean validateDetails() {
        boolean bool = true;

        if (invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailDTOList() != null && !invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailDTOList().isEmpty()) {
            for (InvPurchaseInvoiceDetailDTO invPurchaseInvoiceDetailDTO : invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailDTOList()) {
                invPurchaseInvoiceDetailDTOSelected = invPurchaseInvoiceDetailDTO;
                validateItem();
                if (invPurchaseInvoiceDetailDTO.getItemId() == null || invPurchaseInvoiceDetailDTO.getCost() == null || invPurchaseInvoiceDetailDTO.getQuantity() == null) {
                    bool = false;
                    invPurchaseInvoiceDTO.setMsg(" الصنف - العدد - التكلفة -  يجب وضع قيمة ل");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
                }
            }
        } else {
            bool = false;
        }
//        invPurchaseInvoiceDTO.setMsg(" يجب إضافة تفاصيل للفاتورة");
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));

        return bool;
    }

    public void validateItem() {
        if (invPurchaseInvoiceDetailDTOSelected != null && invPurchaseInvoiceDetailDTOSelected.getItemId().getId() != null) {
            invPurchaseInvoiceDetailDTOSelected.setUnitsItemseList(unitsItemsService.getUnitsByItemId(invPurchaseInvoiceDetailDTOSelected.getItemId().getId()));
            setUnitsItemsConverter(new UnitsItemsConverter(invPurchaseInvoiceDetailDTOSelected.getUnitsItemseList()));

        }

        menCase();
        youngCase();
        finalQuantityAndTotal();
        if (invPurchaseInvoiceDTO != null && invPurchaseInvoiceDetailDTOSelected != null && invPurchaseInvoiceDTO.getInvInventoryDTO() != null) {
            invPurchaseInvoiceDetailDTOSelected.setInvInventoryDTO(invPurchaseInvoiceDTO.getInvInventoryDTO());
        }
    }

    private void youngCase() {
        if (invPurchaseInvoiceDTO != null && invPurchaseInvoiceDetailDTOSelected != null && invPurchaseInvoiceDetailDTOSelected.getItemId().getId() != null && invPurchaseInvoiceDTO.getDetectPrice() != null && invPurchaseInvoiceDTO.getDetectPrice() == 0) {
            invPurchaseInvoiceDetailDTOSelected.setUnitsItem(getScrewingAndPrice());
            checkAvalableQuantity();
            casePriceSmallerMinYoungPrice();
            calculateQuantity();
            calculateScreenDiscountYoung();
            calculateBounse();
            calculateDiscountDBYoung();
        }
    }

    private void calculateBounse() {
        if (invPurchaseInvoiceDetailDTOSelected.getBounse() != null) {
//            BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
            invPurchaseInvoiceDetailDTOSelected.setTotal(invPurchaseInvoiceDetailDTOSelected.getTotal().add(invPurchaseInvoiceDetailDTOSelected.getBounse()));
//            finalQuantityAndTotal();
        }
//        casePriceSmallerMenPrice();
//        calculateQuantity();
//        calculateScreenDiscountMen();
//        calculateScreenDiscountYoung();
//        calculateDiscountDB();
    }

    private void finalQuantityAndTotal() {
        sumQuantity();
        sumTotal();
    }

    private void calculateScreenDiscountYoung() {
        if (invPurchaseInvoiceDetailDTOSelected.getDiscount() != null && invPurchaseInvoiceDetailDTOSelected.getCost() != null
                && invPurchaseInvoiceDetailDTOSelected.getUnitsItem() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceYoung() != null) {

            BigDecimal costAfterDiscount = (invPurchaseInvoiceDetailDTOSelected.getCost()).subtract((invPurchaseInvoiceDetailDTOSelected.getCost().divide(new BigDecimal(100))).multiply(invPurchaseInvoiceDetailDTOSelected.getDiscount()));
            if (costAfterDiscount.compareTo(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceYoung()) > 0) {
//                invPurchaseInvoiceDetailDTOSelected.setCost(costAfterDiscount);
                BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
                invPurchaseInvoiceDetailDTOSelected.setTotal(total);
                invPurchaseInvoiceDetailDTOSelected.setTotalQuantityRow((invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()));

//                finalQuantityAndTotal();
            } else {
                invPurchaseInvoiceDetailDTOSelected.setDiscount(BigDecimal.ZERO);
                BigDecimal costAfterDiscountZero = (invPurchaseInvoiceDetailDTOSelected.getCost()).subtract((invPurchaseInvoiceDetailDTOSelected.getCost().divide(new BigDecimal(100))).multiply(invPurchaseInvoiceDetailDTOSelected.getDiscount()));
                BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
                invPurchaseInvoiceDetailDTOSelected.setTotal(total);
                invPurchaseInvoiceDetailDTOSelected.setTotalQuantityRow((invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()));

//                finalQuantityAndTotal();
                invPurchaseInvoiceDTO.setMsg(" ) اقل سعر ممكن  " + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )   = " + invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceYoung());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
            }
        }
//        casePriceSmallerMinYoungPrice();
//        calculateQuantity();
//        calculateBounse();
//        calculateDiscountDB();
    }

    private void casePriceSmallerMinYoungPrice() {
        // اعلى سعر ولادى
        // فى حالة السعر اقل من اقل سعر ولادى
        if (invPurchaseInvoiceDetailDTOSelected.getCost() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceYoung() != null
                && invPurchaseInvoiceDetailDTOSelected.getCost().compareTo(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceYoung()) < 0) {

            invPurchaseInvoiceDetailDTOSelected.setCost(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceYoung());
            BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
            invPurchaseInvoiceDetailDTOSelected.setTotal(total);
            invPurchaseInvoiceDetailDTOSelected.setTotalQuantityRow((invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()));

            finalQuantityAndTotal();
            invPurchaseInvoiceDTO.setMsg(" )اقل سعر لهذا الصنف  " + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )   = " + invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpriceyoung());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
        } else if (invPurchaseInvoiceDetailDTOSelected.getCost() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceYoung() != null
                && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceYoung() != null
                && !(invPurchaseInvoiceDetailDTOSelected.getCost().compareTo(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceYoung()) == 0)
                && invPurchaseInvoiceDetailDTOSelected.getCost().compareTo(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceYoung()) > 0) {

            BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
            invPurchaseInvoiceDetailDTOSelected.setTotal(total);
            invPurchaseInvoiceDetailDTOSelected.setTotalQuantityRow((invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()));

//            finalQuantityAndTotal();
        } else {
            invPurchaseInvoiceDetailDTOSelected.setCost(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceYoung() != null ? invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceYoung() : BigDecimal.ZERO);
        }

//        calculateQuantity();
//        calculateScreenDiscountYoung();
//        calculateBounse();
//        calculateDiscountDB();
    }

    private void menCase() {
        // حالة الرجالى
        if (invPurchaseInvoiceDTO != null && invPurchaseInvoiceDetailDTOSelected != null && invPurchaseInvoiceDetailDTOSelected.getItemId().getId() != null && invPurchaseInvoiceDTO.getDetectPrice() != null && invPurchaseInvoiceDTO.getDetectPrice() == 1) {
            invPurchaseInvoiceDetailDTOSelected.setUnitsItem(getScrewingAndPrice());
            checkAvalableQuantity();
            casePriceSmallerMenPrice();
            calculateQuantity();
            calculateScreenDiscountMen();
            calculateBounse();
            calculateDiscountDBMen();
        }
    }

    private void calculateDiscountDBMen() {
        if (invPurchaseInvoiceDetailDTOSelected.getUnitsItem() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getDiscountrate() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceMen() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getDiscountrate().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal costAfterDiscuontDB = (invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceMen()).subtract((invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceMen().divide(new BigDecimal(100))).multiply(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getDiscountrate()));
            if (!(invPurchaseInvoiceDetailDTOSelected.getCost().compareTo(costAfterDiscuontDB) == 0)) {
                invPurchaseInvoiceDTO.setMsg(" ) لايمكن التعديل على السعر  " + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )   = " + costAfterDiscuontDB);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
            }
            invPurchaseInvoiceDetailDTOSelected.setCost(costAfterDiscuontDB);
            BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
            invPurchaseInvoiceDetailDTOSelected.setTotal(total);
            invPurchaseInvoiceDetailDTOSelected.setTotalQuantityRow((invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()));

//            finalQuantityAndTotal();
        }
//        casePriceSmallerMenPrice();
//        calculateQuantity();
//        calculateScreenDiscountMen();
//        calculateScreenDiscountYoung();
//        calculateBounse();
//        casePriceSmallerMinYoungPrice();

    }

    private void calculateDiscountDBYoung() {
        if (invPurchaseInvoiceDetailDTOSelected.getUnitsItem() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getDiscountrate() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceYoung() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getDiscountrate().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal costAfterDiscuontDB = (invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceYoung()).subtract((invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceYoung().divide(new BigDecimal(100))).multiply(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getDiscountrate()));
            if (!(invPurchaseInvoiceDetailDTOSelected.getCost().compareTo(costAfterDiscuontDB) == 0)) {
                invPurchaseInvoiceDTO.setMsg(" ) لايمكن التعديل على السعر  " + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )   = " + costAfterDiscuontDB);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
            }
            invPurchaseInvoiceDetailDTOSelected.setCost(costAfterDiscuontDB);
            BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
            invPurchaseInvoiceDetailDTOSelected.setTotal(total);
            invPurchaseInvoiceDetailDTOSelected.setTotalQuantityRow((invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()));

//            finalQuantityAndTotal();
        }
//        casePriceSmallerMenPrice();
//        calculateQuantity();
//        calculateScreenDiscountMen();
//        calculateScreenDiscountYoung();
//        calculateBounse();
//        casePriceSmallerMinYoungPrice();

    }

    private void calculateScreenDiscountMen() {
        if (invPurchaseInvoiceDetailDTOSelected.getDiscount() != null && invPurchaseInvoiceDetailDTOSelected.getCost() != null
                && invPurchaseInvoiceDetailDTOSelected.getUnitsItem() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceMen() != null) {

            BigDecimal costAfterDiscount = (invPurchaseInvoiceDetailDTOSelected.getCost()).subtract((invPurchaseInvoiceDetailDTOSelected.getCost().divide(new BigDecimal(100))).multiply(invPurchaseInvoiceDetailDTOSelected.getDiscount()));
            if (costAfterDiscount.compareTo(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceMen()) > 0) {
//                invPurchaseInvoiceDetailDTOSelected.setCost(costAfterDiscount);
                BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
                invPurchaseInvoiceDetailDTOSelected.setTotal(total);
                invPurchaseInvoiceDetailDTOSelected.setTotalQuantityRow((invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()));

//                finalQuantityAndTotal();
            } else {
                invPurchaseInvoiceDetailDTOSelected.setDiscount(BigDecimal.ZERO);
                BigDecimal costAfterDiscountZero = (invPurchaseInvoiceDetailDTOSelected.getCost()).subtract((invPurchaseInvoiceDetailDTOSelected.getCost().divide(new BigDecimal(100))).multiply(invPurchaseInvoiceDetailDTOSelected.getDiscount()));
                BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
                invPurchaseInvoiceDetailDTOSelected.setTotal(total);
                invPurchaseInvoiceDetailDTOSelected.setTotalQuantityRow((invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()));

//                finalQuantityAndTotal();
                invPurchaseInvoiceDTO.setMsg(" ) اقل سعر ممكن  " + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )   = " + invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceMen());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
            }
        }
//        casePriceSmallerMenPrice();
//        calculateQuantity();
//        calculateBounse();
//        calculateDiscountDB();
    }

    private void calculateQuantity() {
        if (invPurchaseInvoiceDetailDTOSelected.getQuantity() != null && invPurchaseInvoiceDetailDTOSelected.getAvailableQuantity() != null && (invPurchaseInvoiceDetailDTOSelected.getQuantity()).compareTo(invPurchaseInvoiceDetailDTOSelected.getAvailableQuantity()) <= 0 && invPurchaseInvoiceDetailDTOSelected.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
            invPurchaseInvoiceDetailDTOSelected.setTotal(total);
            invPurchaseInvoiceDetailDTOSelected.setTotalQuantityRow((invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()));

//            finalQuantityAndTotal();
        } else {
            if (invPurchaseInvoiceDetailDTOSelected.getQuantity() != null && invPurchaseInvoiceDetailDTOSelected.getAvailableQuantity() != null && (invPurchaseInvoiceDetailDTOSelected.getQuantity()).compareTo(invPurchaseInvoiceDetailDTOSelected.getAvailableQuantity()) > 0 && invPurchaseInvoiceDetailDTOSelected.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
                invPurchaseInvoiceDTO.setMsg(" )الكمية المتاحةلهذا الصنف  " + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )   = " + invPurchaseInvoiceDetailDTOSelected.getAvailableQuantity());
//                    invPurchaseInvoiceDetailDTOSelected.setCost(invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpricemen());
                invPurchaseInvoiceDetailDTOSelected.setQuantity(invPurchaseInvoiceDetailDTOSelected.getAvailableQuantity());
                BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
                invPurchaseInvoiceDetailDTOSelected.setTotal(total);
                invPurchaseInvoiceDetailDTOSelected.setTotalQuantityRow((invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()));

//                finalQuantityAndTotal();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
            } else {
                invPurchaseInvoiceDetailDTOSelected.setQuantity(BigDecimal.ZERO);
                BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
                invPurchaseInvoiceDetailDTOSelected.setTotal(total);
                invPurchaseInvoiceDetailDTOSelected.setTotalQuantityRow((invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()));

//                finalQuantityAndTotal();
            }
        }
//        casePriceSmallerMenPrice();
//        calculateScreenDiscountMen();
//        calculateScreenDiscountYoung();
//        calculateBounse();
//        calculateDiscountDB();
    }

    private void casePriceSmallerMenPrice() {

        // اعلى سعر رجالى
        // فى حالة السعر اقل من اقل سعر رجالى
        if (invPurchaseInvoiceDetailDTOSelected.getCost() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceMen() != null && invPurchaseInvoiceDetailDTOSelected.getCost().compareTo(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceMen()) < 0) {
            invPurchaseInvoiceDetailDTOSelected.setCost(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceMen() != null ? invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceMen() : BigDecimal.ZERO);
            BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
            invPurchaseInvoiceDetailDTOSelected.setTotal(total);
            invPurchaseInvoiceDetailDTOSelected.setTotalQuantityRow((invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()));
            invPurchaseInvoiceDTO.setMsg(" )اقل سعر لهذا الصنف  " + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )   = " + invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpricemen());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
        } else if (invPurchaseInvoiceDetailDTOSelected.getCost() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceMen() != null
                && invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceMen() != null
                && !(invPurchaseInvoiceDetailDTOSelected.getCost().compareTo(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceMen()) == 0)
                && invPurchaseInvoiceDetailDTOSelected.getCost().compareTo(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMinPriceMen()) > 0) {

            BigDecimal total = (invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()).multiply(invPurchaseInvoiceDetailDTOSelected.getCost() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getCost());
            invPurchaseInvoiceDetailDTOSelected.setTotal(total);
            invPurchaseInvoiceDetailDTOSelected.setTotalQuantityRow((invPurchaseInvoiceDetailDTOSelected.getQuantity() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getQuantity()).multiply(invPurchaseInvoiceDetailDTOSelected.getNumber() == null ? BigDecimal.ZERO : invPurchaseInvoiceDetailDTOSelected.getNumber()));

//                finalQuantityAndTotal();
        } else {
            if (invPurchaseInvoiceDetailDTOSelected.getUnitsItem() != null) {
                invPurchaseInvoiceDetailDTOSelected.setCost(invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceMen() != null ? invPurchaseInvoiceDetailDTOSelected.getUnitsItem().getMaxPriceMen() : BigDecimal.ZERO);
            }
        }

//            calculateQuantity();
//            calculateScreenDiscountMen();
//            calculateBounse();
//            calculateDiscountDB();
    }

    private UnitsItems getScrewingAndPrice() {
        UnitsItems unitsItems = new UnitsItems();
        if (invPurchaseInvoiceDetailDTOSelected != null && invPurchaseInvoiceDetailDTOSelected.getItemId().getId() != null && invPurchaseInvoiceDetailDTOSelected.getUnitsItemsSelected() != null) {
            unitsItems = unitsItemsService.getScrewingAndPrice(invPurchaseInvoiceDetailDTOSelected.getItemId().getId(), invPurchaseInvoiceDetailDTOSelected.getUnitsItemsSelected());
        }
        return unitsItems;
    }

    private void checkAvalableQuantity() {
        if (getQuantityMap().get(invPurchaseInvoiceDetailDTOSelected.getItemId().getId()) != null && getQuantityMap().get(invPurchaseInvoiceDetailDTOSelected.getItemId().getId()).compareTo(BigDecimal.ZERO) > 0) {
            invPurchaseInvoiceDetailDTOSelected.setAvailableQuantity(getQuantityMap().get(invPurchaseInvoiceDetailDTOSelected.getItemId().getId()));
        } else {
            if (invPurchaseInvoiceDetailDTOSelected.getItemId().getIsinventoryitem() != null && invPurchaseInvoiceDetailDTOSelected.getItemId().getIsinventoryitem() == 1) {

                invPurchaseInvoiceDetailDTOSelected.setAvailableQuantity(BigDecimal.ZERO);
                // طلع رسالة خطا ان الصنف غير موجود بالمخزن
                invPurchaseInvoiceDTO.setMsg("   هذ الصنف غير موجود بالمخزن " + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )   =  " + invPurchaseInvoiceDetailDTOSelected.getAvailableQuantity() + "من مخزن     " + invPurchaseInvoiceDTO.getInvInventoryDTO().getName());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
            } else {
                invPurchaseInvoiceDetailDTOSelected.setInvInventoryDTO(null);
            }

        }
    }

    public BigDecimal calcDiscountRate() {
        BigDecimal sellPrice = (invPurchaseInvoiceDetailDTOSelected.getItemId().getSellPrice() != null ? invPurchaseInvoiceDetailDTOSelected.getItemId().getSellPrice() : BigDecimal.ZERO);
        BigDecimal discountRate = (invPurchaseInvoiceDetailDTOSelected.getItemId().getDiscountrate() != null ? invPurchaseInvoiceDetailDTOSelected.getItemId().getDiscountrate() : BigDecimal.ZERO);
        BigDecimal finalSellPrice = (sellPrice.subtract(sellPrice.divide(new BigDecimal(100)).multiply(discountRate)));
        return sellPrice.subtract(sellPrice.divide(new BigDecimal(100)).multiply(discountRate));
    }

    public void calcScreenDiscount() {
//        BigDecimal cost = (invPurchaseInvoiceDetailDTOSelected.getCost() != null ? invPurchaseInvoiceDetailDTOSelected.getCost() : BigDecimal.ZERO);
//        BigDecimal discount = (invPurchaseInvoiceDetailDTOSelected.getDiscount() != null ? invPurchaseInvoiceDetailDTOSelected.getDiscount() : BigDecimal.ZERO);
//        BigDecimal finalCost = cost.subtract(cost.divide(new BigDecimal(100)).multiply(discount));
//        if (invPurchaseInvoiceDTO.getDetectPrice() != null && invPurchaseInvoiceDTO.getDetectPrice() == 1 && finalCost.compareTo(invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpricemen()) >= 0) {
//            invPurchaseInvoiceDetailDTOSelected.setCost(finalCost);
////             discount();
////            finalQuantityAndTotal();
//        } else {
//            invPurchaseInvoiceDTO.setMsg(" )أقل سعر ممكن بعد الخصم   " + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )   = " + invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpriceyoung());
//            invPurchaseInvoiceDetailDTOSelected.setCost(invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpricemen());
////                    discount();
//            finalQuantityAndTotal();
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
//
//        }
//        if (invPurchaseInvoiceDTO.getDetectPrice() != null && invPurchaseInvoiceDTO.getDetectPrice() == 0 && finalCost.compareTo(invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpriceyoung()) >= 0) {
//            invPurchaseInvoiceDetailDTOSelected.setCost(finalCost);
////             discount();
//            finalQuantityAndTotal();
//        } else {
//            invPurchaseInvoiceDTO.setMsg(" )أقل سعر ممكن بعد الخصم   " + invPurchaseInvoiceDetailDTOSelected.getItemId().getName() + "    " + invPurchaseInvoiceDetailDTOSelected.getItemId().getCode() + "  )   = " + invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpriceyoung());
//            invPurchaseInvoiceDetailDTOSelected.setCost(invPurchaseInvoiceDetailDTOSelected.getItemId().getMinpricemen());
////                    discount();
//            finalQuantityAndTotal();
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invPurchaseInvoiceDTO.getMsg()));
//
//        }

    }

    /**
     * @param customerList the customerList to set
     */
    public void setCustomerList(List<InvOrganizationSiteDTO> customerList) {
        this.customerList = customerList;
    }

    /**
     * @return the invPurchaseInvoiceDTO
     */
    public InvPurchaseInvoiceDTO1 getInvPurchaseInvoiceDTO() {
        if (invPurchaseInvoiceDTO == null) {
            invPurchaseInvoiceDTO = new InvPurchaseInvoiceDTO1();
            invPurchaseInvoiceDTO.setInvPurchaseInvoiceDetailDTOList(new ArrayList<>());
            invPurchaseInvoiceDTO.setDate(new Date());
            invPurchaseInvoiceDTO.setInvInventoryDTO(getInvInventoryList().get(0));
            invPurchaseInvoiceDetailDTO1 = new InvPurchaseInvoiceDetailDTO();
            invPurchaseInvoiceDetailDTO1.setInvItemParentId(new InvItemDTO());
            invPurchaseInvoiceDTO.setGallary(getGallaryList().get(0));

            changeInventory();
        }

        return invPurchaseInvoiceDTO;
    }

    /**
     * @param invPurchaseInvoiceDTO the invPurchaseInvoiceDTO to set
     */
    public void setInvPurchaseInvoiceDTO(InvPurchaseInvoiceDTO1 invPurchaseInvoiceDTO) {
        this.invPurchaseInvoiceDTO = invPurchaseInvoiceDTO;
    }

    @Override
    public String save() {
        try {

            if (validateDetails() && invPurchaseInvoiceDTO.getDate() != null && invPurchaseInvoiceDTO.getDueDate() != null) {
                invPurchaseInvoiceDTO.setRecieved(0);
                invPurchaseInvoiceDTO.setType(true);
                invPurchaseInvoiceDTO = invPurchaseInvoiceService.addInvPurchaseInvoice(invPurchaseInvoiceDTO, getUserData().getUser(), getInventoryDTOList());
            }
                 invPurchaseInvoiceDTO.setMsg(" تم الحفظ بنجاح");
            if (invPurchaseInvoiceDTO.getMsg() != null && !invPurchaseInvoiceDTO.getMsg().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error!", invPurchaseInvoiceDTO.getMsg()));
                newInvoice();
            }
        } catch (Exception e) {
        }
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

    public List<InventoryDelegatorDTO> getInventoryDelegatorList() {
        if (inventoryDelegatorList == null) {
            inventoryDelegatorList = inventoryDelegatorService.findInvPurchaseInvoiceforDelegator(getUserData().getUser());
            setInvDelegatorDTOConvertor(new InvDelegatorDTOConvertor(inventoryDelegatorList));
        }

        return inventoryDelegatorList;
    }

    /**
     * @param inventoryDelegatorList the inventoryDelegatorList to set
     */
    public void setInventoryDelegatorList(List<InventoryDelegatorDTO> inventoryDelegatorList) {
        this.inventoryDelegatorList = inventoryDelegatorList;
    }

    /**
     * @return the glBankList
     */
    public List<GlBankDTO> getGlBankList() {
        if (glBankList == null) {
            glBankList = getUserData().getGlBankList();
            setGlBankDTOConverter(new GlBankDTOConverter(glBankList));
        }
        return glBankList;
    }

    /**
     * @param glBankList the glBankList to set
     */
    public void setGlBankList(List<GlBankDTO> glBankList) {
        this.glBankList = glBankList;
    }

    /**
     * @return the invInventoryList
     */
    public List<InvInventoryDTO> getInvInventoryList() {
        if (invInventoryList == null || invInventoryList.isEmpty()) {
            invInventoryList = tobyUserInventoryService.getAllInventroyDTOByUserAndBranch(getUserData().getUser());
            setInvInventoryDTOConverter(new InvInventoryDTOConverter(invInventoryList));
        }

        return invInventoryList;
    }

    /**
     * @param invInventoryList the invInventoryList to set
     */
    public void setInvInventoryList(List<InvInventoryDTO> invInventoryList) {
        this.invInventoryList = invInventoryList;
    }

    /**
     * @return the glAdminUnitList
     */
    public List<GlAdminUnitDTO> getGlAdminUnitList() {

        if (glAdminUnitList == null || glAdminUnitList.isEmpty()) {
//            glAdminUnitList = glAdminUnitService.getAllSubAdminUnitByBranchIdActive(getUserData().getBranch().getId());
            setGlAdminUnitDTOConverter(new GlAdminUnitDTOConverter(glAdminUnitList));
        }

        return glAdminUnitList;
    }

    /**
     * @param glAdminUnitList the glAdminUnitList to set
     */
    public void setGlAdminUnitList(List<GlAdminUnitDTO> glAdminUnitList) {
        this.glAdminUnitList = glAdminUnitList;
    }

    public List<InvPurchaseInvoiceDTO1> getInvPurchaseInvoiceDTOList() {

        if (invPurchaseInvoiceDTOList == null || invPurchaseInvoiceDTOList.isEmpty()) {
            invPurchaseInvoiceDTOList = invPurchaseInvoiceService.findInvPurchaseInvoicefordelgatorDTO(getUserData().getUser());
            setInvPurchaseInvoiceDTOConverter(new InvPurchaseInvoiceDTOConverter(invPurchaseInvoiceDTOList));
        }
        return invPurchaseInvoiceDTOList;
    }

    /**
     * @param invPurchaseInvoiceDTOList the invPurchaseInvoiceDTOList to set
     */
    public void setInvPurchaseInvoiceDTOList(List<InvPurchaseInvoiceDTO1> invPurchaseInvoiceDTOList) {
        this.invPurchaseInvoiceDTOList = invPurchaseInvoiceDTOList;
    }

    /**
     * @return the invOrganizationSiteDTO
     */
    public InvOrganizationSiteDTO getInvOrganizationSiteDTO() {
        if (invOrganizationSiteDTO == null) {
            invOrganizationSiteDTO = new InvOrganizationSiteDTO();
        }
        return invOrganizationSiteDTO;
    }

    /**
     * @param invOrganizationSiteDTO the invOrganizationSiteDTO to set
     */
    public void setInvOrganizationSiteDTO(InvOrganizationSiteDTO invOrganizationSiteDTO) {
        this.invOrganizationSiteDTO = invOrganizationSiteDTO;
    }

    /**
     * @return the costCenterDTOList
     */
    public List<CostCenterDTO> getCostCenterDTOList() {
        if (costCenterDTOList == null || costCenterDTOList.isEmpty()) {
            costCenterDTOList = costCenterService.getAllCostCenterDTOList(getUserData().getUser());
            setCostCenterDTOConverter(new CostCenterDTOConverter(costCenterDTOList));
        }

        return costCenterDTOList;
    }

    /**
     * @param costCenterDTOList the costCenterDTOList to set
     */
    public void setCostCenterDTOList(List<CostCenterDTO> costCenterDTOList) {
        this.costCenterDTOList = costCenterDTOList;
    }

    /**
     * @return the invOrganizationSiteDTOConverter
     */
    public InvOrganizationSiteDTOConverter getInvOrganizationSiteDTOConverter() {
        return invOrganizationSiteDTOConverter;
    }

    /**
     * @param invOrganizationSiteDTOConverter the
     * invOrganizationSiteDTOConverter to set
     */
    public void setInvOrganizationSiteDTOConverter(InvOrganizationSiteDTOConverter invOrganizationSiteDTOConverter) {
        this.invOrganizationSiteDTOConverter = invOrganizationSiteDTOConverter;
    }

    /**
     * @return the invPurchaseInvoiceDTOConverter
     */
    public InvPurchaseInvoiceDTOConverter getInvPurchaseInvoiceDTOConverter() {
        return invPurchaseInvoiceDTOConverter;
    }

    /**
     * @param invPurchaseInvoiceDTOConverter the invPurchaseInvoiceDTOConverter
     * to set
     */
    public void setInvPurchaseInvoiceDTOConverter(InvPurchaseInvoiceDTOConverter invPurchaseInvoiceDTOConverter) {
        this.invPurchaseInvoiceDTOConverter = invPurchaseInvoiceDTOConverter;
    }

    /**
     * @return the invDelegatorDTOConvertor
     */
    public InvDelegatorDTOConvertor getInvDelegatorDTOConvertor() {
        return invDelegatorDTOConvertor;
    }

    /**
     * @param invDelegatorDTOConvertor the invDelegatorDTOConvertor to set
     */
    public void setInvDelegatorDTOConvertor(InvDelegatorDTOConvertor invDelegatorDTOConvertor) {
        this.invDelegatorDTOConvertor = invDelegatorDTOConvertor;
    }

    /**
     * @return the glAdminUnitDTOConverter
     */
    public GlAdminUnitDTOConverter getGlAdminUnitDTOConverter() {
        return glAdminUnitDTOConverter;
    }

    /**
     * @param glAdminUnitDTOConverter the glAdminUnitDTOConverter to set
     */
    public void setGlAdminUnitDTOConverter(GlAdminUnitDTOConverter glAdminUnitDTOConverter) {
        this.glAdminUnitDTOConverter = glAdminUnitDTOConverter;
    }

    public CostCenterDTOConverter getCostCenterDTOConverter() {
        return costCenterDTOConverter;
    }

    /**
     * @param costCenterDTOConverter the costCenterDTOConverter to set
     */
    public void setCostCenterDTOConverter(CostCenterDTOConverter costCenterDTOConverter) {
        this.costCenterDTOConverter = costCenterDTOConverter;
    }

    /**
     * @return the glBankDTOConverter
     */
    public GlBankDTOConverter getGlBankDTOConverter() {
        return glBankDTOConverter;
    }

    /**
     * @param glBankDTOConverter the glBankDTOConverter to set
     */
    public void setGlBankDTOConverter(GlBankDTOConverter glBankDTOConverter) {
        this.glBankDTOConverter = glBankDTOConverter;
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

    public ItemDTOConverter getItemDTOConverter() {
        return itemDTOConverter;
    }

    /**
     * @param itemDTOConverter the itemDTOConverter to set
     */
    public void setItemDTOConverter(ItemDTOConverter itemDTOConverter) {
        this.itemDTOConverter = itemDTOConverter;
    }

    /**
     * @return the invItemList
     */
    public List<InvItemDTO> getInvItemList() {
        if (invItemList == null || invItemList.isEmpty()) {
            invItemList = invItemService.findInvItemDTOList(getUserData().getUser());
            setItemDTOConverter(new ItemDTOConverter(invItemList));
        }
        return invItemList;
    }

    /**
     * @param invItemList the invItemList to set
     */
    public void setInvItemList(List<InvItemDTO> invItemList) {
        this.invItemList = invItemList;
    }

    /**
     * @return the invPurchaseInvoiceDetailDTOSelected
     */
    public InvPurchaseInvoiceDetailDTO getInvPurchaseInvoiceDetailDTOSelected() {
        return invPurchaseInvoiceDetailDTOSelected;
    }

    /**
     * @param invPurchaseInvoiceDetailDTOSelected the
     * invPurchaseInvoiceDetailDTOSelected to set
     */
    public void setInvPurchaseInvoiceDetailDTOSelected(InvPurchaseInvoiceDetailDTO invPurchaseInvoiceDetailDTOSelected) {
        this.invPurchaseInvoiceDetailDTOSelected = invPurchaseInvoiceDetailDTOSelected;
    }

    public InvItemDTO getInvItemDTO() {
        if (invItemDTO == null) {
            invItemDTO = new InvItemDTO();
        }
        return invItemDTO;
    }

    public void setInvItemDTO(InvItemDTO invItemDTO) {
        this.invItemDTO = invItemDTO;
    }

    /**
     * @return the invPurchaseInvoiceDetailConverter
     */
    public InvPurchaseInvoiceDetailConverter getInvPurchaseInvoiceDetailConverter() {
        return invPurchaseInvoiceDetailConverter;
    }

    /**
     * @param invPurchaseInvoiceDetailConverter the
     * invPurchaseInvoiceDetailConverter to set
     */
    public void setInvPurchaseInvoiceDetailConverter(InvPurchaseInvoiceDetailConverter invPurchaseInvoiceDetailConverter) {
        this.invPurchaseInvoiceDetailConverter = invPurchaseInvoiceDetailConverter;
    }

    public InvPurchaseInvoiceDetailDTOConverter getInvPurchaseInvoiceDetailDTOConverter() {
        return invPurchaseInvoiceDetailDTOConverter;
    }

    /**
     * @param invPurchaseInvoiceDetailDTOConverter the
     * invPurchaseInvoiceDetailDTOConverter to set
     */
    public void setInvPurchaseInvoiceDetailDTOConverter(InvPurchaseInvoiceDetailDTOConverter invPurchaseInvoiceDetailDTOConverter) {
        this.invPurchaseInvoiceDetailDTOConverter = invPurchaseInvoiceDetailDTOConverter;
    }

    /**
     * @return the quantityMap
     */
    public Map<Integer, BigDecimal> getQuantityMap() {
        if (quantityMap == null) {
            quantityMap = new HashMap<>();
        }
        return quantityMap;
    }

    /**
     * @param quantityMap the quantityMap to set
     */
    public void setQuantityMap(Map<Integer, BigDecimal> quantityMap) {
        this.quantityMap = quantityMap;
    }

    /**
     * @return the costRendered
     */
    public Boolean getCostRendered() {
        return costRendered;
    }

    /**
     * @param costRendered the costRendered to set
     */
    public void setCostRendered(Boolean costRendered) {
        this.costRendered = costRendered;
    }

    /**
     * @return the symbolDTOList
     */
    public List<SymbolDTO> getSymbolDTOList() {
        return symbolDTOList;
    }

    /**
     * @param symbolDTOList the symbolDTOList to set
     */
    public void setSymbolDTOList(List<SymbolDTO> symbolDTOList) {
        this.symbolDTOList = symbolDTOList;
    }

    /**
     * @return the unitsItemsMap
     */
//    public Map<Integer, List<UnitsItems>> getUnitsItemsMap() {
//        if (unitsItemsMap == null) {
//            List<UnitsItems> itemsOfUnitList = new ArrayList<>();
//            for (UnitsItems unitsItems : unitsItemList) {
//                if (unitsItemsMap.get(unitsItems.getUnitId()) != null) {
//                    itemsOfUnitList.add(unitsItems);
//                    unitsItemsMap.put(unitsItems.getUnitId(), itemsOfUnitList);
//                } else {
//                    itemsOfUnitList = new ArrayList<>();
//                    itemsOfUnitList.add(unitsItems);
//                    unitsItemsMap.put(unitsItems.getUnitId(), itemsOfUnitList);
//                }
//
//            }
//        }
//        return unitsItemsMap;
//    }
    /**
     * @param unitsItemsMap the unitsItemsMap to set
     */
    public void setUnitsItemsMap(Map<Integer, List<UnitsItems>> unitsItemsMap) {
        this.unitsItemsMap = unitsItemsMap;
    }

    /**
     * @return the unitsItemsConverter
     */
    public UnitsItemsConverter getUnitsItemsConverter() {
        return unitsItemsConverter;
    }

    /**
     * @param unitsItemsConverter the unitsItemsConverter to set
     */
    public void setUnitsItemsConverter(UnitsItemsConverter unitsItemsConverter) {
        this.unitsItemsConverter = unitsItemsConverter;
    }

    /**
     * @return the invItemDTOCompletedList
     */
    public List<InvItemDTO> getInvItemDTOCompletedList() {
        if (invItemDTOCompletedList == null || invItemDTOCompletedList.isEmpty()) {

            invItemDTOCompletedList = invItemService.findInvItemDTOCompletedList(getUserData().getUser());
            setItemDTOConverter1(new ItemDTOConverter(invItemDTOCompletedList));
        }
        return invItemDTOCompletedList;
    }

    /**
     * @param invItemDTOCompletedList the invItemDTOCompletedList to set
     */
    public void setInvItemDTOCompletedList(List<InvItemDTO> invItemDTOCompletedList) {
        this.invItemDTOCompletedList = invItemDTOCompletedList;
    }

    /**
     * @return the invInventoryTransactionDTO
     */
    public InvInventoryTransactionDTO getInvInventoryTransactionDTO() {
        return invInventoryTransactionDTO;
    }

    /**
     * @param invInventoryTransactionDTO the invInventoryTransactionDTO to set
     */
    public void setInvInventoryTransactionDTO(InvInventoryTransactionDTO invInventoryTransactionDTO) {
        this.invInventoryTransactionDTO = invInventoryTransactionDTO;
    }

    /**
     * @return the gallaryList
     */
    public List<InvInventoryDTO> getGallaryList() {
        if (gallaryList == null || gallaryList.isEmpty()) {
            gallaryList = tobyUserInventoryService.getAllGallaryByUserAndBranch(getUserData().getUser());
            setInvInventoryDTOConverter1(new InvInventoryDTOConverter(gallaryList));
        }
        return gallaryList;
    }

    /**
     * @param gallaryList the gallaryList to set
     */
    public void setGallaryList(List<InvInventoryDTO> gallaryList) {
        this.gallaryList = gallaryList;
    }

    /**
     * @return the invPurchaseInvoiceDetailDTO1
     */
    public InvPurchaseInvoiceDetailDTO getInvPurchaseInvoiceDetailDTO1() {
        return invPurchaseInvoiceDetailDTO1;
    }

    /**
     * @param invPurchaseInvoiceDetailDTO1 the invPurchaseInvoiceDetailDTO1 to
     * set
     */
    public void setInvPurchaseInvoiceDetailDTO1(InvPurchaseInvoiceDetailDTO invPurchaseInvoiceDetailDTO1) {
        this.invPurchaseInvoiceDetailDTO1 = invPurchaseInvoiceDetailDTO1;
    }

    /**
     * @return the invInventoryDTOConverter1
     */
    public InvInventoryDTOConverter getInvInventoryDTOConverter1() {
        return invInventoryDTOConverter1;
    }

    /**
     * @param invInventoryDTOConverter1 the invInventoryDTOConverter1 to set
     */
    public void setInvInventoryDTOConverter1(InvInventoryDTOConverter invInventoryDTOConverter1) {
        this.invInventoryDTOConverter1 = invInventoryDTOConverter1;
    }

    /**
     * @return the itemDTOConverter1
     */
    public ItemDTOConverter getItemDTOConverter1() {
        return itemDTOConverter1;
    }

    /**
     * @param itemDTOConverter1 the itemDTOConverter1 to set
     */
    public void setItemDTOConverter1(ItemDTOConverter itemDTOConverter1) {
        this.itemDTOConverter1 = itemDTOConverter1;
    }

    /**
     * @return the inventoryDTOList
     */
    public List<InvInventoryDTO> getInventoryDTOList() {
        if(inventoryDTOList == null){
            inventoryDTOList = new ArrayList<>();
        }
        return inventoryDTOList;
    }

    /**
     * @param inventoryDTOList the inventoryDTOList to set
     */
    public void setInventoryDTOList(List<InvInventoryDTO> inventoryDTOList) {
        this.inventoryDTOList = inventoryDTOList;
    }

}

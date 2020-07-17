/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.toby;

import com.toby.businessservice.CostCenterService;
import com.toby.businessservice.CurrencyService;
import com.toby.businessservice.GlAdminUnitService;
import com.toby.businessservice.InvDelegatorService;
import com.toby.businessservice.InvItemService;
import com.toby.businessservice.InvPurchaseInvoiceService_1;
import com.toby.businessservice.InventorySetupService;
import com.toby.businessservice.TobyUserInventoryService;
import com.toby.businessservice.ItemsBarcodesDetailsViewService;
import com.toby.businessservice.OrganizationSiteService;
import com.toby.businessservice.SymbolService;
import com.toby.businessservice.UnitsItemsService;
import com.toby.converter.CostCenterDTOConverter;
import com.toby.converter.CurrencyDTOConverter;
import com.toby.converter.GlAdminUnitDTOConverter;
import com.toby.converter.GlBankDTOConverter;
import com.toby.converter.InvDelegatorConvertor;
import com.toby.converter.InvDelegatorDTOConvertor;
import com.toby.converter.InvInventoryDTOConverter;
import com.toby.converter.InvOrganizationSiteDTOConverter;
import com.toby.converter.ItemDTOConverter;
import com.toby.converter.ItemsBarcodesDetailsViewConverter;
import com.toby.converter.SymbolDTOConverter;
import com.toby.define.ScreenNameClassEnum;
import com.toby.dto.CostCenterDTO;
import com.toby.dto.CurrencyDTO;
import com.toby.dto.GlAdminUnitDTO;
import com.toby.dto.GlBankDTO;
import com.toby.dto.InvInventoryDTO;
import com.toby.dto.InvItemDTO;
import com.toby.dto.InvOrganizationSiteDTO;
import com.toby.dto.InventoryDelegatorDTO;
import com.toby.dto.SymbolDTO;
import com.toby.entity.InventorySetup;
import com.toby.entity.UnitsItems;
import com.toby.views.ItemsBarcodesDetailsView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
//import com.toby.businessservice.InventorySetupService;

/**
 *
 * @author m_els
 */
public abstract class InventoryBasicDataForm extends BaseFormBean {

    private Integer siteType;
   

    private List<InvOrganizationSiteDTO> invOrganizationSiteDTOList;
    private InvOrganizationSiteDTO organizationSiteId;
    private List<SymbolDTO> regionList;
    private List<InventoryDelegatorDTO> invDelegatorList;
    private List<InvInventoryDTO> invInventoryList;
    private List<InvInventoryDTO> gallaryList;

    private ItemsBarcodesDetailsViewConverter itemsBarcodesDetailsViewConverter;
    private InvOrganizationSiteDTOConverter organizationSiteDTOConvertor;
    private SymbolDTOConverter regionConverter;
    private InvDelegatorDTOConvertor invDelegatorDTOConvertor;

    private List<InvInventoryDTO> invInventoryDTOList;
    private InvInventoryDTOConverter invInventoryDTOConverter;
    private InvInventoryDTOConverter gallaryDTOConverter;
    private List<CurrencyDTO> currencyDTOList;
    private CurrencyDTOConverter currencyConverter;
    private InventorySetup invSetup;
    private List<ItemsBarcodesDetailsView> itemsBarcodesDetailsViewList;
    private Map<String, ItemsBarcodesDetailsView> itemsBarcodeMap;
    private List<CostCenterDTO> costCenterList;
    private CostCenterDTOConverter costCenterConverter;
    private List<GlAdminUnitDTO> adminUnitList;
    private GlAdminUnitDTOConverter adminUnitConverter;
    private List<InvItemDTO> invItemDTOList;
    private List<InvItemDTO> invItemDTOCompletedList;
    private List<SymbolDTO> unitList;
    private List<UnitsItems> unitsItemseList;
    private SymbolDTOConverter unitConverter;
    private List<GlBankDTO> glBankList;
    private GlBankDTOConverter glBankConverter;

    private ItemDTOConverter itemDTOConverter;
    private ItemDTOConverter itemDTOConverter1;
    private String uri;

    @EJB
    private UnitsItemsService unitsItemsService;
    @EJB
    private OrganizationSiteService organizationSiteService;
    @EJB
    private InvItemService invItemService;
    @EJB
    private InvDelegatorService invDelegatorService;
    @EJB
    private CurrencyService currencyService;
    @EJB
    private ItemsBarcodesDetailsViewService itemsBarcodesDetailsViewService;
    @EJB
    private TobyUserInventoryService tobyUserInventoryService;
    @EJB
    private InventorySetupService inventorySetupService;
    @EJB
    private SymbolService symbolService;

    @EJB
    private CostCenterService costCenterService;
    @EJB
    private GlAdminUnitService glAdminUnitService;
    @EJB
    private InvPurchaseInvoiceService_1 invPurchaseInvoiceService;

    public abstract String save();

    public abstract void init();

    public abstract String exit();

    public abstract void load();

    public abstract String getScreenName();

    public void fillItemMap(Map<String, ItemsBarcodesDetailsView> itemsBarcodeMap, List<ItemsBarcodesDetailsView> itemsBarcodesDetailsViewList, Integer branchId) {
        for (ItemsBarcodesDetailsView itemsBarcodesDetailsView : getItemsBarcodesDetailsViewList()) {
            itemsBarcodeMap.put(itemsBarcodesDetailsView.getBarcode(), itemsBarcodesDetailsView);
        }
    }

    protected String validateloginDistributedScreens(ScreenNameClassEnum screenNameClassEnum) {
        return invPurchaseInvoiceService.validateloginDistributedScreens(getUserDataDTO(), screenNameClassEnum);
    }

    public List<InvOrganizationSiteDTO> completeFromCustomer(String query) {
        return AutoComplete.completOrgSiteDTO(query, getInvOrganizationSiteDTOList(Boolean.TRUE, 0), getOrganizationSiteDTOConvertor());

    }

    public List<InvOrganizationSiteDTO> completeToCustomer(String query) {
        return AutoComplete.completOrgSiteDTO(query, getInvOrganizationSiteDTOList(Boolean.TRUE, 0), getOrganizationSiteDTOConvertor());
    }

    public List<InventoryDelegatorDTO> completeDelegatorSales(String query) {
        return AutoComplete.completeDelegatorDTO(query, getInvDelegatorList(1), getInvDelegatorDTOConvertor());
    }

    public List<InventoryDelegatorDTO> completeDelegatorPurchase(String query) {
        return AutoComplete.completeDelegatorDTO(query, getInvDelegatorList(0), getInvDelegatorDTOConvertor());
    }

    public List<SymbolDTO> completeFromRegion(String query) {
        try {
            List<SymbolDTO> regionListFilter = getRegionList();
            if (query == null || query.trim().equals("")) {

                regionConverter = new SymbolDTOConverter(regionListFilter);
                return regionListFilter;
            }
            List<SymbolDTO> regionListFiltered = new ArrayList<>();

            String nameAr;
            Integer code;
            SymbolDTO regionFilter;
            for (int i = 0; i < getRegionList().size(); i++) {
                regionFilter = regionListFilter.get(i);
                nameAr = regionFilter.getName();
                if (nameAr != null && nameAr.toLowerCase().contains(query.toLowerCase())) {
                    if (!regionListFiltered.contains(regionFilter)) {
                        regionListFiltered.add(regionFilter);
                    }
                }

                code = regionFilter.getSerial();
                if (code != null && String.valueOf(code).toLowerCase().contains(query.toLowerCase())) {
                    if (!regionListFiltered.contains(regionFilter)) {
                        regionListFiltered.add(regionFilter);
                    }
                }
            }

            regionConverter = new SymbolDTOConverter(regionListFiltered);
            return regionListFiltered;
        } catch (Exception e) {
            saveError(e, "OrganizationSiteFormMB", "completeRegion");
            return null;
        }
    }

    public List<SymbolDTO> completeToRegion(String query) {
        try {
            List<SymbolDTO> regionListFilter = getRegionList();
            if (query == null || query.trim().equals("")) {

                regionConverter = new SymbolDTOConverter(regionListFilter);
                return regionListFilter;
            }
            List<SymbolDTO> regionListFiltered = new ArrayList<>();

            String nameAr;
            Integer code;
            SymbolDTO regionFilter;
            for (int i = 0; i < getRegionList().size(); i++) {
                regionFilter = regionListFilter.get(i);
                nameAr = regionFilter.getName();
                if (nameAr != null && nameAr.toLowerCase().contains(query.toLowerCase())) {
                    if (!regionListFiltered.contains(regionFilter)) {
                        regionListFiltered.add(regionFilter);
                    }
                }

                code = regionFilter.getSerial();
                if (code != null && String.valueOf(code).toLowerCase().contains(query.toLowerCase())) {
                    if (!regionListFiltered.contains(regionFilter)) {
                        regionListFiltered.add(regionFilter);
                    }
                }
            }

            regionConverter = new SymbolDTOConverter(regionListFiltered);
            return regionListFiltered;
        } catch (Exception e) {
            saveError(e, "OrganizationSiteFormMB", "completeRegion");
            return null;
        }
    }

    public List<CostCenterDTO> completeCostCenters(String query) {
        return AutoComplete.completeCostCenters(query, getCostCenterList(), getCostCenterConverter());
    }

    public List<InvItemDTO> completeItemsData(String query) {
        return AutoComplete.completeItemsData(query, getInvItemDTOList(), getItemDTOConverter());
    }

    public List<SymbolDTO> completeSymbolUnit(String query) {
        return AutoComplete.completeSymbolDTO(query, getUnitList(), getUnitConverter());
    }

    public List<InvOrganizationSiteDTO> completeSuppllier(String query) {
        return AutoComplete.completOrgSiteDTO(query, getInvOrganizationSiteDTOList(Boolean.TRUE, 1), getOrganizationSiteDTOConvertor());
    }

    public List<CurrencyDTO> completeCurrency(String query) {
        return AutoComplete.completeCurrency(query, getCurrencyDTOList(), getCurrencyConverter());

    }

    public List<GlAdminUnitDTO> completeAdminUnits(String query) {
        return AutoComplete.completeAdminUnits(query, getAdminUnitList(), getAdminUnitConverter());
    }

    public List<InvInventoryDTO> completeInventory(String query) {
        return AutoComplete.completeInventoryDTO(query, getInvInventoryDTOList(), getInvInventoryDTOConverter());
    }

    public List<InvInventoryDTO> completeGallary(String query) {
        return AutoComplete.completeInventoryDTO(query, getGallaryList(), getGallaryDTOConverter());
    }

    public List<GlBankDTO> completeGlBank(String query) {
        return AutoComplete.completeGlBank(query, getGlBankList(), getGlBankConverter());
    }

    public List<InvOrganizationSiteDTO> getInvOrganizationSiteDTOList(Boolean active, Integer type) {
        if (getInvOrganizationSiteDTOList() == null) {
            setInvOrganizationSiteDTOList(organizationSiteService.getorganizationSiteByBranchIdDTO(getUserlogin().getBranchId().getId(), active, type));
            setOrganizationSiteDTOConvertor(new InvOrganizationSiteDTOConverter(getInvOrganizationSiteDTOList()));
        }
        return getInvOrganizationSiteDTOList();
    }

    /**
     * @param invOrganizationSiteList the invOrganizationSiteDTOList to set
     */
    public void setInvOrganizationSiteDTOList(List<InvOrganizationSiteDTO> invOrganizationSiteDTOList) {
        this.invOrganizationSiteDTOList = invOrganizationSiteDTOList;
    }

    /**
     * @return the invItemList
     */
//    public List<InvItem> getInvItemList() {
//        if (invItemList == null) {
//            invItemList = invItemService.getInvItemByBranchId(getUserData().getUserBranch().getId());
//        }
//        return invItemList;
//    }
//
//    /**
//     * @param invItemList the invItemList to set
//     */
//    public void setInvItemList(List<InvItem> invItemList) {
//        this.invItemList = invItemList;
//    }
    /**
     * @param type
     * @return the invDelegatorList
     */
    public List<InventoryDelegatorDTO> getInvDelegatorList(Integer type) {
        if (invDelegatorList == null) {
            invDelegatorList = invDelegatorService.getDelegatorsByBranchDTO(type, getUserData().getUser());
            invDelegatorDTOConvertor = new InvDelegatorDTOConvertor(invDelegatorList);
        }
        return invDelegatorList;
    }

    /**
     * @param invDelegatorList the invDelegatorList to set
     */
    public void setInvDelegatorList(List<InventoryDelegatorDTO> invDelegatorList) {
        this.invDelegatorList = invDelegatorList;
    }

    public List<ItemsBarcodesDetailsView> getItemsBarcodesDetailsViewList() {
        if (itemsBarcodesDetailsViewList == null || itemsBarcodesDetailsViewList.isEmpty()) {
            itemsBarcodesDetailsViewList = getItemsBarcodesDetailsViewService().findItemsBarcodesDetailsViewBranchId(getUserData().getUserBranch().getId());
            setItemsBarcodesDetailsViewConverter(new ItemsBarcodesDetailsViewConverter(getItemsBarcodesDetailsViewList()));
        }
        return itemsBarcodesDetailsViewList;
    }

    public void setItemsBarcodesDetailsViewList(List<ItemsBarcodesDetailsView> itemsBarcodesDetailsViewList) {
        this.itemsBarcodesDetailsViewList = itemsBarcodesDetailsViewList;
    }

    public ItemsBarcodesDetailsViewConverter getItemsBarcodesDetailsViewConverter() {
        return itemsBarcodesDetailsViewConverter;
    }

    public void setItemsBarcodesDetailsViewConverter(ItemsBarcodesDetailsViewConverter itemsBarcodesDetailsViewConverter) {
        this.itemsBarcodesDetailsViewConverter = itemsBarcodesDetailsViewConverter;
    }

//    public List<InventoryDelegator> getInventoryDelegatorList() {
//        if (inventoryDelegatorList == null || inventoryDelegatorList.isEmpty()) {
//            setInventoryDelegatorList(invDelegatorService.getPurchaseByBranch(getUserData().getUserBranch().getId()));// )مندوب المشتريات
//            setPurchasePersonConvertor(new InvDelegatorConvertor(getInventoryDelegatorList()));
//        }
//        return inventoryDelegatorList;
//    }
//
//    public void setInventoryDelegatorList(List<InventoryDelegator> inventoryDelegatorList) {
//        this.inventoryDelegatorList = inventoryDelegatorList;
//    }
    /**
     * @return the siteType
     */
    public Integer getSiteType() {
        return siteType;
    }

    /**
     * @param siteType the siteType to set
     */
    public void setSiteType(Integer siteType) {
        this.siteType = siteType;
    }

    /**
     * @return the itemsBarcodesDetailsViewService
     */
    public ItemsBarcodesDetailsViewService getItemsBarcodesDetailsViewService() {
        return itemsBarcodesDetailsViewService;
    }

    /**
     * @param itemsBarcodesDetailsViewService the
     * itemsBarcodesDetailsViewService to set
     */
    public void setItemsBarcodesDetailsViewService(ItemsBarcodesDetailsViewService itemsBarcodesDetailsViewService) {
        this.itemsBarcodesDetailsViewService = itemsBarcodesDetailsViewService;
    }

    /**
     * @return the invInventoryList
     */
    public List<InvInventoryDTO> getInvInventoryList() {
        if (invInventoryList == null || invInventoryList.isEmpty()) {
            invInventoryList = tobyUserInventoryService.getAllInventroisByUserAndBranchPerDTO(getUserData().getUser().getId(), getUserData().getUserBranch().getId());
            invInventoryDTOConverter = new InvInventoryDTOConverter(invInventoryList);
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
     * @return the invSetup
     */
    public InventorySetup getInvSetup() {
        if (invSetup == null || invSetup.getId() == null) {
//            setInvSetup(inventorySetupService.getInventoryByBranchId(getUserData().getUserBranch().getId()));
        }
        return invSetup;
    }

    /**
     * @param invSetup the invSetup to set
     */
    public void setInvSetup(InventorySetup invSetup) {
        this.invSetup = invSetup;
    }

    /**
     * @return the organizationSiteDTOConvertor
     */
    public InvOrganizationSiteDTOConverter getOrganizationSiteDTOConvertor() {
        return organizationSiteDTOConvertor;
    }

    /**
     * @param organizationSiteDTOConvertor the organizationSiteDTOConvertor to
     * set
     */
    public void setOrganizationSiteDTOConvertor(InvOrganizationSiteDTOConverter organizationSiteDTOConvertor) {
        this.organizationSiteDTOConvertor = organizationSiteDTOConvertor;
    }

    /**
     * @return the regionConverter
     */
    public SymbolDTOConverter getRegionConverter() {
        return regionConverter;
    }

    /**
     * @param regionConverter the regionConverter to set
     */
    public void setRegionConverter(SymbolDTOConverter regionConverter) {
        this.regionConverter = regionConverter;
    }

    /**
     * @return the regionList
     */
    public List<SymbolDTO> getRegionList() {
        if (regionList == null) {
            regionList = symbolService.getAllStoreRegionDTOByCompanyId(getUserData().getCompany().getId());
            setRegionConverter(new SymbolDTOConverter(regionList));
        }
        return regionList;
    }

    /**
     * @param regionList the regionList to set
     */
    public void setRegionList(List<SymbolDTO> regionList) {
        this.regionList = regionList;
    }

    /**
     * @return the invInventoryDTOList
     */
    public List<InvInventoryDTO> getInvInventoryDTOList() {
        if (invInventoryDTOList == null) {
            invInventoryDTOList = getUserData().getInventoryDTOList();
            setInvInventoryDTOConverter(new InvInventoryDTOConverter(invInventoryDTOList));
        }
        return invInventoryDTOList;
    }

    /**
     * @param invInventoryDTOList the invInventoryDTOList to set
     */
    public void setInvInventoryDTOList(List<InvInventoryDTO> invInventoryDTOList) {
        this.invInventoryDTOList = invInventoryDTOList;
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

    /**
     * @return the currencyDTOList
     */
    public List<CurrencyDTO> getCurrencyDTOList() {
        if (currencyDTOList == null) {
            currencyDTOList = currencyService.getAllCurrenciesWithLocalCurrencyHavingRatesByCompanyIdDTO(getUserData().getCompany().getId()); // العملات
            setCurrencyConverter(new CurrencyDTOConverter(currencyDTOList));
        }
        return currencyDTOList;
    }

    /**
     * @param currencyDTOList the currencyDTOList to set
     */
    public void setCurrencyDTOList(List<CurrencyDTO> currencyDTOList) {
        this.currencyDTOList = currencyDTOList;
    }

    /**
     * @return the currencyConverter
     */
    public CurrencyDTOConverter getCurrencyConverter() {
        return currencyConverter;
    }

    /**
     * @param currencyConverter the currencyConverter to set
     */
    public void setCurrencyConverter(CurrencyDTOConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    /**
     * @return the itemsBarcodeMap
     */
    public Map<String, ItemsBarcodesDetailsView> getItemsBarcodeMap() {
        if (itemsBarcodesDetailsViewList == null || itemsBarcodesDetailsViewList.isEmpty()) {
            itemsBarcodeMap = itemsBarcodesDetailsViewService.findItemsBarcodesDetailsViewBranchIdMap(getUserData().getUserBranch().getId());
        }
        return itemsBarcodeMap;
    }

    /**
     * @param itemsBarcodeMap the itemsBarcodeMap to set
     */
    public void setItemsBarcodeMap(Map<String, ItemsBarcodesDetailsView> itemsBarcodeMap) {
        this.itemsBarcodeMap = itemsBarcodeMap;
    }

    /**
     * @return the costCenterList
     */
    public List<CostCenterDTO> getCostCenterList() {
        if (costCenterList == null) {
            costCenterList = costCenterService.getActiveSubCostCentersByBranchDTO(getUserData().getUserBranch().getId()); // مراكز التكلفة
            setCostCenterConverter(new CostCenterDTOConverter(costCenterList));
        }
        return costCenterList;
    }

    /**
     * @param costCenterList the costCenterList to set
     */
    public void setCostCenterList(List<CostCenterDTO> costCenterList) {
        this.costCenterList = costCenterList;
    }

    /**
     * @return the adminUnitList
     */
    public List<GlAdminUnitDTO> getAdminUnitList() {
        if (adminUnitList == null) {
            adminUnitList = glAdminUnitService.getAllSubAdminUnitByBranchIdActiveDTO(getUserData().getUserBranch().getId()); // الوحدات الادارية
            setAdminUnitConverter(new GlAdminUnitDTOConverter(adminUnitList));
        }
        return adminUnitList;
    }

    /**
     * @param adminUnitList the adminUnitList to set
     */
    public void setAdminUnitList(List<GlAdminUnitDTO> adminUnitList) {
        this.adminUnitList = adminUnitList;
    }

    /**
     * @return the invItemDTOList
     */
    public List<InvItemDTO> getInvItemDTOList() {
        if (invItemDTOList == null || invItemDTOList.isEmpty()) {
            invItemDTOList = invItemService.findInvItemDTOList(getUserData().getUser());
            setItemDTOConverter(new ItemDTOConverter(invItemDTOList));
        }
        return invItemDTOList;
    }

    /**
     * @param invItemDTOList the invItemDTOList to set
     */
    public void setInvItemDTOList(List<InvItemDTO> invItemDTOList) {
        this.invItemDTOList = invItemDTOList;
    }

    /**
     * @return the unitList
     */
    public List<SymbolDTO> getUnitList() {
        if (unitList == null) {
            unitList = symbolService.getUnitsByCompanyIdDTO(getUserData().getCompany().getId()); // الوحدات: جرام, طن
            setUnitConverter(new SymbolDTOConverter(unitList));
        }
        return unitList;
    }

    /**
     * @param unitList the unitList to set
     */
    public void setUnitList(List<SymbolDTO> unitList) {
        this.unitList = unitList;
    }

    /**
     * @return the costCenterConverter
     */
    public CostCenterDTOConverter getCostCenterConverter() {
        return costCenterConverter;
    }

    /**
     * @param costCenterConverter the costCenterConverter to set
     */
    public void setCostCenterConverter(CostCenterDTOConverter costCenterConverter) {
        this.costCenterConverter = costCenterConverter;
    }

    /**
     * @return the adminUnitConverter
     */
    public GlAdminUnitDTOConverter getAdminUnitConverter() {
        return adminUnitConverter;
    }

    /**
     * @param adminUnitConverter the adminUnitConverter to set
     */
    public void setAdminUnitConverter(GlAdminUnitDTOConverter adminUnitConverter) {
        this.adminUnitConverter = adminUnitConverter;
    }

    /**
     * @return the unitConverter
     */
    public SymbolDTOConverter getUnitConverter() {
        return unitConverter;
    }

    /**
     * @param unitConverter the unitConverter to set
     */
    public void setUnitConverter(SymbolDTOConverter unitConverter) {
        this.unitConverter = unitConverter;
    }

    public List<GlBankDTO> getGlBankList() {
        if (glBankList == null) {
            glBankList = getUserData().getGlBankDTOList(); //  ودا بردوا
            setGlBankConverter(new GlBankDTOConverter(glBankList));
        }
        return glBankList;
    }

    public void setGlBankList(List<GlBankDTO> glBankList) {
        this.glBankList = glBankList;
    }

    /**
     * @return the glBankConverter
     */
    public GlBankDTOConverter getGlBankConverter() {
        return glBankConverter;
    }

    /**
     * @param glBankConverter the glBankConverter to set
     */
    public void setGlBankConverter(GlBankDTOConverter glBankConverter) {
        this.glBankConverter = glBankConverter;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        setUri(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI());
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the itemDTOConverter
     */
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
     * @return the gallaryList
     */
    public List<InvInventoryDTO> getGallaryList() {
        if (gallaryList == null || gallaryList.isEmpty()) {
            gallaryList = tobyUserInventoryService.getAllGallaryByUserAndBranch(getUserData().getUser());
            setGallaryDTOConverter(new InvInventoryDTOConverter(gallaryList));
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
     * @return the gallaryDTOConverter
     */
    public InvInventoryDTOConverter getGallaryDTOConverter() {
        return gallaryDTOConverter;
    }

    /**
     * @param gallaryDTOConverter the gallaryDTOConverter to set
     */
    public void setGallaryDTOConverter(InvInventoryDTOConverter gallaryDTOConverter) {
        this.gallaryDTOConverter = gallaryDTOConverter;
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
     * @return the organizationSiteId
     */
    public InvOrganizationSiteDTO getOrganizationSiteId() {
        if (organizationSiteId == null) {
            organizationSiteId = new InvOrganizationSiteDTO();
        }
        return organizationSiteId;
    }

    /**
     * @param organizationSiteId the organizationSiteId to set
     */
    public void setOrganizationSiteId(InvOrganizationSiteDTO organizationSiteId) {
        this.organizationSiteId = organizationSiteId;
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
     * @return the invOrganizationSiteDTOList
     */
    public List<InvOrganizationSiteDTO> getInvOrganizationSiteDTOList() {
        if (invOrganizationSiteDTOList == null || invOrganizationSiteDTOList.isEmpty()) {
            invOrganizationSiteDTOList = organizationSiteService.getorganizationSiteDTOByBranchIdForGlBankModule(getUserData().getUser(), true, getSiteType()); // المورد
            setOrganizationSiteDTOConvertor(new InvOrganizationSiteDTOConverter(invOrganizationSiteDTOList));
        }
        return invOrganizationSiteDTOList;
    }

    /**
     * @return the unitsItemseList
     */
    public List<UnitsItems> getUnitsItemseList() {
      
        return unitsItemseList;
    }

    /**
     * @param unitsItemseList the unitsItemseList to set
     */
    public void setUnitsItemseList(List<UnitsItems> unitsItemseList) {
        this.unitsItemseList = unitsItemseList;
    }

    /**
     * @return the unitsItemsService
     */
    public UnitsItemsService getUnitsItemsService() {
        return unitsItemsService;
    }

    /**
     * @param unitsItemsService the unitsItemsService to set
     */
    public void setUnitsItemsService(UnitsItemsService unitsItemsService) {
        this.unitsItemsService = unitsItemsService;
    }

  
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.common.Validation;
import com.toby.core.GenericDAO;
import com.toby.core.UserDataDTO;
import com.toby.define.CompanyActivityClassEnum;
import com.toby.define.ScreenNameClassEnum;
import com.toby.dto.CostCenterDTO;
import com.toby.dto.CurrencyDTO;
import com.toby.dto.CurrencyOperationDTO;
import com.toby.dto.GlAdminUnitDTO;
import com.toby.dto.GlBankDTO;
import com.toby.dto.InvDetailDTO;
import com.toby.dto.InvInventoryDTO;
import com.toby.dto.InvOrganizationSiteDTO;
import com.toby.dto.InvPurchaseInvoiceDTO;
import com.toby.dto.InventoryDelegatorDTO;
import com.toby.entity.Branch;
import com.toby.entity.CostCenter;
import com.toby.entity.Currency;
import com.toby.entity.GlAccount;
import com.toby.entity.GlAdminUnit;
import com.toby.entity.GlBank;
import com.toby.entity.InvInventory;
import com.toby.entity.InvOrganizationSite;
import com.toby.entity.InvPurchaseInvoice;
import com.toby.entity.InventoryDelegator;
import com.toby.entity.TobyCompany;
import com.toby.entity.TobyUser;
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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author WIN7
 */
@Stateless
public class InvPurchaseInvoiceServiceImpl_1 implements InvPurchaseInvoiceService_1 {

    @EJB
    private GenericDAO dao;
    @EJB
    InvPurchaseInvoiceDetailsService invPurchaseInvoiceDetailsService;
    @EJB
    private GenericService genericService;
    @EJB
    private CurrencyOperationService currencyOperationService;
    @EJB
    private OrganizationSiteService organizationSiteService;
    @EJB
    private CurrencyService currencyService;

    @Override
    public InvPurchaseInvoiceDTO findInvPurchaseInvoiceById(Integer invPurchaseInvoiceId, TobyUser tobyUser) {
        InvPurchaseInvoice invPurchaseInvoice = dao.findEntityById(InvPurchaseInvoice.class, invPurchaseInvoiceId);
        return mapToDTO(invPurchaseInvoice);
    }

    @Override
    public synchronized Integer getMaxSerialTaxForInvoice(Integer branchId, Integer inventoryId, Boolean type, Boolean taxFalgFinal) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);

        StringBuilder query = new StringBuilder();
        query.append("SELECT max(e.serialTax) FROM InvPurchaseInvoice e WHERE e.branchId.id =:branchId");

        if (type != null) {
            params.put("type", type);
            query.append(" AND e.type =:type");
        }

        if (inventoryId != null) {
            params.put("inventoryId", inventoryId);
            query.append(" AND e.invInventoryId.id =:inventoryId");
        }

        if (taxFalgFinal) {
            query.append(" AND e.taxFlagFinal = 1 ");
        } else {
            query.append(" AND e.taxFlagFinal = 0 ");
        }

        Integer serial = dao.findEntityByQuery(query.toString(), params);

        if (serial == null) {
            return 1;
        } else {
            return serial + 1;
        }
    }

    private InvPurchaseInvoiceDTO mapToDTO(InvPurchaseInvoice invPurchaseInvoice) {

        InvPurchaseInvoiceDTO invPurchaseInvoiceDTO = new InvPurchaseInvoiceDTO();

        invPurchaseInvoiceDTO.setId(invPurchaseInvoice.getId());
        invPurchaseInvoiceDTO.setDueperiod(invPurchaseInvoice.getDueperiod());
        invPurchaseInvoiceDTO.setPricetype(invPurchaseInvoice.getPricetype());
        invPurchaseInvoiceDTO.setSerial(invPurchaseInvoice.getSerial());
        invPurchaseInvoiceDTO.setCompanyActivity(invPurchaseInvoice.getBranchId().getCompanyActivity());
        if (invPurchaseInvoice.getAdminUnitId() != null) {
            GlAdminUnitDTO adminUnitDTO = new GlAdminUnitDTO();
            adminUnitDTO.setId(invPurchaseInvoice.getAdminUnitId().getId());
            adminUnitDTO.setName(invPurchaseInvoice.getAdminUnitId().getName());
            adminUnitDTO.setCode(invPurchaseInvoice.getAdminUnitId().getCode());
            invPurchaseInvoiceDTO.setAdminUnitId(adminUnitDTO);
        }
        invPurchaseInvoiceDTO.setBranchId(invPurchaseInvoice.getBranchId() != null ? invPurchaseInvoice.getBranchId().getId() : null);
        if (invPurchaseInvoice.getCostCenterId() != null) {
            CostCenterDTO centerDTO = new CostCenterDTO();
            centerDTO.setId(invPurchaseInvoice.getCostCenterId().getId());
            centerDTO.setName(invPurchaseInvoice.getCostCenterId().getName());
            centerDTO.setCode(invPurchaseInvoice.getCostCenterId().getCode());
            invPurchaseInvoiceDTO.setCostCenterId(centerDTO);
        }
        if (invPurchaseInvoice.getCurrencyId() != null) {
            CurrencyDTO currencyDTO = new CurrencyDTO();
            currencyDTO.setId(invPurchaseInvoice.getCurrencyId().getId());
            currencyDTO.setName(invPurchaseInvoice.getCurrencyId().getName());
            currencyDTO.setCode(invPurchaseInvoice.getCurrencyId().getCode());
            invPurchaseInvoiceDTO.setCurrencyId(currencyDTO);
        }
        invPurchaseInvoiceDTO.setDate(invPurchaseInvoice.getDate());
        if (invPurchaseInvoice.getAccountId() != null) {
            GlAccount glAccount = new GlAccount();
            glAccount.setId(invPurchaseInvoice.getAccountId().getId());
            invPurchaseInvoiceDTO.setAccountId(glAccount.getId());
        }
        invPurchaseInvoiceDTO.setRemarks(invPurchaseInvoice.getRemarks());
        invPurchaseInvoiceDTO.setDiscount(invPurchaseInvoice.getDiscount());
        invPurchaseInvoiceDTO.setDiscountType(invPurchaseInvoice.getDiscountType());
        invPurchaseInvoiceDTO.setDueDate(invPurchaseInvoice.getDueDate());
        invPurchaseInvoiceDTO.setPaymentType(invPurchaseInvoice.getPaymentType());
        invPurchaseInvoiceDTO.setPostFlag(invPurchaseInvoice.getPostFlag());
        invPurchaseInvoiceDTO.setSupplierInvoiceDate(invPurchaseInvoice.getSupplierInvoiceDate());
        invPurchaseInvoiceDTO.setRate(invPurchaseInvoice.getRate());
        if (invPurchaseInvoice.getOrganizationSiteId() != null) {
            InvOrganizationSiteDTO invOrganizationSiteDTO = new InvOrganizationSiteDTO();
            invOrganizationSiteDTO.setId(invPurchaseInvoice.getOrganizationSiteId().getId());
            invOrganizationSiteDTO.setName(invPurchaseInvoice.getOrganizationSiteId().getName());
            invOrganizationSiteDTO.setMobile(invPurchaseInvoice.getOrganizationSiteId().getMobile());
            if (invPurchaseInvoice.getOrganizationSiteId().getCurrencyId() != null) {
                CurrencyDTO currencyDTO = new CurrencyDTO();
                currencyDTO.setId(invPurchaseInvoice.getOrganizationSiteId().getCurrencyId().getId());
                currencyDTO.setCode(invPurchaseInvoice.getOrganizationSiteId().getCurrencyId().getCode());
                currencyDTO.setName(invPurchaseInvoice.getOrganizationSiteId().getCurrencyId().getName());
                invOrganizationSiteDTO.setCurrencyId(currencyDTO);
            }
            invPurchaseInvoiceDTO.setOrganizationSiteId(invOrganizationSiteDTO);
        }
        invPurchaseInvoiceDTO.setSupplierInvoiceNumber(invPurchaseInvoice.getSupplierInvoiceNumber());
        if (invPurchaseInvoice.getInvInventoryId() != null) {
            InvInventoryDTO invInventoryDTO = new InvInventoryDTO();
            invInventoryDTO.setId(invPurchaseInvoice.getInvInventoryId().getId());
            invInventoryDTO.setName(invPurchaseInvoice.getInvInventoryId().getName());
            invInventoryDTO.setCode(invPurchaseInvoice.getInvInventoryId().getCode());
            invPurchaseInvoiceDTO.setInvInventoryId(invInventoryDTO);
        }

        if (invPurchaseInvoice.getGalaryId() != null) {
            InvInventoryDTO invInventoryDTO = new InvInventoryDTO();
            invInventoryDTO.setId(invPurchaseInvoice.getGalaryId().getId());
            invInventoryDTO.setName(invPurchaseInvoice.getGalaryId().getName());
            invInventoryDTO.setCode(invPurchaseInvoice.getGalaryId().getCode());
            invPurchaseInvoiceDTO.setGallaryId(invInventoryDTO);
        }
        invPurchaseInvoiceDTO.setExtraCost(invPurchaseInvoice.getExtraCost());
        invPurchaseInvoiceDTO.setTaxdiscflag(invPurchaseInvoice.getTaxdiscflag());
        invPurchaseInvoiceDTO.setTaxdiscvalue(invPurchaseInvoice.getTaxdiscvalue());
        if (invPurchaseInvoice.getInvDelegatorId() != null) {
            InventoryDelegatorDTO inventoryDelegatorDTO = new InventoryDelegatorDTO();
            inventoryDelegatorDTO.setId(invPurchaseInvoice.getInvDelegatorId().getId());
            inventoryDelegatorDTO.setName(invPurchaseInvoice.getInvDelegatorId().getName());
            inventoryDelegatorDTO.setCode(invPurchaseInvoice.getInvDelegatorId().getCode());
            invPurchaseInvoiceDTO.setInvDelegatorId(inventoryDelegatorDTO);
        }
        if (invPurchaseInvoice.getGlBankId() != null) {
            GlBankDTO glBankDTO = new GlBankDTO();
            glBankDTO.setId(invPurchaseInvoice.getGlBankId().getId());
            glBankDTO.setName(invPurchaseInvoice.getGlBankId().getName());
            glBankDTO.setCode(invPurchaseInvoice.getGlBankId().getCode());
            invPurchaseInvoiceDTO.setGlBankId(glBankDTO);
        }
        invPurchaseInvoiceDTO.setTaxflag(invPurchaseInvoice.getTaxflag());
//        invPurchaseInvoiceDTO.setActualWeight(invPurchaseInvoice.getActualWeight());
//        invPurchaseInvoiceDTO.setTotalActualWeight(invPurchaseInvoice.getTotalActualWeight());
//        invPurchaseInvoiceDTO.setPriceKilo(invPurchaseInvoice.getPriceKilo());

        invPurchaseInvoiceDTO.setCreatedBy(invPurchaseInvoice.getCreatedBy() != null ? invPurchaseInvoice.getCreatedBy().getId() : null);
        invPurchaseInvoiceDTO.setCreatedDate(invPurchaseInvoice.getCreationDate());
        invPurchaseInvoiceDTO.setIndex(invPurchaseInvoice.getSerial());
        invPurchaseInvoiceDTO.setType(invPurchaseInvoice.getType());
        invPurchaseInvoiceDTO.setCompanyId(invPurchaseInvoice.getCompanyId().getId() != null ? invPurchaseInvoice.getCompanyId().getId() : null);
        invPurchaseInvoiceDTO.setCreatedByName(invPurchaseInvoice.getCreatedBy() != null ? invPurchaseInvoice.getCreatedBy().getName() : null);

        List<InvDetailDTO> invPurchaseInvoiceDetailsListDTO = invPurchaseInvoiceDetailsService.getAllInvPurchaseInvoiceDetailsByInvPurchaseInvoiceIdDTO(invPurchaseInvoiceDTO, invPurchaseInvoice.getBranchId().getId());

        invPurchaseInvoiceDTO.setInvPurchaseInvoiceDetailList(invPurchaseInvoiceDetailsListDTO);

        updateSummition(invPurchaseInvoiceDTO);

        return invPurchaseInvoiceDTO;
    }

    private InvPurchaseInvoiceDTO calculateDiscountValue(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        // لحساب الاجمالى بعد الخصم 
        // الخصم ممكن يكون نسبة او قيمة 
        try {
            if (invPurchaseInvoiceDTO != null) {
                invPurchaseInvoiceDTO.setDiscountValue(BigDecimal.ZERO);
                if (invPurchaseInvoiceDTO.getDiscountType() == 0) {
                    if (invPurchaseInvoiceDTO.getTotalNet() != null
                            && invPurchaseInvoiceDTO.getDiscount() != null
                            && invPurchaseInvoiceDTO.getTotalNet().compareTo(BigDecimal.ZERO) == 1
                            && invPurchaseInvoiceDTO.getTotalNet().compareTo(invPurchaseInvoiceDTO.getDiscount()) != -1) {
                        invPurchaseInvoiceDTO.setDiscountValue((invPurchaseInvoiceDTO.getDiscount() != null ? invPurchaseInvoiceDTO.getDiscount() : BigDecimal.ZERO).setScale(3, BigDecimal.ROUND_UP));
                    }
                } else if (Validation.isDiscountValid(invPurchaseInvoiceDTO.getDiscountValue())) {
                    invPurchaseInvoiceDTO.setDiscountValue(invPurchaseInvoiceDTO.getTotalNet().multiply(invPurchaseInvoiceDTO.getDiscount()).divide(BigDecimal.valueOf(100)));
                }
            }
        } catch (Exception e) {

        }
        return invPurchaseInvoiceDTO;
    }

    private InvPurchaseInvoiceDTO calculateTaxDiscValue(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        // لحساب ضريبة الخصم
        // تحسب ضريبة الخصم بعد حساب الاجمالة بعد الخصم
        try {

            invPurchaseInvoiceDTO.setTaxdiscvalue(BigDecimal.ZERO);

            if (invPurchaseInvoiceDTO.getTaxdiscflag() != null && invPurchaseInvoiceDTO.getTaxdiscflag()) {
                invPurchaseInvoiceDTO.setTaxdiscvalue(invPurchaseInvoiceDTO.getTotalNetAfterDiscount().multiply(BigDecimal.valueOf(0.01)));
            }

        } catch (Exception e) {

        }
        return invPurchaseInvoiceDTO;
    }

    private InvPurchaseInvoiceDTO calculateTaxValue(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        // لحساب ضريبة القيمة المضافة
        // تحسب ضريبة القيمة المضافة بعد حساب الاجمالى بعد الخصم
        try {
            if (invPurchaseInvoiceDTO.getTaxflag() != null && invPurchaseInvoiceDTO.getTaxflag()) {
                invPurchaseInvoiceDTO.setTaxvalue(invPurchaseInvoiceDTO.getTotalNetAfterDiscount().multiply(BigDecimal.valueOf(0.14)));
            } else {
                invPurchaseInvoiceDTO.setTaxvalue(BigDecimal.ZERO);
            }
        } catch (Exception e) {
//            saveError(e, "InvPurchaseInvoiceFormMB", "updateTax");

        }
        return invPurchaseInvoiceDTO;
    }

    @Override
    public InvPurchaseInvoiceDTO updateSummition(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        try {

            invPurchaseInvoiceDTO = calculateDetailValues(invPurchaseInvoiceDTO);
            invPurchaseInvoiceDTO = recalcHeadValues(invPurchaseInvoiceDTO);
        } catch (Exception e) {
//            saveError(e, "InvPurchaseInvoiceFormMB", "updateSummition");
        }

        return invPurchaseInvoiceDTO;
    }

    private InvPurchaseInvoiceDTO calculateDetailValues(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        if (invPurchaseInvoiceDTO != null) {
            invPurchaseInvoiceDTO.setTotalNet(BigDecimal.ZERO);
            invPurchaseInvoiceDTO.setTotalQuatity(BigDecimal.ZERO);
            invPurchaseInvoiceDTO.setTotalWeight(BigDecimal.ZERO);
            if (invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList() != null && !invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList().isEmpty()) {
                List<InvDetailDTO> invPurchaseInvoiceDetailDTOList = new ArrayList<>();

                for (InvDetailDTO detailEntity : invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList()) {
                    // حساب الاجماليات الخاصة بكل تفصيلى على حده

                    detailEntity = invPurchaseInvoiceDetailsService.calculateDetailTotalsRow(detailEntity, invPurchaseInvoiceDTO);

                    invPurchaseInvoiceDetailDTOList.add(detailEntity);
                    // حساب إجماليات الفاتورة بعد حساب كل سطر تفصيلى
                    invPurchaseInvoiceDTO = claculateHeadTotalFromDetail(detailEntity, invPurchaseInvoiceDTO);
                }
                invPurchaseInvoiceDTO.setInvPurchaseInvoiceDetailList(invPurchaseInvoiceDetailDTOList);
            }
        }
        return invPurchaseInvoiceDTO;
    }

    @Override
    public InvPurchaseInvoiceDTO recalcHeadValues(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        resetHeadTotals(invPurchaseInvoiceDTO);
        invPurchaseInvoiceDTO = calculateDiscountValue(invPurchaseInvoiceDTO);
        invPurchaseInvoiceDTO.setTotalNetAfterDiscount((invPurchaseInvoiceDTO.getTotalNet().subtract(invPurchaseInvoiceDTO.getDiscountValue() != null ? invPurchaseInvoiceDTO.getDiscountValue() : BigDecimal.ZERO)).setScale(3, BigDecimal.ROUND_UP));
        invPurchaseInvoiceDTO = calculateTaxDiscValue(invPurchaseInvoiceDTO);
        invPurchaseInvoiceDTO = calculateTaxValue(invPurchaseInvoiceDTO);
        invPurchaseInvoiceDTO.setFinalNet((invPurchaseInvoiceDTO.getTotalNetAfterDiscount().add(invPurchaseInvoiceDTO.getTaxvalue() != null ? invPurchaseInvoiceDTO.getTaxvalue() : BigDecimal.ZERO).subtract(invPurchaseInvoiceDTO.getTaxdiscvalue() != null ? invPurchaseInvoiceDTO.getTaxdiscvalue() : BigDecimal.ZERO)).setScale(3, BigDecimal.ROUND_UP));
        return invPurchaseInvoiceDTO;
    }

    private void resetHeadTotals(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        invPurchaseInvoiceDTO.setTotalWithTaxValue(BigDecimal.ZERO);
        invPurchaseInvoiceDTO.setTaxdiscvalue(BigDecimal.ZERO);
        invPurchaseInvoiceDTO.setTaxvalue(BigDecimal.ZERO);
        invPurchaseInvoiceDTO.setDiscountValue(BigDecimal.ZERO);

        invPurchaseInvoiceDTO.setTotalNetAfterDiscount(BigDecimal.ZERO);
        invPurchaseInvoiceDTO.setFinalNet(BigDecimal.ZERO);
    }

    private InvPurchaseInvoiceDTO claculateHeadTotalFromDetail(InvDetailDTO detailEntity, InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        if (invPurchaseInvoiceDTO.getCompanyActivity().equals(CompanyActivityClassEnum.Alumetal)) {
            invPurchaseInvoiceDTO = alumetalActivity(invPurchaseInvoiceDTO, detailEntity);
        }
        invPurchaseInvoiceDTO.setTotalQuatity(invPurchaseInvoiceDTO.getTotalQuatity().add(detailEntity.getQuantity() != null ? detailEntity.getQuantity() : BigDecimal.ZERO));
        invPurchaseInvoiceDTO.setTotalNet((invPurchaseInvoiceDTO.getTotalNet().add(detailEntity.getNet() != null ? detailEntity.getNet() : BigDecimal.ZERO)).setScale(3, BigDecimal.ROUND_UP));
        return invPurchaseInvoiceDTO;
    }

    private InvPurchaseInvoiceDTO alumetalActivity(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO, InvDetailDTO detailEntity) {
        if (invPurchaseInvoiceDTO.getCompanyActivity().equals(CompanyActivityClassEnum.Alumetal)) {
            BigDecimal weight;
            weight = detailEntity.getWeight() == null ? BigDecimal.ZERO : detailEntity.getWeight();
            if (weight.compareTo(BigDecimal.ZERO) > 0) {
                if (detailEntity.getQuantity() != null) {
                    if (weight.compareTo(BigDecimal.ZERO) == 0) {
                        invPurchaseInvoiceDTO.setTotalWeight(invPurchaseInvoiceDTO.getTotalWeight().add(weight.multiply(detailEntity.getQuantity())));
                    } else {
                        invPurchaseInvoiceDTO.setTotalWeight(invPurchaseInvoiceDTO.getTotalWeight().add(weight));
                    }
                } else {
                    invPurchaseInvoiceDTO.setTotalWeight(invPurchaseInvoiceDTO.getTotalWeight().add(weight));
                }
            } else {
                invPurchaseInvoiceDTO.setTotalWeight(invPurchaseInvoiceDTO.getTotalWeight().add(BigDecimal.ZERO));
            }
        }
        return invPurchaseInvoiceDTO;
    }

    private List<InvPurchaseInvoiceDTO> mapToDTOList(List<InvPurchaseInvoice> invPurchaseInvoiceList, TobyUser tobyUser) {
        List<InvPurchaseInvoiceDTO> invPurchaseInvoiceDTOList = new ArrayList<>();
        for (InvPurchaseInvoice invPurchaseInvoice : invPurchaseInvoiceList) {
            invPurchaseInvoiceDTOList.add(mapToDTO(invPurchaseInvoice));
        }
        return invPurchaseInvoiceDTOList;
    }

    @Override
    public InvPurchaseInvoiceDTO updateRate(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        try {
            CurrencyOperationDTO currencyOperation = currencyOperationService.getRatesByDatesDTO(invPurchaseInvoiceDTO.getCurrencyId().getId(), invPurchaseInvoiceDTO.getDate(), invPurchaseInvoiceDTO.getCompanyId());
            invPurchaseInvoiceDTO.setRate(currencyOperation == null ? BigDecimal.ONE : currencyOperation.getRate());
        } catch (Exception e) {
//            saveError(e, "InvPurchaseInvoiceFormMB", "updateRate");
        }
        return invPurchaseInvoiceDTO;
    }

    @Override
    public String validateloginDistributedScreens(UserDataDTO userDataDTO, ScreenNameClassEnum screenNameClassEnum) {
        StringBuilder error = new StringBuilder();
        List<InvInventoryDTO> invInventoryDTOList = userDataDTO.getInventoryDTOList();
        if (invInventoryDTOList == null || invInventoryDTOList.isEmpty()) {
            error.append("\n يجب اعطاء صلاحية للتعامل مع المخازن");
        }

        List<GlBankDTO> glBankList = userDataDTO.getGlBankDTOList();
        if (glBankList == null || glBankList.isEmpty()) {
            error.append("\n يجب اعطاء صلاحية للتعامل مع الخزائن");
        }
        List<InvOrganizationSiteDTO> organizationSiteList = null;
        if (ScreenNameClassEnum.invpurchaseinvoiceform.equals(screenNameClassEnum)) {
            organizationSiteList = organizationSiteService.getorganizationSiteByBranchIdDTO(userDataDTO.getBranchId(), true, 1);
        } else if (ScreenNameClassEnum.invsalesinvoicedtoform.equals(screenNameClassEnum)) {
            organizationSiteList = organizationSiteService.getorganizationSiteByBranchIdDTO(userDataDTO.getBranchId(), true, 0);
        }

        if (organizationSiteList == null || organizationSiteList.isEmpty()) {
            error.append("\n يجب انشاء موردين ");
        }

        List<CurrencyDTO> currencyDTOList = currencyService.getAllCurrenciesWithLocalCurrencyHavingRatesByCompanyIdDTO(userDataDTO.getCompanyId()); // العملات
        if (currencyDTOList == null || currencyDTOList.isEmpty()) {
            error.append("\n يجب انشاء عملة للتعامل بها");
        }
        return error.toString();
    }

    public InvPurchaseInvoiceDTO deleteInvPurchaseInvoiceDetail(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        try {
            if (invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected().getId() != null) {
                invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsDeletedList().add(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected());
            }
            invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList().remove(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsSelected());
            invPurchaseInvoiceDTO = updateSummition(invPurchaseInvoiceDTO);
        } catch (Exception e) {
//            saveError(e, "InvPurchaseInvoiceFormMB", "deleteInvPurchaseInvoiceDetail");
        }
        return invPurchaseInvoiceDTO;
    }

    @Override
    public InvPurchaseInvoiceDTO putCurrency(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        try {
            if (invPurchaseInvoiceDTO.getOrganizationSiteId() != null && invPurchaseInvoiceDTO.getOrganizationSiteId().getCurrencyId() != null) {
                invPurchaseInvoiceDTO.setCurrencyId(invPurchaseInvoiceDTO.getOrganizationSiteId().getCurrencyId());
                invPurchaseInvoiceDTO = updateRate(invPurchaseInvoiceDTO);

                invPurchaseInvoiceDTO = updateSummition(invPurchaseInvoiceDTO);
            } else {
                invPurchaseInvoiceDTO.setMsg("هذا المورد ليس له عملة!");
            }
        } catch (Exception e) {
            invPurchaseInvoiceDTO.setMsg(e.toString());
        }
        return invPurchaseInvoiceDTO;
    }

    //mapToEntity
    private InvPurchaseInvoice mapToEntity(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO, TobyUser tobyUser) {

        InvPurchaseInvoice invPurchaseInvoice = new InvPurchaseInvoice();
        invPurchaseInvoice.setPricetype(invPurchaseInvoiceDTO.getPricetype());
        if (invPurchaseInvoiceDTO.getInvInventoryId() != null) {
            InvInventory inventory = new InvInventory();
            inventory.setId(invPurchaseInvoiceDTO.getInvInventoryId().getId());
            invPurchaseInvoice.setInvInventoryId(inventory);
        }

        if (invPurchaseInvoiceDTO.getGallaryId() != null) {
            InvInventory inventory = new InvInventory();
            inventory.setId(invPurchaseInvoiceDTO.getGallaryId().getId());
            invPurchaseInvoice.setGalaryId(inventory);
        }

        if (invPurchaseInvoiceDTO.getAdminUnitId() != null) {
            GlAdminUnit glAdminUnit = new GlAdminUnit();
            glAdminUnit.setId(invPurchaseInvoiceDTO.getAdminUnitId().getId());
            invPurchaseInvoice.setAdminUnitId(glAdminUnit);
        }
        if (invPurchaseInvoiceDTO.getCostCenterId() != null) {
            CostCenter costCenter = new CostCenter();
            costCenter.setId(invPurchaseInvoiceDTO.getCostCenterId().getId());
            invPurchaseInvoice.setCostCenterId(costCenter);
        }
        if (invPurchaseInvoiceDTO.getCurrencyId() != null) {
            Currency currency = new Currency();
            currency.setId(invPurchaseInvoiceDTO.getCurrencyId().getId());
            invPurchaseInvoice.setCurrencyId(currency);
        }

        if (invPurchaseInvoiceDTO.getOrganizationSiteId() != null) {
            InvOrganizationSite invOrganizationSite = new InvOrganizationSite();
            invOrganizationSite.setId(invPurchaseInvoiceDTO.getOrganizationSiteId().getId());
            invOrganizationSite.setName(invPurchaseInvoiceDTO.getOrganizationSiteId().getName());
            invPurchaseInvoice.setOrganizationSiteId(invOrganizationSite);
        }

        if (invPurchaseInvoiceDTO.getAccountId() != null) {
            GlAccount glAccount = new GlAccount();
            glAccount.setId(invPurchaseInvoiceDTO.getAccountId());
            invPurchaseInvoice.setAccountId(glAccount);
        }

        if (invPurchaseInvoiceDTO.getGlBankId() != null) {
            GlBank glBank = new GlBank();
            glBank.setId(invPurchaseInvoiceDTO.getGlBankId().getId());
            invPurchaseInvoice.setGlBankId(glBank);
        }
        if (invPurchaseInvoiceDTO.getInvDelegatorId() != null) {
            InventoryDelegator inventoryDelegator = new InventoryDelegator();
            inventoryDelegator.setId(invPurchaseInvoiceDTO.getInvDelegatorId().getId());
            invPurchaseInvoice.setInvDelegatorId(inventoryDelegator);
        }

        if (invPurchaseInvoiceDTO.getScreenName().equals(ScreenNameClassEnum.invpurchaseinvoiceform)) {
            invPurchaseInvoice.setType(false);
        }

        invPurchaseInvoice.setId(invPurchaseInvoiceDTO.getId());
        invPurchaseInvoice.setDate(invPurchaseInvoiceDTO.getDate());
        invPurchaseInvoice.setDiscount(invPurchaseInvoiceDTO.getDiscount());
        invPurchaseInvoice.setDiscountType(invPurchaseInvoiceDTO.getDiscountType());
        invPurchaseInvoice.setDueDate(invPurchaseInvoiceDTO.getDueDate());
        invPurchaseInvoice.setRemarks(invPurchaseInvoiceDTO.getRemarks());
        invPurchaseInvoice.setPostFlag(0);
        invPurchaseInvoice.setPaymentType(invPurchaseInvoiceDTO.getPaymentType() > 0 ? invPurchaseInvoiceDTO.getPaymentType() : 0);
        invPurchaseInvoice.setTaxflag(invPurchaseInvoiceDTO.getTaxflag());
        invPurchaseInvoice.setRate(invPurchaseInvoiceDTO.getRate() != null ? invPurchaseInvoiceDTO.getRate() : null);
        invPurchaseInvoice.setSupplierInvoiceNumber(invPurchaseInvoiceDTO.getSupplierInvoiceNumber());
        invPurchaseInvoice.setSupplierInvoiceDate(invPurchaseInvoiceDTO.getSupplierInvoiceDate());
        invPurchaseInvoice.setExtraCost(invPurchaseInvoiceDTO.getExtraCost() != null ? invPurchaseInvoiceDTO.getExtraCost() : BigDecimal.ZERO);
        invPurchaseInvoice.setTaxdiscflag(invPurchaseInvoiceDTO.getTaxdiscflag());
        invPurchaseInvoice.setTaxdiscvalue(invPurchaseInvoiceDTO.getTaxdiscvalue());

        if (invPurchaseInvoiceDTO.getCreatedBy() != null) {
            TobyUser user = new TobyUser();
            user.setId(invPurchaseInvoiceDTO.getCreatedBy());
            invPurchaseInvoice.setCreatedBy(user);
            invPurchaseInvoice.setCreationDate(invPurchaseInvoiceDTO.getCreatedDate());
            invPurchaseInvoice.setModifiedBy(tobyUser);
            invPurchaseInvoice.setModificationDate(new Date());
            if (invPurchaseInvoiceDTO.getCompanyId() != null) {
                TobyCompany company = new TobyCompany();
                company.setId(invPurchaseInvoiceDTO.getCompanyId());
                invPurchaseInvoice.setCompanyId(company);
            }

            if (invPurchaseInvoiceDTO.getBranchId() != null) {
                Branch branch = new Branch();
                branch.setId(invPurchaseInvoiceDTO.getBranchId());
                invPurchaseInvoice.setBranchId(branch);
            }
        } else {
            invPurchaseInvoice.setCreatedBy(tobyUser);
            invPurchaseInvoice.setCreationDate(new Date());
            invPurchaseInvoice.setCompanyId(tobyUser.getCompanyId());
            invPurchaseInvoice.setBranchId(tobyUser.getBranchId());
        }

        /// Get Serial ///
        if (invPurchaseInvoiceDTO.getId() == null) {
            Map<String, Object> params = new HashMap<>();
            params.put("branchId", tobyUser.getBranchId().getId());
            Integer serialmax = 0;
            synchronized (serialmax) {
                try {
                    serialmax = dao.findEntityByQuery("SELECT MAX(g.serial) FROM InvPurchaseInvoice g  WHERE g.branchId.id =:branchId ", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (serialmax == null) {
                serialmax = 0;
            }
            invPurchaseInvoice.setSerial(serialmax + 1);
        } else {
            invPurchaseInvoice.setSerial(invPurchaseInvoiceDTO.getSerial());
        }
        return invPurchaseInvoice;
    }

    public InvPurchaseInvoiceDTO validateSave(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        StringBuilder error = new StringBuilder();
//        if (invPurchaseInvoiceDTO.getInvInventoryId() == null) {
//            error.append("\n يجب أختيار مخزن");
//        }
        if (invPurchaseInvoiceDTO.getPricetype() == null) {
            error.append("\n يجب أختيار تحديد سعر ");
        }
        if (invPurchaseInvoiceDTO.getDate() == null) {
            error.append("\n يجب أختيارالتاريخ  ");
        }
        if (invPurchaseInvoiceDTO.getGlBankId() == null) {
            error.append("\n يجب أختيارالخزنه  ");
        }
        if (invPurchaseInvoiceDTO.getGallaryId() == null) {
            error.append("\n يجب أختيارالمعرض   ");
        }
        if (invPurchaseInvoiceDTO.getCurrencyId() == null) {
            error.append("\n يجب أختيار عمله");
        }

        if (invPurchaseInvoiceDTO.getOrganizationSiteId() == null) {
            error.append("\n يجب أختيار عميل / مورد");
        }

        if (!error.toString().isEmpty()) {
            invPurchaseInvoiceDTO.setMsg(error.toString());
        }
        return invPurchaseInvoiceDTO;
    }

    @Override
    public InvPurchaseInvoiceDTO save(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO, TobyUser tobyUser) {
        try {
            invPurchaseInvoiceDTO = validateSave(invPurchaseInvoiceDTO);

            InvPurchaseInvoice invPurchaseInvoice;
            if (invPurchaseInvoiceDTO != null && (invPurchaseInvoiceDTO.getMsg() == null || invPurchaseInvoiceDTO.getMsg().isEmpty())) {
                invPurchaseInvoice = mapToEntity(invPurchaseInvoiceDTO, tobyUser);
                if (invPurchaseInvoice.getId() != null) {
                    //بعمل حفظ فى حالة التعديل
                    invPurchaseInvoice = dao.updateEntity(invPurchaseInvoice);
                } else {
                    // فى حالة ان الفاتورة جديدة بروح اجيب السيريال الاول 
                    Integer serial = genericService.getMaxSerialForInvoice(InvPurchaseInvoice.class, invPurchaseInvoice.getBranchId().getId(), invPurchaseInvoice.getInvInventoryId().getId(), invPurchaseInvoice.getType());
                    // بجيب السيريال الخاص بالفواتير الضريبية
                    if (invPurchaseInvoice.getTaxflag()) {
                        Integer serialTax = getMaxSerialTaxForInvoice(invPurchaseInvoice.getBranchId().getId(), invPurchaseInvoice.getInvInventoryId().getId(), invPurchaseInvoice.getType(), invPurchaseInvoice.getTaxFlagFinal() == null ? Boolean.FALSE : invPurchaseInvoice.getTaxFlagFinal());
                        invPurchaseInvoice.setSerialTax(serialTax);
                    }
                    invPurchaseInvoice.setSerial(serial);

                    invPurchaseInvoice = dao.updateEntity(invPurchaseInvoice);
                }

                invPurchaseInvoiceDetailsService.deleteInvPurchaseInvoiceDetailListDTO(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailsDeletedList());

                String error = invPurchaseInvoiceDetailsService.addInvPurchaseInvoiceDetailsDTO(invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList(), invPurchaseInvoice.getId(), tobyUser);

                invPurchaseInvoiceDTO = mapToDTO(invPurchaseInvoice);

                if (error != null && !error.isEmpty()) {
                    invPurchaseInvoice.setMsg(error);
                    dao.rollbackQuery();
                }
            }
        } catch (Exception e) {
            invPurchaseInvoiceDTO.setMsg(e.toString());

        }

        return invPurchaseInvoiceDTO;
    }

    public InvPurchaseInvoiceDTO claculateDetailPriceForPriceType(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO) {
        List<InvDetailDTO> detailDTOList = new ArrayList<>();
        for (InvDetailDTO detailEntity : invPurchaseInvoiceDTO.getInvPurchaseInvoiceDetailList()) {
            // حساب الاجماليات الخاصة بكل تفصيلى على حده
            detailEntity = invPurchaseInvoiceDetailsService.validateItems(detailEntity, invPurchaseInvoiceDTO.getPricetype());
            detailEntity = invPurchaseInvoiceDetailsService.validateQuantityColum(detailEntity);
            detailEntity.setDiscount(BigDecimal.ZERO);
            detailDTOList.add(detailEntity);
        }
        invPurchaseInvoiceDTO.setInvPurchaseInvoiceDetailList(detailDTOList);
        invPurchaseInvoiceDTO = updateSummition(invPurchaseInvoiceDTO);
        return invPurchaseInvoiceDTO;
    }

    @Override
    public Date sumDateToDueDate(Date date, Date dueDate, Integer duePeroid) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, (duePeroid != null ? duePeroid : 0));
        return dueDate = cal.getTime();
    }

    @Override
    public Integer subtractDatefromDueDate(Date date, Date dueDate, Integer duePeroid) {
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ld1 = dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();     //2
        Duration duration = Duration.between(ld.atStartOfDay(), ld1.atStartOfDay());
        System.out.println("duration.toDays(); " + duration.toDays());
        if ((int) (long) duration.toDays() < 0) {
            duePeroid = 0;
            sumDateToDueDate(date, dueDate, duePeroid);
        } else {

            duePeroid = ((int) (long) duration.toDays());
        }
        return duePeroid;
    }

    @Override
    public InvOrganizationSiteDTO addNewCustomer(InvOrganizationSiteDTO invOrganizationSiteDTO, List<InvOrganizationSiteDTO> invOrganizationSiteDTOList, TobyUser tobyUser) {

        if (invOrganizationSiteDTO != null && invOrganizationSiteDTO.getName() != null) {
            invOrganizationSiteDTO.setType(0);
            invOrganizationSiteDTO.setActive(1);
            organizationSiteService.addOrganizationSiteDTO(invOrganizationSiteDTO, tobyUser);
            invOrganizationSiteDTOList.add(0, invOrganizationSiteDTO);

        }
        return invOrganizationSiteDTO;
    }
}

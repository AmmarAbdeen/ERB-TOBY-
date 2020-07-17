/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.dto.InvDetailDTO;
import com.toby.dto.InvPurchaseInvoiceDTO;
import com.toby.entity.GlYear;
import com.toby.entity.InvPurchaseInvoiceDetail;
import com.toby.entity.TobyUser;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author WIN7
 */
@Remote
public interface InvPurchaseInvoiceDetailsService {

    public List<InvPurchaseInvoiceDetail> getAllInvPurchaseInvoiceDetailByBranchId(Integer branchId);

    public List<InvPurchaseInvoiceDetail> getAllInvPurchaseInvoiceDetailByBranchIdByDate(Integer branchId);

    public void addInvPurchaseInvoiceDetails(InvPurchaseInvoiceDetail invPurchaseInvoiceDetail);

    public void deleteInvPurchaseInvoiceDetails(InvPurchaseInvoiceDetail invPurchaseInvoiceDetail);

    public InvPurchaseInvoiceDetail updateInvPurchaseInvoiceDetails(InvPurchaseInvoiceDetail invPurchaseInvoiceDetail);

    public List<InvPurchaseInvoiceDetail> getAllInvPurchaseInvoiceDetailsByInvPurchaseInvoiceId(Integer invPurchaseInvoiceId);

    public List<InvPurchaseInvoiceDetail> getAllInvPurchaseInvoiceDetailsByInvPurchaseInvoiceIdWithStatus(Integer invPurchaseInvoiceId);

    public InvPurchaseInvoiceDetail findInvPurchaseInvoiceDetailsById(Integer invPurchaseInvoiceDetailsId);

    public BigDecimal getLastCostItem(Integer itemId, GlYear glYear, Integer branchId);

    public void updateCostAvarage(Integer branchId);

    public void reCalcCostAvarage(Integer branchId);

    public List<InvDetailDTO> getAllInvPurchaseInvoiceDetailsByInvPurchaseInvoiceIdDTO(InvPurchaseInvoiceDTO invPurchaseInvoice, Integer branchId);

    public InvDetailDTO calculateDetailTotalsRow(InvDetailDTO detailEntity, InvPurchaseInvoiceDTO invPurchaseInvoiceDTO);

    public void deleteInvPurchaseInvoiceDetailListDTO(List<InvDetailDTO> invPurchaseInvoiceDetailList);

    public String addInvPurchaseInvoiceDetailsDTO(List<InvDetailDTO> invPurchaseInvoiceDetailList, Integer invPurchaseInvoiceId, TobyUser tobyUser);

    public InvPurchaseInvoiceDTO addInvPurchaseInvoiceDetail(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO, Integer index);

    public InvDetailDTO validateDiscountColumn(InvDetailDTO detailDTO, Integer priceType);

    public InvDetailDTO validatePriceColumn(InvDetailDTO detailDTO, Integer priceType);

    public InvDetailDTO validateQuantityColum(InvDetailDTO detailDTO);

    public InvDetailDTO validateItems(InvDetailDTO detailDTO, Integer priceType);

    public InvDetailDTO validateNumberColum(InvDetailDTO detailDTO);

    public InvDetailDTO validateUnitColum(InvDetailDTO detailDTO, Integer priceType);

    public InvDetailDTO validateBounceColum(InvDetailDTO detailDTO);

}

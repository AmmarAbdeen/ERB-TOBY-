/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.entity.TobyUser;
import com.toby.core.UserDataDTO;
import com.toby.define.ScreenNameClassEnum;
import com.toby.dto.InvOrganizationSiteDTO;
import com.toby.dto.InvPurchaseInvoiceDTO;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author WIN7
 */
@Remote
public interface InvPurchaseInvoiceService_1 {

    public InvPurchaseInvoiceDTO findInvPurchaseInvoiceById(Integer invPurchaseInvoiceId, TobyUser tobyUser);

    public Integer getMaxSerialTaxForInvoice(Integer branchId, Integer inventoryId, Boolean type, Boolean taxFalgFinal);

    public InvPurchaseInvoiceDTO updateRate(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO);

    public String validateloginDistributedScreens(UserDataDTO userDataDTO, ScreenNameClassEnum screenNameClassEnum);

    public InvPurchaseInvoiceDTO updateSummition(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO);

    public InvPurchaseInvoiceDTO recalcHeadValues(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO);

    public InvPurchaseInvoiceDTO putCurrency(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO);

    public InvPurchaseInvoiceDTO save(InvPurchaseInvoiceDTO invPurchaseInvoiceDTO, TobyUser tobyUser);

    public Date sumDateToDueDate(Date date, Date dueDate, Integer duePeroid);

    public Integer subtractDatefromDueDate(Date date, Date dueDate, Integer duePeroid);
    
     public InvOrganizationSiteDTO addNewCustomer(InvOrganizationSiteDTO invOrganizationSiteDTO, List<InvOrganizationSiteDTO> invOrganizationSiteDTOList, TobyUser tobyUser);

}

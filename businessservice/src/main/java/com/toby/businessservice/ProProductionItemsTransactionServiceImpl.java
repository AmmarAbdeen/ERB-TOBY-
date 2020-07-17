/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.core.GenericDAO;
import com.toby.dto.InvItemDTO;
import com.toby.dto.InvPurchaseInvoiceDTO1;
import com.toby.dto.InvPurchaseInvoiceDetailDTO;
import com.toby.dto.ProProductionItemsTransactionDTO;
import com.toby.dto.ProProductionStagesDTO;
import com.toby.dto.ProProductionTransactionDTO;
import com.toby.dto.ProProductionTransactionDetailDetailViewDTO;
import com.toby.entity.Branch;
import com.toby.entity.InvPurchaseInvoiceDetail;
import com.toby.entity.ProProductionItemsTransaction;
import com.toby.entity.ProProductionStages;
import com.toby.entity.ProProductionTransaction;
import com.toby.entity.TobyCompany;
import com.toby.entity.TobyUser;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author H
 */
@Stateless
public class ProProductionItemsTransactionServiceImpl implements ProProductionItemsTransactionService {

    @EJB
    GenericDAO dao;

    @Override
    public ProProductionItemsTransactionDTO deleteproProductionItemsTransactionDTO(ProProductionItemsTransactionDTO proProductionItemsTransactionDTO, TobyUser tobyUser) {
        ProProductionItemsTransaction proProductionItemsTransaction = mapToEntity(proProductionItemsTransactionDTO, tobyUser);
        proProductionItemsTransaction.setIsDeleted(1);
        dao.updateEntity(proProductionItemsTransaction);

        return mapToDTO(proProductionItemsTransaction);

    }

    public ProProductionItemsTransaction mapToEntity(ProProductionItemsTransactionDTO proProductionItemsTransactionDTO, TobyUser tobyUser) {
        ProProductionItemsTransaction proProductionItemsTransaction = new ProProductionItemsTransaction();
        proProductionItemsTransaction.setId(proProductionItemsTransactionDTO.getId());
        proProductionItemsTransaction.setDate(proProductionItemsTransactionDTO.getDate());
        proProductionItemsTransaction.setNumber(proProductionItemsTransactionDTO.getNumber());
        proProductionItemsTransaction.setProductionStageCost(proProductionItemsTransactionDTO.getProductionStageCost());
        proProductionItemsTransaction.setSerial(proProductionItemsTransactionDTO.getSerial());
        proProductionItemsTransaction.setStatus(proProductionItemsTransactionDTO.getStatus());

        if (proProductionItemsTransactionDTO.getInvPurchaseInvoiceDetailId() != null) {
            InvPurchaseInvoiceDetail invPurchaseInvoiceDetail = new InvPurchaseInvoiceDetail();
            invPurchaseInvoiceDetail.setId(proProductionItemsTransactionDTO.getInvPurchaseInvoiceDetailId().getId());
            proProductionItemsTransaction.setInvPurchaseInvoiceDetailId(invPurchaseInvoiceDetail);
        }

        if (proProductionItemsTransactionDTO.getProProductionStagesId() != null) {
            ProProductionStages proProductionStages = new ProProductionStages();
            proProductionStages.setId(proProductionItemsTransactionDTO.getProProductionStagesId().getId());
            proProductionItemsTransaction.setProProductionStagesId(proProductionStages);
        }
        if (proProductionItemsTransactionDTO.getProProductionTransactionId() != null) {
            ProProductionTransaction proProductionTransaction = new ProProductionTransaction();
            proProductionTransaction.setId(proProductionItemsTransactionDTO.getProProductionTransactionId().getId());
            proProductionItemsTransaction.setProProductionTransactionId(proProductionTransaction);
        }

        return proProductionItemsTransaction;
    }

    @Override
    public ProProductionItemsTransaction saveProProductionItemsTransaction(ProProductionTransactionDetailDetailViewDTO proProductionTransactionDetailDetailViewDTOSelected, ProProductionTransactionDTO proProductionTransactionDTO, TobyUser tobyUser) {
        ProProductionItemsTransaction proProductionItemsTransaction = mapToEntity1(proProductionTransactionDetailDetailViewDTOSelected, proProductionTransactionDTO, tobyUser);
        dao.updateEntity(proProductionItemsTransaction);
        return proProductionItemsTransaction;
    }

    public ProProductionItemsTransaction mapToEntity1(ProProductionTransactionDetailDetailViewDTO proProductionTransactionDetailDetailViewDTOSelected, ProProductionTransactionDTO proProductionTransactionDTO, TobyUser tobyUser) {
        ProProductionItemsTransaction proProductionItemsTransaction = new ProProductionItemsTransaction();
        proProductionItemsTransaction.setSerial(1);
        proProductionItemsTransaction.setNumber(proProductionTransactionDetailDetailViewDTOSelected.getNumberExcute());
        proProductionItemsTransaction.setStatus(1);
        proProductionItemsTransaction.setDate(new Date());
        proProductionItemsTransaction.setProductionStageCost(proProductionTransactionDetailDetailViewDTOSelected.getCost());

        if (proProductionTransactionDetailDetailViewDTOSelected.getId() != null) {
            InvPurchaseInvoiceDetail invPurchaseInvoiceDetail = new InvPurchaseInvoiceDetail();
            invPurchaseInvoiceDetail.setId(proProductionTransactionDetailDetailViewDTOSelected.getId());
            proProductionItemsTransaction.setInvPurchaseInvoiceDetailId(invPurchaseInvoiceDetail);
        }
        if (proProductionTransactionDTO != null) {
            ProProductionStages proProductionStages = new ProProductionStages();
            proProductionStages.setId(proProductionTransactionDTO.getProProductionId());
            proProductionItemsTransaction.setProProductionStagesId(proProductionStages);
        }
        if (proProductionTransactionDetailDetailViewDTOSelected.getCreatedBy() != null) {

            TobyUser user = new TobyUser();
            user.setId(proProductionTransactionDetailDetailViewDTOSelected.getCreatedBy());
            proProductionItemsTransaction.setCreatedBy(user);
            proProductionItemsTransaction.setCreationDate(proProductionTransactionDetailDetailViewDTOSelected.getCreatedDate());
            proProductionItemsTransaction.setModifiedBy(tobyUser);
            proProductionItemsTransaction.setModificationDate(new Date());
            if (proProductionTransactionDetailDetailViewDTOSelected.getCompanyId() != null) {
                TobyCompany company = new TobyCompany();
                company.setId(proProductionTransactionDetailDetailViewDTOSelected.getCompanyId());
                proProductionItemsTransaction.setCompanyId(company);
            }

            if (proProductionTransactionDetailDetailViewDTOSelected.getBranchId() != null) {
                Branch branch = new Branch();
                branch.setId(proProductionTransactionDetailDetailViewDTOSelected.getBranchId());
                proProductionItemsTransaction.setBranchId(branch);
            }
        } else {
            proProductionItemsTransaction.setCreatedBy(tobyUser);
            proProductionItemsTransaction.setCreationDate(new Date());
            proProductionItemsTransaction.setCompanyId(tobyUser.getCompanyId());
            proProductionItemsTransaction.setBranchId(tobyUser.getBranchId());
        }

        return proProductionItemsTransaction;
    }

    @Override
    public List<ProProductionItemsTransactionDTO> getProProductionItemsTransaction(TobyUser tobyUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", tobyUser.getBranchId().getId());
        List<ProProductionItemsTransaction> proProductionItemsTransaction = dao.findListByQuery("SELECT e FROM ProProductionItemsTransaction e where e.branchId.id =:branchId and( e.isDeleted != 1 or e.isDeleted is NULL )", params);
        return mapToDTOList(proProductionItemsTransaction, tobyUser);
    }

    public List<ProProductionItemsTransactionDTO> mapToDTOList(List<ProProductionItemsTransaction> ProProductionItemsTransactionlist, TobyUser tobyUser) {
        List<ProProductionItemsTransactionDTO> invInventoryTransactionDetailDTOList = new ArrayList<>();
        for (ProProductionItemsTransaction proProductionItemsTransaction : ProProductionItemsTransactionlist) {
            invInventoryTransactionDetailDTOList.add(mapToDTO(proProductionItemsTransaction));
        }
        return invInventoryTransactionDetailDTOList;
    }

    public ProProductionItemsTransactionDTO mapToDTO(ProProductionItemsTransaction proProductionItemsTransaction) {

        ProProductionItemsTransactionDTO proProductionItemsTransactionDTO = new ProProductionItemsTransactionDTO();

        proProductionItemsTransactionDTO.setId(proProductionItemsTransaction.getId());
        proProductionItemsTransactionDTO.setCreatedDate(proProductionItemsTransaction.getCreationDate());
        proProductionItemsTransactionDTO.setDate(proProductionItemsTransaction.getDate());
        proProductionItemsTransactionDTO.setIndex(proProductionItemsTransaction.getId());

        if (proProductionItemsTransaction.getInvPurchaseInvoiceDetailId() != null) {
            InvPurchaseInvoiceDetailDTO invPurchaseInvoiceDetailDTO = new InvPurchaseInvoiceDetailDTO();
            invPurchaseInvoiceDetailDTO.setId(proProductionItemsTransaction.getInvPurchaseInvoiceDetailId().getId());
            if (proProductionItemsTransaction.getInvPurchaseInvoiceDetailId().getInvPurchaseInvoiceId() != null) {
                InvPurchaseInvoiceDTO1 invPurchaseInvoiceDTO = new InvPurchaseInvoiceDTO1();
                invPurchaseInvoiceDTO.setId(proProductionItemsTransaction.getInvPurchaseInvoiceDetailId().getInvPurchaseInvoiceId().getId());
                invPurchaseInvoiceDTO.setSerial(proProductionItemsTransaction.getInvPurchaseInvoiceDetailId().getInvPurchaseInvoiceId().getSerial());
                invPurchaseInvoiceDetailDTO.setInvPurchaseInvoiceId(invPurchaseInvoiceDTO);
            }
            if (proProductionItemsTransaction.getInvPurchaseInvoiceDetailId().getItemId() != null) {
                InvItemDTO invItemDTO = new InvItemDTO();
                invItemDTO.setId((proProductionItemsTransaction.getInvPurchaseInvoiceDetailId().getItemId().getId()));
                invItemDTO.setName((proProductionItemsTransaction.getInvPurchaseInvoiceDetailId().getItemId().getName()));
                invPurchaseInvoiceDetailDTO.setItemId(invItemDTO);
            }
            invPurchaseInvoiceDetailDTO.setId(proProductionItemsTransaction.getInvPurchaseInvoiceDetailId().getId());
            proProductionItemsTransactionDTO.setInvPurchaseInvoiceDetailId(invPurchaseInvoiceDetailDTO);
        }

        proProductionItemsTransactionDTO.setNumber(proProductionItemsTransaction.getNumber());

        if (proProductionItemsTransaction.getProProductionStagesId() != null) {
            ProProductionStagesDTO proProductionStagesDTO = new ProProductionStagesDTO();
            proProductionStagesDTO.setId(proProductionItemsTransaction.getProProductionStagesId().getId());
            proProductionStagesDTO.setName(proProductionItemsTransaction.getProProductionStagesId().getName());
            proProductionItemsTransactionDTO.setProProductionStagesId(proProductionStagesDTO);

        }

        if (proProductionItemsTransaction.getProProductionTransactionId() != null) {
            ProProductionTransactionDTO proProductionTransactionDTO = new ProProductionTransactionDTO();
            proProductionTransactionDTO.setId(proProductionItemsTransaction.getProProductionTransactionId().getId());

            proProductionItemsTransactionDTO.setProProductionTransactionId(proProductionTransactionDTO);
        }
        proProductionItemsTransactionDTO.setStatus(proProductionItemsTransaction.getStatus());
        proProductionItemsTransactionDTO.setSerial(proProductionItemsTransaction.getSerial());

        proProductionItemsTransactionDTO.setCreatedBy(proProductionItemsTransaction.getCreatedBy() != null ? proProductionItemsTransaction.getCreatedBy().getId() : null);
        proProductionItemsTransactionDTO.setCreatedDate(proProductionItemsTransaction.getCreationDate());
        proProductionItemsTransactionDTO.setBranchId(proProductionItemsTransaction.getBranchId() != null ? proProductionItemsTransaction.getBranchId().getId() : null);

        return proProductionItemsTransactionDTO;
    }

}

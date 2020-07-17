/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.core.GenericDAO;
import com.toby.dto.InvPurchaseOrderDetailDTO;
import com.toby.entity.InvPurchaseOrderDetail;
import com.toby.entity.TobyUser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hq002
 */
@Stateless
public class InvPurchaseOrderDetailsServiceImpl implements InvPurchaseOrderDetailsService {

    @EJB
    private GenericDAO dao;

    @Override
    public void addPurchaseOrderDetails(InvPurchaseOrderDetail details) {
        dao.saveEntity(details);
    }

    @Override
    public void deletePurchaseOrderDetails(InvPurchaseOrderDetail details) {
        dao.deleteEntity(details);
    }

    @Override
    public InvPurchaseOrderDetail updatePurchaseOrderDetails(InvPurchaseOrderDetail details) {
        return dao.updateEntity(details);
    }

    @Override
    public List<InvPurchaseOrderDetail> getAllPurchaseOrderDetails() {
        return dao.findAll(InvPurchaseOrderDetail.class);
    }

    @Override
    public List<InvPurchaseOrderDetail> getAllPurchaseOrderDetailsByInvPurchaseOrderId(Integer invPurchaseOrderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("invPurchaseOrderId", invPurchaseOrderId);
        List<InvPurchaseOrderDetail> details = dao.findListByQuery("SELECT e FROM InvPurchaseOrderDetail e WHERE e.invPurchaseOrderId.id = :invPurchaseOrderId", params);
        return details;
    }

    @Override
    public List<InvPurchaseOrderDetailDTO> getALLPurchaseOrderDetailsByPurchaseOrder(Integer purchaseOrderId, TobyUser tobyUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("purchaseOrderId", purchaseOrderId);
        List<InvPurchaseOrderDetail> details = dao.findListByQuery("SELECT e FROM InvPurchaseOrderDetail e WHERE e.invPurchaseOrderId.id = :purchaseOrderId", params);
        return mapToDTOList(details, tobyUser);
    }

    @Override
    public List<InvPurchaseOrderDetail> getAllPurchaseOrderDetails(Integer id) {
        Map<String, Object> params = new HashMap();
        params.put("purchaseId", id);
        return dao.findListByQuery("SELECT d FROM InvPurchaseOrderDetail d WHERE d.invPurchaseOrderId.id=:purchaseId", params);
    }

    @Override
    public List<InvPurchaseOrderDetail> getAllPurchaseOrderDetailsWithStatus(Integer id) {
        Map<String, Object> params = new HashMap();
        params.put("purchaseId", id);
        return dao.findListByQuery("SELECT d FROM InvPurchaseOrderDetail d WHERE d.invPurchaseOrderId.id=:purchaseId and d.status != 2 ", params);
    }

    @Override
    public InvPurchaseOrderDetail findInvPurchaseOrderDetailById(Integer invPurchaseOrderDetailId) {
        return dao.findEntityById(InvPurchaseOrderDetail.class, invPurchaseOrderDetailId);
    }

    private List<InvPurchaseOrderDetailDTO> mapToDTOList(List<InvPurchaseOrderDetail> details, TobyUser tobyUser) {
        List<InvPurchaseOrderDetailDTO> invPurchaseOrderDetailDTOs = new ArrayList<>();
        for (InvPurchaseOrderDetail invPurchaseOrderDetail : details) {
            invPurchaseOrderDetailDTOs.add(mapToDTO(invPurchaseOrderDetail, tobyUser));
        }
        return invPurchaseOrderDetailDTOs;
    }

    private InvPurchaseOrderDetailDTO mapToDTO(InvPurchaseOrderDetail invPurchaseOrderDetail, TobyUser tobyUser) {
        InvPurchaseOrderDetailDTO invPurchaseOrderDetailDTO = new InvPurchaseOrderDetailDTO();
        invPurchaseOrderDetailDTO.setId(invPurchaseOrderDetail.getId());
        invPurchaseOrderDetailDTO.setSerial(invPurchaseOrderDetail.getSerial());
        invPurchaseOrderDetailDTO.setStatus(invPurchaseOrderDetail.getStatus());
        invPurchaseOrderDetailDTO.setPrice(invPurchaseOrderDetail.getPrice());
        invPurchaseOrderDetailDTO.setCreatedBy(invPurchaseOrderDetail.getCreatedBy() != null ? invPurchaseOrderDetail.getCreatedBy().getId() : null);
        invPurchaseOrderDetailDTO.setCreatedDate(invPurchaseOrderDetail.getCreationDate());
        invPurchaseOrderDetailDTO.setBranchId(invPurchaseOrderDetail.getBranchId() != null ? invPurchaseOrderDetail.getBranchId().getId() : null);
        invPurchaseOrderDetailDTO.setCompanyId(invPurchaseOrderDetail.getCompanyId().getId() != null ? invPurchaseOrderDetail.getCompanyId().getId() : null);
        
        return invPurchaseOrderDetailDTO;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.bussinessservice.global.InvPurchaseReturnSave;
import com.toby.core.GenericDAO;
import com.toby.entity.InvQotation;
import com.toby.entity.InvQotationDetail;
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
public class InvQuotationServiceImpl implements InvQuotationService {

    @EJB
    private GenericDAO dao;
    @EJB
    private GenericService genericService;

    @EJB
    private InvQuotationDetailsService invQuotationDetailsService;
    
    InvPurchaseReturnSave invPurchaseReturnSave ;

    @Override
    public InvPurchaseReturnSave addInvQotation(InvQotation invQotation, List<InvQotationDetail> invQotationDetailDetailList, List<InvQotationDetail> qotationDetailDeleted) {
        Integer serial;
        invPurchaseReturnSave = new InvPurchaseReturnSave();
        if (invQotation.getId() != null) {
            dao.updateEntity(invQotation);
        } else {
            serial = genericService.getMaxGenericSerialByType(InvQotation.class, invQotation.getBranchId().getId(), null);
            invQotation.setSerial(serial);
            dao.saveEntity(invQotation);
        }

        for (InvQotationDetail deleted : qotationDetailDeleted) {
            invQuotationDetailsService.deleteInvQotationDetails(deleted);
        }

        for (InvQotationDetail detail : invQotationDetailDetailList) {
            detail.setQotationId(invQotation);
            if (detail.getId() != null) {
                invQuotationDetailsService.updateInvQotationDetails(detail);
            } else {
                serial = getMaxSerialForInvoiceDetail(invQotation);
                detail.setSerial(serial);
                invQuotationDetailsService.addInvQotationDetails(detail);
            }
        }
        invPurchaseReturnSave.setInvQotation(invQotation);
        invPurchaseReturnSave.setInvQotationDetailList(invQuotationDetailsService.getAllInvQotationDetailsByIInvQotationIdWithStatus(invQotation.getId()));
        return invPurchaseReturnSave;
    }

    @Override
    public void deleteInvQotation(InvQotation invQotation, Integer invQotationIdToPass) {
        if (invQotationIdToPass != null && invQotationIdToPass > 0) {
            dao.executeDeleteQuery("delete from InvQotationDetail e WHERE e.id > 0 AND e.qotationId.id = " + invQotationIdToPass);
        }
        dao.deleteEntity(invQotation);
    }

    @Override
    public List<InvQotation> getALLInvQotation() {
        return dao.findAll(InvQotation.class);
    }

    @Override
    public List<InvQotation> getALLInvQotationByCompanyId(Integer Id) {
        return dao.findEntityListByCompanyId(InvQotation.class, Id);
    }
    
     @Override
    public InvQotation updateInvQotation(InvQotation invQotation) {
        return dao.updateEntity(invQotation);
    }

    @Override
    public List<InvQotation> getALLInvQotationByBranchId(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        List<InvQotation> invQotations = dao.findListByQuery("SELECT e FROM InvQotation e WHERE e.branchId.id = :branchId", params);
        return invQotations;
    }
     @Override
    public List<InvQotation> getALLInvQotationByBranchIdByStatus(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        List<InvQotation> InvQotations = dao.findListByQuery("SELECT e FROM InvQotation e WHERE e.branchId.id = :branchId and (e.status != 2  OR e.status is null) ", params);
        return InvQotations;
    }

    @Override
    public List<InvQotationDetail> getALLInvQotationDetailsByInvQotation(Integer invQotationId) {
        Map<String, Object> params = new HashMap<>();
        params.put("invQotationId", invQotationId);
        List<InvQotationDetail> details = dao.findListByQuery("SELECT e FROM InvQotationDetail e WHERE e.qotationId.id = :invQotationId", params);
        return details;
    }

    @Override
    public InvQotation findInvQotationByInvQotationId(Integer QuotationId) {
        return dao.findEntityById(InvQotation.class, QuotationId);
    }

    public synchronized Integer getMaxSerialForInvoiceDetail(InvQotation invQotation) {
        Map<String, Object> params = new HashMap<>();
        params.put("qotationId", invQotation.getId());

        Integer serial = dao.findEntityByQuery("SELECT max(e.serial) FROM InvQotationDetail e WHERE e.qotationId.id =:qotationId ", params);

        if (serial == null) {
            return 1;
        } else {
            serial = serial + 1;
            return serial;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.core.GenericDAO;
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
public class InvQuotationDetailsServiceImpl implements InvQuotationDetailsService {

    @EJB
    private GenericDAO dao;

    @Override
    public void addInvQotationDetails(InvQotationDetail details) {
        dao.saveEntity(details);
    }

    @Override
    public void deleteInvQotationDetails(InvQotationDetail details) {
        dao.deleteEntity(details);
    }

    @Override
    public InvQotationDetail updateInvQotationDetails(InvQotationDetail details) {
        return dao.updateEntity(details);
    }

    @Override
    public List<InvQotationDetail> getAllInvQotationDetails() {
        return dao.findAll(InvQotationDetail.class);
    }

    @Override
    public List<InvQotationDetail> getAllInvQotationDetails(Integer id) {
        Map<String, Object> params = new HashMap();
        params.put("quotationId", id);
        return dao.findListByQuery("SELECT d FROM InvQotationDetail d WHERE d.qotationId.id =:quotationId", params);
    }

    @Override
    public InvQotationDetail findInvQotationDetailById(Integer invQotationDetailId) {
        return dao.findEntityById(InvQotationDetail.class, invQotationDetailId);
    }
     @Override
    public List<InvQotationDetail> getAllInvQotationDetailsByIInvQotationIdWithStatus(Integer invQotationId) {
        Map<String, Object> params = new HashMap<>();
        params.put("qotationId", invQotationId);
        List<InvQotationDetail> details = dao.findListByQuery("SELECT e FROM InvQotationDetail e WHERE e.qotationId.id = :qotationId and (e.status != 2  OR e.status is null) ", params);
        return details;
    }
}

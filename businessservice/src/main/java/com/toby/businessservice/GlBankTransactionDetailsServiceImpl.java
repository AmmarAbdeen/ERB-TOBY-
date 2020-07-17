/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.core.GenericDAO;
import com.toby.entity.GlBankTransactionDetail;
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
public class GlBankTransactionDetailsServiceImpl implements GlBankTransactionDetailsService {

    @EJB
    private GenericDAO dao;

    @Override
    public void addGlBankTransactionDetails(GlBankTransactionDetail glBankTransactionDetail) {
        dao.saveEntity(glBankTransactionDetail);
    }

    @Override
    public void deleteGlBankTransactionDetails(GlBankTransactionDetail glBankTransactionDetail) {
        dao.deleteEntity(glBankTransactionDetail);
    }

    @Override
    public GlBankTransactionDetail updateGlBankTransactionDetails(GlBankTransactionDetail glBankTransactionDetail) {
        return dao.updateEntity(glBankTransactionDetail);
    }

    @Override
    public List<GlBankTransactionDetail> getAllGlBankTransactionDetailsByGlBankTransactionId(Integer glBankTransactionId) {
        Map<String, Object> params = new HashMap<>();
        params.put("glBankTransactionId", glBankTransactionId);
        List<GlBankTransactionDetail> details = dao.findListByQuery("SELECT e FROM GlBankTransactionDetail e WHERE e.glBankTransactionId.id = :glBankTransactionId", params);
        return details;
    }

    @Override
    public GlBankTransactionDetail findGlBankTransactionDetailsById(Integer glBankTransactionDetailsId) {
        return dao.findEntityById(GlBankTransactionDetail.class, glBankTransactionDetailsId);
    }

    @Override
    public GlBankTransactionDetail addOneGlBankTransactionDetail(GlBankTransactionDetail glBankTransactionDetail) {
        return dao.updateEntity(glBankTransactionDetail);
    }

    @Override
    public GlBankTransactionDetail getGlBankTransactionDetailByGlBankTransactionId(Integer glBankTransactionId) {
        Map<String, Object> params = new HashMap<>();
        params.put("glBankTransactionId", glBankTransactionId);
        return dao.findEntityByQuery("SELECT e FROM GlBankTransactionDetail e WHERE e.glBankTransactionId.id = :glBankTransactionId", params);

    }

    @Override
    public Integer finMaxSerial(Integer glBankTransactionId) {
        Map<String, Object> params = new HashMap<>();
        params.put("glBankTransactionId", glBankTransactionId);
        Integer max = dao.findEntityByQuery("SELECT COALESCE(MAX(g.serial),0) +1  FROM GlBankTransactionDetail g WHERE g.glBankTransactionId.id =:glBankTransactionId  ", params);
        return max;
    }

}

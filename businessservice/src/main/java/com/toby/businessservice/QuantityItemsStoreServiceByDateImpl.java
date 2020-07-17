/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.core.GenericDAO;
import com.toby.views.QuantityItemsStoreByDate;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author anady
 */
@Stateless
public class QuantityItemsStoreServiceByDateImpl implements QuantityItemsStoreByDateService {

    @EJB
    private GenericDAO dao;

    @Override
    public List<QuantityItemsStoreByDate> findInventoryItemsList(Integer inventoryId, Integer branchId, Boolean sellBuy) {
        Map<String, Object> params = new HashMap();
        params.put("inventoryId", inventoryId);
        params.put("branchId", branchId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT inv FROM QuantityItemsStoreByDate inv WHERE inv.inventoryId = :inventoryId AND inv.branchId = :branchId");
        if (sellBuy) {
            sql.append(" and inv.screenCode NOT IN (1 , 2)");
        } else {
            sql.append(" and inv.screenCode NOT IN (3 , 4)");
        }
        return dao.findListByQuery(sql.toString(), params);

    }

    @Override
    public synchronized BigDecimal findInventoryItem(Integer inventoryId, Integer branchId, Integer itemId, Boolean sellBuy, Date date) {
        Map<String, Object> params = new HashMap();
        params.put("inventoryId", inventoryId);
        params.put("branchId", branchId);
        params.put("itemId", itemId);
        params.put("date", date);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT sum(inv.qty) FROM QuantityItemsStoreByDate inv WHERE inv.inventoryId = :inventoryId AND inv.branchId = :branchId AND inv.itemId = :itemId AND inv.date <= :date");
        if (sellBuy) {
            sql.append(" and inv.screenCode NOT IN (1 , 2)");
        } else {
            sql.append(" and inv.screenCode NOT IN (3 , 4)");
        }

        sql.append(" group by inv.itemId ");

        List<BigDecimal> itemsStores = dao.findListByQuery(sql.toString(), params);

        return (itemsStores != null && itemsStores.size() > 0) ? itemsStores.get(0) : null;
    }

    @Override
    public synchronized BigDecimal findInventoryItemByDate(Integer inventoryId, Integer branchId, Integer itemId, Date date) {
        Map<String, Object> params = new HashMap();
        params.put("inventoryId", inventoryId);
        params.put("branchId", branchId);
        params.put("itemId", itemId);
        params.put("date", date);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT sum(inv.qty) FROM QuantityItemsStoreByDate inv WHERE inv.inventoryId = :inventoryId AND inv.branchId = :branchId AND inv.itemId = :itemId AND inv.date < :date");
        sql.append(" and inv.screenCode NOT IN (1 , 2)");

        sql.append(" group by inv.itemId ");

        List<BigDecimal> itemsStores = dao.findListByQuery(sql.toString(), params);

        return (itemsStores != null && itemsStores.size() > 0) ? itemsStores.get(0) : null;
    }

    @Override
    public List<QuantityItemsStoreByDate> findItemsForBranchList(Integer itemId, Integer branchId, Boolean sellBuy) {
        Map<String, Object> params = new HashMap();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT inv FROM QuantityItemsStoreByDate inv WHERE inv.itemId = :itemId AND inv.branchId = :branchId AND inv.inventoryId != null");
        params.put("itemId", itemId);
        params.put("branchId", branchId);
        if (sellBuy) {
            sql.append(" and inv.screenCode NOT IN (1 , 2)");
        } else {
            sql.append(" and inv.screenCode NOT IN (3 , 4)");
        }
        return dao.findListByQuery(sql.toString(), params);

    }

    @Override
    public List<QuantityItemsStoreByDate> findAllQuantityItemsListStoreByBranchId(Integer branchId, Boolean sellBuy) {
        Map<String, Object> params = new HashMap();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT inv FROM QuantityItemsStoreByDate inv WHERE inv.branchId = :branchId AND inv.inventoryId != null");
        params.put("branchId", branchId);
        if (sellBuy) {
            sql.append(" and inv.screenCode NOT IN (1 , 2)");
        } else {
            sql.append(" and inv.screenCode NOT IN (3 , 4)");
        }
        return dao.findListByQuery(sql.toString(), params);
    }
}

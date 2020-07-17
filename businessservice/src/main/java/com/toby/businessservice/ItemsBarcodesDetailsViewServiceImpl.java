/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.core.GenericDAO;
import com.toby.views.ItemsBarcodesDetailsView;
import java.util.ArrayList;
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
public class ItemsBarcodesDetailsViewServiceImpl implements ItemsBarcodesDetailsViewService {
    
    @EJB
    private GenericDAO dao;
    
    @Override
    public List<ItemsBarcodesDetailsView> findItemsBarcodesDetailsViewBranchId(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        return dao.findListByQuery("SELECT e FROM ItemsBarcodesDetailsView e WHERE e.branchId = :branchId", params);
    }
    
    @Override
    public Map<String, ItemsBarcodesDetailsView> findItemsBarcodesDetailsViewBranchIdMap(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        Map<String, ItemsBarcodesDetailsView> itemsBarcodesDetailsViewMap = new HashMap<>();
        params.put("branchId", branchId);
        List<ItemsBarcodesDetailsView> itemsBarcodesDetailsViewList = dao.findListByQuery("SELECT e FROM ItemsBarcodesDetailsView e WHERE e.branchId = :branchId", params);
        for (ItemsBarcodesDetailsView itemsBarcodesDetailsView : itemsBarcodesDetailsViewList) {
            itemsBarcodesDetailsViewMap.put(itemsBarcodesDetailsView.getBarcode(), itemsBarcodesDetailsView);
        }
        return itemsBarcodesDetailsViewMap;
    }
    
    @Override
    public List<ItemsBarcodesDetailsView> findItemsBarcodesDetailsViewBranchIdAndStore(Integer branchId, String barcodes) {
        List<ItemsBarcodesDetailsView> barcodesDetailsViewsList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        String query;
        if (barcodes != null && !barcodes.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            query = "SELECT e FROM ItemsBarcodesDetailsView e WHERE e.branchId = :branchId and e.barcode";
            builder.append(query).append(" in (").append(barcodes).append(")");
            barcodesDetailsViewsList = dao.findListByQuery(builder.toString(), params);
            
        } else {
            return null;
        }
        return barcodesDetailsViewsList;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.core.GenericDAO;
import com.toby.dto.PrintProductionDTO;
import com.toby.views.InvoiceMovementReport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Toby
 */
@Stateless
public class InvoiceMovementReportServiceImpl implements InvoiceMovementReportService {

    @EJB
    private GenericDAO dao;

    @Override
    public List<InvoiceMovementReport> searchReport(PrintProductionDTO printProductionDTO) {
        Map<String, Object> params = new HashMap<>();
//         params.put("branchId", tobyUser.getBranchId().getId());
        StringBuilder query = new StringBuilder();
        query.append("SELECT p FROM InvoiceMovementReport p where p.rowNum > 0  ");
        appendProProductionInvoice(printProductionDTO, params, query);
        appendProProductionDate(printProductionDTO, params, query);
        return  dao.findListByQuery(query.toString(), params);
        

    }
    
  
     private void appendProProductionInvoice(PrintProductionDTO productionDTO, Map<String, Object> params, StringBuilder query) {
        if (productionDTO.getFromInvoice()!= null) {
            params.put("fromInvoice", productionDTO.getFromInvoice().getSerial());
            query.append(" and p.invoice >= :fromInvoice ");
        }
        
        if (productionDTO.getToInvoice()!= null) {
            params.put("toInvoice", productionDTO.getToInvoice().getSerial());
            query.append("and p.invoice <= :toInvoice ");
        }
    }
     
     private void appendProProductionDate(PrintProductionDTO productionDTO, Map<String, Object> params, StringBuilder query) {
        if (productionDTO.getFromDate() != null) {
            params.put("fromDate", productionDTO.getFromDate());
            query.append(" and p.date >= :fromDate ");
        }

        if (productionDTO.getToDate() != null) {
            params.put("toDate", productionDTO.getToDate());
            query.append(" and p.date <= :toDate ");
        }
    }

    



}

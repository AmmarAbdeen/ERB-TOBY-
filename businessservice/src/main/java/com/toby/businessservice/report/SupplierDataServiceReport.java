/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice.report;

import com.toby.businessservice.reports.searchBean.SupplierDataReportSearchBean;
import com.toby.entity.InvOrganizationSite;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author hhhh
 */
@Remote
public interface SupplierDataServiceReport {

   public List<InvOrganizationSite> getAllSupplierDataReportSearchBean(SupplierDataReportSearchBean supplierDataReportSearchBean);
  


}

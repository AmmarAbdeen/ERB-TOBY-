package com.toby.businessservice;

import com.toby.dto.PrintProductionDTO;
import com.toby.views.InvoiceMovementReport;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface InvoiceMovementReportService {
   
   public List<InvoiceMovementReport> searchReport(PrintProductionDTO printProductionDTO );
       
   

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.bussinessservice.global.InvPurchaseReturnSave;
import com.toby.entity.InvQotation;
import com.toby.entity.InvQotationDetail;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author hq002
 */
@Remote
public interface InvQuotationService {
    
     public InvPurchaseReturnSave addInvQotation(InvQotation invQotation , List<InvQotationDetail> invQotationDetailDetailList , List<InvQotationDetail> qotationDetailDeleted);

    public void deleteInvQotation(InvQotation invQotation , Integer invQotationIdToPass);

    public List<InvQotation> getALLInvQotation();

    public List<InvQotation> getALLInvQotationByCompanyId(Integer Id);

    public List<InvQotation> getALLInvQotationByBranchId(Integer Id);
    
    public InvQotation updateInvQotation(InvQotation invQotation);

    public List<InvQotationDetail> getALLInvQotationDetailsByInvQotation(Integer invQotationId);
    
    public InvQotation findInvQotationByInvQotationId(Integer QuotationId);
    
    public List<InvQotation> getALLInvQotationByBranchIdByStatus(Integer branchId);
}

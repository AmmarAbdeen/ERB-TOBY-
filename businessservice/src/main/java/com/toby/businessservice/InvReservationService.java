package com.toby.businessservice;

import com.toby.bussinessservice.global.InvPurchaseReturnSave;
import com.toby.entity.InvReservation;
import com.toby.entity.InvReservationDetail;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author khaled
 */
@Remote
public interface InvReservationService {

    public InvPurchaseReturnSave addInvReservation(InvReservation invReservation, List<InvReservationDetail> invReservationDetailDetailList,
            List<InvReservationDetail> ReservationDetailDeleted) ;

    void deleteInvReservation(InvReservation invReservation, Integer invReservationIdToPass);

    List<InvReservation> getALLInvReservation();

    List<InvReservation> getALLInvReservationByCompanyId(Integer Id);

    List<InvReservation> getALLInvReservationByBranchId(Integer Id);
    public List<InvReservation> getALLInvReservationsByBranchIdByStatus(Integer branchId) ;

    List<InvReservationDetail> getALLInvReservationDetailsByInvReservation(Integer invReservationId);

    InvReservation findInvReservationByInvReservationId(Integer invReservationId);

    Integer findMaxSerialNoByBranch(Integer branchId);
    
    public InvReservation updateInvReservation(InvReservation invReservation);

    public Integer getMaxSerialForInvoiceDetail(InvReservation invReservation);
}

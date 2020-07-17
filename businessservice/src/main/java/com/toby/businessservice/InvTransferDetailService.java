package com.toby.businessservice;

import com.toby.dto.InvTransferDetailDTO;
import com.toby.entity.InvTransferDetail;
import com.toby.entity.TobyUser;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author khaled
 */
@Remote
public interface InvTransferDetailService {

    void addInvTransferDetails(InvTransferDetail details);

    void deleteInvTransferDetails(InvTransferDetail details);

    InvTransferDetail updateInvTransferDetails(InvTransferDetail details);

    List<InvTransferDetail> getAllInvTransferDetails();

    public List<InvTransferDetail> getAllInvTransferDetails(Integer id);
    public List<InvTransferDetailDTO> getAllInvTransferDetaiDTOlList(Integer id , TobyUser tobyUser);
    
    InvTransferDetail findInvTransferDetailsById(Integer invTransferDetailsId);
    
   List<InvTransferDetail> getAllInvTransferDetailsByInvTransferId(Integer invTransferId) ;
   
    public List<InvTransferDetail> getAllInvTransferDetailsByInvTransferIdWithStatus(Integer invTransferId);
    
    public List<InvTransferDetailDTO> addInvTransferDetailsDTO(List<InvTransferDetailDTO>  invTransferDetailDTOList ,Integer invTransferDTOId ,TobyUser tobyUser);
   
}

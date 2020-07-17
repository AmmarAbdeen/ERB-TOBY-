package com.toby.businessservice;

import com.toby.dto.InvItemDTO;
import com.toby.dto.ProItemProductionStagesDTO;
import com.toby.entity.TobyUser;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface ProItemProductionStagesService {
    
    public List<ProItemProductionStagesDTO> getAllInvItem(TobyUser tobyUser);
    
    public ProItemProductionStagesDTO save(ProItemProductionStagesDTO proItemProductionStagesDTO, TobyUser tobyUser);
    
     public List<ProItemProductionStagesDTO> getAll(TobyUser tobyUser,InvItemDTO invItemDTO);
     
     public ProItemProductionStagesDTO findByDTO(TobyUser tobyUser,ProItemProductionStagesDTO dTO);
       
     public void deleteByStage(ProItemProductionStagesDTO itemStages);

}

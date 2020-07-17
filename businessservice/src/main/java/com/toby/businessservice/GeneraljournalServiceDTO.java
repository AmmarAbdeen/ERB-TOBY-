/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.entity.TobyUser;
import com.toby.dto.GeneralJournalDTO;
import com.toby.dto.GlYearDTO;
import com.toby.entity.GeneralJournal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author AhmedEssam
 */
@Remote
public interface GeneraljournalServiceDTO {

    public Long getTotalGeneralJournal(GlYearDTO year, Integer branchId, Map<String, Object> filters);

    public List<GeneralJournalDTO> getGeneralJournalDTOByBranchId(GlYearDTO year, Integer branchId, int first, int pageSize, String sortField, Boolean asc, Map<String, Object> filters);

    public GeneralJournalDTO addGeneralJournalDTO(GeneralJournalDTO generalJournalDTO, TobyUser tobyUser);

    public List<GeneralJournalDTO> getALLGeneralJournalDTOByBranchIdAndYear(Integer selectedBranch, GlYearDTO year);
 
    public void addGeneralJournalsDTOForReviewing(List<GeneralJournalDTO> generalJournalDTOList,TobyUser tobyUser);

    public void updateGeneralJournalsDTOForReviewing(Date dateFrom, Date dateTo, Integer branchId, Integer postFlag, String postFlagReview);

    public void deleteGeneralJournalDTO(GeneralJournalDTO generalJournalDTO, TobyUser tobyUser);

    public GeneralJournal convertFromDTO(GeneralJournalDTO dTO, TobyUser tobyUser);
    
    public void addGeneralJournalDTOForCorrectence(List<GeneralJournalDTO> generalJournals,TobyUser tobyUser); 
    
    
}

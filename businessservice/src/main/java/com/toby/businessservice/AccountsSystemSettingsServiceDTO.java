/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.dto.AccountsSystemSettingsDTO;
import javax.ejb.Remote;

/**
 *
 * @author AhmedEssam
 */
@Remote
public interface AccountsSystemSettingsServiceDTO {
    
     public AccountsSystemSettingsDTO getInventoryByCompanyId(Integer companyId);

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.core.GenericDAO;
import com.toby.dto.TobyUserDTO;
import com.toby.entity.TobyUser;
import com.toby.entity.TobyUserRole;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hq004
 */
@Stateless
public class TobyUserServiceImpl implements TobyUserService {

    @EJB
    private GenericDAO dao;

    public TobyUserServiceImpl() {

    }

    @Override
    public TobyUser authenticateUser(String userCode, String password) {
        Map<String, Object> params = new HashMap();
        params.put("userCode", userCode);
        System.out.println("userCode: " + userCode);
        TobyUser user;
        user = dao.findEntityByNamedQuery("TobyUser.authenticate", params);
        System.out.println("user: " + user);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public List<TobyUser> getAllUsersListByCompanyId(Integer companyId) {
        return dao.findEntityListByCompanyId(TobyUser.class, companyId);
    }

    @Override
    public List<TobyUser> getUsersForBranch(Integer selectedBranchId) {
        List<TobyUser> tobyUserList = new ArrayList<>();
        Map<String, Object> params = new HashMap();
        params.put("id", selectedBranchId);
        String sql = "SELECT iur FROM TobyUserRole iur where iur.branchId = :id";
        List<TobyUserRole> tobyUserRoleList = dao.findListByQuery(sql, params);
        for (TobyUserRole tobyUserRole : tobyUserRoleList) {
            tobyUserList.add(tobyUserRole.getUserId());
        }
        return tobyUserList;
    }

    @Override
    public TobyUser findUser(Integer userId) {
        return dao.findEntityById(TobyUser.class, userId);
    }

    @Override
    public List<TobyUser> getAllUsers() {
        return dao.findAll(TobyUser.class);

    }

    @Override
    public void deleteUser(TobyUser user) {
        dao.deleteEntity(user);
    }

    @Override
    public TobyUser addUser(TobyUser user) {
        return dao.updateEntity(user);
    }

    @Override
    public List<String> getAuthorizedScreens(Integer id) {
        Map<String, Object> params = new HashMap();
        params.put("roleId", id);
        return dao.findListByQuery("SELECT priv.screenId.name FROM TobyPrivilege priv WHERE priv.roleId.id = :roleId AND priv.view = true", params);
    }

    @Override
    public TobyUser updateUser(TobyUser user) {
        return dao.updateEntity(user);
    }

    @Override
    public List<TobyUser> getAllUsersByName(String name) {
        Map<String, Object> params = new HashMap();
        params.put("name", name);
        return dao.findListByQuery("SELECT user FROM TobyUser user WHERE  user.name LIKE CONCAT('%',:name,'%')", params);

    }

    @Override
    public List<TobyUser> getAllUsersByCode(String userCode) {
        Map<String, Object> params = new HashMap();
        params.put("userCode", userCode);
        return dao.findListByQuery("SELECT user FROM TobyUser user WHERE  user.userCode= :userCode", params);

    }

    @Override
    public List<TobyUser> getAllUsersByNameAndCode(String user_name, String userCode) {
        Map<String, Object> params = new HashMap();
        params.put("userCode", userCode);
        params.put("name", user_name);
        return dao.findListByQuery("SELECT user FROM TobyUser user WHERE  user.userCode= :userCode AND user.name LIKE ':name%'", params);

    }

    @Override
    public List<TobyUser> getUsersForBranch(Integer selectedBranchId, TobyUser tobyUser) {
        List<TobyUser> tobyUserList = new ArrayList<>();
        Map<String, Object> params = new HashMap();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT iur FROM TobyUserRole iur where iur.branchId.id = :id");
        params.put("id", selectedBranchId);

        if (tobyUser.getName() != null && !tobyUser.getName().isEmpty()) {
            params.put("name", tobyUser.getName());
            sql.append(" AND iur.userId.name LIKE CONCAT('%',:name,'%') ");

        }
        if (tobyUser.getUserCode() != null && !tobyUser.getUserCode().isEmpty()) {
            params.put("userCode", tobyUser.getUserCode());
            sql.append(" AND iur.userId.userCode = :userCode");
        }

        List<TobyUserRole> tobyUserRoleList = dao.findListByQuery(sql.toString(), params);
        for (TobyUserRole tobyUserRole : tobyUserRoleList) {
            tobyUserList.add(tobyUserRole.getUserId());
        }
        return tobyUserList;
    }

    @Override
    public TobyUser validateOldPassword(TobyUser tobyUser, String newPassword) {
        tobyUser = findUser(tobyUser.getId());
        if (!newPassword.equals(tobyUser.getPassword())) {
            tobyUser.setMsg("يجب ادخال الرقم السري");
        }

        return tobyUser;
    }

    @Override
    public String validateNewPassword(String userPassword, String newPassword) {
        String msg = "";
        if (newPassword.equals(userPassword)) {
            msg = "يجب اختيار رقم سري جديد";
        }

        return msg;
    }

    @Override
    public String validateConfirmationPassword(String confirmationPassword, String newPassword) {
        String msg = "";
        if (!confirmationPassword.equals(newPassword)) {
            msg = "الرقم السري غير متطابق";
        }

        return msg;
    }

    @Override
    public List<TobyUserDTO> getAllUserOfproduction(TobyUser tobyUser) {
       Map<String,Object> params = new HashMap<>(); 
       params.put("branchId", tobyUser.getBranchId().getId());
     List<TobyUser> details= dao.findListByQuery("SELECT e FROM TobyUser e where e.id in ( select p.userId.id from TobyUserProproduction p where p.branchId.id =:branchId)", params);
   
    return mapToDTOList(details, tobyUser);
    }
    
     public TobyUserDTO mapToDTO(TobyUser tobyUser) {

        TobyUserDTO tobyUserDTO = new TobyUserDTO();
       tobyUserDTO.setId(tobyUser.getId());
       tobyUserDTO.setMaster(tobyUser.getMaster());
       tobyUserDTO.setMsg(tobyUser.getMsg());
       tobyUserDTO.setName(tobyUser.getName());
       tobyUserDTO.setPassword(tobyUser.getPassword());
       tobyUserDTO.setUserCode(tobyUser.getUserCode());
       tobyUserDTO.setCreatedBy(tobyUserDTO.getCreatedBy() != null ? tobyUser.getCreatedBy().getId() : null);
        tobyUserDTO.setCreatedDate(tobyUser.getCreationDate());
        tobyUserDTO.setBranchId(tobyUser.getBranchId().getId() != null ? tobyUser.getBranchId().getId() : null);
        tobyUserDTO.setCompanyId(tobyUser.getCompanyId() != null ? tobyUser.getCompanyId().getId() : null);
          
        return tobyUserDTO;
    }
       public List<TobyUserDTO> mapToDTOList(List<TobyUser> tobyUserList, TobyUser tobyUser) {
        List<TobyUserDTO> tobyUserDTOsList = new ArrayList<>();
        for (TobyUser tobyUser1 : tobyUserList) {
            tobyUserDTOsList.add(mapToDTO(tobyUser1));
        }
        return tobyUserDTOsList;
    }

    
}

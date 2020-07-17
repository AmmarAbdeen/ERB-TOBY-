/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;

import com.toby.core.GenericDAO;
import com.toby.dto.GlAccountDTO;
import com.toby.dto.InventoryDelegatorDTO;
import com.toby.entity.Branch;
import com.toby.entity.GlAccount;
import com.toby.entity.InventoryDelegator;
import com.toby.entity.TobyCompany;
import com.toby.entity.TobyUser;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hq002
 */
@Stateless
public class InvDelegatorServiceImpl implements InvDelegatorService {

    @EJB
    private GenericDAO dao;

    @Override
    public List<InventoryDelegator> getDelegatorsByBranch(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", id);
//        List<InventoryDelegator> InventoryDelegator = dao.findEntityByQuery("SELECT ivd FROM InventoryDelegator ivd ");
        List<InventoryDelegator> inventoryDelegators = dao.findListByQuery("SELECT i FROM InventoryDelegator i where i.branchId.id = :branchId ORDER BY i.code", params);
//        return dao.findAll(InventoryDelegator.class);
        return inventoryDelegators;
    }

    @Override
    public InventoryDelegatorDTO findInventoryDelegator(Integer id) {
        InventoryDelegator inventoryDelegator = dao.findEntityById(InventoryDelegator.class, id);
        return mapToDTO(inventoryDelegator);
    }

    @Override
    public void deleteInventoryDelegator(InventoryDelegator inventoryDelegator) {
        dao.deleteEntity(inventoryDelegator);
    }

    @Override
    public InventoryDelegator updateInventoryDelegator(InventoryDelegator inventoryDelegator) {

        List<InventoryDelegator> invList = new ArrayList<>();
        if (inventoryDelegator.getId() != null) {
            invList = findDelegator(inventoryDelegator.getBranchId().getId(), inventoryDelegator.getId(), inventoryDelegator.getCode(), inventoryDelegator.getType());
            if (invList != null && !invList.isEmpty()) {
                return null;
            }
        }

        if (inventoryDelegator.getId() == null) {
            invList = findDelegator(inventoryDelegator.getBranchId().getId(), null, inventoryDelegator.getCode(), inventoryDelegator.getType());
            if (invList != null && !invList.isEmpty()) {
                return null;
            }
        }

        return dao.updateEntity(inventoryDelegator);
    }

    @Override
    public InventoryDelegatorDTO updateInventoryDelegatorDTO(InventoryDelegatorDTO inventoryDelegatorDTO,TobyUser tobyUser) {
        try {
            inventoryDelegatorDTO.setMsg(null);
            String error = genralValidate(tobyUser, inventoryDelegatorDTO);
            if (error.isEmpty()) {
                InventoryDelegator delegator = mapToEntity(inventoryDelegatorDTO, tobyUser);
                dao.updateEntity(delegator);
                inventoryDelegatorDTO = mapToDTO(delegator);
            } else {
                inventoryDelegatorDTO.setMsg(error);
            }
            return inventoryDelegatorDTO;
        } catch (Exception e) {
        }
        return null;
    }
    
    @Override
    public InventoryDelegator addInventoryDelegator(InventoryDelegator inventoryDelegator) {
        dao.saveEntity(inventoryDelegator);
        return inventoryDelegator;
    }

    @Override
    public List<InventoryDelegator> getDelegatorsByBranchAndId(Integer id, Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        params.put("id", id);

        List<InventoryDelegator> inventoryDelegators = dao.findListByQuery("SELECT i FROM InventoryDelegator i where i.branchId.id = :branchId AND i.id = :id  ORDER BY i.code", params);
        return inventoryDelegators;
    }

    @Override
    public List<InventoryDelegator> getSalesByBranch(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        String sql = "SELECT i FROM InventoryDelegator i where i.branchId.id = :branchId AND i.type = 1  ORDER BY i.code";
        List<InventoryDelegator> inventoryDelegatorList = dao.findListByQuery(sql, params);
        return inventoryDelegatorList;
    }

    @Override
    public List<InventoryDelegatorDTO> getSalesDTOByBranch(Integer branchId) {
        return returnListDTO(getSalesByBranch(branchId));
    }

    @Override
    public List<InventoryDelegator> getPurchaseByBranch(Integer branchId) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        String sql = "SELECT i FROM InventoryDelegator i where i.branchId.id = :branchId AND i.type = 0  ORDER BY i.code";
        List<InventoryDelegator> inventoryDelegatorList = dao.findListByQuery(sql, params);
        return inventoryDelegatorList;
    }

    @Override
    public List<InventoryDelegator> findDelegator(Integer branchId, Integer delegatorId, String code, Integer type) {
        StringBuilder query = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        params.put("code", code);
        params.put("type", type);

        query.append("SELECT i FROM InventoryDelegator i where i.branchId.id = :branchId AND i.code = :code AND i.type = :type ");

        if (delegatorId != null) {
            params.put("delegatorId", delegatorId);
            query.append(" AND i.id != :delegatorId ");
        }

        query.append(" ORDER BY i.code");

        return dao.findListByQuery(query.toString(), params);
    }

    private List<InventoryDelegatorDTO> returnListDTO(List<InventoryDelegator> list) {
        List<InventoryDelegatorDTO> dTOList = new ArrayList<>();
        for (InventoryDelegator entity : list) {
            dTOList.add(mapTODTO(entity, false));
        }
        return dTOList;
    }

    public InventoryDelegatorDTO mapTODTO(InventoryDelegator entity, Boolean needDetail) {
        InventoryDelegatorDTO dTO = initMapToDTO(entity);
        if (needDetail) {

        }
        return dTO;
    }

    private InventoryDelegatorDTO initMapToDTO(InventoryDelegator entity) {

        InventoryDelegatorDTO dTO = new InventoryDelegatorDTO();
        dTO.setId(entity.getId());
        dTO.setCode(entity.getCode());
        dTO.setName(entity.getName());
        dTO.setMobile(entity.getMobile());
        dTO.setImg(entity.getImg());
        dTO.setType(entity.getType());
        dTO.setAllowDiscountLimit(entity.getAllowDiscountLimit());
        dTO.setTargetSales(entity.getTargetSales());
        dTO.setCommission(entity.getCommission());

        dTO.setBranchId(entity.getBranchId().getId());
        dTO.setCreatedDate(entity.getCreationDate());
        dTO.setCreatedBy(entity.getCreatedBy().getId());
        dTO.setIndex(entity.getId());
        return dTO;
    }
    
    
    //mapToEntity
    public InventoryDelegator mapToEntity(InventoryDelegatorDTO inventoryDelegatorDTO, TobyUser tobyUser) {
        
        InventoryDelegator inventoryDelegator = new InventoryDelegator();
        inventoryDelegator.setId(inventoryDelegatorDTO.getId());
        inventoryDelegator.setName(inventoryDelegatorDTO.getName());
        inventoryDelegator.setCommission(inventoryDelegatorDTO.getCommission());
        inventoryDelegator.setCode(inventoryDelegatorDTO.getCode());
        inventoryDelegator.setMobile(inventoryDelegatorDTO.getMobile());
        inventoryDelegator.setType(inventoryDelegatorDTO.getType());
        inventoryDelegator.setTargetSales(inventoryDelegatorDTO.getTargetSales());
        if (inventoryDelegatorDTO.getCreatedBy() != null) {
            TobyUser user = new TobyUser();
            user.setId(inventoryDelegatorDTO.getCreatedBy());
            inventoryDelegator.setCreatedBy(user);
            inventoryDelegator.setCreationDate(inventoryDelegatorDTO.getCreatedDate());
            inventoryDelegator.setModifiedBy(tobyUser);
            inventoryDelegator.setModificationDate(new Date());
            if (inventoryDelegatorDTO.getCompanyId() != null) {
                TobyCompany company = new TobyCompany();
                company.setId(inventoryDelegatorDTO.getCompanyId());
                inventoryDelegator.setCompanyId(company);
            }

            if (inventoryDelegator.getBranchId() != null) {
                Branch branch = new Branch();
                branch.setId(inventoryDelegatorDTO.getBranchId());
                inventoryDelegator.setBranchId(branch);
            }
        } else {
            inventoryDelegator.setCreatedBy(tobyUser);
            inventoryDelegator.setCreationDate(new Date());
            inventoryDelegator.setCompanyId(tobyUser.getCompanyId());
            inventoryDelegator.setBranchId(tobyUser.getBranchId());
        }

        return inventoryDelegator;
    }
    
    
    

    public InventoryDelegatorDTO mapToDTO(InventoryDelegator inventoryDelegator) {
        
        InventoryDelegatorDTO delegatorDTO = new InventoryDelegatorDTO();
        delegatorDTO.setCreatedBy(inventoryDelegator.getCreatedBy() != null ? inventoryDelegator.getCreatedBy().getId() : null);
        delegatorDTO.setCreatedDate(inventoryDelegator.getCreationDate());
        delegatorDTO.setBranchId(inventoryDelegator.getBranchId() !=null ? inventoryDelegator.getBranchId().getId() : null);
        delegatorDTO.setId(inventoryDelegator.getId());
        delegatorDTO.setCompanyId(inventoryDelegator.getCompanyId() !=null ? inventoryDelegator.getCompanyId().getId() : null );
        delegatorDTO.setName(inventoryDelegator.getName());
        delegatorDTO.setCode(inventoryDelegator.getCode());
        delegatorDTO.setCommission(inventoryDelegator.getCommission());
        delegatorDTO.setMobile(inventoryDelegator.getMobile());
        delegatorDTO.setType(inventoryDelegator.getType());
        delegatorDTO.setTargetSales(inventoryDelegator.getTargetSales());
        
        return delegatorDTO;
    }

    public List<InventoryDelegatorDTO> mapToDTOList(List<InventoryDelegator> inventoryDelegatorList, TobyUser tobyUser) {
        List<InventoryDelegatorDTO> inventoryDelegatorDTOList = new ArrayList<>();
        for (InventoryDelegator inventoryDelegator : inventoryDelegatorList) {
            inventoryDelegatorDTOList.add(mapToDTO(inventoryDelegator));
        }
        return inventoryDelegatorDTOList;
    }

    @Override
    public List<InventoryDelegatorDTO> getDelegatorsByBranchDTO(Integer type, TobyUser tobyUser) {
    
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", tobyUser.getBranchId().getId());
        params.put("type", type);
        List<InventoryDelegator> inventoryDelegators = dao.findListByQuery("SELECT i FROM InventoryDelegator i where i.branchId.id = :branchId AND i.type = :type ORDER BY i.code", params);
        return mapToDTOList(inventoryDelegators, tobyUser);
    }

    @Override
    public List<InventoryDelegatorDTO> getSalesByBranchDTO(Integer branchId, TobyUser tobyUser) {
   Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        String sql = "SELECT i FROM InventoryDelegator i where i.branchId.id = :branchId AND i.type = 1  ORDER BY i.code";
        List<InventoryDelegator> inventoryDelegatorList = dao.findListByQuery(sql, params);
        return mapToDTOList(inventoryDelegatorList, tobyUser);
    }

    @Override
    public List<InventoryDelegatorDTO> getPurchaseByBranchDTO(Integer branchId, TobyUser tobyUser) {
   Map<String, Object> params = new HashMap<>();
        params.put("branchId", branchId);
        String sql = "SELECT i FROM InventoryDelegator i where i.branchId.id = :branchId AND i.type = 0  ORDER BY i.code";
        List<InventoryDelegator> inventoryDelegatorList = dao.findListByQuery(sql, params);
        return mapToDTOList(inventoryDelegatorList, tobyUser);
    }
    
    @Override
    public boolean validateCode(TobyUser tobyUser, Integer inventoryDelegatorId, String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("branchId", tobyUser.getBranchId().getId());
        params.put("inventoryDelegatorId", inventoryDelegatorId);
        params.put("code", code);
//        params.put("type", type);

        List<InventoryDelegator> invInventoryList = dao.findListByQuery("SELECT i FROM InventoryDelegator i where i.branchId.id = :branchId and i.id != :inventoryDelegatorId and i.code = :code ", params);
        StringBuilder querry = new StringBuilder();
        querry.append("SELECT i FROM InvGroup i where i.branchId.id = :branchId  and i.name = :name");
//        if (inventoryDelegatorId != null) {
//            params.put("inventoryDelegatorId", inventoryDelegatorId);
//            querry.append("and i.id != :inventoryDelegatorId");
//        }
//        List<InvGroup> invGroupList = dao.findListByQuery(querry.toString(), params);
//        if (invGroupList.isEmpty()) {
//            return true;
//        } else {
//
//            return false;
//        }
return false;
    }
    public String genralValidate(TobyUser tobyUser, InventoryDelegatorDTO inventoryDelegatorDTO) {
        StringBuilder errorMessage = new StringBuilder();
        if (validateCode(tobyUser, inventoryDelegatorDTO.getId(), inventoryDelegatorDTO.getCode())) {
            errorMessage.append("الكود موجود");
        }

        return errorMessage.toString();
    }
}

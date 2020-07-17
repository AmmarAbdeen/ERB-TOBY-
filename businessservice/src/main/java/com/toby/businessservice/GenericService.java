/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice;


import javax.ejb.Remote;

/**
 *
 * @author hhhh
 */
@Remote
public interface GenericService {

    public <T> Integer getMaxSerialForInvoice(Class<T> entityClass, Integer branchId, Integer inventoryId, Boolean type);

    public <T> Integer getMaxGenericSerialByType(Class<T> entityClass, Integer branchId, Integer type);
}

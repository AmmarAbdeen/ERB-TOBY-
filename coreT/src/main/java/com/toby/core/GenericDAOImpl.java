/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.core;

import com.toby.entity.BaseEntity;
import com.toby.entity.InvItem;
import com.toby.views.InvItemView;
import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.SessionContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.SortOrder;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author hq004
 */
@Stateless
public class GenericDAOImpl implements GenericDAO {

    @PersistenceContext(name = "TOBY_PU")
    private EntityManager em;
    @Resource
    private SessionContext ctx;

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {
        return executeJPQLQuery("SELECT entity FROM " + entityClass.getSimpleName() + " entity");
    }

    @Override
    public <T> T findEntityById(Class<T> entityClass, Serializable id) {
        return em.find(entityClass, id);
    }

    @Override
    public <T> T findEntityByNamedQuery(String namedQueryName) {
        return findEntityByNamedQuery(namedQueryName, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T findEntityByNamedQuery(String namedQueryName,
            Map<String, Object> params) {
        Query query = em.createNamedQuery(namedQueryName);

        if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        if (query.getResultList() != null && !query.getResultList().isEmpty()) {
            return (T) query.getResultList().get(0);
        } else {
            return null;
        }

    }

    @Override
    public <T> List<T> findListByNamedQuery(String namedQueryName) {
        return findListByNamedQuery(namedQueryName, null, -1, -1);
    }

    @Override
    public <T> List<T> findListByNamedQuery(String namedQueryName,
            Map<String, Object> params) {
        return findListByNamedQuery(namedQueryName, params, -1, -1);
    }

    @Override
    public <T> List<T> findListByNamedQuery(String namedQueryName, int start,
            int maxSize) {
        return findListByNamedQuery(namedQueryName, null, start, maxSize);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> findListByNamedQuery(String namedQueryName,
            Map<String, Object> params, int start, int maxSize) {
        Query query = em.createNamedQuery(namedQueryName);
        if (start > -1) {
            query.setFirstResult(start);
        }
        if (maxSize > -1) {
            query.setMaxResults(maxSize);
        }

        if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        return query.getResultList();
    }

    @Override
    public <T> T findEntityByQuery(String QueryName) {
        return findEntityByQuery(QueryName, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T findEntityByQuery(String QueryName, Map<String, Object> params) {
        Query query = em.createQuery(QueryName);

        if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        if (query.getResultList() != null && !query.getResultList().isEmpty()) {
            return (T) query.getResultList().get(0);
        } else {
            return null;
        }

    }

    @Override
    public <T> List<T> findListByQuery(String QueryName) {
        return findListByQuery(QueryName, null, -1, -1);
    }

    @Override
    public <T> List<T> findListByQuery(String QueryName,
            Map<String, Object> params) {
        return findListByQuery(QueryName, params, -1, -1);
    }

    @Override
    public <T> List<T> findListByQuery(String QueryName, int start, int maxSize) {
        return findListByQuery(QueryName, null, start, maxSize);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> findListByQuery(String QueryName,
            Map<String, Object> params, int start, int maxSize) {
        Query query = em.createQuery(QueryName);
        if (start > -1) {
            query.setFirstResult(start);
        }
        if (maxSize > -1) {
            query.setMaxResults(maxSize);
        }

        if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        return query.getResultList();
    }

    @Override
    public <T> void saveEntity(T entity) {
        em.persist(entity);
        em.flush();
    }

    @Override
    public <T> T updateEntity(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> cv = iterator.next();
                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
            }
        } else {
            T e = em.merge(entity);
            em.flush();

            return e;
        }
        return null;
    }

    @Override
    public <T> void deleteEntity(T entity) {
        if (entity instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) entity;
            Object managedEntity = em.find(entity.getClass(), baseEntity.getId());
            em.remove(managedEntity);
        } else {
            em.remove(entity);
        }
    }

    @Override
    public void executeDeleteQuery(String jpqlQuery) {
        Query query = em.createQuery(jpqlQuery);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> executeNativeQuery(String jpqlQuery) {
        try {
            return em.createNativeQuery(jpqlQuery).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> executeJPQLQuery(String jpqlQuery) {
        return em.createQuery(jpqlQuery).getResultList();
    }

    @Override
    public <T> T findEntityByCompany(Class<T> entityClass, Serializable id, Serializable companyId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> List<T> findEntityListByCompanyId(Class<T> entityClass, Serializable companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("companyId", companyId);
        return findListByQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.companyId.id=:companyId", params);
    }

    @Override
    public <T> T findEntityByCompany(Class<T> entityClass, Serializable companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("companyId", companyId);
        return findEntityByQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.companyId.id=:companyId", params);
    }

    @Override
    public void checkAndQuery(StringBuilder stringBuilder) {
        if (stringBuilder.toString().equals("")) {
            stringBuilder.append(" where ");
        } else {
            stringBuilder.append(" and ");
        }
    }

    @Override
    public void rollbackQuery() {
        ctx.setRollbackOnly();
    }

    public List<InvItem> creteQueryInvItemView(int first, int pageSize, String sortField, Boolean asc, Integer branchId, Map<String, Object> filters) {
        String filter = " and (s.code like CONCAT('%',:filterName ,'%')  OR s.name  like CONCAT('%',:filterName ,'%')  OR s.nickname like CONCAT('%',:filterName ,'%') OR s.unitId.name like CONCAT('%',:filterName ,'%') OR s.groupId.name like CONCAT('%',:filterName ,'%') ) ";
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT s FROM InvItem s where s.branchId.id = :branchId  ");
        if (filters.get("globalFilter") != null && !filters.get("globalFilter").toString().isEmpty()) {
            builder.append(filter);
        }
        builder.append(" ORDER BY s.id DESC");

        TypedQuery<InvItem> query = em.createQuery(builder.toString(), InvItem.class);
        query.setMaxResults(pageSize);
        query.setFirstResult(first);
        query.setParameter("branchId", branchId);
        if (filters.get("globalFilter") != null && !filters.get("globalFilter").toString().isEmpty()) {
            query.setParameter("filterName", filters.get("globalFilter").toString());
        }
        List<InvItem> x = query.getResultList();

        return x;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice.report;

import com.toby.businessservice.reports.searchBean.SubAccountSummarySearchBean;
import com.toby.core.GenericDAO;
import com.toby.entity.GlYear;
import com.toby.views.CashAccountSettlmentView;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author WIN7
 */
@Stateless
public class CashAccountSettlmentServiceImpl implements CashAccountSettlmentService {

    @EJB
    private GenericDAO dao;

    @Override
    public List<CashAccountSettlmentView> findAllCashAccountSettlment(SubAccountSummarySearchBean cashAccountSettlementSearchBean) {
        Map<String, Object> params = new HashMap();
        params.put("branchId", cashAccountSettlementSearchBean.getBranchId());
        String sql = "SELECT cas FROM CashAccountSettlmentView cas WHERE cas.branchId = :branchId " + createSql(cashAccountSettlementSearchBean, params) ;
        if(cashAccountSettlementSearchBean.getOrderBy() == 0){
            sql = sql + " order by cas.bankId asc ,cas.date ,cas.ordered ";
        }else if(cashAccountSettlementSearchBean.getOrderBy() == 1){
            sql = sql + " order by cas.value asc , cas.bankId asc ,cas.date ,cas.ordered ";
        }else if(cashAccountSettlementSearchBean.getOrderBy() == 2){
            sql = sql + " order by cas.serial asc , cas.bankId asc ,cas.date ,cas.ordered ";
        }
        return dao.findListByQuery(sql, params);
    }

    @Override
    public List<CashAccountSettlmentView> findAllRemainingCashAccountSettlment(SubAccountSummarySearchBean cashAccountSettlementSearchBean, StringBuilder stringBuilder) {
        Map<String, Object> params = new HashMap();
        params.put("branchId", cashAccountSettlementSearchBean.getBranchId());
        String sql = "SELECT cas FROM CashAccountSettlmentView cas WHERE cas.branchId = :branchId";
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        sb.append(" and cas.bankId in ( ");
        sb.append(stringBuilder);
        sb.append(" )");
      //  sb.append(" group by cas.bankId ");

        return dao.findListByQuery(sb.toString(), params);
    }

    @Override
    public List<CashAccountSettlmentView> findAllCashAccountSettlmentForLineCheck(SubAccountSummarySearchBean cashAccountSettlementSearchBean) {
        Map<String, Object> params = new HashMap();
        params.put("branchId", cashAccountSettlementSearchBean.getBranchId());
        String sql = "SELECT cas FROM CashAccountSettlmentView cas WHERE cas.branchId = :branchId and cas.paymentType = 2" + createSql(cashAccountSettlementSearchBean, params);

        return dao.findListByQuery(sql, params);
    }

    @Override
    public BigDecimal findOpeningBalancesForSpecificCashAccount(SubAccountSummarySearchBean cashAccountSettlementSearchBean, Integer bankId, Integer type, GlYear glYear) {
        Map<String, Object> params = new HashMap();
        params.put("branchId", cashAccountSettlementSearchBean.getBranchId());
        params.put("bankId", bankId);
        params.put("type", type);
        StringBuilder builder = new StringBuilder();
        String sql = "SELECT sum(COALESCE(cas.valueLocal,0)) FROM CashAccountSettlmentView cas WHERE cas.branchId = :branchId and cas.status = :type and cas.bankId= :bankId";
        builder.append(sql);
        addDateFromAndToForOpeningBalance(builder, cashAccountSettlementSearchBean, glYear);
        BigDecimal value =  dao.findEntityByQuery(builder.toString(), params);
        return value;
    }

    public String createSql(SubAccountSummarySearchBean cashAccountSettlementSearchBean, Map<String, Object> params) {
        StringBuilder sql = new StringBuilder();
        addGlBankFromAndTo(sql, params, cashAccountSettlementSearchBean);
        addDateFromAndTo(sql, cashAccountSettlementSearchBean);
        addType(sql, params, cashAccountSettlementSearchBean);
        addtransactionType(sql, params, cashAccountSettlementSearchBean);
        addserialFromAndTo(sql, params, cashAccountSettlementSearchBean);
        addUserFromAndTo(sql, params, cashAccountSettlementSearchBean);
        // addOrderBy(sql, cashAccountSettlementSearchBean);
        return sql.toString();
    }

    private void addGlBankFromAndTo(StringBuilder sql, Map<String, Object> params, SubAccountSummarySearchBean cashAccountSettlementSearchBean) {
        if (cashAccountSettlementSearchBean.getGlBankFrom() != null && cashAccountSettlementSearchBean.getGlBankFrom().getId() > 0) {
            sql.append(" and cas.bankId >= :glBankFrom");
            params.put("glBankFrom", cashAccountSettlementSearchBean.getGlBankFrom().getId());
        }

        if (cashAccountSettlementSearchBean.getGlBankTo() != null && cashAccountSettlementSearchBean.getGlBankTo().getId() > 0) {
            sql.append(" and cas.bankId <= :glBankTo");
            params.put("glBankTo", cashAccountSettlementSearchBean.getGlBankTo().getId());
        }
    }

    public void addDateFromAndTo(StringBuilder sql, SubAccountSummarySearchBean cashAccountSettlementSearchBean) {
        if (cashAccountSettlementSearchBean.getDateFrom() != null) {
            String d3 = new SimpleDateFormat("yyyy-MM-dd").format(cashAccountSettlementSearchBean.getDateFrom());
            sql.append(" and cas.date >= '").append(d3).append("'");
        }

        if (cashAccountSettlementSearchBean.getDateTo() != null) {
            String d3 = new SimpleDateFormat("yyyy-MM-dd").format(cashAccountSettlementSearchBean.getDateTo());
            sql.append(" and cas.date <= '").append(d3).append("'");
        }
    }

    private void addType(StringBuilder sql, Map<String, Object> params, SubAccountSummarySearchBean cashAccountSettlementSearchBean) {
        if (cashAccountSettlementSearchBean.getType() != null && cashAccountSettlementSearchBean.getType() != 3) {
            sql.append(" and cas.type = :type");
            params.put("type", cashAccountSettlementSearchBean.getType());
        }
    }
    
    private void addtransactionType(StringBuilder sql, Map<String, Object> params, SubAccountSummarySearchBean cashAccountSettlementSearchBean) {
        if (cashAccountSettlementSearchBean.getTransactionType() != null) {
            sql.append(" and cas.typeId = :transactionType");
            params.put("transactionType", cashAccountSettlementSearchBean.getTransactionType());
        }
    } 
     private void addserialFromAndTo(StringBuilder sql, Map<String, Object> params, SubAccountSummarySearchBean cashAccountSettlementSearchBean) {
        if (cashAccountSettlementSearchBean.getSerialFrom() != null ) {
            sql.append(" and cas.serial >= :serialFrom");
            params.put("serialFrom", cashAccountSettlementSearchBean.getSerialFrom());
        }

        if (cashAccountSettlementSearchBean.getSerialTo() != null ) {
            sql.append(" and cas.serial <= :serialTo");
            params.put("serialTo", cashAccountSettlementSearchBean.getSerialTo());
        }
    }
       private void addUserFromAndTo(StringBuilder sql, Map<String, Object> params, SubAccountSummarySearchBean cashAccountSettlementSearchBean) {
        if (cashAccountSettlementSearchBean.getIsagUserFrom() != null ) {
            sql.append(" and cas.createdBy >= :createdByFrom");
            params.put("createdByFrom", cashAccountSettlementSearchBean.getIsagUserFrom());
        }

        if (cashAccountSettlementSearchBean.getIsagUserTo() != null ) {
            sql.append(" and cas.createdBy <= :createdByTo");
            params.put("createdByTo", cashAccountSettlementSearchBean.getIsagUserTo());
        }
    }

    private void addOrderBy(StringBuilder sql, SubAccountSummarySearchBean cashAccountSettlementSearchBean) {
        if (cashAccountSettlementSearchBean.getOrderBy() != null && cashAccountSettlementSearchBean.getOrderBy() == 0) {
            sql.append(" order by cas.date");
        } else {
            sql.append(" order by cas.value");
        }
    }

    public void addDateFromAndToForOpeningBalance(StringBuilder sql, SubAccountSummarySearchBean cashAccountSettlementSearchBean, GlYear glYear) {
//        if (glYear.getDateFrom() != null) {
//            String d3 = new SimpleDateFormat("yyyy-MM-dd").format(glYear.getDateFrom());
//            sql.append(" and cas.date >= '").append(d3).append("'");
//        }

        if (cashAccountSettlementSearchBean.getDateTo() != null) {
            String d3 = new SimpleDateFormat("yyyy-MM-dd").format(cashAccountSettlementSearchBean.getDateFrom());
            sql.append(" and cas.date ").append("< '").append(d3).append("'");
        }
    }

    @Override
    public List<CashAccountSettlmentView> findOpeningBalancesForSpecificCashAccountByGroups(SubAccountSummarySearchBean cashAccountSettlementSearchBean, Integer type, GlYear glYear) {
        List<CashAccountSettlmentView> cashAccountSettlmentViewReturnedList = new ArrayList<>();

        Map<String, Object> params = new HashMap();
        params.put("branchId", cashAccountSettlementSearchBean.getBranchId());
        params.put("type", type);
        StringBuilder builder = new StringBuilder();
        String sql = "SELECT sum(COALESCE(cas.valueLocal,0)) , cas.bankId FROM CashAccountSettlmentView cas WHERE cas.branchId = :branchId and cas.type = :type ";
        builder.append(sql);
        addDateFromAndToForOpeningBalance(builder, cashAccountSettlementSearchBean, glYear);
        builder.append(" group by cas.bankId");

        List<Object[]> res = dao.findListByQuery(builder.toString(), params);
        cashAccountSettlmentViewReturnedList = getTheListBasedOnBankIdGroup(res);
        return cashAccountSettlmentViewReturnedList;
    }

    public List<CashAccountSettlmentView> getTheListBasedOnBankIdGroup(List<Object[]> res) {

        List<CashAccountSettlmentView> cashAccountSettlmentViewList = new ArrayList<>();

        Iterator it = res.iterator();
        while (it.hasNext()) {
            Object[] line = (Object[]) it.next();
            CashAccountSettlmentView cashsIterated = new CashAccountSettlmentView();
            cashsIterated.setValue((BigDecimal) line[0]);
            cashsIterated.setBankId((Integer) line[1]);
            cashAccountSettlmentViewList.add(cashsIterated);
        }
        return cashAccountSettlmentViewList;
    }
}

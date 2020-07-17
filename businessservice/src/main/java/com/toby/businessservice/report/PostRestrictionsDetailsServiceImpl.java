/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice.report;

import com.toby.core.GenericDAO;
import com.toby.views.PostRestrictionsDetailsView;
import com.toby.views.PostRestrictionsView;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ahmed
 */
@Stateless
public class PostRestrictionsDetailsServiceImpl implements PostRestrictionsDetailsService {

    @EJB
    private GenericDAO dao;

    @Override
    public List<PostRestrictionsDetailsView> getPostRestrictionsDetailsByPostRestrictionsId(Integer glBankTransactionId, Integer moduleCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("glBankTransactionId", glBankTransactionId);
        params.put("moduleCode", moduleCode);
        List<PostRestrictionsDetailsView> details = dao.findListByQuery("SELECT e FROM PostRestrictionsDetailsView e WHERE e.glBankTransactionId = :glBankTransactionId and e.moduleCode= :moduleCode ", params);
        return details;
    }

    @Override
    public List<PostRestrictionsDetailsView> getPostRestrictionsDetailsByBankIdDebitAccountAndDate(PostRestrictionsView postRestrictionsView, Date documentDate) {

        Map<String, Object> params = new HashMap<>();
        params.put("accountId", postRestrictionsView.getAccountId());

        String accountIdClass = "";
        if (postRestrictionsView.getScreenCode() == -1000l) {
            accountIdClass = "e.accountDebitId";
            params.put("transactionType", 1);
        } else {
            accountIdClass = "e.accountCreditId";
            params.put("transactionType", 0);
        }
        params.put("documentDate", documentDate);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select sum(COALESCE(e.value,0)) , e.date , e.accountCreditId , e.accountDebitId ,e.accNumberDebit , e.accNumberCredit ,e.accNameDebit , e.accNameCredit ,e.branchId FROM PostRestrictionsDetailsView e "
                + "WHERE ").append(accountIdClass).append(" = :accountId and e.date = :documentDate and e.transactionType = :transactionType group by e.accountCreditId ,e.accountDebitId,e.date ");
        List<Object[]> res = dao.findListByQuery(stringBuilder.toString(), params);
        List<PostRestrictionsDetailsView> PostRestrictionsViewDetailList = getPostRestrictionViewDetailList(res);
        return PostRestrictionsViewDetailList;
    }

    public List<PostRestrictionsDetailsView> getPostRestrictionViewDetailList(List<Object[]> res) {

        List<PostRestrictionsDetailsView> postRestrictionsDetailsViewList = new ArrayList<>();

        Iterator it = res.iterator();
        while (it.hasNext()) {
            Object[] line = (Object[]) it.next();
            PostRestrictionsDetailsView postRestrictionsDetailsView = new PostRestrictionsDetailsView();
            postRestrictionsDetailsView.setValue((BigDecimal) line[0]);
            postRestrictionsDetailsView.setDate((Date) line[1]);
            postRestrictionsDetailsView.setAccountCreditId((Integer) line[2]);
            postRestrictionsDetailsView.setAccountDebitId((Integer) line[3]);
            postRestrictionsDetailsView.setAccNumberDebit((Integer) line[4]);
            postRestrictionsDetailsView.setAccNumberCredit((Integer) line[5]);
            postRestrictionsDetailsView.setAccNameDebit((String) line[6]);
            postRestrictionsDetailsView.setAccNameCredit((String) line[7]);
            postRestrictionsDetailsView.setBranchId((Integer) line[8]);

            postRestrictionsDetailsViewList.add(postRestrictionsDetailsView);
        }
        return postRestrictionsDetailsViewList;
    }

}

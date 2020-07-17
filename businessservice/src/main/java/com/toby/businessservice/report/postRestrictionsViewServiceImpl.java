/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice.report;

import com.toby.core.GenericDAO;
import com.toby.entity.GeneralJournal;
import com.toby.entity.GlAccount;
import com.toby.entity.GlBankTransaction;
import com.toby.views.PostRestrictionsView;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
public class postRestrictionsViewServiceImpl implements postRestrictionsViewService {

    @EJB
    private GenericDAO dao;

    @Override
    public List<PostRestrictionsView> getPostRestrictionByScreenCode(Integer branchId, Date dateFrom, Date dateTo, Integer DocumentId, Integer postFlag) {
        if (branchId != null && branchId != 179) {
            Map<String, Object> params = new HashMap<>();
            String query = "SELECT glb FROM PostRestrictionsView glb WHERE glb.branchId = :branchId AND glb.screenCode = :DocumentId AND glb.postFlag = :postFlag";
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(query);
            appendDateForQueryBuilder(queryBuilder, dateFrom, dateTo);
            params.put("branchId", branchId);
            params.put("DocumentId", DocumentId);
            params.put("postFlag", postFlag);
            List<PostRestrictionsView> PostRestrictionsViewList = dao.findListByQuery(queryBuilder.toString(), params);
            return PostRestrictionsViewList;
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("branchId", branchId);
            params.put("postFlag", postFlag);

            Long newScreenCode = 0l;
            String accountId = "";
            switch (DocumentId) {
                case 1001:
                    params.put("transactionType", 0);
                    params.put("paymentType", 0);
                    accountId = "glbd.accountCreditId";
                    newScreenCode = -2000L;
                    break;
                case 1002:
                    params.put("transactionType", 0);
                    params.put("paymentType", 1);
                    accountId = "glbd.accountCreditId";
                    newScreenCode = -2000L;
                    break;
                case 1003:
                    params.put("transactionType", 0);
                    params.put("paymentType", 2);
                    accountId = "glbd.accountCreditId";
                    newScreenCode = -2000L;
                    break;
                case 1004:
                    params.put("transactionType", 1);
                    params.put("paymentType", 0);
                    accountId = "glbd.accountDebitId";
                    newScreenCode = -1000L;
                    break;
                case 1005:
                    params.put("transactionType", 1);
                    params.put("paymentType", 1);
                    accountId = "glbd.accountDebitId";
                    newScreenCode = -1000L;
                    break;
                case 1006:
                    params.put("transactionType", 1);
                    params.put("paymentType", 2);
                    accountId = "glbd.accountDebitId";
                    newScreenCode = -1000L;
                    break;
                case 1007:
                    params.put("transactionType", 2);
                    params.put("paymentType", 0);
                    break;
                case 1008:
                    params.put("transactionType", 2);
                    params.put("paymentType", 2);
                    break;
            }

//TODO add another 3
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select glb.id  , glb.date ,").append(accountId).append(" ,sum(COALESCE(glbd.value, 0)),glb.generalJournalId ,glb.glYear ,glb.serial from GlBankTransactionDetail glbd"
                    + " LEFT JOIN glbd.glBankTransactionId glb where glb.branchId.id = :branchId and glb.transactionType = :transactionType and glb.postFlag = :postFlag "
                    + " and glb.paymentType = :paymentType ");
            appendDateForQueryBuilder(stringBuilder, dateFrom, dateTo);
            stringBuilder.append(" group by glb.date , ").append(accountId);
            List<Object[]> res = dao.findListByQuery(stringBuilder.toString(), params);
            List<PostRestrictionsView> PostRestrictionsViewList = getPostRestrictionViewList(res, newScreenCode);
            return PostRestrictionsViewList;
        }
    }

    private void appendDateForQueryBuilder(StringBuilder queryBuilder, Date dateFrom, Date dateTo) {
        if (dateFrom != null) {
            String formatDateFrom = new SimpleDateFormat("yyyy-MM-dd").format(dateFrom);
            queryBuilder.append(" and glb.date >= '").append(formatDateFrom).append("'");
        }

        if (dateTo != null) {
            String formatDateTo = new SimpleDateFormat("yyyy-MM-dd").format(dateTo);
            queryBuilder.append(" and glb.date <= '").append(formatDateTo).append("'");
        }
    }

    public List<PostRestrictionsView> getPostRestrictionViewList(List<Object[]> res, Long newScreenCode) {

        List<PostRestrictionsView> postRestrictionsViewList = new ArrayList<>();

        Iterator it = res.iterator();
        while (it.hasNext()) {
            Object[] line = (Object[]) it.next();
            PostRestrictionsView postRestrictionsView = new PostRestrictionsView();
            postRestrictionsView.setId((Integer) line[0]);
            postRestrictionsView.setDate((Date) line[1]);
            GlAccount account = new GlAccount();
            account = (GlAccount) line[2];
            postRestrictionsView.setAccountId(account != null ? account.getId() : null);
            postRestrictionsView.setValue((BigDecimal) line[3]);
            GeneralJournal generalJournal = new GeneralJournal();
            generalJournal = (GeneralJournal) line[4];
            postRestrictionsView.setGeneralJournalId(generalJournal != null ? generalJournal.getId() : null);
            postRestrictionsView.setGlYear((Integer) line[5]);
            postRestrictionsView.setScreenCode(newScreenCode);
            postRestrictionsView.setSerial((Integer) line[6]);
            postRestrictionsViewList.add(postRestrictionsView);
        }
        return postRestrictionsViewList;
    }

    @Override
    public List<PostRestrictionsView> getTransactionsIdForUpdatePostFlag(Integer BankIdAccount, Date documentDate, Long screenCode) {
        Map<String, Object> params = new HashMap<>();
        String accountClass = "";

        switch (screenCode.intValue()) {
            case -1000:
                params.put("transactionType", 1);
                accountClass = "glbd.accountDebitId.id";
                break;
            case -2000:
                params.put("transactionType", 0);
                accountClass = "glbd.accountCreditId.id";
                break;
        }
        params.put("BankIdAccount", BankIdAccount);
        params.put("documentDate", documentDate);
        // params.put("transactionType", 1);
        // params.put("paymentType", 0);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select glbd.glBankTransactionId from GlBankTransactionDetail glbd"
                + " LEFT JOIN glbd.glBankTransactionId glb where ").append(accountClass).append(" = :BankIdAccount and glb.date = :documentDate and glb.transactionType = :transactionType  ");
        stringBuilder.append(" group by glbd.glBankTransactionId.id");
        List<GlBankTransaction> res = dao.findListByQuery(stringBuilder.toString(), params);
        List<PostRestrictionsView> PostRestrictionsViewList = getglBankTransactionIdList(res);
        return PostRestrictionsViewList;
    }

    public List<PostRestrictionsView> getglBankTransactionIdList(List<GlBankTransaction> res) {

        List<PostRestrictionsView> postRestrictionsViewList = new ArrayList<>();

        for (GlBankTransaction bankTransaction : res) {
            PostRestrictionsView postRestrictionsView = new PostRestrictionsView();
            postRestrictionsView.setId(bankTransaction.getId());
            postRestrictionsViewList.add(postRestrictionsView);
        }
        return postRestrictionsViewList;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.businessservice.report;

import com.toby.entity.GlYear;
import com.toby.views.BankBalanceView;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author hq002
 */
@Remote
public interface BankBalanceViewService {

    public List<BankBalanceView> getAllBankBalancesTransactions(Integer branchId, Date dateFrom, Date dateTo, Integer bankIdFrom, Integer bankIdTo);

    public List<BankBalanceView> getAllBankBalancesTransactionsByBankId(Integer branchId, GlYear glYear, Date dateTo, StringBuilder builder);
}

package edu.nju.hostelworld.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import edu.nju.hostelworld.dao.AccountDao;
import edu.nju.hostelworld.model.Account;
import edu.nju.hostelworld.service.AccountService;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sorumi on 17/3/7.
 */
@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    private AccountDao accountDao;

    @Override
    public Account findAccount(String accountID) {
        return accountDao.findAccount(accountID);
    }

    @Override
    public ResultMessage checkAccount(String accountID) {
        Account account = accountDao.findAccount(accountID);
        if (account == null) {
            return ResultMessage.NOT_EXIST;
        } else {
            return ResultMessage.EXIST;
        }
    }

    @Override
    public ResultMessage deposit(String accountID, double money) {
        Account account = accountDao.findAccount(accountID);
        if (account == null) {
            return ResultMessage.NOT_EXIST;
        }

        double oldMoney = account.getMoney();
        if (oldMoney < money) {
            return ResultMessage.INSUFFICIENT;
        }
        account.setMoney(oldMoney - money);
        return accountDao.updateAccount(account);

    }
}

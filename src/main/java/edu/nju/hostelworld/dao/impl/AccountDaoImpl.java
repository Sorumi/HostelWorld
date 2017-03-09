package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.AccountDao;
import edu.nju.hostelworld.model.Account;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.stereotype.Repository;

/**
 * Created by Sorumi on 17/3/7.
 */

@Repository
public class AccountDaoImpl extends BaseDaoImpl implements AccountDao {
    @Override
    public ResultMessage addAccount(Account account) {
        return add(account);
    }

    @Override
    public ResultMessage updateAccount(Account account) {
        return update(account);
    }

    @Override
    public Account findAccount(String account) {
        return (Account) findByID(Account.class, account);
    }
}

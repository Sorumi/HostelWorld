package edu.nju.hostelworld.dao;

import edu.nju.hostelworld.model.Account;
import edu.nju.hostelworld.util.ResultMessage;

/**
 * Created by Sorumi on 17/3/7.
 */
public interface AccountDao {

    public ResultMessage addAccount(Account account);

    public ResultMessage updateAccount(Account account);

    public Account findAccount(String account);
}

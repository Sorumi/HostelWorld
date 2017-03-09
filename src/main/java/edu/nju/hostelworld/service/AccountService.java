package edu.nju.hostelworld.service;

import edu.nju.hostelworld.model.Account;
import edu.nju.hostelworld.util.ResultMessage;

/**
 * Created by Sorumi on 17/3/7.
 */
public interface AccountService {

    public Account findAccount(String accountID);

    public ResultMessage checkAccount(String accountID);

    public ResultMessage deposit(String accountID, double money);
}

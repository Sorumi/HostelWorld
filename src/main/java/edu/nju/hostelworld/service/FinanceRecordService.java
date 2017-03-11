package edu.nju.hostelworld.service;

import edu.nju.hostelworld.bean.FinanceRecordBean;
import edu.nju.hostelworld.model.BookOrder;
import edu.nju.hostelworld.model.FinanceRecord;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/3/11.
 */
public interface FinanceRecordService {

    public ResultMessage addBookFinanceRecord(BookOrder bookOrder);

    public ResultMessage addCancelFinanceRecord(BookOrder bookOrder);

    public ResultMessage addExpireFinanceRecord(BookOrder bookOrder);

    public ResultMessage addAccountFinanceRecord(BookOrder bookOrder);

    public ResultMessage addDepositFinanceRecord(String memberID, double money);

    public ResultMessage addExchangeFinanceRecord(String memberID, double money);

    public List<FinanceRecordBean> findFinanceMemberRecords(String memberID);

    public List<FinanceRecordBean> findFinanceHostelRecords(String hostelID);

    public List<FinanceRecordBean> findFinanceManagerRecords();

    public FinanceRecord findFinanceRecordByID(String ID);

}

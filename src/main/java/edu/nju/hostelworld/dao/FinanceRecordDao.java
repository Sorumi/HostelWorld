package edu.nju.hostelworld.dao;

import edu.nju.hostelworld.model.FinanceRecord;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/3/10.
 */
public interface FinanceRecordDao {

    public ResultMessage addFinanceRecord(FinanceRecord financeRecord);

    public ResultMessage updateFinanceRecord(FinanceRecord financeRecord);

    public long countFinanceRecords();

    public FinanceRecord findFinanceRecordByID(String ID);

    public List<FinanceRecord> findFinanceMemberRecords(String memberID);

    public List<FinanceRecord> findFinanceHostelRecords(String hostelID);

    public List<FinanceRecord> findFinanceManagerRecords();

    public List<FinanceRecord> findAllFinanceRecords();
}

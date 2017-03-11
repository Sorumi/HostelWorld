package edu.nju.hostelworld;

import edu.nju.hostelworld.dao.FinanceRecordDao;
import edu.nju.hostelworld.model.FinanceRecord;
import edu.nju.hostelworld.util.DateAndTimeUtil;
import edu.nju.hostelworld.util.FinanceType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Sorumi on 17/3/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestFinanceRecordDao {

    @Autowired
    private FinanceRecordDao financeRecordDao;


    @Test
    public void addFinanceRecord() throws Exception {
        FinanceRecord financeRecord = new FinanceRecord();
        financeRecord.setID("0000000002");
        financeRecord.setOrderID("201703020001");
        financeRecord.setTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
        financeRecord.setType(FinanceType.Cancel);
        financeRecord.setMoney(400);
        financeRecordDao.addFinanceRecord(financeRecord);
    }

    @Test
    public void findFinanceMemberRecords() throws Exception {
        List list = financeRecordDao.findFinanceMemberRecords("0000001");
        assertEquals(2, list.size());
    }

    @Test
    public void findFinanceHostelRecords() throws Exception {
        List list = financeRecordDao.findFinanceHostelRecords("0000001");
        assertEquals(0, list.size());
    }

    @Test
    public void findFinanceManagerRecords() throws Exception {
        List list = financeRecordDao.findFinanceManagerRecords();
        assertEquals(2, list.size());
    }

}
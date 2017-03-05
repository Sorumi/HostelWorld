package edu.nju.hostelworld;

import edu.nju.hostelworld.service.FinanceRecordService;
import edu.nju.hostelworld.util.ResultMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by Sorumi on 17/3/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestFinanceRecordServiceImpl {

    @Autowired
    private FinanceRecordService financeRecordService;

    @Test
    public void addBookFinanceRecord() throws Exception {

    }

    @Test
    public void addCancelFinanceRecord() throws Exception {

    }

    @Test
    public void addExpireFinanceRecord() throws Exception {

    }

    @Test
    public void addDepositFinanceRecord() throws Exception {

    }

    @Test
    public void addExchangeFinanceRecord() throws Exception {
        ResultMessage resultMessage = financeRecordService.addExchangeFinanceRecord("0000001", 2);
        assertEquals(ResultMessage.SUCCESS, resultMessage);
    }

}
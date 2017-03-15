package edu.nju.hostelworld;

import edu.nju.hostelworld.dao.AccountDao;
import edu.nju.hostelworld.dao.AppDao;
import edu.nju.hostelworld.model.Account;
import edu.nju.hostelworld.model.App;
import edu.nju.hostelworld.service.AccountService;
import edu.nju.hostelworld.util.ResultMessage;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * Created by Sorumi on 17/2/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestAccountDao extends TestCase {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountService accountService;

    @Test
    public void testAdd() {
        Account account = new Account();
        account.setAccount("12345678901234567893");
        account.setMoney(9000);
        accountDao.addAccount(account);

    }
    @Test
    public void testUpdate() {
        ResultMessage resultMessage = accountService.deposit("12345678901234567890", 100);
        assertEquals(ResultMessage.SUCCESS, resultMessage);
    }

    @Test
    public void testFind() {

    }

}

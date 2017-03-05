package edu.nju.hostelworld;

import edu.nju.hostelworld.dao.AppDao;
import edu.nju.hostelworld.model.App;
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
public class TestAppDao extends TestCase {

    @Autowired
    private AppDao appDao;

    @Test
    public void testUpdate() {
        App app = new App();
        app.setID("0");
        app.setMoney(1);
        app.setCommission(0.1);

        ResultMessage resultMessage = appDao.updateApp(app);
        assertEquals(ResultMessage.SUCCESS, resultMessage);
    }

    @Test
    public void testFind() {
        App app = appDao.findApp();
        assertEquals(10, app.getMoney(), 1);
    }

}

package edu.nju.hostelworld;

import edu.nju.hostelworld.dao.LevelDao;
import edu.nju.hostelworld.model.Level;
import edu.nju.hostelworld.util.ResultMessage;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Sorumi on 17/3/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestLevelDao extends TestCase {

    @Autowired
    private LevelDao levelDao;

    @Test
    public void testAdd() {
        Level level = new Level();
        level.setID(1);
        level.setPoints(200);
        level.setDiscount(0.98);
        ResultMessage resultMessage = levelDao.addLevel(level);
        assertEquals(ResultMessage.SUCCESS, resultMessage);
    }

    @Test
    public void testFind() {
        List list = levelDao.findAllLevels();
        assertEquals(1, list.size());
    }


}

package edu.nju.hostelworld;

import edu.nju.hostelworld.service.ApplicationService;
import edu.nju.hostelworld.util.ApplicationType;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by Sorumi on 17/2/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestApplicationService extends TestCase {

    @Autowired
    private ApplicationService applicationService;

    @Test
    public void testFind() {
        List list = applicationService.findApplicationsByHostelID("0000000", ApplicationType.Open);
        assertEquals(1, list.size());
    }



}

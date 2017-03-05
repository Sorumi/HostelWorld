package edu.nju.hostelworld;

import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.HostelRoom;
import edu.nju.hostelworld.service.HostelService;
import edu.nju.hostelworld.util.City;
import edu.nju.hostelworld.util.ResultMessage;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Sorumi on 17/2/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestHostelService extends TestCase {

    @Autowired
    private HostelService hostelService;

    @Test
    public void testAdd() {
        Hostel hostel = new Hostel();
        hostel.setID("0000001");
        hostel.setUsername("hostel1");
        hostel.setPassword("123");
        hostelService.addHostel(hostel);
    }

    @Test
    public void testFindHostelByID() {
        Hostel hostel = hostelService.findHostelByID("0000001");
        assertNotNull(hostel);
    }

    @Test
    public void testFindAllHostels() {
        List hostels = hostelService.findAllHostels();
        assertEquals(hostels.size(), 2);
    }

    @Test
    public void testAddRoomPlan() {
        HostelRoom hostelRoom = new HostelRoom();
        hostelRoom.setHostelID("0000001");
        hostelRoom.setName("测试房");
        hostelRoom.setQuantity(8);
        hostelRoom.setPrice(120);
        hostelRoom.setStartDate("2017-03-01");
        hostelRoom.setEndDate("2017-03-02");
        String ID = hostelService.addHostelRoom(hostelRoom);
        assertNotNull(ID);
    }

    @Test
    public void testUpdateRommStock() {
        ResultMessage resultMessage = hostelService.updateRoomStock("0000001000", -1, LocalDate.now().plusDays(2), LocalDate.now().plusDays(4));
        assertEquals(ResultMessage.SUCCESS, resultMessage);
    }

    @Test
    public void testFindKeyword() {
        List list = hostelService.findHostelsByCityAndKeywordAndCheckDate(City.ShangHai, "1", LocalDate.now().plusDays(30), LocalDate.now().plusDays(31));
        assertEquals(0, list.size());
    }
}

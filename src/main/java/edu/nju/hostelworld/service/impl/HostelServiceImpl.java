package edu.nju.hostelworld.service.impl;


import edu.nju.hostelworld.bean.HostelPriceBean;
import edu.nju.hostelworld.bean.RoomStockBean;
import edu.nju.hostelworld.dao.HostelDao;
import edu.nju.hostelworld.dao.HostelRoomDao;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.HostelRoom;
import edu.nju.hostelworld.model.RoomStock;
import edu.nju.hostelworld.service.HostelService;
import edu.nju.hostelworld.util.City;
import edu.nju.hostelworld.util.DateAndTimeUtil;
import edu.nju.hostelworld.util.HostelState;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Sorumi on 17/2/5.
 */
@Service
public class HostelServiceImpl implements HostelService {

    @Autowired
    private HostelDao hostelDao;

    @Autowired
    private HostelRoomDao hostelRoomDao;

    @Override
    public ResultMessage addHostel(Hostel hostel) {
        hostel.setID(generateHostelID());
        hostel.setState(HostelState.Unopened);
        return hostelDao.addHostel(hostel);
    }

    @Override
    public ResultMessage updateHostel(Hostel hostel) {
        return hostelDao.updateHostel(hostel);
    }

    @Override
    public ResultMessage updateMoney(String ID, double money) {
        Hostel hostel = hostelDao.findHostelByID(ID);
        if (hostel == null) {
            return ResultMessage.NOT_EXIST;
        } else if (hostel.getState() == HostelState.Unopened) {
            return ResultMessage.FAILED;
        }
        double oldMoney = hostel.getMoney();
//        if (oldMoney + money < 0) {
//            return ResultMessage.INSUFFICIENT;
//        }
        hostel.setMoney(oldMoney + money);
        return hostelDao.updateHostel(hostel);
    }

    @Override
    public Hostel findHostelByID(String ID) {
        return hostelDao.findHostelByID(ID);
    }

    @Override
    public Hostel findHostelByUsername(String username) {
        return hostelDao.findHostelByUsername(username);
    }

    @Override
    public ResultMessage checkHostel(String username, String password) {
        Hostel checkHostel = findHostelByUsername(username);

        if (checkHostel == null) {
            return ResultMessage.NOT_EXIST;
        }
        if (password.equals(checkHostel.getPassword())) {
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAILED;
    }

    @Override
    public List<Hostel> findAllHostels() {
        return hostelDao.findAllHostels();
    }

    public List<Hostel> findAllOpeningHostels() {
        return hostelDao.findHostelsByState(HostelState.Opening);
    }

    public List<Hostel> findHostelsByKeyword(String value) {
        List<Hostel> list1 = hostelDao.findHostelByKeyword("address", value);
        List<Hostel> list2 = hostelDao.findHostelByKeyword("name", value);

        Set<Hostel> set = new HashSet();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<Hostel>(set);
    }

    @Override
    public List<HostelPriceBean> findHostelsByCityAndKeywordAndCheckDate(City city, String value, LocalDate checkInDate, LocalDate checkOutDate) {

        List<Hostel> listCity = hostelDao.findHostelsByCity(city);

        List<Hostel> list1 = hostelDao.findHostelByKeyword("address", value);
        List<Hostel> list2 = hostelDao.findHostelByKeyword("name", value);

        Set<Hostel> set = new HashSet();

        set.addAll(list1);
        set.addAll(list2);

        List<Hostel> list = new ArrayList<Hostel>(set);
        list.retainAll(listCity);

        List<HostelPriceBean> result = new ArrayList<>();
        for (Hostel hostel : list) {

            List<RoomStockBean> rooms = getRoomStockByCheckDate(hostel.getID(), checkInDate, checkOutDate);
            if (rooms.size() > 0) {
                HostelRoom hostelRoom = findHostelRoomByID(rooms.get(0).getID());
                double minPrice = hostelRoom.getPrice();
                for (RoomStockBean roomStockBean : rooms) {
                    hostelRoom = findHostelRoomByID(roomStockBean.getID());
                    if (hostelRoom.getPrice() > minPrice) {
                        minPrice = hostelRoom.getPrice();
                    }
                }
                HostelPriceBean hostelPriceBean = new HostelPriceBean();
                hostelPriceBean.setHostel(hostel);
                hostelPriceBean.setPrice(minPrice);
                result.add(hostelPriceBean);
            }
        }
        return result;
    }

    @Override
    public ResultMessage addHostelRoom(HostelRoom hostelRoom) {
        if (hostelRoom.getHostelID() == null) {
            return ResultMessage.FAILED;
        }

        ResultMessage resultMessage;

        String hostelRoomID = generateHostelRoomID(hostelRoom.getHostelID());
        int quantity = hostelRoom.getQuantity();

        hostelRoom.setID(hostelRoomID);
        hostelRoom.setPlannedTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));

        resultMessage = hostelRoomDao.addHostelRoom(hostelRoom);

        if (resultMessage != ResultMessage.SUCCESS) {
            return resultMessage;
        }

        LocalDate startDate = LocalDate.parse(hostelRoom.getStartDate());
        LocalDate endDate = LocalDate.parse(hostelRoom.getEndDate());
        LocalDate date = startDate;
        int num = 0;
        while (date.isBefore(endDate) || date.isEqual(endDate)) {
            RoomStock roomStock = new RoomStock();
            roomStock.setID(String.format(hostelRoomID + "%03d", num));
            roomStock.setHostelRoomID(hostelRoomID);
            roomStock.setDate(date.toString());
            roomStock.setQuantity(quantity);
            resultMessage = hostelRoomDao.addRoomStock(roomStock);
            date = date.plusDays(1);
            num++;
        }

        return resultMessage;
    }

    @Override
    public HostelRoom findHostelRoomByID(String ID) {
        return hostelRoomDao.findHostelRoomByID(ID);
    }

    @Override
    public List<HostelRoom> findHostelRoomsByHostelID(String hostelID) {
        List hostelRooms = hostelRoomDao.findHostelRoomsByHostelID(hostelID);
        hostelRooms.sort(new HostelRoomComparator());
        return hostelRooms;
    }

    @Override
    public List<RoomStockBean> getRoomStockByDate(String hostelID, LocalDate date) {
        List<RoomStockBean> roomStockBeans = new ArrayList<>();
        List<HostelRoom> rooms = hostelRoomDao.findHostelRoomsByHostelIDAndDate(hostelID, date.toString(), date.toString());
        for (HostelRoom hostelRoom : rooms) {
            RoomStock roomStock = hostelRoomDao.findRoomStockByHostelRoomIDAndDate(hostelRoom.getID(), date.toString());
            RoomStockBean roomStockBean = new RoomStockBean();
            roomStockBean.setID(hostelRoom.getID());
            roomStockBean.setName(hostelRoom.getName());
            roomStockBean.setPrice(hostelRoom.getPrice());
            roomStockBean.setTotalQuantity(hostelRoom.getQuantity());
            roomStockBean.setAvailableQuantity(roomStock.getQuantity());
            roomStockBeans.add(roomStockBean);
        }
        return roomStockBeans;
    }

    @Override
    public List<RoomStockBean> getRoomStockByCheckDate(String hostelID, LocalDate checkInDate, LocalDate checkOutDate) {
        List<RoomStockBean> roomStockBeans = new ArrayList<>();

        String startDate = checkInDate.toString();
        String endDate = checkOutDate.plusDays(-1).toString();

        List<HostelRoom> rooms = hostelRoomDao.findHostelRoomsByHostelIDAndDate(hostelID, startDate, endDate);
        for (HostelRoom hostelRoom : rooms) {
            int minQuantity = hostelRoomDao.minQuantityOfRoomStockByHostelRoomIDAndDate(hostelRoom.getID(), startDate, endDate);
            RoomStockBean roomStockBean = new RoomStockBean();
            roomStockBean.setID(hostelRoom.getID());
            roomStockBean.setName(hostelRoom.getName());
            roomStockBean.setPrice(hostelRoom.getPrice());
            roomStockBean.setTotalQuantity(hostelRoom.getQuantity());
            roomStockBean.setAvailableQuantity(minQuantity);
            roomStockBeans.add(roomStockBean);
        }
        return roomStockBeans;
    }

    @Override
    public ResultMessage updateRoomStock(String hostelRoomID, int quantity, LocalDate checkInDate, LocalDate checkOutDate) {
        ResultMessage resultMessage = ResultMessage.FAILED;
        LocalDate date = checkInDate;
        while (date.isBefore(checkOutDate)) {
            RoomStock roomStock = hostelRoomDao.findRoomStockByHostelRoomIDAndDate(hostelRoomID, date.toString());
            if (roomStock != null) {
                roomStock.setQuantity(roomStock.getQuantity() + quantity);
                resultMessage = hostelRoomDao.updateRoomStock(roomStock);
            }
            date = date.plusDays(1);
        }
        return resultMessage;
    }


    private String generateHostelID() {
        int count = Math.toIntExact(hostelDao.countHostels());
        return String.format("%07d", count);
    }

    private String generateHostelRoomID(String hostelID) {
        int count = Math.toIntExact(hostelRoomDao.countHostelRoomsByHostelID(hostelID));
        return String.format(hostelID + "%03d", count);
    }

    private class HostelRoomComparator implements Comparator<HostelRoom> {
        public int compare(HostelRoom r1, HostelRoom r2) {
            return -r1.getPlannedTime().compareTo(r2.getPlannedTime());
        }
    }
}

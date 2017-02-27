package edu.nju.hostelworld.service;

import edu.nju.hostelworld.Bean.RoomStockBean;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.HostelRoom;
import edu.nju.hostelworld.util.ResultMessage;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Sorumi on 17/2/5.
 */
public interface HostelService {
    public ResultMessage addHostel(Hostel hostel);

    public Hostel findHostelByID(String ID);

    public Hostel findHostelByUsername(String username);

    public ResultMessage checkHostel(String username, String password);

    public List<Hostel> findAllHostels();

    public List<Hostel> findAllOpeningHostels();

    public List<Hostel> findHostelsByKeyword(String value);

    public ResultMessage addHostelRoom(HostelRoom hostelRoom);

    public HostelRoom findHostelRoomByID(String ID);

    public List<HostelRoom> findHostelRoomsByHostelID(String hostelID);

    public List<RoomStockBean> getRoomStockByDate(String hostelID, LocalDate date);

    public List<RoomStockBean> getRoomStockByCheckDate(String hostelID, LocalDate checkInDate, LocalDate checkOutDate);

    public ResultMessage updateRoomStock(String hostelRoomID, int quantity, LocalDate checkInDate, LocalDate checkOutDate);
}

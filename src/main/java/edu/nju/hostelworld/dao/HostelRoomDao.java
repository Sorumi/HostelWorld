package edu.nju.hostelworld.dao;

import edu.nju.hostelworld.model.HostelRoom;
import edu.nju.hostelworld.model.RoomStock;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/2/5.
 */
public interface HostelRoomDao {

    public ResultMessage addHostelRoom(HostelRoom hostelRoom);

    public ResultMessage updateHostelRoom(HostelRoom hostelRoom);

    public ResultMessage deleteHostelRoom(HostelRoom hostelRoom);

    public long countHostelRoomsByHostelID(String hostelID);

    public HostelRoom findHostelRoomByID(String ID);

    public List<HostelRoom> findHostelRoomsByHostelID(String hostelID);

//    public List<HostelRoom> findHostelRoomsByHostelIDAndDate(String hostelID, String date);

    public List<HostelRoom> findHostelRoomsByHostelIDAndDate(String hostelID, String startDate, String endDate);

    // room stock

    public ResultMessage addRoomStock(RoomStock roomStock);

    public ResultMessage updateRoomStock(RoomStock roomStock);

    public ResultMessage deleteRoomStock(RoomStock roomStock);

//    public long countRoomStocksByHostelRoomID(String hostelID);

    public List<RoomStock> findRoomStocksByHostelRoomID(String hostelRoomID);

    public RoomStock findRoomStockByHostelRoomIDAndDate(String hostelRoomID, String date);

    public int minQuantityOfRoomStockByHostelRoomIDAndDate(String hostelRoomID, String startDate, String endDate);

}

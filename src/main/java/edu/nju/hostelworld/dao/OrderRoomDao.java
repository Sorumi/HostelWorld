package edu.nju.hostelworld.dao;

import edu.nju.hostelworld.model.OrderRoom;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/2/8.
 */
public interface OrderRoomDao {

    public ResultMessage addOrderRoom(OrderRoom orderRoom);

    public OrderRoom findOrderRoomByID(String ID);

    public List<OrderRoom> findOrderRoomsByOrderID(String orderID);


//    public List<OrderRoom> findHostelRoomsByHostelIDAndDate(String hostelID, String startDate, String endDate);

}

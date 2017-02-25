package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.OrderRoomDao;
import edu.nju.hostelworld.model.OrderRoom;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Sorumi on 17/2/8.
 */
@Repository
public class OrderRoomDaoImpl extends BaseDaoImpl implements OrderRoomDao {


    public ResultMessage addOrderRoom(OrderRoom orderRoom) {
        return add(orderRoom);
    }

    public OrderRoom findOrderRoomByID(String ID) {
        return (OrderRoom) findByID(OrderRoom.class, ID);
    }

    @Override
    public List<OrderRoom> findOrderRoomsByOrderID(String orderID) {

        return findByFieldAndValue(OrderRoom.class, "orderID", orderID);
    }
}

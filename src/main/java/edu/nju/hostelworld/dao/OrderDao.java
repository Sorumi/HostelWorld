package edu.nju.hostelworld.dao;

import edu.nju.hostelworld.model.BookOrder;
import edu.nju.hostelworld.util.OrderState;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/2/7.
 */
public interface OrderDao {

    public ResultMessage addOrder(BookOrder bookOrder);

    public ResultMessage updateOrder(BookOrder bookOrder);

    public long countOrdersByBookedDate(String bookedDate);

    public BookOrder findOrderByID(String ID);

    public List<BookOrder> findOrderByOrderState(OrderState orderState);

    public List<BookOrder> findAllOrders();

    public List<BookOrder> findMemberOrders(String memberID, OrderState orderState);

    public List<BookOrder> findHostelOrders(String hostelID, OrderState orderState);
}

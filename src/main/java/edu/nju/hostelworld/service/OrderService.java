package edu.nju.hostelworld.service;

import edu.nju.hostelworld.Bean.MemberHostelInfoBean;
import edu.nju.hostelworld.Bean.OrderBean;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.util.OrderState;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/2/7.
 */
public interface OrderService {
    public OrderBean generateOrder(MemberHostelInfoBean memberHostelInfoBean, Member member);

//    public ResultMessage addOrder(BookOrder bookOrder);

    public ResultMessage addNewOrder(OrderBean orderBean);

//    public ResultMessage updateOrder(BookOrder order);

    public ResultMessage cancelOrder(String ID);

    public ResultMessage checkInOrder(String ID);

    public ResultMessage checkOutOrder(String ID);

    public ResultMessage expireOrder(String ID);

    public OrderBean findOrderByID(String ID);

    public List<OrderBean> findOrderByOrderState(OrderState orderState);

    public List<OrderBean> findAllOrders();

    public List<OrderBean> findMemberOrders(String memberID, OrderState orderState);

    public List<OrderBean> findHostelOrders(String hostelID, OrderState orderState);

}

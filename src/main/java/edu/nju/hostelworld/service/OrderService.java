package edu.nju.hostelworld.service;

import edu.nju.hostelworld.bean.HostelBookOrderBean;
import edu.nju.hostelworld.bean.MemberHostelInfoBean;
import edu.nju.hostelworld.bean.OrderBean;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.util.OrderState;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/2/7.
 */
public interface OrderService {
    public OrderBean generateOrder(MemberHostelInfoBean memberHostelInfoBean, Member member);

    public OrderBean generateOrder(HostelBookOrderBean hostelBookOrderBean, Hostel hostel);

    public String addNewOrder(OrderBean orderBean);

    public ResultMessage cancelOrder(String ID);

    public ResultMessage checkInOrder(String ID);

    public ResultMessage checkOutOrder(String ID);

    public ResultMessage accountOrder(String ID);

    public ResultMessage expireOrder(String ID);

    public ResultMessage expireOrders();

    public OrderBean findOrderByID(String ID);

    public List<OrderBean> findOrderByOrderState(OrderState orderState);

    public List<OrderBean> findAllOrders();

    public List<OrderBean> findMemberOrders(String memberID, OrderState orderState);

    public List<OrderBean> findHostelOrders(String hostelID, OrderState orderState);

    public List<Integer> countOrdersByStateAndMonth(OrderState orderState, String month);

    public List<Integer> countMemberOrdersByStateAndYear(String memberID, OrderState orderState, String year);

    public List<Integer> countHostelOrdersByStateAndMonth(String hostelID, OrderState orderState, String month);
}

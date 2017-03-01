package edu.nju.hostelworld.service.impl;

import edu.nju.hostelworld.Bean.MemberHostelInfoBean;
import edu.nju.hostelworld.Bean.OrderBean;
import edu.nju.hostelworld.Bean.OrderRoomBean;
import edu.nju.hostelworld.Bean.RoomStockBean;
import edu.nju.hostelworld.dao.OrderDao;
import edu.nju.hostelworld.dao.OrderRoomDao;
import edu.nju.hostelworld.model.BookOrder;
import edu.nju.hostelworld.model.HostelRoom;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.model.OrderRoom;
import edu.nju.hostelworld.service.AppService;
import edu.nju.hostelworld.service.HostelService;
import edu.nju.hostelworld.service.MemberService;
import edu.nju.hostelworld.service.OrderService;
import edu.nju.hostelworld.util.DateAndTimeUtil;
import edu.nju.hostelworld.util.OrderState;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sorumi on 17/2/7.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderRoomDao orderRoomDao;

    @Autowired
    private HostelService hostelService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AppService appService;

    @Override
    public OrderBean generateOrder(MemberHostelInfoBean memberHostelInfoBean, Member member) {
        List<RoomStockBean> roomStocks = memberHostelInfoBean.getRoomStocks();

        double price = 0;
        List<OrderRoomBean> orderRooms = new ArrayList<>();
        for (int i = 0; i < memberHostelInfoBean.getBookQuantity().size(); i++) {
            Integer quantity = memberHostelInfoBean.getBookQuantity().get(i);
            if (quantity != null && quantity > 0) {
                RoomStockBean roomStockBean = roomStocks.get(i);
                OrderRoomBean orderRoomBean = new OrderRoomBean();
                orderRoomBean.setHostelRoomID(roomStockBean.getID());
                orderRoomBean.setName(roomStockBean.getName());
                orderRoomBean.setPrice(roomStockBean.getPrice());
                orderRoomBean.setQuantity(quantity);
                orderRoomBean.setTotal(roomStockBean.getPrice() * quantity);
                orderRooms.add(orderRoomBean);
                price += roomStockBean.getPrice() * quantity;
            }
        }
        OrderBean orderBean = new OrderBean();
        BookOrder bookOrder = new BookOrder();
        bookOrder.setHostelID(memberHostelInfoBean.getHostel().getID());
//        System.out.println(member.getID());
        bookOrder.setMemberID(member.getID());
        bookOrder.setCheckInDate(memberHostelInfoBean.getCheckInDate());
        bookOrder.setCheckOutDate(memberHostelInfoBean.getCheckOutDate());
        bookOrder.setOriginPrice(price);
        bookOrder.setTotalPrice(price);

        orderBean.setBookOrder(bookOrder);
        orderBean.setRooms(orderRooms);
        orderBean.setHostel(memberHostelInfoBean.getHostel());
        orderBean.setMember(member);

        return orderBean;
    }

    @Override
    public ResultMessage addNewOrder(OrderBean orderBean) {
        ResultMessage resultMessage;
        String orderID = generateOrderID();

        BookOrder bookOrder = orderBean.getBookOrder();
        bookOrder.setID(orderID);
        bookOrder.setBookedTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
        bookOrder.setState(OrderState.UnCheckIn);
        resultMessage = orderDao.addOrder(bookOrder);

        if (resultMessage == ResultMessage.FAILED) {
            return resultMessage;
        }
        memberService.updateMoney(bookOrder.getMemberID(), -bookOrder.getTotalPrice());
        appService.updateMoney(bookOrder.getTotalPrice());

        List<OrderRoomBean> orderRoomBeans = orderBean.getRooms();

        for (int i = 0; i < orderRoomBeans.size(); i++) {
            OrderRoomBean orderRoomBean = orderRoomBeans.get(i);
            OrderRoom orderRoom = new OrderRoom();
            orderRoom.setHostelRoomID(orderRoomBean.getHostelRoomID());
            orderRoom.setQuantity(orderRoomBean.getQuantity());
            orderRoom.setOrderID(orderID);
            orderRoom.setID(orderID + String.format("%02d", i));
            orderRoomDao.addOrderRoom(orderRoom);
            resultMessage = hostelService.updateRoomStock(orderRoom.getHostelRoomID(),
                    -orderRoom.getQuantity(),
                    LocalDate.parse(bookOrder.getCheckInDate()),
                    LocalDate.parse(bookOrder.getCheckOutDate()));
        }
        return resultMessage;
    }

    @Override
    public ResultMessage cancelOrder(String ID) {
        BookOrder bookOrder = orderDao.findOrderByID(ID);
        if (bookOrder.getState() == OrderState.UnCheckIn) {
            bookOrder.setState(OrderState.Cancelled);
            bookOrder.setCancelledTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
            ResultMessage resultMessage = orderDao.updateOrder(bookOrder);

            if (resultMessage == ResultMessage.FAILED) {
                return resultMessage;
            }

            memberService.updateMoney(bookOrder.getMemberID(), bookOrder.getTotalPrice());
            appService.updateMoney(-bookOrder.getTotalPrice());

            List<OrderRoom> orderRooms = orderRoomDao.findOrderRoomsByOrderID(bookOrder.getID());
            for (OrderRoom orderRoom : orderRooms) {
                resultMessage = hostelService.updateRoomStock(orderRoom.getHostelRoomID(),
                        orderRoom.getQuantity(),
                        LocalDate.parse(bookOrder.getCheckInDate()),
                        LocalDate.parse(bookOrder.getCheckOutDate()));
            }
            return resultMessage;

        }
        return ResultMessage.FAILED;
    }

    @Override
    public ResultMessage checkInOrder(String ID) {
        BookOrder bookOrder = orderDao.findOrderByID(ID);
//        if (bookOrder.getState() == OrderState.UnCheckIn && LocalDate.parse(bookOrder.getCheckInDate()).equals(LocalDate.now())) {
            bookOrder.setState(OrderState.CheckIn);
            bookOrder.setCheckInTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
            ResultMessage resultMessage = orderDao.updateOrder(bookOrder);
            if (resultMessage == ResultMessage.SUCCESS) {
                return memberService.addPoint(bookOrder.getMemberID(), (int) bookOrder.getTotalPrice());
            }
//        }
        return ResultMessage.FAILED;
    }

    @Override
    public ResultMessage checkOutOrder(String ID) {
        BookOrder bookOrder = orderDao.findOrderByID(ID);
//        if (bookOrder.getState() == OrderState.CheckIn && LocalDate.parse(bookOrder.getCheckOutDate()).equals(LocalDate.now())) {
            bookOrder.setState(OrderState.CheckOut);
            bookOrder.setCheckOutTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
            ResultMessage resultMessage = orderDao.updateOrder(bookOrder);
        return resultMessage;
//        }
//        return ResultMessage.FAILED;
    }

    @Override
    public ResultMessage accountOrder(String ID) {
        BookOrder bookOrder = orderDao.findOrderByID(ID);
        if (bookOrder.getState() == OrderState.CheckOut && !bookOrder.isAccounted()) {
            bookOrder.setAccounted(true);
            ResultMessage resultMessage = orderDao.updateOrder(bookOrder);
            if (resultMessage == ResultMessage.SUCCESS) {
                appService.updateMoney(-bookOrder.getTotalPrice());
                resultMessage = hostelService.updateMoney(bookOrder.getHostelID(), bookOrder.getTotalPrice());
                return resultMessage;
            }
        }
        return ResultMessage.FAILED;
    }

    @Override
    public ResultMessage expireOrder(String ID) {
        BookOrder bookOrder = orderDao.findOrderByID(ID);
        if (bookOrder.getState() == OrderState.UnCheckIn && LocalDate.parse(bookOrder.getCheckInDate()).isAfter(LocalDate.now())) {
            bookOrder.setState(OrderState.Expired);
            return orderDao.updateOrder(bookOrder);
        }
        return ResultMessage.FAILED;
    }

    @Override
    public OrderBean findOrderByID(String ID) {
        BookOrder bookOrder = orderDao.findOrderByID(ID);
        if (bookOrder == null) {
            return null;
        }
        return orderToOrderBean(bookOrder);
    }

    @Override
    public List<OrderBean> findOrderByOrderState(OrderState orderState) {
        List<BookOrder> bookOrders = orderDao.findOrderByOrderState(orderState);
        return ordersToOrderBeans(bookOrders);
    }

    @Override
    public List<OrderBean> findAllOrders() {
        List<BookOrder> bookOrders = orderDao.findAllOrders();

        return ordersToOrderBeans(bookOrders);
    }

    @Override
    public List<OrderBean> findMemberOrders(String memberID, OrderState orderState) {
        List<BookOrder> bookOrders = orderDao.findMemberOrders(memberID, orderState);
        return ordersToOrderBeans(bookOrders);
    }

    @Override
    public List<OrderBean> findHostelOrders(String hostelID, OrderState orderState) {
        List<BookOrder> bookOrders = orderDao.findHostelOrders(hostelID, orderState);
        return ordersToOrderBeans(bookOrders);
    }

    private String generateOrderID() {
        String date = DateAndTimeUtil.dateStringWithOutSeparator(LocalDate.now());
        int count = Math.toIntExact(orderDao.countOrdersByBookedDate(date));
        return String.format(date + "%04d", count);
    }

    private List<OrderBean> ordersToOrderBeans(List<BookOrder> bookOrders) {
        List<OrderBean> orderBeans = new ArrayList<>();
        for (BookOrder bookOrder : bookOrders) {
            OrderBean orderBean = orderToOrderBean(bookOrder);
            orderBeans.add(orderBean);
        }
        orderBeans.sort(new OrderBookDateComparator());
        return orderBeans;
    }

    private OrderBean orderToOrderBean(BookOrder bookOrder) {
        OrderBean orderBean = new OrderBean();

        List<OrderRoom> orderRooms = orderRoomDao.findOrderRoomsByOrderID(bookOrder.getID());
        List<OrderRoomBean> orderRoomBeans = new ArrayList<>();

        for (OrderRoom orderRoom : orderRooms) {
            OrderRoomBean orderRoomBean = new OrderRoomBean();
            HostelRoom hostelRoom = hostelService.findHostelRoomByID(orderRoom.getHostelRoomID());
            orderRoomBean.setName(hostelRoom.getName());
            orderRoomBean.setPrice(hostelRoom.getPrice());
            orderRoomBean.setQuantity(orderRoom.getQuantity());
            orderRoomBean.setTotal(hostelRoom.getPrice() * orderRoom.getQuantity());
            orderRoomBeans.add(orderRoomBean);
        }

        orderBean.setHostel(hostelService.findHostelByID(bookOrder.getHostelID()));
        orderBean.setMember(memberService.findMemberByID(bookOrder.getMemberID()));
        orderBean.setBookOrder(bookOrder);
        orderBean.setRooms(orderRoomBeans);

        return orderBean;
    }

    private class OrderBookDateComparator implements Comparator<OrderBean> {
        public int compare(OrderBean o1, OrderBean o2) {
            return -o1.getBookOrder().getBookedTime().compareTo(o2.getBookOrder().getBookedTime());
        }
    }
}

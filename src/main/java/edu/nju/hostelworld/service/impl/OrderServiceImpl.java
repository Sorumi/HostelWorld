package edu.nju.hostelworld.service.impl;

import edu.nju.hostelworld.bean.*;
import edu.nju.hostelworld.dao.OrderDao;
import edu.nju.hostelworld.dao.OrderRoomDao;
import edu.nju.hostelworld.model.*;
import edu.nju.hostelworld.service.*;
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

    @Autowired
    private FinanceRecordService financeRecordService;

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
                orderRoomBean.setImageType(roomStockBean.getImageType());
                orderRooms.add(orderRoomBean);
                price += roomStockBean.getPrice() * quantity;
            }
        }
        OrderBean orderBean = new OrderBean();
        BookOrder bookOrder = new BookOrder();
        bookOrder.setHostelID(memberHostelInfoBean.getHostel().getID());
        bookOrder.setMemberID(member.getID());
        bookOrder.setCheckInDate(memberHostelInfoBean.getCheckInDate());
        bookOrder.setCheckOutDate(memberHostelInfoBean.getCheckOutDate());
        bookOrder.setOriginPrice(price);
        double discount = memberService.findLevelByMemberID(member.getID()).getDiscount();
        bookOrder.setDiscount(discount);
        bookOrder.setTotalPrice(price * discount);
        bookOrder.setAccountPrice(price * discount);


        orderBean.setBookOrder(bookOrder);
        orderBean.setRooms(orderRooms);
        orderBean.setHostel(memberHostelInfoBean.getHostel());
        orderBean.setMember(member);

        return orderBean;
    }

    @Override
    public OrderBean generateOrder(HostelBookOrderBean hostelBookOrderBean, Hostel hostel) {
        List<RoomStockBean> roomStocks = hostelBookOrderBean.getRoomStocks();

        double price = 0;
        List<OrderRoomBean> orderRooms = new ArrayList<>();
        for (int i = 0; i < hostelBookOrderBean.getBookQuantity().size(); i++) {
            Integer quantity = hostelBookOrderBean.getBookQuantity().get(i);
            if (quantity != null && quantity > 0) {
                RoomStockBean roomStockBean = roomStocks.get(i);
                OrderRoomBean orderRoomBean = new OrderRoomBean();
                orderRoomBean.setHostelRoomID(roomStockBean.getID());
                orderRoomBean.setName(roomStockBean.getName());
                orderRoomBean.setPrice(roomStockBean.getPrice());
                orderRoomBean.setQuantity(quantity);
                orderRoomBean.setTotal(roomStockBean.getPrice() * quantity);
                orderRoomBean.setImageType(roomStockBean.getImageType());
                orderRooms.add(orderRoomBean);
                price += roomStockBean.getPrice() * quantity;
            }
        }

        OrderBean orderBean = new OrderBean();
        BookOrder bookOrder = new BookOrder();
        bookOrder.setHostelID(hostel.getID());
        bookOrder.setNotMemberName(hostelBookOrderBean.getName());
        bookOrder.setNotMemberContact(hostelBookOrderBean.getContact());
        bookOrder.setPeopleQuantity(hostelBookOrderBean.getPeopleQuantity());
        bookOrder.setCheckInDate(hostelBookOrderBean.getCheckInDate());
        bookOrder.setCheckOutDate(hostelBookOrderBean.getCheckOutDate());
        bookOrder.setOriginPrice(price);
        bookOrder.setTotalPrice(price);
        bookOrder.setAccountPrice(price);

        orderBean.setBookOrder(bookOrder);
        orderBean.setRooms(orderRooms);
        orderBean.setHostel(hostel);

        return orderBean;
    }

    @Override
    public String addNewOrder(OrderBean orderBean) {
        ResultMessage resultMessage;
        String orderID = generateOrderID();

        BookOrder bookOrder = orderBean.getBookOrder();
        bookOrder.setID(orderID);
        bookOrder.setBookedTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
        bookOrder.setState(OrderState.UnCheckIn);
        resultMessage = orderDao.addOrder(bookOrder);

        if (resultMessage == ResultMessage.FAILED) {
            return null;
        }
        if (bookOrder.getMemberID() != null) {
            memberService.resume(bookOrder.getMemberID());
            memberService.updateMoney(bookOrder.getMemberID(), -bookOrder.getTotalPrice());
            appService.updateMoney(bookOrder.getTotalPrice());
            financeRecordService.addBookFinanceRecord(bookOrder);
        }


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

        if (resultMessage == ResultMessage.SUCCESS) {
            return orderID;
        } else {
            return null;
        }

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

            if (bookOrder.getMemberID() != null) {
                memberService.updateMoney(bookOrder.getMemberID(), bookOrder.getTotalPrice());
                appService.updateMoney(-bookOrder.getTotalPrice());
                financeRecordService.addCancelFinanceRecord(bookOrder);
            }

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
        if (bookOrder.getState() == OrderState.UnCheckIn) { // && LocalDate.parse(bookOrder.getCheckInDate()).equals(LocalDate.now())) {
            bookOrder.setState(OrderState.CheckIn);
            bookOrder.setCheckInTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
            ResultMessage resultMessage = orderDao.updateOrder(bookOrder);
            if (resultMessage == ResultMessage.SUCCESS && bookOrder.getMemberID() != null) {
                return memberService.addPoint(bookOrder.getMemberID(), (int) bookOrder.getTotalPrice());
            }

        }
        return ResultMessage.FAILED;
    }

    @Override
    public ResultMessage checkOutOrder(String ID) {
        BookOrder bookOrder = orderDao.findOrderByID(ID);
        if (bookOrder.getState() == OrderState.CheckIn) { // && LocalDate.parse(bookOrder.getCheckOutDate()).equals(LocalDate.now())) {
            bookOrder.setState(OrderState.CheckOut);
            bookOrder.setCheckOutTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
            ResultMessage resultMessage = orderDao.updateOrder(bookOrder);
            return resultMessage;
        }
        return ResultMessage.FAILED;
    }

    @Override
    public ResultMessage accountOrder(String ID) {
        BookOrder bookOrder = orderDao.findOrderByID(ID);
        if (bookOrder.getMemberID() == null) return ResultMessage.FAILED;

        App app = appService.findApp();
        double commission = app.getCommission();
        double accountPrice = bookOrder.getTotalPrice() - bookOrder.getTotalPrice() * commission;

        if (bookOrder.getState() == OrderState.CheckOut && bookOrder.getAccountedTime() == null) {
            bookOrder.setCommission(commission);
            bookOrder.setAccountPrice(accountPrice);
            bookOrder.setAccountedTime(DateAndTimeUtil.timeStringWithHyphen(LocalDateTime.now()));
            ResultMessage resultMessage = orderDao.updateOrder(bookOrder);
            if (resultMessage == ResultMessage.SUCCESS) {
                appService.updateMoney(-accountPrice);
                resultMessage = hostelService.updateMoney(bookOrder.getHostelID(), accountPrice);
                if (resultMessage == ResultMessage.SUCCESS) {
                    return financeRecordService.addAccountFinanceRecord(bookOrder);
                }
            }
        }
        return ResultMessage.FAILED;
    }

    @Override
    public ResultMessage expireOrder(String ID) {
        BookOrder bookOrder = orderDao.findOrderByID(ID);
        if (bookOrder.getState() == OrderState.UnCheckIn && LocalDate.parse(bookOrder.getCheckInDate()).isBefore(LocalDate.now())) {
            bookOrder.setState(OrderState.Expired);
            ResultMessage resultMessage = orderDao.updateOrder(bookOrder);
            if (resultMessage == ResultMessage.SUCCESS) {
                return financeRecordService.addExpireFinanceRecord(bookOrder);
            }
        }
        return ResultMessage.FAILED;
    }

    @Override
    public ResultMessage expireOrders() {
        ResultMessage resultMessage = ResultMessage.SUCCESS;
        List<BookOrder> list = orderDao.findOrderByOrderStateAndMaxDate(OrderState.UnCheckIn, "checkInDate", LocalDate.now().minusDays(1).toString());
        for (BookOrder order : list) {
            order.setState(OrderState.Expired);
            resultMessage = orderDao.updateOrder(order);
            resultMessage = financeRecordService.addExpireFinanceRecord(order);
            if (resultMessage == ResultMessage.FAILED) break;
        }
        return resultMessage;
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

    @Override
    public List<Integer> countOrdersByStateAndMonth(OrderState orderState, String month) {

        String field = fieldOfOrderState(orderState);
        if (orderState != OrderState.Expired) orderState = null;
        LocalDate date = LocalDate.parse(month + "-01");
        LocalDate endDate = date.plusMonths(1);
        LocalDate today = LocalDate.now();
        List<Integer> counts = new ArrayList<>();
        for (; date.isBefore(endDate); date = date.plusDays(1)) {
            if (date.isAfter(today)) {
                counts.add(null);
            } else {
                int count = Math.toIntExact(orderDao.countOrdersByStateAndDate(orderState, field, date.toString()));
                counts.add(count);
            }
        }
        return counts;
    }

    @Override
    public List<Integer> countMemberOrdersByStateAndYear(String memberID, OrderState orderState, String year) {
        String field = fieldOfOrderState(orderState);
        if (orderState != OrderState.Expired) orderState = null;
        LocalDate date = LocalDate.parse(year + "-01-01");
        LocalDate endDate = date.plusYears(1);
        LocalDate today = LocalDate.now();
        List<Integer> counts = new ArrayList<>();
        for (; date.isBefore(endDate); date = date.plusMonths(1)) {
            if (date.isAfter(today)) {
                counts.add(null);
            } else {
                int count = Math.toIntExact(orderDao.countMemberOrdersByStateAndDate(memberID, orderState, field, DateAndTimeUtil.monthStringWithHyphen(date)));
                counts.add(count);
            }
        }
        return counts;
    }

    @Override
    public List<Integer> countHostelOrdersByStateAndMonth(String hostelID, OrderState orderState, String month) {
        String field = fieldOfOrderState(orderState);
        if (orderState != OrderState.Expired) orderState = null;
        LocalDate date = LocalDate.parse(month + "-01");
        LocalDate endDate = date.plusMonths(1);
        LocalDate today = LocalDate.now();
        List<Integer> counts = new ArrayList<>();
        for (; date.isBefore(endDate); date = date.plusDays(1)) {
            if (date.isAfter(today)) {
                counts.add(null);
            } else {
                int count = Math.toIntExact(orderDao.countHostelOrdersByStateAndDate(hostelID, orderState, field, date.toString()));
                counts.add(count);
            }
        }
        return counts;
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
            orderRoomBean.setHostelRoomID(hostelRoom.getID());
            orderRoomBean.setName(hostelRoom.getName());
            orderRoomBean.setPrice(hostelRoom.getPrice());
            orderRoomBean.setQuantity(orderRoom.getQuantity());
            orderRoomBean.setTotal(hostelRoom.getPrice() * orderRoom.getQuantity());
            orderRoomBean.setImageType(hostelRoom.getImageType());
            orderRoomBeans.add(orderRoomBean);
        }

        orderBean.setHostel(hostelService.findHostelByID(bookOrder.getHostelID()));
        if (bookOrder.getMemberID() == null) {
            Member member = new Member();
            member.setName(bookOrder.getNotMemberName());
            member.setContact(bookOrder.getNotMemberContact());
            orderBean.setMember(member);
        } else {
            orderBean.setMember(memberService.findMemberByID(bookOrder.getMemberID()));
        }
        orderBean.setBookOrder(bookOrder);
        orderBean.setRooms(orderRoomBeans);

        return orderBean;
    }

    private class OrderBookDateComparator implements Comparator<OrderBean> {
        public int compare(OrderBean o1, OrderBean o2) {
            return -o1.getBookOrder().getBookedTime().compareTo(o2.getBookOrder().getBookedTime());
        }
    }

    private String fieldOfOrderState(OrderState orderState) {
        if (orderState != null) {
            switch (orderState) {
                case UnCheckIn:
                    return "bookedTime";
                case CheckIn:
                    return "checkInTime";
                case CheckOut:
                    return "checkOutTime";
                case Cancelled:
                    return "cancelledTime";
                case Expired:
                    return "checkInDate";
            }
        }
        return "bookedTime";
    }
}

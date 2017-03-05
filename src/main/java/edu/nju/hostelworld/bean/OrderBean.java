package edu.nju.hostelworld.bean;

import edu.nju.hostelworld.model.BookOrder;
import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.model.Member;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Sorumi on 17/2/7.
 */
public class OrderBean {

    private BookOrder bookOrder;

    private List<OrderRoomBean> rooms;

    private Hostel hostel;

    private Member member;

    private String today;

    public BookOrder getBookOrder() {
        return bookOrder;
    }

    public void setBookOrder(BookOrder bookOrder) {
        this.bookOrder = bookOrder;
    }

    public List<OrderRoomBean> getRooms() {
        return rooms;
    }

    public void setRooms(List<OrderRoomBean> rooms) {
        this.rooms = rooms;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }
}

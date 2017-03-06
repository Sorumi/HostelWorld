package edu.nju.hostelworld.bean;

import edu.nju.hostelworld.model.Hostel;

import java.util.List;

/**
 * Created by Sorumi on 17/2/6.
 */
public class MemberHostelInfoBean {

    private Hostel hostel;

    private String checkInDate;

    private String checkOutDate;

    private List<RoomStockBean> roomStocks;

    private List<Integer> bookQuantity;

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public List<RoomStockBean> getRoomStocks() {
        return roomStocks;
    }

    public void setRoomStocks(List<RoomStockBean> roomStocks) {
        this.roomStocks = roomStocks;
    }

    public List<Integer> getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(List<Integer> bookQuantity) {
        this.bookQuantity = bookQuantity;
    }
}

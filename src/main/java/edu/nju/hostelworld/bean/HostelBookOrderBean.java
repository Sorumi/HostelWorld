package edu.nju.hostelworld.bean;

import java.util.List;

/**
 * Created by Sorumi on 17/3/1.
 */
public class HostelBookOrderBean {
    private String name;

    private String contact;

    private int peopleQuantity;

    private String checkInDate;

    private String checkOutDate;

    private List<RoomStockBean> roomStocks;

    private List<Integer> bookQuantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getPeopleQuantity() {
        return peopleQuantity;
    }

    public void setPeopleQuantity(int peopleQuantity) {
        this.peopleQuantity = peopleQuantity;
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

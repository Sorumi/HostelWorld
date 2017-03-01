package edu.nju.hostelworld.model;

import edu.nju.hostelworld.util.OrderState;

import javax.persistence.*;

/**
 * Created by Sorumi on 17/2/3.
 */
@Entity
@Table(name = "BookOrder")
public class BookOrder {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String ID;

    private String hostelID;

    private String memberID;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    private String bookedTime;

    private String cancelledTime;

    private String checkInDate;

    private String checkOutDate;

    private String checkInTime;

    private String checkOutTime;

    private int peopleQuantity;

    private double originPrice;

    private double totalPrice;

    private boolean accounted;

    private String notMemberName;

    private String notMemberContact;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getHostelID() {
        return hostelID;
    }

    public void setHostelID(String hostelID) {
        this.hostelID = hostelID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(String bookedTime) {
        this.bookedTime = bookedTime;
    }

    public String getCancelledTime() {
        return cancelledTime;
    }

    public void setCancelledTime(String cancelledTime) {
        this.cancelledTime = cancelledTime;
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

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public int getPeopleQuantity() {
        return peopleQuantity;
    }

    public void setPeopleQuantity(int peopleQuantity) {
        this.peopleQuantity = peopleQuantity;
    }

    public double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(double originPrice) {
        this.originPrice = originPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isAccounted() {
        return accounted;
    }

    public void setAccounted(boolean accounted) {
        this.accounted = accounted;
    }

    public String getNotMemberName() {
        return notMemberName;
    }

    public void setNotMemberName(String notMemberName) {
        this.notMemberName = notMemberName;
    }

    public String getNotMemberContact() {
        return notMemberContact;
    }

    public void setNotMemberContact(String notMemberContact) {
        this.notMemberContact = notMemberContact;
    }
}

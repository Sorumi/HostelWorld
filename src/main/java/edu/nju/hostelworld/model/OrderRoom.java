package edu.nju.hostelworld.model;

import javax.persistence.*;

/**
 * Created by Sorumi on 17/2/3.
 */
@Entity
@Table(name = "OrderRoom")
public class OrderRoom {

    @Id
//    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private String ID;

    private String orderID;

    private String hostelRoomID;

    private int quantity;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getHostelRoomID() {
        return hostelRoomID;
    }

    public void setHostelRoomID(String hostelRoomID) {
        this.hostelRoomID = hostelRoomID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

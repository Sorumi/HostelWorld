package edu.nju.hostelworld.model;

import javax.persistence.*;

/**
 * Created by Sorumi on 17/2/3.
 */
@Entity
@Table(name = "RoomStock")
public class RoomStock {

    @Id
//    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private String ID;

    private String hostelRoomID;

    private String date;

    private int quantity;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getHostelRoomID() {
        return hostelRoomID;
    }

    public void setHostelRoomID(String hostelRoomID) {
        this.hostelRoomID = hostelRoomID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

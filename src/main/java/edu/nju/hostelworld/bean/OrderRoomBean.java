package edu.nju.hostelworld.bean;

/**
 * Created by Sorumi on 17/2/8.
 */
public class OrderRoomBean {

    private String hostelRoomID;

    private String name;

    private double price;

    private int quantity;

    private double total;

    public String getHostelRoomID() {
        return hostelRoomID;
    }

    public void setHostelRoomID(String hostelRoomID) {
        this.hostelRoomID = hostelRoomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

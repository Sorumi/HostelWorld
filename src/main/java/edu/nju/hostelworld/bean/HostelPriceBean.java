package edu.nju.hostelworld.bean;

import edu.nju.hostelworld.model.Hostel;

/**
 * Created by Sorumi on 17/3/7.
 */
public class HostelPriceBean {

    private Hostel hostel;

    private double price;

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

package edu.nju.hostelworld.Bean;

import edu.nju.hostelworld.model.Hostel;

/**
 * Created by Sorumi on 17/2/25.
 */
public class SearchRoomJsonBean {

    private String hostelID;

    private String checkInDate;

    private String checkOutDate;

    private String date;

    public String getHostelID() {
        return hostelID;
    }

    public void setHostelID(String hostelID) {
        this.hostelID = hostelID;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

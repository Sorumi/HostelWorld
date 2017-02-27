package edu.nju.hostelworld.Bean;

import edu.nju.hostelworld.model.Hostel;

import java.util.List;

/**
 * Created by Sorumi on 17/2/5.
 */
public class SearchHostelBean {

    private String keyword;

    private String checkInDate;

    private String checkOutDate;

    private List<Hostel> hostels;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public List<Hostel> getHostels() {
        return hostels;
    }

    public void setHostels(List<Hostel> hostels) {
        this.hostels = hostels;
    }
}

package edu.nju.hostelworld.bean;

import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.util.City;

import java.util.List;

/**
 * Created by Sorumi on 17/2/5.
 */
public class SearchHostelBean {

    private City city;

    private String keyword;

    private String checkInDate;

    private String checkOutDate;

    private List<HostelPriceBean> hostels;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

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

    public List<HostelPriceBean> getHostels() {
        return hostels;
    }

    public void setHostels(List<HostelPriceBean> hostels) {
        this.hostels = hostels;
    }
}

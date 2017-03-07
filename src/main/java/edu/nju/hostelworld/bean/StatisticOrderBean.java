package edu.nju.hostelworld.bean;

import java.util.List;

/**
 * Created by Sorumi on 17/3/5.
 */
public class StatisticOrderBean {
    List<Integer> booked;

    List<Integer> checkIn;

    List<Integer> checkOut;

    List<Integer> cancelled;

    List<Integer> expired;

    long time;

    public List<Integer> getBooked() {
        return booked;
    }

    public void setBooked(List<Integer> booked) {
        this.booked = booked;
    }

    public List<Integer> getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(List<Integer> checkIn) {
        this.checkIn = checkIn;
    }

    public List<Integer> getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(List<Integer> checkOut) {
        this.checkOut = checkOut;
    }

    public List<Integer> getCancelled() {
        return cancelled;
    }

    public void setCancelled(List<Integer> cancelled) {
        this.cancelled = cancelled;
    }

    public List<Integer> getExpired() {
        return expired;
    }

    public void setExpired(List<Integer> expired) {
        this.expired = expired;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

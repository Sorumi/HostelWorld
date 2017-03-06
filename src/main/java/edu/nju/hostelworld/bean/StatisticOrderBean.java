package edu.nju.hostelworld.bean;

/**
 * Created by Sorumi on 17/3/5.
 */
public class StatisticOrderBean {
    int[] booked;

    int[] checkIn;

    int[] checkOut;

    int[] cancelled;

    int[] expired;

    long time;

    public int[] getBooked() {
        return booked;
    }

    public void setBooked(int[] booked) {
        this.booked = booked;
    }

    public int[] getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(int[] checkIn) {
        this.checkIn = checkIn;
    }

    public int[] getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(int[] checkOut) {
        this.checkOut = checkOut;
    }

    public int[] getCancelled() {
        return cancelled;
    }

    public void setCancelled(int[] cancelled) {
        this.cancelled = cancelled;
    }

    public int[] getExpired() {
        return expired;
    }

    public void setExpired(int[] expired) {
        this.expired = expired;
    }
}

package edu.nju.hostelworld.Bean;

import java.util.List;

/**
 * Created by Sorumi on 17/2/5.
 */
public class HostelSearchRoomBean {

    private String date;

    private List<RoomStockBean> rooms;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<RoomStockBean> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomStockBean> rooms) {
        this.rooms = rooms;
    }
}

package edu.nju.hostelworld.Bean;

import edu.nju.hostelworld.model.HostelRoom;

import java.util.List;

/**
 * Created by Sorumi on 17/2/5.
 */
public class HostelRoomListBean {

    private List<HostelRoom> hostelRooms;

    public List<HostelRoom> getHostelRooms() {
        return hostelRooms;
    }

    public void setHostelRooms(List<HostelRoom> hostelRooms) {
        this.hostelRooms = hostelRooms;
    }
}

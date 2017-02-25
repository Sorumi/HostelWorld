package edu.nju.hostelworld.dao;

import edu.nju.hostelworld.model.Hostel;
import edu.nju.hostelworld.util.HostelState;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/2/5.
 */
public interface HostelDao {

    public ResultMessage addHostel(Hostel hostel);

    public ResultMessage updateHostel(Hostel hostel);

    public long countHostels();

    public Hostel findHostelByID(String ID);

    public Hostel findHostelByUsername(String username);

    public List<Hostel> findAllHostels();

    public List<Hostel> findHostelsByState(HostelState state);

    public List<Hostel> findHostelByKeyword(String keword, String value);

}

package edu.nju.hostelworld.bean;

import edu.nju.hostelworld.model.Application;
import edu.nju.hostelworld.model.Hostel;

/**
 * Created by Sorumi on 17/2/28.
 */
public class HostelInfoBean {

    private Hostel hostel;

    private Application openApplication;

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public Application getOpenApplication() {
        return openApplication;
    }

    public void setOpenApplication(Application openApplication) {
        this.openApplication = openApplication;
    }
}

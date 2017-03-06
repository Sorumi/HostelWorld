package edu.nju.hostelworld.bean;

import edu.nju.hostelworld.model.Application;
import edu.nju.hostelworld.model.Hostel;

/**
 * Created by Sorumi on 17/2/28.
 */
public class ApplicationBean {

    private Application application;

    private Hostel hostel;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }
}

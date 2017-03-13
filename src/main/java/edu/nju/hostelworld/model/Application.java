package edu.nju.hostelworld.model;

import edu.nju.hostelworld.util.ApplicationState;
import edu.nju.hostelworld.util.ApplicationType;
import edu.nju.hostelworld.util.City;

import javax.persistence.*;

/**
 * Created by Sorumi on 17/2/5.
 */
@Entity
@Table(name = "Application")
public class Application {

    @Id
//    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private String ID;

    @Enumerated(EnumType.STRING)
    private ApplicationType type;

    @Enumerated(EnumType.STRING)
    private ApplicationState state;

    private String appliedTime;

    private String checkedTime;

    private String hostelID;

    private String name;

    @Enumerated(EnumType.STRING)
    private City city;

    private String address;

    private String introduction;

    private String facility;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ApplicationType getType() {
        return type;
    }

    public void setType(ApplicationType type) {
        this.type = type;
    }

    public ApplicationState getState() {
        return state;
    }

    public void setState(ApplicationState state) {
        this.state = state;
    }

    public String getAppliedTime() {
        return appliedTime;
    }

    public void setAppliedTime(String appliedTime) {
        this.appliedTime = appliedTime;
    }

    public String getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(String checkedTime) {
        this.checkedTime = checkedTime;
    }

    public String getHostelID() {
        return hostelID;
    }

    public void setHostelID(String hostelID) {
        this.hostelID = hostelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }
}

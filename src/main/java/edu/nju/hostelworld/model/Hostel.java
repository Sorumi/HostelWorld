package edu.nju.hostelworld.model;

import edu.nju.hostelworld.util.City;
import edu.nju.hostelworld.util.HostelState;

import javax.persistence.*;

/**
 * Created by Sorumi on 17/2/3.
 */
@Entity
@Table(name = "Hostel")
public class Hostel {

    @Id
//    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private String ID;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private HostelState state;

    private String name;

    @Enumerated(EnumType.STRING)
    private City city;

    private String address;

    private String introduction;

    private String facility;

    private String imageType;

    private double money;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HostelState getState() {
        return state;
    }

    public void setState(HostelState state) {
        this.state = state;
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

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(ID);
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() != Hostel.class) {
            return false;
        }
        Hostel hostel = (Hostel) object;
        return this.ID.equals(hostel.ID);
    }
}

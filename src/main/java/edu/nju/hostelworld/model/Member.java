package edu.nju.hostelworld.model;

import edu.nju.hostelworld.util.MemberState;

import javax.persistence.*;

/**
 * Created by Sorumi on 17/2/2.
 */

@Entity
@Table(name = "Member")
public class Member {

    @Id
//    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private String ID;

    private String username;

    private String password;

    private String name;

    private String account;

    private String contact;

    private double money;

    private int point;

    @Enumerated(EnumType.STRING)
    private MemberState state;

    private String pauseDate;

    private String startDate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public MemberState getState() {
        return state;
    }

    public void setState(MemberState state) {
        this.state = state;
    }

    public String getPauseDate() {
        return pauseDate;
    }

    public void setPauseDate(String pauseDate) {
        this.pauseDate = pauseDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}

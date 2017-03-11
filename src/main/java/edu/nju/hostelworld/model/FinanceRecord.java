package edu.nju.hostelworld.model;

import edu.nju.hostelworld.util.FinanceType;

import javax.persistence.*;

/**
 * Created by Sorumi on 17/3/10.
 */

@Entity
@Table(name = "FinanceRecord")
public class FinanceRecord {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String ID;

    private String time;

    @Enumerated(EnumType.STRING)
    private FinanceType type;

    private String orderID;

    private String memberID;

    private double money;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public FinanceType getType() {
        return type;
    }

    public void setType(FinanceType type) {
        this.type = type;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }


}

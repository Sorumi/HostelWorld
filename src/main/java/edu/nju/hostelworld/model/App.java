package edu.nju.hostelworld.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Sorumi on 17/2/11.
 */
@Entity
@Table(name = "App")
public class App {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String  ID;

    private double money;

    private double commission;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }
}

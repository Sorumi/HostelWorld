package edu.nju.hostelworld.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Sorumi on 17/3/7.
 */
@Entity
@Table(name = "Account")
public class Account {

    @Id
    @Column(name = "account", unique = true, nullable = false)
    private String account;

    private double money;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}

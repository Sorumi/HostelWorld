package edu.nju.hostelworld.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Sorumi on 17/3/2.
 */
@Entity
@Table(name = "Level")
public class Level {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int ID;

    private int points;

    private double discount;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}

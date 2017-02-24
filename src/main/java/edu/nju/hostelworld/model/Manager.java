package edu.nju.hostelworld.model;

import javax.persistence.*;

/**
 * Created by Sorumi on 17/2/3.
 */
@Entity
@Table(name = "Manager")
public class Manager {

    @Id
//    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private String ID;

    private String username;

    private String password;

    private String name;

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
}

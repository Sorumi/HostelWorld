package edu.nju.hostelworld.bean;

/**
 * Created by Sorumi on 17/2/26.
 */
public class MemberRegisterBean {

    private String username;

    private String password;

    private String confirmPassword;

    private String name;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmedPassword) {
        this.confirmPassword = confirmedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

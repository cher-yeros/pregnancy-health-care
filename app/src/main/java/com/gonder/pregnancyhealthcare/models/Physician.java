package com.gonder.pregnancyhealthcare.models;

import com.gonder.pregnancyhealthcare.util.UserType;

public class Physician {
    private String fullname;
    private String phone;
    private String username;
    private String type;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Physician() {
        this.type = UserType.physician;
    }

    public Physician(String firstname, String phone) {
        this.fullname = firstname;
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

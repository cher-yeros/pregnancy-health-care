package com.gonder.pregnancyhealthcare.models;

import com.gonder.pregnancyhealthcare.util.UserType;
import com.google.firebase.database.Exclude;

public class Mother {
    private String fullname;
    private String password;
    private String username;
    private int age;
    private String phone;
    private String phyId;
    private String type;
    private String status;
    private long regDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhyId() {
        return phyId;
    }

    public void setPhyId(String phyId) {
        this.phyId = phyId;
    }

    public Mother() {
        this.type = UserType.mother;
    }


    public Mother(int age, String firstname, String phone) {
        this.age = age;
        this.fullname = firstname;
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getRegDate() {
        return regDate;
    }

    public void setRegDate(long regDate) {
        this.regDate = regDate;
    }
}

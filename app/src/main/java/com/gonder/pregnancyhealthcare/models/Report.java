package com.gonder.pregnancyhealthcare.models;

public class Report {
    private int no;
    private String title;
    private String status;
    private String sender;
    private String receiver;

    public Report(int no, String title, String status) {
        this.no = no;
        this.title = title;
        this.status = status;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getBody() {
        return title;
    }

    public void setBody(String title) {
        this.title = title;
    }

    public String getDate() {
        return status;
    }

    public void setDate(String date) {
        this.status = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}

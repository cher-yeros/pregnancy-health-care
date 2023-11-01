package com.gonder.pregnancyhealthcare.models;

public class LabResult {
    private String id;
    private long date;
    private String mId;
    private String body;
    private String lTid;

    public LabResult(){

    }

    public LabResult(String id, String body, long date){
        this.id = id;
        this.body = body;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getlTid() {
        return lTid;
    }

    public void setlTid(String lTid) {
        this.lTid = lTid;
    }
}

package com.gonder.pregnancyhealthcare.models;

import ir.mirrajabi.searchdialog.core.Searchable;

public class SearchPhysicianModel implements Searchable {
    private Physician physician;

    private String mTitle;

    @Override
    public String getTitle() {
        return "Dr. "+this.physician.getFullname();
    }

    public SearchPhysicianModel(Physician physician) {
        this.physician = physician;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    public Physician getPhysician() {
        return physician;
    }
}

package com.gonder.pregnancyhealthcare.models;

import ir.mirrajabi.searchdialog.core.Searchable;

public class SearchMother implements Searchable {
    private Mother mother;

    private String mTitle;

    @Override
    public String getTitle() {
        return "Ms. "+this.mother.getFullname();
    }

    public SearchMother(Mother mother) {
        this.mother = mother;
    }

    public void setMother(Physician physician) {
        this.mother = mother;
    }

    public Mother getMother() {
        return mother;
    }
}

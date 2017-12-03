package com.example.canberra.businesscardsaver;

import android.os.Debug;
import android.util.Log;

/**
 * Created by Mark on 1/04/2017.
 */

public class BusinessCard {
    private String title;
    private String name;
    private String company;
    private String website;
    private String phoneNumber;
    private String address;
    private String emailAddress;
    private String id = "0";

    public BusinessCard(String title, String name, String company, String website, String phoneNumber, String address, String emailAddress) {
        this.title = title;
        this.name = name;
        this.company = company;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.emailAddress = emailAddress;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getWebsite() {
        return website;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return name;
    }
}

package com.example.pulkit.boozingo.model;

public class smallPubDetails {

    String id, pub_name, pub_address, pub_cost, pub_time, pub_not_working_day, pub_contact,pub_pic;

    smallPubDetails() {

    }

    public smallPubDetails(String pub_address, String pub_contact, String pub_cost, String pub_name, String pub_not_working_day, String pub_time, String id, String pic) {
        this.pub_address = pub_address;
        this.pub_contact = pub_contact;
        this.pub_cost = pub_cost;
        this.pub_name = pub_name;
        this.pub_not_working_day = pub_not_working_day;
        this.pub_time = pub_time;
        this.id = id;
        this.pub_pic = pic;
    }

    public String getPub_address() {
        return pub_address;
    }

    public void setPub_address(String pub_address) {
        this.pub_address = pub_address;
    }

    public String getPub_contact() {
        return pub_contact;
    }

    public void setPub_contact(String pub_contact) {
        this.pub_contact = pub_contact;
    }

    public String getPub_cost() {
        return pub_cost;
    }

    public void setPub_cost(String pub_cost) {
        this.pub_cost = pub_cost;
    }

    public String getPub_name() {
        return pub_name;
    }

    public void setPub_name(String pub_name) {
        this.pub_name = pub_name;
    }

    public String getPub_not_working_day() {
        return pub_not_working_day;
    }

    public void setPub_not_working_day(String pub_not_working_day) {
        this.pub_not_working_day = pub_not_working_day;
    }

    public String getPub_time() {
        return pub_time;
    }

    public void setPub_time(String pub_time) {
        this.pub_time = pub_time;
    }

    public String getId() {
        return id;
    }

    public String getPub_pic() {
        return pub_pic;
    }

    public void setPub_pic(String pub_pic) {
        this.pub_pic = pub_pic;
    }
    

}

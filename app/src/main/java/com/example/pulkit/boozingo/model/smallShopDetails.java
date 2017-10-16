package com.example.pulkit.boozingo.model;

public class smallShopDetails {

    String id, beer_shop_name, beer_shop_address, beer_shop_cost, beer_shop_time, beer_shop_not_working_day, beer_shop_contact,beer_shop_pic;

    smallShopDetails() {

    }

    public smallShopDetails(String beer_shop_address, String beer_shop_contact, String beer_shop_cost, String beer_shop_name, String beer_shop_not_working_day, String beer_shop_time, String id, String pic) {
        this.beer_shop_address = beer_shop_address;
        this.beer_shop_contact = beer_shop_contact;
        this.beer_shop_cost = beer_shop_cost;
        this.beer_shop_name = beer_shop_name;
        this.beer_shop_not_working_day = beer_shop_not_working_day;
        this.beer_shop_time = beer_shop_time;
        this.id = id;
        this.beer_shop_pic = pic;
    }

    public String getBeer_shop_address() {
        return beer_shop_address;
    }

    public String getBeer_shop_contact() {
        return beer_shop_contact;
    }

    public String getBeer_shop_cost() {
        return beer_shop_cost;
    }

    public String getBeer_shop_name() {
        return beer_shop_name;
    }

    public String getBeer_shop_not_working_day() {
        return beer_shop_not_working_day;
    }

    public String getBeer_shop_pic() {
        return beer_shop_pic;
    }

    public String getBeer_shop_time() {
        return beer_shop_time;
    }

    public String getId() {
        return id;
    }

    public void setBeer_shop_pic(String beer_shop_pic) {
        this.beer_shop_pic = beer_shop_pic;
    }
}

package com.example.omar.pushnpull;

import java.util.List;

public class Items {

    private String id;
    private String name;
    private int price;
    private List<String> size;
    private String code;
    private String imageurl;
    private String selleremail;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public List<String> getSize() {
        return size;
    }

    public String getCode() {
        return code;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getSelleremail() {
        return selleremail;
    }

    public Items(String id, String name, int price, List<String> size, String code, String imageurl, String selleremail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.code = code;
        this.imageurl = imageurl;
        this.selleremail = selleremail;
    }

    public Items() {

    }



}

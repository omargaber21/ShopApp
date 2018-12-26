package com.example.omar.pushnpull;

import java.util.ArrayList;
import java.util.List;

public class Items {
    public Items() {
    }
    private String id;
    private String name;
    private int price;

    private String imageurl;
    private String selleremail;

    public String getSelleremail() {
        return selleremail;
    }

    public void setSelleremail(String selleremail) {
        this.selleremail = selleremail;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public void setSize(List size) {
        this.size = size;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImageurl() {

        return imageurl;
    }
    private List<String> size;
    private String code;
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

    public String getId() {
        return id;
    }

    public Items(String id, String name, int price, List size, String code,String imageurl,String selleremail) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.code = code;
        this.imageurl=imageurl;
        this.selleremail=selleremail;
    }
    public Items(String id, String name, int price, List size, String code) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.code = code;
    }



}

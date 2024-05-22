package com.example.myapplication.domain;

import java.util.ArrayList;

public class Room {
    private String id;
    private ArrayList<Long> userList;
    private ArrayList<Long> productList;

    public Room(String id, ArrayList<Long> userList, ArrayList<Long> productList) {
        this.id = id;
        this.userList = userList;
        this.productList = productList;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Long> getUserList() {
        return userList;
    }

    public ArrayList<Long> getProductList() {
        return productList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserList(ArrayList<Long> userList) {
        this.userList = userList;
    }

    public void setProductList(ArrayList<Long> productList) {
        this.productList = productList;
    }
}

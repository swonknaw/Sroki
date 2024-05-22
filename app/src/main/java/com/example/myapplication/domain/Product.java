package com.example.myapplication.domain;

public class Product {
    private long id;
    private String name;
    private int freshnessId;
    private String data;

    public Product(long id, String name, int freshnessId, String data) {
        this.id = id;
        this.name = name;
        this.freshnessId = freshnessId;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFreshnessId() {
        return freshnessId;
    }

    public String getData() {
        return data;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFreshnessId(int freshnessId) {
        this.freshnessId = freshnessId;
    }

    public void setData(String data) {
        this.data = data;
    }
}

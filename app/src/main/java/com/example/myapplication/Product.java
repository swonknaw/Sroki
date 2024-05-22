package com.example.myapplication;

import java.util.Random;

public class Product {
    private String name;
    private int freshnessId;

    public String getId() {
        return  id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;



    private String data;

    public Product(String name,  String data){
        id = getRandomString();
        this.name= name;
        this.data=data;
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFreshnessId() {
        return freshnessId;
    }

    public void setFreshnessId(int freshnessId) {
        this.freshnessId = freshnessId;
    }
    public String getRandomString(){
        int length = 10; // длина строки
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a'); // генерация буквы в нижнем регистре
            // для букв в верхнем регистре: (random.nextInt(26) + 'A')
            sb.append(c);
        }
        return sb.toString();
    }


}

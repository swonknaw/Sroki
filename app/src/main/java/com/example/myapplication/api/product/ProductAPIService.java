package com.example.myapplication.api.product;

import com.example.myapplication.api.RetrofitService;
import com.example.myapplication.api.room.RoomAPI;

public class ProductAPIService {

    private static ProductAPI productAPI;

    private static ProductAPI create() {
        return RetrofitService.getInstance().create(ProductAPI.class);
    }

    public static ProductAPI getInstance() {
        if (productAPI == null) productAPI = create();
        return productAPI;
    }
}

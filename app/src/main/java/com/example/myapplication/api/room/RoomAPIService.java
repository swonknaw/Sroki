package com.example.myapplication.api.room;

import com.example.myapplication.api.RetrofitService;

public class RoomAPIService {
    private static RoomAPI roomAPI;

    private static RoomAPI create() {
        return RetrofitService.getInstance().create(RoomAPI.class);
    }

    public static RoomAPI getInstance() {
        if (roomAPI == null) roomAPI = create();
        return roomAPI;
    }

}

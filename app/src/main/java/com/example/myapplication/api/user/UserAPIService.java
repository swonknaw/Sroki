package com.example.myapplication.api.user;

import com.example.myapplication.api.RetrofitService;
import com.example.myapplication.api.room.RoomAPI;

public class UserAPIService {
    private static UserAPI userAPI;

    private static UserAPI create() {
        return RetrofitService.getInstance().create(UserAPI.class);
    }

    public static UserAPI getInstance() {
        if (userAPI == null) userAPI = create();
        return userAPI;
    }
}

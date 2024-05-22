package com.example.myapplication.repository;

import com.example.myapplication.api.room.RoomAPIService;
import com.example.myapplication.api.user.UserAPIService;
import com.example.myapplication.domain.Room;
import com.example.myapplication.domain.User;

import retrofit2.Call;

public class UserRepository {
    public static Call<User> addUser(User user) {
        return UserAPIService.getInstance().addUser(user);
    }

    public static Call<User> getUser(long id) {
        return UserAPIService.getInstance().getProducts(id);
    }
}

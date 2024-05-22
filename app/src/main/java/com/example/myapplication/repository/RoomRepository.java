package com.example.myapplication.repository;

import com.example.myapplication.api.room.RoomAPIService;
import com.example.myapplication.domain.Room;

import retrofit2.Call;

public class RoomRepository {

    public static Call<Room> getRoom(String id) {
        return RoomAPIService.getInstance().getRoom(id);
    }

    public static Call<Room> addRoom(Room room) {
        return RoomAPIService.getInstance().addRoom(room);
    }

    public static Call<Room> addUserToRoom(String id, long userId) {
        return RoomAPIService.getInstance().addUserToRoom(id, userId);
    }

    public static Call<Room> deleteUserFromRoom(String id, long userId) {
        return RoomAPIService.getInstance().deleteUserFromRoom(id, userId);
    }

    public static Call<Room> getProducts(String id) {
        return RoomAPIService.getInstance().getProducts(id);
    }

}

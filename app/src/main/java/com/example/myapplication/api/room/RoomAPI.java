package com.example.myapplication.api.room;

import retrofit2.Call;

import com.example.myapplication.domain.Room;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RoomAPI {

    @GET("/room/users/{id}")
    Call<Room> getRoom(@Path("id") String id);

    @POST("/room/save")
    Call<Room> addRoom(@Body Room room);

    @POST("/room/save_user")
    Call<Room> addUserToRoom(@Body String id, long userId);

    @DELETE("/room/delete_user")
    Call<Room> deleteUserFromRoom(@Body String id, long userId);

    @GET("/room/products/{id}")
    Call<Room> getProducts(@Path("id") String id);
}

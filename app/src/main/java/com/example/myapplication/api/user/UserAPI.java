package com.example.myapplication.api.user;

import com.example.myapplication.domain.Room;
import com.example.myapplication.domain.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAPI {

    @POST("/user/save")
    Call<User> addUser(@Body User user);

    @GET("/user/products/{id}")
    Call<User> getProducts(@Path("id") long id);
}

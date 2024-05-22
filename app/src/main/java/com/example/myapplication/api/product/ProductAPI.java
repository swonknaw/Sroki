package com.example.myapplication.api.product;

import com.example.myapplication.domain.Product;
import com.example.myapplication.domain.Room;
import com.example.myapplication.domain.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductAPI {
    @POST("/product/save")
    Call<Product> addProduct(@Body Product product);

    @DELETE("/product/delete/{id}")
    Call<Product> deleteProduct(@Body long id);

    @POST("/product/update")
    Call<Product> updateProduct(@Body long id, Product product);

    @GET("/product/get/{id}")
    Call<Product> getProduct(@Path("id") long id);
}

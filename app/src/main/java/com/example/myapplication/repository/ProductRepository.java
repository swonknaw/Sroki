package com.example.myapplication.repository;

import com.example.myapplication.api.product.ProductAPI;
import com.example.myapplication.api.product.ProductAPIService;
import com.example.myapplication.api.room.RoomAPIService;
import com.example.myapplication.domain.Product;
import com.example.myapplication.domain.Room;

import retrofit2.Call;

public class ProductRepository {
    public static Call<Product> getProduct(long id) {
        return ProductAPIService.getInstance().getProduct(id);
    }

    public static Call<Product> addProduct(Product product) {
        return ProductAPIService.getInstance().addProduct(product);
    }

    public static Call<Product> deleteProduct(long id) {
        return ProductAPIService.getInstance().deleteProduct(id);
    }

    public static Call<Product> updateProduct(long id, Product product) {
        return ProductAPIService.getInstance().updateProduct(id, product);
    }
}

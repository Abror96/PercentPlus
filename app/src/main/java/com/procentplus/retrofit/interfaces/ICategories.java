package com.procentplus.retrofit.interfaces;

import com.procentplus.retrofit.models.CategoriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ICategories {

    @Headers("Content-Type: application/json")
    @GET("activity_types")
    Call<CategoriesResponse> getCategories(@Header("Authorization") String authorization);

}

package com.example.kringle.percentplus.retrofit.interfaces;

import com.example.kringle.percentplus.retrofit.models.CategoriesResponse;
import com.example.kringle.percentplus.retrofit.models.Objects;
import com.example.kringle.percentplus.retrofit.models.ObjectsRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IObjects {

    @Headers("Content-Type: application/json")
    @POST("partners/partners_list")
    Call<Objects> getObjects(@Header("Authorization") String authorization,
                             @Body ObjectsRequest activity_type_id);

}

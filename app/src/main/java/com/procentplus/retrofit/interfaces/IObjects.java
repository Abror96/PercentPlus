package com.procentplus.retrofit.interfaces;

import com.procentplus.retrofit.models.Objects;
import com.procentplus.retrofit.models.ObjectsRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IObjects {

    @Headers("Content-Type: application/json")
    @POST("partners/partners_list")
    Call<Objects> getObjects(@Header("Authorization") String authorization,
                             @Body ObjectsRequest activity_type_id);

}

package com.procentplus.retrofit.interfaces;

import com.procentplus.retrofit.models.SignRequest;
import com.procentplus.retrofit.models.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IAuthorization {

    @Headers("Content-Type: application/json")
    @POST("mobile_users/sign_in")
    Call<AuthResponse> getAccountData(
            @Body SignRequest mobile_user);

}

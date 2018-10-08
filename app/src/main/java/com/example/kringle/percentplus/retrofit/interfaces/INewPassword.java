package com.example.kringle.percentplus.retrofit.interfaces;

import com.example.kringle.percentplus.retrofit.models.NewPassword;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface INewPassword {

    @Headers("Content-Type: application/json")
    @POST("mobile_users/password")
    Call<NewPassword> newPasswordData(
            @Body NewPassword mobile_user);

}

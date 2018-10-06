package com.example.kringle.percentplus.retrofit.interfaces;

import com.example.kringle.percentplus.retrofit.models.RegistrationResponse;
import com.example.kringle.percentplus.retrofit.models.SignRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IRegistration {

        @Headers("Content-Type: application/json")
        @POST("mobile_users")
        Call<RegistrationResponse> registrationData(
                @Body SignRequest mobile_user);

}

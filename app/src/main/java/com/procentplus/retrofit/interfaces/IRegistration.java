package com.procentplus.retrofit.interfaces;

import com.procentplus.retrofit.models.RegistrationResponse;
import com.procentplus.retrofit.models.SignRequest;

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

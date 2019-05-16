package com.procentplus.retrofit.interfaces;

import com.procentplus.retrofit.models.AuthPartnerResponse;
import com.procentplus.retrofit.models.SignPartnerRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IPartnerAuthorization {
    @Headers("Content-Type: application/json")
    @POST("users/sign_in")
    Call<AuthPartnerResponse> getAccountData(
            @Body SignPartnerRequest partner_user);
}

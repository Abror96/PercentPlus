package com.procentplus.retrofit.interfaces;

import com.procentplus.retrofit.models.CategoriesResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;

public interface ILogout {

    @DELETE("mobile_users/sign_out")
    Call<CategoriesResponse> logOut(@Header("Authorization") String authorization);

}

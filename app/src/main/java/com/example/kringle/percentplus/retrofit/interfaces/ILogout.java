package com.example.kringle.percentplus.retrofit.interfaces;

import com.example.kringle.percentplus.retrofit.models.CategoriesResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;

public interface ILogout {

    @DELETE("mobile_users/sign_out")
    Call<CategoriesResponse> logOut(@Header("Authorization") String authorization);

}

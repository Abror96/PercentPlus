package com.example.kringle.percentplus.retrofit.interfaces;

import com.example.kringle.percentplus.retrofit.models.Bonus;
import com.example.kringle.percentplus.retrofit.models.BonusRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IBonus {

    @Headers("Content-Type: application/json")
    @POST("bonuses/current_bonus")
    Call<Bonus> getBonus(@Header("Authorization") String authorization,
                         @Body BonusRequest partner_id);

}

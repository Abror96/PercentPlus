package com.procentplus.retrofit.interfaces;

import com.procentplus.retrofit.models.BonusQR;
import com.procentplus.retrofit.models.BonusQRRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IBonusQR {

    @Headers("Content-Type: application/json")
    @POST("bonuses/checkin")
    Call<BonusQR> getBonusQR(@Header("Authorization") String authorization,
                             @Body BonusQRRequest partner_and_point_id);

}

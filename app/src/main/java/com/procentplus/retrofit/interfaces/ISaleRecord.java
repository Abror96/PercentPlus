package com.procentplus.retrofit.interfaces;

import com.procentplus.retrofit.models.SaleRecordRequest;
import com.procentplus.retrofit.models.SaleRecordResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ISaleRecord {
    @Headers("Content-Type: application/json")
    @POST("sale_records")
    Call<SaleRecordResponse> getAccountData(@Header("Authorization") String authorization,
            @Body SaleRecordRequest sale_record);
}

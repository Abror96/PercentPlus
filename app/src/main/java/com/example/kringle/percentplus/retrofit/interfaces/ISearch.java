package com.example.kringle.percentplus.retrofit.interfaces;

import com.example.kringle.percentplus.retrofit.models.Bonus;
import com.example.kringle.percentplus.retrofit.models.BonusRequest;
import com.example.kringle.percentplus.retrofit.models.SearchRequest;
import com.example.kringle.percentplus.retrofit.models.SearchResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ISearch {

    @Headers("Content-Type: application/json")
    @POST("point_of_sales/search")
    Call<SearchResponse> getSearchResult(@Header("Authorization") String authorization,
                                         @Body SearchRequest search_params);

}

package com.procentplus.retrofit.interfaces;

import com.procentplus.retrofit.models.SearchRequest;
import com.procentplus.retrofit.models.SearchResponse;

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

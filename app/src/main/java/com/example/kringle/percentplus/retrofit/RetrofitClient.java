package com.example.kringle.percentplus.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit ourInstance;

    public static Retrofit getInstance() {
        if (ourInstance == null) {

            ourInstance = new Retrofit.Builder()
                    .baseUrl("http://rocky-reef-79826.herokuapp.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return ourInstance;

    }

    private RetrofitClient() {

    }

}

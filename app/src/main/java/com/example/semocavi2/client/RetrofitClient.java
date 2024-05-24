package com.example.semocavi2.client;

import com.example.semocavi2.service.SemocApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

            private static final String BASE_URL = "https://raw.githubusercontent.com/ucsal/semoc/main/api/";

            public static Retrofit getClient() {
                return new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }

    }

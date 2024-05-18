package com.example.semocavi2.client;

import com.example.semocavi2.service.SemocApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit getClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("Retrofit retrofit = new Retrofit.Builder()\n" +
                        "    .baseUrl(\"https://raw.githubusercontent.com/ucsal/semoc/main/api/\")\n" +
                        "    .build();\n" +
                        "\n" +
                        "SemocApiService service = retrofit.create(SemocApiService.class);")
                .build();
        //isso aqui acho q pode dar ruim, mas veremos
        if (retrofit == null) {
         getClient();
        }

        return retrofit;
    }
}

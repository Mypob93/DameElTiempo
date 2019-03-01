package com.app.mauro.dameeltiempo;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mauro on 9/3/2018.
 */

// Modified by Mauro on 01/03/2019

public class RetrofitClase {

    private static RetrofitInterface MY_APY_SERVICE;

    public static RetrofitInterface createRetrofit() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        String baseUrl = "http://api.apixu.com/v1/";

        if (MY_APY_SERVICE == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            MY_APY_SERVICE = retrofit.create(RetrofitInterface.class);

        }

        return MY_APY_SERVICE;

    }
}

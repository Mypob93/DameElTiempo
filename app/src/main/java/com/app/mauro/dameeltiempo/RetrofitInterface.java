package com.app.mauro.dameeltiempo;

import com.app.mauro.dameeltiempo.ModelsPronosticos.Ciudad;
import com.app.mauro.dameeltiempo.ModelsPronosticos.Extendido.*;
import com.app.mauro.dameeltiempo.ModelsPronosticos.Normal.*;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mauro on 9/3/2018.
 */

public interface RetrofitInterface {

    @GET("forecast.json?key=4a083bcc0ad74c29be961017171111")
    retrofit2.Call<PronosticoExtendidoModel> getPronosticoExtendido(@Query("q") String city, @Query("days") String days);

    @GET("current.json?key=4a083bcc0ad74c29be961017171111")
    retrofit2.Call<com.app.mauro.dameeltiempo.ModelsPronosticos.Normal.PronosticoClase> getPronostico(@Query("q") String city);

    @GET("current.json?key=4a083bcc0ad74c29be961017171111")
    retrofit2.Call<PronosticoClase> getPronosticoLocalizacion(@Query("q") String latLong);

    @GET("search.json?key=4a083bcc0ad74c29be961017171111")
    retrofit2.Call<ArrayList<Ciudad>> getCiudades(@Query("q") String ciudad);
}

package com.chefling.app.api;


import com.chefling.app.response.WeatherResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("weather")
    Call<WeatherResponse> getCurrentWeather(@Query("appid") String apiId, @Query("q") String city);


    @GET("forecast")
    Call<JSONObject> getForecast(@Query("appid") String apiId, @Query("q") String city);


}
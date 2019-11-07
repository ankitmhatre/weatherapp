package com.chefling.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubForescastModel {
    @SerializedName("weather")
    private List<Weather> weather;

    @SerializedName("main")
    private WeatherMain main;

    @SerializedName("dt")
    private long dt;

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public WeatherMain getMain() {
        return main;
    }

    public void setMain(WeatherMain main) {
        this.main = main;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }
}

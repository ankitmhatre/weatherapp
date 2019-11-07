package com.chefling.app.activities


import android.app.ProgressDialog.show
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.api.load
import com.chefling.app.ForecastRecyclerAdapter
import com.chefling.app.R
import com.chefling.app.api.API
import com.chefling.app.api.ApiInterface
import com.chefling.app.models.Forecast
import com.chefling.app.repository.GlobalRepository
import com.chefling.app.response.ForecastResponse
import com.chefling.app.response.WeatherResponse
import com.chefling.app.ui.main.MainViewModel
import com.chefling.app.utilities.PrefUtils
import com.chefling.app.utilities.Utilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class WeatherActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener {
    final val LOG = "WeatherActivity"
    private var cityName: TextView? = null
    lateinit var todaysWeatherIcon: ImageView
    lateinit var changeCityArrow: ImageView
    lateinit var img_name: String
    lateinit var weatherSwipeRefresh: SwipeRefreshLayout
    lateinit var globalViewModel: MainViewModel
    lateinit var forecastRecyclerView: RecyclerView

    lateinit var minTempTv: TextView
    lateinit var tempTv: TextView
    lateinit var maxTempTv: TextView
    lateinit var weatherCondition: TextView
    lateinit var forecastRecyclerAdapter: ForecastRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        globalViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        forecastRecyclerAdapter = ForecastRecyclerAdapter()
        setContentView(com.chefling.app.R.layout.weather_layout)
        setViewIds()
        setUpListeners()

        globalViewModel.forecast.observeForever { forecasts ->
            forecastRecyclerAdapter.update(forecasts)
            try {
                forecastRecyclerView.scrollToPosition(0)
            } catch (e: Exception) {
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (Utilities.isNetworkAvailable(this)) {
            val executor = Executors.newSingleThreadScheduledExecutor()

            val periodicTask = Runnable {
                // Invoke method(s) to do the work
                syncingUpEverything()
            }

            executor.scheduleAtFixedRate(periodicTask, 0, 5, TimeUnit.MINUTES)


        } else {
    AlertDialog.Builder(this).setMessage("No Internet Connecton")
                .setTitle("Warning")
                .show()
        }

    }

    private fun setUpListeners() {
        weatherSwipeRefresh.setOnRefreshListener(this)
        changeCityArrow.setOnClickListener(this)
    }

    private fun setViewIds() {
        cityName = findViewById<View>(com.chefling.app.R.id.cityName) as TextView
        todaysWeatherIcon = findViewById<View>(com.chefling.app.R.id.todaysWeatherIcon) as ImageView
        weatherSwipeRefresh = findViewById(com.chefling.app.R.id.weatherSwipeRefresh)
        minTempTv = findViewById(com.chefling.app.R.id.minTempTv)
        maxTempTv = findViewById(com.chefling.app.R.id.maxTempTv)
        tempTv = findViewById(com.chefling.app.R.id.tempTv)
        weatherCondition = findViewById(com.chefling.app.R.id.weatherCondition)
        changeCityArrow = findViewById(com.chefling.app.R.id.changeCityArrow)
        forecastRecyclerView = findViewById(com.chefling.app.R.id.forecastRecyclerView)

        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        forecastRecyclerView.layoutManager = mLayoutManager
        forecastRecyclerView.itemAnimator = DefaultItemAnimator()
        forecastRecyclerView.adapter = forecastRecyclerAdapter
    }

    override fun onClick(p0: View?) {
        startActivity(Intent(this@WeatherActivity, CityInputActivty::class.java))
    }

    private fun syncingUpEverything() {
        weatherSwipeRefresh.isRefreshing = true
        val apiInterface = API.getClient().create(ApiInterface::class.java)
        val currentWeatherResponseCall = apiInterface.getCurrentWeather(
            getString(com.chefling.app.R.string.openWeatherApi),
            PrefUtils.getString(this, "city", "")
        )
        currentWeatherResponseCall.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.body() != null) {

                    val weatherResponse = response.body()

                    cityName!!.text = weatherResponse!!.name
                    tempTv.text =
                        Utilities.KelvinToCelsius(weatherResponse.main.temp).toDegree()
                    minTempTv.text =
                        Utilities.KelvinToCelsius(weatherResponse.main.temp_min)
                            .toDegree()
                    maxTempTv.text =
                        Utilities.KelvinToCelsius(weatherResponse.main.temp_max)
                            .toDegree()


                    todaysWeatherIcon.load("http://openweathermap.org/img/wn/${weatherResponse.weather[0].icon}@2x.png")

                    weatherCondition.text = weatherResponse.weather[0].main
                }
                weatherSwipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d(LOG, t.toString())
                weatherSwipeRefresh.isRefreshing = false
            }
        })


        val forecastResponseCall = apiInterface.getForecast(
            getString(R.string.openWeatherApi),
            PrefUtils.getString(this, "city", "")
        )
        forecastResponseCall.enqueue(object : Callback<ForecastResponse> {
            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Log.d(LOG, t.toString())
            }

            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                if (response.body() != null) {

                    GlobalRepository(application).deleteAll()
                    response.body()!!.list.forEach {
                        GlobalRepository(application).insertForecast(
                            Forecast(
                                null, it.dt, it.weather[0].icon, it.main.temp_min, it.main.temp_max

                            )
                        )

                    }

                }

            }
        })


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onRefresh() {
        syncingUpEverything()
    }

    fun String.toDegree(): String {
        return "$this°"
    }
}

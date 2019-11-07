package com.chefling.app.activities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.api.load
import com.chefling.app.api.API
import com.chefling.app.api.ApiInterface
import com.chefling.app.response.WeatherResponse
import com.chefling.app.utilities.PrefUtils
import com.chefling.app.utilities.Utilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WeatherActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener {
    final val LOG = "WeatherActivity"
    private var cityName: TextView? = null
    lateinit var todaysWeatherIcon: ImageView
    lateinit var changeCityArrow: ImageView
    lateinit var img_name: String
    lateinit var weatherSwipeRefresh: SwipeRefreshLayout

    lateinit var forecastRecyclerView: RecyclerView

    lateinit var minTempTv: TextView
    lateinit var tempTv: TextView
    lateinit var maxTempTv: TextView
    lateinit var weatherCondition: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(com.chefling.app.R.layout.weather_layout)
        setViewIds()
        setUpListeners()

    }

    override fun onResume() {
        super.onResume()
        syncingUpEverything()
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
    }

    override fun onClick(p0: View?) {
        startActivity(Intent(this@WeatherActivity, CityInputActivty::class.java))
    }

    private fun syncingUpEverything() {
        weatherSwipeRefresh.isRefreshing = true
        val apiInterface = API.getClient().create(ApiInterface::class.java)
        val contactsResponseCall = apiInterface.getCurrentWeather(
            getString(com.chefling.app.R.string.openWeatherApi),
            PrefUtils.getString(this, "city", "")
        )
        contactsResponseCall.enqueue(object : Callback<WeatherResponse> {
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
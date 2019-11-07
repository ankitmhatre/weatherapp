package com.chefling.app.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chefling.app.R
import com.chefling.app.utilities.PrefUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)
        GlobalScope.launch {
            // launch a new coroutine in background and continue
            delay(1500L) // non-blocking delay for 1 second (default time unit is ms)
         if(PrefUtils.getString(this@SplashActivity, "city", "").isEmpty()){
             startActivityForResult(Intent(this@SplashActivity,CityInputActivty::class.java), 99)
         }else{
             startActivityForResult(Intent(this@SplashActivity,WeatherActivity::class.java), 99)

         }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            99 ->{
                finish()
            }
        }
    }
}
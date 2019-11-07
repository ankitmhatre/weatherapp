package com.chefling.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.chefling.app.R
import com.chefling.app.utilities.PrefUtils

class CityInputActivty : AppCompatActivity() {


    lateinit var cityEt: EditText
    lateinit var arrowNext: ImageView
    lateinit var backArrowImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_city)
        cityEt = findViewById(R.id.cityEt)
        arrowNext = findViewById(R.id.arrowNext)
        backArrowImage = findViewById(R.id.backArrowImage)
        backArrowImage.setOnClickListener {
            finish()
        }

        cityEt.setText(PrefUtils.getString(this@CityInputActivty, "city", ""))




        if (PrefUtils.getString(this@CityInputActivty, "city", "").isEmpty()) {
            backArrowImage.visibility = View.GONE
        } else {
            backArrowImage.visibility = View.VISIBLE
        }

        arrowNext.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View) {
                if (cityEt.text.toString().isNotEmpty()) {

                    PrefUtils.setString(this@CityInputActivty, "city", cityEt.text.toString())
                    startActivityForResult(
                        Intent(
                            this@CityInputActivty,
                            WeatherActivity::class.java
                        ), 98
                    )

                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            98 -> {
                finish()
            }
        }
    }
}

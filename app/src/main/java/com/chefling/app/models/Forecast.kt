package com.chefling.app.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Forecast(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @SerializedName("dt")
    var dt: Long,
    @SerializedName("icon")
    var icon: String,
    @SerializedName("temp_min")
    var temp_min: Double,

    @SerializedName("temp_max")
    var temp_max: Double

)
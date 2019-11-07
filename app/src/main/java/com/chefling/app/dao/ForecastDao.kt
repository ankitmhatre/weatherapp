package com.chefling.app.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chefling.app.models.Forecast

@Dao
interface ForecastDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecast: Forecast)

    @Query("DELETE FROM Forecast")
    fun deleteAll()

    @Query("SELECT * FROM Forecast")
    fun getAllForecast(): LiveData<List<Forecast>>

}
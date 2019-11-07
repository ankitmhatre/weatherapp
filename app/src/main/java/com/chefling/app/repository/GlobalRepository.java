package com.chefling.app.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.chefling.app.dao.ForecastDao;
import com.chefling.app.database.GlobalDatabase;
import com.chefling.app.models.Forecast;
import com.chefling.app.models.SubForescastModel;

import java.util.List;

public class GlobalRepository {
private ForecastDao forecastDao;
    public GlobalRepository(Application application){
        GlobalDatabase globalDatabase = GlobalDatabase.getInstance(application);
        forecastDao = globalDatabase.forecastDao();
    }
    public void insertForecast(Forecast forecast) {
        new InsertForecast(forecastDao).execute(forecast);
    }

    public void deleteAll() {
        new DeleteAll(forecastDao).execute();
    }

    private static class InsertForecast extends AsyncTask<Forecast, Void, Void> {
        private ForecastDao forecastDao;

        private InsertForecast(ForecastDao forecastDao) {
            this.forecastDao = forecastDao;
        }

        @Override
        protected Void doInBackground(Forecast... forecasts) {
            forecastDao.insert(forecasts[0]);
            return null;
        }
    }
    private static class DeleteAll extends AsyncTask<Void, Void, Void> {
        ForecastDao forecastDao;

        private DeleteAll(ForecastDao forecastDao) {
            this.forecastDao = forecastDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            forecastDao.deleteAll();
            return null;
        }
    }

    public LiveData<List<Forecast>> getForecasts(){
        return forecastDao.getAllForecast();
    }

}

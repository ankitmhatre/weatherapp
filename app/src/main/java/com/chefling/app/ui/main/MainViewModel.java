package com.chefling.app.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chefling.app.models.Forecast;
import com.chefling.app.repository.GlobalRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private GlobalRepository globalRepository;

    public MainViewModel (@NonNull Application application) {
        super(application);
        globalRepository = new GlobalRepository(application);

    }
    public LiveData<List<Forecast>> getForecast() {
        return globalRepository.getForecasts();
    }
}

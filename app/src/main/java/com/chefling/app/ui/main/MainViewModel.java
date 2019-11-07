package com.chefling.app.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.chefling.app.repository.GlobalRepository;

public class MainViewModel extends AndroidViewModel {
    private GlobalRepository globalRepository;

    public MainViewModel (@NonNull Application application) {
        super(application);
        globalRepository = new GlobalRepository(application);

    }
}

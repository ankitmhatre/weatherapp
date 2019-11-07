package com.chefling.app.repository;

import android.app.Application;

import com.chefling.app.database.GlobalDatabase;

public class GlobalRepository {

    public GlobalRepository(Application application){
        GlobalDatabase globalDatabase = GlobalDatabase.getInstance(application);

    }

}

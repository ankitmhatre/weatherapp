package com.chefling.app.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.chefling.app.models.Forecast;

@Database(entities = {Forecast.class}, version = 1, exportSchema = false)
public abstract class GlobalDatabase extends RoomDatabase{

    public static GlobalDatabase instance;
    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //  new PopulateCallLogs(instance).execute();
            //    new PopulateUssdItems(instance).execute();

        }
    };
    public static synchronized GlobalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GlobalDatabase.class, "allbigdb")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

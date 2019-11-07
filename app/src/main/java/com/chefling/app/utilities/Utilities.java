package com.chefling.app.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utilities {
    public static String getIconUrl(String iconName) {
        return "http://openweathermap.org/img/w/" + iconName + ".png";
    }

    public static String KelvinToCelsius(double temp) {

        double inCelsius = temp - 273.15;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(inCelsius);
    }
    public static String getDay(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp * 1000);

        return c.getDisplayName((Calendar.DAY_OF_WEEK), Calendar.LONG, Locale.getDefault());
    }
    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

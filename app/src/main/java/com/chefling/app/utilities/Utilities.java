package com.chefling.app.utilities;

import java.text.DecimalFormat;

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
}

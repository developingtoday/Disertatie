package com.utils;

import com.activities.SensorActivity;
import com.activities.WeatherActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Revan
 * Date: 6/6/12
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */
///TBE sa se faca singleton generic in cazul asta;
public class Factory {

    private static SensorActivity _sensor;
    private static WeatherActivity _weather;
    public static  synchronized SensorActivity getInstanceSensorActivity()
    {
            if(_sensor==null) _sensor=new SensorActivity();
            return _sensor;
    }


    public static synchronized WeatherActivity getInstanceWeatherActivity()
    {
          if(_weather==null) _weather=new WeatherActivity();
        return _weather;
    }
}

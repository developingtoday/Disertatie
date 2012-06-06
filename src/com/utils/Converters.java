package com.utils;

import android.hardware.Sensor;
import android.location.Location;
import com.google.android.maps.GeoPoint;
import com.obj.SensorData;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/22/12
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Converters {
    
    public static String getOrientation(float azimuth)
    {
       if(azimuth>=0 && azimuth<90) return "N";
       if(azimuth>=90 && azimuth<180) return "E";
       if(azimuth>=180 && azimuth<270) return "S";
       if(azimuth>=270 && azimuth<360) return "W";
       if(azimuth==360 && azimuth==0) return "N";
        return Float.toString(azimuth);
    }


    public static GeoPoint fromLocation2GeoPoint(Location location)
    {
        if(location==null) return null;
        return new GeoPoint((int)(location.getLatitude()*1E6),(int)(location.getLongitude()*1E6));
    }
    public static GeoPoint fromLocation2GeoPoint(SensorData location)
    {
        if(location==null) return null;
        return new GeoPoint((int)(location.getLatitudine()*1E6),(int)(location.getLongitudine()*1E6));
    }
}

package com.listeners;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: Revan
 * Date: 4/8/12
 * Time: 8:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class  LocationListenerGps implements LocationListener {

    public static  final String TAG="LocationListenerGps";

    private LocationManager locationManager;
    public LocationListenerGps(Context context)
    {
       locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);
    }

    @Override
    public void onLocationChanged(Location location) {
        //To change body of implemented methods use File | Settings | File Templates.


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderEnabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderDisabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void closeListeners()
    {
        locationManager.removeUpdates(this);
    }
}

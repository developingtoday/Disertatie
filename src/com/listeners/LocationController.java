package com.listeners;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.abstracte.INotifier;
import com.utils.FileUtils;

import java.io.File;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Revan
 * Date: 4/8/12
 * Time: 8:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationController implements LocationListener {

    public static  final String TAG="LocationController";

    public static LocationController staticController;

    private INotifier<Location> notifier;
    private LocationManager locationManager;
    private Stack<Location> listaLocatii=new Stack<Location>();
    private LocationController(Context context)
    {
       locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);

    }

    public static synchronized LocationController getInstance(Context context)
    {
        if(staticController==null) staticController=new LocationController(context);
        return staticController;
    }

    public void setNotifier(INotifier<Location> notifier)
    {
        this.notifier=notifier;
    }

    @Override
    public void onLocationChanged(Location location) {
        //To change body of implemented methods use File | Settings | File Templates.
         listaLocatii.push(location);
         if(notifier!=null) notifier.notifyView(location);

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
        for(Location loc:listaLocatii)
        {
            FileUtils.WriteTag("Latitude",Double.toString(loc.getLatitude()));
            FileUtils.WriteTag("Longitude",Double.toString(loc.getLongitude()));
            FileUtils.WriteTag("Speed",Float.toString(loc.getSpeed()));
            FileUtils.WriteTag("Altitude",Double.toString(loc.getAltitude()));
            FileUtils.WriteTag("Precision",Float.toString(loc.getAccuracy()));
        }
        FileUtils.appendToLog();
        listaLocatii.clear();
    }

    public Location getLastLocation()
    {
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }


}

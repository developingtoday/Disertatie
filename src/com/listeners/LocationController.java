package com.listeners;

import Obj.GeoInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.*;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import com.abstracte.INotifier;
import com.abstracte.ISensorDataManager;
import com.activities.GMapActivity;
import com.activities.MainActivity;
import com.activities.R;
import com.google.android.maps.GeoPoint;
import com.managers.SensorDataManager;
import com.obj.SensorData;
import com.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Revan
 * Date: 4/8/12
 * Time: 8:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationController implements LocationListener,GpsStatus.Listener {

    public static  final String TAG="LocationController";



    public  boolean isListening,isGpsFix;
    public long lastTimeLocationFix;
    public static LocationController staticController;


    private Vector<INotifier<SensorData>> notifiers;

    private Sensor pressureSensor,acell,magnetic,orientation;
    private SensorManager sManager;

    private LocationManager locationManager;
    private ISensorDataManager manager;
    private PressureListener sensorEventListener;
    private CompassListener compassListener;
    private OrientationCompassListener oLic;
    private Geocoder geocoder;
    private Context context;

    private LocationController(Context context)

    {
        this.context=context;
        notifiers=new Vector<INotifier<SensorData>>();
        sManager=(SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        pressureSensor=sManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        acell=sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetic=sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        orientation=sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorEventListener=new PressureListener();
        compassListener=new CompassListener();
         oLic=new OrientationCompassListener();
        sManager.registerListener(sensorEventListener,pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(compassListener,acell,SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(compassListener,magnetic,SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(oLic,orientation,SensorManager.SENSOR_DELAY_NORMAL);
        isListening=true;
       if(Geocoder.isPresent()) geocoder=new Geocoder(context);

        locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,10,this);
        locationManager.addGpsStatusListener(this);


        manager=new SensorDataManager();
    }

    public static synchronized LocationController getInstance(Context context)
    {
        if(staticController==null) staticController=new LocationController(context);
        return staticController;
    }

    public void addNotifier(INotifier<SensorData> notifier)
    {
        notifiers.add(notifier);
    }
    public void removeNotifier(INotifier<SensorData> notifier)
    {
        notifiers.remove(notifier);
    }

    private void sendNotification(SensorData sensorData)
    {
      for(INotifier<SensorData> n:notifiers)
      {
          n.notifyView(sensorData);
      }
    }

    public void setListening(boolean listening) {
        isListening = listening;
    }


    float[] results=new float[1];
    float  distanta;
    @Override
    public void onLocationChanged(Location location) {
        if(!isListening) return;
        if(!manager.getLastSensorDataKnown().isEmpty()){
            Location.distanceBetween(manager.getLastSensorDataKnown().getLatitudine(),manager.getLastSensorDataKnown().getLongitudine(),location.getLatitude(), location.getLongitude(), results);
            distanta+=results[0];
        }
        lastTimeLocationFix= SystemClock.elapsedRealtime();
        manager.addData(new SensorData(location.getLongitude(),location.getLatitude(),sensorEventListener.getLastPressureValue(),location.getAltitude(),location.getSpeed(),oLic.getLastOrientation(),distanta,sensorEventListener.getLastAltitudeFromPressure(),location.getTime()));
        sendNotification(manager.getLastSensorDataKnown());

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
        isListening=false;
        distanta=0;
        results[0]=0;
        manager.flushToDataSource();

    }

    public Location getLastLocationWeather()
    {
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }


    public Location getLastLocation()
    {
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public GeoInfo getGeocodingFromLocation(Location data) throws IOException
    {
          if(geocoder==null ) throw new NullPointerException("No Geocoder inside device");
          List<Address> list= geocoder.getFromLocation(data.getLatitude(), data.getLongitude(),1);
          if(list==null || list.get(0)==null ) return new GeoInfo();
           Address a=list.get(0);
         GeoInfo g=new GeoInfo();
        g.setCity(a.getLocality());
        return g;
    }

    @Override
    public void onGpsStatusChanged(int i) {
        if(!isListening) return;
        switch (i){
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                isGpsFix=true;
                break;
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                if(!manager.getLastSensorDataKnown().isEmpty()) isGpsFix=(SystemClock.elapsedRealtime()-lastTimeLocationFix)<3000;
                if(isGpsFix)
                {
                    //notificare status bar
                    NotificationManager nman=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification.Builder nbuilder=new Notification.Builder(context);
                    nbuilder.setContentIntent(PendingIntent.getActivity(context,0,new Intent(context, GMapActivity.class),PendingIntent.FLAG_CANCEL_CURRENT));
                    nbuilder.setSmallIcon(R.drawable.chance_of_rain);
                    nbuilder.setContentText("Gps fix acquired");
                    nbuilder.setContentTitle("GpsWalk");
                    nman.notify(0,nbuilder.getNotification());
                }
            break;
        }

    }

    class PressureListener implements SensorEventListener{

        private float presureValue;

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
                 try{

                  presureValue=sensorEvent.values[0];
                 }catch(Exception ex)
                 {

                     Log.d("Pressure Listener", ex.getMessage(), ex);
                 }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

        public float getLastPressureValue()
        {
            return presureValue;
        }

        public float getLastAltitudeFromPressure()
        {
            if(presureValue==0) return 0;
            float alt=SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE,presureValue);
            return alt;
        }
    }

    class CompassListener implements SensorEventListener{

        float[] gravity,geomagnetic,orientation;
        float[]  I=new float[9];
        float[] R=new float[9];
        float lastOrientation;
        boolean succes;
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER) gravity=sensorEvent.values;
            if(sensorEvent.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD) geomagnetic=sensorEvent.values;
            if(gravity==null || geomagnetic==null) return;
            succes=SensorManager.getRotationMatrix(R,I,gravity,geomagnetic);
            if(!succes) return;
            orientation=new float[3];
            SensorManager.getOrientation(R,orientation);
            lastOrientation=orientation[0];
            gravity=geomagnetic=null;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {


        }
        public float getLastOrientation()
        {
            return lastOrientation;
        }
    }

    class OrientationCompassListener implements SensorEventListener{
        private float lastOrientation;
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
               lastOrientation=sensorEvent.values[0];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

        public float getLastOrientation() {
            return lastOrientation;
        }
    }




}

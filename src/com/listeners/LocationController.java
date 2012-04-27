package com.listeners;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.abstracte.INotifier;
import com.abstracte.ISensorDataManager;
import com.managers.SensorDataManager;
import com.obj.SensorData;
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



    public  boolean isListening;
    public static LocationController staticController;

    private Sensor pressureSensor,acell,magnetic,orientation;
    private SensorManager sManager;
    private INotifier<SensorData> notifier;
    private LocationManager locationManager;
    private ISensorDataManager manager;
    private PressureListener sensorEventListener;
    private CompassListener compassListener;
    private OrientationCompassListener oLic;



    private LocationController(Context context)

    {
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

        locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,10,this);


        manager=new SensorDataManager();
    }

    public static synchronized LocationController getInstance(Context context)
    {
        if(staticController==null) staticController=new LocationController(context);
        return staticController;
    }

    public void setNotifier(INotifier<SensorData> notifier)
    {
        this.notifier=notifier;
    }

    public void setListening(boolean listening) {
        isListening = listening;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(!isListening) return;
        manager.addData(new SensorData(location.getLongitude(),location.getLatitude(),sensorEventListener.getLastPressureValue(),location.getAltitude(),location.getSpeed(),oLic.getLastOrientation()));
        if(notifier!=null) notifier.notifyView(manager.getLastSensorDataKnown());
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
        manager.flushToDataSource();

    }

    public Location getLastLocation()
    {
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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

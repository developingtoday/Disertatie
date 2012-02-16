package com.activities;

import Obj.GeoInfo;
import Servicii.ElevationQueryWeb;
import Servicii.ReverseGeocodeQueryWeb;
import android.app.Activity;
import android.hardware.*;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/11/12
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class SensorActivity extends Activity {
    private TextView txtLatitude,txtLongitutde,txtOrientation,txtSpeed,txtDistance,txtAltitude,txtElevation;
    private LocationManager lManager;
    private SensorManager sManager;
    private LocationListener locListener;
    private Sensor _orientationSensor;
    private SensorEventListener sensorEventListener;
    private TextView txtGeocode;
    private ArrayList<Location> locationList;
    private Button btnStop,btnGeocode,btnElevate,btnExport;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationList=new ArrayList<Location>();
        setContentView(R.layout.sensoractivity);
        txtLatitude=(TextView)findViewById(R.id.txtLatitude);
        txtLongitutde=(TextView)findViewById(R.id.txtLongitude);
        txtOrientation=(TextView)findViewById(R.id.txtOrientation);
        txtSpeed=(TextView)findViewById(R.id.txtSpeed);
        txtDistance=(TextView)findViewById(R.id.txtDistance);
        txtAltitude=(TextView)findViewById(R.id.txtAltitude);
        txtGeocode=(TextView)findViewById(R.id.txtGeocode);
        txtElevation=(TextView)findViewById(R.id.txtElevation) ;
        btnElevate=(Button)findViewById(R.id.btnElevate);
        btnGeocode=(Button)findViewById(R.id.btnGeocode);
        btnStop=(Button)findViewById(R.id.btnStop);
        btnElevate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ElevateListener();
            }
        });
        btnGeocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               GeocodeListener();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopListener();
            }
        });
        setupListeners();
       }

    private void ElevateListener()
    {

    }
    private void GeocodeListener()
    {

    }
    private void StopListener()
    {
       lManager.removeUpdates(locListener);
    }
    private void setupListeners()
    {
        lManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        locListener=new GpsLocationListener();
        lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locListener);
        sManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        _orientationSensor=sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorEventListener=new CompassListener();
        sManager.registerListener(sensorEventListener, _orientationSensor, sManager.SENSOR_DELAY_NORMAL);

    }

    Double actualDistance;
    private void CalculateDistance()
    {
        actualDistance=Double.parseDouble(txtDistance.getText().toString());
        if(locationList.size()<=1) return;
        actualDistance=(double)locationList.get(locationList.size()-1).distanceTo(locationList.get(locationList.size()-2));
        txtDistance.setText(actualDistance.toString());
    }
   class CompassListener implements SensorEventListener{
         float[] valori;
       @Override
       public void onSensorChanged(SensorEvent sensorEvent) {
           //To change body of implemented methods use File | Settings | File Templates.
            valori=sensorEvent.values;
           txtOrientation.setText(Float.toString(valori[0]));
       }

       @Override
       public void onAccuracyChanged(Sensor sensor, int i) {
           //To change body of implemented methods use File | Settings | File Templates.
       }
   }

    
    class GpsLocationListener implements LocationListener{


                @Override
        public void onLocationChanged(Location location) {
            //To change body of implemented methods use File | Settings | File Templates.
            PopulateViewControls(location);
            locationList.add(location);
            CalculateDistance();
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
        
        private void PopulateViewControls(Location l)
        {
            txtLatitude.setText(Double.toString(l.getLatitude()));
            txtLongitutde.setText(Double.toString(l.getLongitude()));
            txtAltitude.setText(Double.toString(l.getAltitude()));
            txtSpeed.setText(Double.toString(l.getSpeed()));

        }
    }
}
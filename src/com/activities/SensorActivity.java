package com.activities;

import Obj.GeoInfo;
import Obj.GpsPoint;
import Servicii.ElevationQueryWeb;
import Servicii.ReverseGeocodeQueryWeb;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.*;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.utils.Converters;
import com.utils.ServicesFactory;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/11/12
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class SensorActivity extends Fragment {
    private TextView txtLatitude,txtLongitutde,txtOrientation,txtSpeed,txtDistance,txtAltitude,txtElevation,txtPressure;
    private LocationManager lManager;
    private SensorManager sManager;
    private LocationListener locListener;
    private Sensor _orientationSensor;
    private Sensor _geomagneticSensor;
    private Sensor _pressureSensor;
    private SensorEventListener sensorEventListener;
    private SensorEventListener pressureListener;
    private TextView txtGeocode;
    private ArrayList<Location> locationList;
    private Button btnStop,btnGeocode,btnElevate,btnExport;
    private ReverseGeocodeQueryWeb queryWeb;
    private ElevationQueryWeb _elevQ;

    private Handler handler;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationList=new ArrayList<Location>();
        View fragView = inflater.inflate(R.layout.sensoractivity, container, false);
        txtLatitude=(TextView)fragView.findViewById(R.id.txtLatitude);
        txtLongitutde=(TextView)fragView.findViewById(R.id.txtLongitude);
        txtOrientation=(TextView)fragView.findViewById(R.id.txtOrientation);
        txtSpeed=(TextView)fragView.findViewById(R.id.txtSpeed);
        txtDistance=(TextView)fragView.findViewById(R.id.txtDistance);
        txtAltitude=(TextView)fragView.findViewById(R.id.txtAltitude);
        txtGeocode=(TextView)fragView.findViewById(R.id.txtGeocode);
        txtElevation=(TextView)fragView.findViewById(R.id.txtElevation) ;
        txtPressure=(TextView)fragView.findViewById(R.id.txtPressure);
        btnElevate=(Button)fragView.findViewById(R.id.btnElevate);
        btnGeocode=(Button)fragView.findViewById(R.id.btnGeocode);
        btnStop=(Button)fragView.findViewById(R.id.btnStop);
        queryWeb= ServicesFactory.getReverseGeocodeService();
        _elevQ=ServicesFactory.getElevationService();
        btnElevate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        ElevateListener();
                    }
                };
                new Thread(runnable).start();
            }
        });
        btnGeocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        //To change body of implemented methods use File | Settings | File Templates.
                        GeocodeListener();
                    }
                };
               new Thread(runnable).start();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopListener();
            }
        });
        setupListeners(fragView);
        return fragView;
       }


    private void ElevateListener()
    {
        try
        {

            if(locationList.size()<1) return;
            GpsPoint p=new GpsPoint();
            Location l=locationList.get(locationList.size()-1);
            p.setLatitude(l.getLatitude());
            p.setLongitude(l.getLongitude());
           _elevQ.setPoint(p);
            _elevQ.Popupleaza();
            txtElevation.post(new Runnable() {
                @Override
                public void run() {
                    txtElevation.setText(Double.toString(_elevQ.getPoint().getAltitude()));
                }
            });
        } catch (Exception ex)
        {
            ex.printStackTrace();
            Log.d("Eroare",ex.getMessage(),ex.getCause());
        }
    }
    private void GeocodeListener()
    {
        try{

        GeoInfo g=new GeoInfo();
        if(locationList.size()<1) return;
        Location l=locationList.get(locationList.size()-1);
        g.setLatitude(l.getLatitude());
        g.setLongitude(l.getLongitude());
        queryWeb.setPoint(g);
        queryWeb.PrepareUrl();
        queryWeb.Populeaza();
        //txtGeocode.setText(queryWeb.getPoint().getCity());
         txtGeocode.post(new Runnable() {
             @Override
             public void run() {
                 txtGeocode.setText(queryWeb.getPoint().getAdress());
             }
         });
        }catch (Exception ex)
        {
            Log.e("Exceptie",ex.getMessage(),ex.getCause());
            ex.printStackTrace();
        }
    }
    private void StopListener()
    {
       lManager.removeUpdates(locListener);
        txtAltitude.setText("0");
        txtDistance.setText("0");
        txtGeocode.setText("0");
        txtLatitude.setText("0");
        txtLongitutde.setText("0");
        txtSpeed.setText("0");
        txtElevation.setText("0");
    }
    private void setupListeners(View view)
    {


        lManager=(LocationManager)view.getContext().getSystemService(Context.LOCATION_SERVICE) ;
        locListener=new GpsLocationListener();
        lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5,8,locListener);
        sManager=(SensorManager)view.getContext().getSystemService(Context.SENSOR_SERVICE);
        _orientationSensor=sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
       // _geomagneticSensor=sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        _pressureSensor=sManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        sensorEventListener=new CompassListener();
        pressureListener=new PressureListener();
        sManager.registerListener(pressureListener,_pressureSensor,sManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(sensorEventListener, _orientationSensor, sManager.SENSOR_DELAY_NORMAL);

       // sManager.registerListener(sensorEventListener,_geomagneticSensor,sManager.SENSOR_DELAY_NORMAL);
        
    }

    Double actualDistance;
    private void CalculateDistance()
    {
        actualDistance=Double.parseDouble(txtDistance.getText().toString());
        if(locationList.size()<=1) return;
        actualDistance+=(double)locationList.get(locationList.size()-1).distanceTo(locationList.get(locationList.size()-2));
        txtDistance.setText(actualDistance.toString());
    }
   class CompassListener implements SensorEventListener{

         float azimuth;
       public void onSensorChanged(SensorEvent sensorEvent) {
            azimuth=sensorEvent.values[0];
            txtOrientation.setText(Converters.getOrientation(azimuth)+" "+Float.toString(azimuth));
       }

       @Override
       public void onAccuracyChanged(Sensor sensor, int i) {
           //To change body of implemented methods use File | Settings | File Templates.
       }
   }

    class PressureListener implements SensorEventListener{
        float pressure;
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //To change body of implemented methods use File | Settings | File Templates.
            pressure=sensorEvent.values[0];
            txtPressure.setText(Float.toString(pressure)+" hPa");

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
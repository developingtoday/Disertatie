package com.activities;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/11/12
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class SensorActivity extends Activity {
    private TextView txtLatitude,txtLongitutde,txtOrientation,txtSpeed,txtDistance,txtAltitude;
    private LocationManager lManager;
    private LocationListener locListener;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensoractivity);
        txtLatitude=(TextView)findViewById(R.id.txtLatitude);
        txtLongitutde=(TextView)findViewById(R.id.txtLongitude);
        txtOrientation=(TextView)findViewById(R.id.txtOrientation);
        txtSpeed=(TextView)findViewById(R.id.txtSpeed);
        txtDistance=(TextView)findViewById(R.id.txtDistance);
        txtAltitude=(TextView)findViewById(R.id.txtAltitude);
        lManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        locListener=new GpsLocationListener();
        lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locListener);
    }


    class GpsLocationListener implements LocationListener{

        Double latitude, longitude,altitude;
        Float speed;
        @Override
        public void onLocationChanged(Location location) {
            //To change body of implemented methods use File | Settings | File Templates.
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            speed=location.getSpeed();
            altitude=location.getAltitude();
            txtLatitude.setText(latitude.toString());
            txtLongitutde.setText(longitude.toString());
            txtAltitude.setText(altitude.toString());
            txtSpeed.setText(speed.toString());

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
    }
}
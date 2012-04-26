package com.activities;

import Obj.WeatherInfo;
import Servicii.WeatherQueryWeb;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.listeners.LocationController;
import com.utils.Converters;
import com.utils.ServicesFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/11/12
 * Time: 9:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class WeatherActivity extends Fragment {
    private TextView txtCity,txtTemp,txtForecast;
    private Button btnActualizeaza;
    private Handler handler;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View fragView = inflater.inflate(R.layout.weatheractivity, container, false);

        txtCity=(TextView)fragView.findViewById(R.id.txtCity);
        txtForecast=(TextView)fragView.findViewById(R.id.txtForecast);
        txtTemp=(TextView)fragView.findViewById(R.id.txtTemp);
        handler=new Handler();
        btnActualizeaza=(Button)fragView.findViewById(R.id.btnGetWeather);
        btnActualizeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        ShowInfo();
                    }
                };
                new Thread(runnable).start();
            }
        });
        return fragView;
    }

    private void ShowInfo() {
        //TODO verificat cazul cand nu exista conexiune la internet
        try{
        final WeatherQueryWeb q= ServicesFactory.getWeatherService();
            WeatherInfo         w=new WeatherInfo();
        String url = "http://www.google.com/ig/api?weather=Constanta";
            LocationManager lm= (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            GeoPoint l= Converters.fromLocation2GeoPoint(lm.getLastKnownLocation(lm.GPS_PROVIDER));

            if(l!=null)
            {
                url="http://www.google.com/ig/api?weather=,,,";
                String lng=String.valueOf(l.getLongitudeE6());
                String lat=String.valueOf(l.getLatitudeE6());
                url+=lat;
                url+=",";
                url+=lng;
            }
        q.setUrl(url);
        q.PopuleazaWeather();
        w=q.getWeather();
        handler.post(new Runnable() {
            @Override
            public void run() {
                txtCity.setText(q.getWeather().getLocation());
                txtTemp.setText(q.getWeather().getTemp());
            }
        });
        }catch(Exception ex)
        {
           ex.printStackTrace();
           Log.d("Exceptie", ex.getMessage(), ex.getCause());
        }
    }
}
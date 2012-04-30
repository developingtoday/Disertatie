package com.activities;

import Obj.GpsPoint;
import Obj.WeatherInfo;
import Servicii.GoogleWeatherQueryWeb;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.listeners.LocationController;
import Utils.ServicesFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/11/12
 * Time: 9:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class WeatherActivity extends Fragment {
    private TextView txtCity,txtTemp,txtHumidity,txtWind;
    private Button btnActualizeaza;
    private Handler handler;
    private LocationController lc;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View fragView = inflater.inflate(R.layout.weatheractivity, container, false);

        txtCity=(TextView)fragView.findViewById(R.id.txtCity);
        txtHumidity=(TextView)fragView.findViewById(R.id.txtHumidity);
        txtWind=(TextView)fragView.findViewById(R.id.txtWind);
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
        lc=LocationController.getInstance(getActivity().getApplicationContext());
            }
        });
        return fragView;
    }

    private void ShowInfo() {
        //TODO verificat cazul cand nu exista conexiune la internet
        try{
        final GoogleWeatherQueryWeb q= ServicesFactory.getWeatherService();

          final  WeatherInfo         w;



        GpsPoint g=new GpsPoint();
            g.setLatitude(lc.getLastLocation().getLatitude());
            g.setLongitude(lc.getLastLocation().getLongitude());
        w=q.getInfoFromPoint(g);
        handler.post(new Runnable() {
            @Override
            public void run() {
                txtCity.setText(w.getCity());
                txtTemp.setText(w.getTemp());
                txtWind.setText(w.getWind());
                txtHumidity.setText(w.getUmidity());
            }
        });
        }catch(Exception ex)
        {
           ex.printStackTrace();
           Log.d("Exceptie", ex.getMessage(), ex.getCause());
        }
    }
}
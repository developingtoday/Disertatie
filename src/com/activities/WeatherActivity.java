package com.activities;

import Obj.WeatherInfo;
import Servicii.WeatherQueryWeb;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.utils.ServicesFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/11/12
 * Time: 9:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class WeatherActivity extends Activity {
    private TextView txtCity,txtTemp,txtForecast;
    private Button btnActualizeaza;
    private Handler handler;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weatheractivity);
        txtCity=(TextView)findViewById(R.id.txtCity);
        txtForecast=(TextView)findViewById(R.id.txtForecast);
        txtTemp=(TextView)findViewById(R.id.txtTemp);
        handler=new Handler();
        btnActualizeaza=(Button)findViewById(R.id.btnGetWeather);
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
    }

    private void ShowInfo() {
        //TODO execute async
        //TODO verificat cazul cand nu exista conexiune la internet
        try{
        final WeatherQueryWeb q= ServicesFactory.getWeatherService();
            WeatherInfo         w=new WeatherInfo();
        String url = "http://www.google.com/ig/api?weather=Constanta";

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
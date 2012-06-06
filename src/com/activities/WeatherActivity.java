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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
    private TextView txtCity,txtTemp,txtHumidity,txtWind,txtCondtion;
    private ImageView imgViewWeatherIcon;
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
        txtCondtion=(TextView)fragView.findViewById(R.id.txtCondition);
        imgViewWeatherIcon=(ImageView)fragView.findViewById(R.id.imgViewWeather);
        handler=new Handler();
        lc=LocationController.getInstance(getActivity().getApplicationContext());
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("City",txtCity.getText().toString());
        outState.putString("Temp",txtTemp.getText().toString());
        outState.putString("Wind",txtWind.getText().toString());
        outState.putString("Umidity",txtHumidity.getText().toString());
        outState.putString("Condition",txtCondtion.getText().toString());
    }



    private void ShowInfo() {
        //TODO verificat cazul cand nu exista conexiune la internet
        try{
        final GoogleWeatherQueryWeb q= ServicesFactory.getWeatherService();

          final  WeatherInfo w;



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
                txtCondtion.setText(w.getConditionData());
                imgViewWeatherIcon.setImageResource(getActivity().getResources().getIdentifier(w.getIconData(),"drawable",getActivity().getPackageName()));

            }
        });
        }catch(final Exception ex)
        {
           handler.post(new Runnable() {
               @Override
               public void run() {
                   Toast.makeText(getActivity().getApplicationContext(),ex.toString(),Toast.LENGTH_SHORT).show();
               }
           }) ;

           ex.printStackTrace();
           Log.e("Exceptie", ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }



}
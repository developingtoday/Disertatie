package com.activities;

import Obj.GeoInfo;
import Obj.GpsPoint;
import Servicii.ElevationQueryWeb;
import Servicii.ReverseGeocodeQueryWeb;
import android.app.Fragment;
import android.content.Context;
import android.hardware.*;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import com.abstracte.INotifier;
import com.listeners.LocationController;
import com.obj.SensorData;
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
public class SensorActivity extends Fragment implements INotifier<SensorData> {
    private TextView txtLatitude,txtLongitutde,txtOrientation,txtSpeed,txtDistance,txtAltitude,txtElevation,txtPressure;
    private LocationController list;
    private TextView txtGeocode;
    private ArrayList<Location> locationList;
    private Button btnStop,btnGeocode,btnElevate,btnExport,btnStart;
    private ReverseGeocodeQueryWeb queryWeb;
    private ElevationQueryWeb _elevQ;

    private Handler handler;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list=LocationController.getInstance(getActivity().getApplicationContext());
        list.setNotifier(this);
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
        btnStart=(Button)fragView.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.setListening(true);
                    }
                }
        );
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
        return fragView;
       }


    private void ElevateListener()
    {
        try
        {


            GpsPoint p=new GpsPoint();
            Location l=list.getLastLocation();
            if(l==null) return;
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
        Location l=list.getLastLocation();
        if(l==null) return;
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
        list.closeListeners();
        txtAltitude.setText("0");
        txtDistance.setText("0");
        txtGeocode.setText("0");
        txtLatitude.setText("0");
        txtLongitutde.setText("0");
        txtSpeed.setText("0");
        txtElevation.setText("0");
    }

    Double actualDistance;
    private void CalculateDistance()    //TODO mutat in SensorDataManager
    {
        actualDistance=Double.parseDouble(txtDistance.getText().toString());
        if(locationList.size()<=1) return;
        actualDistance+=(double)locationList.get(locationList.size()-1).distanceTo(locationList.get(locationList.size()-2));
        txtDistance.setText(actualDistance.toString());
    }

    @Override
    public void notifyView(SensorData l) {
        txtLatitude.setText(Double.toString(l.getLatitudine()));
        txtLongitutde.setText(Double.toString(l.getLongitudine()));
        txtAltitude.setText(Double.toString(l.getAltitudine()));
        txtSpeed.setText(Double.toString(l.getViteza()));
        txtPressure.setText(Double.toString(l.getPresiune()));
        txtOrientation.setText(Float.toString(l.getOrientare()));
    }





    







}
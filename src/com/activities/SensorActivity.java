package com.activities;

import Obj.GeoInfo;
import Obj.GpsPoint;
import Utils.ServicesFactory;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.abstracte.INotifier;
import com.listeners.LocationController;
import com.obj.SensorData;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/11/12
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class SensorActivity extends Fragment implements INotifier<SensorData> {
    private TextView txtLatitude, txtLongitude,txtSpeed,txtDistance,txtAltitude,txtElevation,txtAlitudeBarometer;
    private TextView txtGeocode;

    private Button btnStop,btnGeocode,btnElevate,btnExport,btnStart;
    private     LocationController list;
    private ControllerActions controllerActions;
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list=LocationController.getInstance(getActivity().getApplicationContext());
        list.addNotifier(this);
        controllerActions=new ControllerActions();
        handler=new Handler();
        View fragView = inflater.inflate(R.layout.sensoractivity, container, false);
        txtLatitude=(TextView)fragView.findViewById(R.id.txtLatitude);
        txtLongitude =(TextView)fragView.findViewById(R.id.txtLongitude);
        txtSpeed=(TextView)fragView.findViewById(R.id.txtSpeed);
        txtDistance=(TextView)fragView.findViewById(R.id.txtDistance);
        txtAltitude=(TextView)fragView.findViewById(R.id.txtAltitude);
        txtGeocode=(TextView)fragView.findViewById(R.id.txtGeocode);
        txtElevation=(TextView)fragView.findViewById(R.id.txtElevation) ;
        txtAlitudeBarometer=(TextView)fragView.findViewById(R.id.txtAltitudeBarometric);
        return fragView;
       }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void stopListener()
    {
        list.closeListeners();
        txtAltitude.setText("0");
        txtDistance.setText("0");
        txtGeocode.setText("0");
        txtLatitude.setText("0");
        txtLongitude.setText("0");
        txtSpeed.setText("0");
        txtElevation.setText("0");
        txtAlitudeBarometer.setText("0");
    }
    @Override
    public void notifyView(SensorData l) {
        txtLatitude.setText(Double.toString(l.getLatitudine()));
        txtLongitude.setText(Double.toString(l.getLongitudine()));
        txtAltitude.setText(Double.toString(l.getAltitudine()));
        txtSpeed.setText(Double.toString(l.getSpeedInKmh()));
        txtAlitudeBarometer.setText(Double.toString(l.getAltitudineBarometrica()));
        txtDistance.setText(Float.toString(l.getDistanceInKm()));
    }

    GpsPoint getGpsPoint()
    {
        GpsPoint point=new GpsPoint();
        point.setLatitude(list.getLastLocation().getLatitude());
        point.setLongitude(list.getLastLocation().getLongitude());
        return point;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menusensor,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mnuGeocode:
                geocode();
                return true;
            case R.id.mnuElevation:
                elevate();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




    private void elevate()
    {


            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    try{

                        final GpsPoint p= ServicesFactory.getElevationService().getInfoFromPoint(getGpsPoint());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                txtElevation.setText(Double.toString(p.getAltitude()));
                            }
                        });
                    }catch (Exception ex)
                    {
                        Log.e("Exceptie",ex.getMessage(),ex);
                        ex.printStackTrace();
                    }
                }
            };
            new Thread(runnable).start();
    }

    private void geocode()
    {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try{

                    final GeoInfo p= ServicesFactory.getReverseGeocodeService().getInfoFromPoint(getGpsPoint());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtGeocode.setText(p.getCity());
                        }
                    });
                }catch (final Exception ex)
                {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),ex.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.e("Exceptie",ex.getMessage(),ex);
                    ex.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    class ControllerActions implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if(view.equals(btnElevate))
            {
                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        try{

                        final GpsPoint p= ServicesFactory.getElevationService().getInfoFromPoint(getGpsPoint());
                         handler.post(new Runnable() {
                             @Override
                             public void run() {
                               txtElevation.setText(Double.toString(p.getAltitude()));
                             }
                         });
                        }catch (Exception ex)
                        {
                            Log.e("Exceptie",ex.getMessage(),ex);
                            ex.printStackTrace();
                        }
                    }
                };
                new Thread(runnable).start();
            }
            if(view.equals(btnGeocode))
            {
                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        try{

                           final GeoInfo p= ServicesFactory.getReverseGeocodeService().getInfoFromPoint(getGpsPoint());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    txtGeocode.setText(p.getCity());
                                }
                            });
                        }catch (final Exception ex)
                        {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity().getApplicationContext(),ex.toString(),Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.e("Exceptie",ex.getMessage(),ex);
                            ex.printStackTrace();
                        }
                    }
                };
                new Thread(runnable).start();
            }
            if(view.equals(btnStart))
            {
                Toast.makeText(getActivity().getApplicationContext(),"Listening started",Toast.LENGTH_SHORT).show();
                list.setListening(true);
            }
            if(view.equals(btnStop))
            {  Toast.makeText(getActivity().getApplicationContext(),"Listening stopped",Toast.LENGTH_SHORT).show();
               stopListener();
            }

        }
    }




    







}
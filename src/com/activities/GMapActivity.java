package com.activities;

import android.app.*;
import android.graphics.*;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.abstracte.INotifier;
import com.google.android.maps.*;
import com.listeners.LocationController;
import com.obj.SensorData;
import com.utils.Converters;
import com.utils.DrawLocationOverlay;
import com.utils.Factory;

import java.util.List;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/11/12
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class GMapActivity extends MapActivity implements INotifier<SensorData> {
    private MapController _mapController;
    private MapView _mapView;
    private LocationController gpsLoc;
    private GeoPoint point;
    private ViewGroup _viewFrag;
     List<Overlay> mapOverlays;
     MyLocationOverlay myLoc;
     ActionBar actionBar;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);
        setupMapController();
        _viewFrag=(ViewGroup)findViewById(R.id.fragPlaceHolder);
        actionBar=getActionBar();
        ShowFragment(Factory.getInstanceSensorActivity(),"Sensor");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
         inflater.inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager managerFrag=getFragmentManager();
        FragmentTransaction transFrag;

        switch (item.getItemId())
        {
            case R.id.mnuDate:

                 ShowFragment(Factory.getInstanceSensorActivity(),"Sensor");

                return true;
            case R.id.mnuWeather:
                ShowFragment(Factory.getInstanceWeatherActivity(),"Weather");

                return true;
            case R.id.mnuStart:
                Toast.makeText(getApplicationContext(),"Listening started",Toast.LENGTH_SHORT).show();
                gpsLoc.setListening(true);
                return true;
            case R.id.mnuStop:
                Toast.makeText(getApplicationContext(),"Listening stopped",Toast.LENGTH_SHORT).show();
                gpsLoc.closeListeners();
                mapOverlays.clear();
                mapOverlays.add(myLoc);
                return false;
            case R.id.mnuCalibrate:
               ShowCalibration();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

     private void ShowCalibration()
    {
           FragmentTransaction ft=getFragmentManager().beginTransaction();
           Fragment prev=getFragmentManager().findFragmentByTag("dialog");
        if(prev!=null) ft.remove(prev);
        ft.addToBackStack(null);
        DialogFragment dialogFragment=new CalibrateDialogFragment();
        dialogFragment.show(ft,"dialog");


    }

    String lastFrag;
    void ShowFragment(Fragment curr,String tag)
    {
        FragmentManager managerFrag=getFragmentManager();
        FragmentTransaction transFrag=managerFrag.beginTransaction();
        Fragment last=managerFrag.findFragmentByTag(lastFrag);

        if(last!=null ) transFrag.hide(last);
        if(curr!=null && curr.isAdded()) transFrag.show(curr);
        if(!curr.isAdded()) transFrag.add(R.id.fragPlaceHolder,curr,tag);
        lastFrag=tag;
        transFrag.commit();
    }


    private void setupMapController()
    {
        _mapView=(MapView)findViewById(R.id.map_view);
        _mapController=_mapView.getController();
        mapOverlays=_mapView.getOverlays();
        gpsLoc=LocationController.getInstance(getApplicationContext());
        setupMyLocation();
        _mapView.invalidate();
        gpsLoc.addNotifier(this);
    }

     GeoPoint gpAux;
    public void notifyView(SensorData location) {
        try{
            gpAux= Converters.fromLocation2GeoPoint(location);
            _mapController.animateTo(gpAux);
          _listaLocatii.add(gpAux);
            if(_listaLocatii.size()<=1) return;
            mapOverlays.add(new DrawLocationOverlay(_listaLocatii.elementAt(_listaLocatii.size()-2),_listaLocatii.elementAt(_listaLocatii.size()-1)));
            _mapView.postInvalidate();
        }catch (Exception ex)
        {
            Log.d("Exception",ex.getMessage(),ex.getCause());
        }
    }
    @Override
    public void onResume()
    {
       super.onResume();
      myLoc.enableMyLocation();
    }

    public void onPause()
    {
        super.onPause();
        myLoc.disableMyLocation();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
    protected boolean isLocationDisplayed()
    {
        return myLoc.isMyLocationEnabled();
    }

    private Stack<GeoPoint> _listaLocatii=new Stack<GeoPoint>();



    private void setupMyLocation()
    {
        myLoc=new MyLocationOverlay(this,_mapView);
        myLoc.enableCompass();

        mapOverlays.add(myLoc);


    }








}


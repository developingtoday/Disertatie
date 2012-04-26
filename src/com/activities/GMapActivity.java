package com.activities;

import android.graphics.*;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import com.abstracte.INotifier;
import com.google.android.maps.*;
import com.listeners.LocationController;
import com.utils.Converters;

import java.util.List;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/11/12
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class GMapActivity extends MapActivity implements INotifier<Location> {
    private MapController _mapController;
    private MapView _mapView;
    private LocationController gpsLoc;
    private GeoPoint point;
     List<Overlay> mapOverlays;
     MyLocationOverlay myLoc;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);
        _mapView=(MapView)findViewById(R.id.map_view);
        _mapController=_mapView.getController();
        _mapView.setBuiltInZoomControls(true);
        mapOverlays=_mapView.getOverlays();
        gpsLoc=LocationController.getInstance(getApplicationContext());
        setupMyLocation();
        _mapView.invalidate();
        notifyView(gpsLoc.getLastLocation());

    }
     GeoPoint gpAux;
    public void notifyView(Location location) {
        
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
      myLoc.runOnFirstFix(new Runnable() {
          @Override
          public void run() {
            DrawMapList();
          }
      });

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
    private GeoPoint _previous;



    private void setupMyLocation()
    {
        myLoc=new MyLocationOverlay(this,_mapView);
        myLoc.enableCompass();
        myLoc.enableMyLocation();
        mapOverlays.add(myLoc);
        myLoc.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                DrawMapList();
            }
        });

    }
    private void DrawMapList()
    {
        try
        {
            if(myLoc.getMyLocation()==null) return;
            _mapController.animateTo(myLoc.getMyLocation());
            _mapController.setZoom(10);
            _listaLocatii.add(myLoc.getMyLocation());
            _mapView.postInvalidate();
        }catch(Exception ex)
        {
            Log.d("Exception",ex.getMessage(),ex.getCause());
            ex.printStackTrace();
        }
    }




    class DrawLocationOverlay extends Overlay{
        private GeoPoint gp1;
        private GeoPoint gp2;
        
        public DrawLocationOverlay(GeoPoint start, GeoPoint end)
        {
            gp1=start;
            gp2=end;
        }
        public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
                            long when) {
            // TODO Auto-generated method stub
            Projection projection = mapView.getProjection();
            if (shadow == false) {

                Paint paint = new Paint();
                paint.setAntiAlias(true);
                Point point = new Point();
                projection.toPixels(gp1, point);
                paint.setColor(Color.BLUE);
                Point point2 = new Point();
                projection.toPixels(gp2, point2);
                paint.setStrokeWidth(2);
                canvas.drawLine((float) point.x, (float) point.y, (float) point2.x,(float) point2.y, paint);
            }
            return super.draw(canvas, mapView, shadow, when);
        }
        @Override
        public void draw(Canvas canvas, MapView mapView, boolean shadow) {
            // TODO Auto-generated method stub

            super.draw(canvas, mapView, shadow);
        }


    }



}


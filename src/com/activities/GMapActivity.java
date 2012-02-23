package com.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.maps.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/11/12
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class GMapActivity extends MapActivity {
    private MapController _mapController;
    private MapView _mapView;
    private LocationManager loc;
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
        setupMyLocation();
        _mapView.invalidate();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


    private void setupMyLocation()
    {
        myLoc=new MyLocationOverlay(this,_mapView);
        myLoc.enableCompass();
        myLoc.enableMyLocation();
        mapOverlays.add(myLoc);
        myLoc.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                _mapController.animateTo(myLoc.getMyLocation());
                _mapController.setZoom(10);
            }
        }) ;
    }
}
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);
        _mapView=(MapView)findViewById(R.id.map_view);
        _mapController=_mapView.getController();
        _mapView.setBuiltInZoomControls(true);
        final List<Overlay> mapOverlays=_mapView.getOverlays();
        Drawable drawable=getResources().getDrawable(R.drawable.pinclip);
       final CustomItemizedOverlay item=new CustomItemizedOverlay(drawable,this);

        loc=(LocationManager)getSystemService(LOCATION_SERVICE);
        LocationListener list=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
               point=new GeoPoint((int)location.getLatitude()*1000000,(int)location.getLongitude()*1000000);
                // item.mapOverlays.clear();
                item.addOverlay(new OverlayItem(point, "Hello","Ba"));

                mapOverlays.add(item);
                _mapController.animateTo(point);
                _mapController.setZoom(5);
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
        } ;
        loc.requestLocationUpdates(LocationManager.GPS_PROVIDER,5,8,list);
        _mapView.invalidate();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


    class CustomItemizedOverlay extends ItemizedOverlay<OverlayItem> {

        private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();

        private Context context;

        public CustomItemizedOverlay(Drawable defaultMarker) {
            super(boundCenterBottom(defaultMarker));
        }

        public CustomItemizedOverlay(Drawable defaultMarker, Context context) {
            this(defaultMarker);
            this.context = context;
        }

        @Override
        protected OverlayItem createItem(int i) {
            return mapOverlays.get(i);
        }

        @Override
        public int size() {
            return mapOverlays.size();
        }

        @Override
        protected boolean onTap(int index) {
            OverlayItem item = mapOverlays.get(index);
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(item.getTitle());
            dialog.setMessage(item.getSnippet());
            dialog.show();
            return true;
        }

        public void addOverlay(OverlayItem overlay) {
            mapOverlays.add(overlay);
            this.populate();
        }

    }
}
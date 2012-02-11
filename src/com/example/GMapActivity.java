package com.example;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.maps.MapActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/11/12
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class GMapActivity extends MapActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
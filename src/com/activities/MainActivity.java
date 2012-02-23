package com.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity
{
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TabHost host=getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        Context c=this.getApplicationContext();
        intent=new Intent().setClass(c,GMapActivity.class);
        spec=host.newTabSpec("Map").setIndicator("Map").setContent(intent);
        host.addTab(spec);

//         intent=new Intent().setClass(this,RoutesActivity.class);
//         spec=host.newTabSpec("Routes").setIndicator("Routes").setContent(intent);
//        host.addTab(spec);
//        intent=new Intent().setClass(this, PlotActivity.class);
//        spec=host.newTabSpec("Plot").setIndicator("Plot").setContent(intent);
//        host.addTab(spec);
        intent=new Intent().setClass(this, SensorActivity.class);
        spec=host.newTabSpec("Sensors").setIndicator("Sensors").setContent(intent);
        host.addTab(spec);
        intent=new Intent().setClass(this, WeatherActivity.class);
        spec=host.newTabSpec("Weather").setIndicator("Weather").setContent(intent);
        host.addTab(spec);
        intent=new Intent().setClass(this, Settings.class);
        spec=host.newTabSpec("Settings").setIndicator("Settings").setContent(intent);
        host.addTab(spec);
    }


}

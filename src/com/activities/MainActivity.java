package com.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity
{


    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActionBar actionBar=getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tabMap,tabWeather,tabSensors;
        //tabMap=actionBar.newTab().setText("Map");
        tabSensors=actionBar.newTab().setText("Sensors");
        tabWeather=actionBar.newTab().setText("Weather");
        Fragment fragSensor=new SensorActivity();
        Fragment fragWeather=new WeatherActivity();
        tabSensors.setTabListener(new TabListAct(fragSensor));
        tabWeather.setTabListener(new TabListAct(fragWeather));
        actionBar.addTab(tabSensors);
        actionBar.addTab(tabWeather);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    MenuItem map = menu.add(0, 1, 0, "Map");
    map.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    map.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            Intent i = new Intent(getApplicationContext(), GMapActivity.class);
            startActivity(i);
            return true;
        }
    });

    return true;
}

      class TabListAct implements ActionBar.TabListener
        {
            private Fragment fragment;
            public TabListAct(Fragment fragment)
            {
                this.fragment = fragment;
            }
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                //To change body of implemented methods use File | Settings | File Templates.
                //fragmentTransaction.add(R.id.fragment_place, fragment, null);
                    if(fragment.isAdded()) fragmentTransaction.show(fragment);
                    else fragmentTransaction.add(R.id.fragment_place,fragment, null);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                //To change body of implemented methods use File | Settings | File Templates.
                fragmentTransaction.hide(fragment);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                //To change body of implemented methods use File | Settings | File Templates.
                fragmentTransaction.show(fragment);

            }
        }

}

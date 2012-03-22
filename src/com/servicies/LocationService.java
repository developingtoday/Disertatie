package com.servicies;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 3/6/12
 * Time: 8:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationService extends Service {

    private static final String TAG="LocationService";

    @Override
    public void onCreate()
    {
       Log.d(TAG,"onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return null;
    }
}

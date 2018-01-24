package com.demo.tomcat.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ServiceDemo extends Service
{
    final static String TAG = ServiceDemo.class.getSimpleName();

    private DownloadBinder mBinder = new DownloadBinder();
    class DownloadBinder extends Binder
    {
        final String TAGDB = DownloadBinder.class.getSimpleName();
        public void startBownload()
        {
            Log.d(TAGDB, "startBownload() executed !!");
        }

        public int getProgress()
        {
            Log.d(TAGDB, "getProgress() executed !!");
            return 0;
        }
    }

    public ServiceDemo() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG, "onBind() executed !!");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed !!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed !!");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() executed !!");
        super.onDestroy();
    }
}

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
            Log.w(TAGDB, "startBownload(), ");
        }

        public int getProgress()
        {
            Log.w(TAGDB, "getProgress(), ");
            return 0;
        }
    }

    public ServiceDemo() {
        Log.w(TAG, "ServiceDemo(), constructor ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.w(TAG, "onBind(), ");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w(TAG, "onCreate(), ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(TAG, "onStartCommand(), ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.w(TAG, "onDestroy(), ");
        super.onDestroy();
    }
}

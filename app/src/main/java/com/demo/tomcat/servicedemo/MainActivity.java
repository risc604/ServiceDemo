package com.demo.tomcat.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = MainActivity.class.getSimpleName();

    Button startService, stopService, bindService, unbindService;

    //Handler handler;
    private ServiceDemo.DownloadBinder  downloadBinder;
    private ServiceConnection   connection = new ServiceConnection()
    {
        private final String CONNECTAG = ServiceConnection.class.getSimpleName();

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.w(CONNECTAG, " onServiceConnected()");
            downloadBinder = (ServiceDemo.DownloadBinder) service;
            downloadBinder.startBownload();
            downloadBinder.getProgress();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.w(CONNECTAG, " onServiceDisconnected()");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w(TAG, "onCreate(), ");

        initView();
        initControl();
    }


    @Override
    public void onClick(View v)
    {
        Log.w(TAG, "onClick(), Id: " + getResources().getResourceName(v.getId()));
        switch (v.getId())
        {
            case R.id.start_service:
                Intent startIntent = new Intent(this, ServiceDemo.class);
                startService(startIntent);
                break;

            case R.id.stop_service:
                Intent stopIntent = new Intent(this, ServiceDemo.class);
                stopService(stopIntent);
                break;

            case R.id.bind_service:
                Intent bindIntent = new Intent(this, ServiceDemo.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);

                break;

            case R.id.unbind_service:
                try {
                    if (connection != null)
                        unbindService(connection);
                    else
                        Log.e(TAG, "1 connect is NULL !!");
                }
                catch (Exception e) {
                 e.printStackTrace();
                    Log.e(TAG, "connect is NULL !!");
                }
                break;

            default:
                break;
        }
    }


    //--------------------- user function --------------------------//
    private void initView()
    {
        Log.w(TAG, " initView(), ");
        startService = (Button) findViewById(R.id.start_service);
        stopService = (Button) findViewById(R.id.stop_service);
        bindService = (Button) findViewById(R.id.bind_service);
        unbindService = (Button) findViewById(R.id.unbind_service);
    }

    private void initControl()
    {
        Log.w(TAG, " initControl(), ");
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
    }

}


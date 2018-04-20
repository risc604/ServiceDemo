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
    final static String TAG = MainActivity.class.getSimpleName();

    //Handler handler;
    private ServiceDemo.DownloadBinder  downloadBinder;
    private ServiceConnection   connection = new ServiceConnection() {
        final String CONNECTAG = ServiceConnection.class.getSimpleName();

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            downloadBinder = (ServiceDemo.DownloadBinder) service;
            downloadBinder.startBownload();
            downloadBinder.getProgress();
            Log.d(CONNECTAG, "onServiceConnected()");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(CONNECTAG, "onServiceDisconnected()");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startService = (Button) findViewById(R.id.start_service);
        Button stopService = (Button) findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);

        Button bindService = (Button) findViewById(R.id.bind_service);
        Button unbindService = (Button) findViewById(R.id.unbind_service);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
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
                    unbindService(connection);
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
}


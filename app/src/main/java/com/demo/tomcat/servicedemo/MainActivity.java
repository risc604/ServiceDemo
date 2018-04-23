package com.demo.tomcat.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


//https://blog.csdn.net/Chen_xiaobao/article/details/71597801
//https://blog.csdn.net/guolin_blog/article/details/9797169
//https://blog.csdn.net/guolin_blog/article/details/11952435



public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = MainActivity.class.getSimpleName();

    Button startService, stopService;
    Button  bindService, unbindService, sendMsgBtn;

    private boolean mBound;
    private Handler     mClientHandler = new ClientHandler();
    private Messenger   mServiceMessenger;
    private Messenger   mClientMessenger = new Messenger(mClientHandler);
    private ServiceDemo.DownloadBinder  downloadBinder = new ServiceDemo.DownloadBinder();
    private ServiceDemo     mServiceDemo;
    private class ClientHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            if (msg.what == ServiceDemo.MSG_FROM_SERVER_TO_CLIENT)
            {
                Log.w(TAG, "reveive msg from server");
            }
        }
    }

    private NewServiceConnection connection = new NewServiceConnection();
    //private ServiceConnection   connection = new ServiceConnection()
    private class NewServiceConnection implements ServiceConnection
    {
        private final String CONNECTAG = ServiceConnection.class.getSimpleName();

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.w(CONNECTAG, " onServiceConnected()");
            downloadBinder = (ServiceDemo.DownloadBinder) service;
            //downloadBinder.startBownload();
            //downloadBinder.getProgress();
            mServiceDemo = downloadBinder.getService();
            mServiceMessenger = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.w(CONNECTAG, " onServiceDisconnected()");
            mBound = false;
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
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onCreate(), ");
        excuteUnbindService();
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
                excuteUnbindService();
                break;

            case R.id.sendmsgbtn:
                sayHello();
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
        sendMsgBtn = findViewById(R.id.sendmsgbtn);
    }

    private void initControl()
    {
        Log.w(TAG, " initControl(), ");
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);

        new Handler().postDelayed(new Runnable()
        {
           @Override
           public void run()
           {
                Intent intent = new Intent(MainActivity.this,
                AlarmReceiver.class);
                sendBroadcast(intent);
            }
        }, 3 * 1000);
    }

    private void excuteUnbindService()
    {
        Log.w(TAG, " excuteUnbindService(), ");
        try {
            //if (connection != null)
            if (mBound) {
                unbindService(connection);
                mBound = false;
            }
            else
                Log.e(TAG, "1 connect is NULL !!");
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "connect is NULL !!");
        }
    }

    private void sayHello()
    {
        if (!mBound)
            return;

        Message msg = Message.obtain(null, ServiceDemo.MSG_FROM_CLIENT_TO_SERVER, 0);
        msg.replyTo = mClientMessenger;
        try
        {
            mServiceMessenger.send(msg);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

}


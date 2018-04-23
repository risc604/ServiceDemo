package com.demo.tomcat.servicedemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ServiceDemo extends Service
{
    final static String TAG = ServiceDemo.class.getSimpleName();


    public static final int MSG_FROM_CLIENT_TO_SERVER = 1;
    public static final int MSG_FROM_SERVER_TO_CLIENT = 2;
    public static final int NOTIFICATION_ID = 3;
    private Messenger mClientMessenger;
    private Messenger mServerMessenger = new Messenger(new ServerHandler());

    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    private DownloadBinder mBinder = new DownloadBinder();
    class DownloadBinder extends Binder
    {
        final String TAGDB = DownloadBinder.class.getSimpleName();

        ServiceDemo getService()
        {
            return ServiceDemo.this;
        }
        //public void startBownload()
        //{
        //    Log.w(TAGDB, "startBownload(), ");
        //}
        //
        //public int getProgress()
        //{
        //    Log.w(TAGDB, "getProgress(), ");
        //    return 0;
        //}
    }

    class ServerHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {

            Log.w(TAG, " handleMessage(), what: " + msg.what);
            switch (msg.what)
            {
                case MSG_FROM_CLIENT_TO_SERVER:
                    mClientMessenger = msg.replyTo;
                    Message toClientMsg = Message.obtain(null, MSG_FROM_SERVER_TO_CLIENT);
                    try {
                        Log.w(TAG, "server begin send msg to client");
                        mClientMessenger.send(toClientMsg);
                    }
                    catch (RemoteException e)
                    {
                        e.printStackTrace();
                    }
                    break;

                case MSG_FROM_SERVER_TO_CLIENT:
                    break;

                default:
                    super.handleMessage(msg);
                    break;
            }
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
        //return mBinder;
        return mServerMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //sendNotification("Set Notifiaction ...");
        Log.w(TAG, "onCreate(), ");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
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


    //------------------ user function ----------------------//
    // Post a notification indicating whether a doodle was found.
    private void sendNotification(String msg) {
        Log.w(TAG, " sendNotification(), msg: " + msg);
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Has notification !!")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg)
                        .setVibrate(new long[]{0, 20000, 500, 20000});

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}

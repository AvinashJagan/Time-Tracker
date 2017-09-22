package com.example.computer.signinapp;

/**
 * Created by Computer on 9/18/2017.
 */


import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//import com.estimote.*;

public class BeaconApp extends Application {
    FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;
    private BeaconManager beaconManager;
    DatabaseReference databaseReference,childref;
    @Override
    public void onCreate() {
        super.onCreate();

      EstimoteSDK.initialize(getApplicationContext(), "com.example.computer.signinapp", "<>Here goes your application token");

        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {

            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> beacons) {

                showNotification(
                        "Your Are in Time tracker region","Here is Your msg");

            }

            @Override
            public void onExitedRegion(BeaconRegion beaconRegion) {
                cancelNotification();
            }
        });


        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {

                beaconManager.startMonitoring(new BeaconRegion("monitored region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null));
            }
        });
    }


    public void cancelNotification() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
    }

    public void showNotification(String title, String message) {
        firebaseAuth = FirebaseAuth.getInstance();

        currentuser = firebaseAuth.getCurrentUser();

       // databaseReference.child("USER_LOCATION").child("Beacon").setValue(loc);
       // Toast.makeText(getApplicationContext(),"Registered successfully",Toast.LENGTH_LONG).show();
        Intent notifyIntent = new Intent(this, BeaconTrigger.class);
            Bundle bundle = new Bundle();
            bundle.putString("trigger", "1");
            notifyIntent.putExtras(bundle);
        //  startActivity(notifyIntent);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

    }
}

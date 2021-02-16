package com.example.android.geofencing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.android.geofencing.MapsActivity;
import com.example.android.geofencing.NotificationHelper;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceiv";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

         NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event");
            return;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence: geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
          //  Toast.makeText(context, "Geofence ID : "+ geofence.getRequestId() + " has triggered: ", Toast.LENGTH_SHORT).show();

            int transitionType = geofencingEvent.getGeofenceTransition();

            switch (transitionType) {
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                    Toast.makeText(context, " The senior has entered Geofence ID : " + geofence.getRequestId(), Toast.LENGTH_SHORT).show();
                    notificationHelper.sendHighPriorityNotification(" The senior has entered Geofence ID : "+ geofence.getRequestId(), "", MapsActivity.class);
                    break;
                case Geofence.GEOFENCE_TRANSITION_DWELL:
                    Toast.makeText(context, "The senior is dwelling Geofence ID :" + geofence.getRequestId(), Toast.LENGTH_SHORT).show();
                    notificationHelper.sendHighPriorityNotification("The senior is dwelling Geofence ID :"+ geofence.getRequestId() , "", MapsActivity.class);
                    break;
                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    Toast.makeText(context, "The senior has exited Geofence ID :"+ geofence.getRequestId(), Toast.LENGTH_SHORT).show();
                    notificationHelper.sendHighPriorityNotification("The senior has exited Geofence ID : "+ geofence.getRequestId(), "", MapsActivity.class);
                    break;
            }

        }



    }
}
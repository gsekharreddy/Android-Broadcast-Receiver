package com.example.broadcastreceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Example data payload from server
        if (remoteMessage.getData().size() > 0) {
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            String timestamp = remoteMessage.getData().get("time"); // In millis or formatted time

            long triggerTime = Long.parseLong(timestamp); // e.g. future time in millis

            // Schedule with AlarmManager
            Intent intent = new Intent(this, NotificationReceiver.class);
            intent.putExtra("title", title);
            intent.putExtra("body", body);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }
    }
}

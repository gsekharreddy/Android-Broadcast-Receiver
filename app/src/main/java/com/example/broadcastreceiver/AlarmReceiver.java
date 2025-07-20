package com.example.broadcastreceiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }

    private void showNotification(Context context) {
        String channelId = "alarm_channel";
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create channel for Android 8+
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId, "Alarm Notification", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        Intent i = new Intent(context, ScheduleAlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.outline_circle_notifications_24) // Add your icon in res/drawable
                .setContentTitle("Alarm Triggered")
                .setContentText("This is your scheduled notification.")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        manager.notify(1, builder.build());
    }
}

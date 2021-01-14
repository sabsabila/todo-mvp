package pens.lab.app.belajaractivity.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import pens.lab.app.belajaractivity.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String taskData = intent.getStringExtra("title") + " | " + intent.getStringExtra("description");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "notification")
                        .setSmallIcon(R.drawable.alarm_icon)
                        .setContentTitle("One of your task needs to be done !")
                        .setContentText(taskData)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, mBuilder.build());
    }
}

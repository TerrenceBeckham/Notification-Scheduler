package com.afrodroid.notificationscheduler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationJobService extends JobService {
    NotificationManager mNotifyManager;
    //Notification channel Id
    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    @Override
    public boolean onStartJob(JobParameters params) {

        createNotificationChannel();

        //Set up the notification content intent to launch the app when clicked.
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this,
                0, new Intent(this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_job_running)
                .setContentTitle(getString(R.string.job_service_notification))
                .setContentText(getString(R.string.job_notification_text))
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
//        Build the notification
        mNotifyManager.notify(0, builder.build());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        return true;
    }

    public void createNotificationChannel() {
        /**
         * Make a notification channel for Oreo or higher.
         */
        //Define notification manager object.
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Notification channels are only available in Oreo or higher.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Make the NotificationChannel with all of the parameters.
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Job Service notification", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Job Service");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
}

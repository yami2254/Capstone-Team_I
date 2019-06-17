package com.example.ex_login;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FIREBASE_MESSAGING";

    /**
     *  1. Using the Firebase Console
     *   - foreground: can be received through onMessageReceived,
     *      *                 can custom notification(sound, light, vibration, efc).
     *   - background: default notification not through onMessageReceived,
     *                 if you defined default option in Manifest.xml.
     *   * additional benefit: can see how many opened notifications.
     *
     *  2. Using JSON POST tool (ex advanced REST Client, postman)
     *  - foreground, background are same, can custom notification.
     *  - additional setting: In MainActivity' onCreate, need to put subscribe 'Your Topic' codes.
     *                        Then, you can send push message to all users.
     *  - If using advanced REST Client,
     *      Method: POST
     *      Request URL: https://fcm.googleapis.com/fcm/send
     *      Headers > Content-Type: application/json
     *              > Authorization > key='Your FCM Server Key'
     *      Body    > {
     *                  "to": "/topics/Your Topic",
     *                  "data": {
     *                  "title": "Test title",
     *                  "content": "Test content"
     *                  }
     *                }
     *      SEND
     *  - Reference URL: http://bitly.kr/Ltdkcg
     *  - Download URL(advanced REST Client): http://bitly.kr/bwhWkH
     */

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (false) { // using true, if push data over 4KB or 10 seconds
                scheduleJob();
            } else {
                handleNow();
            }
        }

        if (remoteMessage.getNotification() != null) {
            // send using fcm
            // foreground only
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            Log.d(TAG, "Message Notification: " + remoteMessage.getNotification().getTitle() + remoteMessage.getNotification().getBody());

            sendNotification(title, body);
        } else if (remoteMessage.getNotification() == null && remoteMessage.getData().size() > 0){
            // send using self post
            // foreground, background are same.
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("content");

            Log.d(TAG, "Message Data: " + remoteMessage.getData());

            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myApp:Wakelock");
            wakeLock.acquire(3000);

            sendNotification(title, body);
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }

    private void scheduleJob() {
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();
        WorkManager.getInstance().beginWith(work).enqueue();
    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendNotification(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.school3)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{300, 200, 300, 200})
                        .setLights(Color.BLUE, 1, 1)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel Name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
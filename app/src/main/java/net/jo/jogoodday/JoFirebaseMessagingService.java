package net.jo.jogoodday;

import android.app.NotificationManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * 接收FCM, 自訂Notification
 * Created by Johnny on 2017/11/18.
 */

public class JoFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "Msg";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Jo 測試通知啦！")
                .setContentText("Jo的FCM就是不一樣！最高87分！")
                .setSmallIcon(R.mipmap.ic_launcher_jo)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        manager.notify(78, builder.build());


    }

}

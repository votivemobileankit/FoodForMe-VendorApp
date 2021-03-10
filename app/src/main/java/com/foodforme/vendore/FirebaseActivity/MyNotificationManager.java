package com.foodforme.vendore.FirebaseActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.foodforme.vendore.R;
import com.foodforme.vendore.activity.MainActivity;
import com.foodforme.vendore.serverintegration.Constant;


import static android.content.Context.NOTIFICATION_SERVICE;

public class MyNotificationManager {
    public Context mCtx;
    private static MyNotificationManager mInstance;

    private MyNotificationManager(Context context) {
        mCtx = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }
    Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mCtx.getPackageName() + "/" + R.raw.ringtone);

    public void displayNotification(String title, String body) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mCtx, Constant.CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setSound(sound)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentText(body);
        Intent resultIntent = new Intent(mCtx, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotifyMgr = (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);


        if (mNotifyMgr != null) {
            mNotifyMgr.notify(1, mBuilder.build());
        }
    }
}

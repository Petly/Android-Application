package com.hackaton.petly.notifications;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.hackaton.petly.R;
import com.hackaton.petly.ui.activities.MainActivity;
import com.parse.ParsePushBroadcastReceiver;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by guillermo.rosales on 7/24/15.
 */
public class GcmBroadcastReceiver extends ParsePushBroadcastReceiver {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManagerCompat mNotificationManager;

    @Override
    public void onPushReceive(Context context, Intent intent) {
        sendTimeLineNotification(context, intent);

    }


    public void sendTimeLineNotification(final Context context, Intent intent) {


        Target saveFileTarget = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mNotificationManager = NotificationManagerCompat.from(context);
                        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                                new Intent(context, MainActivity.class), 0);
                        Intent adopIntent = new Intent(context, AdoptIntentService.class);
                        PendingIntent pendingAdoptIntent =  PendingIntent.getService(context, 0, adopIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(context)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setContentTitle("Petly")
                                        .setAutoCancel(true)
                                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                        .setVibrate(new long[]{1000, 1000, 1000})
                                        .setContentText("Perro en adopción")
                                        .addAction(R.drawable.ic_insert_emoticon_black, "Adoptar", pendingAdoptIntent)
                                        .addAction(R.drawable.ic_favorite_white, "Favorito", contentIntent)
                                        .setStyle(new NotificationCompat.BigPictureStyle()
                                                .bigPicture(bitmap));


                        mBuilder.setContentIntent(contentIntent);
                        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };


        String action = intent.getAction();
        JSONObject json = null;

        InputStream bitmap = null;
        try {
            json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            Log.d("json", json.toString());
            Picasso.with(context)
                    .load(json.getJSONObject("imagageUrl").getString("_url"))
                    .into(saveFileTarget);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
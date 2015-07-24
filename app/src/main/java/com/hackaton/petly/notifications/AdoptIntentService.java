package com.hackaton.petly.notifications;

/**
 * Created by guillermo.rosales on 7/24/15.
 */

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseObject;

public class AdoptIntentService extends IntentService {


    public AdoptIntentService() {
        super("AdoptIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
        adoptDog();

    }

    public void adoptDog(){
        ParseObject gameScore = new ParseObject("Adpotion");
        gameScore.put("perro", 1);
        gameScore.saveInBackground();

    }



}


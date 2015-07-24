package com.hackathon.petly.app;

import android.app.Application;
import android.util.Log;

import com.hackaton.petly.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by leonelmendez on 21/07/15.
 */
public class PetlyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(PetlyApplication.this, getString(R.string.parse_id), getString(R.string.parse_client_key));
        ParseFacebookUtils.initialize(PetlyApplication.this);
        ParsePush.subscribeInBackground("Timeline", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }
}

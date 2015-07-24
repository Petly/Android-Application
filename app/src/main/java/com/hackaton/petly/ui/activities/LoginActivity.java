package com.hackaton.petly.ui.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.eftimoff.androipathview.PathView;
import com.facebook.FacebookSdk;
import com.hackaton.petly.R;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity{

    @Bind(R.id.path_view)
    PathView pathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pathView.setFillAfter(true);
        pathView.getPathAnimator()
                .delay(100)
                .duration(1300)
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();

        userLoggedInParse();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.hackaton.petly",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("TAG_KEY_HASH", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int layoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode,resultCode,data);
    }


    @OnClick(R.id.login_button)
    public void loginFacebook(final View view){
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, Arrays.asList(getResources().getStringArray(R.array.facebook_permissions)), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Snackbar.make(view, getString(R.string.login_error), Snackbar.LENGTH_SHORT).show();
                } else if (user.isNew()) {
                    startActivity(MainActivity.createIntent(LoginActivity.this));
                    finish();
                } else {
                    startActivity(MainActivity.createIntent(LoginActivity.this));
                    finish();
                }
            }
        });
    }

    private void userLoggedInParse(){
        ParseUser parseUser = ParseUser.getCurrentUser();
        if(parseUser != null && ParseFacebookUtils.isLinked(parseUser)){
            startActivity(MainActivity.createIntent(LoginActivity.this));
            finish();
        }
    }
}

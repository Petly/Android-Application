package com.hackaton.petly.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.hackaton.petly.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends BaseActivity {

   public static Intent createIntent(Activity activity){
       Intent mainActivityIntent = new Intent(activity,MainActivity.class);
       return mainActivityIntent;
   }

    @Override
    public int layoutResource() {
        return R.layout.activity_main;
    }
}

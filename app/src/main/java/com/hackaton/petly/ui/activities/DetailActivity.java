package com.hackaton.petly.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.hackaton.petly.R;
import com.hackaton.petly.ui.fragments.DetailFragment;
import com.parse.ParseObject;

public class DetailActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getIntent().getExtras() != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_fragment,DetailFragment.newInstance(getIntent().getExtras()))
                    .commit();
        }
        supportPostponeEnterTransition();
    }

    public static Intent createIntent(Activity activity, Bundle bundle){

        Intent detailActivityIntent = new Intent(activity,DetailActivity.class);
        detailActivityIntent.putExtras(bundle);
        return detailActivityIntent;
    }


}

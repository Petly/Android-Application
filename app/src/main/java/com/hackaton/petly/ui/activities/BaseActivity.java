package com.hackaton.petly.ui.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.hackaton.petly.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResource());
        ButterKnife.bind(BaseActivity.this);

    }
    abstract public int layoutResource();
}

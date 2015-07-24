package com.hackaton.petly.ui.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackaton.petly.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by leonelmendez on 19/07/15.
 */
public abstract class BaseFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(fragmentLayoutResource(),container,false);
        ButterKnife.bind(BaseFragment.this, mainView);
        return mainView;
    }

    abstract public int fragmentLayoutResource();
}

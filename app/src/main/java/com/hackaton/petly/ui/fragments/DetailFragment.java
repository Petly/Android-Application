package com.hackaton.petly.ui.fragments;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackaton.petly.R;
import com.parse.ParseImageView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leonelmendez on 22/07/15.
 */
public class DetailFragment extends BaseFragment {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.dog_image)
    ImageView dogImage;
    @Bind(R.id.detail_dog_name)
    TextView detailDogName;
    @Bind(R.id.detail_dog_age)
    TextView detailDogAge;
    @Bind(R.id.detail_dog_race)
    TextView detailDogRace;
    @Bind(R.id.detail_dog_desc)
    TextView detailDogDesc;

    static final String DETAIL_TRANSITION_ANIMATION = "DTA";
    private boolean mTransitionAnimation;

    public static DetailFragment newInstance(Bundle extras){
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(extras);
        return detailFragment;
    }


    @Override
    public int fragmentLayoutResource() {
        return R.layout.fragment_detail;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();

        if(args != null){
            mTransitionAnimation = getArguments().getBoolean(DETAIL_TRANSITION_ANIMATION);
        }

        if(args != null){
            Picasso.with(getActivity())
                    .load(getArguments().getString("url_image"))
                    .into(dogImage);
            detailDogName.setText(args.getString("dog_name"));
            detailDogAge.setText(args.getString("dog_age"));
            detailDogRace.setText(args.getString("dog_race"));
            detailDogDesc.setText(args.getString("dog_desc"));
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mTransitionAnimation){
            getActivity().supportStartPostponedEnterTransition();
        }
    }

    @OnClick(R.id.i_love_him)
    public void iLoveHim(View view){
        Snackbar.make(view, "¡Lo amo!", Snackbar.LENGTH_SHORT).show();
    }

}

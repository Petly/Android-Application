package com.hackaton.petly.ui.fragments;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.hackaton.petly.R;
import com.hackaton.petly.adapters.ParseRecyclerAdapter;
import com.hackaton.petly.ui.activities.DetailActivity;
import com.hackaton.petly.utils.CircleTransform;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import butterknife.Bind;

/**
 * Created by leonelmendez on 19/07/15.
 */
public class MainFragment extends BaseFragment {

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.timeline)
    RecyclerView timeLine;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private boolean mHoldTransition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        TypedArray typedArray = activity.obtainStyledAttributes(attrs,R.styleable.TimelineFragment,0,0);
        mHoldTransition = typedArray.getBoolean(R.styleable.TimelineFragment_sharedElementTransitions,false);
        typedArray.recycle();

    }

    @Override
    public int fragmentLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.app_name,R.string.app_name){

        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        timeLine.setLayoutManager(layoutManager);

        ParseRecyclerAdapter parseRecyclerAdapter = new ParseRecyclerAdapter(getActivity());
        timeLine.setAdapter(parseRecyclerAdapter);
        parseRecyclerAdapter.loadObjects();

       parseRecyclerAdapter.setOnRecyclerViewClickListener(new ParseRecyclerAdapter.OnRecyclerViewClickListener() {
           @Override
           public void onRecyclerViewClick(ParseObject parseObject) {

               Bundle bundle = new Bundle();
               bundle.putString("url_image", parseObject.getParseFile("image").getUrl());
               bundle.putString("dog_name", parseObject.getString("name"));
               bundle.putString("dog_age", parseObject.getString("age"));
               bundle.putString("dog_desc", parseObject.getString("description"));
               bundle.putString("dog_race", parseObject.getString("race"));
               bundle.putBoolean(DetailFragment.DETAIL_TRANSITION_ANIMATION,true);

               startActivity(DetailActivity.createIntent(getActivity(), bundle));
           }
       });

        NavigationView navView = (NavigationView) view.findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        if(toolbar != null) {
            toolbar.setTitle(getString(R.string.app_name));
            toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }

        getUserInfoFromFacebook(view);

        if(parseRecyclerAdapter.getItemCount() == 0){
            getActivity().supportStartPostponedEnterTransition();
        }

        if(mHoldTransition){
            getActivity().supportStartPostponedEnterTransition();
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mHoldTransition){
            getActivity().supportStartPostponedEnterTransition();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getUserInfoFromFacebook(final View view) {

        GraphRequest me = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                Log.d(MainFragment.class.getSimpleName(), jsonObject.toString());
                ((TextView)view.findViewById(R.id.username)).setText(jsonObject.optString("name"));

                Picasso.with(view.getContext())
                        .load("https://graph.facebook.com/" + jsonObject.optString("id") + "/picture?type=large")
                        .transform(new CircleTransform())
                        .into((ImageView) view.findViewById(R.id.avatar));

            }
        });

        me.executeAsync();
    }

}

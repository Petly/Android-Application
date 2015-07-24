package com.hackaton.petly.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackaton.petly.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by leonelmendez on 23/07/15.
 */
public class ParseRecyclerAdapter extends RecyclerView.Adapter<ParseRecyclerAdapter.ParseRecyclerViewHolder> {

    private List<ParseObject> timeLime;
    private Context mContext;


    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public ParseRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
        timeLime = new ArrayList<>();
    }

    @Override
    public ParseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View mainView = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_card_layout,parent,false);
        return new ParseRecyclerViewHolder(mainView);
    }

    @Override
    public void onBindViewHolder(ParseRecyclerViewHolder holder, final int position) {

        holder.parseImageView.setParseFile(getTimeLime().get(position).getParseFile("image"));
        holder.parseImageView.loadInBackground();
        holder.dogName.setText(getTimeLime().get(position).getString("name"));
        holder.dogRace.setText("Raza: "+getTimeLime().get(position).getString("race"));
        holder.dogAge.setText("Edad: "+getTimeLime().get(position).getString("age"));
        holder.dogDesc.setText(getTimeLime().get(position).getString("description"));

        holder.mainContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRecyclerViewClickListener != null){
                    onRecyclerViewClickListener.onRecyclerViewClick(getTimeLime().get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return timeLime == null?0:timeLime.size();
    }

    public List<ParseObject> getTimeLime() {
        return timeLime;
    }

    public void setTimeLime(List<ParseObject> timeLime) {
        if(this.timeLime != null && this.timeLime.size() > 0)
            this.timeLime.clear();
        this.timeLime.addAll(timeLime);
        notifyDataSetChanged();
    }

    public void loadObjects(){

        final ParseQuery<ParseObject> timeLimeQuery = new ParseQuery<ParseObject>("Timeline");
        timeLimeQuery.orderByDescending("createdAt");
        timeLimeQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null)
                    setTimeLime(list);
            }
        });
    }

    public class ParseRecyclerViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.card_container)
        CardView mainContainer;
        @Bind(R.id.thumbnail)
        ParseImageView parseImageView;
        @Bind(R.id.dog_name)
        TextView dogName;
        @Bind(R.id.dog_age)
        TextView dogAge;
        @Bind(R.id.dog_desc)
        TextView dogDesc;
        @Bind(R.id.dog_race)
        TextView dogRace;

        public ParseRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    public interface OnRecyclerViewClickListener{
        void onRecyclerViewClick(ParseObject parseObject);
    }

}

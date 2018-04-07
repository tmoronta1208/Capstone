package com.example.c4q.capstone.userinterface.user.userprofilefragments.userprofileviews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.c4q.capstone.R;
import com.example.c4q.capstone.userinterface.CurrentUser;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by melg on 3/18/18.
 */

public class EventsViewHolder extends RecyclerView.ViewHolder {
    private TextView event_name, event_date;
    private Context eventContext;
    private CircleImageView userIcon;

    public EventsViewHolder(View itemView) {
        super(itemView);
    }

    public void setEvent_name(String name) {
        event_name = itemView.findViewById(R.id.event_name);
        event_name.setText(name);
    }

    public Context getEventContext() {
        eventContext = itemView.getContext();
        return eventContext;
    }

    public void setUserIcon(String url) {
        userIcon = itemView.findViewById(R.id.userImageProfile);
        Glide.with(getEventContext()).load(url).into(userIcon);
    }
    public void setEvent_date(String date){
        event_date = itemView.findViewById(R.id.event_date_tv);
        event_date.setText(date);
    }

}

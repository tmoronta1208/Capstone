package com.example.c4q.capstone.userinterface.events.eventfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c4q.capstone.R;
import com.example.c4q.capstone.database.model.events.Events;
import com.example.c4q.capstone.database.model.publicuserdata.PublicUser;
import com.example.c4q.capstone.userinterface.events.EventDataListener;
import com.example.c4q.capstone.userinterface.events.EventPresenter;
import com.example.c4q.capstone.utils.FBUserDataListener;
import com.example.c4q.capstone.utils.FBUserDataUtility;
import com.example.c4q.capstone.utils.FBUserFriendsListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SingleEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SingleEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleEventFragment extends Fragment {
    Bundle bundle;
    View rootView;
    String eventID;
    EventPresenter eventPresenter;
    private static Events currentEvent;
    TextView eventName, eventOrganizer, eventDate;
    String organizerFullName;
    UserFriendsFragment userFriendsFragment;
    List<PublicUser> friendsUserList = new ArrayList<>();
    FBUserDataUtility fbUserDataUtility = new FBUserDataUtility();
    List<String> friendKeys = new ArrayList<>();




    public SingleEventFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_single_event, container, false);
        bundle = getArguments();

        if (bundle != null) {
           eventID = bundle.getString("eventID", null);
        }
        eventPresenter = new EventPresenter();
        eventName = (TextView) rootView.findViewById(R.id.event_title_text_view);
        eventOrganizer = (TextView) rootView.findViewById(R.id.event_organier_text_view);
        eventDate = (TextView) rootView.findViewById(R.id.event_date_text_view);
        userFriendsFragment = new UserFriendsFragment();
        getEventData();

        loadUserFriendsFragment();

        return rootView;
    }

    public void getEventData(){
        eventPresenter.getEventFromDB(eventID, new EventDataListener() {
            @Override
            public void getEvent(Events event) {
                currentEvent = event;
                if(currentEvent != null){
                    Log.d ("Event Fragment", "event: name" + event.getEvent_name());
                    eventName.setText(currentEvent.getEvent_name());

                    eventDate.setText(currentEvent.getEvent_date());
                } else {
                    Log.d ("Event Fragment", "event is null");
                }
            }

            @Override
            public void getUserFullName(String name) {
                organizerFullName = name;
                eventOrganizer.setText(organizerFullName);
            }

            @Override
            public void getUser(PublicUser publicUser) {

            }
        });

    }

    public void loadUserFriendsFragment(){

        userFriendsFragment = new UserFriendsFragment();
        userFriendsFragment.getList(friendsUserList);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.event_fragment_container, userFriendsFragment).commit();


    }





}
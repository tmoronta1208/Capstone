package com.example.c4q.capstone.network.foursquare.foursquaremodel.detailsmodel;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("unreadCount")
    private int unreadCount;

    public int getUnreadCount() {
        return unreadCount;
    }
}
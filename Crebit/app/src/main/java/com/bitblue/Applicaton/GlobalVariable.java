package com.bitblue.Applicaton;

import android.app.Application;

import com.bitblue.crebit.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

public class GlobalVariable extends Application {
    private String availableBalance;

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<>();

    public synchronized Tracker getTracker(TrackerName trackerId) {

        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(R.xml.app_tracker);
            mTrackers.put(trackerId, t);
        }
        return mTrackers.get(trackerId);
    }
    public enum TrackerName {
        APP_TRACKER,
        GLOBAL_TRACKER,
        E_COMMERCE_TRACKER,
    }
}

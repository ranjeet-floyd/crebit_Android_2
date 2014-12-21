package com.bitblue.crebit.servicespage.fragments.electricity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitblue.crebit.R;

public class TorrentPower extends Fragment {
    public static TorrentPower newInstance(String param1, String param2) {
        TorrentPower fragment = new TorrentPower();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TorrentPower() {
    }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_torrent_power, container, false);
    }
}

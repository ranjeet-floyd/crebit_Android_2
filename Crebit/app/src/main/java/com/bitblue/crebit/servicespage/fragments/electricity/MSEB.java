package com.bitblue.crebit.servicespage.fragments.electricity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitblue.crebit.R;

public class MSEB extends Fragment {
    public static MSEB newInstance(String param1, String param2) {
        MSEB fragment = new MSEB();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MSEB() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mseb, container, false);
    }
}

package com.bitblue.crebit.servicespage.fragments.margin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bitblue.Applicaton.GlobalVariable;
import com.bitblue.apinames.API;
import com.bitblue.crebit.R;
import com.bitblue.crebit.servicespage.listAdapter.OpMarCustomAdapter;
import com.bitblue.jsonparse.JSONParser;
import com.bitblue.response.MarginResult;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Margin extends Fragment {
    private JSONParser jsonParser;
    private ListView listView;
    private Tracker tracker;

    public Margin() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_margin, container, false);
        listView = (ListView) view.findViewById(R.id.operator_margin_list);
        tracker = ((GlobalVariable) getActivity().getApplication()).getTracker(GlobalVariable.TrackerName.APP_TRACKER);
        tracker.setScreenName("Margin Page");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
        new retrieveMargin().execute();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Get an Analytics tracker to report app starts & uncaught exceptions etc.
        GoogleAnalytics.getInstance(getActivity()).reportActivityStart(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        //Stop the analytics tracking
        GoogleAnalytics.getInstance(getActivity()).reportActivityStop(getActivity());
    }


    private class retrieveMargin extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        String jsonResult;
        JSONArray jsonArray;
        JSONObject jsonObject;
        MarginResult marginResult;
        ArrayList<MarginResult> marginResultArrayList;

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please wait ...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            jsonParser = new JSONParser();
            jsonResult = jsonParser.getMargin(API.DHS_OPERATOR_MARGIN);
            try {
                jsonArray = new JSONArray(jsonResult);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            marginResultArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                    marginResult = new MarginResult(String.valueOf(i + 1), jsonObject.getString("type"),
                            jsonObject.getString("name"), jsonObject.getString("margin"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                marginResultArrayList.add(marginResult);
            }
            listView.setAdapter(new OpMarCustomAdapter(getActivity(), marginResultArrayList));
        }
    }
}

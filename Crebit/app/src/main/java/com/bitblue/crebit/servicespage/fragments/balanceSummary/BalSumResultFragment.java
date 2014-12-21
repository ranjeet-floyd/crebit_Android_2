package com.bitblue.crebit.servicespage.fragments.balanceSummary;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bitblue.apinames.API;
import com.bitblue.crebit.R;
import com.bitblue.jsonparse.JSONParser;
import com.bitblue.network.NetworkUtil;
import com.bitblue.requestparam.BalSumParams;
import com.bitblue.response.BalSumResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BalSumResultFragment extends Fragment {
    private TextView tvbaltaken, tvnodata;

    private ListView resultList;
    private Double TotalBalanceGiven, TotalBalanceTaken;

    private ArrayList<BalSumResult> balSumResultList = new ArrayList<BalSumResult>();
    private String UserId, Key, fromDate, toDate, TypeId, Value;
    private SharedPreferences prefs;
    private final static String MY_PREFS = "mySharedPrefs";
    private JSONParser jsonParser;
    private JSONObject jsonResponse, balanceUseArrobject;
    private BalSumParams balSumParams;
    private BalSumResponse balSumResponse;
    private BalSumResult balSumResult;
    private ArrayList<NameValuePair> nameValuePairs;
    private JSONArray balanceUseArr;

    public BalSumResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bal_sum_result, container, false);
        prefs = getActivity().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        fromDate = getArguments().getString("fromDate");
        toDate = getArguments().getString("toDate");
        UserId = prefs.getString("userId", "");
        Key = prefs.getString("userKey", "");
        TypeId = prefs.getString("TypeId", "");
        Value = prefs.getString("Value", "");
        initViews(view);
        resultList = (ListView) view.findViewById(R.id.lv_balsum_result);
        new retrieveData().execute();
        return view;
    }

    private void initViews(View view) {
        tvbaltaken = (TextView) view.findViewById(R.id.tvbaltaken);
        tvnodata = (TextView) view.findViewById(R.id.tv_balSum_list_nodata);
    }

    private class retrieveData extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please Wait...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            jsonParser = new JSONParser();
            balSumParams = new BalSumParams(UserId, Key, fromDate, toDate, TypeId, Value);
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("UserId", UserId));
            nameValuePairs.add(new BasicNameValuePair("Key", Key));
            nameValuePairs.add(new BasicNameValuePair("FromDate", fromDate));
            nameValuePairs.add(new BasicNameValuePair("ToDate", toDate));
            nameValuePairs.add(new BasicNameValuePair("TypeId", TypeId));
            nameValuePairs.add(new BasicNameValuePair("Value", Value));
            jsonResponse = jsonParser.makeHttpPostRequestforJsonObject(API.DASHBOARD_BALANCE_USE, nameValuePairs);
            if (jsonResponse == null) {
                return null;
            } else {
                try {
                    balSumResponse = new BalSumResponse(jsonResponse.getDouble("totalBalanceGiven"),
                            jsonResponse.getDouble("totalBalanceTaken"),
                            jsonResponse.getJSONArray("balanceUse"));
                    TotalBalanceGiven = balSumResponse.getTotalBalanceGiven();
                    TotalBalanceTaken = balSumResponse.getTotalBalanceTaken();
                    balanceUseArr = balSumResponse.getBalUse();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return String.valueOf(TotalBalanceGiven);
            }
        }

        @Override
        protected void onPostExecute(String status) {
            dialog.dismiss();
            if (status == null) {
                showAlertDialog();
            } else {
                tvbaltaken.setText(String.valueOf(TotalBalanceTaken));
                if (balanceUseArr.length() == 0) {
                    tvnodata.setVisibility(View.VISIBLE);
                    resultList.setVisibility(View.GONE);
                } else {
                    tvnodata.setVisibility(View.GONE);
                    resultList.setVisibility(View.VISIBLE);
                    for (int i = 0; i < balanceUseArr.length(); i++) {
                        try {
                            balanceUseArrobject = (JSONObject) balanceUseArr.get(i);
                            balSumResult = new BalSumResult();
                            balSumResult.setCount(i + 1);
                            balSumResult.setName(balanceUseArrobject.getString("name"));
                            balSumResult.setAmount(balanceUseArrobject.getString("amount"));
                            balSumResult.setContact(balanceUseArrobject.getString("contact"));
                            balSumResult.setDate(balanceUseArrobject.getString("date"));
                            balSumResult.setTransactionId(balanceUseArrobject.getString("transactionId"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        balSumResultList.add(balSumResult);
                    }
                    resultList.setAdapter(new BalSumCustomAdapter(getActivity(), balSumResultList));
                }
            }
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("\tUnable to connect to Internet." +
                "\n \tCheck Your Network Connection.")
                .setCancelable(false)
                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isNetworkAvailable()) {
                            dialog.cancel();
                        } else {
                            showAlertDialog();
                        }
                    }
                })
                .setNeutralButton("Turn on Data", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static class NetworkChangeReceiver extends BroadcastReceiver {
        public NetworkChangeReceiver() {
        }

        @Override
        public void onReceive(final Context context, final Intent intent) {
            String status = NetworkUtil.getConnectivityStatusString(context);
        }
    }
}


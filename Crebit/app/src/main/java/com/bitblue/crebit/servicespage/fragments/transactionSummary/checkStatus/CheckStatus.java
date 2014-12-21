package com.bitblue.crebit.servicespage.fragments.transactionSummary.checkStatus;

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
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitblue.Applicaton.GlobalVariable;
import com.bitblue.apinames.API;
import com.bitblue.crebit.R;
import com.bitblue.jsonparse.JSONParser;
import com.bitblue.network.NetworkUtil;
import com.bitblue.nullcheck.Check;
import com.bitblue.requestparam.RefundOrTransParams;
import com.bitblue.response.RefundOrTransResponse;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckStatus extends ActionBarActivity implements View.OnClickListener {
    private String Status, OperatorName, Comments, Key, TransId, TypeId, UserId;
    private String RespStatus, Message, CyberTranID, OperatorId;
    private int cur;
    private TextView tvStatus, tvTranid, tvMessage;
    private EditText etcomment;
    private Button bchkstat, bsubrefreq;
    private LinearLayout checkstatus, chkstatButton;
    private JSONParser jsonParser;
    private ArrayList<NameValuePair> nameValuePairs;
    private JSONObject jsonResponse;
    private RefundOrTransParams refParams;
    private RefundOrTransResponse refResponse;
    private SharedPreferences prefs;
    private final static String MY_PREFS = "mySharedPrefs";
    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tracker = ((GlobalVariable) getApplication()).getTracker(GlobalVariable.TrackerName.APP_TRACKER);
        tracker.setScreenName("Check Status Page");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.crebit);
        setContentView(R.layout.activity_check_status);
        Status = getIntent().getStringExtra("Status");
        Log.e("Status", Status);
        OperatorName = getIntent().getStringExtra("OperatorName");
        Log.e("OperatorName", OperatorName);
        TransId = getIntent().getStringExtra("TransId");
        Log.e("TransId", TransId);
        initViews();
        if (!(Status.equals("Success"))) {  //This is to test the crebit api since check status is not working poroperly
            finish();
        }
        if (Check.ifCrebitApi(OperatorName)) {
            chkstatButton.setVisibility(View.GONE);
            checkstatus.setVisibility(View.GONE);
        } else {
            chkstatButton.setVisibility(View.VISIBLE);
            checkstatus.setVisibility(View.INVISIBLE);
        }
    }

    private void initViews() {

        tvStatus = (TextView) findViewById(R.id.tv_cs_Status);
        tvTranid = (TextView) findViewById(R.id.tv_cs_TransId);
        tvMessage = (TextView) findViewById(R.id.tv_cs_Message);
        etcomment = (EditText) findViewById(R.id.et_cs_comment);
        etcomment.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.edittext_focus);
                } else {
                    view.setBackgroundResource(R.drawable.edittext_lostfocus);
                }
            }
        });
        bchkstat = (Button) findViewById(R.id.b_cs_checkstatus);
        bsubrefreq = (Button) findViewById(R.id.b_subrefreq);
        prefs = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        UserId = prefs.getString("userId", "");
        Log.e("Uid", UserId);
        TypeId = prefs.getString("uType", "");
        Log.e("typeid", TypeId);
        Key = prefs.getString("userKey", "");
        Log.e("key", Key);
        bchkstat.setOnClickListener(this);
        bsubrefreq.setOnClickListener(this);
        checkstatus = (LinearLayout) findViewById(R.id.ll_checkstatus);
        chkstatButton = (LinearLayout) findViewById(R.id.ll_checkstatusButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Get an Analytics tracker to report app starts & uncaught exceptions etc.
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Stop the analytics tracking
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_cs_checkstatus:

                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Button")
                        .setAction("Clicked on Check Status Button on CheckStatus Page")
                        .setLabel("Check Status Button")
                        .build());
                cur = R.id.b_cs_checkstatus;
                Comments = etcomment.getText().toString();
                if (Check.ifNull(Comments))
                    Comments = "Comments";
                new checkrefundresult().execute();
                break;
            case R.id.b_subrefreq:

                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Button")
                        .setAction("Clicked on Submit request button on CheckStatus Page")
                        .setLabel("Submit Button")
                        .build());
                cur = R.id.b_subrefreq;
                Comments = etcomment.getText().toString();
                if (Check.ifNull(Comments)) {
                    etcomment.setText("");
                    etcomment.setHint("  Enter request for refund");
                    etcomment.setHintTextColor(getResources().getColor(R.color.red));
                    break;
                }
                new checkrefundresult().execute();
                break;
        }
    }

    private class checkrefundresult extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(CheckStatus.this);

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
            refParams = new RefundOrTransParams(Comments, Key, TransId, TypeId, UserId);
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("UserId", UserId));
            nameValuePairs.add(new BasicNameValuePair("Key", Key));
            nameValuePairs.add(new BasicNameValuePair("TransId", TransId));
            nameValuePairs.add(new BasicNameValuePair("TypeId", TypeId));
            nameValuePairs.add(new BasicNameValuePair("Comments", Comments));
            jsonResponse = jsonParser.makeHttpPostRequestforJsonObject(API.DASHBOARD_REFUNDORTRANS_STATUS, nameValuePairs);
            if (jsonResponse == null) {
                return null;
            } else {
                try {
                    refResponse = new RefundOrTransResponse(jsonResponse.getString("typeId"),
                            jsonResponse.getString("status"), jsonResponse.getString("message"),
                            jsonResponse.getString("cybertransId"), jsonResponse.getString("operatorId"));
                    Log.e("Response=", "\nTypeid: " +
                            jsonResponse.getString("typeId") + "\nMessage: " +
                            jsonResponse.getString("message") + "\nCybertransid: " +
                            jsonResponse.getString("cybertransId") + "\nStatus: " +
                            jsonResponse.getString("status"));
                    TypeId = refResponse.getTypeId();
                    RespStatus = refResponse.getStatus();
                    Message = refResponse.getMessage();
                    CyberTranID = refResponse.getCybertransId();
                    OperatorId = refResponse.getOperatorId();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return RespStatus;
            }
        }

        @Override
        protected void onPostExecute(String status) {
            dialog.dismiss();
            if (status == null) {
                showAlertDialog();
            } else {
                if (Message.equals("null")) Message = "";
                tvStatus.setText("Status: " + RespStatus);
                tvTranid.setText("TransId: " + CyberTranID);
                tvMessage.setText("Message: " + Message);
                if ((cur == R.id.b_subrefreq) || Check.ifCrebitApi(OperatorName)) {
                    chkstatButton.setVisibility(View.INVISIBLE);
                    checkstatus.setVisibility(View.INVISIBLE);
                } else {
                    chkstatButton.setVisibility(View.VISIBLE);
                    checkstatus.setVisibility(View.VISIBLE);
                    bchkstat.setVisibility(View.GONE);
                }
            }
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

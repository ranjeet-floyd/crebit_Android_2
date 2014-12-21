package in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.activities;

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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.crebit.bitblue.app.WebView.bitblue.Applicaton.GlobalVariable;
import in.crebit.bitblue.app.WebView.bitblue.IDs.broadBand;
import in.crebit.bitblue.app.WebView.bitblue.R;
import in.crebit.bitblue.app.WebView.bitblue.apinames.API;
import in.crebit.bitblue.app.WebView.bitblue.jsonparse.JSONParser;
import in.crebit.bitblue.app.WebView.bitblue.network.NetworkUtil;
import in.crebit.bitblue.app.WebView.bitblue.nullcheck.Check;
import in.crebit.bitblue.app.WebView.bitblue.requestparam.BroadBandParams;
import in.crebit.bitblue.app.WebView.bitblue.response.BroadBandResponse;

public class BroadBand extends ActionBarActivity implements View.OnClickListener {
    private TextView operator, number, amount;
    private EditText et_number, et_amount;
    private Button recharge, operatorType;
    private TextView transId, message, statcode, availablebal;

    private String UserId, Key, OperatorId, Number, Account = "";
    private String Amount;
    private static final String SOURCE = "2";
    private Tracker tracker;

    private ArrayAdapter<String> adapter;
    private String[] items;
    private JSONParser jsonParser;
    private JSONObject jsonResponse;
    private BroadBandResponse broadBandResponse;
    private BroadBandParams broadBandParams;
    private List<NameValuePair> nameValuePairs;
    private GlobalVariable globalVariable;
    private String TransId, Message;
    private int StatusCode;
    private String AvailableBalance;

    private SharedPreferences prefs;
    private final static String MY_PREFS = "mySharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tracker = ((GlobalVariable) getApplication()).getTracker(GlobalVariable.TrackerName.APP_TRACKER);
        tracker.setScreenName("BroadBand Page");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        setContentView(R.layout.activity_broad_band);
        items = getResources().getStringArray(R.array.broadband_operator);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initViews();
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

    private void initViews() {
        operator = (TextView) findViewById(R.id.tv_bb_operator);
        number = (TextView) findViewById(R.id.tv_bb_number);
        amount = (TextView) findViewById(R.id.tv_bb_amount);

        et_number = (EditText) findViewById(R.id.et_bb_number);
        et_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.edittext_focus);
                } else {
                    view.setBackgroundResource(R.drawable.edittext_lostfocus);
                }
            }
        });
        et_amount = (EditText) findViewById(R.id.et_bb_amount);
        et_amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.edittext_focus);
                } else {
                    view.setBackgroundResource(R.drawable.edittext_lostfocus);
                }
            }
        });

        recharge = (Button) findViewById(R.id.b_bb_recharge);
        operatorType = (Button) findViewById(R.id.b_bb_operator);
        recharge.setOnClickListener(this);
        operatorType.setOnClickListener(this);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        UserId = prefs.getString("userId", "");
        Key = prefs.getString("userKey", "");
        globalVariable = (GlobalVariable) getApplicationContext();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_bb_operator:
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Button")
                        .setAction("Clicked on Select Operator Button on BroadBand Page")
                        .setLabel("Select Operator Button")
                        .build());
                new AlertDialog.Builder(this)
                        .setTitle("Select Operator")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position) {
                                OperatorId = broadBand.getBroadBandOperatorId(position);
                                operatorType.setText(items[position]);
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.b_bb_recharge:
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Button")
                        .setAction("Clicked on Recharge Button on BroadBand Page")
                        .setLabel("Recharge Button")
                        .build());
                Number = et_number.getText().toString();
                try {
                    Amount = et_amount.getText().toString();
                } catch (Exception e) {
                    Amount = "0";
                }
                if (operatorType.getText().equals("Select")) {
                    operator.setTextColor(getResources().getColor(R.color.red));
                    break;
                }
                if (Check.ifNull(Amount)) {
                    et_amount.setText("");
                    et_amount.setHint(" Enter correct Amount");
                    et_amount.setHintTextColor(getResources().getColor(R.color.red));
                    break;
                }
                if (Check.ifNumberInCorrect(Number)) {
                    et_number.setText("");
                    et_number.setHint(" Enter correct Number");
                    et_number.setHintTextColor(getResources().getColor(R.color.red));
                    break;
                }
                new retrievebroadbanddata().execute();
                break;
        }
    }

    private class retrievebroadbanddata extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(BroadBand.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please Wait...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            jsonParser = new JSONParser();
            broadBandParams = new BroadBandParams(UserId, Key, OperatorId, Number, Amount, SOURCE);
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("UserId", UserId));
            nameValuePairs.add(new BasicNameValuePair("Key", Key));
            nameValuePairs.add(new BasicNameValuePair("OperatorId", OperatorId));
            nameValuePairs.add(new BasicNameValuePair("Number", Number));
            nameValuePairs.add(new BasicNameValuePair("account", Account));
            nameValuePairs.add(new BasicNameValuePair("Amount", Amount));
            nameValuePairs.add(new BasicNameValuePair("Source", SOURCE));
            jsonResponse = jsonParser.makeHttpPostRequestforJsonObject(API.DASHBOARD_SERVICE, nameValuePairs);
            if (jsonResponse == null) {
                return null;
            } else {
                try {
                    broadBandResponse = new BroadBandResponse(jsonResponse.getString("transId"),
                            jsonResponse.getString("message"),
                            jsonResponse.getInt("statusCode"),
                            jsonResponse.getString("availableBalance"));

                    TransId = broadBandResponse.getTransId();
                    Message = broadBandResponse.getMessage();
                    StatusCode = broadBandResponse.getStatusCode();
                    AvailableBalance = broadBandResponse.getAvailableBalance();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return String.valueOf(StatusCode);
            }
        }

        @Override
        protected void onPostExecute(String StatusCode) {
            dialog.dismiss();
            if (StatusCode == null) {
                showAlertDialog();
            } else if (StatusCode.equals("0") || StatusCode.equals("-1")) {
                TransId = Message = AvailableBalance = "";
                new AlertDialog.Builder(BroadBand.this)
                        .setTitle("Error").setIcon(getResources().getDrawable(R.drawable.erroricon))
                        .setMessage("Request Not Completed.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            } else if (StatusCode.equals("1")) {
                new AlertDialog.Builder(BroadBand.this)
                        .setTitle("Success").setIcon(getResources().getDrawable(R.drawable.successicon))
                        .setMessage("Request Completed." +
                                "\n\nTransaction ID: " + TransId +
                                "\nMessage: " + Message +
                                "\nAvailable Balance: " + AvailableBalance)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                globalVariable.setAvailableBalance(AvailableBalance);
            } else if (StatusCode.equals("2")) {
                new AlertDialog.Builder(BroadBand.this)
                        .setTitle("Error").setIcon(getResources().getDrawable(R.drawable.erroricon))
                        .setMessage("Insufficient Balance")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
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

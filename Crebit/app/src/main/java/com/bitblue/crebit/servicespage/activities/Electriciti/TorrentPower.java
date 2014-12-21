package com.bitblue.crebit.servicespage.activities.Electriciti;

import android.app.Activity;
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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bitblue.Applicaton.GlobalVariable;
import com.bitblue.IDs.torrpow;
import com.bitblue.apinames.API;
import com.bitblue.crebit.R;
import com.bitblue.jsonparse.JSONParser;
import com.bitblue.network.NetworkUtil;
import com.bitblue.nullcheck.Check;
import com.bitblue.requestparam.TorPowerParams;
import com.bitblue.response.TorPowerResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TorrentPower extends Activity implements View.OnClickListener {
    TextView tvCity, tvServiceno, tvcustmob, tvamount;
    EditText etServiceNo, etmobNo, etamount;
    Button bcity, bpayBill;
    private ArrayAdapter<String> adapter;
    private String Bu, Amount, CusAcc, CusMob, Key, UserId, Message;
    private int AvaiBal, Status;
    private String[] items;
    private JSONParser jsonParser;
    private JSONObject jsonResponse;
    private List<NameValuePair> nameValuePairs;
    private TorPowerParams torPowerParams;
    private TorPowerResponse torPowerResponse;
    private SharedPreferences prefs;
    private final static String MY_PREFS = "mySharedPrefs";
    private GlobalVariable globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = getResources().getStringArray(R.array.city);
        setContentView(R.layout.activity_torrent_power);
        initViews();
    }

    private void initViews() {
        tvCity = (TextView) findViewById(R.id.tv_elec_torrpow_city);
        tvServiceno = (TextView) findViewById(R.id.tv_elec_torrpow_serviceno);
        tvcustmob = (TextView) findViewById(R.id.tv_elec_torrpow_custmobno);
        tvamount = (TextView) findViewById(R.id.tv_elec_torrpow_amount);

        etServiceNo = (EditText) findViewById(R.id.et_elec_torrpow_serviceno);
        etServiceNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.edittext_focus);
                } else {
                    view.setBackgroundResource(R.drawable.edittext_lostfocus);
                }
            }
        });
        etmobNo = (EditText) findViewById(R.id.et_elec_torrpow_custmobno);
        etmobNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.edittext_focus);
                } else {
                    view.setBackgroundResource(R.drawable.edittext_lostfocus);
                }
            }
        });
        etamount = (EditText) findViewById(R.id.et_elec_torrpow_amount);
        etamount.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.edittext_focus);
                } else {
                    view.setBackgroundResource(R.drawable.edittext_lostfocus);
                }
            }
        });
        bcity = (Button) findViewById(R.id.b_elec_torrpow_city);
        bpayBill = (Button) findViewById(R.id.b_elec_torpow_paybill);
        bcity.setOnClickListener(this);
        bpayBill.setOnClickListener(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);

        prefs = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        UserId = prefs.getString("userId", "");
        Key = prefs.getString("userKey", "");
        globalVariable = (GlobalVariable) getApplicationContext();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_elec_torrpow_city:
                new AlertDialog.Builder(TorrentPower.this)
                        .setTitle("Select BU")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position) {
                                bcity.setText(items[position]);
                                Bu = torrpow.getCityID(position);
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.b_elec_torpow_paybill:
                CusAcc = etServiceNo.getText().toString();
                CusMob = etmobNo.getText().toString();
                Amount = etamount.getText().toString();
                if (bcity.getText().equals("Select")) {
                    bcity.setTextColor(getResources().getColor(R.color.red));
                    break;
                }
                if (Check.ifAccountNumberIncorrect(CusAcc)) {
                    etServiceNo.setText("");
                    etServiceNo.setHint(" Enter correct number");
                    etServiceNo.setHintTextColor(getResources().getColor(R.color.red));
                    break;
                }
                if (Check.ifNumberInCorrect(CusMob)) {
                    etmobNo.setText("");
                    etmobNo.setHint(" Enter correct number");
                    etmobNo.setHintTextColor(getResources().getColor(R.color.red));
                    break;
                }
                if (Check.ifEmpty(Double.parseDouble(Amount))) {
                    etamount.setText("");
                    etamount.setHint(" Enter correct number");
                    etamount.setHintTextColor(getResources().getColor(R.color.red));
                    break;
                }
                new retrieveData().execute();
                break;

        }
    }

    private class retrieveData extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(TorrentPower.this);

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
            torPowerParams = new TorPowerParams(Bu, Amount, CusAcc, CusMob, Key, UserId);
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("userId", UserId));
            nameValuePairs.add(new BasicNameValuePair("key", Key));
            nameValuePairs.add(new BasicNameValuePair("cusAcc", CusAcc));
            nameValuePairs.add(new BasicNameValuePair("cusMob", CusMob));
            nameValuePairs.add(new BasicNameValuePair("amount", Amount));
            nameValuePairs.add(new BasicNameValuePair("Bu", Bu));
            jsonResponse = jsonParser.makeHttpPostRequestforJsonObject(API.DHS_TORRENT_POWER, nameValuePairs);
            if (jsonResponse == null) {
                return null;
            } else {
                try {
                    torPowerResponse = new TorPowerResponse(jsonResponse.getInt("Status"),
                            jsonResponse.getString("Message"),
                            jsonResponse.getInt("AvaiBal"));

                    AvaiBal = torPowerResponse.getAvaiBal();
                    Message = torPowerResponse.getMessage();
                    Status = torPowerResponse.getStatus();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return String.valueOf(Status);
            }
        }

        @Override
        protected void onPostExecute(String StatusCode) {
            dialog.dismiss();
            if (StatusCode == null) {
                showAlertDialog();
            } else if (StatusCode.equals("0") || StatusCode.equals("-1")) {
                new AlertDialog.Builder(TorrentPower.this)
                        .setTitle("Error").setIcon(getResources().getDrawable(R.drawable.erroricon))
                        .setMessage("Request Not Completed.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            } else if (StatusCode.equals("1")) {
                if (Message == null || Message.equals("null"))
                    Message = "";
                new AlertDialog.Builder(TorrentPower.this)
                        .setTitle("Success").setIcon(getResources().getDrawable(R.drawable.successicon))
                        .setMessage("Request Completed." +
                                "\n\nMessage: " + Message +
                                "\nAvailable Balance: " + AvaiBal)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                globalVariable.setAvailableBalance(String.valueOf(AvaiBal));
            } else if (StatusCode.equals("2")) {
                new AlertDialog.Builder(TorrentPower.this)
                        .setTitle("Error").setIcon(getResources().getDrawable(R.drawable.erroricon))
                        .setMessage("Insufficient Balance")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                dialog.dismiss();
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

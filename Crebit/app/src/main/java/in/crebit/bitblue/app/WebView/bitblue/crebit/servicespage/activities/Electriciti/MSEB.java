package in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.activities.Electriciti;

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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.crebit.bitblue.app.WebView.bitblue.Applicaton.GlobalVariable;
import in.crebit.bitblue.app.WebView.bitblue.R;
import in.crebit.bitblue.app.WebView.bitblue.apinames.API;
import in.crebit.bitblue.app.WebView.bitblue.jsonparse.JSONParser;
import in.crebit.bitblue.app.WebView.bitblue.network.NetworkUtil;
import in.crebit.bitblue.app.WebView.bitblue.nullcheck.Check;
import in.crebit.bitblue.app.WebView.bitblue.requestparam.MsebParams;
import in.crebit.bitblue.app.WebView.bitblue.requestparam.MsebPayBillparams;
import in.crebit.bitblue.app.WebView.bitblue.response.MsebPayBllResponse;
import in.crebit.bitblue.app.WebView.bitblue.response.MsebResponse;

public class MSEB extends Activity implements View.OnClickListener {
    private String UserId, Key, Bu, DueDate, CusMob, BuCode, CusAcc;
    private int ServiceId = 40, BillAmount, ConsumptionUnits;

    private TextView tvBu, tvcustAccNo, tvbillAmount, tvDueDate, tvnotexceeded, tvbillMonth, tvConsunit;
    private Button bGetDetails, bpaybill;
    private EditText etcustAccNo, etcusMobNo;
    private LinearLayout llerrorDueDate, lleleccustno;
    private AutoCompleteTextView actvbu;
    private String[] items;
    private JSONParser jsonParser;
    private JSONObject jsonResponse;
    private MsebParams msebParams;
    private MsebResponse msebResponse;
    private MsebPayBillparams msebPayBillparams;
    private MsebPayBllResponse msebPayBllResponse;
    private List<NameValuePair> nameValuePairs;
    private int Status, AvailBal;
    private GlobalVariable globalVariable;
    private SharedPreferences prefs;
    private final static String MY_PREFS = "mySharedPrefs";
    private String BillMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = getResources().getStringArray(R.array.bu);
        setContentView(R.layout.activity_mseb);
        initViews();
    }

    private void initViews() {
        tvBu = (TextView) findViewById(R.id.tv_elec_mseb_buoperator);
        tvcustAccNo = (TextView) findViewById(R.id.tv_elec_mseb_cust_acc_no);
        tvbillAmount = (TextView) findViewById(R.id.tv_elec_mseb_Bill_Amount);
        tvDueDate = (TextView) findViewById(R.id.tv_elec_mseb_DueDate);
        tvnotexceeded = (TextView) findViewById(R.id.tv_elec_mseb_Not_Exceeded);
        tvbillMonth = (TextView) findViewById(R.id.tv_elec_mseb_BillMonth);
        tvConsunit = (TextView) findViewById(R.id.tv_elec_mseb_ConsumptionUnits);
        //  bBu = (Button) findViewById(R.id.b_elec_mseb_BU);
        actvbu = (AutoCompleteTextView) findViewById(R.id.b_elec_mseb_BU);
        ArrayAdapter adapter = new ArrayAdapter
                (this, R.layout.dropdownlist, items);

        actvbu.setThreshold(1);
        actvbu.setAdapter(adapter);
        bGetDetails = (Button) findViewById(R.id.b_elec_mseb_getDetails);
        bpaybill = (Button) findViewById(R.id.b_elec_mseb_paybill);
        etcustAccNo = (EditText) findViewById(R.id.et_elec_mseb_cust_acc_no);
        etcustAccNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.edittext_focus);
                } else {
                    view.setBackgroundResource(R.drawable.edittext_lostfocus);
                }
            }
        });
        etcusMobNo = (EditText) findViewById(R.id.et_elec_mseb_cust_mobno);
        etcusMobNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.edittext_focus);
                } else {
                    view.setBackgroundResource(R.drawable.edittext_lostfocus);
                }
            }
        });
        llerrorDueDate = (LinearLayout) findViewById(R.id.ll_error_result);
        lleleccustno = (LinearLayout) findViewById(R.id.ll_elec_mseb_cust_mobno);
        //  bBu.setOnClickListener(this);
        bGetDetails.setOnClickListener(this);
        bpaybill.setOnClickListener(this);

        // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        prefs = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        UserId = prefs.getString("userId", "");
        Key = prefs.getString("userKey", "");
        globalVariable=(GlobalVariable)getApplicationContext();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_elec_mseb_getDetails:
                CusAcc = etcustAccNo.getText().toString();
                Bu = actvbu.getText().toString();
                if (Check.ifNull(Bu)) {
                    tvBu.setTextColor(getResources().getColor(R.color.red));
                    break;
                } else {
                    BuCode = Bu.substring(0, 4);
                }
                if (Check.ifAccountNumberIncorrect(CusAcc)) {
                    etcustAccNo.setText("");
                    etcustAccNo.setHint(" Enter correct number");
                    etcustAccNo.setHintTextColor(getResources().getColor(R.color.red));
                    break;

                }
                new retrieveDataforGetDetails().execute();
                break;
            case R.id.b_elec_mseb_paybill:
                CusMob = etcusMobNo.getText().toString();
                if (Check.ifNumberInCorrect(CusMob)) {
                    etcusMobNo.setText("");
                    etcusMobNo.setHint(" Enter correct number");
                    etcusMobNo.setHintTextColor(getResources().getColor(R.color.red));
                    break;
                }
                new retrieveDataforPayBill().execute();
                break;
        }
    }

    private class retrieveDataforGetDetails extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(MSEB.this);

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
            msebParams = new MsebParams(UserId, Key, CusAcc, BuCode, ServiceId);
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("userId", UserId));
            nameValuePairs.add(new BasicNameValuePair("key", Key));
            nameValuePairs.add(new BasicNameValuePair("serviceId", String.valueOf(ServiceId)));
            nameValuePairs.add(new BasicNameValuePair("consumerNo", CusAcc));
            nameValuePairs.add(new BasicNameValuePair("buCode", BuCode));
            jsonResponse = jsonParser.makeHttpPostRequestforJsonObject(API.DHS_GET_MSEB_CUS_DETAILS, nameValuePairs);
            if (jsonResponse == null) {
                return null;
            } else {
                try {
                    msebResponse = new MsebResponse(jsonResponse.getInt("billAmount"),
                            jsonResponse.getString("dueDate"),
                            jsonResponse.getInt("consumptionUnits"), jsonResponse.getString("billMonth"));
                    BillAmount = msebResponse.getBillAmount();
                    DueDate = msebResponse.getDueDate();
                    ConsumptionUnits = msebResponse.getConsumptionUnits();
                    BillMonth = msebResponse.getBillMonth();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return DueDate;
            }
        }

        @Override
        protected void onPostExecute(String dueDate) {
            dialog.dismiss();
            if (dueDate == null) {
                showAlertDialog();
            } else {
                llerrorDueDate.setVisibility(View.VISIBLE);
                if (Check.ifTodayLessThanDue(dueDate)) {
                    llerrorDueDate.setBackgroundResource(R.drawable.rounded_green_layout);
                    bGetDetails.setVisibility(View.GONE);
                    tvnotexceeded.setVisibility(View.GONE);
                    bpaybill.setVisibility(View.VISIBLE);
                    tvbillAmount.setText("Bill Amount:" + BillAmount);
                    tvDueDate.setText("Due Date:" + DueDate);
                    tvbillMonth.setText("Bill Month: " + BillMonth);
                    tvConsunit.setText("Consumption Unit: " + ConsumptionUnits);
                    lleleccustno.setVisibility(View.VISIBLE);

                } else {
                    llerrorDueDate.setBackgroundResource(R.drawable.rounded_red_layout);
                    tvbillAmount.setText("Due Date Exceeded");
                    tvDueDate.setText("Your Due Date was on: " + dueDate);
                }
            }
        }
    }

    private class retrieveDataforPayBill extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(MSEB.this);

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
            msebPayBillparams = new MsebPayBillparams(BillAmount, Bu, CusAcc, CusMob, DueDate, Key, ServiceId, UserId);
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("amount", String.valueOf(BillAmount)));
            nameValuePairs.add(new BasicNameValuePair("bU", Bu));
            nameValuePairs.add(new BasicNameValuePair("cusAcc", CusAcc));
            nameValuePairs.add(new BasicNameValuePair("cusMob", CusMob));
            nameValuePairs.add(new BasicNameValuePair("dueDate", DueDate));
            nameValuePairs.add(new BasicNameValuePair("key", Key));
            nameValuePairs.add(new BasicNameValuePair("serviceId", String.valueOf(ServiceId)));
            nameValuePairs.add(new BasicNameValuePair("userId", UserId));

            jsonResponse = jsonParser.makeHttpPostRequestforJsonObject(API.DASHBOARD_ELECTRICITY, nameValuePairs);
            if (jsonResponse == null) {
                return null;
            } else {
                try {
                    msebPayBllResponse = new MsebPayBllResponse(jsonResponse.getInt("avaiBal"), jsonResponse.getInt("status"));
                    Status = msebPayBllResponse.getStatus();
                    AvailBal = msebPayBllResponse.getAvaiBal();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return String.valueOf(Status);
            }
        }

        @Override
        protected void onPostExecute(String status) {
            dialog.dismiss();
            if (status == null) {
                showAlertDialog();
            } else if (status.equals("0") || status.equals("-1")) {
                new AlertDialog.Builder(MSEB.this)
                        .setTitle("Error").setIcon(getResources().getDrawable(R.drawable.erroricon))
                        .setMessage("Request Not Completed.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            } else if (status.equals("1")) {
                new AlertDialog.Builder(MSEB.this)
                        .setTitle("Success").setIcon(getResources().getDrawable(R.drawable.successicon))
                        .setMessage("Request Completed." +
                                "\n\nAvailable Balance: " + AvailBal)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                globalVariable.setAvailableBalance(String.valueOf(AvailBal));
            } else if (status.equals("2")) {
                new AlertDialog.Builder(MSEB.this)
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

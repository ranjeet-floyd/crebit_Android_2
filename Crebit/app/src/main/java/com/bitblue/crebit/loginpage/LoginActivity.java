package com.bitblue.crebit.loginpage;

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
import android.widget.Button;
import android.widget.EditText;

import com.bitblue.Applicaton.GlobalVariable;
import com.bitblue.apinames.API;
import com.bitblue.crebit.R;
import com.bitblue.crebit.servicespage.service;
import com.bitblue.jsonparse.JSONParser;
import com.bitblue.network.NetworkUtil;
import com.bitblue.nullcheck.Check;
import com.bitblue.requestparam.LoginParams;
import com.bitblue.response.LoginResponse;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener {
    private JSONParser jsonParser = new JSONParser();
    private JSONArray jsonArray = null;
    private EditText mNumber, passwd;
    private Button login, forgotPass, signUp;
    private String logoutmessage;
    private String mobile, pass;
    private String Version = "1.0";
    private String userName, availableBalance, userId, userKey, uType;
    private boolean isActive;
    private Tracker tracker;
    private LoginParams loginParams;
    private JSONObject JsonResponse;
    private LoginResponse loginResponse;
    private List<NameValuePair> nameValuePairs;
    private final static String MY_PREFS = "mySharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tracker = ((GlobalVariable) getApplication()).getTracker(GlobalVariable.TrackerName.APP_TRACKER);
        tracker.setScreenName("Login Page");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        setContentView(R.layout.activity_login);
        getSupportActionBar().setIcon(R.drawable.crebit_icon);
        logoutmessage = getIntent().getStringExtra("logout");
        initViews();
        if (!isNetworkAvailable())
            showAlertDialog();
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
    protected void onRestart() {
        super.onRestart();
        if (!isNetworkAvailable())
            showAlertDialog();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void initViews() {
        //existinguser = (TextView) findViewById(R.id.existingUser);
        mNumber = (EditText) findViewById(R.id.et_mobileNumber);
        mNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.edittext_focus);
                } else {
                    view.setBackgroundResource(R.drawable.edittext_lostfocus);
                }
            }
        });
        passwd = (EditText) findViewById(R.id.et_password);
        passwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {

                    view.setBackgroundResource(R.drawable.edittext_focus);
                } else {
                    view.setBackgroundResource(R.drawable.edittext_lostfocus);
                }
            }
        });
        login = (Button) findViewById(R.id.b_login);
        forgotPass = (Button) findViewById(R.id.b_forgot_pass);
        signUp = (Button) findViewById(R.id.b_signUp);
        login.setOnClickListener(this);
        forgotPass.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.b_login:

                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Button")
                        .setAction("Clicked on Login Button on Login Page")
                        .setLabel("Login Button")
                        .build());

                mobile = mNumber.getText().toString();
                pass = passwd.getText().toString();
                if (Check.ifNumberInCorrect(mobile)) {
                    mNumber.setHint(" Mobile Number Required");
                    mNumber.setHintTextColor(getResources().getColor(R.color.red));
                    break;
                }
                if (Check.ifNull(pass)) {
                    passwd.setHint(" Password Required");
                    passwd.setHintTextColor(getResources().getColor(R.color.red));
                    break;
                }
                new retrieveData().execute();
                break;
            case R.id.b_forgot_pass:
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Button")
                        .setAction("Clicked on Forgot Pass Button on login Page")
                        .setLabel("Forgot Password Button")
                        .build());
                Intent openForgotPass = new Intent(LoginActivity.this, ForgotPass.class);
                startActivity(openForgotPass);
                break;
            case R.id.b_signUp:
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Button")
                        .setAction("Clicked on Signup Button on login Page")
                        .setLabel("SignUp Button")
                        .build());
                Intent opensignUp = new Intent(LoginActivity.this, SignUp.class);
                startActivity(opensignUp);
                break;
        }
    }

    private class retrieveData extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Please Wait");
            dialog.setMessage("Signing in...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            loginParams = new LoginParams(mobile, pass, Version);
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("Mobile", mobile));
            nameValuePairs.add(new BasicNameValuePair("Pass", pass));
            nameValuePairs.add(new BasicNameValuePair("Version", Version));
            jsonArray = jsonParser.makeHttpPostRequest(API.DHS_LOGIN, nameValuePairs);
            if (jsonArray == null) {
                return null;
            } else {
                try {
                    JsonResponse = jsonArray.getJSONObject(0);
                    loginResponse = new LoginResponse(JsonResponse.getBoolean("isSupported"),
                            JsonResponse.getBoolean("isActive"),
                            JsonResponse.getString("userId"),
                            JsonResponse.getString("availableBalance"),
                            JsonResponse.getBoolean("isUpdated"),
                            JsonResponse.getBoolean("isDataUpdated"),
                            JsonResponse.getString("name"),
                            JsonResponse.getString("userKey"),
                            JsonResponse.getString("uType"));
                    userName = loginResponse.getName();
                    availableBalance = loginResponse.getAvailableBalance();
                    userId = loginResponse.getUserID();
                    userKey = loginResponse.getUserKey();
                    isActive = loginResponse.isActive();
                    uType = loginResponse.getuType();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return userName;
            }
        }

        @Override
        protected void onPostExecute(String name) {
            dialog.dismiss();
            if (name == null) {
                showAlertDialog();
            } else if (name.equals("null")) {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Error").setIcon(getResources().getDrawable(R.drawable.erroricon))
                        .setMessage("Invalid Credentials")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            } else {
                SharedPreferences.Editor prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();  //to pass data between activities
                prefs.putString("userId", userId);
                prefs.putString("userKey", userKey);
                prefs.putString("availableBalance", availableBalance);
                prefs.putString("userName", userName);
                prefs.putBoolean("isActive", isActive);
                prefs.putString("uType", uType);
                prefs.commit();
                //  globalVariable.setAvailableBalance(availableBalance);
                clearField(mNumber);
                clearField(passwd);
                Intent openService = new Intent(LoginActivity.this, service.class);
                startActivity(openService);
            }
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("\tUnable to connect to Internet." +
                "\n \tCheck Your Network Connection.")
                .setCancelable(false)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
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

    public static class NetworkChangeReceiver extends BroadcastReceiver {
        public NetworkChangeReceiver() {
        }

        @Override
        public void onReceive(final Context context, final Intent intent) {
            String status = NetworkUtil.getConnectivityStatusString(context);
        }
    }

    private void clearField(EditText et) {
        et.setText("");
    }
}

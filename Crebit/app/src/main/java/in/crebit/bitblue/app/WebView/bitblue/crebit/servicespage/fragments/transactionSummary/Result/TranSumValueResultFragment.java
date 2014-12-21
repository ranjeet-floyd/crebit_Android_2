package in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.fragments.transactionSummary.Result;

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

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.crebit.bitblue.app.WebView.bitblue.Applicaton.GlobalVariable;
import in.crebit.bitblue.app.WebView.bitblue.R;
import in.crebit.bitblue.app.WebView.bitblue.apinames.API;
import in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.fragments.transactionSummary.TransSumResult;
import in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.fragments.transactionSummary.adapter.TransSumCustomAdapter;
import in.crebit.bitblue.app.WebView.bitblue.jsonparse.JSONParser;
import in.crebit.bitblue.app.WebView.bitblue.network.NetworkUtil;
import in.crebit.bitblue.app.WebView.bitblue.requestparam.TranSumParams;
import in.crebit.bitblue.app.WebView.bitblue.response.TranSumResponse;

public class TranSumValueResultFragment extends Fragment {

    private TextView tvtotalprofit, tvtotalamount, tvnodata;
    private String Value;
    private JSONParser jsonParser;
    private JSONObject jsonResponse, tranValueResArrObject;
    private JSONArray tranValueResArr;
    private ListView resultList;
    private TranSumParams tranSumValueParams;
    private TranSumResponse tranSumValueResponse;
    private TransSumResult transSumValueResult;
    private ArrayList<NameValuePair> nameValuePairs;
    private ArrayList<TransSumResult> transSumValueResultList = new ArrayList<TransSumResult>();
    private SharedPreferences prefs;
    private final static String MY_PREFS = "mySharedPrefs";
    private String UserId, Key;
    private double TotalAmount, TotalProfit;
    private Tracker tracker;

    public TranSumValueResultFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tran_sum_value_result, container, false);
        prefs = getActivity().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        UserId = prefs.getString("userId", "");
        Key = prefs.getString("userKey", "");
        Value = getArguments().getString("Value");
        tracker = ((GlobalVariable) getActivity().getApplication()).getTracker(GlobalVariable.TrackerName.APP_TRACKER);
        tracker.setScreenName("Transaction Summary srchbyvalue Result Page");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
        initViews(view);
        new retrieveTransactionData().execute();
        resultList = (ListView) view.findViewById(R.id.lv_transumvalue_result);

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

    private void initViews(View view) {
        tvtotalprofit = (TextView) view.findViewById(R.id.tvtransumValueprofit);
        tvtotalamount = (TextView) view.findViewById(R.id.tvtransumValueamount);
        tvnodata = (TextView) view.findViewById(R.id.tv_tranSumValue_list_nodata);

    }

    private class retrieveTransactionData extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(getActivity());

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
            tranSumValueParams = new TranSumParams(UserId, Key, Value);
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("UserId", UserId));
            nameValuePairs.add(new BasicNameValuePair("Key", Key));
            nameValuePairs.add(new BasicNameValuePair("Value", Value));
            jsonResponse = jsonParser.makeHttpPostRequestforJsonObject(API.DASHBOARD_TRANSEARCH, nameValuePairs);
            if (jsonResponse == null) {
                return null;
            } else {
                try {
                    tranSumValueResponse = new TranSumResponse(jsonResponse.getDouble("totalAmount"),
                            jsonResponse.getDouble("totalProfit"), jsonResponse.getJSONArray("dL_TransactionReturns"));

                    TotalAmount = tranSumValueResponse.getTotalAmount();
                    TotalProfit = tranSumValueResponse.getTotalProfit();
                    tranValueResArr = tranSumValueResponse.getTranSumResults();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return String.valueOf(TotalAmount);
            }
        }

        @Override
        protected void onPostExecute(String status) {
            dialog.dismiss();
            if (status == null) {
                showAlertDialog();
            } else {
                tvnodata.setVisibility(View.GONE);
                resultList.setVisibility(View.VISIBLE);
                tvtotalamount.setText("Total Amount Rs: " + String.valueOf(TotalAmount));
                tvtotalprofit.setText("Total Profit Rs: " + String.valueOf(TotalProfit));

                if (tranValueResArr.length() == 0) {
                    tvnodata.setVisibility(View.VISIBLE);
                    resultList.setVisibility(View.GONE);
                }
                for (int i = 0; i < tranValueResArr.length(); i++) {
                    try {
                        tranValueResArrObject = (JSONObject) tranValueResArr.get(i);
                        transSumValueResult = new TransSumResult();
                        transSumValueResult.setCount(i + 1);
                        transSumValueResult.setId(tranValueResArrObject.getString("id"));
                        transSumValueResult.setcBalance(tranValueResArrObject.getString("cBalance"));
                        transSumValueResult.setAmount(tranValueResArrObject.getString("amount"));
                        transSumValueResult.setProfit(tranValueResArrObject.getString("profit"));
                        transSumValueResult.setSource(tranValueResArrObject.getString("source"));
                        transSumValueResult.settDate(tranValueResArrObject.getString("tDate"));
                        transSumValueResult.setStatus(tranValueResArrObject.getString("status"));
                        transSumValueResult.setOperaterName(tranValueResArrObject.getString("operaterName"));
                        transSumValueResult.setOperaterId(tranValueResArrObject.getInt("operaterId"));
                        transSumValueResult.setOpType(tranValueResArrObject.getInt("OpType"));
                        transSumValueResult.setCharge(tranValueResArrObject.getString("charge"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    transSumValueResultList.add(transSumValueResult);
                }
                resultList.setAdapter(new TransSumCustomAdapter(getActivity(), transSumValueResultList));
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

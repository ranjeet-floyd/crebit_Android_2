package com.bitblue.crebit.servicespage.fragments.transactionSummary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.bitblue.requestparam.TranSumParams;
import com.bitblue.response.TranSumResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransSumResultFragment extends Fragment {
    private TextView tvtotalprofit, tvprofit, tvamount, tvtotalamount;

    private JSONParser jsonParser;
    private JSONObject jsonResponse, tranResArrObject;
    private JSONArray tranResArr;
    private ListView resultList;
    private TranSumParams tranSumParams;
    private TranSumResponse tranSumResponse;
    private TransSumResult transSumResult;
    private ArrayList<NameValuePair> nameValuePairs;
    private ArrayList<TransSumResult> transSumResultList = new ArrayList<TransSumResult>();

    private String UserId, Key, fromDate, toDate;
    private int StatusId, TypeId;

    private SharedPreferences prefs;
    private final static String MY_PREFS = "mySharedPrefs";
    private double TotalAmount, TotalProfit;

    public TransSumResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trans_sum_result, container, false);
        prefs = getActivity().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        fromDate = getArguments().getString("fromDate");
        toDate = getArguments().getString("toDate");
        StatusId = getArguments().getInt("StatusId");
        TypeId = getArguments().getInt("TypeId");
        UserId = prefs.getString("userId", "");
        Key = prefs.getString("userKey", "");
        initViews(view);
        resultList = (ListView) view.findViewById(R.id.lv_transum_result);
        new retrieveTransactionData().execute();
        return view;
    }

    private void initViews(View view) {
        tvtotalprofit = (TextView) view.findViewById(R.id.tvtotalprofit);
        tvtotalamount = (TextView) view.findViewById(R.id.tvtotalamount);
        tvprofit = (TextView) view.findViewById(R.id.tvprofit);
        tvamount = (TextView) view.findViewById(R.id.tvamount);
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
            tranSumParams = new TranSumParams(UserId, Key, fromDate, toDate, StatusId, TypeId);
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("UserId", UserId));
            nameValuePairs.add(new BasicNameValuePair("Key", Key));
            nameValuePairs.add(new BasicNameValuePair("FromDate", fromDate));
            nameValuePairs.add(new BasicNameValuePair("ToDate", toDate));
            nameValuePairs.add(new BasicNameValuePair("StatusId", String.valueOf(StatusId)));
            nameValuePairs.add(new BasicNameValuePair("TypeId", String.valueOf(TypeId)));
            jsonResponse = jsonParser.makeHttpPostRequestforJsonObject(API.DASHBOARD_TRANSACTION_DETAILS, nameValuePairs);
            try {
                tranSumResponse = new TranSumResponse(jsonResponse.getDouble("totalAmount"),
                        jsonResponse.getDouble("totalProfit"), jsonResponse.getJSONArray("dL_TransactionReturns"));

                TotalAmount = tranSumResponse.getTotalAmount();
                TotalProfit = tranSumResponse.getTotalProfit();
                tranResArr = tranSumResponse.getTranSumResults();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String status) {
            dialog.dismiss();
            tvamount.setText(String.valueOf(TotalAmount));
            tvprofit.setText(String.valueOf(TotalProfit));

            for (int i = 0; i < tranResArr.length(); i++) {
                try {
                    tranResArrObject = (JSONObject) tranResArr.get(i);
                    transSumResult = new TransSumResult();
                    transSumResult.setId(tranResArrObject.getString("id"));
                    transSumResult.setcBalance(tranResArrObject.getString("cBalance"));
                    transSumResult.setAmount(tranResArrObject.getString("amount"));
                    transSumResult.setProfit(tranResArrObject.getString("profit"));
                    transSumResult.setSource(tranResArrObject.getString("source"));
                    transSumResult.settDate(tranResArrObject.getString("tDate"));
                    transSumResult.setStatus(tranResArrObject.getString("status"));
                    transSumResult.setOperaterName(tranResArrObject.getString("operaterName"));
                    transSumResult.setOperaterId(tranResArrObject.getInt("operaterId"));
                    transSumResult.setOpType(tranResArrObject.getInt("OpType"));
                    transSumResult.setCharge(tranResArrObject.getString("charge"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                transSumResultList.add(transSumResult);
            }
            resultList.setAdapter(new TransSumCustomAdapter(getActivity(), transSumResultList));
        }
    }
}

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

public class TranSumValueResultFragment extends Fragment {

    private TextView tvtotalprofit, tvprofit, tvamount, tvtotalamount;

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
        initViews(view);
        new retrieveTransactionData().execute();
        resultList = (ListView) view.findViewById(R.id.lv_transumvalue_result);

        return view;
    }

    private void initViews(View view) {
        tvtotalprofit = (TextView) view.findViewById(R.id.tvtotalprofit);
        tvtotalamount = (TextView) view.findViewById(R.id.tvtotalamount);
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
            try {
                tranSumValueResponse = new TranSumResponse(jsonResponse.getDouble("totalAmount"),
                        jsonResponse.getDouble("totalProfit"), jsonResponse.getJSONArray("dL_TransactionReturns"));

                TotalAmount = tranSumValueResponse.getTotalAmount();
                TotalProfit = tranSumValueResponse.getTotalProfit();
                tranValueResArr = tranSumValueResponse.getTranSumResults();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String status) {
            dialog.dismiss();

            for (int i = 0; i < tranValueResArr.length(); i++) {
                try {
                    tranValueResArrObject = (JSONObject) tranValueResArr.get(i);
                    transSumValueResult = new TransSumResult();
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

package com.bitblue.crebit.servicespage.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bitblue.crebit.R;
import com.bitblue.nullcheck.Check;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransSummary extends Fragment implements View.OnClickListener {
    private TextView tvfromto, tvstatus, tvtype;
    private Button from_Date, to_Date, status, type, search, mobsearch;
    private EditText mobNum;

    private String fromDate, toDate, stat, typ, mobileNumber;
    private int cur;
    private static final int FROM_DATE = 1;
    private static final int TO_DATE = 2;
    private String UserId, Key, FromDate, ToDate;
    private String CBalance, profit, Amount, Source, TDate, Status, OperaterName;

    private String[] statlist;
    private ArrayAdapter<String> statusAdapter;
    private String[] typelist;
    private ArrayAdapter<String> typeAdapter;

    private SharedPreferences prefs;
    private final static String MY_PREFS = "mySharedPrefs";

    public TransSummary() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trans_summary, container,
                false);
        statlist = getResources().getStringArray(R.array.status);
        typelist = getResources().getStringArray(R.array.type);

        initViews(view);
        return view;
    }

    public void initViews(View view) {

        tvfromto = (TextView) view.findViewById(R.id.tv_ts_fromto);
        tvstatus = (TextView) view.findViewById(R.id.tv_ts_status);
        tvtype = (TextView) view.findViewById(R.id.tv_ts_type);
        from_Date = (Button) view.findViewById(R.id.b_ts_from);
        to_Date = (Button) view.findViewById(R.id.b_ts_to);
        status = (Button) view.findViewById(R.id.b_ts_status);
        type = (Button) view.findViewById(R.id.b_ts_type);
        search = (Button) view.findViewById(R.id.b_ts_search);
        mobsearch = (Button) view.findViewById(R.id.b_ts_srch_mobnum);
        mobNum = (EditText) view.findViewById(R.id.et_ts_mobnum);

        from_Date.setOnClickListener(this);
        to_Date.setOnClickListener(this);
        status.setOnClickListener(this);
        type.setOnClickListener(this);

        search.setOnClickListener(this);
        mobsearch.setOnClickListener(this);
        statusAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, statlist);
        typeAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, typelist);

        prefs = this.getActivity().getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        UserId = prefs.getString("userId", "");
        Key = prefs.getString("userKey", "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_ts_from:
                cur = FROM_DATE;
                showDatePicker();
                break;
            case R.id.b_ts_to:
                cur = TO_DATE;
                showDatePicker();
                break;

            case R.id.b_ts_status:
                new AlertDialog.Builder(getActivity())
                        .setTitle("Select Status")
                        .setAdapter(statusAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position) {
                                status.setText(statlist[position]);
                                stat = status.getText().toString();
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.b_ts_type:
                new AlertDialog.Builder(getActivity())
                        .setTitle("Select Type")
                        .setAdapter(typeAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position) {
                                type.setText(typelist[position]);
                                typ = type.getText().toString();
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.b_ts_search:
                stat = status.getText().toString();
                typ = type.getText().toString();
                if (Check.ifNull(stat)) {
                    Toast.makeText(getActivity(), "Status cannot be null", Toast.LENGTH_LONG).show();
                    break;
                }
                if (Check.ifNull(typ)) {
                    Toast.makeText(getActivity(), "Type cannot be null", Toast.LENGTH_LONG).show();
                    break;
                }

            case R.id.b_ts_srch_mobnum:
                mobileNumber = mobNum.getText().toString();
                break;
        }

    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Date from = new Date();
            Date to = new Date();
            if (cur == FROM_DATE) {
                from_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                fromDate = from_Date.getText().toString();

            }
            if (cur == TO_DATE) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
                try {
                    from = sdf.parse(from_Date.getText().toString());
                    to = sdf.parse(to_Date.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(Check.ifNull(fromDate)){Toast.makeText(getActivity(), "FromDate cannot be null", Toast.LENGTH_LONG).show();}
                if(Check.ifNull(toDate)){Toast.makeText(getActivity(), "ToDate cannot be null", Toast.LENGTH_LONG).show();}
                if (from.before(to)) {
                    to_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    toDate = to_Date.getText().toString();

                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Enter Proper Range")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                }
            }
        }
    };

}


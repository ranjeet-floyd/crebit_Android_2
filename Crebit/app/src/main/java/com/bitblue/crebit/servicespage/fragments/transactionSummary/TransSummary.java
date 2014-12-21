package com.bitblue.crebit.servicespage.fragments.transactionSummary;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.bitblue.Applicaton.GlobalVariable;
import com.bitblue.IDs.status;
import com.bitblue.IDs.type;
import com.bitblue.crebit.R;
import com.bitblue.crebit.servicespage.fragments.DatePickerFragment;
import com.bitblue.crebit.servicespage.fragments.transactionSummary.Result.TranSumValueResultFragment;
import com.bitblue.crebit.servicespage.fragments.transactionSummary.Result.TransSumResultFragment;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransSummary extends Fragment implements View.OnClickListener {
    private TextView tvfromto, tvstatus, tvtype;
    private Button from_Date, to_Date, bstatus, btype, search, valuesearch;
    private EditText etvalue;
    Date from, to;
    private String fromDate, toDate, value;
    private int cur;
    private static final int FROM_DATE = 1;
    private static final int TO_DATE = 2;
    private int StatusId = -1, TypeId = -1;

    private String[] statlist;
    private ArrayAdapter<String> statusAdapter;
    private String[] typelist;
    private ArrayAdapter<String> typeAdapter;
    private Tracker tracker;

    public TransSummary() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trans_summary, container,
                false);
        statlist = getResources().getStringArray(R.array.status);
        typelist = getResources().getStringArray(R.array.type);
        tracker = ((GlobalVariable) getActivity().getApplication()).getTracker(GlobalVariable.TrackerName.APP_TRACKER);
        tracker.setScreenName("Transaction Summary Page");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
        initViews(view);
        return view;
    }

    public void initViews(View view) {

        tvfromto = (TextView) view.findViewById(R.id.tv_ts_fromto);
        tvstatus = (TextView) view.findViewById(R.id.tv_ts_status);
        tvtype = (TextView) view.findViewById(R.id.tv_ts_type);
        from_Date = (Button) view.findViewById(R.id.b_ts_from);
        to_Date = (Button) view.findViewById(R.id.b_ts_to);
        bstatus = (Button) view.findViewById(R.id.b_ts_status);
        btype = (Button) view.findViewById(R.id.b_ts_type);
        search = (Button) view.findViewById(R.id.b_ts_search);
        valuesearch = (Button) view.findViewById(R.id.b_ts_srch_value);
        etvalue = (EditText) view.findViewById(R.id.et_ts_value);

        from_Date.setOnClickListener(this);
        to_Date.setOnClickListener(this);
        bstatus.setOnClickListener(this);
        btype.setOnClickListener(this);

        search.setOnClickListener(this);
        valuesearch.setOnClickListener(this);
        statusAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, statlist);
        typeAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, typelist);
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

            if (cur == FROM_DATE) {
                from_Date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                fromDate = from_Date.getText().toString();
            }

            if (cur == TO_DATE) {
                to_Date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                toDate = to_Date.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                try {
                    from = sdf.parse(from_Date.getText().toString());
                    to = sdf.parse(to_Date.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_ts_from:

                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Button")
                        .setAction("Clicked on From Button on TranSum Page")
                        .setLabel("From Button")
                        .build());

                cur = FROM_DATE;
                showDatePicker();
                break;
            case R.id.b_ts_to:

                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Button")
                        .setAction("Clicked on To Button on TranSum Page")
                        .setLabel("To Button")
                        .build());

                cur = TO_DATE;
                showDatePicker();
                break;

            case R.id.b_ts_status:

                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Button")
                        .setAction("Clicked on Status Button on TranSum Page")
                        .setLabel("Status Button")
                        .build());

                new AlertDialog.Builder(getActivity())
                        .setTitle("Select Status")
                        .setAdapter(statusAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position) {
                                bstatus.setText(statlist[position]);
                                StatusId = status.getStatusId(position);
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.b_ts_type:

                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Button")
                        .setAction("Clicked on Type Button on TranSum Page")
                        .setLabel("Type Button")
                        .build());

                new AlertDialog.Builder(getActivity())
                        .setTitle("Select Type")
                        .setAdapter(typeAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position) {
                                btype.setText(typelist[position]);
                                TypeId = type.getTypeId(position);
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.b_ts_search:

                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Button")
                        .setAction("Clicked on Search Button on TranSum Page")
                        .setLabel("Search Button")
                        .build());

                if (from_Date.getText().toString().equals("from") || to_Date.getText().toString().equals("to")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error").setIcon(getResources().getDrawable(R.drawable.erroricon))
                            .setMessage("Enter From and To Date")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                    break;
                } else {
                    Bundle args1 = new Bundle();
                    args1.putString("fromDate", fromDate);
                    args1.putString("toDate", toDate);
                    args1.putInt("StatusId", StatusId);
                    args1.putInt("TypeId", TypeId);

                    TransSumResultFragment transSumResultFragment = new TransSumResultFragment();
                    FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                    transSumResultFragment.setArguments(args1);
                    ft1.replace(R.id.transumframe, transSumResultFragment);
                    ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
                break;
            case R.id.b_ts_srch_value:

                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Button")
                        .setAction("Clicked on Searchbyvalue Button on Transum Page")
                        .setLabel("Srchbyvalue Button")
                        .build());

                value = etvalue.getText().toString();
                if (value.equals("")) {
                    etvalue.setText("");
                    etvalue.setHint(" Enter Number");
                    etvalue.setHintTextColor(getResources().getColor(R.color.red));
                    break;
                } else {
                    Bundle args2 = new Bundle();
                    args2.putString("Value", value);
                    TranSumValueResultFragment transSumvalueResultFragment = new TranSumValueResultFragment();
                    FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                    transSumvalueResultFragment.setArguments(args2);
                    ft2.replace(R.id.transumframe, transSumvalueResultFragment);
                    ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft2.addToBackStack(null);
                    ft2.commit();
                }
                break;
        }

    }
}


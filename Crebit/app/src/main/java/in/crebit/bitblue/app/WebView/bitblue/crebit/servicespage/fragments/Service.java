package in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import in.crebit.bitblue.app.WebView.bitblue.Applicaton.GlobalVariable;
import in.crebit.bitblue.app.WebView.bitblue.R;
import in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.activities.BroadBand;
import in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.activities.Datacard;
import in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.activities.Dth;
import in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.activities.Electricity;
import in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.activities.FundTransfer;
import in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.activities.GasBill;
import in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.activities.Insurance;
import in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.activities.PostPaid;
import in.crebit.bitblue.app.WebView.bitblue.crebit.servicespage.activities.PrePaid;

public class Service extends Fragment implements View.OnClickListener {
    ImageView postPaid, prePaid, dataCard, dth, insurance, electricity, gasBill, broadBand, fundTransfer;
    TextView postpaid, prepaid, datacard, Dth, Insurance, Electricity, gasbill, broadband, fundtransfer;
    private Tracker tracker;

    public Service() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_list, container,
                false);
        tracker = ((GlobalVariable) getActivity().getApplication()).getTracker(GlobalVariable.TrackerName.APP_TRACKER);
        tracker.setScreenName("Services  Page");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
        initViews(view);
        return view;
    }

    public void initViews(View view) {
        postPaid = (ImageView) view.findViewById(R.id.ib_postPaid);
        prePaid = (ImageView) view.findViewById(R.id.ib_prePaid);
        dth = (ImageView) view.findViewById(R.id.ib_dth);

        dataCard = (ImageView) view.findViewById(R.id.ib_datacard);
        insurance = (ImageView) view.findViewById(R.id.ib_insurance);
        electricity = (ImageView) view.findViewById(R.id.ib_electricity);

        gasBill = (ImageView) view.findViewById(R.id.ib_gasBill);
        broadBand = (ImageView) view.findViewById(R.id.ib_broadband);
        fundTransfer = (ImageView) view.findViewById(R.id.ib_fundTransfer);

        postpaid = (TextView) view.findViewById(R.id.tv_postPaid);
        prepaid = (TextView) view.findViewById(R.id.tv_prePaid);
        Dth = (TextView) view.findViewById(R.id.tv_dth);

        datacard = (TextView) view.findViewById(R.id.tv_datacard);
        Insurance = (TextView) view.findViewById(R.id.tv_insurance);
        Electricity = (TextView) view.findViewById(R.id.tv_electricity);

        gasbill = (TextView) view.findViewById(R.id.tv_gasBill);
        broadband = (TextView) view.findViewById(R.id.tv_broadband);
        fundtransfer = (TextView) view.findViewById(R.id.tv_fundTransfer);

        postPaid.setOnClickListener(this);
        prePaid.setOnClickListener(this);
        dth.setOnClickListener(this);
        dataCard.setOnClickListener(this);
        insurance.setOnClickListener(this);
        electricity.setOnClickListener(this);
        gasBill.setOnClickListener(this);
        broadBand.setOnClickListener(this);
        fundTransfer.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_postPaid:

                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Services Page items")
                        .setAction("Clicked on postpaid item on services Page")
                        .setLabel("postpaid image")
                        .build());

                Intent openPostPaidActivity = new Intent(getActivity(), PostPaid.class);
                startActivity(openPostPaidActivity);
                break;
            case R.id.ib_prePaid:

                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Services Page items")
                        .setAction("Clicked on prepaid item on services Page")
                        .setLabel("prepaid image")
                        .build());

                Intent openPrePaidActivity = new Intent(getActivity(), PrePaid.class);
                startActivity(openPrePaidActivity);
                break;
            case R.id.ib_dth:

                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Services Page items")
                        .setAction("Clicked on dth item on services Page")
                        .setLabel("dth image")
                        .build());

                Intent openDthActivity = new Intent(getActivity(), Dth.class);
                startActivity(openDthActivity);
                break;
            case R.id.ib_datacard:


                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Services Page items")
                        .setAction("Clicked on datacard item on services Page")
                        .setLabel("datacard image")
                        .build());

                Intent opendataCardActivity = new Intent(getActivity(), Datacard.class);
                startActivity(opendataCardActivity);
                break;
            case R.id.ib_insurance:


                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Services Page items")
                        .setAction("Clicked on insurance item on services Page")
                        .setLabel("insurance image")
                        .build());

                Intent openinsuranceActivity = new Intent(getActivity(), Insurance.class);
                startActivity(openinsuranceActivity);
                break;
            case R.id.ib_electricity:


                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Services Page items")
                        .setAction("Clicked on electricity item on services Page")
                        .setLabel("electricity image")
                        .build());

                Intent openElectricityActivity = new Intent(getActivity(), Electricity.class);
                startActivity(openElectricityActivity);
                break;
            case R.id.ib_gasBill:


                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Services Page items")
                        .setAction("Clicked on gasbill item on services Page")
                        .setLabel("gasbill image")
                        .build());

                Intent opengasBillActivity = new Intent(getActivity(), GasBill.class);
                startActivity(opengasBillActivity);
                break;
            case R.id.ib_broadband:


                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Services Page items")
                        .setAction("Clicked on broadband item on services Page")
                        .setLabel("broadband image")
                        .build());

                Intent openBroadBandActivity = new Intent(getActivity(), BroadBand.class);
                startActivity(openBroadBandActivity);
                break;
            case R.id.ib_fundTransfer:


                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Services Page items")
                        .setAction("Clicked on fundtransfer item on services Page")
                        .setLabel("fundtransfer image")
                        .build());

                Intent openFundTransferActivity = new Intent(getActivity(), FundTransfer.class);
                startActivity(openFundTransferActivity);
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}

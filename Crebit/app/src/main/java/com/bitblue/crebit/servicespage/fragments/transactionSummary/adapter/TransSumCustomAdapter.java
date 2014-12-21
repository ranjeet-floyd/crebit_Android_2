package com.bitblue.crebit.servicespage.fragments.transactionSummary.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bitblue.crebit.R;
import com.bitblue.crebit.servicespage.fragments.transactionSummary.TransSumResult;
import com.bitblue.crebit.servicespage.fragments.transactionSummary.checkStatus.CheckStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransSumCustomAdapter extends BaseAdapter {
    private static ArrayList<TransSumResult> tranSumResultArrayList;
    private LayoutInflater mInflater;
    private Context context;
    private ViewHolder holder;

    public TransSumCustomAdapter() {
    }

    public TransSumCustomAdapter(Context context, ArrayList<TransSumResult> transSumResultList) {
        tranSumResultArrayList = transSumResultList;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tranSumResultArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return tranSumResultArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.transaction_summary_list_row, null);
            initViews(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setTexxt(position);
        if (tranSumResultArrayList.get(position).getStatus().equals("Success")) {
            Log.e("Conditionforsuccess", String.valueOf(tranSumResultArrayList.get(position).getStatus().equals("Success")));
            holder.checkStatus.setVisibility(View.VISIBLE);
        } else {
            holder.checkStatus.setVisibility(View.GONE);
        }
        holder.checkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCheckStatus = new Intent(context, CheckStatus.class);
                openCheckStatus.putExtra("Status", tranSumResultArrayList.get(position).getStatus());
                openCheckStatus.putExtra("OperatorName", tranSumResultArrayList.get(position).getOperaterName());
                openCheckStatus.putExtra("TransId", tranSumResultArrayList.get(position).getId());
                Log.e("opname", tranSumResultArrayList.get(position).getOperaterName());
                context.startActivity(openCheckStatus);
            }
        });
        notifyDataSetChanged();
        return convertView;
    }

    private void initViews(View convertView) {
        holder = new ViewHolder();
        holder.count = (TextView) convertView.findViewById(R.id.tv_transum_list_count);
        holder.id = (TextView) convertView.findViewById(R.id.tv_transum_list_id);
        holder.cBalance = (TextView) convertView.findViewById(R.id.tv_transum_list_cBalance);
        holder.profit = (TextView) convertView.findViewById(R.id.tv_tranSum_list_profit);
        holder.amount = (TextView) convertView.findViewById(R.id.tv_tranSum_list_amount);
        holder.source = (TextView) convertView.findViewById(R.id.tv_tranSum_list_source);
        holder.tDate = (TextView) convertView.findViewById(R.id.tv_tranSum_list_tDate);
        holder.status = (TextView) convertView.findViewById(R.id.tv_tranSum_list_status);
        holder.operatorName = (TextView) convertView.findViewById(R.id.tv_tranSum_list_operatorName);
        holder.operatorId = (TextView) convertView.findViewById(R.id.tv_tranSum_list_operatorId);
        holder.Optype = (TextView) convertView.findViewById(R.id.tv_tranSum_list_Optype);
        holder.charge = (TextView) convertView.findViewById(R.id.tv_tranSum_list_charge);
        holder.checkStatus = (Button) convertView.findViewById(R.id.b_transum_checkstatus);
    }

    private void setTexxt(int position) {
        holder.count.setText(String.valueOf(tranSumResultArrayList.get(position).getCount()));
        holder.id.setText(tranSumResultArrayList.get(position).getId());
        holder.cBalance.setText(tranSumResultArrayList.get(position).getcBalance());
        holder.profit.setText(tranSumResultArrayList.get(position).getProfit());
        holder.amount.setText(tranSumResultArrayList.get(position).getAmount());
        holder.source.setText(String.valueOf(tranSumResultArrayList.get(position).getSource()));

        String date = DateAndTimeFormat(position);

        holder.tDate.setText(date);
        holder.status.setText(tranSumResultArrayList.get(position).getStatus());
        holder.operatorName.setText(tranSumResultArrayList.get(position).getOperaterName());
        holder.operatorId.setText(String.valueOf(tranSumResultArrayList.get(position).getOperaterId()));
        holder.Optype.setText(String.valueOf(tranSumResultArrayList.get(position).getOpType()));
        holder.charge.setText(tranSumResultArrayList.get(position).getCharge());

    }

    static class ViewHolder {
        TextView count, id, cBalance, profit, amount, source, tDate, status, operatorName, operatorId, Optype, charge;
        public Button checkStatus;
    }

    private static String DateAndTimeFormat(int position) {
        String tDate;
        Date date = null;
        tDate = tranSumResultArrayList.get(position).gettDate();
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = input.parse(tDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);
        Log.e("Formatted time", formattedTime);
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formattedTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String format = new SimpleDateFormat("dd-MMM-yyyy hh:mm a").format(date);
        return format;
    }

}

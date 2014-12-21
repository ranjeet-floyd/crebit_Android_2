package com.bitblue.crebit.servicespage.fragments.transactionSummary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bitblue.crebit.R;

import java.util.ArrayList;

public class TransSumCustomAdapter extends BaseAdapter {
    private static ArrayList<TransSumResult> tranSumResultArrayList;
    private LayoutInflater mInflater;
    private Button checkStatus;

    public TransSumCustomAdapter(Context context, ArrayList<TransSumResult> transSumResultList) {
        tranSumResultArrayList = transSumResultList;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        checkStatus = (Button) convertView.findViewById(R.id.b_transum_checkstatus);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.transaction_summary_list_row, null);
            holder = new ViewHolder();
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.id.setText(tranSumResultArrayList.get(position).getId());
        holder.cBalance.setText(tranSumResultArrayList.get(position).getcBalance());
        holder.profit.setText(tranSumResultArrayList.get(position).getProfit());
        holder.amount.setText(tranSumResultArrayList.get(position).getAmount());
        holder.source.setText(String.valueOf(tranSumResultArrayList.get(position).getSource()));
        holder.tDate.setText(tranSumResultArrayList.get(position).gettDate());
        holder.status.setText(tranSumResultArrayList.get(position).getStatus());
        holder.operatorName.setText(tranSumResultArrayList.get(position).getOperaterName());
        holder.operatorId.setText(String.valueOf(tranSumResultArrayList.get(position).getOperaterId()));
        holder.Optype.setText(String.valueOf(tranSumResultArrayList.get(position).getOpType()));
        holder.charge.setText(tranSumResultArrayList.get(position).getCharge());
        return convertView;
    }

    static class ViewHolder {
        TextView id, cBalance, profit, amount, source, tDate, status, operatorName, operatorId, Optype, charge;
    }

}

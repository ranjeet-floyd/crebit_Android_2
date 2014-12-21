package com.bitblue.crebit.servicespage.fragments.balanceSummary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bitblue.crebit.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BalSumCustomAdapter extends BaseAdapter {
    private static ArrayList<BalSumResult> balSumResultArrayList;
    private LayoutInflater mInflater;

    public BalSumCustomAdapter(Context context, ArrayList<BalSumResult> balSumResultList) {
        balSumResultArrayList = balSumResultList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return balSumResultArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return balSumResultArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.balance_sumary_list_row, null);
            holder = new ViewHolder();
            holder.count = (TextView) convertView.findViewById(R.id.tv_balsum_list_count);
            holder.name = (TextView) convertView.findViewById(R.id.tv_balsum_list_name);
            holder.amount = (TextView) convertView.findViewById(R.id.tv_balsum_list_amount);
            holder.contact = (TextView) convertView.findViewById(R.id.tv_balsum_list_contact);
            holder.date = (TextView) convertView.findViewById(R.id.tv_balsum_list_date);
            holder.transid = (TextView) convertView.findViewById(R.id.tv_balsum_list_transId);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.count.setText(String.valueOf(balSumResultArrayList.get(position).getCount()));
        holder.name.setText(balSumResultArrayList.get(position).getName());
        holder.amount.setText(balSumResultArrayList.get(position).getAmount());
        holder.contact.setText(balSumResultArrayList.get(position).getContact());
        String date= convertDateAndTimeFormat(position);
        holder.date.setText(date);
        holder.transid.setText(balSumResultArrayList.get(position).getTransactionId());
        return convertView;
    }

    static class ViewHolder {
        TextView count, name, amount, contact, date, transid;
    }
    private String convertDateAndTimeFormat(int position) {
        String tDate;
        Date date = null;
        tDate = balSumResultArrayList.get(position).getDate();
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

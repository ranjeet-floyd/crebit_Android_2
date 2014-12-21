package com.bitblue.crebit.servicespage.listAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bitblue.crebit.R;

import java.util.ArrayList;

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
            holder.name = (TextView) convertView.findViewById(R.id.tv_balsum_list_name);
            holder.amount = (TextView) convertView.findViewById(R.id.tv_balsum_list_amount);
            holder.contact = (TextView) convertView.findViewById(R.id.tv_balsum_list_contact);
            holder.date = (TextView) convertView.findViewById(R.id.tv_balsum_list_date);
            holder.transid = (TextView) convertView.findViewById(R.id.tv_balsum_list_transId);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

      holder.name.setText(balSumResultArrayList.get(position).getName());
        holder.amount.setText(balSumResultArrayList.get(position).getAmount());
        holder.contact.setText(balSumResultArrayList.get(position).getContact());
        holder.date.setText(balSumResultArrayList.get(position).getDate());
        holder.transid.setText(balSumResultArrayList.get(position).getTransactionId());
        return convertView;
    }

    static class ViewHolder {
        TextView name, amount, contact, date, transid;
    }
}

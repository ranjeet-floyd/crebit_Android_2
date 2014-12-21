package com.bitblue.crebit.servicespage.listAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bitblue.crebit.R;
import com.bitblue.response.MarginResult;

import java.util.ArrayList;

public class OpMarCustomAdapter extends BaseAdapter {
    private static ArrayList<MarginResult> OpMarArrayList;
    private LayoutInflater mInflater;

    public OpMarCustomAdapter(Context context, ArrayList<MarginResult> OpMarList) {
        OpMarArrayList = OpMarList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return OpMarArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return OpMarArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.operator_margin_list_row, null);
            holder = new ViewHolder();
            holder.count = (TextView) convertView.findViewById(R.id.tv_margin_list_count);
            holder.operator = (TextView) convertView.findViewById(R.id.tv_margin_list_operator);
            holder.op_type = (TextView) convertView.findViewById(R.id.tv_margin_list_type);
            holder.margin = (TextView) convertView.findViewById(R.id.tv_margin_list_margin);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.count.setText(OpMarArrayList.get(position).getCount());
        holder.operator.setText(OpMarArrayList.get(position).getName());
        holder.op_type.setText(OpMarArrayList.get(position).getType());
        holder.margin.setText(OpMarArrayList.get(position).getMargin());
        return convertView;
    }

    static class ViewHolder {
        TextView count, operator, op_type, margin;
    }
}

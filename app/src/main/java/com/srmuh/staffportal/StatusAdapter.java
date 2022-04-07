package com.srmuh.staffportal;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StatusAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> statuses;
    LayoutInflater inflater;

    public StatusAdapter(Context context,
                         int textViewResourceId, ArrayList<String> statuses) {
        super(context, textViewResourceId, statuses);
        this.context = context;
        this.statuses = statuses;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.dropdownlistitem, parent, false);
        TextView label = (TextView) row.findViewById(R.id.text1);
            label.setText(statuses.size()+ statuses.get(position));
        return row;
    }
}
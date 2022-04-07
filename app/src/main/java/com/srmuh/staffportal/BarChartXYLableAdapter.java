package com.srmuh.staffportal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BarChartXYLableAdapter extends RecyclerView.Adapter<BarChartXYLableAdapter.ViewHolder> {
    private static ArrayList<String> leavestatus_list=new ArrayList<String>();
    private int itemLayout;

    public BarChartXYLableAdapter(ArrayList<String> leavestatus_list, int itemLayout) {
        this.leavestatus_list = leavestatus_list;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = leavestatus_list.get(position);
        String[] strColumns = item.split("##");

            holder.xlable.setText(strColumns[0]);
            holder.vColor.setBackgroundColor(Integer.parseInt(strColumns[1]));
      //  holder.textAvailability.setText(strColumns[1]);
        //    holder.textEligibility.setText(strColumns[2]);


    }

    @Override
    public int getItemCount() {
        return leavestatus_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView xlable;
        View vColor;

        public ViewHolder(View itemView){
            super(itemView);
            xlable = (TextView) itemView.findViewById(R.id.xlable);
            vColor = (View) itemView.findViewById(R.id.vColor);

        }
    }
}

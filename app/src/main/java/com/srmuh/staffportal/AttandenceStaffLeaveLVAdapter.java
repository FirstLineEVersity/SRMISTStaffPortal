package com.srmuh.staffportal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AttandenceStaffLeaveLVAdapter extends RecyclerView.Adapter<AttandenceStaffLeaveLVAdapter.ViewHolder> {
    private static ArrayList<String> leavestatus_list = new ArrayList<String>();
    private int itemLayout;

    public AttandenceStaffLeaveLVAdapter(ArrayList<String> leavestatus_list, int itemLayout) {
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

        holder.txtLeaveCount.setText(strColumns[0]);
        holder.txtODCount.setText(strColumns[1]);
        holder.textPermissionCount.setText(strColumns[2]);
        holder.txtLateCount.setText(strColumns[3]);
        holder.txtFromDate.setText(strColumns[4]);
        String colorID = strColumns[5];

        holder.trAttend.setBackgroundColor(Integer.parseInt(colorID));


    }
//{"Status":"Success","Message":"","Data":[{"todate":"07-Apr-2022","leavecnt":"1","latecnt":"0","disleavedate":"03-APR-2022","permissioncnt":"0","odcnt":"0","fromdate":"03-Apr-2022"},{"todate":"07-Apr-2022","leavecnt":"2","latecnt":"0","disleavedate":"04-APR-2022","permissioncnt":"1",
// "odcnt":"0","fromdate":"03-Apr-2022"}]}
    @Override
    public int getItemCount() {
        return leavestatus_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textPermissionCount, txtFromDate, txtLeaveCount, txtODCount, txtLateCount;
        TableRow trAttend;

        public ViewHolder(View itemView) {
            super(itemView);
            txtLeaveCount = (TextView) itemView.findViewById(R.id.textLeaveCount);
            txtODCount = (TextView) itemView.findViewById(R.id.textODCount);
            textPermissionCount = (TextView) itemView.findViewById(R.id.textPermissionCount);
            txtLateCount = (TextView) itemView.findViewById(R.id.textLateCount);
            txtFromDate = (TextView) itemView.findViewById(R.id.textFromDate);
            trAttend = (TableRow) itemView.findViewById(R.id.trAttend);

        }
    }
}

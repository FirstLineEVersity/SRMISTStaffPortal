package com.srmuh.staffportal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class FineHistoryLVAdapter extends RecyclerView.Adapter<FineHistoryLVAdapter.ViewHolder> {
    private static ArrayList<String> leavestatus_list=new ArrayList<String>();
    private int itemLayout;

    public FineHistoryLVAdapter(ArrayList<String> leavestatus_list, int itemLayout) {
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

            holder.txtAccessionNumber.setText(strColumns[0]);
            holder.txtTitle.setText(strColumns[1]);
            holder.txtDueDate.setText(strColumns[2]);
            holder.txtReturnDate.setText(strColumns[3]);
            holder.txtStatus.setText(strColumns[4]);
            holder.txtFine.setText(strColumns[5]);

    }

    @Override
    public int getItemCount() {
        return leavestatus_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextInputEditText   txtAccessionNumber,txtTitle,txtDueDate,txtReturnDate,txtStatus,txtFine;

        public ViewHolder(View itemView){
            super(itemView);
            txtAccessionNumber = (TextInputEditText) itemView.findViewById(R.id.txtAccessionNumber);
            txtTitle = (TextInputEditText) itemView.findViewById(R.id.txtTitle);
            txtDueDate = (TextInputEditText) itemView.findViewById(R.id.txtDueDate);
            txtReturnDate = (TextInputEditText) itemView.findViewById(R.id.txtReturnDate);
            txtStatus = (TextInputEditText) itemView.findViewById(R.id.txtStatus);
            txtFine = (TextInputEditText) itemView.findViewById(R.id.txtFine);

        }
    }
}

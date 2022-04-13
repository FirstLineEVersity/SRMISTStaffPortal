package com.srmuh.staffportal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class BooksInHandLVAdapter extends RecyclerView.Adapter<BooksInHandLVAdapter.ViewHolder> {
    private static ArrayList<String> leavestatus_list=new ArrayList<String>();
    private int itemLayout;

    public BooksInHandLVAdapter(ArrayList<String> leavestatus_list, int itemLayout) {
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

            holder.txtIssueDate.setText(strColumns[0]);
            holder.txtAccessionNumber.setText(strColumns[1]);
            holder.txtTitle.setText(strColumns[2]);
            holder.txtDueDate.setText(strColumns[3]);
            holder.txtDueDays.setText(strColumns[4]);

    }

    @Override
    public int getItemCount() {
        return leavestatus_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextInputEditText  txtIssueDate, txtAccessionNumber,txtTitle,txtDueDate,txtDueDays;

        public ViewHolder(View itemView){
            super(itemView);
            txtIssueDate = (TextInputEditText) itemView.findViewById(R.id.txtIssueDate);
            txtAccessionNumber = (TextInputEditText) itemView.findViewById(R.id.txtAccessionNumber);
            txtTitle = (TextInputEditText) itemView.findViewById(R.id.txtTitle);
            txtDueDate = (TextInputEditText) itemView.findViewById(R.id.txtDueDate);
            txtDueDays = (TextInputEditText) itemView.findViewById(R.id.txtDueDays);

        }
    }
}

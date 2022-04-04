package com.srmuh.staffportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import webservice.SqlliteController;
import webservice.WebService;

public class LeaveStatus extends AppCompatActivity {
    private TextView tvPageTitle, tvLastUpdated;
    private long lngEmployeeId=0;
    private long lngCancelLeaveApplnId=0;
    private static String strParameters[];
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    private static String ResultString = "";
    private String strResultMessage="";
    private ArrayList<String> leavestatus_list = new ArrayList<String>(200);
    SQLiteDatabase db;
    private int intFlag = 0;
    SqlliteController controllerdb = new SqlliteController(this);

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavestatus);
        StatusColor.SetStatusColor(getWindow(), ContextCompat.getColor(this, R.color.colorblue));
        tvPageTitle = (TextView) findViewById(R.id.pageTitle);
        tvPageTitle.setText("Leave Status");
//        tvLastUpdated = (TextView) findViewById(R.id.txtLastUpdated);
        Button btnBack=(Button) findViewById(R.id.button_back);
//        Button btnRefresh=(Button) findViewById(R.id.button_refresh);
        final SharedPreferences loginsession = getApplicationContext().getSharedPreferences("SessionLogin", 0);
        lngEmployeeId = loginsession.getLong("userid", 1);
        intFlag=getIntent().getIntExtra("Flag",1);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (intFlag == 1){
                    intent = new Intent(LeaveStatus.this, HomeScreenCategory.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                if (intFlag == 2){
                    intent = new Intent(LeaveStatus.this, HomePageGridViewLayout.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
            }
        });
//        btnRefresh.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
            strParameters = new String[]{"Long", "employeeid", String.valueOf(lngEmployeeId)};
            WebService.strParameters = strParameters;
            WebService.METHOD_NAME = "leaveStatusjson";
            AsyncCallWS task = new AsyncCallWS();
            task.execute();
//            }
//        });
//        displayLeaveStatus();
    }

    public void callLeaveCancel(long lngLeaveApplicationId){
        lngCancelLeaveApplnId = lngLeaveApplicationId;
        strParameters = new String[]{"Long", "leaveapplicationid", String.valueOf(lngLeaveApplicationId)};
        WebService.strParameters = strParameters;
        WebService.METHOD_NAME = "cancelLeaveApplication";
        AsyncCallCancelLeaveWS task = new AsyncCallCancelLeaveWS();
        task.execute();
    }

    private class AsyncCallCancelLeaveWS extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(LeaveStatus.this);
        @Override
        protected void onPreExecute() {
            dialog.setMessage(getResources().getString(R.string.loading));
            //show dialog
            dialog.show();
            //Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Log.i(TAG, "doInBackground");
            if (android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();
            ResultString = WebService.invokeWS();
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            //Log.i(TAG, "onPostExecute");
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
            try {
                JSONObject jsonObject = new JSONObject(ResultString.toString());
                if (jsonObject.getString("Status").equals("Success")){
                    SqlliteController sc = new SqlliteController(LeaveStatus.this);
                    sc.cancelLeaveApplication(lngCancelLeaveApplnId);
                }
                if (jsonObject.getString("Status").equals("Error") || jsonObject.getString("Status").equals("Success"))
                    strResultMessage = jsonObject.getString("Message");
                Toast.makeText(LeaveStatus.this, "Response: " + strResultMessage, Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent;
        if (intFlag == 1){
            intent = new Intent(LeaveStatus.this, HomeScreenCategory.class);
            startActivity(intent);
        }
        if (intFlag == 2){
            intent = new Intent(LeaveStatus.this, HomePageGridViewLayout.class);
            startActivity(intent);
        }
        this.finish();
    }

    private void displayLeaveStatus(){
        db = controllerdb.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT strftime('%d-%m-%Y %H:%M:%S', lastupdatedate) as lastupdated,* FROM leavestatus " +
                    "WHERE employeeid=" + lngEmployeeId + " ORDER BY leaveapplicationid desc", null);
            if (cursor.moveToFirst()){
                do {
//                    tvLastUpdated.setText("Last Updated: "+cursor.getString(cursor.getColumnIndex("lastupdated")));
                    leavestatus_list.add(cursor.getString(cursor.getColumnIndex("applicationdate")) + "##" +
                            cursor.getString(cursor.getColumnIndex("leavetype")) + "##" + cursor.getString(cursor.getColumnIndex("fromdate")) + "##" +
                            cursor.getString(cursor.getColumnIndex("todate"))+ "##" + cursor.getString(cursor.getColumnIndex("session"))+ "##" +
                            cursor.getString(cursor.getColumnIndex("reason"))+ "##" + cursor.getString(cursor.getColumnIndex("noofdays"))+ "##" +
                            cursor.getString(cursor.getColumnIndex("leavestatus"))+ "##" + cursor.getString(cursor.getColumnIndex("leaveapplicationid")));
                } while (cursor.moveToNext());
                if (leavestatus_list.size() == 0) {
                    Toast.makeText(LeaveStatus.this, "Response: No Data Found", Toast.LENGTH_LONG).show();
                } else {
                    mRecyclerView = (RecyclerView) findViewById(R.id.rvLeaveStatus); // Assigning the RecyclerView Object to the xml View

                    mRecyclerView.setHasFixedSize(true);
                    // Letting the system know that the list objects are of fixed size
                    LeaveStatusLVAdapter TVA = new LeaveStatusLVAdapter(leavestatus_list, R.layout.leavestatuslistitem);
                    mRecyclerView.setAdapter(TVA);
                    mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
                    mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager
                }
            } else {
                strParameters = new String[]{"Long", "employeeid", String.valueOf(lngEmployeeId)};
                WebService.strParameters = strParameters;
                WebService.METHOD_NAME = "leaveStatusjson";
                AsyncCallWS task = new AsyncCallWS();
                task.execute();
            }
            cursor.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            strParameters = new String[]{"Long", "employeeid", String.valueOf(lngEmployeeId)};
            WebService.strParameters = strParameters;
            WebService.METHOD_NAME = "leaveStatusjson";
            AsyncCallWS task = new AsyncCallWS();
            task.execute();
        }
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(LeaveStatus.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage(getResources().getString(R.string.loading));
            //show dialog
            dialog.show();
            //Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Log.i(TAG, "doInBackground");
            if (android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();
            ResultString = WebService.invokeWS();
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            //Log.i(TAG, "onPostExecute");
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            try {
                JSONArray temp = new JSONArray(ResultString.toString());
                SqlliteController sc = new SqlliteController(LeaveStatus.this);
                sc.deleteLeaveStatus(lngEmployeeId);
                for (int i = 0; i <= temp.length() - 1; i++) {
                    JSONObject object = new JSONObject(temp.getJSONObject(i).toString());
                    sc.insertLeaveStatus(lngEmployeeId,object.getLong("leaveapplicationid"), object.getString("applicationdate"), object.getString("leavetype"), object.getString("fromdate"), object.getString("todate"), object.getString("session"), object.getString("reason"), object.getString("noofdays"),object.getString("leavestatus"));
                }
                mRecyclerView = (RecyclerView) findViewById(R.id.rvLeaveStatus); // Assigning the RecyclerView Object to the xml View
                leavestatus_list.clear();
                LeaveStatusLVAdapter TVA = new LeaveStatusLVAdapter(leavestatus_list, R.layout.leavestatuslistitem);
                TVA.notifyDataSetChanged();
                displayLeaveStatus();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

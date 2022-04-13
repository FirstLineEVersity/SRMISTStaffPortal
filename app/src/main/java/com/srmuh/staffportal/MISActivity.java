package com.srmuh.staffportal;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.srmuh.staffportal.properties.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import webservice.WebService;

public class MISActivity extends AppCompatActivity {
    long lngEmployeeId = 0;
    String strEmployeeName = "";
    TextView tvPageTitle;
    String strHTML = "";
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    private static String ResultString = "";
    private String strResultMessage = "";
    private ArrayList<String> leavestatus_list = new ArrayList<String>(200);
    String pageTitele = "";
    RadioButton booksInHand, fineHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaveavailability);
        StatusColor.SetStatusColor(getWindow(), ContextCompat.getColor(this, R.color.colorblue));
        tvPageTitle = (TextView) findViewById(R.id.pageTitle);
        booksInHand = findViewById(R.id.booksInHand);
        fineHistory = findViewById(R.id.fineHistory);
        pageTitele = getIntent().getExtras().getString(Properties.dashboardName, "");
        tvPageTitle.setText(pageTitele);
        Button btnBack = (Button) findViewById(R.id.button_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        final SharedPreferences loginsession = getApplicationContext().getSharedPreferences("SessionLogin", 0);
        lngEmployeeId = loginsession.getLong("userid", 1);
        strEmployeeName = loginsession.getString("employeename", "");
        RadioGroup rgLibrary = findViewById(R.id.rgLibrary);
        if (pageTitele.equalsIgnoreCase(getResources().getString(R.string.mdLibraryTransaction))) {
            rgLibrary.setVisibility(View.VISIBLE);
        }else{
            rgLibrary.setVisibility(View.GONE);

        }

        if (!CheckNetwork.isInternetAvailable(MISActivity.this)) {
            Toast.makeText(MISActivity.this, getResources().getString(R.string.loginNoInterNet), Toast.LENGTH_LONG).show();
            return;
        } else {

            AsyncCallWS task = new AsyncCallWS();
            task.execute();
        }


        TableLayout tbl = findViewById(R.id.tbAtten);
        tbl.setVisibility(View.GONE);
        LinearLayout ll = findViewById(R.id.attenColour);
        ll.setVisibility(View.GONE);
            booksInHand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fineHistory.setChecked(false);
                    WebService.strParameters = new String[]{"Long", "employeeid", String.valueOf(lngEmployeeId)};
                    WebService.METHOD_NAME = getResources().getString(R.string.wsMembersBooksinHand);
                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();

                }
            }
        });
        fineHistory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    booksInHand.setChecked(false);
                    WebService.strParameters = new String[]{"Long", "employeeid", String.valueOf(lngEmployeeId)};
                    WebService.METHOD_NAME = getResources().getString(R.string.wsMemberFineHistory);
                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();

                }
            }
        });


    }


    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(MISActivity.this);

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
         //   Log.d("TEST: ",WebService.METHOD_NAME);
            ResultString = WebService.invokeWS();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Log.i(TAG, "onPostExecute");
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            leavestatus_list.clear();
            if (ResultString.toString().equalsIgnoreCase("")) {
                TextView txtNoData = findViewById(R.id.txtNoData);
                txtNoData.setVisibility(View.VISIBLE);

            } else {
                try {
                    JSONObject JSobject = new JSONObject(ResultString.toString());
                    if (JSobject.has("Status") && JSobject.getString("Status").equalsIgnoreCase("Success")) {
                        JSONArray temp = new JSONArray(JSobject.getString("Data"));
                        mRecyclerView = (RecyclerView) findViewById(R.id.rvLeaveStatus); // Assigning the RecyclerView Object to the xml View
                        //mRecyclerView.re
                        if (pageTitele.equalsIgnoreCase(getResources().getString(R.string.mdStaffBirthday))) {
                            for (int i = 0; i <= temp.length() - 1; i++) {
                                JSONObject object = new JSONObject(temp.getJSONObject(i).toString());
                                leavestatus_list.add(object.getString("employeename") + "##" + object.getString("designation"));
                            }
                            BirthdayLVAdapter TVA = new BirthdayLVAdapter(leavestatus_list, R.layout.bithdaylistitem);
                            TVA.notifyDataSetChanged();
                            mRecyclerView.setAdapter(TVA);

                        } else if (pageTitele.equalsIgnoreCase(getResources().getString(R.string.mdStaffAttendance))) {
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                            int colorID = 0;
                            for (int i = 0; i <= temp.length() - 1; i++) {
                                JSONObject object = new JSONObject(temp.getJSONObject(i).toString());
                                String displayDate = object.getString("disleavedate");
                                Date d = df.parse(displayDate);
                                Date currentDate = Calendar.getInstance().getTime();
                                if (df.format(currentDate).equalsIgnoreCase(displayDate)) {
                                    colorID = getResources().getColor(R.color.lSelectedDay);

                                } else if (currentDate.compareTo(d) > 0) {
                                    colorID = getResources().getColor(R.color.lPreviousDay);

                                } else {
                                    colorID = getResources().getColor(R.color.lFutureDay);
                                }
                                String leaveCount = "-";
                                String odCount = "-";
                                String permissionCount = "-";
                                String lateCount = "-";
                                if (object.has("leavecnt")) {
                                    leaveCount = object.getString("leavecnt");
                                }
                                if (object.has("odcnt")) {
                                    odCount = object.getString("odcnt");
                                }
                                if (object.has("permissioncnt")) {
                                    permissionCount = object.getString("permissioncnt");
                                }

                                if (object.has("permissioncnt")) {
                                    permissionCount = object.getString("permissioncnt");
                                }
                                if (object.has("latecnt")) {
                                    lateCount = object.getString("latecnt");
                                }
                                leavestatus_list.add(leaveCount + "##" + odCount + "##" + permissionCount + "##" + lateCount + "##" + displayDate + "##" + colorID);

                            }
                            TableLayout tbl = findViewById(R.id.tbAtten);
                            tbl.setVisibility(View.VISIBLE);
                            LinearLayout ll = findViewById(R.id.attenColour);
                            ll.setVisibility(View.VISIBLE);
                            AttandenceStaffLeaveLVAdapter TVA = new AttandenceStaffLeaveLVAdapter(leavestatus_list, R.layout.attendancestaffleavelistitem);
                            TVA.notifyDataSetChanged();
                            mRecyclerView.setAdapter(TVA);

                        } else if (pageTitele.equalsIgnoreCase(getResources().getString(R.string.mdStaffLeave))) {
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                            int colorID = 0;
                            for (int i = 0; i <= temp.length() - 1; i++) {
                                JSONObject object = new JSONObject(temp.getJSONObject(i).toString());

                                String displayDate = object.getString("disleavedate");

                                Date d = df.parse(displayDate);
                                Date currentDate = Calendar.getInstance().getTime();
                                if (df.format(currentDate).equalsIgnoreCase(displayDate)) {
                                    colorID = getResources().getColor(R.color.lSelectedDay);

                                } else if (currentDate.compareTo(d) > 0) {
                                    colorID = getResources().getColor(R.color.lPreviousDay);

                                } else {
                                    colorID = getResources().getColor(R.color.lFutureDay);
                                }
                                String leaveCount = "-";
                                String odCount = "-";
                                String permissionCount = "-";
                                String lateCount = "-";
                                if (object.has("leavecnt")) {
                                    leaveCount = object.getString("leavecnt");
                                }
                                if (object.has("odcnt")) {
                                    odCount = object.getString("odcnt");
                                }
                                if (object.has("permissioncnt")) {
                                    permissionCount = object.getString("permissioncnt");
                                }

                                if (object.has("permissioncnt")) {
                                    permissionCount = object.getString("permissioncnt");
                                }
                                if (object.has("latecnt")) {
                                    lateCount = object.getString("latecnt");
                                }
                                leavestatus_list.add(leaveCount + "##" + odCount + "##" + permissionCount + "##" + lateCount + "##" + displayDate + "##" + colorID);
                            }
                            TableLayout tbl = findViewById(R.id.tbAtten);
                            tbl.setVisibility(View.VISIBLE);
                            LinearLayout ll = findViewById(R.id.attenColour);
                            ll.setVisibility(View.VISIBLE);
                            AttandenceStaffLeaveLVAdapter TVA = new AttandenceStaffLeaveLVAdapter(leavestatus_list, R.layout.attendancestaffleavelistitem);
                            TVA.notifyDataSetChanged();
                            mRecyclerView.setAdapter(TVA);

                        } else if (pageTitele.equalsIgnoreCase(getResources().getString(R.string.mdAbsenteeCount))) {
                            for (int i = 0; i <= temp.length() - 1; i++) {
                                JSONObject object = new JSONObject(temp.getJSONObject(i).toString());
                                leavestatus_list.add(object.getString("program") + "##" + object.getString("absenteecount") + "##" + object.getString("studentcount") + "##" + object.getString("hour"));
                            }
                            AbsenteeCountLVAdapter TVA = new AbsenteeCountLVAdapter(leavestatus_list, R.layout.absenteecountlistitem);
                            TVA.notifyDataSetChanged();
                            mRecyclerView.setAdapter(TVA);


                        } else if (pageTitele.equalsIgnoreCase(getResources().getString(R.string.mdTodaysAbsentee))) {
                            // {"Status":"Success","Message":"","Data":[{"hour":"2","studentname":"SANGAMITHRA S","program":"B.Sc. Computer Science 6th Semester-B-Shift 1","dayorder":"Day 1","registerno":"221911104"},{"hour":"2","studentname":"SUMITHA  A","program":"B.Sc. Computer Science 6th Semester-B-Shift 1","dayorder":"Day 1","registerno":"221911129"}]}
                            for (int i = 0; i <= temp.length() - 1; i++) {
                                JSONObject object = new JSONObject(temp.getJSONObject(i).toString());
                                leavestatus_list.add(object.getString("program") + "##" + object.getString("dayorder") + "##" + object.getString("hour") + "##" + object.getString("studentname") + "##" + object.getString("registerno"));
                            }
                            TodaysLeaveLVAdapter TVA = new TodaysLeaveLVAdapter(leavestatus_list, R.layout.todaysabsenteelistitem);
                            TVA.notifyDataSetChanged();
                            mRecyclerView.setAdapter(TVA);

                        }else if (pageTitele.equalsIgnoreCase(getResources().getString(R.string.mdLibraryTransaction))) {
                            if (WebService.METHOD_NAME.equalsIgnoreCase(MISActivity.this.getResources().getString(R.string.wsMembersBooksinHand))) {
                                for (int i = 0; i <= temp.length() - 1; i++) {
                                    JSONObject object = new JSONObject(temp.getJSONObject(i).toString());
                                    leavestatus_list.add(object.getString("issuedate") + "##" + object.getString("accessionno") + "##" + object.getString("title") + "##" + object.getString("duedate") + "##" + object.getString("duedays"));
                                }
                                BooksInHandLVAdapter TVA = new BooksInHandLVAdapter(leavestatus_list, R.layout.booksinhandlistitem);
                                TVA.notifyDataSetChanged();
                                mRecyclerView.setAdapter(TVA);

                            } else {
                                for (int i = 0; i <= temp.length() - 1; i++) {
                                    JSONObject object = new JSONObject(temp.getJSONObject(i).toString());
                                    leavestatus_list.add( object.getString("accessionno") + "##" + object.getString("title") + "##" + object.getString("duedate") + "##" + object.getString("returndate")+ "##" + object.getString("status")+ "##" + object.getString("fine"));
                                }
                                FineHistoryLVAdapter TVA = new FineHistoryLVAdapter(leavestatus_list, R.layout.finehistorylistitem);
                                TVA.notifyDataSetChanged();
                                mRecyclerView.setAdapter(TVA);

                            }



                        }

                        mLayoutManager = new LinearLayoutManager(getApplicationContext());                 // Creating a layout Manager
                        mRecyclerView.setLayoutManager(mLayoutManager);
                    } else {
                        TextView txtNoData = findViewById(R.id.txtNoData);
                        txtNoData.setVisibility(View.VISIBLE);
                        txtNoData.setText(JSobject.getString("Message"));
                        Toast.makeText(MISActivity.this, JSobject.getString("Message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    TextView txtNoData = findViewById(R.id.txtNoData);
                    txtNoData.setVisibility(View.VISIBLE);
                    txtNoData.setText(ResultString);
                    Toast.makeText(MISActivity.this, ResultString, Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
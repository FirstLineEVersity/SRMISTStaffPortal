package com.srmuh.staffportal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class FacultyDashboard extends AppCompatActivity implements View.OnClickListener {
    long lngEmployeeId = 0;
    TextView tvPageTitle;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facultydashboard);
        StatusColor.SetStatusColor(getWindow(), ContextCompat.getColor(this, R.color.colorblue));
        final SharedPreferences loginsession = getApplicationContext().getSharedPreferences("SessionLogin", 0);
        lngEmployeeId = loginsession.getLong("userid", 1);
        tvPageTitle = (TextView) findViewById(R.id.pageTitle);
        tvPageTitle.setText("Faculty Dashboard");

        Button btnBack=(Button) findViewById(R.id.button_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
            Intent intent = new Intent(FacultyDashboard.this, HomeScreenCategory.class);
            startActivity(intent);
            }
        });

        CardView cv_staffleave = (CardView) findViewById(R.id.fdbstaffleave);
        CardView cv_librarytransaction = (CardView) findViewById(R.id.fdblibrarytransactions);
        CardView cv_absenteecount = (CardView) findViewById(R.id.fdbabsenteecount);
        CardView cv_todaysabsentee = (CardView) findViewById(R.id.fdbtodaysabsentee);
//        CardView cv_projecttracker = (CardView) findViewById(R.id.fdbprojecttracker);
//        CardView cv_staffbirthday = (CardView) findViewById(R.id.fdbstaffbirthday);
//        CardView cv_UpcomingEvents = (CardView) findViewById(R.id.fdbUpcomingEvents);

        cv_staffleave.setOnClickListener(this);
        cv_librarytransaction.setOnClickListener(this);
        cv_absenteecount.setOnClickListener(this);
        cv_todaysabsentee.setOnClickListener(this);
//        cv_projecttracker.setOnClickListener(this);
//        cv_staffbirthday.setOnClickListener(this);
//        cv_UpcomingEvents.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(FacultyDashboard.this, tabbedfacultydashboard.class);
        switch(v.getId()){
            case R.id.fdbstaffleave:
                intent.putExtra("ClickedId",0);
                break;
            case R.id.fdblibrarytransactions:
                intent.putExtra("ClickedId",1);
                break;
            case R.id.fdbabsenteecount:
                intent.putExtra("ClickedId",2);
                break;
            case R.id.fdbtodaysabsentee:
                intent.putExtra("ClickedId",3);
                break;
//            case R.id.fdbprojecttracker:
//                intent.putExtra("ClickedId",4);
//                break;
//            case R.id.fdbstaffbirthday:
//                intent.putExtra("ClickedId",5);
//                break;
//            case R.id.fdbUpcomingEvents:
//                intent.putExtra("ClickedId",6);
//                break;
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, HomeScreenCategory.class);
        startActivity(intent);
        this.finish();
    }
}

 package com.srmuh.staffportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

 public class ManagementDashboard extends AppCompatActivity implements View.OnClickListener {
    private TextView tvPageTitle;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managementdashboard);
        StatusColor.SetStatusColor(getWindow(), ContextCompat.getColor(this, R.color.colorblue));
        tvPageTitle = (TextView) findViewById(R.id.pageTitle);
        tvPageTitle.setText("Management Dashboard");
        Button btnBack=(Button) findViewById(R.id.button_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
            Intent intent = new Intent(ManagementDashboard.this, HomeScreenCategory.class);
            startActivity(intent);
            }
        });

        CardView cv_currentadmission = (CardView) findViewById(R.id.mdbcurrentadmission);
        CardView cv_duelist = (CardView) findViewById(R.id.mdbduelist);
        CardView cv_staffbirthday = (CardView) findViewById(R.id.mdbstaffbirthday);
        CardView cv_staffattendance = (CardView) findViewById(R.id.mdbstaffattendance);

        //CardView cv_grievancecell = (CardView) findViewById(R.id.mdbgrievancecell);
        CardView cv_librarydetails = (CardView) findViewById(R.id.mdblibrarydetails);
        CardView cv_voucherdetails = (CardView) findViewById(R.id.mdbVoucherDetails);
//        CardView cv_helpdesk = (CardView) findViewById(R.id.mdbhelpdesk);

        cv_currentadmission.setOnClickListener(this);
        cv_duelist.setOnClickListener(this);
        cv_staffbirthday.setOnClickListener(this);
        cv_staffattendance.setOnClickListener(this);
        //cv_grievancecell.setOnClickListener(this);
        cv_librarydetails.setOnClickListener(this);
        cv_voucherdetails.setOnClickListener(this);
//        cv_helpdesk.setOnClickListener(this);
    }

     @Override
     public void onClick(View v) {
         Intent intent;
         switch(v.getId()){
             case R.id.mdbcurrentadmission:
                 intent = new Intent(ManagementDashboard.this, AdmissionCurrentYearBarChart.class);
                 intent.putExtra("ClickedId",0);
                 startActivity(intent);
                 break;
             case R.id.mdbduelist:
                 intent = new Intent(ManagementDashboard.this, tabbedmanagementdashboard.class);
                 intent.putExtra("ClickedId",1);
                 startActivity(intent);
                 break;
             case R.id.mdbstaffbirthday:
                 intent = new Intent(ManagementDashboard.this, tabbedmanagementdashboard.class);
                 intent.putExtra("ClickedId",2);
                 startActivity(intent);
                 break;
             case R.id.mdbstaffattendance:
                 intent = new Intent(ManagementDashboard.this, tabbedmanagementdashboard.class);
                 intent.putExtra("ClickedId",3);
                 startActivity(intent);
                 break;
             case R.id.mdblibrarydetails:
                 intent = new Intent(ManagementDashboard.this, tabbedmanagementdashboard.class);
                 intent.putExtra("ClickedId",4);
                 startActivity(intent);
                 break;
             case R.id.mdbVoucherDetails:
                 intent = new Intent(ManagementDashboard.this, tabbedmanagementdashboard.class);
                 intent.putExtra("ClickedId",5);
                 startActivity(intent);
                 break;
//             case R.id.mdbhelpdesk:
//                 intent = new Intent(ManagementDashboard.this, tabbedmanagementdashboard.class);
//                 intent.putExtra("ClickedId",7);
//                 startActivity(intent);
//                 break;
         }
     }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, HomeScreenCategory.class);
        startActivity(intent);
        this.finish();
    }
}

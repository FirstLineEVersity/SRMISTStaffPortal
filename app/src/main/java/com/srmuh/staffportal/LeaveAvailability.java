package com.srmuh.staffportal;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LeaveAvailability extends AppCompatActivity {
    long lngEmployeeId = 0;
    String strEmployeeName = "";
    TextView tvPageTitle;
    String strHTML = "";
    private WebView wvLeaveAvailability;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaveavailability);
        StatusColor.SetStatusColor(getWindow(), ContextCompat.getColor(this, R.color.colorblue));
        tvPageTitle = (TextView) findViewById(R.id.pageTitle);
        tvPageTitle.setText("Leave Availability");
        Button btnBack=(Button) findViewById(R.id.button_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LeaveAvailability.this, LeaveEntry.class);
                startActivity(intent);
            }
        });
        final SharedPreferences loginsession = getApplicationContext().getSharedPreferences("SessionLogin", 0);
        lngEmployeeId = loginsession.getLong("userid", 1);
        strEmployeeName = loginsession.getString("employeename","");
        wvLeaveAvailability = (WebView) findViewById(R.id.wvLeaveAvailability);
        wvLeaveAvailability.setWebViewClient(new WebViewClient());

        WebSettings webSettings = wvLeaveAvailability.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wvLeaveAvailability.getSettings().setBuiltInZoomControls(true);
        wvLeaveAvailability.getSettings().setSupportZoom(true);
        wvLeaveAvailability.getSettings().setUseWideViewPort(true);
        wvLeaveAvailability.getSettings().setLoadWithOverviewMode(true);
        wvLeaveAvailability.loadUrl("http://erp.shasuncollege.edu.in/evarsityshasun/workforce/LeaveManagement/LeaveAvailabilityForStaffMobileView.jsp?EmployeeId="+lngEmployeeId);
    }
    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
        wvLeaveAvailability.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        wvLeaveAvailability.onPause();
        // ...
        super.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        // new intent to call an activity that you choose
        Intent intent = new Intent(this, LeaveEntry.class);
        setResult(RESULT_OK,intent);
        startActivity(intent);
        this.finish();
    }
    public class WebViewClient extends android.webkit.WebViewClient {
        ProgressDialog dialog = new ProgressDialog(LeaveAvailability.this);
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            dialog.setMessage(getResources().getString(R.string.loading));
            dialog.show();
            Runnable progressRunnable = new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            };
            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 500);
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

}

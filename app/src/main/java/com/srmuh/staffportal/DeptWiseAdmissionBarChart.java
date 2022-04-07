
package com.srmuh.staffportal;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.srmuh.staffportal.properties.DemoBase;
import com.srmuh.staffportal.properties.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import webservice.WebService;

public class DeptWiseAdmissionBarChart extends DemoBase implements
        OnChartValueSelectedListener {

    private PieChart chart;
    private static String ResultString = "";
    private int intOfficeId = 0;
    List<Integer> gradientFills = new ArrayList<>();
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pie_chart);
        TextView tvPageTitle = (TextView) findViewById(R.id.pageTitle);
        if(getIntent().getExtras() != null) {
            tvPageTitle.setText(getIntent().getExtras().getString(Properties.dashboardName, ""));
        } Button btnBack=(Button) findViewById(R.id.button_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
        int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
        int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
        int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
        int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);
        int endColor6 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
        int endColor7 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
        int endColor8 = ContextCompat.getColor(this, android.R.color.holo_green_light);
        int endColor9 = ContextCompat.getColor(this, android.R.color.holo_red_light);
        int endColor10 = ContextCompat.getColor(this, R.color.btnHoverGreen);
        int endColor11 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
        int endColor12 = ContextCompat.getColor(this, R.color.bYellow);
        int endColor13 = ContextCompat.getColor(this, R.color.bMeroon);
        int endColor14 = ContextCompat.getColor(this, R.color.bpink);
        int endColor15 = ContextCompat.getColor(this, R.color.bIblue);

        gradientFills.add(endColor1);
        gradientFills.add(endColor2);
        gradientFills.add(endColor3);
        gradientFills.add(endColor4);
        gradientFills.add(endColor5);
        gradientFills.add(endColor6);
        gradientFills.add(endColor7);
        gradientFills.add(endColor8);
        gradientFills.add(endColor9);
        gradientFills.add(endColor10);
        gradientFills.add(endColor11);
        gradientFills.add(endColor12);
        gradientFills.add(endColor13);
        gradientFills.add(endColor14);
        gradientFills.add(endColor15);
        gradientFills.add(endColor1);
        gradientFills.add(endColor2);
        gradientFills.add(endColor3);
        gradientFills.add(endColor4);
        gradientFills.add(endColor5);
        gradientFills.add(endColor6);
        gradientFills.add(endColor7);
        gradientFills.add(endColor8);
        gradientFills.add(endColor9);
        gradientFills.add(endColor10);
        gradientFills.add(endColor11);
        gradientFills.add(endColor12);
        gradientFills.add(endColor13);
        gradientFills.add(endColor14);
        gradientFills.add(endColor15);

        chart = findViewById(R.id.chart1);
        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterTextTypeface(tfLight);
        chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(38f);
        chart.setTransparentCircleRadius(51f);

        chart.setDrawCenterText(true);
        chart.setDrawEntryLabels(false);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.setCenterText("Admission Count");

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);



        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        chart.setOnChartValueSelectedListener(this);
        String offID = "1";
        if(getIntent() != null && getIntent().getExtras() != null) {
            offID = getIntent().getExtras().getString("OfficeId", 1 + "");
            intOfficeId = Integer.parseInt(offID);
            WebService.strParameters = new String[]{"int", "officeid", String.valueOf(intOfficeId)};
            WebService.METHOD_NAME = "getDeptWiseAdmission";
            AsyncCallWS task = new AsyncCallWS();
            task.execute();
        }


        chart.animateY(400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);


        // entry label styling

    }

    private void setData() {

        for(int i=0;i<entries.size();i++){
            PieEntry pe = entries.get(i);
        }
        PieDataSet dataSet = new PieDataSet(entries, "Admission Count");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors


        dataSet.setColors(gradientFills);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tfLight);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.pie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }


    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "PieChartActivity");
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(DeptWiseAdmissionBarChart.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage(getResources().getString(R.string.loading));
            //show dialog
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();
            ResultString = WebService.invokeWS();
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            Log.d("TEST: ",ResultString.toString());
            try {
                JSONObject JSobject = new JSONObject(ResultString.toString());
                if(JSobject.has("Status") && JSobject.getString("Status").equalsIgnoreCase("Success")) {
                    JSONArray temp = new JSONArray(JSobject.getString("Data"));
                    for (int i = 0; i <= temp.length() - 1; i++) {

                        JSONObject object = new JSONObject(temp.getJSONObject(i).toString());
                        entries.add(new PieEntry(Float.parseFloat(object.getString("admissioncnt")),object.getString("program")+" : "+object.getString("admissioncnt")));
                        leavestatus_list.add(object.getString("program")+" - "+object.getString("admissioncnt")+ "##" +gradientFills.get(i));

                    }
                    setData();
                    mRecyclerView = (RecyclerView) findViewById(R.id.xyLable); // Assigning the RecyclerView Object to the xml View
                    BarChartXYLableAdapter TVA = new BarChartXYLableAdapter(leavestatus_list, R.layout.barchartxylableitem);
                    TVA.notifyDataSetChanged();
                    mRecyclerView.setAdapter(TVA);
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());                 // Creating a layout Manager
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    //displayBarChart();
                }else{
                    Toast.makeText(DeptWiseAdmissionBarChart.this,JSobject.getString("Message"),Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                Toast.makeText(DeptWiseAdmissionBarChart.this,ResultString,Toast.LENGTH_LONG).show();
            }
        }
    }
    ArrayList<PieEntry> entries = new ArrayList<>();

    private ArrayList<String> leavestatus_list = new ArrayList<String>(200);

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

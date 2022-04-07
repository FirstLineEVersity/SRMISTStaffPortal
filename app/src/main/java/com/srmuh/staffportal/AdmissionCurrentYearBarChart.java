
package com.srmuh.staffportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.srmuh.staffportal.properties.DemoBase;
import com.srmuh.staffportal.properties.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import webservice.WebService;

public class AdmissionCurrentYearBarChart extends DemoBase implements
        OnChartValueSelectedListener {
    List<Integer> gradientFills = new ArrayList<>();
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager

    private BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.admissioncurrentyearbarchart);

        TextView tvPageTitle = (TextView) findViewById(R.id.pageTitle);
        if(getIntent().getExtras() != null) {
            tvPageTitle.setText(getIntent().getExtras().getString(Properties.dashboardName, ""));
        }
        Button btnBack=(Button) findViewById(R.id.button_back);
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
        chart.setOnChartValueSelectedListener(this);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        //xAxis.setValueFormatter(xAxisFormatter);

       // IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        //leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        //rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        WebService.strParameters = new String[]{"int", "flag", "0"};
        WebService.METHOD_NAME = "getCurrentYearAdmissionShift";
        AsyncCallWS task = new AsyncCallWS();
        task.execute();

        //XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        //mv.setChartView(chart); // For bounds control
        //chart.setMarker(mv); // Set the marker to the chart

        // setting data
        //seekBarY.setProgress(50);
        //seekBarX.setProgress(12);

        chart.getLegend().setEnabled(false);
        //chart.setDrawLegend(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }
 @Override
    protected void saveToGallery() {
        saveToGallery(chart, "BarChartActivity");
    }


    private final RectF onValueSelectedRectF = new RectF();

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Intent intent = new Intent(AdmissionCurrentYearBarChart.this, com.srmuh.staffportal.DeptWiseAdmissionBarChart.class);
        try {
            JSONObject JSobject = new JSONObject(ResultString.toString());
            if(JSobject.has("Status") && JSobject.getString("Status").equalsIgnoreCase("Success")) {
                JSONArray temp = new JSONArray(JSobject.getString("Data"));
                int i = Math.round(e.getX());
                //[{"officename":"Shift 1","officeid":"1","updateddate":"23-06-2021 22:24","admissioncnt":"176"},{"officename":"Shift 2","officeid":"2","updateddate":"23-06-2021 22:24","admissioncnt":"66"}]

                JSONObject object = new JSONObject(temp.getJSONObject(i).toString());

                intent.putExtra("OfficeId", object.getString("officeid"));
                intent.putExtra(Properties.dashboardName, "Admission: " + object.getString("officename"));
                //Log.e("TEST",object.getString("officeid"));
                startActivity(intent);

            }

//            Toast.makeText(BarChartActivity.this," DataSet index: " + h.getDataSetIndex() + " Data: " + object.getString("OfficeId"), Toast.LENGTH_LONG).show();

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        Log.e("TEST",e.getX()+"" );
//

    }

    @Override
    public void onNothingSelected() { }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(AdmissionCurrentYearBarChart.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage(getResources().getString(R.string.loading));
            //show dialog
            dialog.show();
            //Log.i(TAG, "onPreExecute");
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
            //Log.i(TAG, "onPostExecute");
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            leavestatus_list.clear();

            try {
                JSONObject JSobject = new JSONObject(ResultString.toString());
                if(JSobject.has("Status") && JSobject.getString("Status").equalsIgnoreCase("Success")) {
                    JSONArray temp = new JSONArray(JSobject.getString("Data"));

                    BarDataSet set1;
                    ArrayList<IBarDataSet> dataSets = new ArrayList<>();


                    for (int i = 0; i <= temp.length() - 1; i++) {
                    JSONObject object = new JSONObject(temp.getJSONObject(i).toString());
                    ArrayList<BarEntry> values = new ArrayList<>();
                    values.add(new BarEntry(i,Float.parseFloat(object.getString("admissioncnt"))));
                    set1 = new BarDataSet(values,"");
                        xAxisLabel.add(object.getString("officename"));
                    set1.setDrawIcons(false);
                    int color = getResources().getColor(R.color.appColor);
                    if(i< gradientFills.size()) {
                        color = gradientFills.get(i);
                    }
                        set1.setColors(color);
                        dataSets.add(set1);
                        leavestatus_list.add(object.getString("officename")+" - "+object.getString("admissioncnt")+ "##" +color);


                }
                    BarData data = new BarData(dataSets);
                    data.setValueTextSize(10f);
                    data.setValueTypeface(tfLight);
                    data.setBarWidth(0.9f);
                    chart.setData(data);
                    mRecyclerView = (RecyclerView) findViewById(R.id.xyLable); // Assigning the RecyclerView Object to the xml View
                    BarChartXYLableAdapter TVA = new BarChartXYLableAdapter(leavestatus_list, R.layout.barchartxylableitem);
                    TVA.notifyDataSetChanged();
                    mRecyclerView.setAdapter(TVA);
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());                 // Creating a layout Manager
                    mRecyclerView.setLayoutManager(mLayoutManager);
                }else{
                    Toast.makeText(AdmissionCurrentYearBarChart.this,JSobject.getString("Message"),Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                Toast.makeText(AdmissionCurrentYearBarChart.this,ResultString,Toast.LENGTH_LONG).show();
            }
        }
    }private static String ResultString = "";
    private ArrayList<String> leavestatus_list = new ArrayList<String>(200);

    final ArrayList<String> xAxisLabel = new ArrayList<>();


    public class DayAxisValueFormatter extends ValueFormatter {
        private final BarLineChartBase<?> chart;
        public DayAxisValueFormatter(BarLineChartBase<?> chart) {
            this.chart = chart;
        }
        @Override
        public String getFormattedValue(float value) {
            int val = Math.round(value);

String xAxisLabelText = xAxisLabel.get(val);
            return xAxisLabelText;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package com.srmuh.staffportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.listener.OnDrawListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import webservice.WebService;

public class AdmissionCurrentYearBarChart extends AppCompatActivity implements OnChartValueSelectedListener,
        OnDrawListener {
    private static String strParameters[];
    private static String ResultString = "";
    private String strResultMessage="";
    private ArrayList<String> BarEntryLabels; // = new ArrayList<String>(200);
    ArrayList<BarEntry> BARENTRY;
    BarChart chart ;
//    ArrayList<BarEntry> BARENTRY ;
//    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admissioncurrentyearbarchart);
        chart = (BarChart) findViewById(R.id.chart1);

        chart.setOnChartValueSelectedListener(this);
        chart.setOnDrawListener(this);
        chart.setDrawGridBackground(false);
        strParameters = new String[]{"int", "flag", "0"};
        WebService.strParameters = strParameters;
        WebService.METHOD_NAME = "getCurrentYearAdmissionShift";
        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    public void displayBarChart(){
        if (BARENTRY.size() == 0) {
            Toast.makeText(AdmissionCurrentYearBarChart.this, "Response: No Data Found", Toast.LENGTH_LONG).show();
        } else {
            Bardataset = new BarDataSet(BARENTRY, "Admission Count");
            BARDATA = new BarData(BarEntryLabels, Bardataset);
            Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
            chart.setData(BARDATA);
            chart.animateY(3000);
        }
    }

    @Override
   public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Intent intent = new Intent(AdmissionCurrentYearBarChart.this, DeptWiseAdmissionBarChart.class);
        intent.putExtra("OfficeId", Integer.parseInt(e.getData().toString()));
        startActivity(intent);

        Toast.makeText(AdmissionCurrentYearBarChart.this, "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + h.getDataSetIndex() + " Data: " + e.getData(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected() {

    }

    /** callback for each new entry drawn with the finger */
    @Override
    public void onEntryAdded(Entry entry) {
        Log.i(Chart.LOG_TAG, entry.toString());
    }

    /** callback when a DataSet has been drawn (when lifting the finger) */
    @Override
    public void onDrawFinished(DataSet<?> dataSet) {
        Log.i(Chart.LOG_TAG, "DataSet drawn. " + dataSet.toSimpleString());
        // prepare the legend again
        chart.getLegendRenderer().computeLegend(chart.getData());
    }

    @Override
    public void onEntryMoved(Entry entry) {
        Log.i(Chart.LOG_TAG, "Point moved " + entry.toString());
    }

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
            try {
                JSONArray temp = new JSONArray(ResultString.toString());
                BARENTRY = new ArrayList<>();
                BarEntryLabels = new ArrayList<String>();

                for (int i = 0; i <= temp.length() - 1; i++) {
                    JSONObject object = new JSONObject(temp.getJSONObject(i).toString());
                    BarEntryLabels.add(object.getString("officename"));
                    BARENTRY.add(new BarEntry(Float.parseFloat(object.getString("admissioncnt")), i, object.getInt("officeid")));
                }
                displayBarChart();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
package com.example.fishbowlapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class page1_1Activity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    BarChart NtuBarchart;
    DBHelper dbHelper;
    Spinner SelTime;
    RadioButton RBNtu,RBPh;
    RadioGroup RGntuph;
    List<LinkedHashMap<String,Object>> jsonList;
    List<LinkedHashMap<String,Object>> jsonList2;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1_1);
        setTitle("그래프");
        NtuBarchart = findViewById(R.id.NtuBarchart);
        RBNtu = findViewById(R.id.RBNtu);
        RBPh = findViewById(R.id.RBPh);
        RGntuph = findViewById(R.id.RGntuph);
        RGntuph.setOnCheckedChangeListener(this);
        SelTime = findViewById(R.id.SelTime);
        dbHelper = new DBHelper(page1_1Activity.this,1);
        final String[] date = {"최근 24시간 수온","최근 7일 수온"};
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,date);
        SelTime.setAdapter(adapter);
        final ArrayList<BarEntry> entries = new ArrayList<>();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                URL githubEndpoint = null;
                StringBuilder sb;
                try {
                    sb = new StringBuilder();
                    githubEndpoint = new URL("http://127.0.0.1:8080/data/ntu");
                    HttpURLConnection myConnection =
                            (HttpURLConnection) githubEndpoint.openConnection();
                    myConnection.setRequestMethod("GET");
                    if (myConnection.getResponseCode() == 200) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                myConnection.getInputStream(), "utf-8"
                        ));
                        String line;
                        while((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        br.close();
                        JSONObject jsonObject = new JSONObject(sb.toString());
                        JSONArray jsonObject1 = jsonObject.getJSONArray("data");
                        jsonList = new ArrayList<>();
                        for(int i=0;i<jsonObject1.length(); i++) {
                            JSONObject jsonObject2 = new JSONObject(String.valueOf(jsonObject1.getJSONObject(i)));
                            jsonList.add(toMap(jsonObject2));
                        }
                        myConnection.disconnect();
                        float[] arrdata = arraydata(jsonList,24);
                        //j = arrdata.length;
                        for (int i = 0; i < arrdata.length; i++) {
                            entries.add(new BarEntry(i+1, arrdata[i]));
                            //j--;
                        }

                        BarDataSet barDataSet = new BarDataSet(entries, "NTU");
                        barDataSet.setDrawValues(false);

                        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                        dataSets.add(barDataSet);

                        BarData data = new BarData(dataSets);
                        NtuBarchart.setData(data);
                        NtuBarchart.setFitBars(true);

                        NtuBarchart.getDescription().setEnabled(false);
                        NtuBarchart.setMaxVisibleValueCount(60);
                        NtuBarchart.setPinchZoom(false);
                        NtuBarchart.setDrawBarShadow(false);
                        NtuBarchart.setDrawGridBackground(false);
                        XAxis xAxis = NtuBarchart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);

                        YAxis YLAxis = NtuBarchart.getAxisLeft();
                        YAxis YRAxis = NtuBarchart.getAxisRight();
                        YLAxis.setAxisMinimum(0);
                        YRAxis.setAxisMinimum(0);

                        NtuBarchart.getAxisLeft().setDrawGridLines(false);

                        NtuBarchart.animateY(1500);
                        NtuBarchart.getLegend().setEnabled(false);
                        NtuBarchart.setTouchEnabled(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    sb = new StringBuilder();
                    githubEndpoint = new URL("http://127.0.0.1ss:8080/data/ph");
                    HttpURLConnection myConnection =
                            (HttpURLConnection) githubEndpoint.openConnection();
                    myConnection.setRequestMethod("GET");
                    if (myConnection.getResponseCode() == 200) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                myConnection.getInputStream(), "utf-8"
                        ));
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        br.close();
                        JSONObject jsonObject = new JSONObject(sb.toString());
                        JSONArray jsonObject1 = jsonObject.getJSONArray("data");
                        jsonList2 = new ArrayList<>();
                        for (int i = 0; i < jsonObject1.length(); i++) {
                            JSONObject jsonObject2 = new JSONObject(String.valueOf(jsonObject1.getJSONObject(i)));
                            jsonList2.add(toMap(jsonObject2));
                        }
                        myConnection.disconnect();
                    }
                }catch (Exception e ) {
                    e.printStackTrace();
                }
            }
        });





    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedid) {
        if (RGntuph == radioGroup) {
            if (checkedid == R.id.RBNtu) {
                ArrayList<BarEntry> entries = new ArrayList<>();
                float[] arrdata = arraydata(jsonList,24);
                //j = arrdata.length;
                for (int i = 0; i < arrdata.length; i++) {
                    entries.add(new BarEntry(i+1, arrdata[i]));
                    //j--;
                }

                BarDataSet barDataSet = new BarDataSet(entries, "NTU");
                barDataSet.setDrawValues(false);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(barDataSet);

                BarData data = new BarData(dataSets);
                NtuBarchart.setData(data);
                NtuBarchart.setFitBars(true);

                NtuBarchart.getDescription().setEnabled(false);
                NtuBarchart.setMaxVisibleValueCount(60);
                NtuBarchart.setPinchZoom(false);
                NtuBarchart.setDrawBarShadow(false);
                NtuBarchart.setDrawGridBackground(false);
                XAxis xAxis = NtuBarchart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);

                YAxis YLAxis = NtuBarchart.getAxisLeft();
                YAxis YRAxis = NtuBarchart.getAxisRight();
                YLAxis.setAxisMinimum(0);
                YRAxis.setAxisMinimum(0);

                NtuBarchart.getAxisLeft().setDrawGridLines(false);

                NtuBarchart.animateY(1500);
                NtuBarchart.getLegend().setEnabled(false);
                NtuBarchart.setTouchEnabled(false);
        }
            if (checkedid == R.id.RBPh) {
                ArrayList<BarEntry> entries = new ArrayList<>();
                float[] arrdata = arraydata(jsonList2,24);
                //j = arrdata.length;
                for (int i = 0; i < arrdata.length; i++) {
                    entries.add(new BarEntry(i+1, arrdata[i]));
                    //j--;
                }

                BarDataSet barDataSet = new BarDataSet(entries, "NTU");
                barDataSet.setDrawValues(false);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(barDataSet);

                BarData data = new BarData(dataSets);
                NtuBarchart.setData(data);
                NtuBarchart.setFitBars(true);

                NtuBarchart.getDescription().setEnabled(false);
                NtuBarchart.setMaxVisibleValueCount(60);
                NtuBarchart.setPinchZoom(false);
                NtuBarchart.setDrawBarShadow(false);
                NtuBarchart.setDrawGridBackground(false);
                XAxis xAxis = NtuBarchart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);

                YAxis YLAxis = NtuBarchart.getAxisLeft();
                YAxis YRAxis = NtuBarchart.getAxisRight();
                YLAxis.setAxisMinimum(0);
                YRAxis.setAxisMinimum(0);

                NtuBarchart.getAxisLeft().setDrawGridLines(false);

                NtuBarchart.animateY(1500);
                NtuBarchart.getLegend().setEnabled(false);
                NtuBarchart.setTouchEnabled(false);
            }
        }
    }

    public LinkedHashMap<String,Object> toMap(JSONObject object) throws JSONException {
        LinkedHashMap<String,Object> map = new LinkedHashMap<>();

        Iterator<String> keyItr = object.keys();
        while(keyItr.hasNext()) {
            String key = keyItr.next();
            Object value = object.get(key);

            map.put(key,value);
        }
        return map;
    }

    public float[] arraydata(List<LinkedHashMap<String,Object>> jsonList,int date) {
        float[] data = new float[date];
        for(int x=0;x < jsonList.size(); x++) {
            if(x>date) {break;}
            data[x] = Float.valueOf(jsonList.get(x).get("sensorValue").toString());
        }
        return data;
    }
}

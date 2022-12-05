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
                    githubEndpoint = new URL("http://192.168.0.8:8080/data/ntu");
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
                        for (int i = 0; i < jsonObject1.length(); i++) {
                            JSONArray jsonObject2 = jsonObject1.getJSONArray(i);
                            LinkedHashMap<String,Object> temp = new LinkedHashMap<>();
                            temp.put("ntu",jsonObject2.get(0));
                            jsonList.add(temp);
                        }
                        myConnection.disconnect();
                        double[] arrdata = arraydataDouble(jsonList,24,1);
                        //j = arrdata.length;
                        for (int i = 0; i < arrdata.length; i++) {
                            entries.add(new BarEntry(i+1, (float)arrdata[i]));
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
                        YLAxis.setAxisMinimum((float) 2.4);
                        YRAxis.setAxisMinimum((float) 2.4);

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
                    githubEndpoint = new URL("http://192.168.0.8:8080/data/ph");
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
                            JSONArray jsonObject2 = jsonObject1.getJSONArray(i);
                            LinkedHashMap<String,Object> temp = new LinkedHashMap<>();
                            temp.put("ph",jsonObject2.get(0));
                            jsonList2.add(temp);
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
                double[] arrdata = arraydataDouble(jsonList,24,1);
                //j = arrdata.length;
                for (int i = 0; i < arrdata.length; i++) {
                    entries.add(new BarEntry(i+1, (float) arrdata[i]));
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
                YLAxis.setAxisMinimum((float) 2.4);
                YRAxis.setAxisMinimum((float) 2.4);

                NtuBarchart.getAxisLeft().setDrawGridLines(false);

                NtuBarchart.animateY(1500);
                NtuBarchart.getLegend().setEnabled(false);
                NtuBarchart.setTouchEnabled(false);
        }
            if (checkedid == R.id.RBPh) {
                ArrayList<BarEntry> entries = new ArrayList<>();
                double[] arrdata = arraydataDouble(jsonList2,24,2);
                //j = arrdata.length;
                for (int i = 0; i < arrdata.length; i++) {
                    entries.add(new BarEntry(i+1, (float) arrdata[i]));
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
                YLAxis.setAxisMinimum((float) 2.4);
                YRAxis.setAxisMinimum((float) 2.4);

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

    public double[] arraydataDouble(List<LinkedHashMap<String,Object>> jsonList, int date,int index) {
        double[] data = new double[date];
        for (int x = 0; x < jsonList.size(); x++) {
            if (x > date-1) {
                break;
            }
            double aDouble = 0.00;
            if(jsonList.get(x).get("ntu") instanceof Integer && index == 1) {
                aDouble = (int) jsonList.get(x).get("ntu");
            }else if(index == 1) {
                aDouble = (double) jsonList.get(x).get("ntu");
            }
            if(jsonList.get(x).get("ph") instanceof Integer && index == 2) {
                aDouble = (int) jsonList.get(x).get("ph");
            }else if(index == 2) {
                aDouble = (double) jsonList.get(x).get("ph");
            }
            try {
                data[x] =  aDouble;
            }catch(ClassCastException e) {

            }
        }
        return data;
    }
}

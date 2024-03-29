package com.example.fishbowlapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class page3Activity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    com.github.mikephil.charting.charts.LineChart Tempchart;
    TextView hourtemp[] = new TextView[6];
    int hourtempid[] = {R.id.hour4,R.id.hour8,R.id.hour12,R.id.hour16,R.id.hour20,R.id.hour24};
    TextView tempresult,maxtemp,mintemp,avgtemp;
    TextView TextTemp,Text4hour,Text8hour,Text12hour,Text16hour,Text20hour,Text24hour,TextAvgTemp,TextMaxTemp,TextMinTemp;
    DBHelper dbHelper;
    LinearLayout grapadd,tempinformation;
    Spinner Tempdate;
    RadioGroup RGtemp;
    RadioButton RBgrap,RBinfor;
    MyMarkerView marker;
    LayoutInflater inflater;
    View header;
    double [] arrdata;
    double[] TempWeeksArray;
    double[] TempWeeks = new double[7];
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    List<LinkedHashMap<String, Object>> map;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        setTitle("온도정보");
        final String[] date = {"최근 24시간 수온","최근 7일 수온"};
        grapadd = findViewById(R.id.grapadd);
        inflater = getLayoutInflater();
        header = inflater.inflate(R.layout.tempinformation,null);
        tempinformation = header.findViewById(R.id.tempinformation);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT /* layout_width */, LinearLayout.LayoutParams.MATCH_PARENT /* layout_height */, 1f /* layout_weight */);
        Tempchart = new com.github.mikephil.charting.charts.LineChart(this);
        Tempchart.setLayoutParams(layoutParams);
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        Tempdate = findViewById(R.id.Tempdate);
        RGtemp = findViewById(R.id.RGtemp);
        RGtemp.setOnCheckedChangeListener(this);
        RBgrap = findViewById(R.id.RBgrap);
        RBinfor = findViewById(R.id.RBinfor);
        for(int i=0; i<hourtempid.length;i++) {
            hourtemp[i] = header.findViewById(hourtempid[i]);
        }
        tempresult = header.findViewById(R.id.tempresult);
        maxtemp = header.findViewById(R.id.maxtemp);
        mintemp = header.findViewById(R.id.mintemp);
        avgtemp = header.findViewById(R.id.avgtemp);
        TextTemp = header.findViewById(R.id.TextTemp);
        Text4hour = header.findViewById(R.id.Text4hour);
        Text8hour = header.findViewById(R.id.Text8hour);
        Text12hour = header.findViewById(R.id.Text12hour);
        Text16hour = header.findViewById(R.id.Text16hour);
        Text20hour = header.findViewById(R.id.Text20hour);
        Text24hour = header.findViewById(R.id.Text24hour);
        TextAvgTemp = header.findViewById(R.id.TextAvgTemp);
        TextMaxTemp = header.findViewById(R.id.TextMaxTemp);
        TextMinTemp = header.findViewById(R.id.TextMinTemp);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,date);
        Tempdate.setAdapter(adapter);
        dbHelper = new DBHelper(page3Activity.this,1);
        //arrdata = dbHelper.latelyTem(24);
        arrdata = arraydataDouble(((MainActivity)MainActivity.context_main).jsonList2,24);

        //TempWeeksArray = dbHelper.latelyTem(168);
        TempWeeksArray = arraydataDouble(((MainActivity)MainActivity.context_main).jsonList2,168);

        int i=0,j=0,count=1;
        double sum=0;
        while(j != 7) {
            if(TempWeeksArray[i] == 0) { break; }
            sum = sum + TempWeeksArray[i];
            if(count==24) {
                TempWeeks[j] = sum / count;
                count=1;
                j++;
                sum=0;
            }
            count++;
            i++;
        }




        Tempdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RBgrap.setChecked(false);
                RBinfor.setChecked(false);
                if(i == 0) {
                    editor.putInt("Spinner24", 0);
                    editor.apply();
                    grapadd.removeAllViews();
                }
                if(i == 1) {
                    editor.putInt("Spinner24", 1);
                    editor.apply();
                    grapadd.removeAllViews();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedid) {
        if (RGtemp == radioGroup) {
            switch(checkedid) {
                case R.id.RBgrap:
                    grapadd.removeAllViews();

                    List<Entry> entries = new ArrayList<>();
                    if (pref.getInt("Spinner24", 0) == 1) {
                        for (int i = 0; i < TempWeeks.length; i++) {
                            entries.add((new Entry(i + 1, (float) TempWeeks[i])));
                        }
                    }
                    else if (pref.getInt("Spinner24", 0) == 0) {
                        for (int i = 0; i < arrdata.length; i++) {
                            entries.add((new Entry(i+1,(float) arrdata[i])));
                        }
                    }
                    LineDataSet lineDataSet = new LineDataSet(entries,"수온");
                    lineDataSet.setLineWidth(3);
                    lineDataSet.setCircleRadius(6);
                    lineDataSet.setDrawValues(false);
                    lineDataSet.setDrawCircleHole(true);
                    lineDataSet.setDrawCircles(true);
                    lineDataSet.setDrawHorizontalHighlightIndicator(false);
                    lineDataSet.setDrawHighlightIndicators(false);
                    lineDataSet.setColor(Color.rgb(255, 155, 155));
                    lineDataSet.setCircleColor(Color.rgb(255, 155, 155));
                    LineData lineData = new LineData(lineDataSet);
                    Tempchart.setData(lineData);

                    XAxis xAxis = Tempchart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1f);
                    xAxis.setTextSize(14f);
                    xAxis.setTextColor(Color.rgb(118, 118, 118));
                    YAxis yLAxis = Tempchart.getAxisLeft();
                    yLAxis.setTextColor(Color.BLACK);

                    YAxis yRAxis = Tempchart.getAxisRight();
                    yRAxis.setDrawLabels(false);
                    yRAxis.setDrawAxisLine(false);
                    yRAxis.setDrawGridLines(false);

                    if(pref.getInt("Spinner24",0) == 1 ) {
                        yLAxis.setAxisMinimum(20);
                        yRAxis.setAxisMinimum(20);
                    }
                    else if(pref.getInt("Spinner24",0) == 0) {
                        yLAxis.setAxisMinimum(20);
                        yRAxis.setAxisMinimum(20);
                    }
                    Description description = new Description();
                    description.setText("");

                    Tempchart.setDoubleTapToZoomEnabled(false);
                    Tempchart.setDrawGridBackground(false);
                    Tempchart.setDescription(description);
                    Tempchart.animateY(2000);
                    Tempchart.invalidate();
                    Tempchart.getLegend().setTextSize(18);
                    Tempchart.getLegend().setFormSize(15);
                    MyMarkerView mv1 = new MyMarkerView(getApplicationContext(),R.layout.custom_marker_view);
                    mv1.setChartView(Tempchart);
                    Tempchart.setMarker(mv1);
                    grapadd.addView(Tempchart);
                    break;

                case R.id.RBinfor:
                    DecimalFormat df = new DecimalFormat("0.00");
                    if(pref.getInt("Spinner24",0) == 1 ) {
                        Text4hour.setText("최근1일전");
                        Text8hour.setText("최근2일전");
                        Text12hour.setText("최근3일전");
                        Text16hour.setText("최근4일전");
                        Text20hour.setText("최근5일전");
                        Text24hour.setText("최근6일전");
                        TextAvgTemp.setText("7일 내 평균온도: ");
                        TextMaxTemp.setText("7일 내 최고온도: ");
                        TextMinTemp.setText("7일 내 최저온도: ");
                        TextTemp.setText("최근1일 내 평균온도: ");
                    }
                    else if(pref.getInt("Spinner24",0) == 0) {
                        Text4hour.setText("4시간전");
                        Text8hour.setText("8시간전");
                        Text12hour.setText("12시간전");
                        Text16hour.setText("16시간전");
                        Text20hour.setText("20시간전");
                        Text24hour.setText("24시간전");
                        TextAvgTemp.setText("24시간 내 평균온도: ");
                        TextMaxTemp.setText("24시간 내 최고온도: ");
                        TextMinTemp.setText("24시간 내 최저온도: ");
                        TextTemp.setText("현재온도: ");
                    }
                    if(pref.getInt("Spinner24",0) == 1 ) {
                        double max = 0, min = 100, sum = 0;
                        double avg;
                        grapadd.removeAllViews();
                        grapadd.addView(tempinformation);
                        for(int i=0; i<TempWeeks.length; i++) {
                                sum += TempWeeks[i];
                                max = Math.max(TempWeeks[i],max);
                                min = Math.min(TempWeeks[i],min);
                        }
                        tempresult.setText(df.format(TempWeeks[0]) + "도");
                            avg = sum / TempWeeks.length;
                            maxtemp.setText(df.format(max)+"도");
                            mintemp.setText(df.format(min)+"도");
                            avgtemp.setText(df.format(avg)+"도");
                        for(int i=0; i<TempWeeks.length-1; i++) {
                            hourtemp[i].setText(df.format(TempWeeks[i]));

                        }
                    }
                    else if(pref.getInt("Spinner24",0) == 0 ) {
                        double max = 0, min = 100, sum = 0;
                        double avg;
                        grapadd.removeAllViews();
                        grapadd.addView(tempinformation);
                        for (int i = 0; i < arrdata.length; i++) {
                            sum += arrdata[i];
                            max = Math.max(arrdata[i], max);
                            min = Math.min(arrdata[i], min);
                        }
                        avg = sum / arrdata.length;
                        tempresult.setText(df.format(arrdata[0]) + "도");
                        avg = sum / arrdata.length;
                        maxtemp.setText(df.format(max)+"도");
                        mintemp.setText(df.format(min)+"도");
                        avgtemp.setText(df.format(avg)+"도");
                        for (int i = 3; i < arrdata.length; i = i + 4) {
                            hourtemp[i / 4].setText(df.format(arrdata[i]));
                        }
                    }

                    break;

            }
        }
    }

    public double[] arraydataDouble(List<LinkedHashMap<String,Object>> jsonList, int date) {
        double[] data = new double[date];
        for (int x = 0; x < jsonList.size(); x++) {
            if (x > date-1) {
                break;
            }
            double aDouble = 0.00;
            if(jsonList.get(x).get("temp") instanceof Integer) {
                aDouble = (int) jsonList.get(x).get("temp");
            }else {
                aDouble = (double) jsonList.get(x).get("temp");
            }
            System.out.println(aDouble);
            try {
                data[x] =  aDouble;
            }catch(ClassCastException e) {

            }
        }
        return data;
    }
}
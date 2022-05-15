package com.example.fishbowlapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

public class page1_1Activity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    BarChart NtuBarchart;
    DBHelper dbHelper;
    Spinner SelTime;
    RadioButton RBNtu,RBPh;
    RadioGroup RGntuph;
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
        ArrayList<BarEntry> entries = new ArrayList<>();
        int[] arrdata = dbHelper.latelyNtu(24);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedid) {
        if (RGntuph == radioGroup) {
            if (checkedid == R.id.RBNtu) {
                ArrayList<BarEntry> entries = new ArrayList<>();
            int[] arrdata = dbHelper.latelyNtu(24);
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
                int[] arrdata = dbHelper.latelyPh(24);
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
}

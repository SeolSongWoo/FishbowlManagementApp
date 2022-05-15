package com.example.fishbowlapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

import static com.example.fishbowlapplication.StartActivity.BT_MESSAGE_READ;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout Mpage1, Mpage2, Mpage3, Mpage4, Mpage5,Mpage6, DBTEST;
    TextView Nturesult,Phresult,Temresult,TempRange,PhRange,SetNtu;
    DBHelper dbHelper;
    Class ActivityClass;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String[] array;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        dbHelper = new DBHelper(MainActivity.this, 1);
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        //Nturesult.setText(dbHelper.LastNtuResult());
        //Temresult.setText(String.valueOf(dbHelper.LastTemResult())+"℃");
        Phresult.setText(String.valueOf(dbHelper.LastPhResult()));

        SetNtu.setText(String.valueOf(dbHelper.LastNtuResult2()));
        TempRange.setText("적정수온("+pref.getInt("SetTemp1",0)+"~"+pref.getInt("SetTemp2",0)+")");
        PhRange.setText("적정PH("+pref.getInt("SetPh1",0)+"~"+pref.getInt("SetPh2",0)+")");


        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mp);
        setTitle("어항");
        Mpage1 = findViewById(R.id.Mpage1);
        Mpage2 = findViewById(R.id.Mpage2);
        Mpage3 = findViewById(R.id.Mpage3);
        Mpage4 = findViewById(R.id.Mpage4);
        Mpage5 = findViewById(R.id.Mpage5);
        Mpage6 = findViewById(R.id.Mpage6);
        DBTEST = findViewById(R.id.DBTEST);
        Nturesult = findViewById(R.id.Nthresult);
        Phresult = findViewById(R.id.Phresult);
        Temresult = findViewById(R.id.Temresult);
        TempRange = findViewById(R.id.TempRange);
        PhRange = findViewById(R.id.PhRange);
        SetNtu = findViewById(R.id.SetNtu);
        Mpage1.setOnClickListener(this);
        Mpage2.setOnClickListener(this);
        Mpage3.setOnClickListener(this);
        Mpage4.setOnClickListener(this);
        Mpage5.setOnClickListener(this);
        Mpage6.setOnClickListener(this);
        DBTEST.setOnClickListener(this);

        ((StartActivity) StartActivity.context).mBluetoothHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == BT_MESSAGE_READ) {
                    String readMessageNtu = null;
                    try {
                        readMessageNtu = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    array = readMessageNtu.split(",");
                    Nturesult.setText(array[0]);
                    Temresult.setText(array[1]);
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Mpage1:
                ActivityClass = page1Activity.class;
                break;
            case R.id.Mpage2:
                ActivityClass = page2Activity.class;
                break;
            case R.id.Mpage3:
                ActivityClass = page3Activity.class;
                break;
            case R.id.Mpage4:
                ActivityClass = page4Activity.class;
                break;
            case R.id.Mpage5:
                ActivityClass = page5Activity.class;
                break;
            case R.id.Mpage6:
                ActivityClass = page6Activity.class;
                break;
            case R.id.DBTEST:
                ActivityClass = DBTESTActivity.class;
                break;
        }
        Intent intent = new Intent(getApplicationContext(), ActivityClass);
        startActivity(intent);
    }
}

package com.example.fishbowlapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class page1Activity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout Mpage1_1,Mpage1_2,Mpage1_3;
    DBHelper dbHelper;
    Class ActivityClass;
    TextView Nturesult2,Phresult2;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String[] array;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        dbHelper = new DBHelper(page1Activity.this, 1);
        Nturesult2.setText(((MainActivity)MainActivity.context_main).SetNtu.getText());
        Phresult2.setText(((MainActivity)MainActivity.context_main).Phresult.getText());
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        setTitle("수질관리");
        Mpage1_1 = findViewById(R.id.Mpage1_1);
        Mpage1_2 = findViewById(R.id.Mpage1_2);
        Mpage1_3 = findViewById(R.id.Mpage1_3);
        Nturesult2 = findViewById(R.id.Nthresult2);
        Phresult2 = findViewById(R.id.Phresult2);
        Mpage1_1.setOnClickListener(this);
        Mpage1_2.setOnClickListener(this);
        Mpage1_3.setOnClickListener(this);
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Mpage1_1:
                ActivityClass = page1_1Activity.class;
                break;
            case R.id.Mpage1_2:
                ActivityClass = page1_2Activity.class;
                break;
            case R.id.Mpage1_3:
                AlertDialog.Builder dlg = new AlertDialog.Builder(page1Activity.this);
                if(pref.getInt("CycleRun",0) == 0) {
                    dlg.setTitle("순환");
                    dlg.setMessage("순환을 시작하시겠습니까?");
                    dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            StringBuilder sb = new StringBuilder();
                                            URL githubEndpoint = new URL("http://192.168.0.2/waterpumpon");
                                            HttpURLConnection myConnection =
                                                    (HttpURLConnection) githubEndpoint.openConnection();
                                            myConnection.setRequestMethod("GET");
                                            if (myConnection.getResponseCode() == 200) {
                                                myConnection.disconnect();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        }
                    });
                    dlg.setNegativeButton("아니요", null);
                    dlg.show();
                    editor.putInt("CycleRun", 1);
                    editor.apply();
                    break;
                }
                else {
                    dlg.setTitle("순환");
                    dlg.setMessage("순환을 중지하시겠습니까?");
                    dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        StringBuilder sb = new StringBuilder();
                                        URL githubEndpoint = new URL("http://192.168.0.2/waterpumpoff");
                                        HttpURLConnection myConnection =
                                                (HttpURLConnection) githubEndpoint.openConnection();
                                        myConnection.setRequestMethod("GET");
                                        if (myConnection.getResponseCode() == 200) {
                                            myConnection.disconnect();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                    dlg.setNegativeButton("아니요", null);
                    dlg.show();
                    editor.putInt("CycleRun", 0);
                    editor.apply();
                    break;
                }
        }
        if(view.getId() == R.id.Mpage1_1 || view.getId() == R.id.Mpage1_2) {
            Intent intent = new Intent(getApplicationContext(), ActivityClass);
            startActivity(intent);
        }
    }
}

package com.example.fishbowlapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static Context context_main;

    LinearLayout Mpage1, Mpage2, Mpage3, Mpage4, Mpage5,Mpage6;
    TextView Nturesult,Phresult,Temresult,TempRange,PhRange,SetNtu;
    DBHelper dbHelper;
    Class ActivityClass;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    List<LinkedHashMap<String,Object>> jsonList2;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                URL githubEndpoint = null;
                StringBuilder sb;
                try {
                    sb = new StringBuilder();
                    githubEndpoint = new URL("http://192.168.1.247:8080/data/temp/lastdata");
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
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        Temresult.setText(jsonObject1.getString("sensorValue"));
                        myConnection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    sb = new StringBuilder();
                    githubEndpoint = new URL("http://192.168.1.247:8080/data/ph/lastdata");
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
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        Phresult.setText(jsonObject1.getString("sensorValue"));
                        myConnection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    sb = new StringBuilder();
                    githubEndpoint = new URL("http://192.168.1.247:8080/data/ntu/lastdata");
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
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        SetNtu.setText(jsonObject1.getString("sensorValue"));
                        myConnection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    sb = new StringBuilder();
                    githubEndpoint = new URL("http://192.168.1.247:8080/data/temp");
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
                        jsonList2 = new ArrayList<>();
                        for (int i = 0; i < jsonObject1.length(); i++) {
                            JSONObject jsonObject2 = new JSONObject(String.valueOf(jsonObject1.getJSONObject(i)));
                            jsonList2.add(toMap(jsonObject2));
                        }

                        myConnection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        dbHelper = new DBHelper(MainActivity.this, 1);
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        Nturesult.setText("높음(순환필요)");

        //SetNtu.setText(String.valueOf(dbHelper.LastNtuResult2()));
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
        //DBTEST.setOnClickListener(this);
        //btn.setOnClickListener(this);

        context_main = this;

    }

    @Override
    public void onClick(View view) {
        int x=0;
        switch (view.getId()) {
            case R.id.Mpage1:
                x++;
                ActivityClass = page1Activity.class;
                break;
            case R.id.Mpage2:
                x++;
                ActivityClass = page2Activity.class;
                break;
            case R.id.Mpage3:
                x++;
                ActivityClass = page3Activity.class;
                break;
            case R.id.Mpage4:
                x++;
                ActivityClass = page4Activity.class;
                break;
            case R.id.Mpage5:
                x++;
                ActivityClass = page5Activity.class;
                break;
            case R.id.Mpage6:
                x++;
                ActivityClass = page6Activity.class;
                break;
        }
        if(x!=0) {
            Intent intent = new Intent(getApplicationContext(), ActivityClass);
            startActivity(intent);
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
}

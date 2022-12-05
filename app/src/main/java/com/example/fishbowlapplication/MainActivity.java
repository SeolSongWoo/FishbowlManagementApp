package com.example.fishbowlapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fishbowlapplication.Api.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

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
                    githubEndpoint = new URL("http://192.168.0.2/");
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
                        sb.deleteCharAt(sb.length()-2);
                        final JSONObject jsonObject = new JSONObject(sb.toString());
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                try {
                                    SetNtu.setText(jsonObject.getString("turbidity"));
                                    Temresult.setText(jsonObject.getString("temp"));
                                    Phresult.setText(jsonObject.getString("PH"));
                                    double ntu = Double.parseDouble(SetNtu.getText().toString());
                                    if(ntu > 2.0) {
                                        Nturesult.setText("높음(순환필요)");
                                    }else {
                                        Nturesult.setText("좋음");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        myConnection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    sb = new StringBuilder();
                    githubEndpoint = new URL("http://192.168.0.8:8080/data/temp");
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
                            JSONArray jsonObject2 = jsonObject1.getJSONArray(i);
                            LinkedHashMap<String,Object> temp = new LinkedHashMap<>();
                            temp.put("temp",jsonObject2.get(0));
                            jsonList2.add(temp);
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

        ContentValues map = new ContentValues();
        map.put("rangeTemp1",pref.getFloat("SetTemp1",0));
        map.put("rangeTemp2",pref.getFloat("SetTemp2",0));
        map.put("rangePh1",pref.getFloat("SetPh1",0));
        map.put("rangePh2",pref.getFloat("SetPh2",0));
        MainActivity.NetworkTask networkTask = new MainActivity.NetworkTask("http://192.168.0.8:8080/data/range/save",map);
        networkTask.execute();
        //SetNtu.setText(String.valueOf(dbHelper.LastNtuResult2()));
        TempRange.setText("적정수온("+pref.getFloat("SetTemp1",0)+"~"+pref.getFloat("SetTemp2",0)+")");
        PhRange.setText("적정PH("+pref.getFloat("SetPh1",0)+"~"+pref.getFloat("SetPh2",0)+")");

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

            map.put("temp",value);
        }
        return map;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
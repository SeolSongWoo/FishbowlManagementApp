package com.example.fishbowlapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class DBTESTActivity extends AppCompatActivity implements View.OnClickListener {
    Button pushData,thirtydatainput;
    TextView NtuData,PhData,TemData,DBntudata,DBphdata,DBtemdata;
    DBHelper dbHelper;
    int[] Data = new int[3];
    String test;
    URLConnector task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);
        pushData = findViewById(R.id.pushData);
        NtuData = findViewById(R.id.NtuData);
        PhData = findViewById(R.id.Phdata);
        TemData = findViewById(R.id.Temdata);
        DBntudata = findViewById(R.id.DBntudata);
        DBphdata = findViewById(R.id.DBphdata);
        DBtemdata = findViewById(R.id.DBtemdata);
        thirtydatainput = findViewById(R.id.thirtydatainput);
        pushData.setOnClickListener(this);
        thirtydatainput.setOnClickListener(this);
        Gson gson = new Gson();
        ArrayList<Data> list_album = new ArrayList<>();
        /*
        dbHelper = new DBHelper(DBTESTActivity.this,1);
        DBntudata.setText(dbHelper.getResultNtu());
        DBphdata.setText(dbHelper.getResultPh());
        DBtemdata.setText(dbHelper.getResultTem());
        */
        test = "http://112.161.172.100/ab.php"; // 안드로이드는 mysql에 직접 접근이 불가능하여, php를 통해 테이블 정보를 가져옴.
        task = new URLConnector(test);
        task.start();
        try{
            task.join();
            System.out.println("waiting... for result");
            JSONObject json = new JSONObject();
            json = task.getResult(); // json으로 데이터를 가져옴
            JSONArray jsonArray = json.getJSONArray("result"); // 테이블(sensor)의 정보들이 json으로 담겨져있음. result키의 데이터들만 파싱함.
            int index = 0;
            while (index < jsonArray.length()) { // 키,값을 뽑기위해 ArrayList로 파싱
                Data datata = gson.fromJson(jsonArray.get(index).toString(), Data.class);
                list_album.add(datata);

                index++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        String Ntudata = "";
        String Tempdata = "";
        String phdata = "";

        for(int i=0; i<list_album.size(); i++) {
            Ntudata += list_album.get(i).turbidity()+"\n";
            Tempdata += list_album.get(i).temp()+"\n";
            phdata += list_album.get(i).ph()+"\n";
        }
        DBntudata.setText(Ntudata);
        DBtemdata.setText(Tempdata);
        DBphdata.setText(phdata);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pushData:
                Data[0] = Integer.parseInt(NtuData.getText().toString().trim());
                Data[1] = Integer.parseInt(PhData.getText().toString().trim());
                Data[2] = Integer.parseInt(TemData.getText().toString().trim());
                dbHelper.insert(Data[0],Data[1],Data[2]);
                DBntudata.setText(dbHelper.getResultNtu());
                DBphdata.setText(dbHelper.getResultPh());
                DBtemdata.setText(dbHelper.getResultTem());
                break;
            case R.id.thirtydatainput:
                for(int i=0; i<30; i++) {
                    int rannum = new Random().nextInt((1000 - 1)+ 1) + 1;
                    int rannum2 = new Random().nextInt((10 - 1)+ 1) + 1;
                    int rannum3 = new Random().nextInt((30 - 24)+ 1) + 24;
                    dbHelper.insert(rannum,rannum2,rannum3);
                }
                DBntudata.setText(dbHelper.getResultNtu());
                DBphdata.setText(dbHelper.getResultPh());
                DBtemdata.setText(dbHelper.getResultTem());
                break;
        }
    }
}

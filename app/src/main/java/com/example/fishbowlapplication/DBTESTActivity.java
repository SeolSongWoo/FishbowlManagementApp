package com.example.fishbowlapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class DBTESTActivity extends AppCompatActivity implements View.OnClickListener {
    Button pushData,thirtydatainput;
    TextView NtuData,PhData,TemData,DBntudata,DBphdata,DBtemdata;
    DBHelper dbHelper;
    int[] Data = new int[3];
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
        dbHelper = new DBHelper(DBTESTActivity.this,1);
        DBntudata.setText(dbHelper.getResultNtu());
        DBphdata.setText(dbHelper.getResultPh());
        DBtemdata.setText(dbHelper.getResultTem());


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

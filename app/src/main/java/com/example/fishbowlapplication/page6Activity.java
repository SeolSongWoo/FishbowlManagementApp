package com.example.fishbowlapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Set;

public class page6Activity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout Setcase1, Setcase2, Setcase3, Setcase4, Setcase5;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page6);
        Setcase1 = findViewById(R.id.Setcase1);
        Setcase2 = findViewById(R.id.Setcase2);
        Setcase3 = findViewById(R.id.Setcase3);
        Setcase4 = findViewById(R.id.Setcase4);
        Setcase5 = findViewById(R.id.Setcase5);
        Setcase1.setOnClickListener(this);
        Setcase2.setOnClickListener(this);
        Setcase3.setOnClickListener(this);
        Setcase4.setOnClickListener(this);
        Setcase5.setOnClickListener(this);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();


    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(page6Activity.this);
        switch (view.getId()) {
            case R.id.Setcase1:
                dlg.setTitle("케이스1 설정");
                dlg.setMessage("케이스1으로 환경설정 하시겠습니까?\n적정수온 : 21~26도 | 적정PH : 4~6");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putInt("SetTemp1", 21);
                        editor.putInt("SetTemp2", 26);
                        editor.putInt("SetPh1",4);
                        editor.putInt("SetPh2",6);
                        editor.apply();
                        }
                    });
                dlg.setNegativeButton("아니요", null);
                dlg.show();
                break;
            case R.id.Setcase2:
                dlg.setTitle("케이스2 설정");
                dlg.setMessage("케이스2으로 환경설정 하시겠습니까?\n적정수온 : 23~26도 | 적정PH : 4~5");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putInt("SetTemp1", 23);
                        editor.putInt("SetTemp2", 26);
                        editor.putInt("SetPh1",4);
                        editor.putInt("SetPh2",5);
                        editor.apply();
                    }
                });
                dlg.setNegativeButton("아니요", null);
                dlg.show();
                break;
            case R.id.Setcase3:
                dlg.setTitle("케이스3 설정");
                dlg.setMessage("케이스3으로 환경설정 하시겠습니까?\n적정수온 : 19~24도 | 적정PH : 5~7");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putInt("SetTemp1", 19);
                        editor.putInt("SetTemp2", 24);
                        editor.putInt("SetPh1",5);
                        editor.putInt("SetPh2",7);
                        editor.apply();
                    }
                });
                dlg.setNegativeButton("아니요", null);
                dlg.show();
                break;
            case R.id.Setcase4:
                dlg.setTitle("케이스4 설정");
                dlg.setMessage("케이스4으로 환경설정 하시겠습니까?\n적정수온 : 23~25도 | 적정PH : 3~5");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putInt("SetTemp1", 23);
                        editor.putInt("SetTemp2", 25);
                        editor.putInt("SetPh1",3);
                        editor.putInt("SetPh2",5);
                        editor.apply();
                    }
                });
                dlg.setNegativeButton("아니요", null);
                dlg.show();
                break;
            case R.id.Setcase5:
                dlg.setTitle("케이스5 설정");
                dlg.setMessage("케이스5으로 환경설정 하시겠습니까?\n적정수온 : 19~23도 | 적정PH : 6~8");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putInt("SetTemp1", 19);
                        editor.putInt("SetTemp2", 23);
                        editor.putInt("SetPh1",6);
                        editor.putInt("SetPh2",8);
                        editor.apply();
                    }
                });
                dlg.setNegativeButton("아니요", null);
                dlg.show();
                break;
        }
    }
}

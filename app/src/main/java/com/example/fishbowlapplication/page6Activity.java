package com.example.fishbowlapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.PrecomputedTextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fishbowlapplication.Service.AlrimService;

import java.util.Set;
import java.util.zip.Inflater;

public class page6Activity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout Setcase1, Setcase2, Setcase3, Setcase4, Setcase5;
    EditText editPriavteTemp1,editPriavteTemp2,editPriavtePh1,editPriavtePh2;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    LinearLayout dialoginputLayout;
    View header;
    LayoutInflater inflater;
    Button alrimon,alrimoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page6);
        Setcase1 = findViewById(R.id.Setcase1);
        Setcase2 = findViewById(R.id.Setcase2);
        Setcase3 = findViewById(R.id.Setcase3);
        Setcase4 = findViewById(R.id.Setcase4);
        Setcase5 = findViewById(R.id.Setcase5);
        alrimon = findViewById(R.id.AlrimOn);
        alrimoff = findViewById(R.id.AlrimOff);
        inflater = getLayoutInflater();
        header = inflater.inflate(R.layout.dialoginput,null);
        dialoginputLayout = header.findViewById(R.id.dialoginputLayout);
        editPriavtePh1 = header.findViewById(R.id.editPriavtePh1);
        editPriavtePh2 = header.findViewById(R.id.editPriavtePh2);
        editPriavteTemp1 = header.findViewById(R.id.editPriavteTemp1);
        editPriavteTemp2 = header.findViewById(R.id.editPriavteTemp2);
        Setcase1.setOnClickListener(this);
        Setcase2.setOnClickListener(this);
        Setcase3.setOnClickListener(this);
        Setcase4.setOnClickListener(this);
        Setcase5.setOnClickListener(this);
        alrimon.setOnClickListener(this);
        alrimoff.setOnClickListener(this);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();


    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(page6Activity.this);
        switch (view.getId()) {
            case R.id.Setcase1:
                dlg.setTitle("구피 설정");
                dlg.setMessage("구피으로 환경설정 하시겠습니까?\n적정수온 : 22~28도 | 적정PH : 6.8~7.8");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putFloat("SetTemp1", 22);
                        editor.putFloat("SetTemp2", 28);
                        editor.putFloat("SetPh1", (float) 6.8);
                        editor.putFloat("SetPh2", (float) 7.8);
                        editor.apply();
                        }
                    });
                dlg.setNegativeButton("아니요", null);
                dlg.show();
                break;
            case R.id.Setcase2:
                dlg.setTitle("베타 설정");
                dlg.setMessage("베타로 환경설정 하시겠습니까?\n적정수온 : 26~28도 | 적정PH : 6.7~7.5");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putFloat("SetTemp1", 26);
                        editor.putFloat("SetTemp2", 28);
                        editor.putFloat("SetPh1", 6.7F);
                        editor.putFloat("SetPh2",7.5F);
                        editor.apply();
                    }
                });
                dlg.setNegativeButton("아니요", null);
                dlg.show();
                break;
            case R.id.Setcase3:
                dlg.setTitle("네온테트라 설정");
                dlg.setMessage("네온테트라로 환경설정 하시겠습니까?\n적정수온 : 22~30도 | 적정PH : 6~7");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putFloat("SetTemp1", 22);
                        editor.putFloat("SetTemp2", 30);
                        editor.putFloat("SetPh1",6);
                        editor.putFloat("SetPh2",7);
                        editor.apply();
                    }
                });
                dlg.setNegativeButton("아니요", null);
                dlg.show();
                break;
            case R.id.Setcase4:
                dlg.setTitle("플래티 설정");
                dlg.setMessage("플래티로 환경설정 하시겠습니까?\n적정수온 : 22~27도 | 적정PH : 7~8");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putFloat("SetTemp1", 22);
                        editor.putFloat("SetTemp2", 27);
                        editor.putFloat("SetPh1",7);
                        editor.putFloat("SetPh2",8);
                        editor.apply();
                    }
                });
                dlg.setNegativeButton("아니요", null);
                dlg.show();
                break;
            case R.id.Setcase5:
                if(pref.getFloat("PrivateSet",0) == 1F) {
                    dlg.setTitle("개인설정적용");
                    dlg.setMessage("개인설정을 완료하셨습니다.\n해당설정을 적용하시겠습니까?\n적정수온 : "+pref.getFloat("SetPriTemp1",0)+"~"+pref.getFloat("SetPriTemp2",0)+"도 | 적정PH : "+pref.getFloat("SetPriPh1",0)+"~"+pref.getFloat("SetPriPh2",0)+"");
                    dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editor.putFloat("SetTemp1", pref.getFloat("SetPriTemp1",0));
                            editor.putFloat("SetTemp2", pref.getFloat("SetPriTemp2",0));
                            editor.putFloat("SetPh1",pref.getFloat("SetPriPh1",0));
                            editor.putFloat("SetPh2",pref.getFloat("SetPriPh2",0));
                            editor.apply();
                        }
                    });
                    dlg.setNeutralButton("재설정", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder dlg2 = new AlertDialog.Builder(page6Activity.this);
                            dlg2.setTitle("개인설정");
                            if(dialoginputLayout.getParent() != null) ((ViewGroup) dialoginputLayout.getParent()).removeView(dialoginputLayout);
                            dlg2.setView(dialoginputLayout);
                            dlg2.setPositiveButton("완료", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    editor.putFloat("SetPriTemp1", Float.parseFloat(editPriavteTemp1.getText().toString()));
                                    editor.putFloat("SetPriTemp2", Float.parseFloat(editPriavteTemp2.getText().toString()));
                                    editor.putFloat("SetPriPh1",Float.parseFloat(editPriavtePh1.getText().toString()));
                                    editor.putFloat("SetPriPh2",Float.parseFloat(editPriavtePh2.getText().toString()));
                                    editor.apply();
                                }
                            });
                            dlg2.setNegativeButton("취소", null);
                            dlg2.show();
                        }
                    });
                    dlg.setNegativeButton("아니요", null);
                    dlg.show();
                }
                else {
                    dlg.setTitle("개인설정");
                    if(dialoginputLayout.getParent() != null) ((ViewGroup) dialoginputLayout.getParent()).removeView(dialoginputLayout);
                    dlg.setView(dialoginputLayout);
                    dlg.setPositiveButton("완료", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editor.putFloat("SetPriTemp1", Float.parseFloat(editPriavteTemp1.getText().toString()));
                            editor.putFloat("SetPriTemp2", Float.parseFloat(editPriavteTemp2.getText().toString()));
                            editor.putFloat("SetPriPh1",Float.parseFloat(editPriavtePh1.getText().toString()));
                            editor.putFloat("SetPriPh2",Float.parseFloat(editPriavtePh2.getText().toString()));
                            editor.putFloat("PrivateSet",1);
                            editor.apply();
                        }
                    });
                    dlg.setNegativeButton("취소", null);
                    dlg.show();
                }
                break;
            case R.id.AlrimOn:
                Toast.makeText(getApplicationContext(),"이제 설정된 수치를 벗어나면 상태바에 표시됩니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(page6Activity.this, AlrimService.class);
                startService(intent);
                break;

            case R.id.AlrimOff:
                Toast.makeText(getApplicationContext(),"알림이 종료됩니다.", Toast.LENGTH_SHORT).show();
                intent = new Intent(page6Activity.this,AlrimService.class);
                stopService(intent);
                break;
        }
    }
}

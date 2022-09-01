package com.example.fishbowlapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.PrecomputedTextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page6);
        Setcase1 = findViewById(R.id.Setcase1);
        Setcase2 = findViewById(R.id.Setcase2);
        Setcase3 = findViewById(R.id.Setcase3);
        Setcase4 = findViewById(R.id.Setcase4);
        Setcase5 = findViewById(R.id.Setcase5);
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
                if(pref.getInt("PrivateSet",0) == 1) {
                    dlg.setTitle("개인설정적용");
                    dlg.setMessage("개인설정을 완료하셨습니다.\n해당설정을 적용하시겠습니까?\n적정수온 : "+pref.getInt("SetPriTemp1",0)+"~"+pref.getInt("SetPriTemp2",0)+"도 | 적정PH : "+pref.getInt("SetPriPh1",0)+"~"+pref.getInt("SetPriPh2",0)+"");
                    dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editor.putInt("SetTemp1", pref.getInt("SetPriTemp1",0));
                            editor.putInt("SetTemp2", pref.getInt("SetPriTemp2",0));
                            editor.putInt("SetPh1",pref.getInt("SetPriPh1",0));
                            editor.putInt("SetPh2",pref.getInt("SetPriPh2",0));
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
                                    editor.putInt("SetPriTemp1", Integer.parseInt(editPriavteTemp1.getText().toString()));
                                    editor.putInt("SetPriTemp2", Integer.parseInt(editPriavteTemp2.getText().toString()));
                                    editor.putInt("SetPriPh1",Integer.parseInt(editPriavtePh1.getText().toString()));
                                    editor.putInt("SetPriPh2",Integer.parseInt(editPriavtePh2.getText().toString()));
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
                            editor.putInt("SetPriTemp1", Integer.parseInt(editPriavteTemp1.getText().toString()));
                            editor.putInt("SetPriTemp2", Integer.parseInt(editPriavteTemp2.getText().toString()));
                            editor.putInt("SetPriPh1",Integer.parseInt(editPriavtePh1.getText().toString()));
                            editor.putInt("SetPriPh2",Integer.parseInt(editPriavtePh2.getText().toString()));
                            editor.putInt("PrivateSet",1);
                            editor.apply();
                        }
                    });
                    dlg.setNegativeButton("취소", null);
                    dlg.show();
                }
                break;
        }
    }
}

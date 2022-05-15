package com.example.fishbowlapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class page4Activity extends AppCompatActivity {
    ImageView RGBControl;
    View color_view;
    Bitmap bitmap;
    int bitheight,bitwidth,r,g,b;
    Button rgbApply;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);
        setTitle("조명관리");

        RGBControl = findViewById(R.id.RGBControl);
        color_view = findViewById(R.id.color_view);
        rgbApply = findViewById(R.id.rgbApply2);
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        color_view.setBackgroundColor(Color.rgb(pref.getInt("r",0),pref.getInt("g",0),pref.getInt("b",0)));

        RGBControl.setDrawingCacheEnabled(true);
        RGBControl.buildDrawingCache(true);

        RGBControl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                            try {
                                bitheight = bitmap.getHeight();
                                bitwidth = bitmap.getWidth();
                            }catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            bitmap = RGBControl.getDrawingCache();
                            if((int) motionEvent.getY() < bitheight && (int) motionEvent.getY() > 0 && (int) motionEvent.getX() < bitwidth && (int) motionEvent.getX() > 0) {
                                int pixel = bitmap.getPixel((int) motionEvent.getX(), (int) motionEvent.getY());
                                r = Color.red(pixel);
                                g = Color.green(pixel);
                                b = Color.blue(pixel);
                                color_view.setBackgroundColor(Color.rgb(r, g, b));
                            }

                }

                return true;
            }
        });
        rgbApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("r", r);
                editor.putInt("g", g);
                editor.putInt("b", b);
                editor.apply();
            }
        });
    }
}

package com.example.fishbowlapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout Mpage1,Mpage2,Mpage3,Mpage4,Mpage5,DBTEST;
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
        DBTEST = findViewById(R.id.DBTEST);


        Mpage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),page1Activity.class);
                startActivity(intent);
            }
        });

        Mpage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),page2Activity.class);
                startActivity(intent);
            }
        });

        Mpage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),page3Activity.class);
                startActivity(intent);
            }
        });

        Mpage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),page4Activity.class);
                startActivity(intent);
            }
        });

        Mpage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),page5Activity.class);
                startActivity(intent);
            }
        });
        DBTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DBTESTActivity.class);
                startActivity(intent);
            }
        });


    }
}

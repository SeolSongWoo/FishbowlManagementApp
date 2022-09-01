package com.example.fishbowlapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class page5Activity extends AppCompatActivity {
    LinearLayout Murl1,Murl2,Murl3,Murl4,Murl5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page5);
        setTitle("커뮤니티");
        Murl1 = findViewById(R.id.Murl1);
        Murl2 = findViewById(R.id.Murl2);
        Murl3 = findViewById(R.id.Murl3);
        Murl4 = findViewById(R.id.Murl4);
        Murl5 = findViewById(R.id.Murl5);

        Murl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://cafe.naver.com/kts4565"));
                startActivity(urlintent);
            }
        });

        Murl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://cafe.naver.com/purplebtrd1"));
                startActivity(urlintent);
            }
        });

        Murl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://cafe.naver.com/fishbowlstory"));
                startActivity(urlintent);
            }
        });

        Murl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://cafe.naver.com/gupilove"));
                startActivity(urlintent);
            }
        });

        Murl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://cafe.naver.com/hby"));
                startActivity(urlintent);
            }
        });


    }
}

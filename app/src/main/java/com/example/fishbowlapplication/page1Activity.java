package com.example.fishbowlapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lakue.lakuepopupactivity.PopupActivity;
import com.lakue.lakuepopupactivity.PopupGravity;
import com.lakue.lakuepopupactivity.PopupType;

public class page1Activity extends AppCompatActivity {
    LinearLayout Mpage1_1,Mpage1_2,Mpage1_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        setTitle("수질관리");
        Mpage1_1 = findViewById(R.id.Mpage1_1);
        Mpage1_2 = findViewById(R.id.Mpage1_2);
        Mpage1_3 = findViewById(R.id.Mpage1_3);

        Mpage1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),page1_1Activity.class);
                startActivity(intent);
            }
        });

        Mpage1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),page1_2Activity.class);
                startActivity(intent);
            }
        });

        Mpage1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PopupActivity.class);
                intent.putExtra("type", PopupType.SELECT);
                intent.putExtra("gravity", PopupGravity.CENTER);
                intent.putExtra("title", "순환시작");
                intent.putExtra("content", "순환을 시작하시겠습니까?");
                intent.putExtra("buttonLeft", "예");
                intent.putExtra("buttonRight", "아니요");
                startActivityForResult(intent, 2);

            }
        });

    }
}

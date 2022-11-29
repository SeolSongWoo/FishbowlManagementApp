package com.example.fishbowlapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONObject;

public class URLTestActivity extends AppCompatActivity {
    String test;
    URLConnector task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urltest);
        test = "http://112.161.172.100/ab.php";
        task = new URLConnector(test);

        task.start();

        try{
            task.join();
            System.out.println("waiting... for result");
        }
        catch(InterruptedException e){

        }

        JSONObject json = new JSONObject();
        json = task.getResult();


        try {
            System.out.println(json.get("result").toString());
        } catch ( Exception e) {

        }
    }
}

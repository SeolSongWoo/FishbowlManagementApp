package com.example.fishbowlapplication;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class apicommunication extends AppCompatActivity {
    private URL githubEndpoint;
    private StringBuilder sb;
    private HttpURLConnection myConnection;
    private BufferedReader br;
    private JSONObject jsonObject;
    apicommunication () {
        this.sb = new StringBuilder();
    }



}

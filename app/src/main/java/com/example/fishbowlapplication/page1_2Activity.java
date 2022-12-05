package com.example.fishbowlapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class page1_2Activity extends AppCompatActivity  {
    WebView webView;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1_2);
        webView = findViewById(R.id.cctvweb);
        swipeRefreshLayout = findViewById(R.id.swipe);
        webView.setPadding(0,0,0,0);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        String url ="http://192.168.0.2/cam";
        webView.loadUrl(url);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.setPadding(0,0,0,0);
                webView.getSettings().setBuiltInZoomControls(false);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setUseWideViewPort(true);
                String url ="http://192.168.0.2/cam";
                webView.loadUrl(url);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }



}

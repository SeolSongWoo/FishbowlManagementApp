package com.example.fishbowlapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fishbowlapplication.Api.NetworkTask;
import com.example.fishbowlapplication.Api.RequestHttpURLConnection;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText login_id,login_password;
    Button login,facebooklogin;
    TextView m_signup,m_findpassword;
    public static String s_login_id,s_login_password;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_id = findViewById(R.id.login_id);
        login_password = findViewById(R.id.login_password);
        login = findViewById(R.id.login);
        facebooklogin = findViewById(R.id.facebooklogin);
        m_signup = findViewById(R.id.m_signup);
        m_findpassword = findViewById(R.id.m_findpassword);
        login.setOnClickListener(this);
        m_signup.setOnClickListener(this);
        facebooklogin.setOnClickListener(this);
        m_findpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                s_login_id = login_id.getText().toString();
                s_login_password = login_password.getText().toString();
                if(s_login_id.equals("") && s_login_password.equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else if(s_login_id.equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else if(s_login_password.equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = "http://192.168.0.8:8080/user/check";
                NetworkTask networkTask = new NetworkTask(url, null,this);
                networkTask.execute();
                break;
            case R.id.m_signup:
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(getApplicationContext(), "준비중입니다.", Toast.LENGTH_SHORT).show();
        }
    }
}



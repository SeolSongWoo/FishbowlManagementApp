package com.example.fishbowlapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fishbowlapplication.Api.RequestHttpURLConnection;
import com.google.android.material.textfield.TextInputEditText;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    Button signup;
    TextInputEditText signup_id,signup_password,signup_name,signup_email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup = findViewById(R.id.signup);
        signup_id = findViewById(R.id.singup_id);
        signup_password = findViewById(R.id.signup_password);
        signup_name = findViewById(R.id.signup_name);
        signup_email = findViewById(R.id.signup_email);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                ContentValues map = new ContentValues();
                map.put("usersId",signup_id.getText().toString());
                map.put("usersPassword",signup_password.getText().toString());
                map.put("usersName",signup_name.getText().toString());
                map.put("usersEmail",signup_email.getText().toString());

                NetworkTask networkTask = new NetworkTask("http://192.168.1.247:8080/user/signup",map);
                networkTask.execute();
                break;
        }
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            switch(s) {
                case "회원가입성공":
                    Toast.makeText(getApplicationContext(), "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "회원가입 실패(서버불안정)", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



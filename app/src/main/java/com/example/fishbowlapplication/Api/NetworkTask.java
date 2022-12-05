package com.example.fishbowlapplication.Api;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.fishbowlapplication.MainActivity;

public class NetworkTask extends AsyncTask<Void, Void, String> {

    private String url;
    private ContentValues values;
    private Context context;

    public NetworkTask(String url, ContentValues values,Context context) {
        this.context = context;
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
            case "계정없음":
                Toast.makeText(context, "계정이 존재하지않습니다.", Toast.LENGTH_SHORT).show();
                break;
            case "로그인성공":
                Toast.makeText(context, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
                break;
            case "비밀번호틀림":
                Toast.makeText(context, "비밀번호를 제대로 입력해주세요.", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(context, "로그인 실패(서버불안정)", Toast.LENGTH_SHORT).show();
        }
    }
}
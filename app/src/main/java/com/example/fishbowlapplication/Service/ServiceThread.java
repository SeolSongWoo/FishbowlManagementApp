package com.example.fishbowlapplication.Service;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceThread extends Thread{
    Handler handler;
    boolean isRun = true;

    public ServiceThread(Handler handler){
        this.handler = handler;
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run(){
        //반복적으로 수행할 작업을 한다.
        while(isRun){
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    URL githubEndpoint = null;
                    StringBuilder sb;

                    try {
                        float[] data = UnoData();
                        sb = new StringBuilder();
                        githubEndpoint = new URL("http://192.168.0.8:8080/data/range");

                        HttpURLConnection myConnection =

                                (HttpURLConnection) githubEndpoint.openConnection();

                        myConnection.setRequestMethod("GET");

                        if (myConnection.getResponseCode() == 200) {

                            BufferedReader br = new BufferedReader(new InputStreamReader(

                                    myConnection.getInputStream(), "utf-8"
                            ));
                            String line;
                            while ((line = br.readLine()) != null) {
                                sb.append(line);
                            }
                            br.close();
                            JSONObject jsonObject = new JSONObject(sb.toString());
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            double temp1 = jsonObject1.getDouble("rangeTemp1");
                            double temp2 = jsonObject1.getDouble("rangeTemp2");
                            double ph1 = jsonObject1.getDouble("rangePh1");
                            double ph2 = jsonObject1.getDouble("rangePh2");
                            int check = 0;
                            if(temp1 > data[1] || data[1]  > temp2) {
                                check++;
                            }else if(ph1 > data[2] || data[2] > ph2) {
                                check++;
                            }
                            if(check != 0 ) {
                                handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
                            }
                            myConnection.disconnect();
                            /////////////////////////////////////
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            try{
                Thread.sleep(5000); //10초씩 쉰다.
            }catch (Exception e) {}
        }
    }

    public float[] UnoData() {
        float[] data = new float[3];
        URL githubEndpoint = null;
        StringBuilder sb;

        try {
            sb = new StringBuilder();
            githubEndpoint = new URL("http://192.168.0.2/");
            HttpURLConnection myConnection =
                    (HttpURLConnection) githubEndpoint.openConnection();
            myConnection.setRequestMethod("GET");
            if (myConnection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        myConnection.getInputStream(), "utf-8"
                ));
                String line;
                while((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                sb.deleteCharAt(sb.length()-2);
                final JSONObject jsonObject = new JSONObject(sb.toString());
                String ntu = jsonObject.getString("turbidity");
                String temp = jsonObject.getString("temp");
                String ph = jsonObject.getString("PH");
                float ntu1 = Float.parseFloat(ntu);
                float temp1 = Float.parseFloat(temp);
                float ph1 = Float.parseFloat(ph);
                data[0] = ntu1;
                data[1] = temp1;
                data[2] = ph1;
                myConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;

    }
}

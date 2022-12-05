package com.example.fishbowlapplication.Service;

import static java.text.DateFormat.DEFAULT;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.fishbowlapplication.MainActivity;
import com.example.fishbowlapplication.R;
import com.example.fishbowlapplication.page6Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import kotlin.random.Random;

public class AlrimService extends Service {
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;


    ServiceThread thread;

    @Override
    public IBinder onBind(Intent intent) {

        return null;    // TODO: Return the communication channel to the service.
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        thread.stopForever();
        thread = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            /////////////////////////////////////
            mBuilder = new NotificationCompat.Builder(AlrimService.this, "notify_001");
            Intent intent = new Intent(AlrimService.this, page6Activity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(AlrimService.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText("수온또는 PH가 범위를 초과했습니다.");
            bigText.setBigContentTitle("어항알림");
            bigText.setSummaryText("어항관리");

            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
            mBuilder.setContentTitle("Your Title");
            mBuilder.setContentText("Your text");
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            mBuilder.setStyle(bigText);

            mNotificationManager =
                    (NotificationManager) AlrimService.this.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = "Your_channel_id";
                NotificationChannel channel = new NotificationChannel(
                        channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_HIGH);
                mNotificationManager.createNotificationChannel(channel);
                mBuilder.setChannelId(channelId);


                mNotificationManager.notify(0, mBuilder.build());
            }

            //토스트 띄우기
            //Toast.makeText(AlrimService.this, "뜸?", Toast.LENGTH_LONG).show();
        }
    }

    ;

}
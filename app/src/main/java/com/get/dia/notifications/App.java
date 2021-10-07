package com.get.dia.notifications;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class App extends Application {
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificacoes();


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createNotificacoes(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel canal1 = new NotificationChannel(
                    CHANNEL_1_ID,"canal 1", NotificationManager.IMPORTANCE_HIGH
            );
            canal1.setDescription("Notificação DIA");

            NotificationChannel canal2 = new NotificationChannel(
                    CHANNEL_2_ID,"canal 2", NotificationManager.IMPORTANCE_LOW
            );
            canal1.setDescription("Notificação 2 DIA");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(canal1);
            manager.createNotificationChannel(canal2);
        }

    }}


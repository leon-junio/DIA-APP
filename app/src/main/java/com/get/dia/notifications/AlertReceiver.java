package com.get.dia.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import androidx.core.app.NotificationCompat;



public class AlertReceiver extends BroadcastReceiver {
    public static String desc,nome;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(desc,nome);
        notificationHelper.getManager().notify(1, nb.build());
    }
}

package com.cdp.agenda;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification_receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent repeating_intent =new Intent(context,FiltroActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent= PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "chanelID")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_birthday)
                .setContentTitle("Agenda")
                .setContentText("Hoy hay cumpleaños ...")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat managerCompat= NotificationManagerCompat.from(context.getApplicationContext());
        managerCompat.notify(1,builder.build());


    }
}

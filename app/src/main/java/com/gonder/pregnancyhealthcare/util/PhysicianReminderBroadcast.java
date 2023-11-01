package com.gonder.pregnancyhealthcare.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.google.gson.Gson;

public class PhysicianReminderBroadcast extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        Mother mother = new Gson().fromJson(intent.getStringExtra("mother"), Mother.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "physician.reminder");



        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle("Chechup reminder");
        builder.setContentText("You have checkup with : "+ mother.getFullname());
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(201,builder.build());
    }

}

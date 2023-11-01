package com.gonder.pregnancyhealthcare.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.Physician;
import com.google.gson.Gson;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "regular.reminder");

        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle("Monthly Reminder");
        builder.setContentText("Your monthly checkup date is dersual...");
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200,builder.build());

        sendSms(context,intent);
    }

    private void sendSms(Context context, Intent intent) {
        Mother mother1 = new Gson().fromJson(intent.getStringExtra("mother"), Mother.class);
        Mother mother = (Mother) new SharedPrefConfig(context).user();
//        TODO: send sms to mother.getPhone 'using textlocal
    }

}

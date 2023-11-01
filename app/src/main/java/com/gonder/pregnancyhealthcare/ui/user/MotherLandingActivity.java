package com.gonder.pregnancyhealthcare.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gonder.pregnancyhealthcare.Login2Activity;
import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.util.ReminderBroadcast;
import com.gonder.pregnancyhealthcare.util.SharedPrefConfig;
import com.google.gson.Gson;

public class MotherLandingActivity extends AppCompatActivity {


    Toolbar toolbar;
    private CardView btnConsultation,btnCalculator, careNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_landing);

        createRegularNotification();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Mother Landing");


        btnConsultation = findViewById(R.id.view_consultation);
        btnCalculator = findViewById(R.id.due_date_calculator);
        careNotification = findViewById(R.id.care_notification);

        btnConsultation.setOnClickListener(this::viewConsultation);
        btnCalculator.setOnClickListener(this::dueDateCalculator);
        careNotification.setOnClickListener(this::showCareNotification);
    }

    private void showCareNotification(View view) {
        Toast.makeText(this, "Care Notification", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), CareNotificationListActivity.class);
        startActivity(intent);

    }

    private void createRegularNotification() {
        createNotificationChannel();
        Toast.makeText(this, "Regular Reminder set!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, ReminderBroadcast.class);
        Mother mada = (Mother) new SharedPrefConfig(getApplicationContext()).user();
        intent.putExtra("mother",new Gson()
                .toJson(mada));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long now = System.currentTimeMillis();
        long tenSec = 1000 * 10;
        long oneMin = 2 * 1000;
        long dayMilliSec = 86400000;
        long monthMilliSec = dayMilliSec * 30;
        long hourMilliSec = 3600000;

        long regDate = mada.getRegDate() + hourMilliSec;

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                regDate,
                monthMilliSec,
                pendingIntent

        );

    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String name = "checkupRegularReminder";
            String desc = "Channel for Regular Reminder";
            int i = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("regular.reminder", name, i);

            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void dueDateCalculator(View view) {
        DueDateCalculatorDialog dialog = new DueDateCalculatorDialog();
        dialog.show(getSupportFragmentManager(),"due_date_calculator_dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.clerk_toolbar_menu,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Toast.makeText(this, "Lougout", Toast.LENGTH_SHORT).show();
                SharedPrefConfig config = new SharedPrefConfig(this);
                config.deleteLoginInfo();

                Intent intent = new Intent(getApplicationContext(),Login2Activity.class);
                startActivity(intent);

                break;
        }
//        return super.onOptionsItemSelected(item);
        return true;
    }

    public void viewConsultation(View view) {
        startActivity(new Intent(getApplicationContext(),ViewConsultationActivity.class));
    }

    public void getServiceNotification(View view) {
        startActivity(new Intent(this,MothersNotificationListActivity.class));
    }
}

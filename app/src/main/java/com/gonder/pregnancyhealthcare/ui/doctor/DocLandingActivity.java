package com.gonder.pregnancyhealthcare.ui.doctor;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.Physician;
import com.gonder.pregnancyhealthcare.ui.user.NotificationDetailDialog;
import com.gonder.pregnancyhealthcare.util.PhysicianReminderBroadcast;
import com.gonder.pregnancyhealthcare.util.ReminderBroadcast;
import com.gonder.pregnancyhealthcare.util.SharedPrefConfig;
import com.gonder.pregnancyhealthcare.util.UserType;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DocLandingActivity extends AppCompatActivity {
    Toolbar toolbar;
    CardView consult, viewList, labResult,notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_landing);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Doc Landing");

        consult = findViewById(R.id.give_consultation);
        viewList = findViewById(R.id.view_list);
        labResult = findViewById(R.id.lab_result);
        notification = findViewById(R.id.receive_notification);

        consult.setOnClickListener(this::giveConsultation);
        viewList.setOnClickListener(this::viewList);
        labResult.setOnClickListener(this::viewLabResult);


        for(Mother mother: this.fetchMyMothers()) {
            createRegularNotification(mother);
        }
    }

    private void giveConsultation(View view) {
//        TODO #: think what is next here
    }

    private void createRegularNotification(Mother mother) {
        createNotificationChannel();
        Toast.makeText(this, "Regular Reminder set!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, PhysicianReminderBroadcast.class);
//        Mother mada = (Mother) new SharedPrefConfig(getApplicationContext()).user();
        intent.putExtra("mother",new Gson()
                .toJson(mother));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long now = System.currentTimeMillis();
        long tenSec = 1000 * 10;
        long oneMin = 2 * 1000;
        long dayMilliSec = 86400000;
        long monthMilliSec = dayMilliSec * 30;
        long hourMilliSec = 3600000;

        long regDate = mother.getRegDate() + hourMilliSec;

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
            NotificationChannel channel = new NotificationChannel("physician.reminder", name, i);

            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private ArrayList<Mother> fetchMyMothers() {
        ArrayList<Mother> madas = new ArrayList<>();
        Physician loggedIn = new Physician();
        SharedPrefConfig prefConfig = new SharedPrefConfig(getApplicationContext());
        if(prefConfig.getStatus()) {
            if (prefConfig.userType().equals(UserType.physician)) {
                loggedIn = (Physician) prefConfig.user();
            }
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference docRef = database.getReference(Utility.PATH_PHYSICIANS);

        docRef = docRef.child(loggedIn.getUsername()).child(Utility.PATH_MOTHERES);

        docRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot midSnapshot: dataSnapshot.getChildren()) {
                    madas.add(midSnapshot.getValue(Mother.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error)
            {
//                makeToast(error.getMessage());
            }
        });

        return madas;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.clerk_toolbar_menu,menu);


        return true;
    }

    public void sendUpdatedReport(View view) {
        Intent intent = new Intent(getApplicationContext(), SendUpdatedReportActivity.class);
        startActivity(intent);

    }

    public void getReport(View view) {
    }

    public void viewList(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewListActivity.class);
        startActivity(intent);


    }

    public void viewLabResult(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewLabResultActivity.class);
        startActivity(intent);
    }
}

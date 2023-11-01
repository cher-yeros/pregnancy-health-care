package com.gonder.pregnancyhealthcare.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseListAdapter;
import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.adapters.CareNotificationAdapter;
import com.gonder.pregnancyhealthcare.adapters.MothersListAdapter;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.Notification;
import com.gonder.pregnancyhealthcare.util.SharedPrefConfig;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CareNotificationListActivity extends AppCompatActivity {

    private ArrayList<Notification> nots = new ArrayList<>();
    private CareNotificationAdapter listAdapter;
    private ProgressBar progressBar;
    RecyclerView mothersRecylerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_notification_list);

        progressBar = findViewById(R.id.progress_bar);


//        listView = view.findViewById(R.id.list_view);
        mothersRecylerView = findViewById(R.id.mother_recycler_view);

//        initMothers();
        fetchCareNotifications();
//        setFirebaseListAdapter();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mothersRecylerView.setLayoutManager(layoutManager);


        listAdapter = new CareNotificationAdapter(getApplicationContext(), nots);
        mothersRecylerView.setAdapter(listAdapter);


    }

    private void fetchCareNotifications() {
        progressBar.setVisibility(View.VISIBLE);
        Mother mother = (Mother) new SharedPrefConfig(getApplicationContext()).user();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mynots, notRef = database.getReference(Utility.PATH_NOTIFICATIONS);

        mynots = notRef.child(Utility.PATH_MOTHERES).child(mother.getUsername());

        mynots.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    nots.add(snap.getValue(Notification.class));
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

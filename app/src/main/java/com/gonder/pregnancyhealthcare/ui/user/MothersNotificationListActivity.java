package com.gonder.pregnancyhealthcare.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.adapters.NotificationnListAdapter;
import com.gonder.pregnancyhealthcare.adapters.ViewListAdapter;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.Notification;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MothersNotificationListActivity extends AppCompatActivity {

    private ArrayList<Notification> notifications = new ArrayList<>();
    NotificationnListAdapter listAdapter;
    ListView mothersRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mothers_notification_list);

        initNotifications();


        mothersRecyclerView = findViewById(R.id.rv);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        mothersRecyclerView.setLayoutManager(layoutManager);
//
//        listAdapter = new NotificationnListAdapter(notifications, getApplicationContext());
//        mothersRecyclerView.setAdapter(listAdapter);
    }

    private void initNotifications() {
        for (int i=1;i<10;i++) {
            Notification notification = new Notification();
            notification.setSenderId("jdkf-"+i);
            notification.setTitle("title-"+i);
            notifications.add(notification);
        }


    }

    private void fetchNotifications() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notRef = database.getReference(Utility.PATH_NOTIFICATIONS);
        notRef = notRef.child(Utility.PATH_MOTHERES).child("this-mother-id");
        notRef.child("common").child("this-mother-id");

        Query e = notRef;

        FirebaseListOptions.Builder<Notification> op = new FirebaseListOptions.Builder<>();
        op.setLayout(R.layout.blueprint_notification_list);
        op.setQuery(notRef,Notification.class);
        op.setLifecycleOwner(this);

        FirebaseListAdapter<Notification> firebaseListAdapter = new FirebaseListAdapter<Notification>(op.build()) {
            @Override
            protected void populateView(@NonNull View itemView, @NonNull Notification model, int position) {
                TextView title,sender;
                title = itemView.findViewById(R.id.title);
                sender = itemView.findViewById(R.id.sender);

                title.setText(model.getTitle());
                sender.setText(model.getSenderId());

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NotificationDetailDialog dialog1 = new NotificationDetailDialog();
                        Bundle bundle = new Bundle();
                        bundle.putString("notification",new Gson().toJson(model));
                        dialog1.setArguments(bundle);
                        dialog1.show(getSupportFragmentManager(),"notification_detail_dialog");
                    }
                });
            }
        };

        mothersRecyclerView.setAdapter(firebaseListAdapter);

//        notRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot notSnapshot: snapshot.getChildren()) {
//                    notifications.add(notSnapshot.getValue(Notification.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), "ops "+error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}

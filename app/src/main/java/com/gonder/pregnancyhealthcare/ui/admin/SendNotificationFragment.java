package com.gonder.pregnancyhealthcare.ui.admin;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.Admin;
import com.gonder.pregnancyhealthcare.models.Notification;
import com.gonder.pregnancyhealthcare.models.Physician;
import com.gonder.pregnancyhealthcare.util.SharedPrefConfig;
import com.gonder.pregnancyhealthcare.util.UserType;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendNotificationFragment extends Fragment {


    public SendNotificationFragment() {
        // Required empty public constructor
    }

    private Button btnNotify;
    private EditText body,title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_notification, container, false);

        body = view.findViewById(R.id.tf_report);
        title = view.findViewById(R.id.title);
        btnNotify = view.findViewById(R.id.btn_send_notification);
        btnNotify.setOnClickListener(this::sendNotification);

        return view;
    }

    private void sendNotification(View view) {
        Admin loggedIn = new Admin();
        SharedPrefConfig prefConfig = new SharedPrefConfig(getContext());
        if(prefConfig.getStatus()) {
            if (prefConfig.userType().equals(UserType.admin)) {
                loggedIn = (Admin) prefConfig.user();
            }
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notRef = database.getReference(Utility.PATH_NOTIFICATIONS);

        DatabaseReference allRef = notRef.child("common").push();
        String key = allRef.getKey();

        Notification notification = new Notification();
        notification.setTitle(title.getText().toString());
        notification.setBody(body.getText().toString());
        notification.setDate(new Date().getTime());
        notification.setSenderId(loggedIn.getUsername());
        notification.setReceiverId("all");
        notification.setId(key);

//        TODO: read this notification in physician and pregnant woman

        allRef.setValue(notification).addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Sent!", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Notification sent successfully!", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> Toast.makeText(getContext(), "ops "+e.getMessage(), Toast.LENGTH_SHORT).show());
    }

}

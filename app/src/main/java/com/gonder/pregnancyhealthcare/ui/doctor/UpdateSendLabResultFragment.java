package com.gonder.pregnancyhealthcare.ui.doctor;


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
import com.gonder.pregnancyhealthcare.models.LabResult;
import com.gonder.pregnancyhealthcare.models.Notification;
import com.gonder.pregnancyhealthcare.models.Physician;
import com.gonder.pregnancyhealthcare.util.SharedPrefConfig;
import com.gonder.pregnancyhealthcare.util.UserType;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateSendLabResultFragment extends Fragment {


    public UpdateSendLabResultFragment() {
        // Required empty public constructor
    }

    private EditText body,title;
    private LabResult labResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_send_lab_result, container, false);

        labResult = new Gson().fromJson(this.getArguments().getString("labResult"),LabResult.class);

        body = view.findViewById(R.id.tf_report);
        title = view.findViewById(R.id.title);
        Button sendService = view.findViewById(R.id.btn_send_report);
        sendService.setOnClickListener(this::sendUpdatedService);

        return view;
    }

    private void sendUpdatedService(View view) {
        Physician loggedIn = new Physician();
        SharedPrefConfig prefConfig = new SharedPrefConfig(getContext());
        if(prefConfig.getStatus()) {
            if (prefConfig.userType().equals(UserType.physician)) {
                loggedIn = (Physician) prefConfig.user();
            }
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notRef = database.getReference(Utility.PATH_NOTIFICATIONS);

        String mId = labResult.getmId();
        String phyId = loggedIn.getUsername();

        DatabaseReference mnRef = notRef.child(Utility.PATH_MOTHERES).child(mId).push();
        String key = mnRef.getKey();

        Notification notification = new Notification();
        notification.setTitle(title.getText().toString());
        notification.setBody(body.getText().toString());
        notification.setDate(new Date().getTime());
        notification.setSenderId(phyId);
        notification.setReceiverId(mId);
        notification.setId(key);

        mnRef.setValue(notification).addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Sent!", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> {
            System.out.println("jaskfdjal");
            Toast.makeText(getContext(), "ops "+e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

}

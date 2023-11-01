package com.gonder.pregnancyhealthcare.ui.admin.technician;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.LabTechnician;
import com.gonder.pregnancyhealthcare.models.Physician;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateLabTechnicianFragment extends Fragment {


    private ProgressBar progressBar;
    private View view;
    private LabTechnician technician;
    private EditText fullname,phone,username,password,cPassword;
    private Button updateBtn;
    String sFullname,sPhone,sPassword,sUsername,sCPassword;

    public UpdateLabTechnicianFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_lab_technician, container, false);
        initComponents();

        initFields();

        return view;
    }



    private void initComponents() {
        String jsonString = this.getArguments().getString("technician");
        technician = new Gson().fromJson(jsonString, LabTechnician.class);

        fullname = view.findViewById(R.id.fullname);
        phone = view.findViewById(R.id.phone);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        cPassword = view.findViewById(R.id.confirm_password);
        updateBtn = view.findViewById(R.id.btn_update);
        progressBar = view.findViewById(R.id.progress_bar);

        updateBtn.setOnClickListener(this::updateUser);
    }

    private void updateUser(View view) {
        sFullname = fullname.getText().toString().trim();
        sPhone = phone.getText().toString().trim();
        sPassword = password.getText().toString().trim();
        sUsername = username.getText().toString().trim();
        sCPassword = cPassword.getText().toString().trim();

        if(!validate()){
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        updateBtn.setVisibility(View.INVISIBLE);

        LabTechnician labTechnician = new LabTechnician();
        labTechnician.setFullname(sFullname);
        labTechnician.setPhone(sPhone);
        labTechnician.setUsername(sUsername);
        labTechnician.setPassword(sPassword);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference techRef = database.getReference(Utility.PATH_LABTECHNICIANS);

//        String key = techRef.push().getKey();

        if (!technician.getUsername().equals(labTechnician.getUsername())) {
            techRef.child(technician.getUsername()).removeValue((error, ref) -> techRef.child(labTechnician.getUsername()).setValue(labTechnician).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    putToFirebase(labTechnician);
                }
            }));
        } else {
            putToFirebase(labTechnician);
        }


    }

    private void putToFirebase(LabTechnician labTechnician) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference techRef = database.getReference(Utility.PATH_LABTECHNICIANS);

        techRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(getContext(), "Username already exist", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    updateBtn.setVisibility(View.VISIBLE);

                }
                else {
                    techRef.child(sUsername).setValue(labTechnician).addOnSuccessListener(aVoid -> {
                        progressBar.setVisibility(View.GONE);
                        updateBtn.setVisibility(View.VISIBLE);

                        Toast.makeText(getContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initFields() {
        fullname.setText(technician.getFullname());
        phone.setText(technician.getPhone());
        username.setText(technician.getUsername());
        password.setText(technician.getPassword());
    }

    private boolean validate() {
        boolean valid = false;
        if (sFullname.isEmpty()) {
            Utility.showToast(getContext(),getString(R.string.fullname_cannot_be_empty));
        } else if (sPhone.isEmpty()) {
            Utility.showToast(getContext(),getString(R.string.phone_cannot_be_empty));
        }  else if (sUsername.isEmpty()) {
            Utility.showToast(getContext(),getString(R.string.username_cannot_be_empty));
        } else if (sUsername.length() < 6) {
            Utility.showToast(getContext(),getString(R.string.username_cannot_be_lessthan_6));
        } else if (sPassword.isEmpty()) {
            Utility.showToast(getContext(),getString(R.string.password_cannot_be_empty));
        } else if (sPassword.length() < 6) {
            Utility.showToast(getContext(),getString(R.string.password_cannot_be_lessthan_6));
        } else if (!sPassword.equals(sCPassword)) {
            Utility.showToast(getContext(),getString(R.string.password_and_c_password_must_match));
        } else {
            valid = true;
        }
        return valid;
    }

}

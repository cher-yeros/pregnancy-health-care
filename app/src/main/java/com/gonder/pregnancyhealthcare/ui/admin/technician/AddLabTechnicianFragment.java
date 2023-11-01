package com.gonder.pregnancyhealthcare.ui.admin.technician;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AddLabTechnicianFragment extends Fragment {


    public AddLabTechnicianFragment() {
        // Required empty public constructor
    }

    private EditText fullname,phone,username,password,cPassword;
    private Button registerBtn;
    private ProgressBar progressBar;
    String sFullname,sPhone,sPassword,sUsername,sCPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_lab_technician, container, false);

        registerBtn = view.findViewById(R.id.btn_register);
        fullname = view.findViewById(R.id.fullname);
        phone = view.findViewById(R.id.phone);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        cPassword = view.findViewById(R.id.confirm_password);
        progressBar = view.findViewById(R.id.progress_bar);


        registerBtn.setOnClickListener(this::registerAction);

        return view;
    }

    private void registerAction(View view) {
        sFullname = fullname.getText().toString().trim();
        sPhone = phone.getText().toString().trim();
        sPassword = password.getText().toString().trim();
        sUsername = username.getText().toString().trim();
        sCPassword = cPassword.getText().toString().trim();

        if(!validate()){
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        registerBtn.setVisibility(View.INVISIBLE);




        LabTechnician labTechnician = new LabTechnician();
        labTechnician.setFullname(fullname.getText().toString());
        labTechnician.setPhone(phone.getText().toString());
        labTechnician.setUsername(username.getText().toString());
        labTechnician.setPassword(password.getText().toString());


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference techRef = database.getReference(Utility.PATH_LABTECHNICIANS);

//        String key = techRef.push().getKey();

        techRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(getContext(), "Username already exist", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    registerBtn.setVisibility(View.VISIBLE);

                }
                else {
                    techRef.child(username.getText().toString()).setValue(labTechnician).addOnSuccessListener(aVoid -> {
                        progressBar.setVisibility(View.GONE);
                        registerBtn.setVisibility(View.VISIBLE);

                        Toast.makeText(getContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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

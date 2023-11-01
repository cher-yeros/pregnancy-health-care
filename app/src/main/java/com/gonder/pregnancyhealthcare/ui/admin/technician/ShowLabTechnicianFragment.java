package com.gonder.pregnancyhealthcare.ui.admin.technician;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.LabTechnician;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowLabTechnicianFragment extends Fragment {


    public ShowLabTechnicianFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_lab_technician, container, false);

        initComponents();
        initFields();

        return view;
    }

    private View view;
    private LabTechnician technician;
    private TextView fullname,phone,email,age,username,password;
    private Button call,mailTo,showPwd;

    private void initComponents() {
        fullname = view.findViewById(R.id.fullname);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);

        call = view.findViewById(R.id.call);
        mailTo = view.findViewById(R.id.btn_email);
        showPwd = view.findViewById(R.id.btn_show_password);

        showPwd.setOnClickListener(this::showPassword);


        String jsonString = this.getArguments().getString("technician");

        technician = new Gson().fromJson(jsonString, LabTechnician.class);
    }

    private void initFields() {
        String fullName = technician.getFullname();
        fullname.setText(fullName);
        phone.setText(technician.getPhone());
        username.setText(technician.getUsername());
    }

    private void showPassword(View view) {
//        password.setText(technician.getPassword());
    }

}

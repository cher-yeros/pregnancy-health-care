package com.gonder.pregnancyhealthcare.ui.admin.mother;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.google.gson.Gson;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowMotherFragment extends Fragment {


    public ShowMotherFragment() {
        // Required empty public constructor
    }
    private View view;
    private Mother mother;
    private TextView fullname,phone,email,age,username,password;
    private Button call,mailTo,showPwd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_show_mother, container, false);

        initComponents();
        initFields();

        return view;
    }

    private void initComponents() {
        fullname = view.findViewById(R.id.fullname);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        age = view.findViewById(R.id.age);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);

        call = view.findViewById(R.id.call);
        mailTo = view.findViewById(R.id.btn_email);
        showPwd = view.findViewById(R.id.btn_show_pwd);

        showPwd.setOnClickListener(this::showPassword);


        String jsonString = this.getArguments().getString("mother");

        mother = new Gson().fromJson(jsonString,Mother.class);
    }

    private void initFields() {
        String fullName = mother.getFullname();
        fullname.setText(fullName);
        phone.setText(mother.getPhone());
        age.setText(String.valueOf(mother.getAge()));
        username.setText(mother.getUsername());
    }

    private void showPassword(View view) {
        password.setText(mother.getPassword());
    }

}

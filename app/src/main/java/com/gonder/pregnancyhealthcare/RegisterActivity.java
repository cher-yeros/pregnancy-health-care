package com.gonder.pregnancyhealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gonder.pregnancyhealthcare.models.Admin;
import com.gonder.pregnancyhealthcare.ui.admin.ClerkNavDrawer;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullname,phone,username,password,cPassword;
    private Button btnRegister;
    private ProgressBar progressBar;
    String sFullname,sPhone,sPassword,sUsername,sCPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname = findViewById(R.id.fullname);
        phone = findViewById(R.id.phone);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        cPassword = findViewById(R.id.confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);

        btnRegister.setOnClickListener(this::registerAdmin);
    }

    private void registerAdmin(View view) {
        sFullname = fullname.getText().toString().trim();
        sPhone = phone.getText().toString().trim();
        sPassword = password.getText().toString().trim();
        sUsername = username.getText().toString().trim();
        sCPassword = cPassword.getText().toString().trim();

        if(!validate()){
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnRegister.setVisibility(View.INVISIBLE);

        Admin admin = new Admin();
        admin.setFullname(sFullname);
        admin.setUsername(sUsername);
        admin.setPhone(sPhone);
        admin.setPassword(sPassword);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference adminRef = database.getReference(Utility.PATH_ADMINS);

        adminRef.child(sUsername).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "Username already exist", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    btnRegister.setVisibility(View.VISIBLE);

                }
                else {
                    adminRef.child(sUsername).setValue(admin).addOnSuccessListener((Void aVoid) -> {
                        progressBar.setVisibility(View.GONE);
                        btnRegister.setVisibility(View.VISIBLE);
                        login();
                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void login() {
        Intent intent = new Intent(getApplicationContext(), ClerkNavDrawer.class);
        startActivity(intent);
    }

    private boolean validate() {
        boolean valid = false;
        if (sFullname.isEmpty()) {
            Utility.showToast(this,getString(R.string.fullname_cannot_be_empty));
        } else if (sPhone.isEmpty()) {
            Utility.showToast(this,getString(R.string.phone_cannot_be_empty));
        }  else if (sUsername.isEmpty()) {
            Utility.showToast(this,getString(R.string.username_cannot_be_empty));
        } else if (sUsername.length() < 6) {
            Utility.showToast(this,getString(R.string.username_cannot_be_lessthan_6));
        } else if (sPassword.isEmpty()) {
            Utility.showToast(this,getString(R.string.password_cannot_be_empty));
        } else if (sPassword.length() < 6) {
            Utility.showToast(this,getString(R.string.password_cannot_be_lessthan_6));
        } else if (!sPassword.equals(sCPassword)) {
            Utility.showToast(this,getString(R.string.password_and_c_password_must_match));
        } else {
            valid = true;
        }
        return valid;
    }
}

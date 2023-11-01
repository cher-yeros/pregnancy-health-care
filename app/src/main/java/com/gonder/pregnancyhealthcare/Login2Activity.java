package com.gonder.pregnancyhealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gonder.pregnancyhealthcare.models.Admin;
import com.gonder.pregnancyhealthcare.models.LabTechnician;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.Physician;
import com.gonder.pregnancyhealthcare.ui.admin.ClerkNavDrawer;
import com.gonder.pregnancyhealthcare.ui.doctor.DocLandingActivity;
import com.gonder.pregnancyhealthcare.ui.technician.LabTechnicianLandingActivity;
import com.gonder.pregnancyhealthcare.ui.user.MotherLandingActivity;
import com.gonder.pregnancyhealthcare.util.CheckNetwork;
import com.gonder.pregnancyhealthcare.util.SharedPrefConfig;
import com.gonder.pregnancyhealthcare.util.UserType;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class Login2Activity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    Button roleBtn,loginBtn, registerBtn;
    EditText username,password;
    String uname,pwd;
    TextView forgetPassword;
    ProgressBar progressBar;
    LinearLayout lenLayout;

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        checkIfLoggedIn();

        database = FirebaseDatabase.getInstance().getReference();

        CheckNetwork network = new CheckNetwork(getApplicationContext());
        network.registerNetworkCallback();

        roleBtn = findViewById(R.id.role);
        registerBtn = findViewById(R.id.btn_register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.btn_login);
        forgetPassword = findViewById(R.id.forget_password);
        progressBar = findViewById(R.id.prog_bar);
        lenLayout = findViewById(R.id.linearLayout5);

        forgetPassword.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
            startActivity(intent);
        });


//        TODO : implement forget password

        loginBtn.setOnClickListener(v -> {
            uname = username.getText().toString();
            pwd = password.getText().toString();


//            startActivity(new Intent(getApplicationContext(),DocLandingActivity.class));
//            loginMother();
            checkInternetConnection();

            if(!validate()){
             return;
            }

            String role = roleBtn.getText().toString();

            String admin = getString(R.string.admin);
            String doctor = getString(R.string.doctor);
            String user = getString(R.string.user);
            String technician = getString(R.string.lab_technician);

            lenLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            if(role.equals(admin)) {
                loginAdmin();
            } else if (role.equals(doctor)) {
                loginPhysician();
            } else if (role.equals(user)) {
                loginMother();
            }else if (role.equals(technician)) {
                loginLabTechnician();
            }

        });

        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void checkInternetConnection() {
        if (!Utility.isNetworkConnected){
            // Internet Connected
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_warning);
            builder.setTitle("Warning!");
            builder.setMessage("You'are not connected internet!");
            builder.setPositiveButton("Try again", (dialog, which) ->{
                checkInternetConnection();
            });
            builder.show();
        }
        // Not Connected
//            Toast.makeText(this, "network is not available", Toast.LENGTH_SHORT).show();

    }

    private void checkIfLoggedIn() {
        SharedPrefConfig prefConfig = new SharedPrefConfig(this);
//        prefConfig.writeLoginInfo(UserType.mother,new Gson().toJson(mother));
        if(prefConfig.getStatus()) {
            if (prefConfig.userType().equals(UserType.mother)) {
                startActivity(new Intent(this,MotherLandingActivity.class));
            }
        }

//        TODO: check online here
    }

    private void loginLabTechnician() {
//        Intent intent = new Intent(getApplicationContext(), LabTechnicianLandingActivity.class);
//        startActivity(intent);

        database.child(Utility.PATH_LABTECHNICIANS).child(uname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    LabTechnician technician = snapshot.getValue(LabTechnician.class);
                    if(pwd.equals(technician.getPassword())) {
                        Intent intent = new Intent(getApplicationContext(), LabTechnicianLandingActivity.class);
                        startActivity(intent);
                        labTechnicianToSharedPref(technician);
                    } else {
                        showToast("Incorrect password");
                    }

                } else {
                    showToast("Username didn't found!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loginMother() {
//        Intent intent = new Intent(getApplicationContext(), MotherLandingActivity.class);
//        startActivity(intent);

        database.child(Utility.PATH_MOTHERES).child(uname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Mother mother = snapshot.getValue(Mother.class);
                    if(pwd.equals(mother.getPassword())) {
                        Intent intent = new Intent(getApplicationContext(), MotherLandingActivity.class);
                        startActivity(intent);
                        motherToSharedPref(mother);
                    } else {
                        showToast("Incorrect password");
                    }

                } else {
                    showToast("Username didn't found!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loginPhysician() {
//        Intent intent = new Intent(getApplicationContext(), DocLandingActivity.class);
//        startActivity(intent);

        database.child(Utility.PATH_PHYSICIANS).child(uname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Physician physician = snapshot.getValue(Physician.class);
                    if(pwd.equals(physician.getPassword())) {
                        physicianToSharedPref(physician);
                        Intent intent = new Intent(getApplicationContext(), DocLandingActivity.class);
                        startActivity(intent);

                    } else {
                        showToast("Incorrect password");
                    }

                } else {
                    showToast("Username didn't found!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void loginAdmin() {
//        Intent intent = new Intent(getApplicationContext(), ClerkNavDrawer.class);
//        startActivity(intent);

        showToast("1");
        database.child(Utility.PATH_ADMINS).child(uname).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                showToast("2");
                if(snapshot.exists()) {
                    Admin admin = snapshot.getValue(Admin.class);
                    if(pwd.equals(admin.getPassword())) {

                        showToast("3");
                        Intent intent = new Intent(getApplicationContext(), ClerkNavDrawer.class);
                        startActivity(intent);
//                        adminToSharedPref(admin);
                    } else {
                        showToast("Incorrect password");
                    }

                } else {
                    showToast("Username didn't found!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private boolean validate() {
        String role = roleBtn.getText().toString();
        String chooseRole = getString(R.string.choose_role);

        if(role.equals(chooseRole)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_warning);
            builder.setTitle("Warning!");
            builder.setMessage("Please choose your role!");
            builder.setPositiveButton("Ok, Go back", (dialog, which) -> dialog.dismiss());
            builder.show();

            return false;
        } else if(uname.isEmpty() || pwd.isEmpty()) {
            showToast("Username or Password cannot be empty!");
            return false;
        }
        else if(uname.length() < 5 ) {
            showToast("Username must be >= 5 character");
            return false;
        }

        return true;
    }

    private void adminToSharedPref(Admin admin) {
        SharedPrefConfig prefConfig = new SharedPrefConfig(this);
        prefConfig.writeLoginInfo(UserType.admin,new Gson().toJson(admin));
    }

    private void motherToSharedPref(Mother mother) {
        SharedPrefConfig prefConfig = new SharedPrefConfig(this);
        prefConfig.writeLoginInfo(UserType.mother,new Gson().toJson(mother));
    }

    private void physicianToSharedPref(Physician physician) {
        SharedPrefConfig prefConfig = new SharedPrefConfig(this);
        prefConfig.writeLoginInfo(UserType.physician,new Gson().toJson(physician));
    }

    private void labTechnicianToSharedPref(LabTechnician technician) {
        SharedPrefConfig prefConfig = new SharedPrefConfig(this);
        prefConfig.writeLoginInfo(UserType.labTechnician,new Gson().toJson(technician));
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void fireRolePopup(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        MenuInflater menuInflater = popupMenu.getMenuInflater();

        menuInflater.inflate(R.menu.login_role,popupMenu.getMenu());

        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        CharSequence title = item.getTitle();
        roleBtn.setText(title);
        return true;
    }
}

package com.gonder.pregnancyhealthcare.ui.technician;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.LabResult;
import com.gonder.pregnancyhealthcare.models.LabTechnician;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.Physician;
import com.gonder.pregnancyhealthcare.util.SharedPrefConfig;
import com.gonder.pregnancyhealthcare.util.UserType;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.Date;

public class GenerateLabReportActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText resultBody;
    Mother mother;
    Button btnSendReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Generate report");


        mother = new Gson().fromJson(getIntent().getStringExtra("mother"), Mother.class);

        resultBody = findViewById(R.id.tf_report);
        btnSendReport = findViewById(R.id.btn_send_report);

        btnSendReport.setOnClickListener(this::sendReport);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.clerk_toolbar_menu,menu);


        return true;
    }


    public void sendReport(View view) {
        String sBody = resultBody.getText().toString();
        if (sBody.isEmpty()) {
            Utility.showToast(this,"report body cannot be empty");
            return;
        }

        LabTechnician loggedIn = new LabTechnician();
        SharedPrefConfig prefConfig = new SharedPrefConfig(getApplicationContext());
        if(prefConfig.getStatus()) {
            if (prefConfig.userType().equals(UserType.labTechnician)) {
                loggedIn = (LabTechnician) prefConfig.user();
            }
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference resultRef = database.getReference(Utility.PATH_LABRESULTS);

        String key = resultRef.push().getKey();

        LabResult labResult = new LabResult();
        labResult.setId(key);
        labResult.setBody(sBody);
        labResult.setDate(new Date().getTime());
        labResult.setmId(mother.getUsername());
        labResult.setlTid(loggedIn.getUsername());

        resultRef.
                child(mother.getPhyId()).
                child(key).
                setValue(labResult).
                addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Successfully Sent to the doc", Toast.LENGTH_SHORT)
                    .show();
            startActivity(new Intent(getApplicationContext(),LabTechnicianLandingActivity.class));
        });


    }
}

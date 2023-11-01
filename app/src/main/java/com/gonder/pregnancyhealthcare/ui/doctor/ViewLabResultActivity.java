package com.gonder.pregnancyhealthcare.ui.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.adapters.LabResultAdapter;
import com.gonder.pregnancyhealthcare.models.LabResult;
import com.gonder.pregnancyhealthcare.models.LabTechnician;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.Physician;
import com.gonder.pregnancyhealthcare.util.SharedPrefConfig;
import com.gonder.pregnancyhealthcare.util.UserType;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class ViewLabResultActivity extends AppCompatActivity {

    ArrayList<LabResult> results = new ArrayList<>();
    RecyclerView reportRV;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lab_result);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id.progress_bar);

//        initResult();
        fetchLabResult();

        getSupportActionBar().setTitle("Lab Results");

        reportRV = findViewById(R.id.lab_rv);
        layoutManager = new LinearLayoutManager(this);
        reportRV.setLayoutManager(layoutManager);


        LabResultAdapter listAdapter = new LabResultAdapter(getApplicationContext(), results);
        reportRV.setAdapter(listAdapter);
    }

    private void initResult() {
        results = new ArrayList<>();
        String result = "A material metaphor is the unifying theory of a rationalized space and a system of motion."+
                "The material is grounded in tactile reality, inspired by the study of paper and ink, yet "+
                "technologically advanced and open to imagination and magic.\n"+
                "Surfaces and edges of the material provide visual cues that are grounded in reality."+
                "world, without breaking the rules of physics. ";
        for (int i = 1;i< 15;i++) {
            LabResult report1 = new LabResult();
            report1.setId(String.valueOf(i));
            report1.setDate(new Date().getTime());
            report1.setBody(result);
            report1.setmId("uname");
            results.add(report1);
        }
    }

    private void fetchLabResult() {
        progressBar.setVisibility(View.VISIBLE);
        Physician loggedIn = new Physician();
        SharedPrefConfig prefConfig = new SharedPrefConfig(getApplicationContext());
        if(prefConfig.getStatus()) {
            if (prefConfig.userType().equals(UserType.physician)) {
                loggedIn = (Physician) prefConfig.user();
            }
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference labResRef = database.getReference(Utility.PATH_LABRESULTS);

        labResRef = labResRef.child(loggedIn.getUsername());

//        TODO: we need adapter here

        labResRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    results.add(snap.getValue(LabResult.class));
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





//        FirebaseListOptions.Builder<LabResult> options = new FirebaseListOptions.Builder<>();
//        options.setQuery(labResRef,LabResult.class);
//        options.setLayout(R.layout.blueprint_lab_result_list);
//        options.setLifecycleOwner(this);
//
//        new FirebaseListAdapter<LabResult>(options.build()) {
//            @Override
//            protected void populateView(@NonNull View itemView, @NonNull LabResult result, int position) {
//                TextView resNo,resDate,resSubject;
//
//                resNo = itemView.findViewById(R.id.result_no);
//                resDate = itemView.findViewById(R.id.date);
//                resSubject = itemView.findViewById(R.id.subject);
//
//                resNo.setText("Result : " + result.getId());
//                resDate.setText(Utility.toETc(result.getDate()).getMonthGeez());
//                resSubject.setText(result.getmId());
//
//
//
//                itemView.setOnClickListener(v -> {
//                    Intent intent = new Intent(getApplicationContext(), SeeUpdateLabResultActivity.class);
//                    intent.putExtra("labResult", new Gson().toJson(result));
//
//                    startActivity(intent);
//                });
//
//                progressBar.setVisibility(View.GONE);
//            }
//        };



//        labResRef.child("_ksejflajs").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot lResSnapshot: snapshot.getChildren()) {
//                    results.add(lResSnapshot.getValue(LabResult.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ViewLabResultActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.clerk_toolbar_menu,menu);


        return true;
    }
}

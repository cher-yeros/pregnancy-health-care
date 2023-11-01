package com.gonder.pregnancyhealthcare.ui.doctor;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.adapters.ViewListAdapter;
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

import java.util.ArrayList;

public class ViewListActivity extends AppCompatActivity {
    private ArrayList<Mother> mothers = new ArrayList<>();
    ViewListAdapter listAdapter;
    Toolbar toolbar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Mothers List");

//        initMothers();
        progressBar = findViewById(R.id.progress_bar);
        fetchMothers();

//        TODO: create adapter or create dialog box showing info abt mohter
        RecyclerView mothersRecyclerView = findViewById(R.id.mother_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mothersRecyclerView.setLayoutManager(layoutManager);


        listAdapter = new ViewListAdapter(mothers, getApplicationContext());
        mothersRecyclerView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.clerk_toolbar_menu,menu);


        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        fetchMothers();

    }

    private void initMothers() {
//        mothers = new ArrayList<>();
        for (int i = 1;i<51;i++) {
            mothers.add(new Mother(20+i,"ሰብለወንጌል ገ/ስላሴ "+i, "0964432858"));
        }
    }

    private void fetchMothers() {
        progressBar.setVisibility(View.VISIBLE);
        Physician loggedIn = new Physician();
        SharedPrefConfig prefConfig = new SharedPrefConfig(getApplicationContext());
        if(prefConfig.getStatus()) {
            if (prefConfig.userType().equals(UserType.physician)) {
                loggedIn = (Physician) prefConfig.user();
            }
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference docRef = database.getReference(Utility.PATH_PHYSICIANS);

        docRef = docRef.child(loggedIn.getUsername()).child(Utility.PATH_MOTHERES);

        docRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot midSnapshot: dataSnapshot.getChildren()) {

                    mothers.add(midSnapshot.getValue(Mother.class));
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                makeToast(error.getMessage());
            }
        });
    }

    public void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

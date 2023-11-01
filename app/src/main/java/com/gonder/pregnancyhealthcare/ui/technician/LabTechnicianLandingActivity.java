package com.gonder.pregnancyhealthcare.ui.technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.SearchMother;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class LabTechnicianLandingActivity extends AppCompatActivity {
    Toolbar toolbar;
    CardView generateResult;
    private FirebaseListAdapter<Mother> firebaseListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_technician_landing);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Lab Technician Landing");

        generateResult = findViewById(R.id.generate_report);

        generateResult.setOnClickListener(this::generateReport);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.clerk_toolbar_menu,menu);


        return true;
    }

    public void generateReport(View view) {
        startActivity(new Intent(this,MothersListActivity.class));
//        new SimpleSearchDialogCompat(
//                getApplicationContext(),
//                "Search Mothers",
//                "type mothers name ...",
//                null,
//                initMothers(),
//                (SearchResultListener<Searchable>) (dialog, item, position) -> {
//                    SearchMother searchMother = (SearchMother) item;
//                    Mother mother = searchMother.getMother();
//                    Intent intent = new Intent(getApplicationContext(), GenerateLabReportActivity.class);
//
//                    intent.putExtra("name",mother.getFullname());
//                    intent.putExtra("phyId",mother.getPhyId());
//                    intent.putExtra("mId",mother.getUsername());
//                    startActivity(intent);
//                }
//        ).show();



    }

    private ArrayList<SearchMother> fetchMothers() {
        ArrayList<SearchMother> searchMothers = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mothersRef = database.getReference(Utility.PATH_MOTHERES);

        mothersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mSnapshot: snapshot.getChildren()) {
                    searchMothers.add(new SearchMother(mSnapshot.getValue(Mother.class)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LabTechnicianLandingActivity.this, "error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return searchMothers;
    }

    private ArrayList<SearchMother> initMothers() {
        ArrayList<SearchMother> searchMothers = new ArrayList<>();
        for (int i = 1;i<51;i++) {
            Mother mother = new Mother(20 + i, "ሰብለወንጌል", "0964432858");

            mother.setFullname("Trusew "+i);
            mother.setPassword("ajksdlf");
            mother.setUsername("mother"+i+15);
            mother.setPhyId("-kkasjdfk-dfkasdf");


            searchMothers.add(new SearchMother(mother));
        }

        return searchMothers;
    }


//    public void setFirebaseListAdapter() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference mothersRef = database.getReference(Utility.PATH_MOTHERES);
//
//        FirebaseListOptions.Builder<Mother> options = new FirebaseListOptions.Builder<>();
//        options.setQuery(mothersRef, Mother.class);
//        options.setLayout(R.layout.blueprint_mothers_list);
//        options.setLifecycleOwner(this);
//
//
//        firebaseListAdapter = new FirebaseListAdapter<Mother>(options.build()) {
//            @Override
//            protected void populateView(@NonNull View itemView, @NonNull Mother model, int position) {
//                ImageView imageOptions, imageThumb;
//                TextView fullname, phone;
//
//                imageOptions = itemView.findViewById(R.id.img_popup);
////                imageThumb = itemView.findViewById(R.id.img_thumb);
//                fullname = itemView.findViewById(R.id.fullname);
//                phone = itemView.findViewById(R.id.phone);
//
//                fullname.setText(model.getFullname());
//                phone.setText(model.getPhone());
//
//                imageOptions.setOnClickListener(v -> {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                    builder.setMessage("Are you sure to delete?");
//                    builder.setCancelable(false);
//                    builder.setPositiveButton("Yes", (dialog, which) -> {
////                        mothers.remove(position);
//                        notifyDataSetChanged();
//                    });
//                    builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
//                    AlertDialog dialog = builder.create();
//                    dialog.setTitle("Alert!!");
//                    dialog.setIcon(R.drawable.ic_warning);
//                    dialog.show();
//
//                });
//
//                itemView.setOnClickListener(v -> {
//                    Toast.makeText(getApplicationContext(), model.getFullname(), Toast.LENGTH_SHORT).show();
//
//                    NavController navController = Navigation.findNavController(v);
//
//                    Bundle bundle = new Bundle();
//                    bundle.putString("mother", new Gson().toJson(model));
//
//                    navController.navigate(R.id.action_mothers_list_to_mother_show_update_nav, bundle);
//
//                });
//
//            }
//        };
//    }
}

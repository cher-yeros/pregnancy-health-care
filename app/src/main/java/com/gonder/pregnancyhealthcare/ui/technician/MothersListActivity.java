package com.gonder.pregnancyhealthcare.ui.technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.adapters.MothersAdapterForTech;
import com.gonder.pregnancyhealthcare.adapters.MothersListAdapter;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MothersListActivity extends AppCompatActivity implements androidx.appcompat.widget.SearchView.OnQueryTextListener{

    private ArrayList<Mother> mothers = new ArrayList<>();
    private MothersAdapterForTech listAdapter;
    private ProgressBar progressBar;
    RecyclerView mothersRecylerView;
    FirebaseListAdapter<Mother> firebaseListAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mothers_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Search Users");

//        setHasOptionsMenu(true);

        progressBar = findViewById(R.id.progress_bar);


        mothersRecylerView = findViewById(R.id.mother_recycler_view);

//        initMothers();
        fetchMothers();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mothersRecylerView.setLayoutManager(layoutManager);


        listAdapter = new MothersAdapterForTech(mothers, this);
        mothersRecylerView.setAdapter(listAdapter);

    }

    private void initMothers() {
        for (int i = 1;i<51;i++) {
            mothers.add(new Mother(20+i,"ሰብለወንጌል ገ/ስላሴ "+i, "0964432858"));
        }

        Log.d("Mothers", String.valueOf(mothers));
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.search_menu,menu);
//        menu.add
        MenuItem searchItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView actionView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        actionView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu);

        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        androidx.appcompat.widget.SearchView actionView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
//        actionView.setOnQueryTextListener(this);
//        return true;
//    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
            case R.id.action_home:
                Toast.makeText(getApplicationContext(), "Home Sweet Home", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Mother> list = new ArrayList<>();
        for (Mother mother: this.mothers) {
            if(mother.getFullname().toLowerCase().contains(newText.toLowerCase())) {
                list.add(mother);
            }
        }
        listAdapter.updateList(list);
        return true;
    }

    public void fetchMothers() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mothersRef = database.getReference(Utility.PATH_MOTHERES);

        mothersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mothers = new ArrayList<>();
                for (DataSnapshot mSnapshot: snapshot.getChildren()) {
//                    Toast.makeText(getContext(), "Eziga dersual", Toast.LENGTH_SHORT).show();
                    mothers.add(mSnapshot.getValue(Mother.class));
                }
                listAdapter.updateList(mothers);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//
//    public void setFirebaseListAdapter() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference mothersRef = database.getReference(Utility.PATH_MOTHERES);
//
//        FirebaseListOptions.Builder<Mother> options = new FirebaseListOptions.Builder<>();
//        options.setQuery(mothersRef,Mother.class);
//        options.setLayout(R.layout.blueprint_mothers_list);
//        options.setLifecycleOwner(this);
//
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
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                    builder.setMessage("Are you sure to delete?");
//                    builder.setCancelable(false);
//                    builder.setPositiveButton("Yes", (dialog, which) -> {
//                        mothers.remove(position);
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
//                    Toast.makeText(getContext(), model.getFullname(), Toast.LENGTH_SHORT).show();
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
//
//
//        firebaseListAdapter.startListening();
////        mothersRecylerView.setAdapter((RecyclerView.Adapter) firebaseListAdapter);
//    }

}

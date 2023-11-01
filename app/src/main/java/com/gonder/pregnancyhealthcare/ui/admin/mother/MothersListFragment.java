package com.gonder.pregnancyhealthcare.ui.admin.mother;


import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.adapters.MothersListAdapter;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.SearchMother;
import com.gonder.pregnancyhealthcare.ui.technician.LabTechnicianLandingActivity;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class MothersListFragment extends Fragment implements androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private ArrayList<Mother> mothers = new ArrayList<>();
    private MothersListAdapter listAdapter;
    private ProgressBar progressBar;
    RecyclerView mothersRecylerView;
    FirebaseListAdapter<Mother> firebaseListAdapter;
    ListView listView;

//    Toolbar toolbar;

    public MothersListFragment() {
        // Required empty public constructor
    }


//
//    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mothers_list, container, false);

        setHasOptionsMenu(true);

        progressBar = view.findViewById(R.id.progress_bar);


//        listView = view.findViewById(R.id.list_view);
        mothersRecylerView = view.findViewById(R.id.mother_recycler_view);

//        initMothers();
        fetchMothers();
//        setFirebaseListAdapter();


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_mothers_list_to_add_mother);
        });


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mothersRecylerView.setLayoutManager(layoutManager);


        listAdapter = new MothersListAdapter(mothers, getContext());
        mothersRecylerView.setAdapter(listAdapter);

        return view;
    }

        private void initMothers() {
            for (int i = 1;i<51;i++) {
                mothers.add(new Mother(20+i,"ሰብለወንጌል ገ/ስላሴ "+i, "0964432858"));
            }

            Log.d("Mothers", String.valueOf(mothers));
        }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView actionView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        actionView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
            case R.id.action_home:
                Toast.makeText(getContext(), "Home Sweet Home", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setFirebaseListAdapter() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mothersRef = database.getReference(Utility.PATH_MOTHERES);

        FirebaseListOptions.Builder<Mother> options = new FirebaseListOptions.Builder<>();
        options.setQuery(mothersRef,Mother.class);
        options.setLayout(R.layout.blueprint_mothers_list);
        options.setLifecycleOwner(this);

        firebaseListAdapter = new FirebaseListAdapter<Mother>(options.build()) {
            @Override
            protected void populateView(@NonNull View itemView, @NonNull Mother model, int position) {
                ImageView imageOptions, imageThumb;
                TextView fullname, phone;

                imageOptions = itemView.findViewById(R.id.img_popup);
//                imageThumb = itemView.findViewById(R.id.img_thumb);
                fullname = itemView.findViewById(R.id.fullname);
                phone = itemView.findViewById(R.id.phone);

                fullname.setText(model.getFullname());
                phone.setText(model.getPhone());

                imageOptions.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure to delete?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        mothers.remove(position);
                        notifyDataSetChanged();
                    });
                    builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                    AlertDialog dialog = builder.create();
                    dialog.setTitle("Alert!!");
                    dialog.setIcon(R.drawable.ic_warning);
                    dialog.show();

                });

                itemView.setOnClickListener(v -> {
                    Toast.makeText(getContext(), model.getFullname(), Toast.LENGTH_SHORT).show();

                    NavController navController = Navigation.findNavController(v);

                    Bundle bundle = new Bundle();
                    bundle.putString("mother", new Gson().toJson(model));

                    navController.navigate(R.id.action_mothers_list_to_mother_show_update_nav, bundle);

                });

            }
        };


        firebaseListAdapter.startListening();
        listView.setAdapter(firebaseListAdapter);
//        mothersRecylerView.setAdapter((RecyclerView.Adapter) firebaseListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
//        firebaseListAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
//        firebaseListAdapter.stopListening();
    }
}



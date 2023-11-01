package com.gonder.pregnancyhealthcare.ui.admin.physician;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.gonder.pregnancyhealthcare.adapters.PhysiciansListAdapter;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.Physician;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhysiciansListFragment extends Fragment implements androidx.appcompat.widget.SearchView.OnQueryTextListener{
    private ArrayList<Physician> physicians = new ArrayList<>();
    private PhysiciansListAdapter listAdapter;
    private RecyclerView mothersRecylerView;
    private ProgressBar progressBar;
    FirebaseListAdapter<Physician> firebaseListAdapter;
    ListView listView;

    public PhysiciansListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_physicians_list, container, false);

        setHasOptionsMenu(true);
        progressBar = view.findViewById(R.id.progress_bar);

//        listView = view.findViewById(R.id.list_view);
        mothersRecylerView = view.findViewById(R.id.mother_recycler_view);

//        setFirebaseAdapter();

//        initPhyscians();

        fetchPhysicians();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_physicians_list_to_add_physician);
        });


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mothersRecylerView.setLayoutManager(layoutManager);


        listAdapter = new PhysiciansListAdapter(physicians, getContext());
        mothersRecylerView.setAdapter(listAdapter);

        return view;
    }

    private void setFirebaseAdapter() {
        if(!FirebaseApp.getApps(getContext()).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        DatabaseReference motherRef = FirebaseDatabase
                .getInstance()
                .getReference(Utility.PATH_MOTHERES);

//        motherRef.keepSynced(true);

//        FirebaseListAdapter<Mother> listAdapter = new FirebaseListAdapter<Mother>(
//                getContext(),
//                R.layout.blueprint_mothers_list,
//        ) {
//            @Override
//            protected void populateView(@NonNull View v, @NonNull Mother model, int position) {
//
//            }
//        };

    }

    private void initPhyscians() {
        for (int i = 1;i<51;i++) {
            physicians.add(new Physician("Dr. Liya Tadessie "+i, "0964432858"));
        }
    }

    public void fetchPhysicians() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference docRef = database.getReference(Utility.PATH_PHYSICIANS);

        docRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                physicians = new ArrayList<>();
                for (DataSnapshot docsnapshot: snapshot.getChildren()) {
                    physicians.add(docsnapshot.getValue(Physician.class));
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Physician> list = new ArrayList<>();
        for (Physician physician: this.physicians) {
            if(physician.getFullname().toLowerCase().contains(newText.toLowerCase())) {
                list.add(physician);
            }
        }
        listAdapter.updateList(list);
        return true;
    }


    public void setFirebaseListAdapter() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference docRef = database.getReference(Utility.PATH_PHYSICIANS);

        FirebaseListOptions.Builder<Physician> options = new FirebaseListOptions.Builder<>();
        options.setQuery(docRef,Physician.class);
        options.setLayout(R.layout.blueprint_physican_list);
        options.setLifecycleOwner(this);

        firebaseListAdapter = new FirebaseListAdapter<Physician>(options.build()) {
            @Override
            protected void populateView(@NonNull View itemView, @NonNull Physician model, int position) {
                ImageView imageOptions,imageThumb;
                TextView fullname,phone;
//                Context context;
//                ArrayList<Physician> physicians;

                imageOptions = itemView.findViewById(R.id.img_popup);
                imageThumb = itemView.findViewById(R.id.img_thumb);
                fullname = itemView.findViewById(R.id.fullname);
                phone = itemView.findViewById(R.id.phone);

                fullname.setText(model.getFullname());
                phone.setText(model.getPhone());

                imageOptions.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure to delete?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        physicians.remove(position);
                        notifyDataSetChanged();
                    });
                    builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                    AlertDialog dialog = builder.create();
                    dialog.setTitle("Alert!!");
                    dialog.setIcon(R.drawable.ic_warning);
                    dialog.show();

                });

                itemView.setOnClickListener(v -> {
                    NavController navController = Navigation.findNavController(v);

                    Bundle bundle = new Bundle();
                    bundle.putString("physician",new Gson().toJson(model));

                    navController.navigate(R.id.action_physicians_list_to_physician_show_update_nav,bundle);

                });

            }
        };


        firebaseListAdapter.startListening();
        listView.setAdapter(firebaseListAdapter);
//        mothersRecylerView.setAdapter((RecyclerView.Adapter) firebaseListAdapter);
    }


}

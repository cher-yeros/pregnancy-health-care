package com.gonder.pregnancyhealthcare.ui.admin.technician;


import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.adapters.LabTechniciansListAdapter;
import com.gonder.pregnancyhealthcare.adapters.MothersListAdapter;
import com.gonder.pregnancyhealthcare.models.LabTechnician;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.Physician;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabTechniciansListFragment extends Fragment implements androidx.appcompat.widget.SearchView.OnQueryTextListener{

    private ArrayList<LabTechnician> labTechnicians = new ArrayList<>();
    private LabTechniciansListAdapter listAdapter;
    ProgressBar progressBar;
    public LabTechniciansListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lab_technicians_list, container, false);

        setHasOptionsMenu(true);

        progressBar = view.findViewById(R.id.progress_bar);
//        initPhyscians();
        fetchLabTechs();


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_technicians_list_to_add_technician);
        });

        RecyclerView mothersRecylerView = view.findViewById(R.id.mother_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mothersRecylerView.setLayoutManager(layoutManager);


        listAdapter = new LabTechniciansListAdapter(labTechnicians, getContext());
        mothersRecylerView.setAdapter(listAdapter);

        return view;
    }

    private void initPhyscians() {
        for (int i = 1;i<51;i++) {
            labTechnicians.add(new LabTechnician("Tech. ቡጣቆ "+i, "0964432858"));
        }
    }

    private void fetchLabTechs() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference labTechs = database.getReference(Utility.PATH_LABTECHNICIANS);

        labTechs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                labTechnicians = new ArrayList<>();
                for (DataSnapshot mSnapshot: snapshot.getChildren()) {
//                    Toast.makeText(getContext(), "Eziga dersual", Toast.LENGTH_SHORT).show();
                    labTechnicians.add(mSnapshot.getValue(LabTechnician.class));
                }
                listAdapter.updateList(labTechnicians);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "error "+error.getMessage(), Toast.LENGTH_SHORT).show();
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
        ArrayList<LabTechnician> list = new ArrayList<>();
        for (LabTechnician labTechnician: this.labTechnicians) {
            if(labTechnician.getFullname().toLowerCase().contains(newText.toLowerCase())) {
                list.add(labTechnician);
            }
        }
        listAdapter.updateList(list);
        return true;
    }

}

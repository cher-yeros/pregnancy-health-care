package com.gonder.pregnancyhealthcare.ui.admin.technician;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.gonder.pregnancyhealthcare.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LabTechnicianShowUpdateFragment extends Fragment {

    private ShowLabTechnicianFragment showLabTechnicianFragment;
    private UpdateLabTechnicianFragment updateLabTechnicianFragment;
    public LabTechnicianShowUpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lab_technician_show_update, container, false);

        BottomNavigationView bottomNavbar = view.findViewById(R.id.bottom_navbar);
        FrameLayout frameLayout = view.findViewById(R.id.frameLayout);
        showLabTechnicianFragment = new ShowLabTechnicianFragment();
        updateLabTechnicianFragment = new UpdateLabTechnicianFragment();



        initializeFragment(showLabTechnicianFragment);
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                switch (itemId) {
                    case R.id.show:
                        initializeFragment(showLabTechnicianFragment);
                        break;
                    case R.id.update:
                        initializeFragment(updateLabTechnicianFragment);
                        break;
                }
                return true;
            }
        });

        return view;
    }

    private void initializeFragment(Fragment fragment) {
        fragment.setArguments(this.getArguments());
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout,fragment);
        transaction.commit();
    }

}

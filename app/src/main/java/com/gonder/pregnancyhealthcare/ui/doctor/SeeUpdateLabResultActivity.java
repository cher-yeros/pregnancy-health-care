package com.gonder.pregnancyhealthcare.ui.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.gonder.pregnancyhealthcare.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SeeUpdateLabResultActivity extends AppCompatActivity {

    FrameLayout containerLayout;
    BottomNavigationView bottomNavbar;

    SeeLabResultFragment seeLabResultFragment;
    UpdateSendLabResultFragment updateSendLabResultFragment;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_update_lab_result);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Send Updated Report");


        containerLayout = findViewById(R.id.container_layout);
        bottomNavbar = findViewById(R.id.bottom_navbar);

        seeLabResultFragment = new SeeLabResultFragment();
        updateSendLabResultFragment = new UpdateSendLabResultFragment();

        initializeFragment(seeLabResultFragment);
        bottomNavbar.setOnNavigationItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();
            switch (itemId) {
                case R.id.lab_result:
                    initializeFragment(seeLabResultFragment);
                    break;
                case R.id.update_send:
                    initializeFragment(updateSendLabResultFragment);
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.clerk_toolbar_menu,menu);


        return true;
    }

    private void initializeFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("labResult",getIntent().getStringExtra("labResult"));
        fragment.setArguments(bundle);
//        assert getFragmentManager() != null;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_layout, fragment);
        transaction.commit();
    }
}

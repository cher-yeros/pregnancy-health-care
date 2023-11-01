package com.gonder.pregnancyhealthcare.ui.admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.gonder.pregnancyhealthcare.Login2Activity;
import com.gonder.pregnancyhealthcare.R;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.gonder.pregnancyhealthcare.util.SharedPrefConfig;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ClerkNavDrawer extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 1;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_nav_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(ContextCompat.checkSelfPermission(
                getApplicationContext(),
                Manifest.permission.INTERNET
        )
                != PackageManager.PERMISSION_GRANTED)
        {
            // Permission is not granted
            checkPermission(
                    Manifest.permission.INTERNET,
                    STORAGE_PERMISSION_CODE);
        }


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_edit_profile,
                R.id.nav_change_password,
                R.id.nav_tools,
                R.id.nav_logout,
                R.id.nav_version,
                R.id.mothers_list,
                R.id.add_mother,
                R.id.mother_show_update_nav,
                R.id.physicians_list,
                R.id.physician_show_update_nav,
                R.id.add_physician,
                R.id.technicians_list,
                R.id.technician_show_update_nav,
                R.id.add_technician,
                R.id.send_notification
        )
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_logout:
                    SharedPrefConfig config = new SharedPrefConfig(getApplicationContext());
                    config.deleteLoginInfo();
                    Intent intent = new Intent(getApplicationContext(), Login2Activity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_change_password:
//                    TODO : change password
                    break;
                case R.id.nav_edit_profile:
//                    TODO : Edit profile
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clerk_nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void checkPermission(String permission, int requestCode)
    {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(),
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            ClerkNavDrawer.this,
                            new String[] { permission },
                            requestCode);
        }
        else {
            Toast
                    .makeText(getApplicationContext(),
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }
}

package com.gonder.pregnancyhealthcare.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.adapters.ReceivedReportListAdapter;
import com.gonder.pregnancyhealthcare.models.Report;

import java.util.ArrayList;

public class ReceivedUpdateReportActivity extends AppCompatActivity {
    ArrayList<Report> reports = new ArrayList<>();
    Toolbar toolbar;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView receivedReportsRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_update_report);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initReports();
        getSupportActionBar().setTitle("Updated report");

        receivedReportsRV = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        receivedReportsRV.setLayoutManager(layoutManager);


        ReceivedReportListAdapter listAdapter = new ReceivedReportListAdapter(this, reports);
        receivedReportsRV.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.clerk_toolbar_menu,menu);


        return true;
    }

    private void initReports() {
        reports = new ArrayList<>();
        String report = "A material metaphor is the unifying theory of a rationalized space and a system of motion."+
                "The material is grounded in tactile reality, inspired by the study of paper and ink, yet "+
                "technologically advanced and open to imagination and magic.\n"+
                "Surfaces and edges of the material provide visual cues that are grounded in reality."+
                "world, without breaking the rules of physics. ";
        for (int i = 1;i< 15;i++) {
            Report report1 = new Report(i,report+i,"12/06/2012");
            report1.setReceiver("Mother "+i+" Father");
            reports.add(report1);
        }
    }
}

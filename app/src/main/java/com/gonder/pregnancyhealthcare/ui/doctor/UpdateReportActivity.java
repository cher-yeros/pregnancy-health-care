package com.gonder.pregnancyhealthcare.ui.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gonder.pregnancyhealthcare.R;

public class UpdateReportActivity extends AppCompatActivity {
    EditText report,title;
    Button assignMother,send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_report);

        report = findViewById(R.id.tf_report);
        title = findViewById(R.id.title);
        assignMother = findViewById(R.id.choose_mother);
        send = findViewById(R.id.btn_send_report);

        Intent intent = getIntent();
        String report = intent.getStringExtra("body");
        this.report.setText(report);
    }

    public void sendUpdatedReport(View view) {


    }


}

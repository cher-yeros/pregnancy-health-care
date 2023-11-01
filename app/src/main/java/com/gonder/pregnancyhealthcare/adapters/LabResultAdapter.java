package com.gonder.pregnancyhealthcare.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.LabResult;
import com.gonder.pregnancyhealthcare.models.LabTechnician;
import com.gonder.pregnancyhealthcare.models.Report;
import com.gonder.pregnancyhealthcare.ui.doctor.SeeUpdateLabResultActivity;
import com.gonder.pregnancyhealthcare.ui.doctor.UpdateReportActivity;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LabResultAdapter extends RecyclerView.Adapter<LabResultAdapter.ResultViewHolder>{
    Context context;
    ArrayList<LabResult> result;

    public LabResultAdapter(Context context, ArrayList<LabResult> result) {
        this.result = new ArrayList<>();
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public LabResultAdapter.ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.blueprint_lab_result_list, parent, false);

        return new LabResultAdapter.ResultViewHolder(view,context,result);
    }

    @Override
    public void onBindViewHolder(@NonNull LabResultAdapter.ResultViewHolder holder, int position) {
        TextView resNo = holder.resNo;
        TextView resDate = holder.resDate;
        TextView resSubject = holder.resSubject;

        long date = result.get(position).getDate();


        resNo.setText("Result : " + result.get(position).getId());
        resDate.setText(Utility.toETc(result.get(position).getDate()).getMonthGeez());
        resSubject.setText(result.get(position).getmId());
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        TextView resNo,resDate,resSubject;
        Context context;
        ArrayList<LabResult> result;

        public ResultViewHolder(@NonNull View itemView,Context context,ArrayList<LabResult> result) {
            super(itemView);
            this.context = context;
            this.result = result;
            resNo = itemView.findViewById(R.id.result_no);
            resDate = itemView.findViewById(R.id.date);
            resSubject = itemView.findViewById(R.id.subject);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            LabResult report = result.get(getAdapterPosition());
            Intent intent = new Intent(context, SeeUpdateLabResultActivity.class);
            intent.putExtra("labResult", new Gson().toJson(report));

            context.startActivity(intent);
        }
    }
}

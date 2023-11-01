package com.gonder.pregnancyhealthcare.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.Report;
import com.gonder.pregnancyhealthcare.ui.doctor.UpdateReportActivity;

import java.util.ArrayList;

public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.ReportViewHolder>{
    Context context;
    ArrayList<Report> reports;

    public ReportListAdapter(Context context, ArrayList<Report> reports) {
        this.reports = new ArrayList<>();
        this.context = context;
        this.reports = reports;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.blueprint_reports_list, parent, false);

        return new ReportViewHolder(view,context,reports);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        TextView repNo = holder.repNo;
        TextView repDate = holder.repDate;
        TextView repSubject = holder.repSubject;

        repNo.setText("Report : " + reports.get(position).getNo());
        repDate.setText(reports.get(position).getDate());
        repSubject.setText(reports.get(position).getReceiver());
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        TextView repNo,repDate,repSubject;
        Context context;
        ArrayList<Report> reports;

        public ReportViewHolder(@NonNull View itemView,Context context,ArrayList<Report> reports) {
            super(itemView);
            this.context = context;
            this.reports = reports;
            repNo = itemView.findViewById(R.id.report_no);
            repDate = itemView.findViewById(R.id.date);
            repSubject = itemView.findViewById(R.id.subject);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int i = getAdapterPosition();
            Intent intent = new Intent(context, UpdateReportActivity.class);
            Report report = reports.get(i);
            intent.putExtra("body", report.getBody());

            context.startActivity(intent);
        }
    }
}

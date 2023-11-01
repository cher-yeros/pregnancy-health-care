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
import com.gonder.pregnancyhealthcare.models.LabResult;
import com.gonder.pregnancyhealthcare.models.Notification;
import com.gonder.pregnancyhealthcare.ui.doctor.SeeUpdateLabResultActivity;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CareNotificationAdapter extends RecyclerView.Adapter<CareNotificationAdapter.ResultViewHolder>{
    Context context;
    ArrayList<Notification> result;

    public CareNotificationAdapter(Context context, ArrayList<Notification> result) {
        this.result = new ArrayList<>();
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public CareNotificationAdapter.ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.blueprint_lab_result_list, parent, false);

        return new CareNotificationAdapter.ResultViewHolder(view,context,result);
    }


    @Override
    public void onBindViewHolder(@NonNull CareNotificationAdapter.ResultViewHolder holder, int position) {
        TextView resNo = holder.resNo;
        TextView resDate = holder.resDate;
        TextView resSubject = holder.resSubject;

        long date = result.get(position).getDate();


        resNo.setText("Result : " + result.get(position).getId());
        resDate.setText(Utility.toETc(result.get(position).getDate()).getMonthGeez());
//        resSubject.setText(result.get(position).getmId());
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        TextView resNo,resDate,resSubject;
        Context context;
        ArrayList<Notification> result;

        public ResultViewHolder(@NonNull View itemView,Context context,ArrayList<Notification> result) {
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
//            Notification report = result.get(getAdapterPosition());
//            Intent intent = new Intent(context, SeeUpdateLabResultActivity.class);
//            intent.putExtra("notification", new Gson().toJson(report));
//
//            context.startActivity(intent);
//            TODO: what happens if one of notification is clicked
        }
    }
}

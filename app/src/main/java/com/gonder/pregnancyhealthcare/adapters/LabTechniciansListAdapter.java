package com.gonder.pregnancyhealthcare.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.LabTechnician;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LabTechniciansListAdapter extends RecyclerView.Adapter<LabTechniciansListAdapter.TechnicianViewHolder>{
    private ArrayList<LabTechnician> labTechnicians;
    private Context context;

    public LabTechniciansListAdapter(ArrayList<LabTechnician> labTechnicians, Context context) {
        this.labTechnicians = labTechnicians;
        this.context = context;
    }

    @NonNull
    @Override
    public LabTechniciansListAdapter.TechnicianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.blueprint_technician_list, parent, false);

        LabTechniciansListAdapter.TechnicianViewHolder viewHolder = new LabTechniciansListAdapter.TechnicianViewHolder(view, labTechnicians,context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LabTechniciansListAdapter.TechnicianViewHolder holder, int position) {
//        ImageView imageThumb = holder.imageThumb;
        ImageView imageOptions = holder.imageOptions;
        TextView fullname = holder.fullname;
        TextView phone = holder.phone;

        fullname.setText(labTechnicians.get(position).getFullname());
        phone.setText(labTechnicians.get(position).getPhone());

        imageOptions.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure to delete?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                labTechnicians.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.setTitle("Alert!!");
            dialog.setIcon(R.drawable.ic_warning);
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return labTechnicians.size();
    }

    public static class TechnicianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageOptions,imageThumb;
        TextView fullname,phone;
        Context context;
        ArrayList<LabTechnician> labTechnicians;

        public TechnicianViewHolder(@NonNull View itemView, ArrayList<LabTechnician> labTechnicians, Context context) {
            super(itemView);
            this.labTechnicians = labTechnicians;
            this.context = context;


            imageOptions = itemView.findViewById(R.id.img_popup);
            imageThumb = itemView.findViewById(R.id.img_thumb);
            fullname = itemView.findViewById(R.id.fullname);
            phone = itemView.findViewById(R.id.phone);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            LabTechnician labTechnician = labTechnicians.get(getAdapterPosition());
            NavController navController = Navigation.findNavController(v);

            Bundle bundle = new Bundle();
            bundle.putString("technician",new Gson().toJson(labTechnician));

            navController.navigate(R.id.action_technicians_list_to_technician_show_update_nav,bundle);
        }
    }

    public void updateList(ArrayList<LabTechnician> newList){
        labTechnicians = newList;
        notifyDataSetChanged();
    }
}

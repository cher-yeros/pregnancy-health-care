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
import com.gonder.pregnancyhealthcare.models.Physician;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PhysiciansListAdapter extends RecyclerView.Adapter<PhysiciansListAdapter.PhysicianViewHolder>{
    private ArrayList<Physician> physicians;
    private Context context;

    public PhysiciansListAdapter(ArrayList<Physician> physicians, Context context) {
        this.physicians = physicians;
        this.context = context;
    }

    @NonNull
    @Override
    public PhysiciansListAdapter.PhysicianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.blueprint_physican_list, parent, false);

        PhysiciansListAdapter.PhysicianViewHolder viewHolder = new PhysiciansListAdapter.PhysicianViewHolder(view, physicians,context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhysiciansListAdapter.PhysicianViewHolder holder, int position) {
//        ImageView imageThumb = holder.imageThumb;
        ImageView imageOptions = holder.imageOptions;
        TextView fullname = holder.fullname;
        TextView phone = holder.phone;

        fullname.setText(physicians.get(position).getFullname());
        phone.setText(physicians.get(position).getPhone());

        imageOptions.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure to delete?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                physicians.remove(holder.getAdapterPosition());
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
        return physicians.size();
    }

    public static class PhysicianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageOptions,imageThumb;
        TextView fullname,phone;
        Context context;
        ArrayList<Physician> physicians;

        public PhysicianViewHolder(@NonNull View itemView, ArrayList<Physician> physicians, Context context) {
            super(itemView);
            this.physicians = physicians;
            this.context = context;

            imageOptions = itemView.findViewById(R.id.img_popup);
            imageThumb = itemView.findViewById(R.id.img_thumb);
            fullname = itemView.findViewById(R.id.fullname);
            phone = itemView.findViewById(R.id.phone);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Physician physician = physicians.get(getAdapterPosition());
            NavController navController = Navigation.findNavController(v);

            Bundle bundle = new Bundle();
            bundle.putString("physician",new Gson().toJson(physician));

            navController.navigate(R.id.action_physicians_list_to_physician_show_update_nav,bundle);
        }
    }

    public void updateList(ArrayList<Physician> newList){
        physicians = newList;
        notifyDataSetChanged();
    }
}

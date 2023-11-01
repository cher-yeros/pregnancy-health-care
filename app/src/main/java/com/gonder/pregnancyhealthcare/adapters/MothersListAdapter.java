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
import com.gonder.pregnancyhealthcare.models.Mother;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MothersListAdapter extends RecyclerView.Adapter<MothersListAdapter.MyViewHolder> {
    private ArrayList<Mother> mothers;
    private Context context;

    public MothersListAdapter(ArrayList<Mother> mothers, Context context) {
        this.mothers = mothers;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.blueprint_mothers_list, parent, false);

        return new MyViewHolder(view,mothers,context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        ImageView imageThumb = holder.imageThumb;
        ImageView imageOptions = holder.imageOptions;
        TextView fullname = holder.fullname;
        TextView phone = holder.phone;

        fullname.setText(mothers.get(position).getFullname());
        phone.setText(mothers.get(position).getPhone());

        imageOptions.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure to delete?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                mothers.remove(holder.getAdapterPosition());
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
        return mothers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageOptions,imageThumb;
        TextView fullname,phone;
        Context context;
        ArrayList<Mother> mothers;

        public MyViewHolder(@NonNull View itemView, ArrayList<Mother> mothers, Context context) {
            super(itemView);
            this.mothers = mothers;
            this.context = context;


            imageOptions = itemView.findViewById(R.id.img_popup);
            imageThumb = itemView.findViewById(R.id.img_thumb);
            fullname = itemView.findViewById(R.id.fullname);
            phone = itemView.findViewById(R.id.phone);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Mother mother = mothers.get(getAdapterPosition());
            NavController navController = Navigation.findNavController(v);

            Bundle bundle = new Bundle();
            bundle.putString("mother",new Gson().toJson(mother));

            navController.navigate(R.id.action_mothers_list_to_mother_show_update_nav,bundle);
        }


    }


    public void updateList(ArrayList<Mother> newList){
        mothers = newList;
        notifyDataSetChanged();
    }

}

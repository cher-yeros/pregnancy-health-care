package com.gonder.pregnancyhealthcare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.Notification;

import java.util.ArrayList;

public class NotificationnListAdapter extends RecyclerView.Adapter<NotificationnListAdapter.MyViewHolder>{

    private ArrayList<Notification> notifications;
    private Context context;

    public NotificationnListAdapter(ArrayList<Notification> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationnListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.blueprint_view_mother_list, parent, false);

        return new NotificationnListAdapter.MyViewHolder(view,notifications,context);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationnListAdapter.MyViewHolder holder, int position) {
//        ImageView imageThumb = holder.imageThumb;
//        ImageView imageOptions = holder.imageOptions;
        TextView sender = holder.sender;
        TextView title = holder.title;

        sender.setText(notifications.get(position).getSenderId());
        title.setText(notifications.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title,sender;
        Context context;
        ArrayList<Notification> notifications;

        public MyViewHolder(@NonNull View itemView, ArrayList<Notification> notifications, Context context) {
            super(itemView);
            this.notifications = notifications;
            this.context = context;


//            imageOptions = itemView.findViewById(R.id.img_popup);
//            imageThumb = itemView.findViewById(R.id.img_thumb);
            title = itemView.findViewById(R.id.title);
            sender = itemView.findViewById(R.id.sender);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Notification notification = notifications.get(getAdapterPosition());
            Toast.makeText(context, notification.getTitle(), Toast.LENGTH_SHORT).show();
//            Todo # : dialog alert

        }


    }


    public void updateList(ArrayList<Notification> newList){
        notifications = newList;
        notifyDataSetChanged();
    }
}

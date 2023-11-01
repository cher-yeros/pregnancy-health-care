package com.gonder.pregnancyhealthcare.ui.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.Notification;
import com.google.gson.Gson;

public class NotificationDetailDialog extends DialogFragment {
    private TextView date,title,body,sender;
    private Notification notification;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        Bundle bundle = this.getArguments();

        notification = new Gson().fromJson(bundle.getString("notification"),Notification.class);


        View view = inflater.inflate(R.layout.fragment_notification_detail, null);

        date = view.findViewById(R.id.date);
        title = view.findViewById(R.id.title);
        sender = view.findViewById(R.id.sender);
        body = view.findViewById(R.id.body);

        date.setText(String.valueOf(notification.getDate()));
        title.setText(notification.getTitle());
        sender.setText(notification.getSenderId());
        body.setText(notification.getBody());

        builder.setView(view);

        builder.setPositiveButton("Go back", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.create();

        return builder.create();
    }


}

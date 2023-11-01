package com.gonder.pregnancyhealthcare.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.ui.admin.ReceivedUpdateReportActivity;

public class AdminLandingFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private CardView manage,register,sendReport,getNotify,broadcast,getReport;
    private CardView sendNotification;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.activity_clerk_landing, container, false);

        manage = root.findViewById(R.id.manage_accounts);
        sendNotification = root.findViewById(R.id.send_notification);
//        register = root.findViewById(R.id.register);
//        sendReport = root.findViewById(R.id.send_updated_report);
//        getNotify = root.findViewById(R.id.receive_updated_report);
        broadcast = root.findViewById(R.id.send_updated_report);
//        getReport = root.findViewById(R.id.get_report);

        manage.setOnClickListener(this::manageAcount);
        sendNotification.setOnClickListener(this::sendNotification);
//        register.setOnClickListener(this::register);
//        sendReport.setOnClickListener(this::sendReport);
//        getNotify.setOnClickListener(this::receiveUpdatedReport);
        broadcast.setOnClickListener(this::broadcast);
//        getReport.setOnClickListener(this::getReport);


        return root;
    }

    private void sendNotification(View view) {
        NavController navController = Navigation.findNavController(root);
        navController.navigate(R.id.action_nav_home_to_send_notification);

    }

    public void manageAcount(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        MenuInflater menuInflater = popupMenu.getMenuInflater();

        menuInflater.inflate(R.menu.manage_account_menu,popupMenu.getMenu());
        popupMenu.show();
    }


    public void register(View view) {
        Toast.makeText(getContext(),"Register",Toast.LENGTH_SHORT).show();
    }

    public void broadcast(View view) {
        Toast.makeText(getContext(),"Broadcast",Toast.LENGTH_SHORT).show();
    }

    public void getReport(View view) {
        Toast.makeText(getContext(),"Get Report",Toast.LENGTH_SHORT).show();
    }

    public void sendReport(View view) {
        Toast.makeText(getContext(),"Send Report",Toast.LENGTH_SHORT).show();
    }

    private void receiveUpdatedReport(View view) {
        Intent intent = new Intent(getContext(), ReceivedUpdateReportActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        NavController navController = Navigation.findNavController(root);
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.doctor:
                navController.navigate(R.id.action_nav_home_to_physicians_list);
                break;
            case R.id.mother:
                navController.navigate(R.id.action_nav_home_to_mothers_list);
                break;
            case R.id.technician:
                navController.navigate(R.id.action_nav_home_to_technicians_list);
                break;
        }
        return true;
    }


}
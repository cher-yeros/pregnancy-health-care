package com.gonder.pregnancyhealthcare.ui.admin.mother;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.Mother;
import com.gonder.pregnancyhealthcare.models.Physician;
import com.gonder.pregnancyhealthcare.models.SearchPhysicianModel;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMotherFragment extends Fragment implements PopupMenu.OnMenuItemClickListener{


    private ProgressBar progressBar;

    public AddMotherFragment() {

        // Required empty public constructor
    }

    private Button assignPhysician,registerBtn,chooseStatus;
    private EditText fullname,phone,cPassword,age,username,password;
    private Physician assignedPhysician;
    private String sFullname,sPhone,sPassword,sUsername,sCPassword,sAge,sPhyBtn,sStatus;
    private ArrayList<SearchPhysicianModel> physiciansSearchModel = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_add_mother, container, false);

        physiciansSearchModel = fetchPhysicians();

        assignPhysician = view.findViewById(R.id.assign_physician);
        registerBtn = view.findViewById(R.id.btn_register);
        chooseStatus = view.findViewById(R.id.choose_status);

        fullname = view.findViewById(R.id.fullname);
        phone = view.findViewById(R.id.phone);
        username = view.findViewById(R.id.username);
        age = view.findViewById(R.id.age);
        password = view.findViewById(R.id.password);
        cPassword = view.findViewById(R.id.confirm_password);
        progressBar = view.findViewById(R.id.progress_bar);

        assignPhysician.setOnClickListener(this::assignPhysician);
        chooseStatus.setOnClickListener(this::chooseStatus);
        registerBtn.setOnClickListener(this::registerUser);

        return view;
    }

    private void chooseStatus(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        MenuInflater menuInflater = popupMenu.getMenuInflater();

        menuInflater.inflate(R.menu.choose_status_menu,popupMenu.getMenu());

        popupMenu.show();
    }

    private void assignPhysician(View view) {
        new SimpleSearchDialogCompat(
                getContext(),
                "Search physician",
                "type name of the physician...",
                null,
                physiciansSearchModel,
                (SearchResultListener<Searchable>) (dialog, item, position) -> {
                    assignPhysician.setText(item.getTitle());
                    SearchPhysicianModel item1 = (SearchPhysicianModel) item;
                    assignedPhysician = item1.getPhysician();
                    dialog.dismiss();
                }).show();


//        Toast.makeText(getContext(), "Check out the library", Toast.LENGTH_SHORT).show();
    }

    private ArrayList<SearchPhysicianModel> initPhysician() {
        ArrayList<SearchPhysicianModel> array = new ArrayList<>();
        for(int i = 10;i<50;i++) {
            Physician physician = new Physician();
            physician.setFullname("Taddese "+i);
            physician.setPhone("0964432858");
            physician.setUsername("liyataddese"+(i+102));

            SearchPhysicianModel model = new SearchPhysicianModel(physician);
            array.add(model);
        }
//        return array;


//        progressBar.setVisibility(View.VISIBLE);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference docRef = database.getReference(Utility.PATH_PHYSICIANS);
//
//        docRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot docsnapshot: snapshot.getChildren()) {
//                    array.add(new SearchPhysicianModel(docsnapshot.getValue(Physician.class)));
//                }
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
//            }
//        });

        return array;
    }

    private void registerUser(View view) {
        sFullname = fullname.getText().toString().trim();
        sAge = age.getText().toString();
        sPhone = phone.getText().toString().trim();
        sPassword = password.getText().toString().trim();
        sUsername = username.getText().toString().trim();
        sPhyBtn = assignPhysician.getText().toString();
        sStatus = chooseStatus.getText().toString();
        sCPassword = cPassword.getText().toString().trim();

        if(!validate()){
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        registerBtn.setVisibility(View.INVISIBLE);

//        LocalDate now = LocalDate.now();

        Mother mother = new Mother();
        mother.setFullname(sFullname);
        mother.setAge(Integer.parseInt(sAge));
        mother.setPhone(sPhone);
        mother.setPassword(sPassword);
        mother.setUsername(sUsername);
        mother.setPhyId(assignedPhysician.getUsername());
        mother.setStatus(sStatus);
        mother.setRegDate(new Date().getTime());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference motherRef = database.getReference(Utility.PATH_MOTHERES);
        DatabaseReference docRef = database.getReference(Utility.PATH_PHYSICIANS);


//        DatabaseReference mChild = motherRef.child(mId);

        motherRef.child(sUsername).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(getContext(), "Username already exist", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    registerBtn.setVisibility(View.VISIBLE);

                }
                else {
                    motherRef.child(sUsername).setValue(mother).addOnSuccessListener(aVoid -> {
                        docRef.child(assignedPhysician.getUsername())
                                .child(Utility.PATH_MOTHERES)
                                .child(sUsername).setValue(mother);

                        progressBar.setVisibility(View.GONE);
                        registerBtn.setVisibility(View.VISIBLE);

                        Toast.makeText(getContext(), "Already Registered", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



}

    public ArrayList<SearchPhysicianModel> fetchPhysicians() {
        ArrayList<SearchPhysicianModel> physicians = new ArrayList<>();
//        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference docRef = database.getReference(Utility.PATH_PHYSICIANS);

        docRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot docsnapshot: snapshot.getChildren()) {
                    physicians.add(new SearchPhysicianModel(docsnapshot.getValue(Physician.class)));
                }
//                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });

        return physicians;
    }

    private boolean validate() {
        boolean valid = false;
        if (sFullname.isEmpty()) {
            Utility.showToast(getContext(),getString(R.string.fullname_cannot_be_empty));
        } else if (sPhone.isEmpty()) {
            Utility.showToast(getContext(),getString(R.string.phone_cannot_be_empty));
        } else if (sAge.isEmpty() || Integer.parseInt(sAge) < 0) {
            Utility.showToast(getContext(),getString(R.string.age_cannot_be_empty));
        } else if (sUsername.isEmpty()) {
            Utility.showToast(getContext(),getString(R.string.username_cannot_be_empty));
        } else if (sUsername.length() < 6) {
            Utility.showToast(getContext(),getString(R.string.username_cannot_be_lessthan_6));
        } else if (sPassword.isEmpty()) {
            Utility.showToast(getContext(),getString(R.string.password_cannot_be_empty));
        } else if (sPassword.length() < 6) {
            Utility.showToast(getContext(),getString(R.string.password_cannot_be_lessthan_6));
        } else if (!sPassword.equals(sCPassword)) {
            Utility.showToast(getContext(),getString(R.string.password_and_c_password_must_match));
        }
        else if (sPhyBtn.equals(getString(R.string.assign_physician))) {
            Utility.showToast(getContext(),getString(R.string.physician_must_be_selected));
        }
        else if (sStatus.equals(getString(R.string.choose_status))) {
            Utility.showToast(getContext(),getString(R.string.status_must_be_selected));
        } else {
            valid = true;
        }
        return valid;
    }



    @Override
    public boolean onMenuItemClick(MenuItem item) {
        chooseStatus.setText(item.getTitle());
        return true;
    }

    // Function to check and request permission

}

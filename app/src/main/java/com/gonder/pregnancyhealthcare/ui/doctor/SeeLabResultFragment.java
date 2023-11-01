package com.gonder.pregnancyhealthcare.ui.doctor;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gonder.pregnancyhealthcare.R;
import com.gonder.pregnancyhealthcare.models.LabResult;
import com.gonder.pregnancyhealthcare.util.Utility;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeeLabResultFragment extends Fragment {


    public SeeLabResultFragment() {
        // Required empty public constructor
    }

    private View view;
    private LabResult labResult;
    private TextView name,body,date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_see_lab_result, container, false);
        labResult = new Gson().fromJson(this.getArguments().getString("labResult"), LabResult.class);

        name = view.findViewById(R.id.mother_name);
        body = view.findViewById(R.id.body);
        date = view.findViewById(R.id.date);

        name.setText(labResult.getmId());
        body.setText(labResult.getBody());
        date.setText(Utility.toETc(labResult.getDate()).getMonthGeez());

        return view;
    }

}

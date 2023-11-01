package com.gonder.pregnancyhealthcare.ui.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gonder.pregnancyhealthcare.R;

import java.util.ArrayList;
import java.util.Arrays;

public class DueDateCalculatorDialog extends DialogFragment {

    TextView date;
    private Spinner spMonth,spDay,spYear;

    String[] months = {"መስከረም","ጥቅምት","ኅዳር","ታኅሳስ","ጥር","የካቲት","መጋቢት","ሚያዝያ","ግንቦት","ሰኔ","ኃምሌ","ነሐሴ","ጷጉሜን"};
    int posM,posD,posY;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_due_date_calculator, null);

        Button btnCalculate = view.findViewById(R.id.btn_calculate);
        date = view.findViewById(R.id.tv_date);
        spMonth = view.findViewById(R.id.month);
        spDay = view.findViewById(R.id.day);
        spYear = view.findViewById(R.id.year);
        btnCalculate.setOnClickListener(this::calculateDueDate);

        ArrayList<String> monthsal = new ArrayList<>(Arrays.asList(months));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.style_spinner, monthsal);
        spMonth.setAdapter(adapter);

        ArrayList<Integer> years = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            years.add(2000+i);
        }
        ArrayAdapter<Integer> adapterY = new ArrayAdapter<>(getContext(), R.layout.style_spinner, years);
        spYear.setAdapter(adapterY);

        ArrayList<Integer> days = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
//            days.set(i, 1+i);
            days.add(i+1);
        }
//        ArrayList<int[]> dayAl = new ArrayList<>(Arrays.asList(days));
        ArrayAdapter<Integer> adapterD = new ArrayAdapter<>(getContext(), R.layout.style_spinner, days);
        spDay.setAdapter(adapterD);


//        spDay.setOnItemClickListener((parent, view1, position, id) -> {
//            Toast.makeText(getContext(), days.get(position).toString(), Toast.LENGTH_SHORT).show();
//        });

//        spDay.getPrompt();

        builder.setView(view);

        builder.setPositiveButton("Go back", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.create();

        return builder.create();
    }

    private void calculateDueDate(View view) {
        String month = spMonth.getSelectedItem().toString();
        int day = Integer.parseInt(spDay.getSelectedItem().toString());
        int year = Integer.parseInt(spYear.getSelectedItem().toString());

        int monthN = 1;

        for (int i = 0; i < months.length; i++) {
            if (month.equals(months[i])) {
                monthN = i + 1;
            }
        }

        day += 7;
        monthN += 8; //3
//        year++;


//        Toast.makeText(getContext(), ""+monthN, Toast.LENGTH_SHORT).show();

        if(day > 30) {
            monthN++;
            day = day - 30; //35
        }

        if (monthN > 13) { //
            year++;
            monthN = monthN - 13;
        }

        if(monthN == 13 && day > 6) {
            year++;
            day = day - 6;
//            TODO: ቋግሜ thing
        }



        String month1 = months[monthN - 1];
        String s = month1 + ", " + day + " " + year;
        date.setText(s);

    }


}

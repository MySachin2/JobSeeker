package com.phacsin.jobseeker.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.phacsin.jobseeker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OptionalDetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog datePickerDialog;
    EditText dob_edittext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_optional_details, container, false);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), this,newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        dob_edittext = rootView.findViewById(R.id.dob_edittext);
        dob_edittext.setInputType(InputType.TYPE_NULL);
        dob_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        return rootView;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, dayOfMonth);
        dob_edittext.setText(dateFormatter.format(newDate.getTime()));
    }
}

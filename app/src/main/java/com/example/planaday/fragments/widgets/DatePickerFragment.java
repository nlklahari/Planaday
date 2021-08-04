package com.example.planaday.fragments.widgets;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;

import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.planaday.R;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    /**
     * String format of a date
     */
    public static final String DATE_FORMAT = "MM/dd/yyyy";

    private OnSuccessListener<DatePickerFragment> listener;
    private String dateString;

    public DatePickerFragment(OnSuccessListener<DatePickerFragment> listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dpd = new DatePickerDialog(getContext(), R.style.DialogTheme, this, year, month, day);
        dpd.getDatePicker().setMinDate(c.getTimeInMillis()); // sets the min date to today
        return dpd;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateString = "";
        if (month < 10) {
            dateString = "0";
        }
        dateString += (month + 1) + "/";
        if (dayOfMonth < 10) {
            dateString+= "0";
        }
        dateString += dayOfMonth + "/" + year;
        listener.onSuccess(this);
    }

    /**
     * Sets the TextView with the string format of the date
     * @return
     */
    public void setTVField(TextView field1) {
        Log.i("DatePickerFragment", "setting textview to selected date");
        field1.setText(dateString);
    }

}

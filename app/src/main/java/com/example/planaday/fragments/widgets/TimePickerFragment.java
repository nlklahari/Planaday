package com.example.planaday.fragments.widgets;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.planaday.R;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TextView field;
    private int[] time;

    public TimePickerFragment(TextView field) {
        this.field = field;
        time = new int[2];
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getContext(), R.style.DialogTheme, this, hour, minute,
                true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String stringTime = "";
        if (hourOfDay < 10) {
            stringTime += 0;
        }
        stringTime += hourOfDay + ":";
        if (minute < 10) {
            stringTime += 0;
        }
        time[0] = hourOfDay;
        time[1] = minute;
        stringTime += String.valueOf(minute);
        field.setText(stringTime);
    }

    public int[] getTime() {
        return time;
    }
}

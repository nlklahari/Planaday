package com.example.planaday.fragments.widgets;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.planaday.R;
import com.google.android.gms.tasks.OnSuccessListener;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private OnSuccessListener<TimePickerFragment> listener;
    private String timeString;

    public TimePickerFragment(OnSuccessListener<TimePickerFragment> listener) {
        this.listener = listener;
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
        timeString = "";
        if (hourOfDay < 10) {
            timeString += 0;
        }
        timeString += hourOfDay + ":";
        if (minute < 10) {
            timeString += 0;
        }
        timeString += String.valueOf(minute);
        listener.onSuccess(this);
    }

    /**
     * Sets the TextView with the string format of the date
     * @return
     */
    public void setTVField(TextView field1) {
        Log.i("TimePickerFragment", "setting textview to selected time");
        field1.setText(timeString);
    }

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDuration(String startTime, String endTime) {
        int startHour = Integer.parseInt(startTime.substring(0,2));
        int endHour = Integer.parseInt(endTime.substring(0,2));
        return endHour - startHour;
    }
}

package com.taekung.nady.el_3yada;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Taekunger on 5/23/2016.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TextView activity_edittext;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),this,hours,minutes,true);
    }

    public void setTimeTextView(TextView activity_edittext) {
        this.activity_edittext = activity_edittext;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.activity_edittext.setText(hourOfDay + " : " + minute);
    }
}

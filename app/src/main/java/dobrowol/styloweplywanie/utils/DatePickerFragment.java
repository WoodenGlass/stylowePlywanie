package dobrowol.styloweplywanie.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import dobrowol.styloweplywanie.R;

/**
 * Created by dobrowol on 03.12.17.
 */

public class DatePickerFragment extends Dialog
        implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    OnDateSelectedListener mCallback;

    public DatePickerFragment(@NonNull Context context) {
        super(context);
    }

    public DatePickerFragment(@NonNull Context context, OnDateSelectedListener listener) {
        super(context);
        setContentView(R.layout.dialog_datepicker);
        mCallback = listener;

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.datePickerButton);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.datePickerButton:
                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
                Calendar date = Calendar.getInstance();
                date.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                mCallback.onDateSelected(date);
                dismiss();
        }
    }

    // Container Activity must implement this interface
    public interface OnDateSelectedListener {
        public void onDateSelected(Calendar dateOfBirth);
    }


    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.set(year, month, day);
        if (mCallback != null)
            mCallback.onDateSelected(dateOfBirth);
    }
}
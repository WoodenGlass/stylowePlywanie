package dobrowol.styloweplywanie.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by dobrowol on 03.12.17.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    OnDateSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnDateSelectedListener {
        public void onDateSelected(Calendar dateOfBirth);
    }
    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        Log.d("DUPA","DUPA DatePickerFragment onAttach Activity");
        try {

            mCallback = (OnDateSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(mCallback.toString()
                    + " must implement SellFragmentListener");
        }
    }
    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        Log.d("DUPA","DUPA DatePickerFragment onAttach");
        try {
            Activity activity = (Activity) context;
            mCallback = (OnDateSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(mCallback.toString()
                    + " must implement SellFragmentListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.set(year, month, day);
        if (mCallback != null)
            mCallback.onDateSelected(dateOfBirth);
    }
}
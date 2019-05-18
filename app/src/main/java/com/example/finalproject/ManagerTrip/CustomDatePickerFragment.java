package com.example.finalproject.ManagerTrip;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;

import java.util.Calendar;

public class CustomDatePickerFragment extends DialogFragment {

    OnDataPassStart dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPassStart)context;
    }

    public  interface OnDataPassStart{
        public void onDataPassStart(String data);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Toast.makeText(getActivity(), "The selected date is: "
                    + view.getDayOfMonth()
                    + "/" + (view.getMonth() + 1)
                    + "/" + view.getYear(), Toast.LENGTH_SHORT).show();
            String startDate = view.getDayOfMonth() + "/" + (view.getMonth() + 1) + "/" + view.getYear();
            dataPasser.onDataPassStart(startDate);
            TextView mTextViewStartDate;
            mTextViewStartDate = getActivity().findViewById(R.id.textViewStartDate);
            mTextViewStartDate.setText(startDate);
        }
    };
}
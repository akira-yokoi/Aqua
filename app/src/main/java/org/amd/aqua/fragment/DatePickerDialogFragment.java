package org.amd.aqua.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Akira on 2018/04/20.
 */

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String ARG_DATE = "date";

    public static DatePickerDialogFragment newInstance(Date date) {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Date date = null;
        if (getArguments() != null) {
            date = (Date) getArguments().getSerializable(ARG_DATE);
        }

        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        List<Integer> result = new ArrayList<>();
        result.add(year);
        result.add(month);
        result.add(day);
        ((FragmentActionListener) getActivity()).onSelect(this, result);
    }
}
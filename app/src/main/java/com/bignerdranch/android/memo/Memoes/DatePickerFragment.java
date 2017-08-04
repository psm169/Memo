package com.bignerdranch.android.memo.Memoes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import com.bignerdranch.android.memo.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by realbyte on 2017. 7. 11..
 */

public class DatePickerFragment extends DialogFragment {

    public  static final  String EXTRA_DATE = "com.bignerdranch.android.memodata.memodate";

    private Date mDate;

    public static  DatePickerFragment newInstance(Date date)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE,date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        int day= calendar.get(Calendar.DAY_OF_MONTH);


        View v = getActivity().getLayoutInflater().inflate(R.layout.datepicker,null);

        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();

                getArguments().putSerializable(EXTRA_DATE,mDate);
            }
        });

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.settiong_date_title).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
        {public void onClick(DialogInterface dialog, int which){
            sendResult(Activity.RESULT_OK);
        }
        }).create();
    }

    //목표ㅡ래그먼트 메서드 호출
    private void sendResult(int resultCode)
    {
        if(getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,i);

    }

}

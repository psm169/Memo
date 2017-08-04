package com.bignerdranch.android.memo.Categories;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bignerdranch.android.memo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * Created by realbyte on 2017. 7. 25..
 */

public class MemoListButtonFragment extends Fragment {

    private Button leftButton;
    private Button rightButton;
    private Button centerButton;
    public static Calendar calendar;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.list_fragment_memo_button,parent,false);

        leftButton = (Button)v.findViewById(R.id.memo_list_fragment_cal_left);
        rightButton = (Button)v.findViewById(R.id.memo_list_fragment_cal_right);
        centerButton = (Button)v.findViewById(R.id.memo_list_fragment_cal_center);

        calendar = Calendar.getInstance();
        centerButton.setText(calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH )+1));


        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.set(calendar.get(Calendar.YEAR) , (calendar.get(Calendar.MONTH )-1) ,calendar.get(Calendar.DAY_OF_MONTH));
                centerButton.setText(calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH)+1));
            }
        });
        leftButton.setEnabled(true);

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.set(calendar.get(Calendar.YEAR) , (calendar.get(Calendar.MONTH )+1) ,calendar.get(Calendar.DAY_OF_MONTH));
                centerButton.setText(calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH)+1));
            }
        });
        rightButton.setEnabled(true);

        centerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int y = calendar.get(Calendar.YEAR);
                int m = calendar.get(Calendar.MONTH);

                ArrayList<Integer> tempArrayInt = new ArrayList<Integer>();
                tempArrayInt.add(y);
                tempArrayInt.add(m);
                v.setTag(tempArrayInt);

                DateButtonClicked(v);
            }
        });

        return v;
    }


    public interface CustomOnClickListener{
        public void onClicked(View v);
    }

    CustomOnClickListener customOnClickListener;
    public void DateButtonClicked(View v)
    {
        customOnClickListener.onClicked(v);
    }

    @Override
    @Deprecated
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        customOnClickListener = (CustomOnClickListener)activity;
    }

}

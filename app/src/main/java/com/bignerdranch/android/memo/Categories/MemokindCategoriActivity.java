package com.bignerdranch.android.memo.Categories;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bignerdranch.android.memo.Data.MemoData;
import com.bignerdranch.android.memo.Database.RunDatabaseHelper;
import com.bignerdranch.android.memo.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by realbyte on 2017. 7. 17..
 */

public class MemokindCategoriActivity extends AppCompatActivity implements MemoListButtonFragment.CustomOnClickListener {


    private RunDatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_categori_kind_list);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer5);
        dbHelper = new RunDatabaseHelper(this , "categoridata" ,null ,1 );

        if(fragment == null)
        {
            fragment = new MemokindCategoriFragment();
            fm.beginTransaction().add(R.id.fragmentContainer5,fragment).commit();
        }

    }

    @Override
    public void onClicked(View v)
    {
    //    MemoListButtonFragment mButFragment = new MemoListButtonFragment();
    //    int Year = mButFragment.calendar.get(Calendar.YEAR);
    //    int Month = mButFragment.calendar.get(Calendar.MONTH);

        FragmentManager fm2 = getSupportFragmentManager();
        MemokindCategoriFragment fragment2 = (MemokindCategoriFragment)fm2.findFragmentById(R.id.fragment2);

        switch (v.getId())
        {
            case R.id.memo_list_fragment_cal_center:

                ArrayList<Integer> tempArrayInt = (ArrayList)v.getTag();
                int y = tempArrayInt.get(0);
                int x = tempArrayInt.get(1);
                ArrayList<MemoData> asd = dbHelper.selectEqualCalendar(y,x);
                    fragment2.onResumeListCalSel(asd);
                break;

            default:
                break;
        }
    }

}

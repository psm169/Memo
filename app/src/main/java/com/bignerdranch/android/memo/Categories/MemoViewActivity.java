package com.bignerdranch.android.memo.Categories;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.memo.R;

/**
 * Created by realbyte on 2017. 7. 18..
 */

public class MemoViewActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

            if(fragment == null)
            {
                fragment = new MemoViewFragment();
                fm.beginTransaction().add(R.id.fragmentContainer,fragment).commit();
            }
    }
}

package com.bignerdranch.android.memo.Memoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.bignerdranch.android.memo.R;

/**
 * Created by realbyte on 2017. 7. 7..
 */

public class MemoListActivity extends AppCompatActivity {

    protected Fragment createFragment() {
        return new MemoListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {   //프래그먼트에 값이 제대로 안들어갔을때에 대한 객체 생성과 방어코드
            fragment = new MemoListFragment(); //
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.floting_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MemoActivity.class);
                i.putExtra(MemoFragment.EXTRA_MEMO_ID,MemoActivity.class);
                i.putExtra("newmemo",false);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}






  //      final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
  //      fab.setOnClickListener(new View.OnClickListener() {





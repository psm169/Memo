package com.bignerdranch.android.memo.Memoes;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bignerdranch.android.memo.Database.RunDatabaseHelper;
import com.bignerdranch.android.memo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MemoActivity extends AppCompatActivity {


//    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 mm월 dd일");


    protected Fragment createFragment()
    {
        UUID memoId = (UUID)getIntent().getSerializableExtra(MemoFragment.EXTRA_MEMO_ID);
        return MemoFragment.newInstance(memoId);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo2);



        //메뉴프래그멘트를 메뉴액티비티에 추가
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer2);
        if(fragment == null)
        {   //프래그먼트에 값이 제대로 안들어갔을때에 대한 객체 생성과 방어코드
            fragment = new MemoFragment(); //
            fm.beginTransaction().add(R.id.fragmentContainer2,fragment).commit();
        }
    }


}

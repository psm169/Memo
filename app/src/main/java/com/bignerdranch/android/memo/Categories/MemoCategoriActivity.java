package com.bignerdranch.android.memo.Categories;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.memo.R;

/**
 * Created by realbyte on 2017. 7. 13..
 */

public class MemoCategoriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_categori);



        //메뉴프래그멘트를 메뉴액티비티에 추가
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer3);
        if(fragment == null)
        {   //프래그먼트에 값이 제대로 안들어갔을때에 대한 객체 생성과 방어코드
            fragment = new MemoCategoriFragment(); //
            fm.beginTransaction().add(R.id.fragmentContainer3,fragment).commit();
        }
    }
}

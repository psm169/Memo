package com.bignerdranch.android.memo.Categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bignerdranch.android.memo.R;

/**
 * Created by realbyte on 2017. 7. 13..
 */

public class MemoListCategoriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list_categori);

        FloatingActionButton fab2 = (FloatingActionButton)findViewById(R.id.floting_button_makecategori);
        fab2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {

                Intent i = new Intent(getApplicationContext(),MemoCategoriActivity.class);
                i.putExtra(MemoCategoriFragment.EXTRA_CATEGORI_ID,MemoCategoriActivity.class);
                i.putExtra("newcategori",true);
                startActivity(i);
            }
        });
        //메뉴프래그멘트를 메뉴액티비티에 추가
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer4);
        if(fragment == null)
        {   //프래그먼트에 값이 제대로 안들어갔을때에 대한 객체 생성과 방어코드
            fragment = new MemoListCategoriFragment(); //
            fm.beginTransaction().add(R.id.fragmentContainer4,fragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}

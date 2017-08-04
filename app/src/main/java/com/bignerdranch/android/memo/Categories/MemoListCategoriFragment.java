package com.bignerdranch.android.memo.Categories;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.memo.Data.CategoriData;
import com.bignerdranch.android.memo.Data.MemoData;
import com.bignerdranch.android.memo.Database.RunDatabaseHelper;
import com.bignerdranch.android.memo.Memoes.MemoListFragment;
import com.bignerdranch.android.memo.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by realbyte on 2017. 7. 13..
 */

public class MemoListCategoriFragment extends ListFragment {

    private CategoriData mCategoriData;
    private ArrayList<CategoriData> mArrayCategoriData;
    private MemoCategoriAdapter mcategoriAdapter;
    private RunDatabaseHelper dbHelper;

    private Button deleteButton;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        dbHelper = new RunDatabaseHelper(getActivity(),"categoridata",null,1);
        mArrayCategoriData = new ArrayList<>();
        mcategoriAdapter = new MemoCategoriAdapter(mArrayCategoriData);
        dbHelper.insertCategoriData("없음");

        setListAdapter(mcategoriAdapter);
    }

    @Override
    public void onResume()
    {
        super.onResume();

 //       mArrayCategoriData = new ArrayList<CategoriData>();

        new Thread()
        {
            public void run()
            {
                mArrayCategoriData.clear();
                ArrayList<CategoriData> test = dbHelper.getResultCategoriList();
                String noCategori = "없음";
                for (CategoriData temp : test)
                {
                    if(temp.getCategoriTitle().equals(noCategori))
                        mArrayCategoriData.add(temp);
                }

                for (CategoriData str : test) {

                    if(!str.getCategoriTitle().equals(noCategori))
                        mArrayCategoriData.add(str);
                }

                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        }.start();


        mcategoriAdapter.notifyDataSetChanged();

    }

 /*   @Override
    public View onCreateView(LayoutInflater inflate , ViewGroup parent , Bundle savedInstanceState)
    {
        View v = inflate.inflate(R.layout.fragment_memo_list_categori , parent , false);


        deleteButton = (Button)v.findViewById(R.id.categori_list_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                long categoriIdTemp = getActivity().getIntent().getLongExtra("categori_id",0);
                Log.d("select button" + categoriIdTemp , "1");
            }
        });
        deleteButton.setEnabled(true);


        return  v;

    }*/



    //카테고리 분류 선택 된 것으로 view 해주는 액티비티를 띄움
    @Override
    public void onListItemClick(ListView l, View view , final int position, long id)
    {
 //       View convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_memo_list_categori,null);

 //       CategoriData categoriData = mArrayCategoriData.get(position);
 //       Intent i = new Intent(getActivity(),MemokindCategoriActivity.class);
  //      long testtetet = categoriData.getId();
 //      Log.d("i Input" + categoriData.getId(),"good");
 //       i.putExtra("Categori_id_info",categoriData.getId());


 //       startActivity(i);




    }

    @Override
    public void onActivityResult(int requestCode, int resultCoe, Intent data){
        ((MemoListCategoriFragment.MemoCategoriAdapter)getListAdapter()).notifyDataSetChanged();;
    }
    private class MemoCategoriAdapter  extends ArrayAdapter<CategoriData>
    {
        public MemoCategoriAdapter(ArrayList<CategoriData> memos)
        {
            //안드로이드에 사전 정의된 레이아웃을 사용하지 않을것임으로 0값(layout.id)을 주었다
            super(getActivity(),0,memos);
        }

        //getView메서드를 오버라이딩 해서 커스텀레이아웃으로부터 인플레이트된 Memo데이터를 반환
        @Override
        public View getView(int position, View convertView, ViewGroup group)
        {

            if(convertView == null)
            {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_memo_list_categori,null);
            }

            //객체의 뷰를 구성 시작 제목 + 날짜 +내용
            CategoriData categoriSetTitle = getItem(position);
            TextView titleTextView =(TextView)convertView.findViewById(R.id.memo_menu_categori);
            titleTextView.setText(categoriSetTitle.getCategoriTitle());




            LinearLayout categoryItem = (LinearLayout) convertView.findViewById(R.id.category_item);
            categoryItem.setTag(position);
            categoryItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CategoriData categoriData = mArrayCategoriData.get((int)v.getTag());
                    Intent i = new Intent(getActivity(),MemokindCategoriActivity.class);

                    i.putExtra("Categori_id_info",categoriData.getId());
                    i.putExtra("Categori_title_info",categoriData.getCategoriTitle());

                    Toast.makeText(getActivity(), v.getTag().toString(), Toast.LENGTH_SHORT).show();

                    startActivity(i);

                }
            });

 //           if(dbHelper.findId("없음") == mCategoriData.getId())






            //   long date = Calendar.getInstance().getTimeInMillis();
            //      TextView date.setText(memoTextStr.getMemoDate());



            //     TextView dateTextView = (TextView)convertView.findViewById(R.id.list_memo_date);
            //     dateTextView.setText(dbHelper.getResultListDate());
            return convertView;

        }
    }

    Handler handler = new Handler()
    {
        public void handlerMessage(Message msg)
        {
            Bundle bun = msg.getData();
            mcategoriAdapter.notifyDataSetChanged();
        }
    };


}

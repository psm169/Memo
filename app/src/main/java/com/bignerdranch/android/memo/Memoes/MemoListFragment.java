package com.bignerdranch.android.memo.Memoes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bignerdranch.android.memo.Categories.MemoListCategoriActivity;
import com.bignerdranch.android.memo.Data.MemoData;
import com.bignerdranch.android.memo.Database.RunDatabaseHelper;
import com.bignerdranch.android.memo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by realbyte on 2017. 7. 7..
 */

public class MemoListFragment extends ListFragment {

    public String urlRealbyteSeockmin = "http://realbyte.co.kr/seockmin/memopadtext.json";


    private static final String TAG = "MemoListFragment";
    private ArrayList<MemoData> memoTextArray;
    private MemoAdapter memoAdapter;
    private RunDatabaseHelper dbHelper;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(R.string.memo_list_title);
    //    mMemos = MemoLab.get(getActivity()).getMemo(); //구조 이해 필요 get(액티비티호출).getmemo
     //   ArrayAdapter<Memo> adapter = new ArrayAdapter<Memo>(getActivity(),android.R.layout.simple_list_item_1,mMemos);
        setHasOptionsMenu(true);

        dbHelper = new RunDatabaseHelper(getActivity(),"memodata",null,1);
        dbHelper.insertCategoriData("없음");
        memoTextArray = new ArrayList<>();
        memoAdapter = new MemoAdapter(memoTextArray);
        setListAdapter(memoAdapter);


     //   ListView listView = (ListView)v2.findViewById(R.id.memo_list_view);

    //대표님 테스트
    //    RunDatabaseHelper dbHelper = new RunDatabaseHelper(getActivity(),"memodata",null,1);
    //    Toast.makeText(getActivity(), dbHelper.getResultListTitle(), Toast.LENGTH_LONG).show();
    }


    //객체의 데이터가 소실되지않고 리스트를 다시로딩
    public void onResume()
    {
        super.onResume();

        new Thread(){
            public void run()
            {
                memoTextArray.clear();
                //메모리스트를 담는 과정
                ArrayList<MemoData> test = dbHelper.getResultList();
                for (MemoData str : test) {
                    memoTextArray.add(str);
                }
                Message msg = listShowHanler.obtainMessage();
                listShowHanler.sendMessage(msg);

            }
        }.start();
        memoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu , menu);
    }

    //카테고리 눌려졌을때
    @Override
    public boolean onOptionsItemSelected (MenuItem menu)
    {
        switch (menu.getItemId())
        {
            case  R.id.memo_categori_menu:
                Intent i = new Intent(getActivity(), MemoListCategoriActivity.class);
                i.putExtra("movecategori",true);
                startActivity(i);
                return false;

            case R.id.memo_getdata_server:

                new Thread()
                {
                    public void run()
                    {
                        String jsonT = requestJSONCode(urlRealbyteSeockmin);
                        try {
                            parseJSONCode(jsonT);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);

                    }
                }.start();

                return false;

            default:
                return super.onOptionsItemSelected(menu);
        }
    }

    //목록을 클릭했을때
    @Override
    public void onListItemClick(ListView l , View view, int position, long id)
    {
//        Memo m = ((MemoAdapter)getListAdapter()).getItem(position);
        //   m = (Memo)getListAdapter().getItem(position);

        MemoData memoData = memoTextArray.get(position);
        Intent i = new Intent(getActivity(), MemoActivity.class);
        i.putExtra("memo_id", memoData.getMemoId());
        i.putExtra("newmemo",true);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCoe, Intent data){
        ((MemoAdapter)getListAdapter()).notifyDataSetChanged();;
    }
    private class MemoAdapter  extends ArrayAdapter<MemoData>
    {
 //       CategoriData mCategoriData = new CategoriData();

        public MemoAdapter(ArrayList<MemoData> memos)
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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_fragment_memo,null);
            }

            //객체의 뷰를 구성 시작 제목 + 날짜 +내용 +카테고리
            MemoData memoTextStr = getItem(position);


            TextView titleTextView =(TextView)convertView.findViewById(R.id.list_memo_title);
            titleTextView.setText(memoTextStr.getMemoTitle());
            TextView textTextView =(TextView)convertView.findViewById(R.id.list_memo_text);
            textTextView.setText(memoTextStr.getMemoText());


            long spinnerSelectinfo = memoTextStr.getMemoCategoriInfo();
            TextView categoriTextView = (TextView)convertView.findViewById(R.id.list_memo_categori_info);
            categoriTextView.setText(dbHelper.findCategoriTitle(spinnerSelectinfo));


            long calendarTime = memoTextStr.getMemoDate();
        //    long calendarTime =  memoTextStr.getMemoDate()  ;
            Calendar displayCalenar = Calendar.getInstance();
            displayCalenar.setTimeInMillis(calendarTime);

            TextView dateTextView =(TextView)convertView.findViewById(R.id.list_memo_date);
            dateTextView.setText(displayCalenar.get(Calendar.YEAR) +
                        " / " + (displayCalenar.get(Calendar.MONTH)+1) +
                        " / " + displayCalenar.get(Calendar.DAY_OF_MONTH));

            return convertView;

        }
    }


    public String requestJSONCode(String url)
    {
        try{
            URL serverURL = new URL(url);
            HttpURLConnection urlConn = (HttpURLConnection)serverURL.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("Accept", "UTF-8");
            urlConn.setDoInput(true);

            int resCode = urlConn.getResponseCode();
            if(resCode != HttpURLConnection.HTTP_OK) return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"UTF-8"));
            String input;
            StringBuffer sb = new StringBuffer();

            while((input = reader.readLine()) != null){
                sb.append(input + "\n");
            }
            Log.d("JSON크롤링:" , sb.toString());
            return sb.toString();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void parseJSONCode(String json)
    {
        try {
            JSONObject jsonOb = new JSONObject(json);
            JSONArray jsonArray = jsonOb.getJSONArray("notetest1");
            for(int i = 0 ; i < jsonArray.length() ; i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MemoData memoTemp = new MemoData();
                memoTemp.setMemoTitle(jsonObject.getString("memo_title"));
                memoTemp.setMemoText(jsonObject.getString("memo_text"));
                memoTemp.setMemoDate(jsonObject.getLong("memo_date"));
                String CategoriTitleTemp = jsonObject.getString("memo_categori_info");
                dbHelper.isCategoriTitle(CategoriTitleTemp);

                memoTemp.setMemoCategoriInfo(dbHelper.getCategoriIdnum(CategoriTitleTemp));

                dbHelper.insertMemoData(memoTemp);


            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            Bundle bun = msg.getData();
            onResume();
            memoAdapter.notifyDataSetChanged();

        }
    };

    Handler listShowHanler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            Bundle bun = msg.getData();
            memoAdapter.notifyDataSetChanged();
        }
    };

}


/*
    final FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.floting_button);
    public boolean onOptionsItemSelected(FloatingActionButton fab) {
        switch (fab.getId()) {
            case R.id.floting_button:
                Memo m = new Memo();
                MemoLab.get(getActivity()).addMemo(m);

                //Activity to Activity
                Intent i = new Intent(getActivity(), MemoActivity.class);
                i.putExtra(MemoFragment.EXTRA_MEMO_ID, MemoActivity.class);
                startActivity(i);

            default:
                return onOptionsItemSelected(fab);
        }
        );
*/




/*    public boolean onOptionsItemSelected(FloatingActionButton fab) {
        switch (fab.getId()) {
            case R.id.floting_button:
                Memo m = new Memo();
                MemoLab.get(getActivity()).addMemo(m);

                //Activity to Activity
                Intent i = new Intent(getActivity(), MemoActivity.class);
                i.putExtra(MemoFragment.EXTRA_MEMO_ID, MemoActivity.class);
                startActivity(i);

            default:
                return onOptionsItemSelected(fab);
        }
    }
*/
 /*   private class MemoAdapter extends ArrayAdapter<Memo>
    {
        public MemoAdapter(ArrayList<Memo> memos)
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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_fragment_memo,null);
            }

            //객체의 뷰를 구성 시작 제목 + 날짜
            Memo m = getItem(position);
            RunDatabaseHelper dbHelper = new RunDatabaseHelper(getActivity(),"memodata",null,1);


            TextView titleTextView =(TextView)convertView.findViewById(R.id.list_memo_title);
            titleTextView.setText(dbHelper.getResultListTitle());


       //     TextView dateTextView = (TextView)convertView.findViewById(R.id.list_memo_date);
       //     dateTextView.setText(dbHelper.getResultListDate());
//
            return convertView;

        }
    }
*/


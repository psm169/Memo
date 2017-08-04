package com.bignerdranch.android.memo.Categories;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.bignerdranch.android.memo.Data.CategoriData;
import com.bignerdranch.android.memo.Database.RunDatabaseHelper;
import com.bignerdranch.android.memo.R;

import java.util.ArrayList;
import java.util.zip.Inflater;


/**
 * Created by realbyte on 2017. 7. 18..
 */

public class MemoViewFragment extends Fragment {

    private RunDatabaseHelper dbHelper;
    private ArrayList<CategoriData> arrayCategoriDatas;
    private ArrayList<String > arrayString;
    private Button deleteButton;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dbHelper = new RunDatabaseHelper(getActivity(),"listview",null,1);
        arrayCategoriDatas = new ArrayList<CategoriData>();
        arrayString = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_listview,null);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,arrayString);
                                                                                                //ArrayList
 //       ListView listView = (ListView)v.findViewById(R.id.list_view);
 //       listView.setAdapter(adapter);

 //       deleteButton = (Button)v.findViewById(R.id.categori_list_delete_button);
 //       deleteButton.setOnClickListener(new View.OnClickListener()
 //       {

 //           @Override
 //           public void onClick(View view)
 //           {
 //           }
  //      });
  //      deleteButton.setEnabled(true);



        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();


        arrayCategoriDatas.clear();
        arrayString.clear();
        ArrayList<CategoriData> test = dbHelper.getResultCategoriList();
        for(CategoriData str : test)
        {
            arrayCategoriDatas.add(str);
            arrayString.add(str.getCategoriTitle());
        }


    }

}

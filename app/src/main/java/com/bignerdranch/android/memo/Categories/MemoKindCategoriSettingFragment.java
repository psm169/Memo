package com.bignerdranch.android.memo.Categories;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bignerdranch.android.memo.Data.CategoriData;
import com.bignerdranch.android.memo.Database.RunDatabaseHelper;
import com.bignerdranch.android.memo.R;

/**
 * Created by realbyte on 2017. 7. 19..
 */

public class MemoKindCategoriSettingFragment extends Fragment {

    private RunDatabaseHelper dbHelper;
    private CategoriData mCategoriData;
    private String selectedCategoriTitle;
    private long selectedCategoriId;

    private EditText mCategoriTitle;
    private Button mUpdateButton;
    private Button mDeleteButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mCategoriData = new CategoriData();
        dbHelper = new RunDatabaseHelper(getActivity() , "categoridata" ,null ,1 );
        selectedCategoriTitle = getActivity().getIntent().getStringExtra("Categori_title_info");
        selectedCategoriId = getActivity().getIntent().getLongExtra("Categori_id_info",0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_memo_list_kind_setting_categori ,parent , false);

        mCategoriTitle = (EditText)v.findViewById(R.id.categori_kind_title);
        mCategoriTitle.setText(selectedCategoriTitle);


        mUpdateButton = (Button)v.findViewById(R.id.categori_kind_update);
        mUpdateButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                mCategoriData.setCategoriTitle(mCategoriTitle.getText().toString());
                mCategoriData.setId(selectedCategoriId);
                dbHelper.updateCategori(mCategoriData);
                getActivity().finish();
            }
        });
        mUpdateButton.setEnabled(true);


        mDeleteButton = (Button)v.findViewById(R.id.categori_kind_delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                AlertDialog.Builder as = new AlertDialog.Builder(getActivity());
                    as.setTitle("확인 메세지");
                    as.setMessage("정말 카테고리를 삭제하시겠습니까?");
                    as.setPositiveButton("네" , new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog,int which){

                    dbHelper.deleteCategoriUpdate(selectedCategoriId);
                    getActivity().finish();
                }


            });

                as.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                as.show();


            }
        });
        mDeleteButton.setEnabled(true);

        if(dbHelper.findId("없음") ==  selectedCategoriId) {
            mDeleteButton.setVisibility(View.GONE);
            mUpdateButton.setVisibility(View.GONE);

        }

        return v;
    }
}

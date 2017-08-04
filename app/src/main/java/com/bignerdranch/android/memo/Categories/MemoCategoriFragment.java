package com.bignerdranch.android.memo.Categories;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.memo.Data.CategoriData;
import com.bignerdranch.android.memo.Database.RunDatabaseHelper;
import com.bignerdranch.android.memo.R;

/**
 * Created by realbyte on 2017. 7. 13..
 */

public class MemoCategoriFragment extends Fragment{

    private EditText mEditTitle;
    private Button mSaveButton;
    private Button mDeleteButton;
    private CategoriData mCategoriData;
    private boolean isOldCategori;
    public RunDatabaseHelper dbHelper;

    public static final String EXTRA_CATEGORI_ID = "categori_memo";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        isOldCategori = getActivity().getIntent().getBooleanExtra("newcategori",false);
   //     if(isOldCategori)
        mCategoriData = new CategoriData();
        dbHelper = new RunDatabaseHelper(getActivity(),"categoridata",null,1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_memo_categori, parent, false);

        mEditTitle = (EditText)v.findViewById(R.id.memo_write_categori_name);
        mEditTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCategoriData.setCategoriTitle(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mSaveButton = (Button)v.findViewById(R.id.memo_categori_save);
        mSaveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {

                    String categoriTitleTemp = mEditTitle.getText().toString();
                    int a = dbHelper.insertCategoriData(categoriTitleTemp);
                    if (a == 0)
                        Toast.makeText(getActivity(), "중복된 카테고리입니다", Toast.LENGTH_SHORT).show();
                    getActivity().finish();

            }
        });
        mSaveButton.setEnabled(true);

        mDeleteButton = (Button)v.findViewById(R.id.memo_categori_delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }

    });
        mDeleteButton.setEnabled(true);


        return v;
    }
}

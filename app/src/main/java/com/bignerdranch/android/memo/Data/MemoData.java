package com.bignerdranch.android.memo.Data;

import java.util.Date;

/**
 * Created by realbyte on 2017. 7. 10..
 */

public class MemoData {

    private String MemoTitle = " ";
    private long MemoDate ;
    private String MemoText;
    private long MemoId;
    private long MemoCategoriInfo;

    public void setMemoCategoriInfo(long memoCategoriInfo) {
        MemoCategoriInfo = memoCategoriInfo;
    }

    public long getMemoCategoriInfo() {

        return MemoCategoriInfo;
    }

    public long getMemoId() { return MemoId ;}

    public void setMemoId(long memoId) { MemoId = memoId; }

    public long getMemoDate () {
       return MemoDate;
    }

    public String getMemoTitle() { return MemoTitle; }

   public String getMemoText() {
        return MemoText;
    }

    public void setMemoDate(long memoDate) {
        MemoDate = memoDate;
    }

    public void setMemoText(String memoText) {
        MemoText = memoText;
    }

    public void setMemoTitle(String memoTitle) {
        MemoTitle = memoTitle;
    }
}

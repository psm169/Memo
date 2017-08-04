package com.bignerdranch.android.memo.Data;

/**
 * Created by realbyte on 2017. 7. 31..
 */

public class CalendarData {
    int hours;
    long memoDataId;

    public void setMemoDataId(long memoDataId) {
        this.memoDataId = memoDataId;
    }

    public void setHours(int hours) {

        this.hours = hours;
    }

    public long getMemoDataId() {

        return memoDataId;
    }

    public int getHours() {

        return hours;
    }
}

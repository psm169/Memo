package com.bignerdranch.android.memo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.bignerdranch.android.memo.Data.CategoriData;
import com.bignerdranch.android.memo.Data.MemoData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by realbyte on 2017. 7. 10..
 */

public class RunDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "memodata";
    private static final int VERSION = 1;
    private static final String TABLE_RUN = "run";

    private static final String COLUMN_RUN_ID ="memo_id";
    private static final String COLUMN_RUN_TITLE = "memo_title";
    private static final String COLUMN_RUN_START_DATE = "start_date";
    private static final String COLUMN_RUN_TEXT = "memo_text";

    private MemoData mMemoData;
    private CategoriData mCategoriData;

    public RunDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //run테이블
        db.execSQL("create table memodata (memo_id integer primary key autoincrement, memo_title TEXT, start_date integer, memo_text TEXT, memo_categori_info integer)");

        db.execSQL("create table categoridata (categori_id integer primary key autoincrement, categori_title TEXT)");

        db.execSQL("create table calendardata (calendar_id integer primary key autoincrement, cal_id integer, cal_memo_id integer, cal_time integer)");
        //location 테이블
 //       db.execSQL("create table location ( timestamp integer ,latitude real, longitude real, altitude real, provider varchar(100), run_id integer references run(_id))");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //데이터베이스 스키마 변경시 피요한 코드를 넣는다
    }

    //activity memo의 내용 insert 받겟다
    public void insert(String title, long date, String text, long categoriInfo) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("insert into memodata values(null, '" + title + "' , '" + date + "', '" + text + "'," + categoriInfo +  ")");
        db.close();
        //cv에 담길 내용
    }

    public void insertMemoData(MemoData memodata) {
        SQLiteDatabase db = getWritableDatabase();

        long memoCategoriInfoTemp = memodata.getMemoCategoriInfo();
  //      long memoIdTemp = memodata.getMemoId();
        long memoDateTemp = memodata.getMemoDate();
        String memoTitleTemp = memodata.getMemoTitle();
        String memoTextTemp = memodata.getMemoText();

        ContentValues cv = new ContentValues();
        cv.put("memo_title",memoTitleTemp);
        cv.put("memo_text",memoTextTemp);

  //      cv.put("memo_id",memoIdTemp);
        cv.put("start_date",memoDateTemp);
        cv.put("memp_categori_info",memoCategoriInfoTemp);


        db.execSQL("insert into memodata values(null, '" + memoTitleTemp +"' ,"+ memoDateTemp + " , '"+ memoTextTemp +"' ,"+ memoCategoriInfoTemp+")");

        //db.execSQL("insert into memodata values(null, '" + title + "' , '" + date + "', '" + text + "'," + categoriInfo +  ")");
        //db.insert("memodata",null,cv);
        db.close();
        //cv에 담길 내용
    }

    public void update(long id,String title,String text, long date) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update from memodata where memo_id = ?'"+id+"'set memo_title='" + title + "'set memo_text='" + text + "'set start_date'" + date + "' ;");
        db.close();
    }

    public void updateTest(MemoData memodataTemp) {
        SQLiteDatabase db = getWritableDatabase();

        long id =memodataTemp.getMemoId();
        long date = memodataTemp.getMemoDate();
        String title = memodataTemp.getMemoTitle();
        String text = memodataTemp.getMemoText();
        long categoriId = memodataTemp.getMemoCategoriInfo();
//..흠
        ContentValues cv = new ContentValues();
        cv.put("start_date",date);
        cv.put("memo_title",title);
        cv.put("memo_text",text);
        cv.put("memo_categori_info",categoriId);
        String[] s = new String[1];
        s[0] = new String();
        s[0] = String.valueOf(id);

//        db.execSQL("update memodata set memo_title = '" + title + "', memo_text = '" + text + "', start_date = " + date + ",memo_categori_info =" +categoriId
//                    + " where memo_id ="+ id);

//        Cursor cursor = db.rawQuery("update memodata where memo_id = " + id, null);
//        cursor.moveToNext();
        db.update(DB_NAME, cv, "memo_id =?",s);


     //   Cursor cursor = db.rawQuery("SELECT * FROM memodata where memo_id = " + id +";", null);

     //   db.close();
    }

    public void delete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from memodata where memo_id = " + id);
     //   db.close();

    }


    public ArrayList<MemoData> getResultList() {
        ArrayList<MemoData> memodata = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from memodata order by start_date desc", null);

        while (cursor.moveToNext()) {
            String titleTemp = cursor.getString(cursor.getColumnIndex("memo_title"));
            String textTemp = cursor.getString(cursor.getColumnIndex("memo_text"));
            long dateTemp =  cursor.getLong(cursor.getColumnIndex("start_date"));
            long memoId = cursor.getLong(cursor.getColumnIndex("memo_id"));
            long categoriId = cursor.getLong(cursor.getColumnIndex("memo_categori_info"));

            mMemoData = new MemoData();
            mMemoData.setMemoTitle(titleTemp);
            mMemoData.setMemoText(textTemp);
            mMemoData.setMemoDate(dateTemp);
            mMemoData.setMemoId(memoId);
            mMemoData.setMemoCategoriInfo(categoriId);

            memodata.add(mMemoData);
        }
        return memodata;

    }
    public String getResultListDate() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("select * from memodata", null);

        while (cursor.moveToNext()) {
            result += cursor.getString(2);
        }
        return result;

    }

    public MemoData getReadMemo(long id)
    {
        SQLiteDatabase db = getReadableDatabase();

      //  Cursor cursor =

        if(id > 0) {
            Cursor cursor = db.rawQuery("SELECT * FROM memodata where memo_id = " + id, null);

            cursor.moveToNext();

            long memoId = cursor.getLong(cursor.getColumnIndex("memo_id"));
            String titleTemp = cursor.getString(cursor.getColumnIndex("memo_title"));
            long dateTemp = cursor.getLong(cursor.getColumnIndex("start_date"));
            String textTemp = cursor.getString(cursor.getColumnIndex("memo_text"));
            long categoriIdTemp = cursor.getLong(cursor.getColumnIndex("memo_categori_info"));
            mMemoData = new MemoData();
            mMemoData.setMemoTitle(titleTemp);
            mMemoData.setMemoText(textTemp);
            mMemoData.setMemoDate(dateTemp);
            mMemoData.setMemoId(memoId);
            mMemoData.setMemoCategoriInfo(categoriIdTemp);
        }

        return mMemoData;
    }

    public Boolean isCheckTitleIsEmpty(String title)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from memodata where memo_title = '" + title+"' ",null);
        //db.execSQL("select memodata where memo_title =" + title);
    //    while(cursor.moveToNext());

        boolean rtValue = false;
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToNext();
                String memoTitle = cursor.getString(cursor.getColumnIndex("memo_title"));
                if (memoTitle.equals(title))
                    rtValue = true;
            }
        }
        return rtValue;
    }


    public String findCategoriTitle(long id)
    {
        SQLiteDatabase db = getReadableDatabase();
     //   Cursor cursor = db.rawQuery("select * from categoridata where categori_id =" + id,null);
        Cursor cursor = db.rawQuery("select * from memodata inner join categoridata on memodata.memo_categori_info = categoridata.categori_id " +
                                        " where memodata.memo_categori_info =" + id,null);

        if(cursor.moveToNext())
        {
            String categoriTitleTemp = cursor.getString(cursor.getColumnIndex("categori_title"));
            return categoriTitleTemp;
        }
        else
            return " ";

    }

    public void deleteCategori(long id)
    {
        SQLiteDatabase db = getWritableDatabase();
 //       db.execSQL("delete from memodata from categoridata inner join memodata on categoridata.categori_id = memodata.memo_categori_info " +
 //                                           " where categoridata.categori_id = " + id);
        db.execSQL("delete from memodata where memo_categori_info = " +id);
        db.execSQL("delete from categoridata where categori_id = "+id);

    }
    //////////////////////////카테고리 database
    public int insertCategoriData(String title)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from categoridata where categori_title = '" + title + "'",null);

        if(!cursor.moveToNext())
        {
            cursor.moveToNext();
            db.execSQL("insert into categoridata values(null, '" + title + "')");
            return 1;
        }
        else
            return 0;

    //    db.execSQL("insert into memodata values(null, '" + title + "' , '" + date + "', '" + text + "')");
    }

    public ArrayList<CategoriData> getResultCategoriList()
    {
        ArrayList<CategoriData> categoridatas = new ArrayList<CategoriData>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from categoridata order by categori_title desc",null);

        while(cursor.moveToNext())
        {
            mCategoriData = new CategoriData();
            mCategoriData.setCategoriTitle(cursor.getString(cursor.getColumnIndex("categori_title")));
            mCategoriData.setId(cursor.getLong(cursor.getColumnIndex("categori_id")));

            categoridatas.add(mCategoriData);

        }
        return categoridatas;
    }

    public ArrayList<MemoData> getResultKindCategoriList(long id)
    {
        ArrayList<MemoData> memodatas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from memodata where memo_categori_info = " + id,null);

        while(cursor.moveToNext())
        {
            mMemoData = new MemoData();
            mMemoData.setMemoId(cursor.getLong(cursor.getColumnIndex("memo_id")));
            mMemoData.setMemoTitle(cursor.getString(cursor.getColumnIndex("memo_title")));
            mMemoData.setMemoText(cursor.getString(cursor.getColumnIndex("memo_text")));
            mMemoData.setMemoDate(cursor.getLong(cursor.getColumnIndex("start_date")));
            mMemoData.setMemoCategoriInfo(cursor.getLong(cursor.getColumnIndex("memo_categori_info")));

            memodatas.add(mMemoData);
        }
        return memodatas;
    }


    public  long getCategoriIdnum(String getCategoriData)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select categori_id from categoridata where categori_title = '" + getCategoriData +"' ",null);
     //   Cursor cursor = db.rawQuery("select * from categoridata where categori_title = '" + getCategoriData + "' ",null);
        if(cursor.moveToNext()) {
            long getId = cursor.getLong(cursor.getColumnIndex("categori_id"));
            return getId;
        }
        else return 0;

    }

    public ArrayList<CategoriData> getSpinnerList()
    {
        ArrayList<CategoriData> getSL = new ArrayList< >();
        CategoriData categoridata ;
        SQLiteDatabase db = getReadableDatabase();
        String aaa = "없음";
        Cursor cursor = db.rawQuery("select * from categoridata order by categori_title desc ",null);
        while (cursor.moveToNext())
        {
            if(aaa.equals(cursor.getString(cursor.getColumnIndex("categori_title"))))
            {
                categoridata = new CategoriData();
                categoridata.setId(cursor.getLong(cursor.getColumnIndex("categori_id")));
                categoridata.setCategoriTitle(cursor.getString(cursor.getColumnIndex("categori_title")));
                //   getSL.add(cursor.getString(cursor.getColumnIndex("categori_title")));
                getSL.add(categoridata);
            }

        }
        cursor = db.rawQuery("select * from categoridata order by categori_title desc ",null);
        while(cursor.moveToNext())
        {
            if(!aaa.equals(cursor.getString(cursor.getColumnIndex("categori_title"))))
            {
                categoridata = new CategoriData();
                categoridata.setId(cursor.getLong(cursor.getColumnIndex("categori_id")));
                categoridata.setCategoriTitle(cursor.getString(cursor.getColumnIndex("categori_title")));
                //   getSL.add(cursor.getString(cursor.getColumnIndex("categori_title")));
                getSL.add(categoridata);
            }
        }
        return getSL;
    }

    public void updateCategori(CategoriData datas)
    {
        SQLiteDatabase db = getWritableDatabase();
        String title = datas.getCategoriTitle();
        long id = datas.getId();
        db.execSQL("update categoridata set categori_title = '" + title + "' where categori_id = " + id);

    }

    //카테고리항목을 지웟을때 기존에있는 문서들은 없음 으로 이동
    public long findId(String title)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from categoridata where categori_title = '" + title + "'" , null);

        cursor.moveToNext();
        long temp = cursor.getLong(cursor.getColumnIndex("categori_id"));
        return temp;
    }
    public void deleteCategoriUpdate(long oldId)
    {
        SQLiteDatabase db = getWritableDatabase();
        long id = findId("없음");
        if(oldId != id) {
            db.execSQL("update memodata set memo_categori_info = " + id + " where memo_categori_info = " + oldId);

            db.execSQL("delete from categoridata where categori_id = " + oldId);
        }
    }

    public boolean isCategoriTitle(String title)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from categoridata where categori_title = ' " + title +"'" ,null);

        if(cursor.moveToNext())
        {
            if(cursor.getCount() > 0)

            return true;
        }
        else
            insertCategoriData(title);
            return false;
    }

    public ArrayList<MemoData> selectEqualCalendar(int year , int month)
    {
        SQLiteDatabase db = getReadableDatabase();
        Calendar precalendar = Calendar.getInstance();
        Calendar aftcalendar = Calendar.getInstance();
        precalendar.set(year,month,01);
        aftcalendar.set(year,month,aftcalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        Date predate = precalendar.getTime();
        Date aftdate = aftcalendar.getTime();

        long preTime = predate.getTime();
        long aftTime = aftdate.getTime();

        Cursor cursor = db.rawQuery("select * from memodata where start_date between '" + preTime +"' and '"+ aftTime +"'",null);

        ArrayList<MemoData> memoDataArrayList = new ArrayList<>();

        if (cursor.getCount() > 0)
        {
            while(cursor.moveToNext()) {
                MemoData memoData= new MemoData();

                memoData.setMemoTitle(cursor.getString(cursor.getColumnIndex("memo_title")));
                memoData.setMemoText(cursor.getString(cursor.getColumnIndex("memo_text")));
                memoData.setMemoDate(cursor.getLong(cursor.getColumnIndex("start_date")));
                memoData.setMemoCategoriInfo(cursor.getLong(cursor.getColumnIndex("memo_categori_info")));
                memoData.setMemoId(cursor.getLong(cursor.getColumnIndex("memo_id")));

                Log.d(memoData.getMemoTitle(),"seockmin");

                memoDataArrayList.add(memoData);

            }
        }
        else
            return null;

        return memoDataArrayList;
    }
}
package com.harar.khalil.quraan.databases;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.harar.khalil.quraan.models.BookMark;
import com.harar.khalil.quraan.models.SuraContent;
import com.harar.khalil.quraan.models.SuraList;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelp extends SQLiteOpenHelper {
    public static final String DBName = "quran2db.db";
    @SuppressLint("SdCardPath")
    public static final String DBLocation = "/data/data/com.harar.khalil.quraan/databases/";
    private Context mcontext;
    private SQLiteDatabase msqLiteDatabase;


    public DatabaseHelp(Context context) {
        super(context, DBName, null, 1);
        this.mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void openDatabase() {
        String path = mcontext.getDatabasePath(DBName).getPath();

        if (msqLiteDatabase != null && msqLiteDatabase.isOpen()) {
            return;
        }
        msqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void closeDatabase() {
        if (msqLiteDatabase != null) {
            msqLiteDatabase.close();
        }
    }

    public List<SuraList> getSuraList(String wordsearch) {

        SuraList suraList;
        List<SuraList> suraListList = new ArrayList<>();

        openDatabase();

        String[] args = {"%" + wordsearch + "%"};

        Cursor cursor = msqLiteDatabase.rawQuery("Select * From suralist Where oromo Like ?", args);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            suraList = new SuraList(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
            suraListList.add(suraList);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return suraListList;

    }

    public String getSuraName(int sura) {
        openDatabase();
        Cursor cursor = msqLiteDatabase.rawQuery("Select oromo From suralist where id=" + sura, null);
        cursor.moveToFirst();
        if (cursor.isAfterLast()) {
            closeDatabase();
            return "";
        }
        String name = cursor.getString(0);
        closeDatabase();
        return name;
    }

    public boolean isBookMark(int id, String table) {
        openDatabase();
        Cursor x = msqLiteDatabase.rawQuery("select * from " + table + " where bookmark=1 and id=" + id, null);
        x.moveToFirst();
        if (x.isAfterLast()) {
            closeDatabase();
            return false;
        }
        closeDatabase();
        return true;
    }


    public void updateBookmark(int id, int bookmark, String table) {

        openDatabase();
        msqLiteDatabase.execSQL("UPDATE Arabic SET bookmark =" + bookmark + " WHERE id =" + id);
        msqLiteDatabase.execSQL("UPDATE " + table + " SET bookmark =" + bookmark + " WHERE id =" + id);
        closeDatabase();
    }

    public List<BookMark> getBookMarks(String table) {
        BookMark bookMark;
        List<BookMark> bookMarks = new ArrayList<>();
        openDatabase();
        Cursor cursor = msqLiteDatabase.rawQuery("Select id,ayah,content From Arabic where bookmark=1", null);
        Cursor cursor1 = msqLiteDatabase.rawQuery("Select id,ayah,content,sura From " + table + " where bookmark=1", null);
        cursor.moveToFirst();
        cursor1.moveToFirst();
        while (!cursor.isAfterLast() && !cursor1.isAfterLast()) {
            while (cursor.getInt(0) != cursor1.getInt(0) && !cursor.isAfterLast()) {
                cursor.moveToNext();
            }
            Log.i("test", String.valueOf(cursor.getInt(0)) + " " + String.valueOf(cursor1.getInt(0)));
            bookMark = new BookMark(cursor.getInt(1), cursor.getString(2), cursor1.getString(2), cursor1.getInt(3));
            bookMarks.add(bookMark);
            cursor.moveToNext();
            cursor1.moveToNext();
        }
        cursor.close();
        cursor1.close();
        closeDatabase();
        return bookMarks;
    }

    public List<SuraContent> getSuraContent(Integer s) {

        SuraContent suraContent;
        List<SuraContent> suraContents = new ArrayList<>();
        openDatabase();
        String arabic = "Select ayah,id,content From Arabic Where sura= ?";
        String oromo = "Select ayah,id,content From Oromic Where sura Like ?";
        String amharic = "Select ayah,id,content  From Amharic Where sura Like ?";
        String english = "Select ayah,id,content  From English Where sura Like ?";
        String[] args = {String.valueOf(s)};
        Cursor cursor = msqLiteDatabase.rawQuery(arabic, args);
        Cursor cursor1 = msqLiteDatabase.rawQuery(oromo, args);
        Cursor cursor2 = msqLiteDatabase.rawQuery(amharic, args);
        Cursor cursor3 = msqLiteDatabase.rawQuery(english, args);
        cursor.moveToFirst();
        cursor1.moveToFirst();
        cursor2.moveToFirst();
        cursor3.moveToFirst();
        if (s != 9 && s != 1) {
            suraContent = new SuraContent(0, 0, "   بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "", "", "");
            suraContents.add(suraContent);
        }
        while (!cursor.isAfterLast() || !cursor1.isAfterLast() || !cursor2.isAfterLast() || !cursor3.isAfterLast()) {
            if (cursor1.isAfterLast()) {
                suraContent = new SuraContent(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor3.getString(2), cursor2.getString(2), cursor3.getString(2));
            } else {
                suraContent = new SuraContent(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor1.getString(2), cursor2.getString(2), cursor3.getString(2));
            }
            suraContents.add(suraContent);
            cursor.moveToNext();
            cursor1.moveToNext();
            cursor2.moveToNext();
            cursor3.moveToNext();
        }
        cursor.close();
        cursor1.close();
        cursor2.close();
        cursor3.close();
        closeDatabase();
        return suraContents;

    }
}

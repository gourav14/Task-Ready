package com.example.gourav_chawla.taskready;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by GOURAV_chawla on 21/05/2017.
 */

public class sqlnotehelper extends SQLiteOpenHelper {

    public sqlnotehelper(Context context) {
        super(context,"notedb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table note (content text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

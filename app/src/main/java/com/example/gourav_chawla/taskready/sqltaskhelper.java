package com.example.gourav_chawla.taskready;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by GOURAV_chawla on 25/05/2017.
 */

public class sqltaskhelper extends SQLiteOpenHelper{
    public sqltaskhelper(Context context) {
        super(context,"taskdb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table task (title text , discrip text  , duedate text , duetime text , reminder text,notifyid integer )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

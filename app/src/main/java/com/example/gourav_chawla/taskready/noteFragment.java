package com.example.gourav_chawla.taskready;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class noteFragment extends Fragment {
    ListView lvnote;
    String[] notes ;
    int rows;
    TextView notetitleview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_note_fragment,container,false);
        return v ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvnote = (ListView)getActivity().findViewById(R.id.notelistviewid);
        sqlnotehelper sqlhelper = new sqlnotehelper(getActivity());
        SQLiteDatabase db = sqlhelper.getReadableDatabase();
        String format[] = {"content" };
        Cursor c = db.query("note" , format , null , null , null , null ,null);
        rows = c.getCount();
        notes = new String[rows];
        c.moveToPosition(rows-1);
        int count =0;
        for(int i=0;i<=rows-1;i++){
            System.out.println("inside cursor");
            notes[count] = c.getString(0);
            c.moveToPrevious();
            count++;
        }
        if(rows!=0) {
            mynoteadapter mynoteadapter = new mynoteadapter(getActivity(), notes);
            lvnote.setAdapter(mynoteadapter);
        }
    }
}


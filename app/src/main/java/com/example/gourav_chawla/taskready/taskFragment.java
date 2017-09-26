package com.example.gourav_chawla.taskready;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.zip.Inflater;

public class taskFragment extends Fragment {
    ListView listView;
   public String[] duedatedb ;
    public String[] duetimedb ;
    public String[] titledb ;
    public String[] discripdb ;
    public String[] reminderdb ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.activity_task_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView)getActivity().findViewById(R.id.listview);
        sqltaskhelper sqltaskhelper = new sqltaskhelper(getActivity());
        SQLiteDatabase db = sqltaskhelper.getReadableDatabase();
        String format[] = {"title" , "discrip" , "duedate" , "duetime" , "reminder" };
        Cursor c = db.query("task" , format , null , null , null , null ,null);
        int taskrows = c.getCount();
        titledb  = new String[taskrows];
        discripdb  = new String[taskrows];
        duedatedb  = new String[taskrows];
        duetimedb  = new String[taskrows];
        reminderdb  = new String[taskrows];

        c.moveToPosition(taskrows-1);
        for(int i=0;i<=taskrows-1;i++){
            System.out.println("inside cursor");
            titledb[i] = c.getString(0);
            discripdb[i] = c.getString(1);
            duedatedb[i] = c.getString(2);
            duetimedb[i] = c.getString(3);
            reminderdb[i] = c.getString(4);
            c.moveToPrevious();
        }
        if(taskrows!=0) {
            Myadapter myadapter = new Myadapter(getActivity(),titledb,discripdb,duedatedb,duetimedb,reminderdb);
            listView.setAdapter(myadapter);
        }

    }
}

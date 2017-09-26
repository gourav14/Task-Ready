package com.example.gourav_chawla.taskready;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class noteDetail extends AppCompatActivity {
    EditText note;
    Button notebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        note = (EditText)findViewById(R.id.mynote);
        notebutton = (Button)findViewById(R.id.submitButton);


        notebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = note.getText().toString();
                sqlnotehelper sqlnotehelper = new sqlnotehelper(noteDetail.this);
                SQLiteDatabase db = sqlnotehelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("content" , text);
                db.insert("note",null,values);
                Intent i = new Intent(noteDetail.this,MainActivity.class);
                Toast.makeText(noteDetail.this,"Note created" ,Toast.LENGTH_SHORT).show();
                startActivity(i);

            }
        });


    }
}

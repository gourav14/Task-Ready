package com.example.gourav_chawla.taskready;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by GOURAV_chawla on 24/05/2017.
 */

public class mynoteadapter extends ArrayAdapter<String> {
    private Activity context;
    private String[] notes;

    public mynoteadapter(Activity context,String[] notes) {
        super(context,R.layout.noteview,notes);
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        View rowview = inflater.inflate(R.layout.noteview,null,true);
       final TextView Notetitle = (TextView) rowview.findViewById(R.id.notetitleview);
        Button deletenote = (Button)rowview.findViewById(R.id.deletenotebutton);
        Button copynote = (Button) rowview.findViewById(R.id.copy);
        final Button movenote = (Button)rowview.findViewById(R.id.movebutton);
        final Button noteedit = (Button) rowview.findViewById(R.id.editnotebutton);
        Notetitle.setText(notes[position]);

        noteedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View noteview = inflater.inflate(R.layout.note_edit,null,true);
                final EditText noteeditbox = (EditText) noteview.findViewById(R.id.noteedit);
                noteeditbox.setText(notes[position]);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit your Note below ").setView(noteview).setCancelable(false).setNegativeButton("cancel" ,null)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               notes[position] = noteeditbox.getText().toString();
                                sqlnotehelper sqlnotehelper = new sqlnotehelper(context);
                                SQLiteDatabase db = sqlnotehelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put("content" , notes[position]);
                                db.update("note",values,"content = ?" ,new String[] {Notetitle.getText().toString()});
                                Toast.makeText(getContext(),"Note edited" ,Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, MainActivity.class));
                            }
                        });
                AlertDialog  alert = builder.create();
                alert.show();

            }
        });

        movenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlnotehelper sqlnotehelper = new sqlnotehelper(context);
                SQLiteDatabase db = sqlnotehelper.getWritableDatabase();
                int i =0;
                i = db.delete("note" ,"content = ?" ,new String[] {Notetitle.getText().toString()});
                if(i>0){
                    ContentValues values = new ContentValues();
                    values.put("content" , notes[position]);
                    db.insert("note",null,values);
                    db.close();
                    Toast.makeText(context,"NOTE moved to top",Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity.class));
                }
                else {
                    Toast.makeText(context,"NOTE not moved to top- "+i,Toast.LENGTH_SHORT).show();
                    db.close();
                }


            }
        });

        deletenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are u Sure ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sqlnotehelper sqlnotehelper = new sqlnotehelper(context);
                        SQLiteDatabase db = sqlnotehelper.getWritableDatabase();
                        int i =0;
                        i = db.delete("note" ,"content = ?" ,new String[] {Notetitle.getText().toString()});
                        if(i>0){
                            Intent intent = new Intent(context,MainActivity.class);
                            Toast.makeText(context,"note deleted",Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, MainActivity.class));
                        }
                        else {
                            Toast.makeText(context,"NOTE not deleted - "+i,Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel" , null).setCancelable(false);
                AlertDialog  alert = builder.create();
                alert.show();

            }
        });


        copynote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Text Label", Notetitle.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
            }
        });
        return rowview;

    }


}

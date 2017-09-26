package com.example.gourav_chawla.taskready;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by GOURAV_chawla on 24/05/2017.
 */

public class Myadapter extends ArrayAdapter<String> {
   private Activity context;
   private String[] tasktitle, taskdiscrip, taskdate, tasktime, taskreminder;

    public Myadapter(Activity context,String[] tasktitle ,String[] taskdiscrip,String[] taskdate,String[] tasktime,String[] taskreminder) {
        super(context,R.layout.taskview,tasktitle);
        this.context = context;
        this.tasktitle = tasktitle;
        this.taskdiscrip = taskdiscrip;
        this.taskdate = taskdate;
        this.tasktime = tasktime;
        this.taskreminder = taskreminder;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowview = inflater.inflate(R.layout.taskview,null,true);
      final   TextView title = (TextView) rowview.findViewById(R.id.titleviewid);
    final TextView discrip = (TextView) rowview.findViewById(R.id.discripid);
        Button taskdeleteb = (Button) rowview.findViewById(R.id.taskdelete);
        TextView date = (TextView) rowview.findViewById(R.id.dueviewid);
        TextView reminder = (TextView) rowview.findViewById(R.id.reminderview);
        title.setText(tasktitle[position]);
        discrip.setText(taskdiscrip[position]);
        date.setText(taskdate[position] +"  " + tasktime[position]);
        reminder.setText(taskreminder[position]);

         taskdeleteb.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(context);
                 builder.setTitle("Are u Sure ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         sqltaskhelper sqltaskhelper = new sqltaskhelper(context);
                         SQLiteDatabase db = sqltaskhelper.getWritableDatabase();
                         int i =0;
                         i = db.delete("task" ,"title = ?" ,new String[] {title.getText().toString()});
                         if(i>0){
                             Intent intent = new Intent(context,MainActivity.class);
                             Toast.makeText(context,"task deleted",Toast.LENGTH_SHORT).show();
                             context.startActivity(new Intent(context, MainActivity.class));
                         }
                         else {
                             Toast.makeText(context,"task not deleted - "+i,Toast.LENGTH_SHORT).show();
                         }
                     }
                 }).setNegativeButton("Cancel" , null).setCancelable(false);
                 AlertDialog  alert = builder.create();
                 alert.show();

             }
         });

        return rowview;

    }
}

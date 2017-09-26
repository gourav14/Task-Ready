package com.example.gourav_chawla.taskready;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    Button taskbutton , notebutton;
    FloatingActionButton floatingActionButton , fab_note , fab_task;
    Animation fab_open , fab_close  , fab_rclockwise , fab_ranticlockwise;
    boolean is_open =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskbutton  = (Button)findViewById(R.id.taskbutton);
        notebutton = (Button)findViewById(R.id.notebutton);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        fab_task = (FloatingActionButton)findViewById(R.id.fab_task);
        fab_note = (FloatingActionButton)findViewById(R.id.fab_note);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        fab_rclockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_rotate_clockwise);
        fab_ranticlockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_rotate_anticlockwise);

        fab_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , TaskDetail.class);
                startActivity(i);
            }
        });
        fab_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , noteDetail.class);
                startActivity(i);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(is_open){
                  fab_task.startAnimation(fab_close);
                  fab_note.startAnimation(fab_close);
                  floatingActionButton.startAnimation(fab_ranticlockwise);
                  fab_note.setClickable(false);
                  fab_task.setClickable(false);
                  is_open = false;
               /*   cancelfab.startAnimation(fab_close);
                  taskfab.startAnimation(fab_close);
                  notefab.startAnimation(fab_close);
                  cancelfab.setVisibility(View.INVISIBLE);
                  taskfab.setVisibility(View.INVISIBLE);
                  notefab.setVisibility(View.INVISIBLE); */


              }
              else{
                   fab_task.startAnimation(fab_open);
                  fab_note.startAnimation(fab_open);
                  floatingActionButton.startAnimation(fab_rclockwise);
                  fab_note.setClickable(true);
                  fab_task.setClickable(true);
                  is_open = true;
                 /* cancelfab.startAnimation(fab_open);
                  taskfab.startAnimation(fab_open);
                  notefab.startAnimation(fab_open);
                  cancelfab.setVisibility(View.VISIBLE);
                  taskfab.setVisibility(View.VISIBLE);
                  notefab.setVisibility(View.VISIBLE); */



              }
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentcontainer , new taskFragment());
        transaction.commit();

       /* Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String tasksave = extras.getString("task saved");
            Toast.makeText(this,tasksave,Toast.LENGTH_LONG).show();
            String toast = extras.getString("return note");
            Toast.makeText(this,toast,Toast.LENGTH_LONG).show();
        }
        */

        notebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notebutton.setBackgroundResource(R.drawable.my_button_bg);
                taskbutton.setBackgroundResource(R.drawable.default_button_color);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentcontainer, new noteFragment());
                transaction.commit();

            }
        });

        taskbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskbutton.setBackgroundResource(R.drawable.my_button_bg);
                notebutton.setBackgroundResource(R.drawable.default_button_color);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentcontainer, new taskFragment());
                transaction.commit();

            }
        });


    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            moveTaskToBack(true);
         //   finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.first,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== R.id.aboutusmenu){
            Intent i = new Intent(MainActivity.this ,aboutUs.class );
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}

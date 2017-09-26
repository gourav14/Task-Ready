package com.example.gourav_chawla.taskready;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TaskDetail extends AppCompatActivity {
   static int notifyid;
    int rem_day,rem_month,rem_year;
    int due_day , due_month , due_year;
    int due_hour ,due_min;
    final int[] rem_hourOfday = new int[1];
    final int[] rem_min = new int[1];
    Button datebutton , timebutton , save , calenderb , timeb;
    TextView reminderdate , remindertime,  duedate, duetime;
    EditText   title ,descrip;
    Switch reminderswitch;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        linearLayout = (LinearLayout)findViewById(R.id.activity_task_detail);
        title = (EditText) findViewById(R.id.titletaskid);
        descrip = (EditText)findViewById(R.id.taskdescriptionid);
        datebutton = (Button) findViewById(R.id.reminderdateid);
        timebutton = (Button) findViewById(R.id.remindertimeid);
        save = (Button)findViewById(R.id.savetaskid);
        reminderswitch =(Switch)findViewById(R.id.reminderswitch);
        remindertime = (TextView)findViewById(R.id.remindertimetextid);
        reminderdate = (TextView)findViewById(R.id.reminderdatetextid);
        duedate = (TextView) findViewById(R.id.startdateid);
        duetime = (TextView)findViewById(R.id.starttimeid);
        calenderb = (Button) findViewById(R.id.calenderview);
        timeb = (Button)findViewById(R.id.timeview);


       reminderswitch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(reminderswitch.isChecked() == true){
                   datebutton.setVisibility(View.VISIBLE);
                   timebutton.setVisibility(View.VISIBLE);
               }else{
                   datebutton.setVisibility(View.GONE);
                   timebutton.setVisibility(View.GONE);
               }
           }
       });
        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datelistener();
            }
        });

        calenderb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year =calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(TaskDetail.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        final Calendar c = Calendar.getInstance();
                        String mon =  c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
                        due_day = dayOfMonth;
                        due_month = month;
                        due_year = year;
                        duedate.setText(dayOfMonth+" "+ mon);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        timeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(calendar.HOUR);
                int min = calendar.get(calendar.MONTH);
                TimePickerDialog timePickerDialog = new TimePickerDialog(TaskDetail.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String period;

                        if(minute-15<0 && hourOfDay>0){
                            due_min = 60+ (minute-15);
                            due_hour = hourOfDay-1;

                        }else if(minute-15<0 && hourOfDay==0 && due_day >1){
                            due_min = 60 + (minute-15);
                            due_hour = 23;
                            due_day = due_day -1;
                        }else if(minute-15<0 && hourOfDay==0 && due_day ==1){
                            due_min = 60 + (minute-15);
                            due_hour = 23;
                            due_day = 30;
                        }else{
                            due_hour = hourOfDay;
                            due_min = minute-15;
                        }


                        if(hourOfDay>=13){
                            hourOfDay = hourOfDay-12;
                            period = "PM";
                        }else if(hourOfDay==12){
                            period = "PM";
                        }else {
                            period = "AM";
                        }
                        duetime.setText(hourOfDay + ":"+minute+" "+period);

                    }},hour,min,false);
                timePickerDialog.show();
            }
        });


        timebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              final  Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(calendar.HOUR);
                int min = calendar.get(calendar.MONTH);

                TimePickerDialog timePickerDialog = new TimePickerDialog(TaskDetail.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        rem_hourOfday[0] = hourOfDay;
                        rem_min[0] = minute;
                         String period;
                        if(hourOfDay>=13){
                            hourOfDay = (int)hourOfDay-12;
                            period = "PM";
                        }else if(hourOfDay==12){
                            period = "PM";
                        }else {
                            period = "AM";
                        }
                        remindertime.setText(hourOfDay + " : "+minute+" "+period);
                        remindertime.setVisibility(View.VISIBLE);

                    }},hour,min,false);
                timePickerDialog.show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().trim().isEmpty()){
                    Snackbar.make(linearLayout,"Title can't be empty",Snackbar.LENGTH_SHORT).show();
                }

               else if(duedate.getText().toString().trim().isEmpty()){
                    Snackbar.make(linearLayout,"Due Date date can't be empty",Snackbar.LENGTH_SHORT).show();
                }
               else if(duetime.getText().toString().trim().isEmpty()){
                Snackbar.make(linearLayout,"Due time can't be empty",Snackbar.LENGTH_SHORT).show();
            }

                else{
                    notifyid = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                    sqltaskhelper sqltaskhelper = new sqltaskhelper(TaskDetail.this);
                    SQLiteDatabase db = sqltaskhelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("notifyid",notifyid);
                    values.put("title", title.getText().toString());
                    values.put("discrip", descrip.getText().toString());
                    values.put("duedate", duedate.getText().toString());
                    values.put("duetime", duetime.getText().toString());
                    values.put("reminder", reminderdate.getText().toString() + "  " + remindertime.getText().toString());
                    db.insert("task", null, values);
                    db.close();
                    if(rem_day!=0 && rem_month!=0){
                    setalarm(rem_hourOfday[0],rem_min[0],rem_day,rem_month,rem_year);
                    }

                  setalarm(due_hour,due_min,due_day,due_month,due_year);
                    System.out.println("rem day - "+ due_month);
                    Intent i = new Intent(TaskDetail.this, MainActivity.class);
                    Toast.makeText(TaskDetail.this,"task created",Toast.LENGTH_SHORT).show();
                  startActivity(i);
                }
            }
        });

    }

    public void datelistener(){
        final  Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year =calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(TaskDetail.this, new DatePickerDialog.OnDateSetListener() {
            @Override

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final Calendar c = Calendar.getInstance();
                String mon =  c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
                rem_day = dayOfMonth;
                rem_month = month;
                rem_year = year;
                reminderdate.setText(dayOfMonth+" "+ mon);
                reminderdate.setVisibility(View.VISIBLE);

            }
        },year,month,day);
        datePickerDialog.show();


    }

    public void setalarm(int hr,int mn ,int dy ,int mnth ,int yr) {
        Intent myIntent = new Intent(this , com.example.gourav_chawla.taskready.Notification.class);
        myIntent.putExtra("notifyid",notifyid);
        myIntent.putExtra("title",title.getText().toString());
        myIntent.putExtra("discrip" , descrip.getText().toString() );
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,mnth);
        calendar.set(Calendar.YEAR,yr);
        calendar.set(Calendar.DAY_OF_MONTH,dy);
        calendar.set(Calendar.HOUR_OF_DAY, hr );
        calendar.set(Calendar.MINUTE,mn);
        calendar.set(Calendar.SECOND, 00);
        System.out.println("aa" + calendar.getTime());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }



}

package com.example.gourav_chawla.taskready;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by GOURAV_chawla on 07/06/2017.
 */

public class noti_reciever extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.delete".equals(action)) {
            int notifyid = intent.getIntExtra("notifyid", ((int) Calendar.getInstance().getTimeInMillis()));
            String Title = intent.getStringExtra("title");
            sqltaskhelper sqltaskhelper = new sqltaskhelper(context);
            SQLiteDatabase db = sqltaskhelper.getWritableDatabase();
            int i = 0;
            i = db.delete("task", "title = ?", new String[]{String.valueOf(notifyid)});

            if (i > 0) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(notifyid);
                Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Task already deleted", Toast.LENGTH_SHORT).show();
            }

        } else if ("android.intent.action.snooze".equals(action)) {
            int notifyid = intent.getIntExtra("notifyid", ((int) Calendar.getInstance().getTimeInMillis()));
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(notifyid);
            LayoutInflater inflater = LayoutInflater.from(context);
            View snoozeview = inflater.inflate(R.layout.noti_snoozer, null, true);
            TextView snooze10 = (TextView) snoozeview.findViewById(R.id.snooze_10);
            TextView snoozehour = (TextView) snoozeview.findViewById(R.id.snooze_hour);
            TextView snoozetomm = (TextView) snoozeview.findViewById(R.id.snooze_tommorow);
            snooze10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     setsnooze(context,intent,10);
                }
            });
            snoozehour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setsnooze(context,intent,1);
                }
            });
            snoozetomm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setsnooze(context,intent,24);
                }
            });
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(snoozeview).setCancelable(false);
            AlertDialog alert = builder.create();
            alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            alert.show();

        }
    }
    private void setsnooze(Context context ,Intent intent ,int time){
        Intent myIntent = new Intent(context , Notification.class);
        String Title = intent.getStringExtra("title");
        String discrip = intent.getStringExtra("discrip");
        myIntent.putExtra("title",Title);
        myIntent.putExtra("discrip" , discrip);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
           if(time==10){
               int min = calendar.get(Calendar.MINUTE);
               calendar.set(Calendar.MINUTE,min+10);
               setalarm(alarmManager,calendar,pendingIntent);
               Toast.makeText(context, "we will remind you again after 10 minutes", Toast.LENGTH_SHORT).show();
           }else if (time == 1){
               int hour = calendar.get(Calendar.HOUR_OF_DAY);
               calendar.set(Calendar.HOUR_OF_DAY,hour+1);
               setalarm(alarmManager,calendar,pendingIntent);
               Toast.makeText(context, "we will remind you after an hour", Toast.LENGTH_SHORT).show();
           }else if(time== 24){
               int day = calendar.get(Calendar.DAY_OF_MONTH);
               calendar.set(Calendar.DAY_OF_MONTH,day+1);
               setalarm(alarmManager,calendar,pendingIntent);
               Toast.makeText(context, "we will remind you tomorrow again", Toast.LENGTH_SHORT).show();
           }

        System.out.println("aa" + calendar.getTime());


    }
    private void setalarm(AlarmManager alarmManager , Calendar calendar ,PendingIntent pendingIntent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    }


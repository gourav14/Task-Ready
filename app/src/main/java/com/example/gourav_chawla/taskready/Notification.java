package com.example.gourav_chawla.taskready;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by GOURAV_chawla on 03/06/2017.
 */


    public class Notification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
           String Title = intent.getStringExtra("title");
        String discrip = intent.getStringExtra("discrip");
        int notifyid = intent.getIntExtra("notifyid" ,m );
        Toast.makeText(context,"aa"+notifyid,Toast.LENGTH_LONG).show();
        android.support.v4.app.NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.drawable.ic_stat_clock)
                .setContentTitle(Title)
                .setContentText(discrip).setAutoCancel(true);


        Intent i = new Intent();
        i.putExtra("notifyid" ,notifyid);
        i.putExtra("title" ,Title);
        i.setAction("android.intent.action.delete");
        PendingIntent pendingIdelete = PendingIntent.getBroadcast(context,12345,i,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.addAction(R.drawable.ic_action_delete,"Delete",pendingIdelete);
        Intent snoozeI = new Intent();
        snoozeI.setAction("android.intent.action.snooze");
        snoozeI.putExtra("notifyid" ,notifyid);
        snoozeI.putExtra("title" , Title);
        snoozeI.putExtra("discrip",discrip);
        PendingIntent pendingIsnooze = PendingIntent.getBroadcast(context,12345,snoozeI,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mBuilder.addAction(R.drawable.ic_stat_clock,"snooze",pendingIsnooze);

        manager.notify(notifyid , mBuilder.build());

    }
    }





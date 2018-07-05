package com.example.madchocho.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
       Intent in = new Intent(context,GpsActivity2.class);
       context.startActivity(in);

    }
}

package com.example.demet.demetandroid;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class NewPlan extends AppCompatActivity implements View.OnClickListener{

    private int notificationId=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);
        findViewById(R.id.setalarmbtn).setOnClickListener(this);
        findViewById(R.id.cancelalarmbtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText editText=findViewById(R.id.editTextAlarm);
        TimePicker timePicker=findViewById(R.id.timepicker);
        Intent intent=new Intent(NewPlan.this,AlarmAlarm.class);

        intent.putExtra("notificationId",notificationId);
        intent.putExtra("todo",editText.getText().toString());

        PendingIntent alarmIntent=PendingIntent.getBroadcast(NewPlan.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarm=(AlarmManager) getSystemService(ALARM_SERVICE);
        switch (view.getId()){
            case  R.id.setalarmbtn:
                int hour=timePicker.getCurrentHour();
                int munite=timePicker.getCurrentMinute();

                Calendar startTime=Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY,hour);
                startTime.set(Calendar.MINUTE,munite);
                long alarmStartTime=startTime.getTimeInMillis();
                alarm.set(AlarmManager.RTC_WAKEUP,alarmStartTime,alarmIntent);
                Toast.makeText(this,"Done!",Toast.LENGTH_LONG).show();
                break;
            case R.id.cancelalarmbtn:
                alarm.cancel(alarmIntent);
                Toast.makeText(this,"Canceled!",Toast.LENGTH_LONG).show();
                break;
        }

    }

    }

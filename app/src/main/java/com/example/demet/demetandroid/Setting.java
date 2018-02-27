package com.example.demet.demetandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

public class Setting extends AppCompatActivity {
DayNightSwitch dayNightSwitch;
View background_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dayNightSwitch=(DayNightSwitch) findViewById(R.id.daynight);
        background_view=findViewById(R.id.background_view);
        dayNightSwitch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean b) {
                if(b){
                    Toast.makeText(Setting.this,"vfdvd",Toast.LENGTH_LONG).show();
                    background_view.setAlpha(1f);
                }else{
                    Toast.makeText(Setting.this,"cdsd",Toast.LENGTH_LONG).show();
                    background_view.setAlpha(0f);
                }
            }
        });
    }

}

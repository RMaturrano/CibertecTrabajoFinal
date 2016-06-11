package com.project.cibertec.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.cibertec.finalproject.database.DataBaseHelper;
import com.project.cibertec.finalproject.database.DataBaseSingleton;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_activity);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(SplashActivity.this);
                    try {
                        dataBaseHelper.createDataBase();
                        new DataBaseSingleton(SplashActivity.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

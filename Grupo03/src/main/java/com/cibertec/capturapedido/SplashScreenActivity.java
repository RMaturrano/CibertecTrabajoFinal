package com.cibertec.capturapedido;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.cibertec.capturapedido.util.GifView;

public class SplashScreenActivity extends AppCompatActivity {
//    GifView gifView;
//    private static final int PROGRESS = 0x1;
//    private ProgressBar pbSplachCargando;
//    private int mProgressStatus = 0;
//    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        //gifView = (GifView) findViewById(R.id.gifSplashIcon);
//        pbSplachCargando = (ProgressBar) findViewById(R.id.pbSplachCargando);
//        pbSplachCargando.setProgress(0);

        final int totalProgressTime = 100;
        final Thread timerThread = new Thread() {
            public void run() {
                try {
                    int jumpTime = 0;

                    while (jumpTime < totalProgressTime) {
                        sleep(200);
                        jumpTime += 5;
//                        pbSplachCargando.setProgress(jumpTime);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();
    }
}

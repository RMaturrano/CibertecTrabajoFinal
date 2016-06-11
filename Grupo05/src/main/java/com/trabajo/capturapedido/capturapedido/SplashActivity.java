package com.trabajo.capturapedido.capturapedido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

/**
 * Created by Shutten on 04/06/2016.
 */
public class SplashActivity extends Activity {

    private Handler mHandler;
    private Runnable mRunnable;
    private static final long Splash_Duration=3000L;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                CerrarSplash();
            }
        };
    }


    private void CerrarSplash(){
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable,Splash_Duration);
    }


}

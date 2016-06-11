package cibertec.pe.proyectofinal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by SOPORTE on 31/05/2016.
 */
public class Primera_Actividad_Splash extends AppCompatActivity {

    // TIEMPO DE DURACION
    private static final long SPLASH_TIEMPO_DURACION = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ORIENTACION DE PANTALLA
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //OCULTAMOS LA BARRA DE TITULO
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.primera_actividad_splash);

        //TimerTask se encargaba de hacer repeticiones cada X segundos

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // llamamos a la siguiente actividad  utilizando Intent
                Intent intent = new Intent().setClass(Primera_Actividad_Splash.this,Segunda_Actividad_Login.class);
                startActivity(intent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_TIEMPO_DURACION);
    }
}

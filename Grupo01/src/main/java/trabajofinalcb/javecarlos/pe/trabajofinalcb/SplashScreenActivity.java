package trabajofinalcb.javecarlos.pe.trabajofinalcb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Administrator on 18/05/2016.
 */
public class SplashScreenActivity extends AppCompatActivity {
    private TextView tvProgress;
    private ProgressBar progressBar;
    int progressStatusCounter = 0;
    Handler progressHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setScaleY(3f);

        tvProgress = (TextView) findViewById(R.id.tvProgress);

        Thread timerThread = new Thread(new Runnable() {
            public void run() {
                while (progressStatusCounter < 100) {
                    progressStatusCounter += 1;
                    progressHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatusCounter);
                            //actualizando el estado del textview
                            tvProgress.setText(progressStatusCounter + " %");
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Intent intentLogin = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
            }
        });
        timerThread.start();
    }
}

package com.cibertec.capturapedido.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.cibertec.capturapedido.R;

import java.io.InputStream;

/**
 * Created by bestrada on 09/05/2016.
 */
public class GifView extends View {
    private InputStream gifInputStream;
    private Movie gifMovie;
    private int movieWidth,movieHeiht;
    private long movieDuration,movieStar;

    public GifView(Context context) {
        super(context);
        init(context);
    }

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setFocusable(true);
        gifInputStream=context.getResources().openRawResource(+R.drawable.icon_splashscreen);
        gifMovie=Movie.decodeStream(gifInputStream);
        movieWidth=gifMovie.width();
        movieHeiht=gifMovie.height();
        movieDuration=gifMovie.duration();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(movieWidth,movieHeiht);
    }

    public int getMovieWidth(){
        return movieWidth;
    }

    public  int getMovieHeiht()
    {
        return movieHeiht;
    }

    public long getMovieDuration(){
        return movieDuration;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long now = SystemClock.uptimeMillis();
        if(movieStar==0){
            movieStar=now;
        }

        if(gifMovie!=null){
            int dur=gifMovie.duration();
            if(dur==0){
                dur=100;
            }
            int relTime=(int)((now-movieStar)%dur);
            gifMovie.setTime(relTime);
            gifMovie.draw(canvas,0,0);
            invalidate();
        }
    }

}

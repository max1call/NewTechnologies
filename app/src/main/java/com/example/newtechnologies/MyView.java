package com.example.newtechnologies;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import android.widget.TextView;


public class MyView extends SurfaceView implements SurfaceHolder.Callback {

//    Context c;

    MyThread thread ;
    public TextView tvt1;
    CharSequence str;

    MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        tvt1 = (TextView) findViewById(R.id.tv1);

        Log.w("Target", "MyView: thread = new MyThread(holder, context...");
        thread = new MyThread(this, holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {
                if(m.what==1){
//                    str = "countLive= "+m.getData().getInt("countLive");
//                    tvt1.setText(str);
                }
            }
        });
        setFocusable(true);
    }
    public MyThread getThread() {
        return thread;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) thread.pause();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean re = true;
        thread.setRunning(false);
        while (re){
            try {
                thread.join();
                re=false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTextView(TextView tvt1) {
        this.tvt1=tvt1;
    }
}

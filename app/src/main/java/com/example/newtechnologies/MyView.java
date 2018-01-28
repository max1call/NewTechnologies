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

/**
 * Created by асер on 25.01.2018.
 */

public class MyView extends SurfaceView implements SurfaceHolder.Callback {

//    Context c;
    InputOutput inputOutput;
    MyThread thread ;
    private TextView mStatusText;
    MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.w("Target", "MyView: Constructor begin");
//        c=context;
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        inputOutput = new InputOutput(this);
        Log.w("Target", "MyView: thread = new MyThread(holder, context...");
        thread = new MyThread(holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {
                mStatusText.setVisibility(m.getData().getInt("viz"));
                mStatusText.setText(m.getData().getString("text"));
            }
        });


        setFocusable(true);
    }

    public MyThread getThread() {
        return thread;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        return thread.doKeyDown(keyCode, msg);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent msg) {
        return thread.doKeyUp(keyCode, msg);
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

}

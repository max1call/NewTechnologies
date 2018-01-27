package com.example.newtechnologies;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class InputOutput implements OnTouchListener{

    float x;
    float y;
    MyView myView;
    String TAG="Target";

    public InputOutput(MyView myView) {
        this.myView = myView;
        myView.setOnTouchListener(this);
    }
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        tv = new TextView(this);
//        tv.setOnTouchListener(this);
//        setContentView(tv);
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                Log.w(TAG, "Down: " + x + "," + y);
                break;
            case MotionEvent.ACTION_MOVE: // движение
                Log.w(TAG, "Move: " + x + "," + y);
                break;
            case MotionEvent.ACTION_UP: // отпускание
            case MotionEvent.ACTION_CANCEL:
                Log.w(TAG, "Up: " + x + "," + y);
                break;
        }
        return true;
    }
}


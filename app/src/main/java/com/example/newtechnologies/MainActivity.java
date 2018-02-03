package com.example.newtechnologies;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import javax.security.auth.login.LoginException;

public class MainActivity extends Activity {

    String TAG="Target";
    MyView myView;
    MyThread myThread;
    public TextView tvt1;
    public TextView tvt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        handler = new Handler() {
//            public void handleMessage(Message msg) {
//                if (msg.what == 1) {
//                    str = "down x= " + msg.arg1 + "; y= " + msg.arg2;
//                    tvt1.setText(str);
//                }
//                if (msg.what == 2) {
//                    str = "down x= " + msg.arg1 + "; y= " + msg.arg2;
//                    tvt2.setText(str);
//                }
//            }
//        };
        setContentView(R.layout.activity_main);
        myView= (MyView) findViewById(R.id.myview);
        myThread=myView.getThread();
        tvt1 = (TextView) findViewById(R.id.tv1);
        tvt2 = (TextView) findViewById(R.id.tv2);
        myView.setTextView(tvt1, tvt2);

//        Log.w(TAG, "Main: myThread=myView.getThread()");

//        tv = (TextView) findViewById(R.id.tv);
//        tv.setText("This is main");
//
//        handler = new Handler(){
//            public void handleMessage(Message msg){
//                Log.i(TAG, "my Handler"+msg.what);
//            };
//        };
//
//        MyThread t = new MyThread(tv, handler);
//
//        t.start();
//        for(int i=0; i<10; i++) {
//            s="MainActivity"+i;
//            Log.i(TAG, s);
//            try{
//                Thread.sleep(1100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
//    public Handler getHandler(){
//        return handler;
//    }
//    public Rect scale(){
//        Rect rect = new Rect();
//        WindowManager w = this.getWindowManager();
//        Display d = w.getDefaultDisplay();
//        d.getRectSize(rect);
//        return rect;
//
//    }
}

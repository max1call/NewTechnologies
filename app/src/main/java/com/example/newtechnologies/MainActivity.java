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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        myView= (MyView) findViewById(R.id.myview);
        myThread=myView.getThread();
        tvt1 = (TextView) findViewById(R.id.tv1);
        tvt2 = (TextView) findViewById(R.id.tv2);
        myView.setTextView(tvt1, tvt2);
    }
}

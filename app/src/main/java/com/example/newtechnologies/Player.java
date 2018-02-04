package com.example.newtechnologies;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

public class Player extends PlayObject implements Constants {

    private int heading, x1, y1, dx, dy, lengthJump;
    private boolean readXY = false;
    private int curentState;
    private int speedFly;
    private double radians;
    Kuvshinka lastKuvshinka;
    ArrayList<Kuvshinka> arrayKuvshinka;
    Message msg;
    Handler handler;
    MyThread myThread;
    int timeUnderWater;
    long now;

    Player(Map<String, Drawable> hashMapImg, Map<String, Integer> hashMapSize, int x, int y, int speedFly,
           ArrayList<Kuvshinka> arrayKuvshinka, MyThread myThread, int timeUnderWater, Handler handler) {
        super(hashMapImg, hashMapSize, x, y);
        this.speedFly=speedFly;
        this.handler = handler;
        this.myThread=myThread;
        this.timeUnderWater = timeUnderWater;
        lengthJump = hashMapSize.get("lengthJump");
        heading = 0;
        this.speedFly = 10;
        rect.set(x, y, x + hashMapSize.get("idleFrogWidth"), y + hashMapSize.get("idleFrogHeight"));
        this.arrayKuvshinka = arrayKuvshinka;
        lastKuvshinka = arrayKuvshinka.get(0);
        setState(STATE_IDLE);
    }

    public void setState(int curentState) {
        this.curentState = curentState;
        if(curentState == STATE_IDLE){
            curentImg = hashMapImg.get("idleFrogImg");
            rect.set(rect.left, rect.top, rect.left + hashMapSize.get("idleFrogWidth"), rect.top + hashMapSize.get("idleFrogHeight"));
        }else if (curentState == STATE_MOVE){
            curentImg = curentImg = hashMapImg.get("flyFrogImg");
            rect.set(rect.left, rect.top, rect.left + hashMapSize.get("flyFrogWidth"), rect.top + hashMapSize.get("flyFrogHeight"));
//        }else if (curentState == STATE_BULK){
//
//
//        }else if (curentState == STATE_LOSE){
//            //game over
//        }else if (curentState == STATE_WIN){
//            //win
        }else if (curentState == STATE_BULK) {
            now = System.currentTimeMillis();
        }
    }

    public int getHeading() {
        return heading;
    }
    public void setTouchDown(float x, float y){
        if (rect.contains((int)x, (int)y) && curentState == STATE_IDLE) {
            readXY = true;
            x1 = rect.centerX();
            y1 = rect.centerY();

            Log.i("fly", "xStartJump = "+x1+ "; yStartJump = "+y1);
        }
    }
    public void setHeading(float x, float y){
        if (readXY) {
            radians = Math.acos((y-y1)/Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1)));
            if (x>x1)radians=(-1)*radians;
            heading = (int) (radians*360/(2 * Math.PI));
            Log.i("fly", "heading = "+heading);
        }
    }
    public void setTouchUp(float x, float y){
        if (readXY) {
//            msg = handler.obtainMessage(2, (int) x, (int) y);
//            handler.sendMessage(msg);
            dy = (int) (speedFly*Math.cos(radians));
            dx = (int) (speedFly*Math.sin(radians));
            setState(STATE_MOVE);
            readXY = false;
        }
    }
    public void updatePhysics() {
        msg = handler.obtainMessage();
        Bundle b = new Bundle();
        b.putBoolean("readXY", readXY);
        msg.setData(b);
        msg.what=1;

        handler.sendMessage(msg);
        Log.i("fly", "curentState= "+curentState);
        if (curentState == STATE_MOVE) {
            rect.offset(dx, -dy);
            Log.i("fly", "xFinishJump = "+ dx + "; yFinishJump = "+ dy);
            if (Math.sqrt((rect.centerX()-x1)*(rect.centerX()-x1)+(rect.centerY()-y1)*(rect.centerY()-y1))>lengthJump) {
                checkLocation();
            }
        }else if (curentState == STATE_BULK) {
            if(System.currentTimeMillis()>now+ timeUnderWater) {
                rect.offset(lastKuvshinka.getRect().centerX() - rect.centerX(), lastKuvshinka.getRect().centerY() - rect.centerY());
                myThread.setState(STATE_RUNING);
            }
        }
    }
    private void checkLocation() {
        boolean contains = false;
        for(Kuvshinka k : arrayKuvshinka) {
            if (k.getRect().contains(rect.centerX(), rect.centerY())) {
                lastKuvshinka = k;
                rect.offset(k.getRect().centerX()-rect.centerX(), k.getRect().centerY()-rect.centerY());
                setState(STATE_IDLE);
                contains = true;
            }
        }
        if (!contains) myThread.setState(STATE_BULK);
    }

}

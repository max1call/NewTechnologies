package com.example.newtechnologies;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by асер on 26.01.2018.
 */

public class Player extends PlayObject implements Constants {

    private int heading, x1, y1, dx, dy, lengthJump;
    private boolean readXY = false;
    private int curentState;
    private int speedFly;
    private double radians;
    ArrayList<Kuvshinka> arrayKuvshinka;
    Message msg;
    Handler handler;

    Player(Map<String, Drawable> hashMapImg, Map<String, Integer> hashMapSize, int x, int y, int speedFly, ArrayList<Kuvshinka> arrayKuvshinka, Handler handler) {
        super(hashMapImg, hashMapSize, x, y);
        this.speedFly=speedFly;
        this.handler = handler;
        lengthJump = hashMapSize.get("lengthJump");
        heading = 0;
        this.speedFly = 10;
        rect.set(x, y, x + hashMapSize.get("idleFrogWidth"), y + hashMapSize.get("idleFrogHeight"));
        this.arrayKuvshinka = arrayKuvshinka;
        setState(STATE_IDLE);
//        Log.i(TAG, "Player Finish Constr ");
    }

    public void setState(int curentState) {
        this.curentState = curentState;
//        Log.i(TAG, "Player Begin setState ");
        if(curentState == STATE_IDLE){
            curentImg = hashMapImg.get("idleFrogImg");
            rect.set(rect.left, rect.top, rect.left + hashMapSize.get("idleFrogWidth"), rect.top + hashMapSize.get("idleFrogHeight"));
        }else if (curentState == STATE_MOVE){
            curentImg = curentImg = hashMapImg.get("flyFrogImg");;
            rect.set(rect.left, rect.top, rect.left + hashMapSize.get("flyFrogWidth"), rect.top + hashMapSize.get("flyFrogHeight"));
        }else if (curentState == STATE_BULK){
            curentImg = curentImg = hashMapImg.get("bulkImg");
            heading = 0;
            rect.set(rect.left, rect.top, rect.left + hashMapSize.get("bulkWidth"), rect.top + hashMapSize.get("bulkWidth"));
        }else if (curentState == STATE_LOSE){
            //game over
        }else if (curentState == STATE_WIN){
            //win
        }
//        Log.i(TAG, "Player Finish setState ");
    }

    public int getHeading() {
//        Log.i(TAG, "Player getHeading");
        return heading;
    }
    public void setTouchDown(float x, float y){
        if (rect.contains((int)x, (int)y)) {
            readXY = true;
            x1 = rect.centerX();
            y1 = rect.centerY();

            Log.i("fly", "xStartJump = "+x1+ "; yStartJump = "+y1);
        }
    }
    public void setHeading(float x, float y){
        if (readXY) {//(dy-y1) / sqrt((dx-x1)кв + (dy-y1)кв)
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
//            radians = 2 * Math.PI * heading / 360;
            dy = (int) (speedFly*Math.cos(radians));
            dx = (int) (speedFly*Math.sin(radians));

            msg = handler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("dx", (int) dx);
            b.putInt("dy", (int) dy);
            b.putInt("radians", (int) (radians*360/6.28));
            msg.setData(b);
            msg.what=1;
            handler.sendMessage(msg);
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    "Пора покормить кота!", Toast.LENGTH_SHORT);
//            toast.show();
            setState(STATE_MOVE);
            readXY = false;
        }
    }
    public void updatePhysics() {
        Log.i("fly", "curentState= "+curentState);
        if (curentState == STATE_MOVE) {
            rect.offset(dx, -dy);
            Log.i("fly", "xFinishJump = "+ dx + "; yFinishJump = "+ dy);
            if (Math.sqrt((rect.centerX()-x1)*(rect.centerX()-x1)+(rect.centerY()-y1)*(rect.centerY()-y1))>lengthJump) {
                checkLocation();
            }
        }
    }
    private void checkLocation() {
        boolean contains = false;
        for(Kuvshinka k : arrayKuvshinka) {
            if (k.getRect().contains(rect.centerX(), rect.centerY())) {
                rect.offset(k.getRect().centerX()-rect.centerX(), k.getRect().centerY()-rect.centerY());
                setState(STATE_IDLE);
                contains = true;
            }
        }
        if (!contains) setState(STATE_BULK);
    }

}

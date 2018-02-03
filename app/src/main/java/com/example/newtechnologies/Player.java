package com.example.newtechnologies;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Map;

/**
 * Created by асер on 26.01.2018.
 */

public class Player extends PlayObject implements Constants {

    private int heading, x1, y1, x2, y2, lengthJump;
    private boolean readXY = false;
    private int curentState;
    private int speedFly = 5;
    private double radians;
    Message msg;
    Handler handler;

    Player(Map<String, Drawable> hashMapImg, Map<String, Integer> hashMapSize, int x, int y, Handler handler) {
        super(hashMapImg, hashMapSize, x, y);
        this.handler = handler;
        lengthJump = hashMapSize.get("lengthJump");
        heading = 0;

        setState(STATE_IDLE);
//        Log.i(TAG, "Player Finish Constr ");
    }

    public void setState(int curentState) {
        this.curentState = curentState;
//        Log.i(TAG, "Player Begin setState ");
        if(curentState == STATE_IDLE){
            curentImg = hashMapImg.get("idleFrogImg");
            rect.set(x, y, x + hashMapSize.get("idleFrogWidth"), y + hashMapSize.get("idleFrogHeight"));
        }else if (curentState == STATE_MOVE){
            curentImg = curentImg = hashMapImg.get("flyFrogImg");;

        }else if (curentState == STATE_BULK){
            curentImg = curentImg = hashMapImg.get("bulkImg");;

        }else if (curentState == STATE_LOSE){
            //game over
        }else if (curentState == STATE_WIN){
            //win
        }
//        Log.i(TAG, "Player Finish setState ");
    }
    public void updatePhysics() {
//        Log.i("fly", "updatePhysics");
        Log.i("fly", "curentState= "+curentState);
        if (curentState == STATE_MOVE) {
            rect.offset(x2, y2);
            Log.i("fly", "xFinishJump = "+x2+ "; yFinishJump = "+y2);
            if (Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1))>lengthJump)
                setState(STATE_IDLE);
        }
//        Log.i(TAG, "Player updatePhysics");
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
            msg = handler.obtainMessage(1, (int) x, (int) y);
            handler.sendMessage(msg);
            Log.i("fly", "xStartJump = "+x1+ "; yStartJump = "+y1);
        }
    }
    public void setHeading(float x, float y){
        if (readXY) {//(y2-y1) / sqrt((x2-x1)кв + (y2-y1)кв)
            radians = Math.acos((y-y1)/Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1)));
            if (x>x1)radians=(-1)*radians;
            heading = (int) (radians*360/(2 * Math.PI));
            Log.i("fly", "heading = "+heading);
        }
    }
    public void setTouchUp(float x, float y){
        if (readXY) {
            msg = handler.obtainMessage(2, (int) x, (int) y);
            handler.sendMessage(msg);
//            radians = 2 * Math.PI * heading / 360;
            x2 = (int) (speedFly*Math.cos(radians));
            y2 = (int) (speedFly*Math.sin(radians));
            setState(STATE_MOVE);
            readXY = false;
        }
    }

}

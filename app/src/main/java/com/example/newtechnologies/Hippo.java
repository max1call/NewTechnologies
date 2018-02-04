package com.example.newtechnologies;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.Map;

public class Hippo extends PlayObject implements Constants {

//    private Drawable hippoImg;
    private int dy, speed, maxCanvasHeight, minCanvasHeight ;//x, y, hippoWidth, hippoHeight;

    Hippo(Map<String, Drawable> hashMapImg, Map<String, Integer> hashMapSize, int x, int y){
        super(hashMapImg, hashMapSize, x, y);
        curentImg = hashMapImg.get("hippoImg");
        rect.set(x, y, x + hashMapSize.get("hippoWidth"), y + hashMapSize.get("hippoHeight"));
        maxCanvasHeight = (int) (hashMapSize.get("mCanvasHeight")*0.9);
        minCanvasHeight = (int) (hashMapSize.get("mCanvasHeight")*0.07);
        setState(STATE_MOVE);
    }
    public void updatePhysics() {
        rect.offset(0, speed);
        if (rect.bottom > maxCanvasHeight || rect.top < minCanvasHeight) speed=(-1)*speed;
    }
    public void setState(int curentState) {
        if (curentState == STATE_MOVE) {
            speed = hashMapSize.get("lengthJump")/100;
        } else if (curentState == STATE_IDLE) {
            speed = 0;
        }
    }
//    private void defineSizeImg() {
//        hippoWidth = hippoImg.getIntrinsicWidth();
//        hippoHeight = hippoImg.getIntrinsicHeight();
//    }
//    public void resizeImg(float coefficientScale) {
//        hippoWidth = (int) (hippoWidth/coefficientScale);
//        hippoHeight = (int) (hippoHeight/coefficientScale);
//    }
//
//
//    public void setState(int curentState) {
//        if(curentState == STATE_IDLE){
//
//        }else if (curentState == STATE_MOVE) {
//
//        }
//    }
//    public Drawable getCurentImg() {
//        return curentImg;
//    }
}

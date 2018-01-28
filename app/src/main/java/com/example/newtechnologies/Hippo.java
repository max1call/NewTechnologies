package com.example.newtechnologies;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.Map;

public class Hippo extends PlayObject {

    private Drawable hippoImg;
    private int x, y, hippoWidth, hippoHeight;

    Hippo(Map<String, Drawable> hashMapImg, Map<String, Integer> hashMapSize, int x, int y){
        super(hashMapImg, hashMapSize, x, y);

        curentImg = hashMapImg.get("hippoImg");
        rect.set(x, y, x + hashMapSize.get("hippoWidth"), y + hashMapSize.get("hippoHeight"));
    }
    private void updatePhysics() {

    }
    public void setState(int curentState) {
        if (curentState == STATE_MOVE) {

        } else if (curentState == STATE_IDLE) {

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

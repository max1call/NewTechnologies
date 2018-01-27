package com.example.newtechnologies;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Hippo extends PlayObject {

    private Drawable hippoImg;
    private int x, y, hippoWidth, hippoHeight;

    Hippo(Context context, int x, int y){
        super(context, x, y);
        hippoImg = context.getResources().getDrawable(R.drawable.begemot);
        ObjImg = hippoImg;
        defineSizeImg();
        curentImg = ObjImg;
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

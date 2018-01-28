package com.example.newtechnologies;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class PlayObject implements Constants {

    protected Drawable curentImg, ObjImg;
    protected int x, y, ObjWidth, ObjHeight;
    Rect rect;
    String TAG = "Target";

    PlayObject(Context context, int x, int y){
        Log.i(TAG, "PlayObject Begin Constr");
        this.x=x;
        this.y=y;
        rect = new Rect();
        Log.i(TAG, "PlayObject Finish Constr");
    }

    protected void defineSizeImg() {
        Log.i(TAG, "PlayObject Begin defineSizeImg");
        ObjWidth = ObjImg.getIntrinsicWidth();
        ObjHeight = ObjImg.getIntrinsicHeight();
        Log.i(TAG, "PlayObject Finish defineSizeImg");
    }
    public void resizeImg(float coefficientScale) {
        Log.i(TAG, "PlayObject Begin resizeImg");
        ObjWidth = (int) (ObjWidth/coefficientScale);
        ObjHeight = (int) (ObjHeight/coefficientScale);
        rect.set(x, y, x+ObjWidth, y+ObjHeight);
        Log.i(TAG, "PlayObject Finish resizeImg");
    }

    public Drawable getCurentImg() {
        Log.i(TAG, "PlayObject getCurentImg");
        return curentImg;
    }
    public Rect getRect() {
        Log.i(TAG, "PlayObject getRect");
        return rect;
    }
    public int getWidth() {
        Log.i(TAG, "PlayObject getWidth");
        return ObjWidth;
    }
}

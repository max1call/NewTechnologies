package com.example.newtechnologies;

import android.graphics.drawable.Drawable;

import java.util.Map;

public class Win extends PlayObject {

    Boolean canUpdate = false;
    int centerX, centerY, ddx, ddy, bgWidth, bgHeight;
    int dx;
    int dy;
    int speed = 30;
    boolean canDownSize = false;
//    boolean canChangeSize = true;

    Win(Map<String, Drawable> hashMapImg, Map<String, Integer> hashMapSize, int x, int y) {
        super(hashMapImg, hashMapSize, x, y);
        curentImg = hashMapImg.get("winImg");
        rect.set(x, y, x + 1, y + 1);
        bgWidth = hashMapSize.get("mCanvasWidth");
        bgHeight = hashMapSize.get("mCanvasHeight");
        centerX = bgWidth/2;
        centerY = bgHeight/2;
        dx = bgWidth/5;
        dy = bgHeight/5;
        ddx = bgWidth/speed;
        ddy = bgHeight/speed;
    }
    public void setCanUpdate(Boolean b){
        canUpdate = b;
    }
    public void updatePhysics() {
        if (canUpdate){
            if(canDownSize){
                downSize();
            } else upSize();
        }
    }
    public void upSize() {
        if(dx < bgWidth*0.6 && dy < bgHeight*0.6) {
            rect.set(centerX - dx, centerY - dy, centerX + dx, centerY + dy);
            dx += ddx;
            dy += ddy;
        } else canDownSize = true;
    }
    public void downSize() {
        if(dx > bgWidth*0.5 && dy > bgHeight*0.5) {
            rect.set(centerX - dx, centerY - dy, centerX + dx, centerY + dy);
            dx -= ddx/2;
            dy -= ddy/2;
        } else canUpdate = false;
    }
}

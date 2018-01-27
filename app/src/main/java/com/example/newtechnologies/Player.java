package com.example.newtechnologies;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by асер on 26.01.2018.
 */

public class Player implements Constants {

    private int x, y;
    private int xHeading, yHeading;
    private int heading;
    private int curentWidth, curentHeight, idleFrogWidth, idleFrogHeight, flyFrogWidth, flyFrogHeight, bulkWidth, bulkHeight;
    private int curentState;
    Rect rect;

    private Drawable curentImg;
    private Drawable idleFrogImg;
    private Drawable flyFrogImg;
    private Drawable bulkImg;

    Player(Context context, int x, int y) {
        initImg(context);
        defineSizeImg();
        curentState = STATE_IDLE;
        rect = new Rect();
        this.x=x;
        this.y=y;
    }
    private void initImg(Context context) {
        idleFrogImg = context.getResources().getDrawable(R.drawable.idle_frog);
        flyFrogImg = context.getResources().getDrawable(R.drawable.fly_frog);
        bulkImg = context.getResources().getDrawable(R.drawable.bulk);
    }
    private void defineSizeImg() {
        idleFrogWidth = idleFrogImg.getIntrinsicWidth();
        idleFrogHeight = idleFrogImg.getIntrinsicHeight();
        flyFrogWidth = flyFrogImg.getIntrinsicWidth();
        flyFrogHeight = flyFrogImg.getIntrinsicHeight();
        bulkWidth = bulkImg.getIntrinsicWidth();
        bulkHeight = bulkImg.getIntrinsicHeight();
    }
    public void resizeImg(int coefficientScale) {
        idleFrogWidth = idleFrogWidth/coefficientScale;
        idleFrogHeight = idleFrogHeight/coefficientScale;
        flyFrogWidth = flyFrogWidth/coefficientScale;
        flyFrogHeight = flyFrogHeight/coefficientScale;
        bulkWidth = bulkWidth/coefficientScale;
        bulkHeight = bulkHeight/coefficientScale;
    }
    public void setState(int curentState) {
        if(curentState == STATE_IDLE){
            curentImg = idleFrogImg;
            rect.set(x, y, x + idleFrogWidth, y + idleFrogHeight);//////////////////
//            curentWidth = idleFrogWidth;
//            curentHeight = idleFrogHeight;
            xHeading = rect.centerX();//x + idleFrogWidth/2;
            yHeading = rect.centerY();//y + idleFrogHeight/2;
        }else if (curentState == STATE_FLY){
            curentImg = flyFrogImg;
            curentWidth = flyFrogWidth;
            curentHeight = flyFrogHeight;
            xHeading = x + flyFrogWidth/2;
            yHeading = y + flyFrogHeight/2;
        }else if (curentState == STATE_BULK){
            curentImg = bulkImg;
            curentWidth = bulkWidth;
            curentHeight = bulkHeight;
        }else if (curentState == STATE_LOSE){
            //game over

        }else if (curentState == STATE_WIN){
            //win
        }

    }
    public void updatePhysics() {

    }

    public Drawable getCurentImg() {
        return curentImg;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getHeading() {
        return heading;
    }

    public int getxHeading() {
        return xHeading;
    }

    public int getyHeading() {
        return yHeading;
    }

    public int getCurentWidth() {
        return x+curentWidth+50;
    }

    public int getCurentHeight() {
        return y+curentHeight+50;
    }


}

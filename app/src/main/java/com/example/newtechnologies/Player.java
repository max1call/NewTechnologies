package com.example.newtechnologies;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by асер on 26.01.2018.
 */

public class Player extends PlayObject implements Constants {

    private int heading;
    private int idleFrogWidth, idleFrogHeight, flyFrogWidth, flyFrogHeight, bulkWidth, bulkHeight;

    private Drawable idleFrogImg;
    private Drawable flyFrogImg;
    private Drawable bulkImg;

    Player(Context context, int x, int y) {
        super(context, x, y);
        Log.i(TAG, "Player Begin Constr ");
        initImg(context);
        defineSizeImg();
        setState(STATE_IDLE);
        Log.i(TAG, "Player Finish Constr ");
    }
    private void initImg(Context context) {
        Log.i(TAG, "Player Begin initImg ");
        idleFrogImg = context.getResources().getDrawable(R.drawable.idle_frog);
        flyFrogImg = context.getResources().getDrawable(R.drawable.fly_frog);
        bulkImg = context.getResources().getDrawable(R.drawable.bulk);
        Log.i(TAG, "Player Finish initImg ");
    }
    protected void defineSizeImg() {
        Log.i(TAG, "Player Begin defineSizeImg ");
        idleFrogWidth = idleFrogImg.getIntrinsicWidth();
        idleFrogHeight = idleFrogImg.getIntrinsicHeight();
        flyFrogWidth = flyFrogImg.getIntrinsicWidth();
        flyFrogHeight = flyFrogImg.getIntrinsicHeight();
        bulkWidth = bulkImg.getIntrinsicWidth();
        bulkHeight = bulkImg.getIntrinsicHeight();
        Log.i(TAG, "Player Finish defineSizeImg ");

    }
    public void resizeImg(float coefficientScale){
        Log.i(TAG, "Player Begin resizeImg ");
        idleFrogWidth = (int) (idleFrogWidth/coefficientScale);
        Log.i(TAG, "resizeImg: idleFrogWidth = "+idleFrogWidth);
        idleFrogHeight = (int) (idleFrogHeight/coefficientScale);
        flyFrogWidth = (int) (flyFrogWidth/coefficientScale);
        flyFrogHeight = (int) (flyFrogHeight/coefficientScale);
        bulkWidth = (int) (bulkWidth/coefficientScale);
        bulkHeight = (int) (bulkHeight/coefficientScale);
        Log.i(TAG, "Player Finish resizeImg ");
    }
    public void setState(int curentState) {
        Log.i(TAG, "Player Begin setState ");
        if(curentState == STATE_IDLE){
            curentImg = idleFrogImg;
            rect.set(x, y, x + idleFrogWidth, y + idleFrogHeight);
//            Log.i(TAG, "setState: idleFrogWidth = "+idleFrogWidth);
//            xHeading = rect.centerX();//x + idleFrogWidth/2;
//            yHeading = rect.centerY();//y + idleFrogHeight/2;
//            curentWidth = idleFrogWidth;
//            curentHeight = idleFrogHeight;
        }else if (curentState == STATE_MOVE){
            curentImg = flyFrogImg;

        }else if (curentState == STATE_BULK){
            curentImg = bulkImg;

        }else if (curentState == STATE_LOSE){
            //game over

        }else if (curentState == STATE_WIN){
            //win
        }
        Log.i(TAG, "Player Finish setState ");
    }
    public void updatePhysics() {
        Log.i(TAG, "Player updatePhysics");
    }
    public int getHeading() {
        Log.i(TAG, "Player getHeading");
        return heading;
    }

}

package com.example.newtechnologies;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.Map;

/**
 * Created by асер on 26.01.2018.
 */

public class Player extends PlayObject implements Constants {

    private int heading;
//    private int idleFrogWidth, idleFrogHeight, flyFrogWidth, flyFrogHeight, bulkWidth, bulkHeight;

//    private Drawable idleFrogImg;
//    private Drawable flyFrogImg;
//    private Drawable bulkImg;



    Player(Map<String, Drawable> hashMapImg, Map<String, Integer> hashMapSize, int x, int y) {
        super(hashMapImg, hashMapSize, x, y);
//        Log.i(TAG, "Player Begin Constr ");
//        initImg(context);
//        defineSizeImg();
        heading = 10;
        setState(STATE_IDLE);
//        Log.i(TAG, "Player Finish Constr ");
    }

//    private void initImg(Context context) {
//        Log.i(TAG, "Player Begin initImg ");
//
//        Log.i(TAG, "Player Finish initImg ");
//    }

    public void setState(int curentState) {
//        Log.i(TAG, "Player Begin setState ");
        if(curentState == STATE_IDLE){
            curentImg = hashMapImg.get("idleFrogImg");
            rect.set(x, y, x + hashMapSize.get("idleFrogWidth"), y + hashMapSize.get("idleFrogHeight"));
//            Log.i(TAG, "setState: idleFrogWidth = "+idleFrogWidth);
//            xHeading = rect.centerX();//x + idleFrogWidth/2;
//            yHeading = rect.centerY();//y + idleFrogHeight/2;
//            curentWidth = idleFrogWidth;
//            curentHeight = idleFrogHeight;
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
//        Log.i(TAG, "Player updatePhysics");
    }
    public int getHeading() {
//        Log.i(TAG, "Player getHeading");
        return heading;
    }

}

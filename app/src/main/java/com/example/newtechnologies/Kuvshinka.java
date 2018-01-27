package com.example.newtechnologies;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Kuvshinka extends PlayObject{

    private Drawable kuvshinkaImg;
//    private int kuvshinkaWidth, kuvshinkaHeight;
//    Rect rect;

    Kuvshinka(Context context, int x, int y){
        super(context, x, y);
        Log.i(TAG, "Kuvshinka Begin Constr");
        kuvshinkaImg = context.getResources().getDrawable(R.drawable.kuvshinka);
        ObjImg = kuvshinkaImg;
        defineSizeImg();
        curentImg = ObjImg;
        Log.i(TAG, "Kuvshinka Finish Constr");
    }

//    private void defineSizeImg() {
//        kuvshinkaWidth = kuvshinkaImg.getIntrinsicWidth();
//        kuvshinkaHeight = kuvshinkaImg.getIntrinsicHeight();
//    }
//    public void resizeImg(float coefficientScale) {
//        kuvshinkaWidth = (int) (kuvshinkaWidth/coefficientScale);
//        kuvshinkaHeight = (int) (kuvshinkaHeight/coefficientScale);
//        rect = new Rect(x, y, x+kuvshinkaWidth, y+kuvshinkaHeight);
//    }
//    public Drawable getCurentImg() {
//        return curentImg;
//    }
//
//    public Rect getRect() {
//        return rect;
//    }
//    public int getX() {
//        return x;
//    }
//    public int getY() {
//        return y;
//    }
//    public int getCurentWidth() {
//        return x+kuvshinkaWidth;
//    }
//
//    public int getCurentHeight() {
//        return y+kuvshinkaHeight;
//    }


}
//структурное, потом как модули работают, скриншоты и заключение

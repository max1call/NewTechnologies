package com.example.newtechnologies;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by асер on 26.01.2018.
 */

public class Kuvshinka {

    private Drawable curentImg, kuvshinkaImg;
    private int x, y, kuvshinkaWidth, kuvshinkaHeight;

    Kuvshinka(Context context, int x, int y){
        kuvshinkaImg = context.getResources().getDrawable(R.drawable.kuvshinka);
        curentImg = kuvshinkaImg;
        this.x=x;
        this.y=y;
    }
    private void defineSizeImg() {
        kuvshinkaWidth = kuvshinkaImg.getIntrinsicWidth();
        kuvshinkaHeight = kuvshinkaImg.getIntrinsicHeight();
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
    public int getCurentWidth() {
        return x+kuvshinkaWidth;
    }

    public int getCurentHeight() {
        return y+kuvshinkaHeight;
    }

    public void resizeImg(int coefficientScale) {
    }
}
//структурное, потом как модули работают, скриншоты и заключение

package com.example.newtechnologies;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by асер on 27.01.2018.
 */

public class Hippo {

    private Drawable hippoImg;
    private int x, y, hippoWidth, hippoHeight;

    Hippo(Context context, int x, int y){
        hippoImg = context.getResources().getDrawable(R.drawable.begemot);
    }
    private void defineSizeImg() {
        hippoWidth = hippoImg.getIntrinsicWidth();
        hippoHeight = hippoImg.getIntrinsicHeight();
    }
    private void updatePhysics() {

    }

    public void resizeImg(int coefficientScale) {
    }
}

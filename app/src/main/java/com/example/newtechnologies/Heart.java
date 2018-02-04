package com.example.newtechnologies;

import android.graphics.drawable.Drawable;

import java.util.Map;

/**
 * Created by асер on 04.02.2018.
 */

public class Heart extends PlayObject {
    Heart(Map<String, Drawable> hashMapImg, Map<String, Integer> hashMapSize, int x, int y) {
        super(hashMapImg, hashMapSize, x, y);
        curentImg = hashMapImg.get("heartImg");
        rect.set(x, y, x + hashMapSize.get("heartWidth"), y + hashMapSize.get("heartHeight"));
    }
}

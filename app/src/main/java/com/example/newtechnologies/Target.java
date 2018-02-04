package com.example.newtechnologies;

import android.graphics.drawable.Drawable;

import java.util.Map;

public class Target extends PlayObject {
    Target(Map<String, Drawable> hashMapImg, Map<String, Integer> hashMapSize, int x, int y) {
        super(hashMapImg, hashMapSize, x, y);
        curentImg = hashMapImg.get("targetImg");
        rect.set(x, y, x + hashMapSize.get("targetWidth"), y + hashMapSize.get("targetHeight"));
    }
}

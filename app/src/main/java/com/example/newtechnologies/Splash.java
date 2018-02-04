package com.example.newtechnologies;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.newtechnologies.PlayObject;
import com.example.newtechnologies.Constants;

import java.util.Map;


public class Splash extends PlayObject implements Constants {

    Player player;

    Splash(Map<String, Drawable> hashMapImg, Map<String, Integer> hashMapSize, int x, int y, Player player) {
        super(hashMapImg, hashMapSize, x, y);
        this.player=player;
        curentImg = hashMapImg.get("bulkImg");
        rect.set(0, 0, 0 + hashMapSize.get("bulkWidth"), 0 + hashMapSize.get("bulkWidth"));
    }

    public void setLocatoin() {
        rect.offset(player.getRect().centerX()-rect.centerX(), player.getRect().centerY()-rect.centerY());
    }
}

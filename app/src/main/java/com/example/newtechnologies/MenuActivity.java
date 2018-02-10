package com.example.newtechnologies;


import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;

public class MenuActivity extends Activity implements SoundPool.OnLoadCompleteListener {

//    ToggleButton btn_new;
    final int MAX_STREAM = 5;
    private SoundPool sp;
//    private AssetManager assetManager;
    int idBgMusic, idJumpSound, idWinSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        sp = new SoundPool(MAX_STREAM, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);

        idJumpSound = sp.load(this, R.raw.a, 1);
        idWinSound = sp.load(this, R.raw.egor, 1);
        idBgMusic = loadSound("muz.wav");
        Log.d("war", "idBgMusic= "+idBgMusic);

//        btn_new = (ToggleButton) findViewById(R.id.btn_new);
    }
    public void newGame(View v){
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private int loadSound(String fileName) {
        int idLoad = -1;
        try {
            idLoad = sp.load(getAssets().openFd(fileName), 1);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return idLoad;
    }
    public void onClick(View view){
        sp.play(idBgMusic, 1, 1, 0, 0, 1);
    }
    public void onClick2(View view){
        sp.play(idJumpSound, 1, 1, 0, 0, 1);
    }
    public void onClick3(View view){
        sp.play(idWinSound, 1, 1, 1, 2, 1);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        Log.d("war", "onLoadComplete, sampleId = " + sampleId + ", status = " + status);
    }
}

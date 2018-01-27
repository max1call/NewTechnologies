package com.example.newtechnologies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.widget.TextView;
import android.os.Handler;

public class MyThread extends Thread {

    String TAG="Target";
    boolean running;
    private SurfaceHolder surfaceHolder;
    private Context context;
    private Bitmap backgroundImg;
    private Drawable idle_frog_img, fly_frog_img, bulk_img, kuvshinka_img;
    private int mCanvasWidth, mCanvasHeight, bgWidth, bgHeight;
    private int coefficientScale;
    private int lengthJump;
    Player player;
    Kuvshinka kuvshinka;
    Hippo hippo;

    Handler handler;
    TextView tv;

    public MyThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
        this.surfaceHolder = surfaceHolder;
        this.handler = handler;
        this.context = context;
        initImg();
        defineSizeImg();
        makeStage();
        Log.i(TAG, "MyThread constr!");
    }

    private void makeStage() {
        int x=100;
        int y=100;
        player = new Player(context);
        kuvshinka = new Kuvshinka(context, x, y);
        hippo = new Hippo(context, x, y);
    }

    private void initImg() {
        backgroundImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.background2);

    }

    private void defineSizeImg() {
        bgWidth=backgroundImg.getWidth();
        bgHeight= backgroundImg.getHeight();
        Log.i(TAG, "defineSizeImg bgWidth= "+bgWidth+"; bgHeight= "+bgHeight);
    }

    public void setSurfaceSize(int width, int height) {
        synchronized (surfaceHolder) {
            mCanvasWidth = width;
            mCanvasHeight = height;
//            Log.i(TAG, "setSurfaceSize 11111");
            int coefficientX = bgWidth/width;
            int coefficientY = bgHeight/height;
            if(coefficientX > coefficientY){
                coefficientScale = coefficientY;
            } else coefficientScale = coefficientX;
            backgroundImg = Bitmap.createScaledBitmap(
                    backgroundImg, width, height, true);
            kuvshinka.resizeImg(coefficientScale);
            player.resizeImg(coefficientScale);
            hippo.resizeImg(coefficientScale);
        }
    }

    private void resizeImg() {

    }


    public void run(){
        Canvas canvas;
        while (running){
            canvas=null;
            try{
                canvas=surfaceHolder.lockCanvas();
                if (canvas!=null){
                    updatePhysics();
                    doDraw(canvas);
                }
            }finally {
                if(canvas!=null) surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
//        tv.setText("This is Thread!");
//        for(int i=0; i<10; i++) {
//            try{
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Log.i("Target", "my Thread start!"+i);
//            h.sendEmptyMessage(i);
//           Log.i("Target", "Handler is here!");
//        }
    }

    private void updatePhysics() {
        player.updatePhysics();
    }

    private void doDraw(Canvas canvas) {
        canvas.drawBitmap(backgroundImg, 0, 0, null);
        kuvshinka.getCurentImg().setBounds(kuvshinka.getX(), kuvshinka.getY(), kuvshinka.getCurentWidth(), kuvshinka.getCurentHeight());
        kuvshinka.getCurentImg().draw(canvas);

        canvas.save();
        canvas.rotate((float) player.getHeading(), player.getxHeading(), player.getyHeading());
        player.getCurentImg().setBounds(player.getX(), player.getY(), player.getCurentWidth(), player.getCurentHeight());
        player.getCurentImg().draw(canvas);
        canvas.restore();
    }

    public void setRunning(boolean b) {
        running = b;
    }

    public boolean doKeyUp(int keyCode, KeyEvent msg) {
        return true;
    }

    public void pause() {
    }

    public boolean doKeyDown(int keyCode, KeyEvent msg) {
        return true;
    }
}

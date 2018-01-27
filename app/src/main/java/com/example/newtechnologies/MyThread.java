package com.example.newtechnologies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.widget.TextView;
import android.os.Handler;

import java.util.ArrayList;

public class MyThread extends Thread implements Constants {

    String TAG="Target";
    boolean running;
    private SurfaceHolder surfaceHolder;
    private Context context;
    private Bitmap backgroundImg;
    private Drawable idle_frog_img, fly_frog_img, bulk_img, kuvshinka_img;
    private int mCanvasWidth, mCanvasHeight, bgWidth, bgHeight;
    private float coefficientScale;
    private int lengthJump;
    protected int curentState;
    Player player;
    Kuvshinka kuvshinka;
    Hippo hippo;

    private ArrayList<Kuvshinka> arrayKuvshinka;
    Handler handler;
    TextView tv;
    private boolean canResize;

    public MyThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
        Log.i(TAG, "Begin Constructor MyThread");
        this.surfaceHolder = surfaceHolder;
        this.handler = handler;
        this.context = context;
        canResize = true;
        initImg();
        defineSizeImg();

        Log.i(TAG, "Finish Constructor MyThread");
    }

    private void makeStage1() {
        Log.i(TAG, "Begin makeStage1");
        arrayKuvshinka =new ArrayList<Kuvshinka>();
        int xPlayer=100;
        int yPlayer=100;
        int xHippo = 300;
        int yHippo = 200;
        player = new Player(context, xPlayer, yPlayer);

        int xKuvshinka1 = (int) (lengthJump/4.3);
        int yKuvshinka1 = (int) (lengthJump/0.533);
        newKuvshinka(xKuvshinka1, yKuvshinka1);

        int xKuvshinka2 = (int) (lengthJump/1.6);
        int yKuvshinka2 = calculateY(xKuvshinka1, yKuvshinka1, xKuvshinka2);
        newKuvshinka(xKuvshinka2, yKuvshinka2);

        xKuvshinka1 = (int) (lengthJump/0.711);
        yKuvshinka1 = calculateY(xKuvshinka2, yKuvshinka2, xKuvshinka1);
        newKuvshinka(xKuvshinka1, yKuvshinka1);

        xKuvshinka2 = (int) (lengthJump/0.337);
        yKuvshinka2 = calculateY(xKuvshinka1, yKuvshinka1, xKuvshinka2);
        newKuvshinka(xKuvshinka2, yKuvshinka2);

        hippo = new Hippo(context, xHippo, yHippo);
        Log.i(TAG, "Finish makeStage1");
    }

    private int calculateY(int x1, int y1, int x2) {
        return (int) (Math.sqrt(lengthJump*lengthJump-(x2-x1)*(x2-x1))+y1);
    }

    private void newKuvshinka(int x, int y) {
        kuvshinka = new Kuvshinka(context, x, y);
        arrayKuvshinka.add(kuvshinka);
    }


    public void setState(int state) {
        CharSequence str;
        curentState = state;
        Log.i(TAG, "Begin setState");
        if (curentState == STATE_RUNING) {
            player.setState(STATE_IDLE);
            hippo.setState(STATE_MOVE);
        }else if (curentState == STATE_PAUSE) {
            str = context.getResources().getText(R.string.mode_pause);
        }else if (curentState == STATE_LOSE) {
            //game over

        } else if (curentState == STATE_WIN) {
            //win
        }
        Log.i(TAG, "Finish setState");
    }

    private void initImg() {
        backgroundImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.background2);

    }

    private void defineSizeImg() {
        bgWidth=backgroundImg.getWidth();
        bgHeight= backgroundImg.getHeight();
//        Log.i(TAG, "defineSizeImg bgWidth= "+bgWidth+"; bgHeight= "+bgHeight);
    }

    public void setSurfaceSize(int width, int height) {
        Log.i(TAG, "Begin setSurfaceSize");
        synchronized (surfaceHolder) {
            mCanvasWidth = width;
            mCanvasHeight = height;
            lengthJump = (int) (width/2.5);
            float coefficientX = (float) bgWidth/width;
            float coefficientY = (float) bgHeight/height;
            if(coefficientX > coefficientY){
                coefficientScale = coefficientY;
            } else coefficientScale = coefficientX;
            backgroundImg = Bitmap.createScaledBitmap(
                    backgroundImg, width, height, true);
            resizeImg();
            makeStage1();
            setState(STATE_RUNING);
            Log.i(TAG, "Finish setSurfaceSize");
        }
    }
    public void resizeImg(){
        if (canResize) {
            kuvshinka.resizeImg(coefficientScale);
            player.resizeImg(coefficientScale);
            hippo.resizeImg(coefficientScale);
            canResize = false;
        }
    }
    public void pause() {
        synchronized (surfaceHolder) {
            if (curentState == STATE_RUNING) setState(STATE_PAUSE);
        }
    }

    public void run(){
        Canvas canvas;
        while (running){
            canvas=null;
            try{
                canvas=surfaceHolder.lockCanvas();
                if (canvas!=null && curentState == STATE_RUNING){
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

//        if(!arrayKuvshinka.isEmpty()) {
        for(Kuvshinka kuvshinka : arrayKuvshinka){
            kuvshinka.getCurentImg().setBounds(kuvshinka.getRect());
            kuvshinka.getCurentImg().draw(canvas);
        }

        hippo.getCurentImg().setBounds(hippo.getRect());
        hippo.getCurentImg().draw(canvas);
//        Log.i(TAG, "hippo.getRect().width() = "+hippo.getRect().width()+"; hippo.getRect().height() = "+hippo.getRect().height());

        canvas.save();
        Rect rect = new Rect(player.getRect());
        canvas.rotate((float) player.getHeading(), rect.centerX(), rect.centerY());
        player.getCurentImg().setBounds(rect);
        player.getCurentImg().draw(canvas);
        canvas.restore();
    }

    public void setRunning(boolean b) {
        running = b;
    }

    public boolean doKeyUp(int keyCode, KeyEvent msg) {
        return true;
    }

    public boolean doKeyDown(int keyCode, KeyEvent msg) {
        return true;
    }
}

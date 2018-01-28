package com.example.newtechnologies;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import android.widget.TextView;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyThread extends Thread implements Constants {

    String TAG="Target";
    boolean running;
    private SurfaceHolder surfaceHolder;
    private Context context;
    private Bitmap backgroundImg;
    protected Drawable kuvshinkaImg, idleFrogImg, flyFrogImg, bulkImg, hippoImg;
    private int mCanvasWidth, mCanvasHeight, bgWidth, bgHeight;
    private int idleFrogWidth, idleFrogHeight, flyFrogWidth, flyFrogHeight, bulkWidth,
            bulkHeight, kuvshinkaWidth, kuvshinkaHeight, hippoWidth, hippoHeight;
    private float coefficientScale;
    private int lengthJump;
    protected int curentState;
    private Rect rectDisplay;
    Player player;
    Kuvshinka kuvshinka;
    Hippo hippo;
    Bundle bundleImg;
    Map<String, Drawable> hashMapImg;
    Map<String, Integer> hashMapSize;

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
        calcullateScale();
        resizeImg();
        putToHash();
        makeStage1();
        setState(STATE_RUNING);
        Log.i(TAG, "Finish Constructor MyThread");
    }

    private void initImg() {
        backgroundImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.background2);
        kuvshinkaImg = context.getResources().getDrawable(R.drawable.kuvshinka);
        idleFrogImg = context.getResources().getDrawable(R.drawable.idle_frog);
        flyFrogImg = context.getResources().getDrawable(R.drawable.fly_frog);
        bulkImg = context.getResources().getDrawable(R.drawable.bulk);
        hippoImg = context.getResources().getDrawable(R.drawable.begemot);
    }
    private void defineSizeImg() {
        bgWidth=backgroundImg.getWidth();
        bgHeight= backgroundImg.getHeight();
        idleFrogWidth = idleFrogImg.getIntrinsicWidth();
        idleFrogHeight = idleFrogImg.getIntrinsicHeight();
        flyFrogWidth = flyFrogImg.getIntrinsicWidth();
        flyFrogHeight = flyFrogImg.getIntrinsicHeight();
        bulkWidth = bulkImg.getIntrinsicWidth();
        bulkHeight = bulkImg.getIntrinsicHeight();
        kuvshinkaWidth = kuvshinkaImg.getIntrinsicWidth();//
        kuvshinkaHeight = kuvshinkaImg.getIntrinsicHeight();
        hippoWidth = hippoImg.getIntrinsicWidth();
        hippoHeight = hippoImg.getIntrinsicHeight();
//        Log.i(TAG, "defineSizeImg bgWidth= "+bgWidth+"; bgHeight= "+bgHeight);
    }
    private void calcullateScale() {
        rectDisplay = new Rect();
        WindowManager w = ((Activity) context).getWindowManager();
        Display d = w.getDefaultDisplay();
        d.getRectSize(rectDisplay);
        mCanvasWidth = rectDisplay.width();
        mCanvasHeight = rectDisplay.height();
//        Log.w(TAG, "!!!!!!!!!!!!! mCanvasWidth= "+mCanvasWidth+"; mCanvasHeight= "+mCanvasHeight);
        lengthJump = (int) (mCanvasWidth/5);
        float coefficientX = (float) bgWidth/mCanvasWidth;
        float coefficientY = (float) bgHeight/mCanvasHeight;
        if(coefficientX > coefficientY){
            coefficientScale = coefficientY;
        } else coefficientScale = coefficientX;
    }
    public void resizeImg(){
        if (canResize) {
            idleFrogWidth = (int) (idleFrogWidth/coefficientScale);
            idleFrogHeight = (int) (idleFrogHeight/coefficientScale);
            flyFrogWidth = (int) (flyFrogWidth/coefficientScale);
            flyFrogHeight = (int) (flyFrogHeight/coefficientScale);
            bulkWidth = (int) (bulkWidth/coefficientScale);
            bulkHeight = (int) (bulkHeight/coefficientScale);
            kuvshinkaWidth = (int) (kuvshinkaWidth/coefficientScale);
            kuvshinkaHeight = (int) (kuvshinkaHeight/coefficientScale);
            hippoWidth = (int) (hippoWidth/coefficientScale);
            hippoHeight = (int) (hippoHeight/coefficientScale);
            canResize = false;

        }
    }
    private void putToHash() {
        hashMapImg = new HashMap<String, Drawable>();
        hashMapImg.put("kuvshinkaImg", kuvshinkaImg);
        hashMapImg.put("idleFrogImg", idleFrogImg);
        hashMapImg.put("flyFrogImg", flyFrogImg);
        hashMapImg.put("bulkImg", bulkImg);
        hashMapImg.put("hippoImg", hippoImg);

        hashMapSize = new HashMap<String, Integer>();
        hashMapSize.put("idleFrogWidth",idleFrogWidth);
        hashMapSize.put("idleFrogHeight",idleFrogHeight);
        hashMapSize.put("flyFrogWidth",flyFrogWidth);
        hashMapSize.put("flyFrogHeight",flyFrogHeight);
        hashMapSize.put("bulkWidth",bulkWidth);
        hashMapSize.put("bulkHeight",bulkHeight);
        hashMapSize.put("kuvshinkaWidth",kuvshinkaWidth);
        hashMapSize.put("kuvshinkaHeight",kuvshinkaHeight);
        hashMapSize.put("hippoWidth",hippoWidth);
        hashMapSize.put("hippoHeight",hippoHeight);
    }

    private void makeStage1() {
        Log.i(TAG, "Begin makeStage1");
        arrayKuvshinka =new ArrayList<Kuvshinka>();
        int xPlayer=100;
        int yPlayer=100;
        int xHippo = 300;
        int yHippo = 200;
        player = new Player(hashMapImg, hashMapSize , xPlayer, yPlayer);

        int xKuvshinka1 = (int) (lengthJump/4.3);
        int yKuvshinka1 = (int) (lengthJump/0.533);
        newKuvshinka(xKuvshinka1, yKuvshinka1);
        Log.w(TAG, "Kuvshinka1 ready");

        int xKuvshinka2 = (int) (lengthJump/1.6);
        int yKuvshinka2 = calculateY(xKuvshinka1, yKuvshinka1, xKuvshinka2);
        newKuvshinka(xKuvshinka2, yKuvshinka2);

        Log.w(TAG, "Kuvshinka2 ready");


        xKuvshinka1 = (int) (lengthJump/0.711);
        yKuvshinka1 = calculateY(xKuvshinka2, yKuvshinka2, xKuvshinka1);
        newKuvshinka(xKuvshinka1, yKuvshinka1);
        Log.w(TAG, "Kuvshinka3 ready");

        xKuvshinka2 = (int) (lengthJump/0.337);
        yKuvshinka2 = calculateY(xKuvshinka1, yKuvshinka1, xKuvshinka2);
        newKuvshinka(xKuvshinka2, yKuvshinka2);
        Log.w(TAG, "Kuvshinka4 ready");

        hippo = new Hippo(hashMapImg, hashMapSize , xHippo, yHippo);
        Log.i(TAG, "Finish makeStage1");
    }

    private int calculateY(int x1, int y1, int x2) {
        return (int) (Math.sqrt(lengthJump*lengthJump-(x2-x1)*(x2-x1))+y1);
    }
    int i=0;
    private void newKuvshinka(int x, int y) {
        kuvshinka = new Kuvshinka(hashMapImg, hashMapSize , x, y);
        arrayKuvshinka.add(kuvshinka);

        i++;
        Log.w(TAG, "Kuvshinka "+i+" add to array");
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

    public void setSurfaceSize(int width, int height) {
        Log.i(TAG, "Begin setSurfaceSize");
        synchronized (surfaceHolder) {
            backgroundImg = Bitmap.createScaledBitmap(
                    backgroundImg, width, height, true);
            Log.i(TAG, "Finish setSurfaceSize");
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
        int i=0;
        for(Kuvshinka k : arrayKuvshinka){
            k.getCurentImg().setBounds(k.getRect());
            k.getCurentImg().draw(canvas);
            i++;
            Log.w(TAG, "Kuvshinka "+i+" draw; getRect().width= "+k.getRect().width());
        }

        //TODO background into obj

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

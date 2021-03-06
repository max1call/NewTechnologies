package com.example.newtechnologies;//Сохранение текущего сост. возврат на предыдущее активити без создания нового. 3 стажа.

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.Log;
import android.view.Display;
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
    protected Drawable kuvshinkaImg, idleFrogImg, flyFrogImg, bulkImg, hippoImg, kamishImg, heartImg, game_overImg, winImg, targetImg;
    private int mCanvasWidth, mCanvasHeight, bgWidth, bgHeight, heartWidth, heartHeight;
    private int idleFrogWidth, idleFrogHeight, flyFrogWidth, flyFrogHeight, bulkWidth,
            bulkHeight, kuvshinkaWidth, kuvshinkaHeight, hippoWidth, hippoHeight, kamishWidth, kamishHeight, targetWidth, targetHeight;
    private float coefficientScale;
    private int lengthJump;
    protected int curentState;
    private Rect rectDisplay;
    private ArrayList<Kuvshinka> arrayKuvshinka;
    private ArrayList<Heart> arrayHeart;
    Player player;
    Kuvshinka kuvshinka, lastKuvshinka;
    Hippo hippo;
    Splash splash;
    Heart heart;
    Map<String, Drawable> hashMapImg;
    Map<String, Integer> hashMapSize;
    Handler handler;
    TextView tv;
    private boolean canResize;
    InputOutput inputOutput;
    MyView myView;
    Paint paint;
    Message msg;
    public TextView tvt1;
    MainActivity m;
    private int countLive = 3;
    private Kamish kamish;
    private Target target;
    private GameOver game_over;
    private Win win;
    private boolean canDrawGameOver = false;
    long now;
    int timeUnderWater;
    Rect rectFrog;

    public MyThread(MyView myView, SurfaceHolder surfaceHolder, Context context, Handler handler) {
        Log.i(TAG, "Begin Constructor MyThread");
        this.myView = myView;
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
        running = false;
        setState(STATE_RUNING);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        Log.i(TAG, "Finish Constructor MyThread");
    }
    private void initImg() {
        backgroundImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.background3);
        kamishImg = context.getResources().getDrawable(R.drawable.kamish);
        game_overImg = context.getResources().getDrawable(R.drawable.game_over);
        winImg = context.getResources().getDrawable(R.drawable.win);
        kuvshinkaImg = context.getResources().getDrawable(R.drawable.kuvshinka);
        idleFrogImg = context.getResources().getDrawable(R.drawable.idle_frog);
        flyFrogImg = context.getResources().getDrawable(R.drawable.fly_frog);
        bulkImg = context.getResources().getDrawable(R.drawable.bulk);
        hippoImg = context.getResources().getDrawable(R.drawable.begemot);
        heartImg = context.getResources().getDrawable(R.drawable.heart);
        targetImg = context.getResources().getDrawable(R.drawable.target_frog);
    }
    private void defineSizeImg() {
        bgWidth=backgroundImg.getWidth();
        bgHeight= backgroundImg.getHeight();
        kamishWidth = kamishImg.getIntrinsicWidth();
        kamishHeight = kamishImg.getIntrinsicHeight();
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
        heartWidth = heartImg.getIntrinsicWidth();
        heartHeight = heartImg.getIntrinsicHeight();
        targetWidth = targetImg.getIntrinsicWidth();
        targetHeight = targetImg.getIntrinsicHeight();
    }
    private void calcullateScale() {
        rectDisplay = new Rect();
        WindowManager w = ((Activity) context).getWindowManager();
        Display d = w.getDefaultDisplay();
        d.getRectSize(rectDisplay);
        mCanvasWidth = rectDisplay.width();
        mCanvasHeight = rectDisplay.height();
//        Log.w(TAG, "!!!!!!!!!!!!! mCanvasWidth= "+mCanvasWidth+"; mCanvasHeight= "+mCanvasHeight);
        lengthJump = (int) (mCanvasHeight/2.5);
        float coefficientX = (float) bgWidth/mCanvasWidth;
        float coefficientY = (float) bgHeight/mCanvasHeight;
        if(coefficientX > coefficientY){
            coefficientScale = coefficientY;
        } else coefficientScale = coefficientX;
    }
    public void resizeImg(){
        if (canResize) {
            kamishWidth = (int) (kamishWidth/coefficientScale);
            kamishHeight = (int) (kamishHeight/coefficientScale);
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

            heartWidth = (int) (heartWidth/coefficientScale);
            heartHeight = (int) (heartHeight/coefficientScale);
            targetWidth = (int) (targetWidth/coefficientScale);
            targetHeight = (int) (targetHeight/coefficientScale);
            canResize = false;
        }
    }
    private void putToHash() {
        hashMapImg = new HashMap<String, Drawable>();
        hashMapImg.put("game_overImg", game_overImg);
        hashMapImg.put("winImg", winImg);
        hashMapImg.put("kamishImg", kamishImg);
        hashMapImg.put("kuvshinkaImg", kuvshinkaImg);
        hashMapImg.put("idleFrogImg", idleFrogImg);
        hashMapImg.put("flyFrogImg", flyFrogImg);
        hashMapImg.put("bulkImg", bulkImg);
        hashMapImg.put("hippoImg", hippoImg);
        hashMapImg.put("heartImg", heartImg);
        hashMapImg.put("targetImg", targetImg);

        hashMapSize = new HashMap<String, Integer>();
        hashMapSize.put("kamishWidth",kamishWidth);
        hashMapSize.put("kamishHeight",kamishHeight);
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
        hashMapSize.put("lengthJump",lengthJump);
        hashMapSize.put("mCanvasWidth",mCanvasWidth);
        hashMapSize.put("mCanvasHeight",mCanvasHeight);
        hashMapSize.put("heartWidth",heartWidth);
        hashMapSize.put("heartHeight",heartHeight);
        hashMapSize.put("targetWidth",targetWidth);
        hashMapSize.put("targetHeight",targetHeight);
    }
    private void makeStage1() {
        Log.i(TAG, "Begin makeStage1");
        arrayKuvshinka =new ArrayList<Kuvshinka>();
        arrayHeart = new ArrayList<Heart>();
        int xPlayer=100;
        int yPlayer=100;
        int speedFly= (int)lengthJump/5;
        int xHippo = 300;
        int yHippo = 200;
        timeUnderWater=2000;
        int kHeading;
        double radians;

        int xKuvshinka = (int) (0.3*kuvshinkaWidth);
        int yKuvshinka = (int) (mCanvasHeight - 3*kuvshinkaHeight);
        newKuvshinka(xKuvshinka, yKuvshinka);
        xPlayer = xKuvshinka+(kuvshinkaWidth-idleFrogWidth)/2;
        yPlayer = yKuvshinka+(kuvshinkaHeight-idleFrogHeight)/2;

        kHeading = 35;
        radians = 2 * Math.PI * kHeading / 360;//double radians;
        xKuvshinka += (int) (lengthJump*Math.sin(radians));
        yKuvshinka -= (int) (lengthJump*Math.cos(radians));
        newKuvshinka(xKuvshinka, yKuvshinka);

        kHeading = 115;
        radians = 2 * Math.PI * kHeading / 360;
        xKuvshinka += (int) (lengthJump*Math.sin(radians));
        yKuvshinka -= (int) (lengthJump*Math.cos(radians));
        newKuvshinka(xKuvshinka, yKuvshinka);

        kHeading = 90;
        radians = 2 * Math.PI * kHeading / 360;
        xKuvshinka += (int) (lengthJump*Math.sin(radians));
        yKuvshinka -= (int) (lengthJump*Math.cos(radians));
        xHippo = xKuvshinka;
        yHippo = yKuvshinka;

        kHeading = 35;
        radians = 2 * Math.PI * kHeading / 360;
        xKuvshinka += (int) (lengthJump*Math.sin(radians));
        yKuvshinka -= (int) (lengthJump*Math.cos(radians));
        target = new Target(hashMapImg, hashMapSize , xKuvshinka, yKuvshinka);

        int k = lengthJump/20;
        newHeart(k, k);
        newHeart(2*k+heartWidth, k);
        newHeart(3*k+2*heartWidth, k);

        hippo = new Hippo(hashMapImg, hashMapSize , xHippo, yHippo);
        player = new Player(hashMapImg, hashMapSize , xPlayer, yPlayer, speedFly, this, hippo);
        lastKuvshinka = arrayKuvshinka.get(0);
        splash = new Splash(hashMapImg, hashMapSize , 0, 0, player);
        kamish = new Kamish(hashMapImg, hashMapSize , 0, mCanvasHeight-kamishHeight);
        inputOutput = new InputOutput(myView, player, this);
        game_over = new GameOver(hashMapImg, hashMapSize , mCanvasWidth/2, mCanvasHeight/2);
        win = new Win(hashMapImg, hashMapSize , mCanvasWidth/2, mCanvasHeight/2);
//        Log.i(TAG, "Finish makeStage1");
    }

    private void newKuvshinka(int x, int y) {
        kuvshinka = new Kuvshinka(hashMapImg, hashMapSize , x, y);
        arrayKuvshinka.add(kuvshinka);
    }

    private void newHeart(int x, int y) {
        heart = new Heart(hashMapImg, hashMapSize , x, y);
        arrayHeart.add(heart);
    }

    public void setState(int state) {/////////////****************************////////////////////////////
        CharSequence str;
        curentState = state;
//        Log.i(TAG, "Begin setState");

        if (curentState == STATE_RUNING) {
//            player.setState(STATE_ONKUVSHINKA);
//            hippo.setState(STATE_MOVE);
//            paint.setColor(Color.BLACK);
            running = true;

        } else if (curentState == STATE_PAUSE) {
            str = context.getResources().getText(R.string.mode_pause);
            running = false;

        } else if (curentState == STATE_BULK) {
            now = System.currentTimeMillis();
            splash.setLocatoin();
            countLive--;
            if (countLive>0) {
                arrayHeart.remove(countLive);
                player.setState(STATE_BULK);
            } else if (countLive == 0){
                arrayHeart.remove(countLive);
                setState(STATE_LOSE);
            }

        } else if (curentState == STATE_LOSE) {
            player.setState(STATE_LOSE);
            hippo.setState(STATE_PAUSE);
            gameOver();

        } else if (curentState == STATE_WIN) {
            player.setState(STATE_WIN);
            hippo.setState(STATE_PAUSE);
            win();
        }
    }
    protected void checkLocation(Rect rectFrog) {
        boolean contains = false;
        this.rectFrog = rectFrog;
        for(Kuvshinka k : arrayKuvshinka) {
            if (k.getRect().contains(rectFrog.centerX(), rectFrog.centerY())) {
                lastKuvshinka = k;
//                rectFrog.offset(k.getRect().centerX()-rectFrog.centerX(), k.getRect().centerY()-rectFrog.centerY());
//                player.setPositionFrog(rectFrog);
                player.setState(STATE_ONKUVSHINKA);
                contains = true;
            }
        }
        if (hippo.getRect().contains(rectFrog.centerX(), rectFrog.centerY())){
//            rectFrog.offset(hippo.getRect().centerX()-rectFrog.centerX(), hippo.getRect().centerY()-rectFrog.centerY());
//            player.setPositionFrog(rectFrog);
            player.setState(STATE_ONHIPPO);
            contains = true;
        }
        else if (target.getRect().contains(rectFrog.centerX(), rectFrog.centerY())){
            setState(STATE_WIN);
            contains = true;
        }
        if (!contains) setState(STATE_BULK);
    }

    private void gameOver() {
        now = System.currentTimeMillis();
        game_over.setCanUpdate(true);
//        canDrawGameOver = true;
    }

    private void win() {
//        now = System.currentTimeMillis();
        win.setCanUpdate(true);
//        canDrawGameOver = true;
    }

    public void setSurfaceSize(int width, int height) {
        synchronized (surfaceHolder) {
            backgroundImg = Bitmap.createScaledBitmap(backgroundImg, width, height, true);
//            kamishImg = Bitmap.createScaledBitmap(kamishImg, width, height, true);
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
                if (canvas!=null){
                    try{
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updatePhysics();
                    doDraw(canvas);
                }
            }finally {
                if(canvas!=null) surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
    private void updatePhysics() {
        player.updatePhysics();
        hippo.updatePhysics();
        game_over.updatePhysics();
        win.updatePhysics();

        if (curentState == STATE_BULK) {
            Rect rect = new Rect(player.getRect());
            if(System.currentTimeMillis()>now + timeUnderWater) {
                rect.offset(lastKuvshinka.getRect().centerX() - rect.centerX(), lastKuvshinka.getRect().centerY() - rect.centerY());
                player.setPositionFrog(rect);
                player.setState(STATE_ONKUVSHINKA);
            }
        }
    }

    private void doDraw(Canvas canvas) {

        canvas.drawBitmap(backgroundImg, 0, 0, null);

        if (curentState == STATE_BULK || curentState == STATE_LOSE) {
            splash.getCurentImg().setBounds(splash.getRect());
            splash.getCurentImg().draw(canvas);
        }
        for(Kuvshinka k : arrayKuvshinka){
            k.getCurentImg().setBounds(k.getRect());
            k.getCurentImg().draw(canvas);
        }

        target.getCurentImg().setBounds(target.getRect());
        target.getCurentImg().draw(canvas);

        for(Heart h : arrayHeart){
            h.getCurentImg().setBounds(h.getRect());
            h.getCurentImg().draw(canvas);
        }
        //TODO background into obj

        hippo.getCurentImg().setBounds(hippo.getRect());
        hippo.getCurentImg().draw(canvas);

        if (curentState != STATE_BULK && curentState != STATE_LOSE && curentState != STATE_WIN) {
            canvas.save();
            Rect rect = new Rect(player.getRect());
            canvas.rotate((float) player.getHeading(), rect.centerX(), rect.centerY());
            player.getCurentImg().setBounds(rect);
            player.getCurentImg().draw(canvas);
//            canvas.drawRect(rect, paint);
            canvas.restore();
        }
        kamish.getCurentImg().setBounds(kamish.getRect());
        kamish.getCurentImg().draw(canvas);

        if (curentState == STATE_WIN){
            win.getCurentImg().setBounds(win.getRect());
            win.getCurentImg().draw(canvas);
        }

        if (curentState == STATE_LOSE && (System.currentTimeMillis()>now + timeUnderWater)){
            game_over.getCurentImg().setBounds(game_over.getRect());
            game_over.getCurentImg().draw(canvas);
        }
    }

    public void setRunning(boolean b) {
        running = b;
    }

//    public boolean doKeyUp(int keyCode, KeyEvent msg) {
//        return true;
//    }
//
//    public boolean doKeyDown(int keyCode, KeyEvent msg) {
//        return true;
//    }


}

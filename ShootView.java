package com.example.hideki.myapplication;

/**
 * Created by hideki on 2016/02/19.
 */
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.content.Intent;

import java.util.ArrayList;

public class ShootView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private SurfaceHolder holder;//お決まり
    private Thread thread;//お決まり
    private static String TAG = "Myapp";
    private float disp_w,disp_h;
    private Drawable playerimg,bulletimg;
    private Player P;
    private Enemy E;
    private ArrayList<Bullet[]> bulletlist ;
    private ArrayList<NormalEnemyBullet[]> NEBulletlist ;
    private FpsCalc fpscalc = new FpsCalc();

    int damage = 0,hidan=0;           //敵のダメージおよびヒダンダメージ
    public static long runtime=0;                   //自機弾のためのカウント
    public static long doMovetime=0;
    public static int Homingtime = 0;             //ホーミング弾のためのカウント
    public static int type = 0;                          //自機兵装のタイプ (Normal,Laser,Homing)
    final static int Normal = 0;
    final static int Laser = 1;
    final static int Homing = 2;
    static Point size;
    float currentX;
    float currentY;

    Bullet[] tmpBullet = new Bullet[Bullet.getWid()];

    public ShootView(Context context) {
        super(context);
        init(context);
    }

    public ShootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        holder = getHolder();//お決まり
        holder.addCallback(this);//お決まり
        holder.setFixedSize(getWidth(), getHeight());//お決まり

        disp_w = MainActivity.size.x;
        disp_h = MainActivity.size.y;
        Resources resources = this.getContext().getResources();//画像登録準備

        Bitmap playerimg= BitmapFactory.decodeResource(resources, R.drawable.player);
        Bitmap playerbit = Bitmap.createBitmap(playerimg, 0, 0, playerimg.getWidth(), playerimg.getHeight());
        Bitmap resizedplayerbit = Bitmap.createScaledBitmap(playerbit, 200, 200, false);
        P = new Player(disp_w,disp_h);
        P.Oint(resources,resizedplayerbit, disp_w / 2, disp_h * 2 /3, resizedplayerbit.getWidth(), resizedplayerbit.getHeight());

        initBullet(resources);

        E = new Enemy(disp_w,disp_h);
        if(Enemy.getType() == 0){
            Bitmap enemyimg= BitmapFactory.decodeResource(resources, R.drawable.freezer);
            Bitmap enemybit = Bitmap.createBitmap(enemyimg, 0, 0, enemyimg.getWidth(), enemyimg.getHeight());
            Bitmap resizedenemybit = Bitmap.createScaledBitmap(enemybit, 300, 300, false);
            E.Oint(resources, resizedenemybit, disp_w / 2, disp_h  / 4, resizedenemybit.getWidth(), resizedenemybit.getHeight());
        }

        /*---------------------追加-------------------------*/
        initNEBullet(resources);

        /*---------------------追加-------------------------*/

    }
    public void initBullet(Resources resources){
        Bitmap bulletimg= BitmapFactory.decodeResource(resources, R.drawable.bullet);
        Bitmap bulletbit = Bitmap.createBitmap(bulletimg, 0, 0, bulletimg.getWidth(), bulletimg.getHeight());
        Bitmap resizedbulletbit = Bitmap.createScaledBitmap(bulletbit, 32, 32, false);

        bulletlist = new ArrayList<Bullet[]>();
        int[] BulletVec1 = {-3,-10};
        int[] BulletVec2 = {-1,-10};
        int forcount=0;
        for(int i=0;i < Bullet.getBnum();i++){
            for(int j=0;j < Bullet.getWid();j++){
                tmpBullet[j] = new Bullet(disp_w,disp_h);
                tmpBullet[j].Oint(resources, resizedbulletbit, disp_w / 2, disp_h * 2 / 3, resizedbulletbit.getWidth(), resizedbulletbit.getHeight());
                //Log.d("MyApp", "init bulletnumber" + (forcount));
                tmpBullet[j].setBulletbumber(forcount++);
            }
            bulletlist.add(tmpBullet.clone());
            bulletlist.get(i)[0].setV(BulletVec1[0],BulletVec1[1]);
            bulletlist.get(i)[1].setV(BulletVec2[0],BulletVec2[1]);
            bulletlist.get(i)[2].setV(BulletVec2[0]*-1,BulletVec2[1]);
            bulletlist.get(i)[3].setV(BulletVec1[0]*-1,BulletVec1[1]);
        }
        P.bulletlist = bulletlist;
        /*
        for(Bullet bullet[]:bulletlist){
            for(int j=0;j < Bullet.getWid();j++){
                Log.d("MyApp", "bulletnumber" + bullet[j].getBulletbumber());
            }
        }
        Log.d("MyApp", "bulletnum" + bulletlist.size());
        */
    }

    public void initNEBullet(Resources resources){
        NEBulletlist = new ArrayList<NormalEnemyBullet[]>();
        NormalEnemyBullet[] tmpNEBullet;
        if(Enemy.getType() == 0){

            Bitmap normalEbulletimg= BitmapFactory.decodeResource(resources, R.drawable.enemy_bullet_1);
            Bitmap normalEbulletbit = Bitmap.createBitmap(normalEbulletimg, 0, 0, normalEbulletimg.getWidth(), normalEbulletimg.getHeight());
            Bitmap resizednormalEbulletbit = Bitmap.createScaledBitmap(normalEbulletbit, 80, 80, false);

            NormalEnemyBullet.setBnum(10);
            NormalEnemyBullet.setWid(5);
            NormalEnemyBullet.setInterval(30);

            tmpNEBullet = new NormalEnemyBullet[NormalEnemyBullet.getWid()];
            int forcount = 0;
            for(int i=0;i < NormalEnemyBullet.getBnum();i++){
                for(int j=0;j < NormalEnemyBullet.getWid();j++){
                    tmpNEBullet[j] = new NormalEnemyBullet(disp_w,disp_h);
                    tmpNEBullet[j].Oint(resources, resizednormalEbulletbit, -100, -100, resizednormalEbulletbit.getWidth(), resizednormalEbulletbit.getHeight());
                    //Log.d("MyApp", "init bulletnumber" + (forcount));
                    tmpNEBullet[j].setBulletbumber(forcount++);
                }
                NEBulletlist.add(tmpNEBullet.clone());
            }
            E.NEBulletlist = NEBulletlist;

        for(NormalEnemyBullet NEBullet[]:NEBulletlist){
            for(int j=0;j <NormalEnemyBullet.getWid();j++){
                Log.d("MyApp", "bulletnumber" + NEBullet[j].getBulletbumber());
            }
        }
        Log.d("MyApp", "bulletnum" + NEBulletlist.size());

        }
    }

    public void run() {

        //p.setAntiAlias(true);
        while (thread != null) {

            if(runtime++ > 100){  //ループ回数
                runtime = 0;
            }

            fpscalc.onUpdate();

            doMove();   //移動処理

            doDrow();   //描画処理

            try {
                Thread.sleep(16);//更新頻度
            } catch (Exception e) {
            }
        }
    }

    public void doMove(){
        playerBulletMove();   //自機弾の移動処理
        enemyBulletMove();  //敵弾の移動処理
    }

    public void playerBulletMove(){
        if (runtime % Bullet.getInterval() == 0
                && !P.bulletlist.get(Bullet.getNextshoot())[0].isShooted()) { //インターバルごとに弾を発射

            for (int i = 0; i < Bullet.getWid(); i++) {
                //tmpBullet[i].setShootedFlag(true);
                //tmpBullet[i].setVisible(true);
                P.bulletlist.get(Bullet.getNextshoot())[i].setShooted(true);
                P.bulletlist.get(Bullet.getNextshoot())[i].setVisible(true);
            }

            Bullet.setNextshoot(Bullet.getNextshoot() + 1);
            if (Bullet.getNextshoot() == P.bulletlist.size()) {
                Bullet.setNextshoot(0);
            }
            /*
            Log.d("MyApp", "nextshoot:"+Bullet.getNextshoot()+"\n"
                    + "runtime%:"+runtime % Bullet.getInterval() + "\n"
                    + "runtime:"+runtime + "\n"
                            + "domovetime:"+doMovetime + "\n"
            );
            */
        }

        for(Bullet bullet[]:P.bulletlist){    //自機弾の移動処理
            for (int j = 0; j < Bullet.getWid(); j++) {
                //Log.d("MyApp", "bulletnumber" + bulletlist.get(i)[j].getBulletbumber());
                bullet[j].BulletMove(P.getCx(), P.getCy());
                enemyHit(bullet[j]); //敵に弾がヒットした処理
            }
        }
    }
    public void enemyBulletMove(){
        NEBulletMove();
        SEBulletMove();
    }
    public void NEBulletMove(){

        if (runtime % NormalEnemyBullet.getInterval() == 0 ) {  //インターバルごとに弾を発射

            boolean standby = true;
            for (int i = 0; i < NormalEnemyBullet.getWid(); i++) {
                standby = standby && !E.NEBulletlist.get(NormalEnemyBullet.getNebnextshoot())[i].isShooted();
                }
                 if(standby) {
                     if(Enemy.getType()==0){    //敵タイプ:Freezer、自機狙い弾
                         playerAim();
                     }

                     for (int i = 0; i < NormalEnemyBullet.getWid(); i++) {
                         E.NEBulletlist.get(NormalEnemyBullet.getNebnextshoot())[i].setShooted(true);
                         E.NEBulletlist.get(NormalEnemyBullet.getNebnextshoot())[i].setVisible(true);
                     }

                     NormalEnemyBullet.setNebnextshoot(NormalEnemyBullet.getNebnextshoot() + 1);
                     if (NormalEnemyBullet.getNebnextshoot() == E.NEBulletlist.size()) {
                         NormalEnemyBullet.setNebnextshoot(0);
                     }
                /*
                Log.d("MyApp", "Nbnextshoot:"+Bullet.getNbnextshoot()+"\n"
                        + "runtime%:"+runtime % Bullet.getInterval() + "\n"
                        + "runtime:"+runtime + "\n"
                                + "domovetime:"+doMovetime + "\n"
                );
                */
                 }
        }

        for(NormalEnemyBullet NEBullet[]:E.NEBulletlist){    //敵通常弾の移動処理
            for (int i = 0; i < NormalEnemyBullet.getWid(); i++) {
                //Log.d("MyApp", "bulletnumber" + bulletlist.get(i)[j].getBulletbumber());
                NEBullet[i].EnemyBulletMove();
                playerHit(NEBullet[i]); //敵に弾がヒットした処理
            }
        }
    }
    public void playerAim(){    //自機狙い弾の処理
        double radian = Math.atan2(P.getCy() - E.getCy(), P.getCx() - E.getCx());
        double angle = Calc.radianToangle(radian);
        //Log.d("MyApp", "angle:"+angle+"\n");

        if(NormalEnemyBullet.getWid()%2 == 1){   //奇数弾
            int center = NormalEnemyBullet.getWid() / 2 ;
            int sidenum = (NormalEnemyBullet.getWid() - 1) /2;
            float intervalangle = NormalEnemyBullet.getShootangle() / (E.NEBulletlist.get(NormalEnemyBullet.getNebnextshoot()).length-1);

            E.NEBulletlist.get(NormalEnemyBullet.getNebnextshoot())[center].setCV(E.getCx()
                    , E.getCy()
                    , (float) Math.cos(radian)
                    , (float) Math.sin(radian)
            );
            E.NEBulletlist.get(NormalEnemyBullet.getNebnextshoot())[center].setSpeed(NormalEnemyBullet.DEFAULTSPEED);
            //Log.d("MyApp", "bulletnumber" + E.NEBulletlist.get(NormalEnemyBullet.getNebnextshoot())[center].getBulletbumber());
            //Log.d("MyApp", "sin" + (float) Math.sin(radian) + "\n");
            //Log.d("MyApp", "cos" + (float) Math.cos(radian) + "\n");

                             /*
                             Log.d("MyApp", "length" + E.NEBulletlist.get(NormalEnemyBullet.getNebnextshoot()).length + "\n");
                             Log.d("MyApp", "center:" + center + "\n");
                             Log.d("MyApp", "intervalangle" + intervalangle);
                             */
            for(int i = 1;i <= sidenum;i++) {
                E.NEBulletlist.get(NormalEnemyBullet.getNebnextshoot())[center-i].setCV(
                        E.getCx()
                        , E.getCy()
                        , (float) Math.cos(Calc.angleToradian(angle + intervalangle * i))
                        , (float) Math.sin(Calc.angleToradian(angle + intervalangle * i))
                );
                E.NEBulletlist.get(NormalEnemyBullet.getNebnextshoot())[center+i].setCV(
                        E.getCx()
                        , E.getCy()
                        , (float) Math.cos(Calc.angleToradian(angle - intervalangle * i))
                        , (float) Math.sin(Calc.angleToradian(angle - intervalangle * i))
                );
                E.NEBulletlist.get(NormalEnemyBullet.getNebnextshoot())[center-i].setSpeed(NormalEnemyBullet.DEFAULTSPEED);
                E.NEBulletlist.get(NormalEnemyBullet.getNebnextshoot())[center+i].setSpeed(NormalEnemyBullet.DEFAULTSPEED);

            }
        }else{ //偶数弾
            ;
        }
    }
    public void SEBulletMove(){

        //playerHit();
    }

    public void playerHit(NormalEnemyBullet NEBullet){

    }
    public void playerHit(SpecialEnemyBullet SEBullet){

    }

    public void enemyHit(Bullet bullet) {
        if (bullet.isVisible()) {
            if ((bullet.getCx() - E.getCx()) * (bullet.getCx() - E.getCx())
                    + (bullet.getCy() - E.getCy()) * (bullet.getCy() - E.getCy())
                    <= E.getRadius()){
                bullet.setVisible(false);
                E.setLife(E.getLife() - bullet.getDamage());

                if(E.getLife() > E.getMAXLIFE()/2){
                    //レベル0
                }else if(E.getLife() > E.getMAXLIFE() /3 && E.getLife() <= E.getMAXLIFE()/2){
                    //レベル1
                }else if(E.getLife() <= E.getMAXLIFE() /3 && E.getLife() > 0){

                    //レベル2
                }else{
                    //クリア
                }
            }
        }
    }

    public void doDrow(){
        Canvas canvas = holder.lockCanvas();//お決まり
        Paint p = new Paint();
        p.setAntiAlias(true);

        canvas.drawColor(Color.LTGRAY);  //背景

        //p.setColor(Color.GREEN);  //弾の描画
        for(Bullet bullet[] : P.bulletlist) {
            for (int i = 0; i < Bullet.getWid(); i++) {
                if (bullet[i].isVisible()) {
                    bullet[i].BulletDraw(canvas);
                }
            }
        }

        P.ODraw(canvas);    //自機の描画
        p.setColor(Color.RED);  //当たり判定の描画
        RectF rect = new RectF();
        rect.left = P.getCx()-P.getImgw()/P.getCollDetectRate();
        rect.top    = P.getCy()-P.getImgh()/P.getCollDetectRate();
        rect.right  = P.getCx()+P.getImgw()/P.getCollDetectRate();
        rect.bottom = P.getCy()+P.getImgh()/P.getCollDetectRate();
        canvas.drawOval(rect, p);

        EBulletDraw(canvas,p);   //敵弾の描画

        E.ODraw(canvas);    //敵の描画

        EnemyLifeDraw(canvas, p);    //敵のライフ描画

        //デバッグ用テキストビュー

        p.setTextSize(100);
        p.setColor(Color.WHITE);

        canvas.drawText("fps:" + fpscalc.getFrameRate(), 50, 100, p);

        holder.unlockCanvasAndPost(canvas);//お決まり
    }
    public void EBulletDraw(Canvas c,Paint p) {
        for(NormalEnemyBullet NEBullet[]:E.NEBulletlist){    //敵弾の移動処理
            for (int i = 0; i < NormalEnemyBullet.getWid(); i++) {
                NEBullet[i].EnemyBulletDraw(c);
            }
        }
    }

    public void EnemyLifeDraw(Canvas c,Paint p){

        if(E.getLife() > E.getMAXLIFE()/2){
            p.setColor(Color.parseColor("#228b22"));
        }else if(E.getLife() > E.getMAXLIFE() /3 && E.getLife() <= E.getMAXLIFE()/2){
            p.setColor(Color.YELLOW);
        }else{
            p.setColor(Color.RED);
        }
        // 描画する領域を指定する
        RectF rect = new RectF();
        rect.left   = E.getCx() - E.getImgw()/2;
        rect.right  = E.getCx() + E.getImgw()/2;
        rect.bottom = E.getCy() - E.getImgh()*2;
        rect.top    = rect.bottom - E.getImgh();
        rect.left   = disp_w / 20;
        rect.right  = disp_w / 20 + disp_w /20 * 18 * ((float)E.getLife()/(float)E.getMAXLIFE());
        rect.bottom = disp_h / 20 * 2;
        rect.top    = disp_h / 20 ;
        c.drawRect(rect, p);
        /*
        // 描画する領域を指定する
        RectF rect = new RectF();
        rect.left   = E.getCx() - E.getImgw()/2;
        rect.right  = E.getCx() + E.getImgw()/2;
        rect.bottom = E.getCy() - E.getImgh()*2;
        rect.top    = rect.bottom - E.getImgh();
        // 扇形を描画する
        float startAngle = -90.0f, sweepAngle = -90.0f + 360.0f * E.getLife() / E.getMAXLIFE();
        boolean useCenter = true;
        c.drawArc(rect, 90, 270, useCenter, p);
        */
    }

    public boolean onTouchEvent(MotionEvent e){

        if(e.getAction() == MotionEvent.ACTION_DOWN ){		 //画面に触れたとき
            //  x = e.getX();
            //	tmpx = x;
            currentX = e.getX();
            currentY = e.getY();
        }

        if(e.getAction() == MotionEvent.ACTION_MOVE){        //指を動かしたとき

            P.setCx(P.getCx() + e.getX() - currentX);
            if(P.getCx() < 0){
                P.setCx(0);
            }else if(P.getCx() > disp_w){
                P.setCx(disp_w);
            }

            P.setCy(P.getCy() + e.getY() - currentY);
            if(P.getCy() < 0){
                P.setCy(0);
            }else if(P.getCy() > disp_h){
                P.setCy(disp_h);
            }

            currentX = e.getX();
            currentY = e.getY();

        }
        if(e.getAction() == MotionEvent.ACTION_UP){               //指を離したとき
            currentX = 0;
            currentY = 0;
        }
        return true;
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}//お決まり
    public void surfaceCreated(SurfaceHolder arg0) {thread = new Thread(this);thread.start();}//お決まり
    public void surfaceDestroyed(SurfaceHolder arg0) {thread = null;}//お決まり

}
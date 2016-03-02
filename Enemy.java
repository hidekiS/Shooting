package com.example.hideki.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;

/**
 * Created by hideki on 2016/02/20.
 */
public class Enemy extends Object{
    private final int MAXLIFE= 10000;

    private int life;
    boolean flag;
    private boolean frozen;
    private static int type;
    public ArrayList<NormalEnemyBullet[]> NEBulletlist;
    public ArrayList<NormalEnemyBullet[]> SEBulletlist;
    private float radius;


    public Enemy(){
        super();
        initEnemy();
    }
    public Enemy(float dw,float dh){
        super(dw,dh);
        initEnemy();
    }
    public void initEnemy(){
        life = MAXLIFE;
        flag = true;
        type = 0;
    }


    public void EnemyMove(float PX, float PY) {

    }

    public void Oint(Resources res,Bitmap imgb, float x, float y, int w, int h) {
        super.Oint(res,imgb,x,y,w,h);
        radius = this.getImgw()/2 * this.getImgh()/2;
    }

    public void EnemyDraw(Canvas c){
        super.ODraw(c);
    }
    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
        if(this.life < 0){
            this.life = 0;
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        Enemy.type = type;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getMAXLIFE() {
        return MAXLIFE;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }
}
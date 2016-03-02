package com.example.hideki.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by hideki on 2016/02/19.
 */

public class Bullet extends Object{
    final static float VX = 0;
    final static float VY = -1;
    final static double SPEED = 5;
    final static int BNUM = 4;
    final static int WID = 4;
    final static int INTERVAL = 10;

    static float lenge;
    static int nextshoot = 0;

    private int damage;
    private boolean visible;
    private boolean shooted;
    private int bulletbumber;

    public Bullet() {
        super();
        initBullet();
    }

    public Bullet(float dw,float dh){
        super(dw, dh);
        initBullet();
        lenge = dh;
    }

    public void initBullet(){
        this.setVx(VX);
        this.setVy(VY);
        this.setSpeed(SPEED);
        shooted = false;
        visible = false;
        lenge = 1000;
        bulletbumber = 0;
        damage = 60;
    }

    public void BulletMove(float PX, float PY) {
        if (this.isShooted()) {
            this.setCx(this.getCx() + this.getVx() * (float) this.getSpeed());
            this.setCy(this.getCy() + this.getVy() * (float) this.getSpeed());
            if (this.getCy() < -10) {
                this.setCx(PX);
                this.setCy(PY);
                this.setShooted(false);
                this.setVisible(false);
            }
        }else{
            this.setCx(PX);
            this.setCy(PY);
        }
    }

    public void BulletDraw(Canvas c){
        super.ODraw(c);
    }
    public void setV(float vx, float vy){
        this.setVx(vx);
        this.setVy(vy);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public static int getBnum() {
        return BNUM;
    }

    public boolean isShooted() {
        return shooted;
    }

    public void setShooted(boolean shootedFlag) {
        this.shooted = shootedFlag;
    }

    public static int getWid() {
        return WID;
    }

    public static int getInterval() {
        return INTERVAL;
    }

    public static int getNextshoot() {
        return nextshoot;
    }

    public static void setNextshoot(int nextshoot) {
        Bullet.nextshoot = nextshoot;
    }

    public int getBulletbumber() {
        return bulletbumber;
    }

    public void setBulletbumber(int bulletbumber) {
        this.bulletbumber = bulletbumber;
    }

}
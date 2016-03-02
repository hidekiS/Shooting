package com.example.hideki.myapplication;

import android.graphics.Canvas;

/**
 * Created by hideki on 2016/02/20.
 */
public class EnemyBullet extends Bullet {


    private static int ebnextshoot;
    public EnemyBullet() {
        super();
        initEnemyBullet();
    }

    public EnemyBullet(float dw,float dh){
        super(dw, dh);
        initEnemyBullet();
    }

    public void initEnemyBullet() {
        this.setSpeed(SPEED);
        this.setShooted(false);
        this.setVisible(false);
        this.setBulletbumber(0);
    }

    public void EnemyBulletMove() {
        if (this.isShooted()) {
            this.setCx(this.getCx() + this.getVx() * (float) this.getSpeed());
            this.setCy(this.getCy() + this.getVy() * (float) this.getSpeed());
            if (this.getCy() < 0
                    || this.getCy() > this.getDisp_h()
                    || this.getCx() < 0
                    || this.getCx() > this.getDisp_w()) {
                this.setCx(-100);
                this.setCy(-100);
                this.setShooted(false);
                this.setVisible(false);
            }
        }
    }
    public void setCV(float cx,float cy,float vx,float vy){
        this.setCx(cx);
        this.setCy(cy);
        this.setV(vx, vy);
    }
    public void EnemyBulletDraw(Canvas c){
        super.BulletDraw(c);
    }

    public static int getEbnextshoot() {
        return ebnextshoot;
    }

    public static void setEbnextshoot(int ebnextshoot) {
        EnemyBullet.ebnextshoot = ebnextshoot;
    }

}

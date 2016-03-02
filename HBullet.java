package com.example.hideki.myapplication;

/**
 * Created by hideki on 2016/02/19.
 */

public class HBullet extends Bullet {

    int vectx;
    int vecty;
    int homingtime = 0;
    boolean homingflag;

    public HBullet() {
        super();
        homingflag = false;
        vectx = 0;
        vecty = -20;
    }

    public int getVectx() {
        return vectx;
    }

    public void setVectx(int vectx) {
        this.vectx = vectx;
    }

    public int getVecty() {
        return vecty;
    }

    public void setVecty(int vecty) {
        this.vecty = vecty;
    }

    public int getHomingtime() {
        return homingtime;
    }

    public void setHomingtime(int homingtime) {
        this.homingtime = homingtime;
    }

    public boolean isHomingflag() {
        return homingflag;
    }

    public void setHomingflag(boolean homingflag) {
        this.homingflag = homingflag;
    }
}

package com.example.hideki.myapplication;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by hideki on 2016/02/20.
 */


public class Player extends Object{
    private static int MAXREMAININGLIFE = 3;
    private static int COLLISIONDETECTIONRATE = 14;

    private int remainingLife;
    private int wepontype;//1:通常弾
    private boolean frozen;
    private int collDetectRate;

    public ArrayList<Bullet[]> bulletlist;

    public Player() {
        super();
        initPlayer();

    }
    public Player(float dw,float dh){
        super(dw,dh);
        initPlayer();
    }
    public void initPlayer(){
        remainingLife = MAXREMAININGLIFE;
        frozen = false;
        wepontype = 1;
        collDetectRate = COLLISIONDETECTIONRATE;
    }

    public int getRemainingLife() {
        return remainingLife;
    }

    public void setRemainingLife(int remainingLife) {
        this.remainingLife = remainingLife;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public int getWepontype() {
        return wepontype;
    }

    public void setWepontype(int wepontype) {
        this.wepontype = wepontype;
    }

    public float getCollDetectRate() {
        return collDetectRate;
    }

    public void setCollDetectRate(int collDetectRate) {
        this.collDetectRate = collDetectRate;
    }
}
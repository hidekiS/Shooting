package com.example.hideki.myapplication;

/**
 * Created by hideki on 2016/02/20.
 */
public class NormalEnemyBullet extends EnemyBullet {


    final static double DEFAULTSPEED = 10;
    /*---------------追加------------------*/
    private static int nebnextshoot;
    private static int width = 1;
    private static int bnum = 3;
    private static int interval = 30;
    private static float shootangle = 100;
    private static double masterspeed = 100;

    /*---------------追加------------------*/

    public NormalEnemyBullet() {
        super();
        initEnemyBullet();
    }

    public NormalEnemyBullet(float dw,float dh){
        super(dw, dh);
        initEnemyBullet();
    }

    public void initNormalEnemyBullet() {
        super.initEnemyBullet();
    }

    public static int getWid() {
        return width;
    }

    public static void setWid(int width) {
        NormalEnemyBullet.width = width;
    }

    public static int getInterval() {
        return interval;
    }

    public static void setInterval(int inteval) {
        NormalEnemyBullet.interval = inteval;
    }

    public static int getBnum() {
        return bnum;
    }

    public static void setBnum(int bnum) {
        NormalEnemyBullet.bnum = bnum;
    }

    public static float getShootangle() {
        return shootangle;
    }

    public static void setShootangle(float shootangle) {
        NormalEnemyBullet.shootangle = shootangle;
    }

    public static int getNebnextshoot() {
        return nebnextshoot;
    }

    public static void setNebnextshoot(int nebnextshoot) {
        NormalEnemyBullet.nebnextshoot = nebnextshoot;
    }

}
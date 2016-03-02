package com.example.hideki.myapplication;

/**
 * Created by hideki on 2016/02/27.
 */
public class SpecialEnemyBullet extends EnemyBullet{

    private static int width = 1;
    private static int bnum = 3;
    private static int inteval = 10;
    private static int type;

    public SpecialEnemyBullet(){
        initSpecialEnemyBullet();
    }
    public SpecialEnemyBullet(float dw,float dh){
        super(dw, dh);
        initSpecialEnemyBullet();
    }

    public void initSpecialEnemyBullet() {
        super.initEnemyBullet();
    }
    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        SpecialEnemyBullet.width = width;
    }

    public static int getBnum() {
        return bnum;
    }

    public static void setBnum(int bnum) {
        SpecialEnemyBullet.bnum = bnum;
    }

    public static int getInteval() {
        return inteval;
    }

    public static void setInteval(int inteval) {
        SpecialEnemyBullet.inteval = inteval;
    }

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        SpecialEnemyBullet.type = type;
    }
}

package com.example.hideki.myapplication;

import android.graphics.Rect;

/**
 * Created by hideki on 2016/02/19.
 */
public class Calc {

    static float radian;
    static float A,B;
    static float VX,VY;
    /*
     * わたくしの環境がXPERIAでその画面幅でアプリを作成
     * しているのでそれから各アプリの画面幅に合うように調整
     * させるための変数
     */
    static public final float XPERIA_W = 1080f;
    static public final float XPERIA_H = 1750f;
    //せっかくなので０も固定値に
    static public final float ZERO = 0f;

    public Calc(){
    }
    /*角度の計算*/
    public static float calc_radian(float MX,float MY,float EX,float EY){
        if(MY-EY>0){
            A = MY-EY;
        }else{
            A = EY-MY;
        }
        if(MX-EX>0){
            B = MX-EX;
        }else{
            B = EX-MX;
        }

        radian = (float) Math.atan(A/B);
        return radian;
    }
    //ベクトルの標準化
    public static void calcNorm(float[] vec,float vx, float vy,float sqrt){
        vec[0] = vx / sqrt;
        vec[1]= vy / sqrt;
    }
    public static float calcNormVy(){
        return VY;
    }
    /*引数の武器タイプから対応する番号を返す*/
    public static int convertWeponType(String Type){
        if(Type.equals("Normal")) {
            return 1;
        }else if(Type.equals("Laser")){
            return 2;
        }else if(Type.equals("Horming")){
            return 3;
        }
        return -1;
    }
    /*
     * 受け取ったxy座標と調べたい短形範囲が重なっているかいないか
     */

    public static double angleToradian(double angle){
        return angle * Math.PI / 180;
    }
    public static double radianToangle(double radian){
        return radian  * 180 / Math.PI;
    }

    public static boolean RectTap(int x,int y,Rect gazou){
        return gazou.left < x && gazou.top < y && gazou.right > x && gazou.bottom > y;
    }
    /*
     * この２行で各座標を実装機種の画面比に合わせます
     */
    public static int setSizeX(float disp_w,float zahyou){return (int) (zahyou * (disp_w / XPERIA_W));}
    public static int setSizeY(float disp_h,float zahyou){return (int) (zahyou * (disp_h / XPERIA_H));}
}
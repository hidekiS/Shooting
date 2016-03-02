package com.example.hideki.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by hideki on 2016/02/20.
 */
public abstract class Object {
    private static float disp_w, disp_h;
    private Drawable img;
    private float cx, cy;
    private float vx, vy;
    private double speed;
    private int imgw, imgh;

    Object() {
    }

    public Object(float dw, float dh) {
        disp_w = dw;
        disp_h = dh;
    }

    public void Oint(Resources res,Bitmap imgb, float x, float y, int w, int h) {
        img = new BitmapDrawable(res, imgb);
        cx = Calc.setSizeX(disp_w, x);
        cy = Calc.setSizeY(disp_h, y);
        imgw = w;
        imgh = h;
    }

    public void ODraw(Canvas c) {
        img.setBounds((int) (cx - imgw / 2), (int) (cy - imgh / 2), (int) (cx + imgw / 2), (int) (cy + imgh / 2));
        img.draw(c);
    }

    public float getDisp_w() {
        return disp_w;
    }

    public void setDisp_w(float disp_w) {
        this.disp_w = disp_w;
    }

    public float getDisp_h() {
        return disp_h;
    }

    public void setDisp_h(float disp_h) {
        this.disp_h = disp_h;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getImgw() {
        return imgw;
    }

    public void setImgw(int imgw) {
        this.imgw = imgw;
    }

    public int getImgh() {
        return imgh;
    }

    public void setImgh(int imgh) {
        this.imgh = imgh;
    }
}
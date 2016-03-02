package com.example.hideki.myapplication;

/**
 * Created by hideki on 2016/02/25.
 */
public class FpsCalc {

    private long _startTime = 0;            //測定開始時刻
    private int  _cnt = 0;                  //カウンタ
    private float _fps;                     //fps
    private final static int N = 30;        //平均を取るサンプル数
    private final static int FONT_SIZE = 20;//フォントサイズ

    public FpsCalc(){
        _startTime = System.currentTimeMillis();
    }

    public float getFrameRate() {
        return _fps;
    }

    public void onUpdate(){
        if( _cnt == 0 ){ //1フレーム目なら時刻を記憶
            _startTime = System.currentTimeMillis();
        }
        if( _cnt == N ){ //60フレーム目なら平均を計算する
            long t = System.currentTimeMillis();
            _fps = 1000.f/((t-_startTime)/(float)N );
            _cnt = 0;
            _startTime = t;
        }
        _cnt++;
    }
}
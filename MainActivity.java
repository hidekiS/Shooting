package com.example.hideki.myapplication;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    //   TextView tv1;
    //public float disp_w,disp_h;
    public static Point size;
    //static  Resources res;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Window window = getWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        WindowManager wm = getWindowManager();
        // ディスプレイのインスタンス生成
        Display disp = wm.getDefaultDisplay();
        size = new Point();
        disp.getSize(size);
        //res = getResources();
        //setContentView(new MainLoop(this));

        setContentView(new ShootView(this));
        //setContentView(R.layout.activity_main);
    }
    /*
    public boolean onTouchEvent(MotionEvent e){
        if(e.getAction() == MotionEvent.ACTION_UP){               //指を離したとき
            Intent intent = new Intent(this,ShootActivity.class);
            startActivity(intent);
        }
        return true;
    }
    */

}
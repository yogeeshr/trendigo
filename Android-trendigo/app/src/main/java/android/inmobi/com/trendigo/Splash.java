package android.inmobi.com.trendigo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by deepak.jha on 2/20/16.
 */
public class Splash extends Activity{

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        //StartAnimations();
        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(2*1000);
                    Intent i=new Intent(getBaseContext(),HomePage.class);
                    startActivity(i);
                    finish();

                } catch (Exception e) {

                }
            }
        };


        background.start();
    }

//    private void StartAnimations() {
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
//        anim.reset();
//        LinearLayout l=(LinearLayout) findViewById(R.id.linearlayout);
//        l.clearAnimation();
//        l.startAnimation(anim);
//
//        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
//        anim.reset();
//        ImageView iv = (ImageView) findViewById(R.id.splashimg);
//        iv.clearAnimation();
//        iv.startAnimation(anim);
//
//    }
}
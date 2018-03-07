package com.wja.myapplication.animator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanke on 2018/2/3.
 */

public class ParticleSmasher extends View{
    private List<SmashAnimator> smashAnimators=new ArrayList<>();
    private Activity activity;
    private Canvas canvas;



    public ParticleSmasher(Activity activity) {
        super((Context) activity);
        this.activity=activity;
        addView2Window(activity);
        init();
    }


    private void addView2Window(Activity activity){
        ViewGroup viewGroup=activity.findViewById(Window.ID_ANDROID_CONTENT);
        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(this,params);

    }

    private void init(){
        canvas=new Canvas();
    }


    public SmashAnimator with(View view){
        SmashAnimator smashAnimator=new SmashAnimator(this,view);
        smashAnimators.add(smashAnimator);
        return smashAnimator;
    }
    public Bitmap createBitmapFromView(View view){
        view.clearFocus();
        Bitmap bitmap=Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888);
        if (bitmap!=null){
            synchronized (canvas){
                Canvas c=canvas;
                c.setBitmap(bitmap);
                view.draw(c);
                c.setBitmap(null);
            }
        }
        return bitmap;
    }

    public Rect getViewRect(View view){
        Rect rect=new Rect();
         view.getGlobalVisibleRect(rect);
         int[] loaction=new int[2];
        getLocationOnScreen(loaction);
        //int[] location2=new int [2];
        //view.getLocationOnScreen(location2);
        rect.offset(-loaction[0],-loaction[1]);
        return rect;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (SmashAnimator smashAnimator:smashAnimators){
            smashAnimator.onDraw(canvas);
        }
    }


    public void reShowView(View view){
        view.animate().alpha(1).setDuration(500L).start();

    }



}

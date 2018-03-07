package com.wja.myapplication.animator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import com.wja.myapplication.SizeUtil;

import java.util.Date;
import java.util.Random;

/**
 * Created by hanke on 2018/2/3.
 */

public class SmashAnimator {
    private static final String TAG="SmashAnimator";
    public static final int STYLE_EXPLOSIOM=1;//爆炸
    public static final int STYLE_FLOAT_LEFT=2;//左飘
    public static final int STYLE_FLOAT_RIGHT=3;//右飘
    public static final int STYLE_FLOAT_TOP=4;//上飘
    public static final int STYLE_FLOAT_BOTTOM=5;//下飘
    private ParticleSmasher mContainer;//绘制动画的View
    private Bitmap bitmap;
    private Rect mRect;//获取bitmap的边界位置
    private Particle[][] mParticles;
    private long mDuration=1000L;
    private long mStartDelay=150L;
    private float mEndValue = 1.5f;
    private int mStyle=STYLE_EXPLOSIOM;
    private ValueAnimator valueAnimator;
    private View mAnimatorView;
    private Paint mPaint;
    private int mRadius= SizeUtil.dp2Px(2);
    private float mHorizontalMultiple = 3;             // 粒子水平变化幅度
    private float mVerticalMultiple = 4;
    private OnAnimatorListener animatorListener;
    // 加速度插值器
    private static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateInterpolator(0.6f);

    public SmashAnimator(ParticleSmasher smasherView,View smashAnimator) {
        mContainer=smasherView;
       init(smashAnimator);
    }

    private void init(View smasherView){
        mAnimatorView=smasherView;
        bitmap=mContainer.createBitmapFromView(smasherView);
        mRect=mContainer.getViewRect(smasherView);
        initValueAnimator();
        initPaint();
    }
    private void initValueAnimator(){
        valueAnimator=new ValueAnimator();
        valueAnimator.setFloatValues(0F,mEndValue);
      //  valueAnimator.setInterpolator(DEFAULT_INTERPOLATOR);
    }


    private void initPaint(){
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
    }

    public void start(){
       setValueAnimator();
       calculateParticles(bitmap);
        hideView(mAnimatorView,mStartDelay);
        valueAnimator.start();
       mContainer.invalidate();
    }

    private void setValueAnimator(){
        valueAnimator.setDuration(mDuration);
        valueAnimator.setStartDelay(mStartDelay);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.i(TAG, String.valueOf(new Date(System.currentTimeMillis())));
                if (animatorListener!=null){
                    animatorListener.onAnimatorStart();

                }

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.i(TAG, String.valueOf(new Date(System.currentTimeMillis())));
                if (animatorListener!=null){
                    animatorListener.onAnimatorEnd();
                }
            }
            @Override
            public void onAnimationCancel(Animator animator) {}
            @Override
            public void onAnimationRepeat(Animator animator) {}
        });
    }

    private void calculateParticles(Bitmap bitmap){
        int raw=bitmap.getHeight()/(mRadius*2);//行
        int col=bitmap.getWidth()/(mRadius*2);//列
        Random random = new Random(System.currentTimeMillis());
        mParticles=new Particle[raw][col];
        for (int i=0;i<raw;i++){
           // Log.i(TAG,"i="+i);
            for (int j=0;j<col;j++){
                int x=j*mRadius*2+mRadius;
                int y=i*mRadius*2+mRadius;
                int color=bitmap.getPixel(x,y);
              //  Log.i(TAG,"i="+i+"j="+j);
                Point point=new Point(mRect.left+x,mRect.top+y);
                switch (mStyle){
                    case STYLE_EXPLOSIOM:

                       mParticles[i][j]=new ExplosionParticle(color,mRadius,mRect,mEndValue,random,mHorizontalMultiple,mVerticalMultiple);
                        break;
                    default:
                        mParticles[i][j]=new FloatParticle(mStyle,point,color,mRadius,mRect,mEndValue,random,mHorizontalMultiple,mVerticalMultiple);
                        break;
                }
            }
        }
        bitmap.recycle();
        bitmap=null;
    }

    private void hideView(final View view,long startDelay){
        /*ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(startDelay + 50).setFloatValues(0f, 1f);
        // 使View颤抖
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            Random random = new Random();

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setTranslationX((random.nextFloat() - 0.5F) * view.getWidth() * 0.05F);
                view.setTranslationY((random.nextFloat() - 0.5f) * view.getHeight() * 0.05f);
            }
        });
        valueAnimator.start();
        // 将View 缩放至0、透明至0
        view.animate().setDuration(260).setStartDelay(startDelay).scaleX(0).scaleY(0).alpha(0).start();*/


        ValueAnimator animator=ValueAnimator.ofFloat(0f,1f);
        animator.setDuration(startDelay+50);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            Random random=new Random();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((random.nextFloat()-0.5f)*view.getWidth()*0.05f);
                view.setTranslationY((random.nextFloat()-0.5f)*view.getHeight()*0.05f);
            }
        });
        animator.start();
        view.animate().setDuration(startDelay+50).alpha(0).start();



    }


    public boolean onDraw(Canvas canvas){
        if (!valueAnimator.isStarted()){
            return false;
        }
        for(Particle[] particles:mParticles){
            for (Particle p:particles){
               // Log.i(TAG,"value"+valueAnimator.getAnimatedValue());
                p.advance((float) (valueAnimator.getAnimatedValue()), mEndValue);
                mPaint.setColor(p.color);
                mPaint.setAlpha((int) (Color.alpha(p.color) * p.alpha));
                 canvas.drawCircle(p.cx,p.cy,p.radius,mPaint);
            }
        }
        mContainer.invalidate();
        return true;
    }



    public SmashAnimator setDuration(long duration){
        mDuration=duration;
        return this;
    }

    public SmashAnimator setStartDelay(long delay){
        mStartDelay=delay;
        return this;
    }

    public SmashAnimator setStyle(int style){
        mStyle=style;
        return this;
    }

    public SmashAnimator addAnimatorListener(OnAnimatorListener listener){
        animatorListener=listener;
        return this;
    }

    public SmashAnimator setHorizontalMultiple(float horizontalMultiple){
       mHorizontalMultiple=horizontalMultiple;
        return this;
    }
    public  SmashAnimator setVerticalMultiple(float verticalMultiple){
        verticalMultiple=mVerticalMultiple;
        return this;
    }


    public interface OnAnimatorListener{
        void onAnimatorStart();
        void onAnimatorEnd();

    }
}

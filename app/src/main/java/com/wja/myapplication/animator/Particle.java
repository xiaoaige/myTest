package com.wja.myapplication.animator;

/**
 * Created by hanke on 2018/2/3.
 */

public class Particle {
    public int color;   //颜色
    public float radius;  //半径
    public float alpha;
    public float cx;
    public float cy;
    public float horizontalElement;
    public float verticalElement;
    public float baseRadius;
    public float baseCx;
    public float baseCy;
    public float font;    //决定粒子在动画开始多久之后，开始显示
    public float later;    //决定粒子在动画结束前多少时间开始隐藏


    public void advance(float factor,float endValue){}
}

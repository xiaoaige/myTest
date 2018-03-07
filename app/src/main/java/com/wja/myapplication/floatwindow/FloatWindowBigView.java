package com.wja.myapplication.floatwindow;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.wja.myapplication.R;

public class FloatWindowBigView extends LinearLayout implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private Handler mHandler = new Handler();
    private Context context;
    /**
     * 记录大悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;
    /**
     * 记录大悬浮窗的高度
     */
    public static int viewHeight;

    private WindowManager.LayoutParams params;
    private WindowManager windowManager;

    public FloatWindowBigView(final Context context) {
        super(context);
        this.context = context;
        windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
        View view = findViewById(R.id.big_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        findViewById(R.id.close).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);

        findViewById(R.id.changeInputMethodBtn).setOnClickListener(this);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.close:
                // 点击关闭悬浮窗的时候，移除所有悬浮窗，并停止Service
                MyWindowManager.removeBigWindow(context);
                MyWindowManager.removeSmallWindow(context);
                intent = new Intent(getContext(), FloatWindowService.class);
                context.stopService(intent);
                break;
            case R.id.back:
                // 点击返回的时候，移除大悬浮窗，创建小悬浮窗
                MyWindowManager.removeBigWindow(context);
                break;
            case R.id.changeInputMethodBtn://切换输入法
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showInputMethodPicker();//移除
                mHandler.postDelayed(new Runnable() {//延迟一点时间才关闭的悬浮窗，否则无法显示输入法选择dialog
                    @Override
                    public void run() {
                        MyWindowManager.removeBigWindow(context);
                    }
                }, 1000);
                break;
            case R.id.button1:
                intent = new Intent(Settings.ACTION_DATE_SETTINGS);//设置系统时间
                break;
            case R.id.button2:
                intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);//切换系统语言
                break;
            case R.id.button4:
                intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);// 跳转到应用列表
                break;
            case R.id.button5:
                intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);//  跳转到辅助功能
                break;
            case R.id.button6:
                intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);//开发者选项
                break;
            default:
                break;
        }
        if (intent != null && v.getId() != R.id.close) {
            MyWindowManager.removeBigWindow(context);
            // 点击返回的时候，移除大悬浮窗，创建小悬浮窗
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //   isPressed = true;
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                //  xInView = event.getX();
                //  yInView = event.getY();
                // xDownInScreen = event.getRawX();
                // yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                updateViewPosition();
                // 手指移动的时候更新小悬浮窗的状态和位置
                // updateViewStatus();
                // updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
     /*   isPressed = false;
        if (MyWindowManager.isReadyToLaunch()) {
            launchRocket();
        } else {
            updateViewStatus();
//                    KLog.e(xDownInScreen + " " + xInScreen + " " + yDownInScreen + " " + yInScreen);
            // 如果手指离开屏幕时，xDownInScreen和xInScreen的差值小于10dp，且yDownInScreen和yInScreen的差值小于10dp，则视为触发了单击事件。
            if (Math.abs((xDownInScreen - xInScreen)) < Dp2Px(getContext(), 10) && Math.abs((yDownInScreen - yInScreen)) < Dp2Px(getContext(), 10)) {
//                        KLog.e("打开bigView --------->");
                openBigWindow();
            }
        }*/
                break;


        } return true;
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    //    KLog.e(view + " " + hourOfDay + " " + minute);
    }

    public void setParams(WindowManager.LayoutParams params){
        this.params=params;
    }
    private void updateViewPosition() {
        params.x = (int) (xInScreen);
        params.y = (int) (yInScreen);
        windowManager.updateViewLayout(this, params);
        MyWindowManager.updateLauncher();
    }
}

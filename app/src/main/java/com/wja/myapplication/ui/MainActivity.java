package com.wja.myapplication.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.wja.myapplication.R;
import com.wja.myapplication.animator.ParticleSmasher;
import com.wja.myapplication.animator.SmashAnimator;
import com.wja.myapplication.floatwindow.FloatWindowService;
import com.wja.myapplication.utils.FileUtils;

import java.io.File;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks{
    private static final String TAG="MainActivity";
    private Button explosionButton,dropButton,leftButton,rightButton,topButton,bottomButton,resetButton,zxingButton;
    private LinearLayout linearLayout;
    private ParticleSmasher particleSmasher;
    private static final int REQUEST_CAMERA=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // checkPermissions();
        particleSmasher=new ParticleSmasher(this);
        explosionButton= (Button) findViewById(R.id.explosion_button);
        dropButton= (Button) findViewById(R.id.drop_button);
        leftButton= (Button) findViewById(R.id.left_button);
        rightButton= (Button) findViewById(R.id.right_button);
        topButton= (Button) findViewById(R.id.top_button);
        bottomButton= (Button) findViewById(R.id.bottom_button);
        resetButton= (Button) findViewById(R.id.reset_button);
        explosionButton.setOnClickListener(this);
        dropButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        topButton.setOnClickListener(this);
        bottomButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        linearLayout= (LinearLayout) findViewById(R.id.layout_main);
        findViewById(R.id.sf_button).setOnClickListener(this);
        findViewById(R.id.zxing_button).setOnClickListener(this);
        findViewById(R.id.camera_button).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.explosion_button:
                particleSmasher.with(linearLayout)
                        .setStyle(SmashAnimator.STYLE_EXPLOSIOM)
                        .setDuration(3000)
                        .setStartDelay(300)
                       .setHorizontalMultiple(1)
                        .setVerticalMultiple(4)
                        .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                            @Override
                            public void onAnimatorStart() {
                                Log.i(TAG,"onAnimatorStart()");
                                Log.i(TAG, String.valueOf(new Date(System.currentTimeMillis())));
                            }

                            @Override
                            public void onAnimatorEnd() {
                                Log.i(TAG,"onAnimatorStart()");
                                Log.i(TAG, String.valueOf(new Date(System.currentTimeMillis())));
                            }
                        }).start();
                break;
            case R.id.drop_button:
               /* particleSmasher.with(linearLayout)
                        .setStyle(SmashAnimator.STYLE_DROP)
                        .setDuration(15000)
                        .setStartDelay(300)
                        .setHorizontalMultiple(4)
                        .setVerticalMultiple(4)
                        .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                            @Override
                            public void onAnimatorStart() {

                            }

                            @Override
                            public void onAnimatorEnd() {

                            }
                        }).start();*/
                break;
            case R.id.left_button:
                particleSmasher.with(linearLayout)
                        .setStyle(SmashAnimator.STYLE_FLOAT_LEFT)
                        .setDuration(3000)
                        .setStartDelay(300)
                        .setHorizontalMultiple(2)
                        .setVerticalMultiple(4)
                        .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                            @Override
                            public void onAnimatorStart() {

                            }

                            @Override
                            public void onAnimatorEnd() {

                            }
                        }).start();
                break;
            case R.id.right_button:
                particleSmasher.with(linearLayout)
                        .setStyle(SmashAnimator.STYLE_FLOAT_RIGHT)
                        .setDuration(15000)
                        .setStartDelay(300)
                       // .setHorizontalMultiple(4)
                      //  .setVerticalMultiple(4)
                        .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                            @Override
                            public void onAnimatorStart() {

                            }

                            @Override
                            public void onAnimatorEnd() {

                            }
                        }).start();
                break;
            case R.id.top_button:
               particleSmasher.with(linearLayout)
                        .setStyle(SmashAnimator.STYLE_FLOAT_TOP)
                        .setDuration(15000)
                        .setStartDelay(300)
                       // .setHorizontalMultiple(4)
                       // .setVerticalMultiple(4)
                        .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                            @Override
                            public void onAnimatorStart() {

                            }

                            @Override
                            public void onAnimatorEnd() {

                            }
                        }).start();
                break;
            case R.id.bottom_button:
               particleSmasher.with(linearLayout)
                        .setStyle(SmashAnimator.STYLE_FLOAT_BOTTOM)
                        .setDuration(15000)
                        .setStartDelay(300)
                       // .setHorizontalMultiple(4)
                       // .setVerticalMultiple(4)
                        .addAnimatorListener(new SmashAnimator.OnAnimatorListener() {
                            @Override
                            public void onAnimatorStart() {

                            }

                            @Override
                            public void onAnimatorEnd() {

                            }
                        }).start();
                break;
            case R.id.reset_button:
                particleSmasher.reShowView(linearLayout);
                break;
            case R.id.sf_button:
                Intent intent=new Intent(this, FloatWindowService.class);
                startService(intent);
                break;
            case R.id.zxing_button:
                checkPermissions();
                IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.setPrompt("请扫描"); //底部的提示文字，设为""可以置空
                integrator.setCameraId(0); //前置或者后置摄像头
                integrator.setBeepEnabled(true); //扫描成功的「哔哔」声，默认开启
                integrator.setOrientationLocked(true);
                integrator.setCaptureActivity(ScanActivity.class);
                integrator.initiateScan();
             //   startActivity(new Intent(MainActivity.this, CaptureActivity.class));

                break;
            case R.id.camera_button:
                String time=String.valueOf(System.currentTimeMillis()/1000);
                String filePath= FileUtils.getAppExternalStoragePath();
                Uri uri=Uri.fromFile(new File(filePath,time+".jpg"));
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

               // cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(cameraIntent,REQUEST_CAMERA);
                break;
        }

    }
    private void checkPermissions(){
        if (EasyPermissions.hasPermissions(this,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

        }else{
            EasyPermissions.requestPermissions(this,"请求桌面悬浮框权限",100,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      /*  IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (intentResult!=null){
            if (intentResult.getContents()==null){
                Log.d(TAG, "Cancelled");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d(TAG, "Scanned: " + intentResult.getContents());
                Toast.makeText(this, "Scanned: " +intentResult.getContents(), Toast.LENGTH_LONG).show();
            }
        }*/
    }
}

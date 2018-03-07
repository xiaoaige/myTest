package com.wja.myapplication.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wja.myapplication.presenter.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by hanke on 2018/2/9.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity{


    T listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideViewId());
        ButterKnife.bind(this);
        listener=createPresenter();
        intiData();
        initListener();


    }

    protected abstract int provideViewId();
    protected abstract void intiData();
    protected abstract void initListener();
    protected abstract T createPresenter();
}

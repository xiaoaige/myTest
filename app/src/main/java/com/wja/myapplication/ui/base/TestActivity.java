package com.wja.myapplication.ui.base;

import com.wja.myapplication.entity.News;
import com.wja.myapplication.presenter.INewsListListener;
import com.wja.myapplication.presenter.NewsListPresenter;

import java.util.List;

/**
 * Created by hanke on 2018/2/9.
 */

public class TestActivity extends BaseActivity<NewsListPresenter> implements INewsListListener {
    @Override
    public void onGetNewsListSuccess(List<News> newsList) {

    }

    @Override
    public void onError() {

    }

    @Override
    protected int provideViewId() {
        return 0;
    }

    @Override
    protected void intiData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected NewsListPresenter createPresenter() {
        return null;
    }
}

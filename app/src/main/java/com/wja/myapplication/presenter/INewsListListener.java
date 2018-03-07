package com.wja.myapplication.presenter;

import com.wja.myapplication.entity.News;

import java.util.List;

/**
 * Created by hanke on 2018/2/9.
 */

public interface INewsListListener {
    void onGetNewsListSuccess(List<News> newsList);
    void onError();
}

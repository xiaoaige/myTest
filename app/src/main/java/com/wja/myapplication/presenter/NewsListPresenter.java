package com.wja.myapplication.presenter;

import com.wja.myapplication.entity.NewsResponse;

import rx.Subscriber;

/**
 * Created by hanke on 2018/2/9.
 */

public class NewsListPresenter extends BasePresenter<INewsListListener>{


    public NewsListPresenter(INewsListListener listener) {
        super(listener);
    }


    public void getNewsList(String chanle){
        long later=System.currentTimeMillis()/1000;
        addSubscription(mApiService.getNewsList(chanle,String.valueOf(later), String.valueOf(later-10)), new Subscriber<NewsResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(NewsResponse newsResponse) {
                listener.onGetNewsListSuccess(null);

            }

        });

    }
}
